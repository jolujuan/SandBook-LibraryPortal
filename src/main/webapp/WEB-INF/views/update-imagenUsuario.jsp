<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>
<style>
.container {
	text-align: center;
    max-width: 650px;
}

img{
margin-bottom:15px;}
</style>

<div class="container">
	<p>${errores}</p>
	<img src="imagenUsuario/${loginNickName}" class="rounded-circle"
		style="width: 150px; height: 150px"> <br>

	<mvc:form action="guardar-imagenUsuario" enctype="multipart/form-data"
		method="post" modelAttribute="imagenUsuario">

		<mvc:hidden path="nickname" />
		<mvc:label path="imagen">Puede actualizar la imagen seleccionando una nueva imagen:</mvc:label>
		<mvc:input path="imagen" type="file" />
		<br>
		<mvc:errors path="imagen" cssClass="text-warning" />
		<br>
		<button type="submit" class="btn btn-success">
			<i class="fas fa-user-edit"></i>&nbsp;Actualizar
		</button>
	</mvc:form>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>