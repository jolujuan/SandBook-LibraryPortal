package org.sandbook.mvc;

import java.util.ArrayList;

import org.sandbook.model.ImagenUsuario;
import org.sandbook.model.Usuario;
import org.sandbook.srv.FileService;
import org.sandbook.srv.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {
	@Autowired
	LoginService loginService;
	@Autowired
	FileService fileService;

	@GetMapping("/imagenUsuario/{nickName}")
	public ResponseEntity<FileSystemResource> getFile(@PathVariable("nickName") String nickName) {
		try {
			String nombreFicheroConImagen = "Desconocido.jpg";// valor por defecto
			if (!"Desconocido".contentEquals(nickName)) { // Si no es el valor por defecto
				// La imagen siempre se almacenará como 'nickName.extensionImagen',
				// con la posibilidad de que extensionImagen sea JPG, PNG o GIF.
				// Para saber el formato consultaremos 'Usuario' para conseguir el nombre del
				// fichero
//				System.out.println("nickname es: " + nickName);
				Usuario usuarioAMostrarFoto = loginService.encontrarUsuarioPorNickname(nickName);
				nombreFicheroConImagen = usuarioAMostrarFoto.getNombreFicheroConImagen();
//				System.out.println("fichero " + nombreFicheroConImagen);
			}
			// El servicio nos devolverá la imagen y nos abstrae de saber donde esta
			// guardada realmente
			FileSystemResource resource = fileService.getImagenUsuario(nombreFicheroConImagen);
			if (!resource.exists()) {
				throw new Exception("La imagen no existe");
			}
			ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {// Ante cualquier error
			// Devolver error 404-recurso no encontrado
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/guardar-imagenUsuario")
	public String updateImagenUsuario(ModelMap model, @RequestParam String nickname) {

		ImagenUsuario imagen = new ImagenUsuario(nickname);
		model.addAttribute("imagenUsuario", imagen);

		return "update-imagenUsuario";
	}

	@PostMapping("/guardar-imagenUsuario")
	public String guardarImagenUsuario(ModelMap model, @Valid ImagenUsuario imagenUsuario, BindingResult validacion) {
		if (validacion.hasErrors()) {
			return "update-imagenUsuario";
		}

		String nickname = imagenUsuario.getNickname();
		Usuario usuarioAtributo = (Usuario) model.getAttribute("usuario");

		MultipartFile fichero = imagenUsuario.getImagen();
		try {
			if (usuarioAtributo == null || usuarioAtributo.getNickname() == null
					|| "Desconocido".equals(usuarioAtributo.toString())) {
				throw new Exception(
						"<span style='color:red; font-weight: bold;'>Debes estar logueado para modificar imagen</span<br>");
			}
			// Guarda la imagen y actualizar usuario
			// Si no se ha podido listaErroresAlGuardar no estara vacio
			ArrayList<String> listaErroresAlGuardar = fileService.guardaImagenUsuario(fichero, nickname);
			if (!listaErroresAlGuardar.isEmpty()) {
				// Rellenar los errores al intentar guardar para pasarselos a la excepcion
				String mensajeCompleto = "";
				for (String mensaje : listaErroresAlGuardar) {
					mensajeCompleto += mensaje + "<br>";
				}
				// lanxar excepcion
				throw new Exception(mensajeCompleto);
			}
			// Si llega aqui se ha guardado correctamente la imagen y podemos actualizar el
			// usuario

			Usuario quienModifica = (Usuario) model.getAttribute("usuario");
			Usuario usuarioAModificar = loginService.encontrarUsuarioPorNickname(nickname);
			usuarioAModificar.setNombreFicheroConImagen(fileService.getNombreImagenUsuario(fichero, nickname));
			loginService.modificaUsuario(usuarioAModificar, quienModifica.getNickname());
			// Para evitar parametros innecesarios
			model.clear();
			model.addAttribute(loginService.encontrarUsuarioPorNickname(nickname));
			model.addAttribute("imagenUsuario", new ImagenUsuario(nickname));
			return "update-imagenUsuario";
		} catch (Exception e) {
			// Pasamos el usuario actualizado
			model.addAttribute("usuario", loginService.encontrarUsuarioPorNickname(nickname));
			model.addAttribute("imagenUsuario", new ImagenUsuario(nickname));
			// Pasamos los errores
			model.addAttribute("errores", e.getMessage());
			return "update-imagenUsuario";

		}
	}

}
