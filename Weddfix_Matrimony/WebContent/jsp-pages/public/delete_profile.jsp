<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
	var userId = '<%=session.getAttribute("userId") %>';
	if(userId == 'null') {
		window.location.href = "login";
	} 
	
	function validateForm() {
		validateUpdateProfileFields([ 'deleteProfileReason' ]);

	}
	
	function validateUpdateProfileFields(elementIds) {
		var count = 0;
		var length = elementIds.length;
		var flag = false;
		$.each( elementIds, function( index, id ){
			var elementValue = document.forms["form"][id].value;
			
			if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'email' || id == 'password' || id == 'password2' || id == 'mobile') {
				if(elementValue == null || elementValue == "" || elementValue == "-1"){
					$('#' + id).addClass( 'error' );
					if($("#errorValue").val() == undefined){
						$('#error').append(
						'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill all mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
					}
				} else {
					count = count + 1;
					$('#' + id).removeClass('error');
				}
			
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
//			alert("Success");
			$("#delete_profile_confirm").show();
		}
		}
	
	function formSubmit(){
		$("#delete_profile_confirm").hide();
		 $.ajax({
		     url:'delete_profile_success.action',
		     type: "POST",
	         async: false,
	         data: {
	        	 deleteProfileReason : $("#deleteProfileReason").val()
		     },
		     success: function (data) {
		    	 $("#success").show();
		    },
		 	error : function(xhr, textStatus, errorThrown) {
				// Handle error
		 		alert("error");
			}
		});
		}
	
	function cancel() {
		$("#delete_profile_confirm").hide();
	}
	
	function closePopup() {
		$("#delete_profile_confirm").hide();
		$("#success").hide();
		window.location.replace("delete_profile");
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
                    <li><a href="<%=request.getContextPath()%>/change_password">Change Password</a></li>
                    <li><a href="<%=request.getContextPath()%>/profile_picture">Profile Picture</a></li>
                    <li><a href="<%=request.getContextPath()%>/privacy_settings">Privacy Settings</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_profile">Update Profile</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_personal_details">Update Personal Details</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_partner_preference">Partner Preference</a></li>
                    <li><a href="<%=request.getContextPath()%>/deactivate_profile">Deactivate Profile</a></li>
                    <li class="active"><a href="<%=request.getContextPath()%>/delete_profile">Delete Profile</a></li>
<!--                     <li class="nav-divider"></li> -->
<!--                     <li><a href="javascript:;"><i class="glyphicon glyphicon-off"></i> Sign in</a></li> -->
                </ul>
            </nav>
        </div>
        <div class="col-sm-2 col-sm-offset-2">
        <section id="form-container">
	 <div class="main-content">
		<s:actionerror />
		<s:form cssClass="form" action="update_profile_success" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Delete Profile</h1>
			</div>
			
			<s:hidden name="id" value="%{userProfileBean['id']}"/>
			<c:if test="${isProfileDeleted == false }">
			<div class="form-row">
				<label><span>Enter the reason</span>
					<textarea name="deleteProfileReason" id="deleteProfileReason"></textarea>
				</label>
			</div>
			<div class="form-row">
				<button type="button" onclick="return validateForm();">Delete Account</button>
			</div>
			</c:if>
			<c:if test="${isProfileDeleted == true }">
			<div class="form-row">
			<p> Your profile deleted successfully. You want to re-activate your profile. Please contact your administrator.</p> 
			</div>
			<div class="form-row">
				<button type="button">Profile Deleted</button>
			</div>
			</c:if>
			</div>
			</div>
		</s:form>
	</div>
	</section>
	</div>
	</div>
        </div>
 <div id="delete_profile_confirm" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Are you sure. Delete your profile?</p>
		</div>
		<div class="alerty-action">
			<a class="btn-cancel" onclick="cancel();">Cancel</a>
			<a class="btn-ok" id="btnUpload" onclick="formSubmit();">Delete</a>
		</div>
	</div>
	</div>
 <div id="success" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your profile has been deleted successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>