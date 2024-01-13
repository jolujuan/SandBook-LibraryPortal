package org.sandbook.srv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sandbook.excepciones.LibroDuplicadoException;
import org.sandbook.model.DocLibro;
import org.sandbook.model.Libro;
import org.sandbook.order.ComparadorAutor;
import org.sandbook.order.ComparadorAño;
import org.sandbook.order.ComparadorIsbn;
import org.sandbook.order.ComparadorTitulo;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class LibroService {
	private static List<Libro> libros = new ArrayList<Libro>();
	static {
		libros.add(new Libro("Astilla", "Ira Levin", 21, 1991));
		libros.add(new Libro("Prométeme que serás libre", "Jorge Molist", 26, 2010));
		libros.add(new Libro("Oliver's Story", "Erich Segal", 19, 1977));
		libros.add(new Libro("Venganza en Sevilla", "Matilde Asensi", 22, 2010));
		libros.add(new Libro("Posesión", "A.S. Byatt", 24, 1984));
		libros.add(new Libro("1984", "George Orwell", 45, 1949));
		libros.add(new Libro("La Ratonera", "Agatha Christie", 77, 1952));
		libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 12, 1605));
		libros.add(new Libro("El Cumpleaños Secreto", "Kate Morton", 33, 2007));
		libros.add(new Libro("Cell", "Stephen King", 55, 2006));

	}
	private static ArrayList<DocLibro> docAlumnos = new ArrayList<DocLibro>();

	public void addLibro(Libro libro) throws LibroDuplicadoException {
		if (existeLibro(libro)) {
			throw new LibroDuplicadoException(encontrarLibro(libro.getIsbn()), libro);
		} else {
			libros.add(libro);
		}
	}

	public void addDocLibro(Libro libroPasado, DocLibro docLibro) {
		docLibro.setId(siguienteIdDoc());
		docAlumnos.add(docLibro);
		libroPasado.getDocsLibro().add(docLibro);
	}

	public int siguienteIdDoc() {
		int idMaximo;
		Optional<DocLibro> idMaxEncontrado = docAlumnos.stream().max(Comparator.comparing(DocLibro::getId));
		if (idMaxEncontrado.isPresent()) {
			idMaximo = idMaxEncontrado.get().getId() + 1;
		} else {
			idMaximo = 1;
		}
		return idMaximo;
	}

	public void delLibro(Libro libro) {
		libros.remove(libro);
	}

	boolean existeLibro(Libro nuevoLibro) {
		return libros.contains(nuevoLibro);
	}

	public Libro encontrarLibro(int isbnPasado) {
		Libro libroEncontrado = null;

		Optional<Libro> libroLeido = libros.stream().filter(libro -> libro.getIsbn() == isbnPasado).findFirst();
		libroEncontrado = libroLeido.get();
		return libroEncontrado;
	}

	public Optional<DocLibro> encontrarDocLibroPorDni_IdDoc(int isbn, Integer idDoc) {

		Optional<Libro> libroLeido = libros.stream().filter(libro -> libro.getIsbn() == isbn).findFirst();
		if (libroLeido.isPresent()) {

			return libroLeido.get().getDocsLibro().stream().filter(doc -> doc.getId().equals(idDoc)).findFirst();
		}
		return Optional.empty();
	}

	public List<Libro> listaLibros(String criterioOrd) {
		switch (criterioOrd) {
		case "titulo":
			Collections.sort(libros, new ComparadorTitulo());
			break;

		case "autor":
			Collections.sort(libros, new ComparadorAutor());
			break;

		case "isbn":
			Collections.sort(libros, new ComparadorIsbn());
			break;

		case "año":
			Collections.sort(libros, new ComparadorAño());
			break;
		default:
			// Este és el metode per defecte
			Collections.sort(libros);
		}
		return libros;
	}

	public void modificarLibro(Libro libroModificado, String nicknameQuienModifica) throws Exception {

		if (nicknameQuienModifica == null || libroModificado == null
				|| "Desconocido".equals(nicknameQuienModifica.toString())) {
			throw new Exception(
					"<span style='color:red; font-weight: bold;'>Para actualizar libro debe estar logeado</span<br>");
		} else {
			Libro libroActual = encontrarLibro(libroModificado.getIsbn());
			if (libroActual.sePuedeModificarUtilizando(libroModificado)) {
				libros.remove(libroActual);
				libroModificado.setUser(nicknameQuienModifica);
				libroModificado.setTs(new Date());
				libros.add(libroModificado);

			} else {
				throw new Exception(libroActual.mensajeNoSePuedeModificar());
			}
		}
	}

	public List<String> listaInteresaEn() {
		List<String> listaInteresaEn = new ArrayList<String>();
		listaInteresaEn.add("Novelas");
		listaInteresaEn.add("Ciencia Ficcion");
		listaInteresaEn.add("Misterio");

		return listaInteresaEn;
	}

	public List<String> listaGenero() {
		List<String> listaGenero = new ArrayList<String>();
		listaGenero.add("Hombre");
		listaGenero.add("Mujer");
		listaGenero.add("N/A");

		return listaGenero;
	}

	public List<String> listaFiltrar() {
		List<String> listaFiltrar = new ArrayList<String>();
		listaFiltrar.add("Titulo");
		listaFiltrar.add("Autor");
		listaFiltrar.add("Isbn");
		listaFiltrar.add("Año");

		return listaFiltrar;
	}
	
	public List<Integer> listaAños(){
		List<Integer> listaAños=new ArrayList<Integer>();
		
		listaAños=libros.stream().map(años-> años.getAño()).sorted().collect(Collectors.toList());
		
		return listaAños;
	}

	public List<String> listDoc() {
		List<String> listaDoc = new ArrayList<String>();
		listaDoc.add("Pedido");
		listaDoc.add("Reclamacion");
		listaDoc.add("Solicitud");

		return listaDoc;
	}

	public List<Libro> filtrarLibros(String campo, String valor) {
		List<Libro> librosFiltrados = new ArrayList<>();

		switch (campo) {
		case "Titulo":
			List<Libro> libroLeidoTitulo = libros.stream()
					.filter(titulo -> titulo.getTitulo().compareToIgnoreCase(valor) == 0).collect(Collectors.toList());
			librosFiltrados.addAll(libroLeidoTitulo);
			break;
		case "Autor":
			List<Libro> libroLeidoAutor = libros.stream()
					.filter(autor -> autor.getAutor().compareToIgnoreCase(valor) == 0).collect(Collectors.toList());
			librosFiltrados.addAll(libroLeidoAutor);
			break;
		case "Isbn":
			List<Libro> libroLeidoIsbn = libros.stream().filter(isbn -> isbn.getIsbn() == Integer.parseInt(valor))
					.collect(Collectors.toList());
			librosFiltrados.addAll(libroLeidoIsbn);
			break;
		case "Año":
			List<Libro> libroLeidoAño = libros.stream().filter(año -> año.getAño() == Integer.parseInt(valor))
					.collect(Collectors.toList());
			librosFiltrados.addAll(libroLeidoAño);
			break;
		default:
			break;
		}
		return librosFiltrados;
	}

	// Guardar el hash de los pedidos en una variable de sesion para no perder los
	// datos al actualizar
	@SuppressWarnings("unchecked")
	public Map<Integer, Libro> obtenerMapaLibros(HttpSession session) {
		Map<Integer, Libro> libros = (Map<Integer, Libro>) session.getAttribute("pedidos");
		if (libros == null) {
			libros = new HashMap<>();
			session.setAttribute("pedidos", libros);
		}
		return libros;
	}

}
