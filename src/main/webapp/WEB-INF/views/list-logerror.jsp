<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">
	<p>${errores}</p>

	<c:if test="${empty logErrores}">
		<h3>No hay errores en el servicio</h3>
	</c:if>

	<c:if test="${not empty logErrores}">

		<style>
select, .select {
	width: 80%;
	padding: 8px 15px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.boton {
	padding: 10px 15px;
	margin-top: 2.7em;
	width: 50%;
}

.form-row {
	margin-bottom: 15px;
}

.col {
	padding: 0 15px;
}

a {
	display: block;
	padding: .5rem 1rem;
}
</style>

		<mvc:form action="filtrar-error" method="post"
			modelAttribute="filtrarError">

			<div class="form-row">
				<div class="col">
					<mvc:label path="campo">Filtrar por:</mvc:label>
					<br>
					<mvc:select path="campo">
						<mvc:options items="${listaFiltrarError}" />
					</mvc:select>
				</div>


				<div class="col">
					<mvc:label path="valor">Como:</mvc:label>
					<br>
					<mvc:input path="valor" type="text" id="nombre" required="required"
						class="form-control select" />
				</div>

				<div class="col">
					<br> <input type="submit" value="Filtrar"
						class="btn btn-success boton">
				</div>

			</div>
		</mvc:form>
		<br>


		<table class="table table-striped">
			<thead class="thead-dark">
				<th><a>Id</a></th>
				<th><a>Tipo</a></th>
				<th><a>Explicación</a></th>

				<th>Acciones</th>
			</thead>
			<tbody>
				<c:forEach items="${logErrores}" var="error">
					<tr>
						<td>${error.id}</td>
						<td>${error.tipo}</td>
						<td>${error.explicacion}</td>

						<td><a class="btn btn-danger"
							href="del-error?borrarError=${error.id}"> Borrar</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>