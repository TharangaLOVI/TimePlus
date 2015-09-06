<%@page import="com.lovi.timeplus.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@ include file="includes/header.jsp"%>

<!--Slide show -->
<div class="container">
	
	
	<div class="jumbotron" >
	    <h1>ScheduleMe</h1>
	    <p>Schedule your life</p>
	    <p><a class="btn btn-primary btn-lg">Learn more</a></p>
	</div>
</div>
<!--end slide show -->



<!-- sign in form modal -->
<div class="modal fade modal-primary" id="signinModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">User Login</h4>
			</div>
			<div class="modal-body">
				<form id="loginForm" method="post" action="sign_in">
					<fieldset>
						<br />
						<div class="form-group">
							<input class="form-control floating-label" id="userId"
								name="userId" type="email" placeholder="email"
								data-validation="email" value="${user.userId}">
						</div>
						<div class="form-group">
							<input class="form-control floating-label" id="password_1"
								name="password" type="password" placeholder="Password"
								value="${user.password}">
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

<!-- end sign in form modal -->

<!-- sign up form modal -->
<script src="resources/js/form-validator/jquery.form-validator.min.js"></script>
<div class="modal fade modal-primary" id="signupModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">User registration</h4>
			</div>
			<div class="modal-body">
				<form id="signupForm" method="post" action="sign_up">
					<fieldset>
						<br />
						<div class="form-group">
							<input class="form-control floating-label" id="userId"
								name="userId" type="email" placeholder="email"
								data-validation="email" value="${user.userId}">
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
									<input class="form-control floating-label" name="firstName"
										id="firstName" type="text" placeholder="First Name"
										data-validation="length" data-validation-length="min3"
										data-validation="required" value="${user.firstName}"
										data-validation-help="First Name shuold have at least 3 charactors">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group ">
									<input class="form-control floating-label" name="lastName"
										id="lastName" type="text" placeholder="Last Name"
										data-validation="length" data-validation-length="min3"
										data-validation="required" value="${user.lastName}">
								</div>
							</div>
						</div>
						<div class="form-group">
							<input class="form-control floating-label" id="address"
								name="address" type="text" placeholder="Address"
								data-validation="required" value="${user.address}">
						</div>

						<div class="form-group">
							<input class="form-control floating-label" id="contactNo"
								name="contactNo" placeholder="Phone" type="text"
								data-validation="custom" value="${user.contactNo}"
								data-validation-regexp="^(0[1-9][0-9]{8})$"
								data-validation-error-msg="Please enter Valid phone number">
						</div>
						<div class="form-group">
							<input class="form-control floating-label" id="password"
								name="password" type="password" placeholder="Password"
								data-validation="required" data-validation="strength"
								data-validation-strength="2" data-validation="length"
								data-validation-length="min8" value="${user.password}">
						</div>
						<div class="form-group">
							<input class="form-control floating-label" id="signupRptPassword"
								type="password" placeholder="Repeate Password"
								data-validation="confirmation"
								data-validation-confirm="password">
						</div>

						<div class="form-group">
							<label>What is your role</label>

							<div>
								<label> <input type="radio" name="role"
									id="optionsRadios1" value="2"> Guardian
								</label> &nbsp; <label> <input type="radio" name="role"
									id="optionsRadios2" value="3"> Student
								</label>
							</div>

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
<script>
	$.validate({
		form : '#signupForm',
		modules : 'security',
		onModulesLoaded : function() {
			var optionalConfig = {
				fontSize : '6pt',
				padding : '1px',
				bad : 'Very bad',
				weak : 'Weak',
				good : 'Good',
				strong : 'Strong'
			};

			$('input[id="password"]').displayPasswordStrength(optionalConfig);

		},
		onSuccess : function() {

			return true; // Will stop the submission of the form
		}
	}

	);
</script>
<!--sign up form modal end -->

<%
	try {
		JSONObject message = (JSONObject) request
				.getAttribute(CommonConfig.MESSAGE);
		int status = message.getInt(CommonConfig.STATUS);
		String value = message.getString(CommonConfig.VALUE);
		String type = message.getString(CommonConfig.VIEW_MESSAGE_TYPE);
		out.print(message.toString());
		if (status == 1) {
			out.print("<script>notify('Error !','Sorry.You are logged in different account.Please log into linked account','danger','nav');</script>");
			out.print("<script>alert('"+value+"');</script>");
		} else {
			if (type.equals(CommonConfig.VIEW_TYPE_SIGN_IN)) {
				out.print("<script> notify('Error !','"
						+ value
						+ "','danger','#signinModal .modal-header'); </script>");
				out.print("<script>javascript:$('#signinModal').modal('show');</script>");
			} else if (type.equals(CommonConfig.VIEW_TYPE_SIGN_UP)) {
				out.print("<script> notify('Error !','"
						+ value
						+ "','danger','#signupModal .modal-header'); </script>");
				out.print("<script>javascript:$('#signupModal').modal('show');</script>");
			}

		}

	} catch (Exception e) {

	}
%>

<%@ include file="includes/footer.jsp"%>