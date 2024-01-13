<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">
	<p>${errores}</p>
		<h3>¿Desea eliminar completamente el libro?</h3>

	<table class="table table-striped">
		<thead class="thead-dark">
			<th>Titulo</th>
			<th>Autor</th>
			<th>ISBN</th>
			<th>Año</th>
		</thead>
		<tbody>
			<tr>
				<td>&nbsp;${libroAEliminar.getTitulo()}&nbsp;</td>
				<td>&nbsp;${libroAEliminar.getAutor()}&nbsp;</td>
				<td>&nbsp;${libroAEliminar.getIsbn()}&nbsp;</td>
				<td>&nbsp;${libroAEliminar.getAño()}&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<p>
		<a class="btn btn-success" href="confirm-del-libro?borrarLibro=${libroAEliminar.getIsbn()}">Confirmar</a>
		<a class="btn btn-danger" href="list-libro">Cancelar</a>
	</p>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>