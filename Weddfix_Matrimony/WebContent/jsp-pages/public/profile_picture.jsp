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
		
		var file = $('[name="userFile"]');
	    
	    $('#btnUpload').on('click', function() {
	       /*  var filename = $.trim(file.val());
	        
	        if (!(isJpg(filename) || isPng(filename))) {
	            alert('Please browse a JPG/PNG file to upload ...');
	            return;
	        } */
	        
	        var elementValue = document.forms["fileForm"]["userFile"].value;
			
				if(elementValue == null || elementValue == ""){
					$('#userFile').addClass( 'error' );
				} else {
					$('#userFile').removeClass('error');
					$("#btnUpload").prop('disabled', true);
					$("#uploadingInfo").show();
	        
	        $.ajax({
	            url: 'uploadProfilePicture.action',
	            type: "POST",
	            async: false,
	            data: new FormData(document.getElementById("fileForm")),
	            enctype: 'multipart/form-data',
	            processData: false,
	            contentType: false
	          }).done(function(data) {
	        	  window.location.replace("profile_picture");
	        	  /* imgContainer.html('');
	              var img = '<img src="data:' + data.contenttype + ';base64,'
	                  + data.base64 + '"/>';
	    
	              imgContainer.append(img); */
	        	  $("#uploadingInfo").hide();
	          }).fail(function(jqXHR, textStatus) {
	              //alert(jqXHR.responseText);
	              $("#btnUpload").prop('disabled', false);
	              alert('File upload failed ...');
	          });
				}
	    });
	    
	    $('#btnClear').on('click', function() {
	        imgContainer.html('');
	        file.val('');
	    });
	});
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
                    <li class="active"><a href="<%=request.getContextPath()%>/profile_picture">Profile Picture</a></li>
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
		<form id="fileForm" class="form">
		<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Upload your profile picture</h1>
						</div>
			<input type="hidden" name="photoType" id="photoType" value="P<s:property value="%{myPersonalDetailsBean['profileId']}" />" />
			<c:if test="${myPersonalDetailsBean['profilePictureId'] != null }">
			<input type="hidden" name="profilePic" id="profilePic" value="<s:property value="%{myPersonalDetailsBean['profilePictureId']}" />" />
			</c:if>
			<c:if test="${myPersonalDetailsBean['profilePictureId'] == null }">
			<input type="hidden" name="profilePic" id="profilePic" value="null" />
			</c:if>
			<div class="form-row">
			<label> <span></span> 
				<img height="155" width="229"
								src="<s:url action="ImageAction?imageId=%{profilePicturesInfoBean[0]['fileName']}" />" alt="<s:property value="%{profilePicturesInfoBean[0]['fileName']}" />" />
				<span style="float: left; margin-top: 18px;">Upload Photo</span> <input id="userFile" name="userFile" type="file" style="margin-top: 5px;" onchange="isValidFile(this)" accept="image/png,image/gif,image/jpeg,image/pjpeg,image/jpg" />
			</label>
			</div>
			<div class="form-row">
				<button type="button" id="btnUpload">Submit</button>
							<input type="button" class="reset" onclick="this.form.reset()"
								value="Reset" />
			<div id="uploadingInfo" style="display: none; color: #541845;">Uploading... Please wait...</div>
			</div>
			</div>
			</div>
		</form>
	</div>
	</section>
	</div>
	</div>
        </div>
  </body>
</html>