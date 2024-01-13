<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">
	<p>Introduzca los datos a modificar</p>
	<p>${errores}</p>

	<mvc:form action="update-libro" method="post" modelAttribute="libro">
		<mvc:errors path="*" cssClass="text-warning" />

		<div class="form-row">
			<div class="col">
				<mvc:label path="titulo">Titulo:</mvc:label>
				<mvc:input path="titulo" type="text" id="titulo" required="required"
					class="form-control" />
				<mvc:errors path="titulo" cssClass="text-warning" />
			</div>
			<div class="col">
				<mvc:label path="autor">Autor:</mvc:label>
				<mvc:input path="autor" type="text" id="nombre" required="required"
					class="form-control" />
				<mvc:errors path="autor" cssClass="text-warning" />
			</div>
		</div>
		
		<div class="form-row">
			<div class="col">
				<mvc:label path="isbn">Isbn:</mvc:label>
				<mvc:input path="isbn" type="number" id="isbn" required="required"
					class="form-control" />
				<mvc:errors path="isbn" cssClass="text-warning" />

			</div>

			<div class="col">
				<mvc:label path="año">Año:</mvc:label>
				<mvc:input path="año" type="number" id="año" required="required"
					class="form-control" />
				<mvc:errors path="año" cssClass="text-warning" />

			</div>
		</div>
		
		<div class="form-row">

			<div class="col">
				<mvc:label path="interesadoEn">Categoria:</mvc:label>
				<br>
				<mvc:checkboxes element="p" path="interesadoEn" items="${interesadoEnLista}" />
			</div>

			<div class="col">
				<mvc:label path="genero">Género del autor:</mvc:label>
				<br>
				<mvc:radiobuttons element="p" path="genero" items="${generoLista}" />

			</div>
		</div>

		<mvc:hidden path="user" />
		<mvc:hidden path="ts" />

		<br>
		<input type="submit" value="Modificar" class="btn btn-success">
	</mvc:form>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>