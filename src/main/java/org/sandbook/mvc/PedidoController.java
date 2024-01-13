package org.sandbook.mvc;

import org.sandbook.model.Pagina;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PedidoController {
	@GetMapping({ "/list-pedido" })
	public String listadoPedidosLibro(ModelMap model, @ModelAttribute("errores") String errores) {

		model.addAttribute("errores", errores);
		model.addAttribute("pagina", new Pagina("Lista Pedidos", "list-pedido"));

		return "list-pedido";
	}
	//Aqui pasaria al checkout, eliminar y cambiar unidades...
}
