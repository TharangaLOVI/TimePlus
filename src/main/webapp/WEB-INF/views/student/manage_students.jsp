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
						<li><a class="btn-material-light-blue-500" href="${pageContext.request.contextPath}/student">Students</a></li>
						<li><a class="btn-material-light-blue-500"
							href="${pageContext.request.contextPath}/student/report_event_dist">Report</a></li>
						<li><a class="btn-material-light-blue-500" href="${pageContext.request.contextPath}/sign_out">Signout</a></li>
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
								<h3 class="panel-title">Students</h3>

							</div>
							<div class="panel-body">
								<a class="btn btn-info btn-raised" style="float: right" href="#"
									data-toggle="modal" data-target="#studentAddModal"> New
									Student </a>

								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											<th>#</th>
											<th>Student Id</th>
											<th>Student First Name</th>
											<th>Student Last Name</th>
										</tr>
									</thead>
									<tbody>
									
										<% 
											List<Student> students = new ArrayList<Student>();
											students = (List<Student>) request.getAttribute("students");
											
											int i = 1;
											for(Student student : students){	
											
										%>
											
											<tr>
												<td><%=i++ %></td>
												<td><% out.print(student.getUserId()); %></td>
												<td><% out.print(student.getFirstName()); %></td>
												<td><% out.print(student.getLastName()); %></td>
											</tr>
										
										<%
											
											}
										%>
									</tbody>
								</table>

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

<!-- student add form modal -->
<div class="modal fade modal-primary" id="studentAddModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">New Student</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="student">
					<fieldset>
						<br />
						<div class="form-group">
							<input class="form-control floating-label" id="userId"
								name="userId" type="email" placeholder="email"
								data-validation="email" value="${student.userId}">
						</div>
						
						<div class="form-group">
							<input class="form-control floating-label" id="firstName"
								name="firstName" type="text" placeholder="first name"
								data-validation="email" value="${student.firstName}">
						</div>
						
						<div class="form-group">
							<input class="form-control floating-label" id="lastName"
								name="lastName" type="text" placeholder="last name"
								data-validation="email" value="${student.lastName}">
						</div>

						<div class="form-group">
							<input class="form-control floating-label" id="password_1"
								name="password" type="password" placeholder="password"
								value="${student.password}">
						</div>
						<div class="form-group">
							<div>
								<button type="button" class="btn btn-danger"
									data-dismiss="modal">Cancel</button>
								<button id="signupSubmit" type="submit"
									class="btn btn-material-blue-500">Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- student add form modal end-->

<%
	try {
		JSONObject message = (JSONObject) request
				.getAttribute(CommonConfig.MESSAGE);
		int status = message.getInt(CommonConfig.STATUS);
		String value = message.getString(CommonConfig.VALUE);
		String type = message.getString(CommonConfig.VIEW_MESSAGE_TYPE);
		
		if (status == 1) {
			out.print("<script>alert('" + value + "');</script>");
		} else {
			if (type.equals(CommonConfig.VIEW_TYPE_GUARDIAN_ADD_STUDENT)) {
				out.print("<script> notify('Error !','"
						+ value
						+ "','danger','#studentAddModal .modal-header'); </script>");
				out.print("<script>javascript:$('#studentAddModal').modal('show');</script>");
			}

		}

	} catch (Exception e) {

	}
%>
<%@ include file="../includes/footer.jsp"%>