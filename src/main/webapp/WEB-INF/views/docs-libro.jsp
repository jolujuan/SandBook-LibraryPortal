<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">

	<table class="table table-striped">
		<thead>
			<th>Titulo</th>
			<th>Autor</th>
			<th>Isbn</th>
			<th>Año</th>
		</thead>
		<tbody>
			<tr>
				<td>&nbsp;${libro.getTitulo()}&nbsp;</td>
				<td>&nbsp;${libro.getAutor()}&nbsp;</td>
				<td>&nbsp;${libro.getIsbn()}&nbsp;</td>
				<td>&nbsp;${libro.getAño()}&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<p>Si desa añadir un nuevo pedido introduzca los datos:</p>
	<p>${errores}</p>

	<mvc:form action="add-docLibro" method="post" modelAttribute="docLibro"
		enctype="multipart/form-data">
		<mvc:errors path="*" cssClass="text-warning" />
		<input name="isbn" type="hidden" value="${libro.getIsbn()}" />

		<div class="form-row">
			<div class="col">
				<mvc:label path="id">Id:</mvc:label>
				<br>
				<mvc:input path="id" type="text" readonly="true" />
				<br>
				<mvc:label path="cantidad">Cantidad:</mvc:label>
				<br>
				<mvc:input type="number" path="cantidad" />
			</div>
			<div class="col">
				<mvc:label path="tipo">Tipo:</mvc:label>
				<ul>
					<mvc:radiobuttons path="tipo" items="${opcionesTipoDoc}"
						element="p" />
				</ul>
			</div>
			<div class="col">
				<br> <br>
				<button type="submit" class="btn btn-success">
					<i class="fa fa-plus-circle"></i> Añadir
				</button>
			</div>
		</div>
		<div class="form-row">
			<div class="col">
				<mvc:label path="fichero">Albaran de compra (Solo reclamación):</mvc:label>
				<mvc:input path="fichero" type="file" />
			</div>
		</div>

	</mvc:form>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>