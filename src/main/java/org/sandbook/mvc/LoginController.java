package org.sandbook.mvc;

import org.sandbook.model.LogError;
import org.sandbook.model.Pagina;
import org.sandbook.model.Usuario;
import org.sandbook.srv.LogErrorService;
import org.sandbook.srv.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes({"usuario","loginName","loginNickName"})

public class LoginController {
	@Autowired
	LoginService loginServicio;
	@Autowired
	LogErrorService erroresServicio;

	@GetMapping({ "/", "/login" })
	public String mostrarLogin(ModelMap model) {
		model.addAttribute("pagina", new Pagina("Login", "login"));
		model.addAttribute("usuario", new Usuario());
		
		model.addAttribute("loginName", "Usuario desconocido");
		model.addAttribute("loginNickName", "Desconocido");

		return "login";
	}

	@PostMapping({ "/login" })
	public String procesaLogin(@Valid Usuario usuario, BindingResult validacion, String errores, ModelMap model) {

		if (validacion.hasErrors()) {
			// Hay errores y devolveremos
			System.out.println("Error al cargar");
			return "login";
		}
		// Verificar campos vacios
		if (usuario.getNickname().isEmpty() || usuario.getPassword().isEmpty()) {
			errores = "Introduce los dos campos del formulario";
			model.addAttribute("errores", errores);
			return "login";
		} else if (loginServicio.usuarioValidado(usuario)) {
			model.addAttribute("loginName", usuario.getNickname());
			model.addAttribute("loginNickName", usuario.getNickname());
			model.addAttribute("usuario", usuario);
			model.addAttribute("pagina", new Pagina("Home", "login"));

			return "redirect:list-libro";
		} else {
			errores = "Usuario '" + usuario.getNickname() + "' o contraseña incorrecta";
			model.addAttribute("errores", errores);
			
			//Guardar el error en el log
			erroresServicio.addError(new LogError("Login incorrecto", "Login incorrecto de '" + usuario.getNickname() + "'"));

			return "login"; // No ejecutara si la comprovación es correcta
		}
	}
}
