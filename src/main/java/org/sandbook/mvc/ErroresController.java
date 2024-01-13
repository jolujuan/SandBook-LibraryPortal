package org.sandbook.mvc;

import java.util.List;

import org.sandbook.model.FiltrarErrores;
import org.sandbook.model.LogError;
import org.sandbook.model.Pagina;
import org.sandbook.srv.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("loginNickName")

public class ErroresController {
	@Autowired
	LogErrorService erroresServicio;

	@GetMapping({ "/list-logerror" })
	public String listatErrores(ModelMap model, @RequestParam(defaultValue = "nombre") String orden,
			@ModelAttribute("errores") String errores) {

		model.addAttribute("errores", errores);
		model.addAttribute("pagina", new Pagina("Lista Errores", "list-logerror"));
		model.addAttribute("logErrores", erroresServicio.listaErrores(orden));
		model.addAttribute("filtrarError", new FiltrarErrores());


		return "list-logerror";
	}

	@GetMapping({ "del-error" })
	public String borrarLog(ModelMap model, @RequestParam String borrarError,
			RedirectAttributesModelMap redirectAttributes) {

		try {
			String nickname = (String) model.getAttribute("loginNickName");
			if (nickname == null || nickname.isEmpty()) {
				throw new Exception(
						"<span style='color:red; font-weight: bold;'>Debes estar logueado para borrar</span");
			}

			erroresServicio.delError(Integer.parseInt(borrarError));
			model.clear();
			return "redirect:list-logerror";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errores", e.getMessage());
			return "redirect:list-logerror";
		}
	}
	
	@ModelAttribute("listaFiltrarError")
	public Object[] getListaFiltrar() {
		return erroresServicio.listaFiltrar().toArray();
	}
	
	
	@PostMapping({ "filtrar-error" })
	public String procesaFiltrarAlumno(ModelMap model, @Valid FiltrarErrores filtrarError, BindingResult validacion,
			RedirectAttributesModelMap redirectAttributes) {
		model.addAttribute("pagina", new Pagina("Lista Errores", "list-errores"));
		model.addAttribute("filtrarError", new FiltrarErrores());

		if (validacion.hasErrors()) {
			return "list-logerror";
		}
		try {
			List<LogError> erroresFiltrados = erroresServicio.filtrarErrores(filtrarError.getCampo(),
					filtrarError.getValor());

			if (!erroresFiltrados.isEmpty()) {
				model.addAttribute("logErrores", erroresFiltrados);
			} else {
				// Comprobar que existe el siguiente una vez hayas buscado alguno
				model.addAttribute("logErrores", erroresServicio.listaErrores("nombre"));
				model.addAttribute("errores",
						"<span style='color:red; font-weight: bold;'>No existe el error buscado</span");
			}

		} catch (Exception e) {
			// Si no existe volvera a cargar la pagina y avisara
			model.addAttribute("logErrores", erroresServicio.listaErrores("nombre"));
			model.addAttribute("errores",
					"<span style='color:red; font-weight: bold;'>No existe el error buscado</span");
		}
		
		return "list-logerror";
	}
}
