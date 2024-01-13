package org.sandbook.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.sandbook.excepciones.LibroDuplicadoException;
import org.sandbook.model.DocLibro;
import org.sandbook.model.FiltrarLibro;
import org.sandbook.model.Libro;
import org.sandbook.model.LogError;
import org.sandbook.model.Pagina;
import org.sandbook.model.Usuario;
import org.sandbook.srv.FileService;
import org.sandbook.srv.LibroService;
import org.sandbook.srv.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")

public class LibroController {
	@Autowired
	LibroService libroServicio;
	@Autowired
	LogErrorService erroresServicio;
	@Autowired
	FileService fileServicio;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dataFormat, true));
	}

	@GetMapping({ "/list-libro" })
	public String listaLibros(ModelMap model, @RequestParam(defaultValue = "nombre") String orden,
			@ModelAttribute("errores") String errores) {

		model.addAttribute("errores", errores);
		model.addAttribute("pagina", new Pagina("Lista Libros", "list-libro"));
		model.addAttribute("libros", libroServicio.listaLibros(orden));
		model.addAttribute("filtrarLibro", new FiltrarLibro());

		return "list-libro";
	}

	Pagina nombrePagina = new Pagina("Añadir Libros", "list-libro");

	@GetMapping({ "add-libro" })
	public String mostrarLibro(ModelMap model) {
		model.addAttribute("pagina", nombrePagina);
		model.addAttribute("libro", new Libro());
		return "add-libro";
	}

	@PostMapping({ "add-libro" })
	public String añadirLibro(ModelMap model, @Valid Libro libro, BindingResult validacion) {

		String errores = "";
		model.addAttribute("pagina", nombrePagina);

		if (validacion.hasErrors()) {
			// Hay errores y devolveremos
			return "add-libro";
		}
		// Si llega aqui no hay errores
		try {
			libroServicio.addLibro(libro);
			// Evitar pasar parametros innecesarios
			model.clear();
			// Redirigir al listado una vez creado
			return "redirect:list-libro";
		} catch (NumberFormatException e) {
			errores = e.getMessage();
		} catch (LibroDuplicadoException e) {
			errores = e.toString();
		}

		// Si llega hasta aquí ha habido un error porque antes redirigimos
		model.addAttribute("errorDuplicado", errores);

		// Guardar el error en el log
		erroresServicio.addError(
				new LogError("Inserción duplicada", "Inserción duplicada del libro '" + libro.getIsbn() + "'"));

		return "add-libro";
	}

	@GetMapping({ "add-docLibro" })
	public String mostrarDocLibro(ModelMap model, @RequestParam int docLibro) {
		model.addAttribute("pagina", nombrePagina);

		// Mostrar libro pasado
		Libro libroMostrarDoc = libroServicio.encontrarLibro(docLibro);
		model.addAttribute("libro", libroMostrarDoc);

		// Encontrar doc del libro
		DocLibro docEncontrado = new DocLibro(libroServicio.siguienteIdDoc());
		model.addAttribute("docLibro", docEncontrado);

		return "docs-libro";
	}

	@PostMapping({ "add-docLibro" })
	public String añadirDocLibro(ModelMap model, @Valid DocLibro docLibro, BindingResult validacion,
			@RequestParam int isbn, HttpSession session) {

		model.addAttribute("pagina", nombrePagina);

		if (validacion.hasErrors()) {
			model.addAttribute("libro", libroServicio.encontrarLibro(isbn));
			return "docs-libro";
		}
		try {
			Libro libro = libroServicio.encontrarLibro(isbn);
			if (libro == null) {
				throw new Exception("<span style='color:red; font-weight: bold;'>Libro desconocido</span<br>");
			}

			Usuario usuarioAtributo = (Usuario) model.getAttribute("usuario");
			if (usuarioAtributo == null || usuarioAtributo.getNickname() == null
					|| "Desconocido".equals(usuarioAtributo.toString())) {
				throw new Exception(
						"<span style='color:red; font-weight: bold;'>Para añadir un pedido debe estar logeado</span<br>");
			}

			// Hacer la subida de archivos opcional
			if (docLibro.getFichero() != null && !docLibro.getFichero().isEmpty()) {
				// 1o Componer nombre del fichero
				String extension = fileServicio.getExtensionMultipartfile(docLibro.getFichero());
				String nombreFicheroAGuardar = String.format("%s_idDoc_%s.%s", isbn, docLibro.getId(), extension);

				// 2o Guardarlo
				// Si no se ha podido listaErroresAlGuardar no estará vacio
				ArrayList<String> listaErroresAlGuardar = fileServicio.guardaDocumentacionAlumno(docLibro.getFichero(),
						nombreFicheroAGuardar);
				if (!listaErroresAlGuardar.isEmpty()) {
					// Rellenar los errores al intentar guardar para pasarselos a la excepcion
					String mensajeCompleto = "";
					for (String mensaje : listaErroresAlGuardar) {
						mensajeCompleto += mensaje + "<br>";
					}
					// lanzar excepción
					throw new Exception(mensajeCompleto);
				}
				docLibro.setTipoFichero(extension);
				docLibro.setContentTypeFichero(docLibro.getFichero().getContentType());
			}

			libroServicio.addDocLibro(libro, docLibro);
			libroServicio.modificarLibro(libro, usuarioAtributo.getNickname());

			model.addAttribute("docLibro", new DocLibro(libroServicio.siguienteIdDoc()));

			// Hacerla persistente en la sesión y añadirla a un map, para mostrar los
			// diferentes libros añadidos al carrito
			Map<Integer, Libro> pedidos = libroServicio.obtenerMapaLibros(session);
			pedidos.put(isbn, libro);
			session.setAttribute("pedidos", pedidos);
			return "redirect:list-pedido";

		} catch (Exception e) {
			model.addAttribute("libro", libroServicio.encontrarLibro(isbn));
			model.addAttribute("errores", e.getMessage());
		}
		// Si llega hasta aquí ha habido un error porque antes redirigimos
		return "docs-libro";
	}

	@GetMapping("/descargar-docLibro/{isbn}/{idDoc}")
	public @ResponseBody void descargarDocLibro(HttpServletResponse response, @PathVariable("isbn") int isbn,
			@PathVariable("idDoc") Integer idDoc) throws IOException {
		try {
			Optional<DocLibro> docAlumno = libroServicio.encontrarDocLibroPorDni_IdDoc(isbn, idDoc);
			if (docAlumno.isPresent()) {// existe el documento de ese libro con ese id
				// Obtener fichero
				String nombreFichero = isbn + "_idDoc_" + idDoc + "." + docAlumno.get().getTipoFichero();
				FileSystemResource resource = fileServicio.getDocumentoAlumno(nombreFichero);
				if (!resource.exists()) {
					throw new IOException("El documento con el dni '" + isbn + "' y el id '" + idDoc + "' no existe;");
				}
				File fichero = resource.getFile();
				// Montar respuesta para el navegador web
				response.setContentType(docAlumno.get().getContentTypeFichero());
				response.setHeader("Content-Disposition", "attachment; filename=" + fichero.getName());
				response.setHeader("Content-Length", String.valueOf(fichero.length()));
				InputStream in = new FileInputStream(fichero);
				FileCopyUtils.copy(in, response.getOutputStream());
			} else {
				throw new IOException("El documento con el dni '" + isbn + "' y el id '" + idDoc + "' no existe;");
			}
		} catch (Exception e) {// Ante cualquier error
			// Devolver error 404-recurso no encontrado
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@ModelAttribute("interesadoEnLista")
	public Object[] getInteresadoEnLista() {
		return libroServicio.listaInteresaEn().toArray();
	}

	@ModelAttribute("generoLista")
	public Object[] getGeneroLista() {
		return libroServicio.listaGenero().toArray();
	}

	@ModelAttribute("listaFiltrar")
	public Object[] getListaFiltrar() {
		return libroServicio.listaFiltrar().toArray();
	}

	@ModelAttribute("opcionesTipoDoc")
	public Object[] getDocLista() {
		return libroServicio.listDoc().toArray();
	}

	@GetMapping({ "del-libro" })
	public String delLibro(@RequestParam int borrarLibro, ModelMap model,
			RedirectAttributesModelMap redirectAttributes) {

		try {
			Usuario usuarioActivo = (Usuario) model.getAttribute("usuario");
			if (usuarioActivo.getNickname() != null) {

				// Enviar a la nueva vista para confirmar el borrado
				model.addAttribute("libroAEliminar", libroServicio.encontrarLibro(borrarLibro));
				return "confirm-delete";

			} else {
				throw new Exception("Usuario no logeado");
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errores",
					"<span style='color:red; font-weight: bold;'>Para borrar libro debe estar logeado</span><br/>");
			return "redirect:list-libro";
		}
	}

	@GetMapping({ "confirm-del-libro" })
	public String confirmdelLibro(@RequestParam int borrarLibro, ModelMap model) {

		libroServicio.delLibro(new Libro(null, null, borrarLibro, 0));
		model.clear();
		return "redirect:list-libro";
	}

	@GetMapping({ "update-libro" })
	public String actualizarLibro(@RequestParam int modificarLibro, ModelMap model) {
		// Cambiamos el nombre de la página
		model.addAttribute("pagina", new Pagina("Modificar Libros", "list-libro"));
		Libro libroLeidoModificar = libroServicio.encontrarLibro(modificarLibro);
		model.addAttribute("libro", libroLeidoModificar);
		return "update-libro";
	}

	@PostMapping({ "update-libro" })
	public String procesarActualizarLibro(ModelMap model, @Valid Libro libro, BindingResult validacion) {
		model.addAttribute("pagina", new Pagina("Modificar libros", "list-libro"));

		String errores = "";

		if (validacion.hasErrors()) {
			// Hay errores y devolveremos
			return "update-libro";
		}
		// Si llega aqui no hay errores
		try {
			Usuario usuarioActivo = (Usuario) model.getAttribute("usuario");
			if (usuarioActivo != null) {
				libroServicio.modificarLibro(libro, usuarioActivo.getNickname());
				// Evitar pasar parametros innecesarios
				model.clear();
				// Redirigir al listado una vez modificado
				return "redirect:list-libro";
			} else {
				throw new Exception(
						"<span style='color:red; font-weight: bold;'>Para actualizar libro debe estar logeado</span<br>");
			}

		} catch (Exception e) {

			model.addAttribute(libroServicio.encontrarLibro(libro.getIsbn()));
			errores = e.getMessage();
			// Si llega hasta aquí ha habido un error porque antes redirigimos
			model.addAttribute("errores", errores);
			return "update-libro";
		}
	}

	@PostMapping({ "filtrar-libro" })
	public String filtrarLibro(ModelMap model, @Valid FiltrarLibro filtrarLibro, BindingResult validacion,
			RedirectAttributesModelMap redirectAttributes) {
		model.addAttribute("pagina", new Pagina("Modificar libros", "list-alumno"));

		if (validacion.hasErrors()) {
			return "list-libro";
		}
		try {
			List<Libro> librosFiltrados = libroServicio.filtrarLibros(filtrarLibro.getCampo(), filtrarLibro.getValor());

			if (!librosFiltrados.isEmpty()) {
				model.addAttribute("libros", librosFiltrados);
			} else {
				// Comprobar que existe el siguiente una vez hayas buscado alguno
				model.addAttribute("libros", libroServicio.listaLibros("titulo"));
				model.addAttribute("errores",
						"<span style='color:red; font-weight: bold;'>No existe el libro buscado</span");
			}

		} catch (Exception e) {
			// Si no existe volvera a cargar la pagina y avisara
			model.addAttribute("libros", libroServicio.listaLibros("buscado"));
			model.addAttribute("errores",
					"<span style='color:red; font-weight: bold;'>No existe el libro buscado</span");
		}
		return "list-libro";
	}
}
