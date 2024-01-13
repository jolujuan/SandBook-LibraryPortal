<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<div class="container">

	<c:if test="${empty pedidos}">
		<h3>No hay pedidos en el carrito</h3>
	</c:if>

	<c:forEach items="${pedidos}" var="entrada">
		<h3>Libro: ${entrada.value.titulo} (ISBN: ${entrada.key})</h3>
		<table class="table table-striped">
			<tr>
				<th>ID</th>
				<th>ISBN</th>
				<th>Tipo</th>
				<th>Cantidad</th>
			</tr>

			<c:forEach items="${entrada.value.docsLibro}" var="docLibro">
				<tr>
					<td>&nbsp;${docLibro.id}&nbsp;</td>
					<td>&nbsp;${docLibro.isbn}&nbsp;</td>
					<td>&nbsp;${docLibro.tipo}&nbsp;</td>
					<td>&nbsp;${docLibro.cantidad}&nbsp; <c:if
							test="${not empty docLibro.tipoFichero and docLibro.tipo == 'Reclamacion'}">
							<a href="descargar-docLibro/${docLibro.isbn}/${docLibro.id}"
								class="btn btn-info"
								style="margin-right: -100px; margin-left: 48px;"> <i
								class="fa fa-download"></i> Descargar
							</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</c:forEach>

	<c:if test="${not empty pedidos}">
		<p>
			<a class="btn btn-success" href="#">Checkout</a>
		</p>
	</c:if>
</div>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<%@ include file="../jspf/footer.jspf"%>