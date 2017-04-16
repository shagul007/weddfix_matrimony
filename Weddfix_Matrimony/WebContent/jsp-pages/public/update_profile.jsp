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
		validateUpdateProfileFields([ 'userProfileId', 'fullName', 'genderId', 'maritalStatusId', 'dob',
		            		          'mobile', 'heightId', 'educationId', 'occupationId', 'religionId', 'country_Id', 'stateId', 'cityId', 'aboutYou' ]);

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
			
				if(id == 'mobile' && $("#mobileMessageValue").val() == undefined && elementValue != null && elementValue != ""){
					if (elementValue.length < 10) {
						count = count - 1;
						$('#' +id).after(
						'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
					 } else {
							$('#mobileMessage').remove();
							$('#mobileMessageValue').remove();
					 }
				 } else if(id == 'mobile' && elementValue != null && elementValue != "") {
					 if (elementValue.length < 10) {
						 count = count - 1;
						 if($("#mobileMessageValue").val() == undefined){
							$('#' +id).after(
							'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
						 }
						 } else {
								$('#mobileMessage').remove();
								$('#mobileMessageValue').remove();
						 }
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
	
	$(document).ready(function() {
		$("#userProfileId").val('<%=session.getAttribute("userProfileId")%>');
		$("#genderId").val('<%=session.getAttribute("genderId")%>');
		$("#maritalStatusId").val('<%=session.getAttribute("maritalStatusId")%>');
		$("#heightId").val('<%=session.getAttribute("heightId")%>');
		$("#educationId").val('<%=session.getAttribute("educationId")%>');
		$("#occupationId").val('<%=session.getAttribute("occupationId")%>');
		$("#religionId").val('<%=session.getAttribute("religionId")%>');
		$("#country_Id").val(<%=session.getAttribute("countryId")%>);
		$("#countryId").val($("#country_Id").val());
		$("#stateId").val(<%=session.getAttribute("stateId")%>);
		$("#cityId").val('<%=session.getAttribute("cityId")%>');
		onLoadStateByCountryId();
		onLoadCityByStateId();
	});

	function onLoadStateByCountryId() {
		var id = '<%=session.getAttribute("countryId")%>';;
		var stateId = '<%=session.getAttribute("stateId")%>';
		$("#stateId option").remove();
		$("#stateId").append(
				$("<option></option>").attr("value", "-1").text(
						"--- SELECT ---"));
		$.ajax({
			data : {

				country_Id : id
			},
			url : "loadStateByCountryId.action",
			dataType : "json",
			success : function(data) {
					$.each(data.stateMap, function(key,
							value) {
						$("#stateId").append(
								$("<option></option>").attr("value", key).text(
										value));
					});
					$("#stateId option[value="+stateId+"]").attr('selected', 'selected');
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				alert(xhr);
				alert(textStatus);
				alert(errorThrown);
			}
		});
		
	}

	function onLoadCityByStateId() {
		var id = '<%=session.getAttribute("stateId")%>';
		var cityId = '<%=session.getAttribute("cityId")%>';
		$("#cityId option").remove();
		$("#cityId").append(
				$("<option></option>").attr("value", "-1").text(
						"--- SELECT ---"));
		$.ajax({
			data : {

				state_Id : id
			},
			url : "loadCityByStateId.action",
			dataType : "json",
			success : function(data) {
					$.each(data.cityMap, function(key,
							value) {
						$("#cityId").append(
								$("<option></option>").attr("value", key).text(
										value));
					});
					$("#cityId option[value="+cityId+"]").attr('selected', 'selected');
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				alert(xhr);
				alert(textStatus);
				alert(errorThrown);
			}
		});
		
	}
	
	function setCountryId() {
		$("#countryId").val($("#country_Id").val());
	}
	
	function formSubmit(){

		 $.ajax({
		     url:'update_profile_success.action',
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
		window.location.replace("update_profile");
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
                    <li class="active"><a href="<%=request.getContextPath()%>/update_profile">Update Profile</a></li>
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
		<s:form cssClass="form" action="update_profile_success" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Update Profile</h1>
			</div>
			<s:hidden name="id" value="%{userProfileBean['id']}"/>
			<s:hidden name="countryId" id="countryId" />
			<div class="form-row">
			<label> <span>Profile for</span> 
			<select id="userProfileId" name="userProfileId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="profileMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Name</span> 
				<input id="fullName" name="fullName" type="text" value="<s:property value="%{userProfileBean['fullName']}" />" maxlength="50" onkeyup="checkName(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Gender</span> 
			<select id="genderId" name="genderId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="genderMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Marital status</span> 
			<select id="maritalStatusId" name="maritalStatusId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="maritalStatusMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Date of Birth</span> 
				<input id="dob" name="dateOfBirth" type="text" value="<s:property value="%{userProfileBean['dob']}" />" class="calendar" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Email</span> 
					<span>&nbsp;<s:property value="%{userProfileBean['email']}" /> </span>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Mobile</span> 
				<input id="mobile" name="mobile" type="text" value="<s:property value="%{userProfileBean['mobile']}" />" maxlength="10" onkeyup="checkNumeric(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Height</span> 
			<select id="heightId" name="heightId">
					<option value="-1">--- Feet/Inches ---</option>
				<s:iterator value="heightMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Highest Education</span> 
			<select id="educationId" name="educationId">
					<option value="-1">--- Select ---</option>
				<s:iterator value="educationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Occupation</span> 
			<select id="occupationId" name="occupationId">
					<option value="-1">--- Select ---</option>
				<s:iterator value="occupationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="religionMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Country</span> 
			<select id="country_Id" name="country_Id" onchange="setCountryId(); loadStateByCountryId(this);" onkeyup="setCountryId()">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="countryMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>State</span> 
			<select id="stateId" name="stateId" onchange="loadCityByStateId(this)">
					<option value="-1">--- SELECT ---</option>
				<%-- <s:iterator value="stateMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>City</span> 
			<select id="cityId" name="cityId">
					<option value="-1">--- SELECT ---</option>
				<%-- <s:iterator value="cityMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
				<label><span>Something About You</span>
					<textarea name="aboutYou" id="aboutYou"><s:property value="%{userProfileBean['aboutYou']}" /></textarea>
				</label>
			</div>
			<div class="form-row">
				<button type="button" onclick="return validateForm();">Update</button>
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
			<p>Your profile has been updated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>