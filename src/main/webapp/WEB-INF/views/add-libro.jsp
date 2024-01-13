<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">
	<p>Introduzca los datos del nuevo libro</p>
	<p>${errorDuplicado}</p>

	<mvc:form action="add-libro" method="post" modelAttribute="libro">
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
				<mvc:input path="autor" type="text" id="autor" required="required"
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
				<mvc:label path="interesadoEn">Interesado en:</mvc:label>
				<br>
				<mvc:checkboxes path="interesadoEn" items="${interesadoEnLista}" />
			</div>

			<div class="col">
				<mvc:label path="genero">Género:</mvc:label>
				<br>
				<mvc:radiobuttons element="p" path="genero" items="${generoLista}" />

			</div>
		</div>

		<br>
		<input type="submit" value="Añadir" class="btn btn-success">
	</mvc:form>
</div>

<%@ include file="../jspf/footer.jspf"%>
