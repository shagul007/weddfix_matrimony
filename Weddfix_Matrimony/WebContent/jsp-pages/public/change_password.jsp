<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript" src="/js/md5.js"></script>   
<script type="text/javascript">
	var resetUserId = '<%=session.getAttribute("userId") %>';
	var cPw = '<%=session.getAttribute("password") %>';
	if(resetUserId == 'null'|| cPw ==  'null') {
		window.location.href = "login";
	} 
	
	$(document).ready(function() {
		$("#userId").val(resetUserId);
	});

	function validateForm() {
		validateChangePasswordFields([ 'currentPassword', 'password', 'password2' ]);
	}
	
	function validateChangePasswordFields(elementIds) {
	var count = 0;
	var length = elementIds.length;
	var flag = false;
	$.each( elementIds, function( index, id ){
		var elementValue = document.forms["form"][id].value;
		
		if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'currentPassword' || id == 'password' || id == 'password2') {
			if(elementValue == null || elementValue == ""){
				$('#' + id).addClass('error');
				if($("#errorValue").val() == undefined){
					$('#error').append(
					'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill all mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
				}
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
		
			if(id == 'currentPassword' && $("#currentPasswordMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				if (cPw != CryptoJS.MD5(elementValue).toString()) {
					count = count - 1;
					$('#' +id).after(
					'<span id="currentPasswordMessage" style="width: auto; display: block; color: red;">Old password not correct.</span><input type="hidden" id="currentPasswordMessageValue" value="1">').html;
				} else {
					$('#currentPasswordMessage').remove();
					$('#currentPasswordMessageValue').remove();
				}
			} else if(id == 'currentPassword' && elementValue != null && elementValue != "") {
				 if (cPw != CryptoJS.MD5(elementValue).toString()) {
					 count = count - 1;
					 if($("#currentPasswordMessageValue").val() == undefined){
						$('#' +id).after(
						'<span id="currentPasswordMessage" style="width: auto; display: block; color: red;"Old password not correct.</span><input type="hidden" id="currentPasswordMessageValue" value="1">').html;
					 }
					 } else {
							$('#currentPasswordMessage').remove();
							$('#currentPasswordMessageValue').remove();
					 }
			}
			
			if(id == 'password' && $("#passwordMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				if (elementValue.length < 6) {
					count = count - 1;
					$('#' +id).after(
					'<span id="passwordMessage" style="width: auto; display: block; color: red;">Password should be minimum six characters.</span><input type="hidden" id="passwordMessageValue" value="1">').html;
				} else {
					$('#passwordMessage').remove();
					$('#passwordMessageValue').remove();
				}
			} else if(id == 'password' && elementValue != null && elementValue != "") {
				 if (elementValue.length < 6) {
					 count = count - 1;
					 if($("#passwordMessageValue").val() == undefined){
						$('#' +id).after(
						'<span id="passwordMessage" style="width: auto; display: block; color: red;">Password should be minimum six characters.</span><input type="hidden" id="passwordMessageValue" value="1">').html;
					 }
					 } else {
							$('#passwordMessage').remove();
							$('#passwordMessageValue').remove();
					 }
			}
			if(id == 'password2' && $("#passwordMessageValue2").val() == undefined && elementValue != null && elementValue != ""){
			if (elementValue != document.forms["form"]["password"].value) {
				count = count - 1;
				$('#password2').after(
				'<span id="passwordMessage2" style="width: auto; display: block; color: red;">Both Password are not equal. Please re-enter password.</span><input type="hidden" id="passwordMessageValue2" value="1">').html;
				document.forms["form"]["password"].value = "";
				document.forms["form"][id].value = "";
			} else {
				$('#passwordMessage2').remove();
				$('#passwordMessageValue2').remove();
			}
			} 
			if(id == 'password2' && elementValue != null && elementValue != "") {
				if (elementValue != document.forms["form"]["password"].value) {
					count = count - 1;
					 if($("#passwordMessageValue2").val() == undefined){
						$('#' +id).after(
						'<span id="passwordMessage2" style="width: auto; display: block; color: red;">Both Password are not equal. Please re-enter password.</span><input type="hidden" id="passwordMessageValue2" value="1">').html;
						document.forms["form"]["password"].value = "";
						document.forms["form"][id].value = "";
					 }
					 } else {
							$('#passwordMessage2').remove();
							$('#passwordMessageValue2').remove();
					 }
				}
//			alert(count+" "+length);			
		} else {
			count = count + 1;
			$('#' + id).removeClass('error');
		}
		if(length == count){
			$('#error').hide();
			flag = true;
		}
	});
	
	if(flag){
//		alert("Success");
		formSubmit();
	}
	}
	
	function formSubmit(){

		 $.ajax({
		     url:'reset_password_success.action',
		     type: "POST",
	         async: false,
		     data: $("#form").serialize(),
		     success: function (data) {
		    	 $("#success").show();
		    },
		 	error : function(xhr, textStatus, errorThrown) {
				// Handle error
		 		alert("error");
			}
		});
		}
	
	function closePopup() {
		$("#success").hide();
		$("#form").trigger('reset');
		window.location.replace("profile_settings");
	}
</script>
</head>
<body>
 <div class="container">
 <br />
	<h3>Profile Settings<br />
<%--         <small>A simple, sheek navigation bar style!</small> --%>
    </h3>
    <br />
    
    <div class="row">
        <div class="col-sm-2">
            <nav class="nav-sidebar">
                <ul class="nav">
                    <li><a href="<%=request.getContextPath()%>/profile_settings">Account Details</a></li>
                    <li class="active"><a href="<%=request.getContextPath()%>/change_password">Change Password</a></li>
                    <li><a href="<%=request.getContextPath()%>/profile_picture">Profile Picture</a></li>
                    <li><a href="<%=request.getContextPath()%>/privacy_settings">Privacy Settings</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_profile">Update Profile</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_personal_details">Update Personal Details</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_partner_preference">Partner Preference</a></li>
                    <li><a href="<%=request.getContextPath()%>/deactivate_profile">Deactivate Profile</a></li>
                    <li><a href="<%=request.getContextPath()%>/delete_profile">Delete Profile</a></li>
<!--                     <li class="nav-divider"></li> -->
<!--                     <li><a href="javascript:;"><i class="glyphicon glyphicon-off"></i> Sign in</a></li> -->
                </ul>
            </nav>
        </div>
        <div class="col-sm-2 col-sm-offset-2">
        	<section id="form-container">
		<div class="main-content">
			<s:form cssClass="form" action="reset_password_success" name="form"
				id="form" method="post">
				<s:hidden id="userId" name="userId"/>
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Change Password</h1>
						</div>
						<div class="form-row">
							<label> <span>Old Password</span> <input id="currentPassword" name="currentPassword"
								type="password" maxlength="100" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>New Password</span> <input id="password" name="userPassword"
								type="password" maxlength="100" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>Re-type Password</span> <input id="password2" name="password2"
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
        </div>
    </div>
</div>
<div id="success" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your password has been changed successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>