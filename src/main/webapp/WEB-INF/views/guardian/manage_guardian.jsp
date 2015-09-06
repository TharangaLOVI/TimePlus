<%@page import="com.lovi.timeplus.models.Student"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.lovi.timeplus.models.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.lovi.timeplus.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">

		<!-- Left Menu -->
		<div class="col-lg-3 col-md-3">
			<div class="panel panel-material-light-blue-500">
				<div class="panel-heading">
					<h3 class="panel-title">Welcome</h3>
				</div>
				<div class="panel-body">
					<ul class="nav nav-pills nav-stacked">
						<li><a class="btn-material-light-blue-500" href="#">Guardians</a></li>
						<li><a class="btn-material-light-blue-500" href="${pageContext.request.contextPath}/sign_out">Sign
								out</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- Left Menu End-->

		<!-- 1 -->
		<div class="col-lg-9 col-md-9">

			<!-- 1-1 -->
			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12 col-md-12">

						<!-- Content -->
						<div class="panel panel-material-light-blue-500">
							<div class="panel-heading">
								<h3 class="panel-title">Guardians</h3>

							</div>
							<div class="panel-body">
							</div>
						</div>


					</div>
				</div>
				<!-- row end -->

			</div>
			<!-- 1-1 End-->
		</div>
		<!-- 1 End-->


	</div>
	<!-- row end -->
</div>
<!-- Container End -->
<%@ include file="../includes/footer.jsp"%>
