<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/menuSuperior.jspf"%>

<section class="background-radial-gradient overflow-hidden">
	<style>
.background-radial-gradient {
	background-color: hsl(218, 41%, 15%);
	background-image: radial-gradient(650px circle at 0% 0%, hsl(218, 41%, 35%)
		15%, hsl(218, 41%, 30%) 35%, hsl(218, 41%, 20%) 75%,
		hsl(218, 41%, 19%) 80%, transparent 100%),
		radial-gradient(1250px circle at 100% 100%, hsl(218, 41%, 45%) 15%,
		hsl(218, 41%, 30%) 35%, hsl(218, 41%, 20%) 75%, hsl(218, 41%, 19%) 80%,
		transparent 100%);
}

#radius-shape-1 {
	height: 220px;
	width: 220px;
	top: -60px;
	left: -130px;
	background: radial-gradient(#44006b, #ad1fff);
	overflow: hidden;
}

#radius-shape-2 {
	border-radius: 38% 62% 63% 37%/70% 33% 67% 30%;
	bottom: -60px;
	right: -110px;
	width: 300px;
	height: 300px;
	background: radial-gradient(#44006b, #ad1fff);
	overflow: hidden;
}

.bg-glass {
	background-color: hsla(0, 0%, 100%, 0.9) !important;
	backdrop-filter: saturate(200%) blur(25px);
}
</style>

	<script type="text/javascript">
		alert('Usuario - Password\n\njoselu - miPassword@1\njoseramon - miPassword@1');
	</script>

	<div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">

		<div class="row gx-lg-5 align-items-center mb-5">
			<div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
				<h1 class="my-5 display-5 fw-bold ls-tight"
					style="color: hsl(218, 81%, 95%)">
					SANDBOOK <br /> <span style="color: hsl(218, 81%, 75%)">para
						tu biblioteca</span>
				</h1>
				<p class="mb-4 opacity-70" style="color: hsl(218, 81%, 85%)">
					Una biblioteca online hecha a medida para llevar el seguimiento de
					stock, usuarios y controles sobre las mejores publicaciones a otro
					nivel. Más adelante contaremos con la venta de los libros online</p>
			</div>

			<div class="col-lg-6 mb-5 mb-lg-0 position-relative">
				<div id="radius-shape-1"
					class="position-absolute rounded-circle shadow-5-strong"></div>
				<div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

				<div class="card bg-glass">
					<div class="card-body px-4 py-5 px-md-5">
						<mvc:form action="login" method="post" modelAttribute="usuario">

							<!-- Email input -->
							<div class="form-outline mb-4">

								<mvc:label path="nickname" class="form-label"
									for="form3Example3">Username</mvc:label>
								<mvc:input path="nickname" type="text" id="form3Example3"
									class="form-control" />
								<mvc:errors path="nickname" cssClass="text-warning" />

							</div>

							<!-- Password input -->
							<div class="form-outline mb-4">

								<mvc:label path="password" class="form-label"
									for="form3Example4">Password</mvc:label>
								<mvc:password path="password" id="form3Example4"
									class="form-control" />
								<mvc:errors path="password" cssClass="text-warning" />

							</div>

							<!-- Submit button -->
							<button type="submit" class="btn btn-primary btn-block mb-4">
								Login</button>

							<div class="form-outline mb-4">
								<p>
									<strong><font color="red">${errores}</font></strong>
								</p>
							</div>

						</mvc:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<%@ include file="../jspf/footer.jspf"%>

