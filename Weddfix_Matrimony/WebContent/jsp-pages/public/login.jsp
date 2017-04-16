<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<html>
<head>
<script type="text/javascript">
	function validateForm() {
		validate([ 'email', 'password1' ]);

	}
</script>
</head>
<body>
	<section id="form-container">
		<div class="main-content">
			<s:form cssClass="form" action="user_home" name="form"
				id="form" method="post">
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<%if (ActionContext.getContext().getName() != null) {
						  if ((ActionContext.getContext().getName().equalsIgnoreCase("logout"))) {%>
						  	<div id="logoutInfo">You have logged out successfully.</div>
						<%}}%>
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Weddfix Login</h1>
						</div>
						<div class="form-row">
							<label> <span>Email</span> <input id="email" name="email"
								type="text" maxlength="100" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>Password</span> <input id="password1"
								name="password" type="password" maxlength="20" />
							</label>
						</div>
						<div class="form-row">
							<button type="button" onclick="return validateForm();">Log
								in</button>
							<input type="button" class="reset" onclick="this.form.reset()"
								value="Reset" />
						</div>
						<div class="form-row">
						<a href="forgot_password">Forgot password ?</a> 
						<span style="color: #000; font-weight: normal;">No account ?</span> <a href="register">Sign Up.</a>
						</div>
					</div>
				</div>
			</s:form>
		</div>
	</section>
</body>
</html>