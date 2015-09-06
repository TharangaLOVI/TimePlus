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
								<h3 class="panel-title">Charts</h3>
							</div>
							<div class="panel-body">

								<ul class="nav nav-pills" style="float: right">
									<li class="active">
										<a href="${pageContext.request.contextPath}/student/report_event_dist">
										Event Distribution
										</a>	
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/student/report_event_progress">
										Event Progress
										</a>
									</li>
								</ul>

								<script type="text/javascript">

									function loadReportData(){

										document.getElementById("piechart").innerHTML = "";
										
										var studentID = document.getElementById("userId").value;
										var url = '${pageContext.request.contextPath}/student/report_event_dist';
										console.log(url);
										
										$.ajax({
											'url' : url,
											'type' : 'POST',
											'data' : {'studentId':studentID},
											success: function(data) {
												console.log(data);
												var response = JSON.parse(data);

												if(response.status == 1){

													var value = response.value;
													var reportData = [["Task","Event Distribution"]];

													if(value.Study != undefined){
														reportData.push(["Study",value.Study]);
													}else{
														reportData.push(["Study",0]);
													}

													if(value.Sport != undefined){
														reportData.push(["Sport",value.Sport]);
													}else{
														reportData.push(["Sport",0]);
													}

													if(value.Sleep != undefined){
														reportData.push(["Sleep",value.Sleep]);
													}else{
														reportData.push(["Sleep",0]);
													}

													if(value.Other != undefined){
														reportData.push(["Other",value.Other]);
													}else{
														reportData.push(["Other",0]);
													}

													if(value.Free != undefined){
														reportData.push(["Free",value.Free]);
													}else{
														reportData.push(["Free",0]);
													}

													console.log(reportData);
													drawChart(reportData);
												}else{
													
													notify('Error !',response.value,'danger','#report_panel ');
												}
											},
										});
								
										return false;
										
									}
	
								</script>


								<form method="post" onsubmit="return loadReportData()">
									<fieldset>
										<table>
											<tr>
												<td><input class="form-control floating-label"
													id="userId" name="userId" type="email" required="required"
													placeholder="Student Id" data-validation="email"></td>
												<td>
													<button id="signupSubmit" type="submit" class="btn">Search
													</button>
												</td>
											</tr>
										</table>
									</fieldset>
								</form>

								<script type="text/javascript"
									src="https://www.google.com/jsapi"></script>
								<script type="text/javascript">
									google.load("visualization", "1", {
										packages : [ "corechart" ]
									});
									//google.setOnLoadCallback(drawChart);
									function drawChart(reportData) {

										var data = google.visualization
												.arrayToDataTable(reportData);

										var options = {
											title : 'Events Distribution',
											is3D: true,
										};

										var chart = new google.visualization.PieChart(
												document
														.getElementById('piechart'));

										chart.draw(data, options);
									}
								</script>
								<div class="row" align="center">
									<div id="piechart" style="width: 700px; height: 500px;"></div>
								</div>


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