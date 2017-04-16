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
		validateUpdateProfileFields([ 'deactivateProfileDays' ]);

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
						'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please select mandetory field.</span><input type="hidden" id="errorValue" value="1">').html;
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
			formSubmit();
		}
		}
	
	
	function formSubmit(){

		 $.ajax({
		     url:'deactivate_profile_success.action',
		     type: "POST",
	         async: false,
		     data: {
		    	 deactivateProfileDays : $("#deactivateProfileDays").val()
		     },
		     success: function (data) {
		    	 $("#deactiveProfileSuccess").show();
		    },
		 	error : function(xhr, textStatus, errorThrown) {
				// Handle error
		 		alert("error");
			}
		});
		}
	
	function activateProfileformSubmit(){

		 $.ajax({
		     url:'activate_profile_success.action',
		     type: "POST",
	         async: false,
	         data: {},
		     success: function (data) {
		    	 $("#activeProfileSuccess").show();
		    },
		 	error : function(xhr, textStatus, errorThrown) {
				// Handle error
		 		alert("error");
			}
		});
		}
	
	function closePopup() {
		$("#deactiveProfileSuccess").hide();
		$("#activeProfileSuccess").hide();
		window.location.replace("deactivate_profile");
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
                    <li class="active"><a href="<%=request.getContextPath()%>/deactivate_profile">Deactivate Profile</a></li>
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
		<s:form cssClass="form" action="update_profile_success" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Deactivate Profile</h1>
			</div>
			<s:hidden name="id" value="%{userProfileBean['id']}"/>
			<c:if test="${isProfileActive == true }">
			<div class="form-row">
			<p> Select the number of days / months you would like to keep your profile deactivated.</p> 
			</div>
			<div class="form-row">
			<label>
			<select id="deactivateProfileDays" name="deactivateProfileDays">
					<option value="-1">--- Select Days ---</option>
					<option value="15">15 Days</option>
					<option value="30">1 Month</option>
					<option value="60">2 Months</option>
					<option value="90">3 Months</option>
			</select>
			</label>
			</div>
			
			<div class="form-row">
				<button type="button" onclick="return validateForm();">Deactivate Now</button>
			</div>
			</c:if>
			<c:if test="${isProfileActive == false }">
			<div class="form-row">
			<p> Your profile currently deactivated. You want activate your profile now.</p> 
			</div>
			<div class="form-row">
				<button type="button" onclick="return activateProfileformSubmit();">Activate Now</button>
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
 <div id="deactiveProfileSuccess" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your profile has been deactivated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
 <div id="activeProfileSuccess" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your profile has been activated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>