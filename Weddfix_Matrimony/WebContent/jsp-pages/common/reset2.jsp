<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	var resetUserId = '<%=session.getAttribute("resetUserId") %>';
	if(resetUserId == 'null') {
		window.location.href = "reset";
	} 
	
	$(document).ready(function() {
		$("#userId").val(resetUserId);
	});

	function validateForm() {
		validate([ 'password', 'password2' ]);
	}
</script>
</head>
<body>
<section id="form-container">
		<div class="main-content">
			<s:form cssClass="form" action="reset_password_success" name="form"
				id="form" method="post">
				<s:hidden id="userId" name="userId"/>
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Reset Password</h1>
						</div>
						<div class="form-row">
							<label> <span>Password</span> <input id="password" name="userPassword"
								type="password" maxlength="100" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>Password2</span> <input id="password2" name="password2"
								type="password" maxlength="100" />
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