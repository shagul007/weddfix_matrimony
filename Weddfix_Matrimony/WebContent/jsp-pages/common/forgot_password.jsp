<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	function validateForm() {
		validateForgotEmailFileds([ 'email' ]);

	}
</script>
</head>
<body>
	<section id="form-container">
		<div class="main-content">
			<s:form cssClass="form" action="forgot_password_success" name="form"
				id="form" method="post">
				<s:hidden id="validateUser" value="1" />
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Forgot Password</h1>
						</div>
						<div class="form-row">
							<label> <span>Email</span> <input id="email" name="emailId"
								type="text" maxlength="100" />
							</label>
						</div>
						<div class="form-row">
							<button type="button" onclick="return validateForm();">Submit</button>
							<input type="button" class="reset" onclick="this.form.reset()"
								value="Reset" />
						</div>
					</div>
				</div>
			</s:form>
		</div>
	</section>
</body>
</html>