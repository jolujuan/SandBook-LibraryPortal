<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">
	<p>${errores}</p>

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
	margin-top: 4.2em;
	width: 50%;
}

.form-row {
	margin-bottom: 15px;
}

.col {
	padding: 0 15px;
}
</style>


	<mvc:form action="filtrar-libro" method="post"
		modelAttribute="filtrarLibro">

		<div class="form-row">
			<div class="col">
				<mvc:label path="campo">Filtrar por:</mvc:label>
				<br>
				<mvc:select path="campo">
					<mvc:options items="${listaFiltrar}" var="item"/>
				</mvc:select>
			</div>

			<div class="col">
				<mvc:label path="valor">Como:</mvc:label>
				<br>
				<mvc:input path="valor" type="text" id="nombre" required="required" 
					class="form-control select" />
			</div>

			<div class="col">
				<input type="submit" value="Filtrar" class="btn btn-success boton">
			</div>

		</div>
	</mvc:form>
	<br>
	<table class="table table-striped">
		<thead class="thead-dark">
			<th><a class="nav-link" href="list-libro?orden=titulo">Titulo</a></th>
			<th><a class="nav-link" href="list-libro?orden=autor">Autor</a></th>
			<th><a class="nav-link" href="list-libro?orden=isbn">ISBN</a></th>
			<th><a class="nav-link" href="list-libro?orden=año">Año</a></th>
			<th></th>
			<th><a>Acciones</a></th>
			<th></th>
		</thead>
		<tbody>
			<c:forEach items="${libros}" var="libro">
				<tr>
					<td>${libro.titulo}</td>
					<td>${libro.autor}</td>
					<td>${libro.isbn}</td>
					<td>${libro.año}</td>
					<td><a class="btn btn-info"
						href="update-libro?modificarLibro=${libro.isbn}"> Modificar</a></td>
					<td><a class="btn btn-danger"
						href="del-libro?borrarLibro=${libro.isbn}"> Borrar</a></td>
					<td><a class="btn btn-success"
						href="add-docLibro?docLibro=${libro.isbn}"> Añadir <i
							class="fas fa-shopping-cart"></i></a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>
		<a class="btn btn-success" href="add-libro">Añadir Libro</a>
	</p>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>