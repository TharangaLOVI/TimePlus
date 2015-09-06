<%@page import="com.lovi.timeplus.session.UserSession"%>
<%@page import="com.lovi.timeplus.models.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">

<!-- floating action button dependencies -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css">
<link rel="stylesheet"
	href="http://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link
	href='http://fonts.googleapis.com/css?family=Raleway:100,200,300,400'
	rel='stylesheet' type='text/css'>
<link href="${pageContext.request.contextPath}/resources/floatingButton/mfb.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/floatingButton/lib/modernizr.touch.js"></script>
<script src="${pageContext.request.contextPath}/resources/floatingButton/mfb.js"></script>
<!-- end floating action button dependencies -->

<!-- bootstrap dependencies -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/maps.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/material.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/ripples.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-material-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/material-fullpalette.min.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/ripples.min.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/roboto.min.css" rel="stylesheet"
	type="text/css" />
<!-- end bootstrap dependencies -->
<script
	src="cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"
	type="text/javascript"></script>
<link
	href="cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" />
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/js/moment.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/urljs.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-material-datetimepicker.js"
	type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/effect.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/trsNotify.js" type="text/javascript"></script>
<style type="text/css">
.mfb-component__button--main, .mfb-component__button--child {
	background-color: #009688;
}
</style>
<title>ScheduleMe</title>
</head>
<body>
	
	<% 
		//geting userSession details
		UserSession userSession = (UserSession)request.getAttribute("user_session");
	%>
	

	<!-- navigation bar -->
	<div class="navbar navbar-material-light-blue-500">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-material-light-blue-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="javascript:void(0)">ScheduleMe</a>
		</div>
		<div
			class="navbar-collapse collapse navbar-material-light-blue-collapse">
			
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li class="active"><a href="#">About</a></li>
				
			</ul>

			<% if(userSession == null || userSession.getUser() == null){ %>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:void(0)"><span class="glyphicon glyphicon-user"></span> &nbsp;Welcome Guest</a></li>
				<li><a data-toggle="modal" data-target="#signinModal" href="#">Sign in</a></li>
				<li><a data-toggle="modal" data-target="#signupModal" href="#">Sign up</a></li>
			</ul>
			
			<% }else{ %>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a href="javascript:void(0)"><span class="glyphicon glyphicon-user"></span> &nbsp;Welcome ${user_session.user.firstName} </a></li>
				<li><a href="sign_out">Sign out</a></li>
			</ul>
			
			<% } %>
			
			
		</div>
	</div>
	<!-- end navigation bar -->