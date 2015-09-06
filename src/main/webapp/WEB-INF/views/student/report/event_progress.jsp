<%@page import="com.lovi.timeplus.models.Event"%>
<%@page import="com.lovi.timeplus.models.Student"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.lovi.timeplus.models.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.lovi.timeplus.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@include file="../../includes/header.jsp"%>


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
							<div class="panel-heading" id="report_panel">
								<h3 class="panel-title">Reports</h3>
							</div>
							<div class="panel-body">

								<ul class="nav nav-pills" style="float: right">
									<li >
										<a href="${pageContext.request.contextPath}/student/report_event_dist">Event
											Distribution
										</a>	
									</li>
									<li class="active">
										<a href="${pageContext.request.contextPath}/student/report_event_progress">Event Progress
										</a>
									</li>
								</ul>
								
								
								<form method="post" action="${pageContext.request.contextPath}/student/report_event_progress">
									<fieldset>
										<table>
											<tr>
												<td><input class="form-control floating-label"
													id="userId" name="studentId" type="email" required="required"
													placeholder="Student Id" data-validation="email"></td>
												<td>
													<button id="signupSubmit" type="submit" class="btn">Search
													</button>
												</td>
											</tr>
										</table>
									</fieldset>
								</form>

								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											<th>#</th>
											<th>Name</th>
											<th>Description</th>
											<th>Progress</th>
										</tr>
									</thead>
									<tbody>
									
										<% 
											List<Event> events = new ArrayList<Event>();
											events = (List<Event>) request.getAttribute("events");
											
											int i = 1;
											for(Event event : events){	
											
										%>
											
											<tr>
												<td><%=i++ %></td>
												<td><% out.print(event.getName()); %></td>
												<td><% out.print(event.getDescription()); %></td>
												
												<td><% out.print(event.getProgress()); %> %</td>
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
<%@ include file="../../includes/footer.jsp"%>