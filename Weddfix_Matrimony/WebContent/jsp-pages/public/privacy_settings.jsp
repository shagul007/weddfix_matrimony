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
	
	$(document).ready(function() {
		var ppHide = '<%=session.getAttribute("showMyProfilePicture")%>';
		if(ppHide == 'true') {
			$("#ppHide").val('No');
		} else {
			$("#ppHide").val('Yes');
		}
	});
	
	function formSubmit(){

		 $.ajax({
		     url:'privacy_settings_success.action',
		     type: "POST",
	         async: false,
		     data: { 
		    	 ppHide: $("#ppHide").val()
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
	
	function closePopup() {
		$("#success").hide();
		window.location.replace("privacy_settings");
	}

	function upgrade() {
		window.location.href = "upgrade";
	}
</script>
</head>
<body>
 <div class="container">
 <br />
	<h3>Privacy Settings<br />
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
                    <li class="active"><a href="<%=request.getContextPath()%>/privacy_settings">Privacy Settings</a></li>
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
		<s:actionerror />
		<s:form cssClass="form" action="privacy_settings_success" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Update Privacy Settings</h1>
			</div>
			<c:if test="${accountType != 'FREE'}">
			<div class="form-row">
			<label> <span>Hide my profile picture</span> 
			<select id="ppHide" name="ppHide" style="min-width: auto;">
					<option value="No">No</option>
					<option value="Yes">Yes</option>
			</select>
			</label>
			</div>
			<div class="form-row">
				<button type="button" onclick="formSubmit();">Update</button>
			</div>
			</c:if>
			<c:if test="${accountType == 'FREE'}">
			<p>Please upgrade your account to enable this feature. </p>
			<div class="form-row">
				<button type="button" onclick="upgrade();">Upgrade Account</button>
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
 <div id="success" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your privacy settings has been updated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>