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
	var partnerPreferenceId = '<%=session.getAttribute("partnerPreferenceId") %>';
	if(userId == 'null') {
		window.location.href = "login";
	}
	
	if(partnerPreferenceId == 'null') {
		window.location.href = "partner_preference?profileIncomplete=true";
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
					$('#userFile').parent().parent().addClass( 'error' );
				} else {
					$('#userFile').parent().parent().removeClass('error');
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
	        	  window.location.replace("my_home");
	        	  /* imgContainer.html('');
	              var img = '<img src="data:' + data.contenttype + ';base64,'
	                  + data.base64 + '"/>';
	    
	              imgContainer.append(img); */
	        	  $("#uploadingInfo").hide();
	          }).fail(function(jqXHR, textStatus) {
	              //alert(jqXHR.responseText);
	              $("#btnUpload").prop('disabled', false);
	              $("#uploadingInfo").hide();
	              window.location.replace("my_home");
	          });
				}
	    });
	    
	    $('#btnClear').on('click', function() {
	        imgContainer.html('');
	        file.val('');
	    });
	});
	
	function update(userId, profileId) {
		$.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId
			},
			url : "view_profile_session.action",
			dataType : "json",
			success : function(data) {
				window.location.href = "view_profile";
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function hideTab(id) {
		if(id == 'home') {
			$("#profile").hide();
			$("#profile1").hide();
			$("#home").show();
		} else if(id == 'profile') {
			$("#profile1").hide();
			$("#home").hide();
			$("#profile").show();
		} else if(id == 'profile1') {
			$("#profile").hide();
			$("#home").hide();
			$("#profile1").show();
		}
	}
</script>
</head>
<body>
 <div class="grid_3">
  <div class="container">
   <div class="breadcrumb1">
     <ul>
        <a href="my_home"><i class="fa fa-home home_1"></i></a>
        <span class="divider">&nbsp;|&nbsp;</span>
        <li class="current-page">My Profile</li>
     </ul>
   </div>
   <div class="profile">
   	 <div class="col-md-8 profile_left">
   	 	<h2>Profile Id : <s:property value="%{myPersonalDetailsBean['profileId']}" /></h2>
   	 	<div class="col_3">
   	        <div class="col-sm-4 row_2">
				<div class="flexslider">
					 <ul class="slides">
			<c:choose>
			<c:when test="${profilePicturesInfoBean[0]['fileName'] != null}">
					<s:iterator value="profilePicturesInfoBean" status="incr">
					 <li data-thumb="<s:url action="ImageAction?imageId=%{profilePicturesInfoBean[#incr.index]['fileName']}" />">
							<a href="#popup1"><img width="200" height="200" src="<s:url action="ImageAction?imageId=%{profilePicturesInfoBean[#incr.index]['fileName']}" />" /></a>
					 </li>
					 </s:iterator>
			</c:when>
			<c:otherwise>
					 <c:if test="${myPersonalDetailsBean['gender'] == 'Male'}">
						<li data-thumb="<%=request.getContextPath()%>/images/No_Male_Photo.png">
							<a href="#popup1"><img width="200" height="200" src="<%=request.getContextPath()%>/images/No_Male_Photo.png" /></a>
						</li>
					 </c:if>
					 <c:if test="${myPersonalDetailsBean['gender'] == 'Female'}">
						<li data-thumb="<%=request.getContextPath()%>/images/No_Female_Photo.png">
							<a href="#popup1"><img width="200" height="200" src="<%=request.getContextPath()%>/images/No_Female_Photo.png" /></a>
						</li>
					 </c:if>
			</c:otherwise>
			</c:choose>
						<!--  <li data-thumb="images/p2.jpg">
							<img src="images/p2.jpg" />
						</li>
						<li data-thumb="images/p3.jpg">
							<img src="images/p3.jpg" />
						</li>
						<li data-thumb="images/p4.jpg">
							<img src="images/p4.jpg" />
						</li> -->
					 </ul>
				  </div>
			</div>
			<div class="col-sm-8 row_1" style="border: 2px solid; background-color: #ffefff;">
				<table class="table_working_hours">
		        	<tbody>
		        		<tr class="opened">
							<td class="day_label">Name :</td>
							<td class="day_value"><s:property value="%{myPersonalDetailsBean['fullName']}" /></td>
						</tr>
		        		<tr class="opened">
							<td class="day_label">Age / Height :</td>
							<td class="day_value"><s:property value="%{myPersonalDetailsBean['age']}" /> Yrs, <s:property value="%{myPersonalDetailsBean['height']}" /></td>
						</tr>
					    <!-- <tr class="opened">
							<td class="day_label">Last Login :</td>
							<td class="day_value">4 hours ago</td>
						</tr> -->
					    <tr class="opened">
							<td class="day_label">Religion :</td>
							<td class="day_value"><s:property value="%{myPersonalDetailsBean['religion']}" /></td>
						</tr>
					    <tr class="opened">
							<td class="day_label">Marital Status :</td>
							<td class="day_value"><s:property value="%{myPersonalDetailsBean['maritalStatus']}" /></td>
						</tr>
					    <tr class="opened">
							<td class="day_label">Location :</td>
							<td class="day_value"><s:property value="%{myPersonalDetailsBean['country']}" /></td>
						</tr>
					    <tr class="closed">
							<td class="day_label">Profile Created by :</td>
							<td class="day_value closed"><span><c:if test="${myPersonalDetailsBean['profileFor'] == 'Myself'}">Self</c:if><c:if test="${myPersonalDetailsBean['profileFor'] != 'Myself'}"><s:property value="%{myPersonalDetailsBean['profileFor']}" /></c:if></span></td>
						</tr>
					    <tr class="closed">
							<td class="day_label">Education :</td>
							<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['education']}" /></span></td>
						</tr>
				    </tbody>
				</table>
				<!-- <ul class="login_details">
			      <li>Already a member? <a href="login.html">Login Now</a></li>
			      <li>If not a member? <a href="register.html">Register Now</a></li>
			    </ul> -->
			</div>
			<div class="clearfix"> </div>
		</div>
		<div class="col_4" style="background-color: #ffefff; border: 2px solid;">
		    <div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
			   <ul id="myTab" class="nav nav-tabs nav-tabs1" role="tablist">
				  <li role="presentation" class="active"><a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true" onclick="hideTab('home');">About Myself</a></li>
				  <li role="presentation"><a href="#profile" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" onclick="hideTab('profile');">Family Details</a></li>
				  <li role="presentation"><a href="#profile1" role="tab" id="profile-tab1" data-toggle="tab" aria-controls="profile1" onclick="hideTab('profile1');">Partner Preference</a></li>
			   </ul>
			   <div id="myTabContent" class="tab-content" style="padding: 0px 10px 5px;">
				  <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
				   <!--  <div class="tab_box">
				    	<h1>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</h1>
				    	<p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor</p>
				    </div> -->
				    <div class="basic_1">
				    	<h3>Basics & Lifestyle</h3>
				    	<div class="col-md-6 basic_1-left">
				    	  <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Name :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['fullName']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Gender :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['gender']}" /></td>
								</tr>
							    <tr class="opened">
									<td class="day_label">Marital Status :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['maritalStatus']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Email :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['email']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Height :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['height']}" /></td>
								</tr>
								<c:if test="${myPersonalDetailsBean['weight'] != null }">
								<tr class="opened">
									<td class="day_label">Weight :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['weight']}" /></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['bodyType'] != null }">
								<tr class="opened">
									<td class="day_label">Body Type :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['bodyType']}" /></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['complexion'] != null }">
								<tr class="opened">
									<td class="day_label">Complexion :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['complexion']}" /></td>
								</tr>
								</c:if>
						    </tbody>
				          </table>
				         </div>
				         <div class="col-md-6 basic_1-left">
				          <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Age :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['age']}" /> Yrs</td>
								</tr>
								<tr class="opened">
									<td class="day_label">Marital Status :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['maritalStatus']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Mobile :</td>
									<td class="day_value">+91 <s:property value="%{myPersonalDetailsBean['mobile']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Profile Created by :</td>
									<td class="day_value closed"><span><c:if test="${myPersonalDetailsBean['profileFor'] == 'Myself'}">Self</c:if><c:if test="${myPersonalDetailsBean['profileFor'] != 'Myself'}"><s:property value="%{myPersonalDetailsBean['profileFor']}" /></c:if></span></td>
								</tr>
								<c:if test="${myPersonalDetailsBean['smoking'] != null }">
								<tr class="closed">
									<td class="day_label">Smoke :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['smoking']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['drinking'] != null }">
								<tr class="opened">
									<td class="day_label">Drink :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['drinking']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['food'] != null }">
								<tr class="closed">
									<td class="day_label">Food :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['food']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['physicalStatus'] != null }">
							    <tr class="opened">
									<td class="day_label">Physical Status :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['physicalStatus']}" /></span></td>
								</tr>
								</c:if>
						    </tbody>
				        </table>
				        </div>
				        <div class="clearfix"> </div>
				    </div>
				    <div class="basic_1">
				    	<h3>Religious / Social & Astro Background</h3>
				    	<div class="col-md-6 basic_1-left">
				    	  <table class="table_working_hours">
				        	<tbody>
				        	    <tr class="opened">
									<td class="day_label">Religion :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['religion']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Date of Birth :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['dob']}" /></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">city :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['city']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">State :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['state']}" /></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Country :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['country']}" /></td>
								</tr>
							 </tbody>
				          </table>
				         </div>
				         <div class="col-md-6 basic_1-left">
				          <table class="table_working_hours">
				        	<tbody>
				        		<c:if test="${myPersonalDetailsBean['caste'] != null }">
				        		<tr class="opened">
									<td class="day_label">caste :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['caste']}" /></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['motherTongue'] != null }">
								<tr class="opened">
									<td class="day_label">Mother Tongue :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['motherTongue']}" /></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['subCaste'] != null && myPersonalDetailsBean['subCaste'] != '' }">
				        		<tr class="opened">
									<td class="day_label">subCaste :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['subCaste']}" /></td>
								</tr>
								</c:if>
								<%-- <tr class="opened">
									<td class="day_label">Place of Birth :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['city']}" /></span></td>
								</tr> --%>
							   <%--  <tr class="opened">
									<td class="day_label">Sub Caste :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['religion']}" /></td>
								</tr>
							    <tr class="opened">
									<td class="day_label">Raasi :</td>
									<td class="day_value">Kanya</td>
								</tr> --%>
							</tbody>
				        </table>
				        </div>
				        <div class="clearfix"> </div>
				    </div>
				    <div class="basic_1 basic_2">
				    	<h3>Education &amp; Career</h3>
				    	<div class="basic_1-left">
				    	  <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Education :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['education']}" /></td>
								</tr>
				        		<tr class="opened">
									<td class="day_label">Occupation :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['occupation']}" /></td>
								</tr>
								<c:if test="${myPersonalDetailsBean['employedIn'] != null }">
								<tr class="opened">
									<td class="day_label">Employed In :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['employedIn']}" /></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['monthlyIncome'] != null }">
								<tr class="opened">
									<td class="day_label">Monthly Income :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['currency']}" /> <s:property value="%{myPersonalDetailsBean['monthlyIncome']}" /></span></td>
								</tr>
								</c:if>
							 </tbody>
				          </table>
				         </div>
				         <div class="clearfix"> </div>
				    </div>
				  </div>
				  <div role="tabpanel" class="tab-pane fade" id="profile" aria-labelledby="profile-tab">
				    <div class="basic_3">
				    	<h4>Family Details</h4>
				    	<div class="basic_1 basic_2">
				    	<h3>Basics</h3>
				    	<c:if test="${myPersonalDetailsBean['familyType'] == null}">
				    	<div class="form-row">
							<a href="update_personal_details" class="comment-btn" style="font-size: 13px; padding: 4px;">Update</a>
						</div>
						</c:if>
						<c:if test="${myPersonalDetailsBean['familyStatus'] != null}">
				    	<div  style="width: 50%;">
				    	  <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Family Status :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['familyStatus']}" /></td>
								</tr>
				        		<tr class="opened">
									<td class="day_label">Family Type :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['familyType']}" /></td>
								</tr>
							    <tr class="opened">
									<td class="day_label">Family Values :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['familyValues']}" /></span></td>
								</tr>
								<c:if test="${myPersonalDetailsBean['fathersStatus'] != null }">
							    <tr class="opened">
									<td class="day_label">Father's Status :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['fathersStatus']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['mothersStatus'] != null }">
							    <tr class="opened">
									<td class="day_label">Mother's Status :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['mothersStatus']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['numberOfBrothers'] != null && myPersonalDetailsBean['numberOfBrothers'] != '-1' }">
							    <tr class="opened">
									<td class="day_label">No. of Brothers :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['numberOfBrothers']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['brothersMarried'] != null && myPersonalDetailsBean['brothersMarried'] != '-1' }">
							    <tr class="opened">
									<td class="day_label">Brothers Married :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['brothersMarried']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['numberOfSisters'] != null && myPersonalDetailsBean['numberOfSisters'] != '-1' }">
							    <tr class="opened">
									<td class="day_label">No. of Sisters :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['numberOfSisters']}" /></span></td>
								</tr>
								</c:if>
								<c:if test="${myPersonalDetailsBean['sistersMarried'] != null && myPersonalDetailsBean['sistersMarried'] != '-1' }">
							    <tr class="opened">
									<td class="day_label">Sisters Married :</td>
									<td class="day_value closed"><span><s:property value="%{myPersonalDetailsBean['sistersMarried']}" /></span></td>
								</tr>
								</c:if>
							 </tbody>
				          </table>
				         </div>
				         </c:if>
				       </div>
				    </div>
				 </div>
				 <div role="tabpanel" class="tab-pane fade" id="profile1" aria-labelledby="profile-tab1">
				    <div class="basic_1 basic_2">
				       <div class="basic_1-left">
				    	  <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Age   :</td>
									<td class="day_value"><s:property value="%{partnerPreferenceDetailsBean['fromAge']}" /> to <s:property value="%{partnerPreferenceDetailsBean['toAge']}" /></td>
								</tr>
				        		<tr class="opened">
									<td class="day_label">Marital Status :</td>
									<td class="day_value"><s:property value="%{partnerPreferenceDetailsBean['maritalStatus']}" /></td>
								</tr>
							    <tr class="opened">
									<td class="day_label">Body Type :</td>
									<td class="day_value closed"><span><s:property value="%{partnerPreferenceDetailsBean['bodyType']}" /></span></td>
								</tr>
							    <tr class="opened">
									<td class="day_label">Complexion :</td>
									<td class="day_value closed"><span><s:property value="%{partnerPreferenceDetailsBean['complexion']}" /></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Height :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['fromHeight'] != null }"><s:property value="%{partnerPreferenceDetailsBean['fromHeight']}" /> to <s:property value="%{partnerPreferenceDetailsBean['toHeight']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['fromHeight'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Food :</td>
									<td class="day_value closed"><span><s:property value="%{partnerPreferenceDetailsBean['food']}" /></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Religion :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['religion'] != null }"><s:property value="%{partnerPreferenceDetailsBean['religion']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['religion'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Caste :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['caste'] != null }"><s:property value="%{partnerPreferenceDetailsBean['caste']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['caste'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Mother Tongue :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['motherTongue'] != null }"><s:property value="%{partnerPreferenceDetailsBean['motherTongue']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['motherTongue'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Education :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['education'] != null }"><s:property value="%{partnerPreferenceDetailsBean['education']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['education'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Occupation :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['occupation'] != null }"><s:property value="%{partnerPreferenceDetailsBean['occupation']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['occupation'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Country of Residence :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['country'] != null }"><s:property value="%{partnerPreferenceDetailsBean['country']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['country'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">State :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['state'] != null }"><s:property value="%{partnerPreferenceDetailsBean['state']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['state'] == null }">Any</c:if></span></td>
								</tr>
								<tr class="opened">
									<td class="day_label">Residency Status :</td>
									<td class="day_value closed"><span><c:if test="${partnerPreferenceDetailsBean['city'] != null }"><s:property value="%{partnerPreferenceDetailsBean['city']}" /></c:if><c:if test="${partnerPreferenceDetailsBean['city'] == null }">Any</c:if></span></td>
								</tr>
							 </tbody>
				          </table>
				        </div>
				     </div>
				 </div>
		     </div>
		  </div>
	   </div>
   	 </div>
     <div class="col-md-4 profile_right">
     	<!-- <div class="newsletter">
		   <form>
			  <input type="text" name="ne" size="30" required="" placeholder="Enter Profile ID :">
			  <input type="submit" value="Go">
		   </form>
        </div> -->
        <div class="view_profile">
        	<h3>View Similar Profiles</h3>
        	<s:iterator value="similarPartnerPreferenceInfoBean" status="incr">
        	<ul class="profile_item">
        	  <a href="javascript:update(<s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['userId']}" />, <s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['profileId']}" />)">
        	   <li class="profile_item-img">
        	   <c:choose>
			<c:when test="${similarPartnerPreferenceInfoBean[incr.index]['fileName'] != null}">
			<c:if test="${similarPartnerPreferenceInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${similarPartnerPreferenceInfoBean[incr.index]['gender'] == 'Male'}">
							<img width="105" height="90" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
					 </c:if>
					 <c:if test="${similarPartnerPreferenceInfoBean[incr.index]['gender'] == 'Female'}">
							<img width="105" height="90" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
					 </c:if>
					 </c:if>
					 <c:if test="${similarPartnerPreferenceInfoBean[incr.index]['showProfilePicture'] == 'true'}">
			<img width="105" height="90" src="<s:url action="ImageAction?imageId=%{similarPartnerPreferenceInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
			</c:if>
			</c:when>
			<c:otherwise>
			<c:if test="${similarPartnerPreferenceInfoBean[incr.index]['gender'] == 'Male'}">
						<img width="105" height="90" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" class="img-responsive" alt=""/>
					 </c:if>
					 <c:if test="${similarPartnerPreferenceInfoBean[incr.index]['gender'] == 'Female'}">
					 <img width="105" height="90" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" class="img-responsive" alt=""/>
					 </c:if>
			</c:otherwise>
			</c:choose>
        	   </li>
        	   <li class="profile_item-desc">
        	   	  <h4><s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['profileId']}" /></h4>
        	   	  <p><s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['age']}" /> Yrs, <s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['height']}" /> <s:property value="%{similarPartnerPreferenceInfoBean[#incr.index]['religion']}" /></p>
        	   	  <h5>View Full Profile</h5>
        	   </li>
        	   <div class="clearfix"> </div>
        	  </a>
           </ul>
        	  </s:iterator>
       </div>
       <div class="view_profile view_profile1">
        	<h3>Members who viewed my profile</h3>
        	<s:iterator value="whoViewedMyProfileInfoBean" status="incr">
        	<ul class="profile_item">
        	  <a href="javascript:update(<s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['userId']}" />, <s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['profileId']}" />)">
        	   <li class="profile_item-img">
        	   <c:choose>
			<c:when test="${whoViewedMyProfileInfoBean[incr.index]['fileName'] != null}">
			<c:if test="${whoViewedMyProfileInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${whoViewedMyProfileInfoBean[incr.index]['gender'] == 'Male'}">
							<img width="105" height="90" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
					 </c:if>
					 <c:if test="${whoViewedMyProfileInfoBean[incr.index]['gender'] == 'Female'}">
							<img width="105" height="90" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
					 </c:if>
					 </c:if>
					 <c:if test="${whoViewedMyProfileInfoBean[incr.index]['showProfilePicture'] == 'true'}">
			<img width="105" height="90" src="<s:url action="ImageAction?imageId=%{whoViewedMyProfileInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
				</c:if>
			</c:when>
			<c:otherwise>
			<c:if test="${whoViewedMyProfileInfoBean[incr.index]['gender'] == 'Male'}">
						<img width="105" height="90" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" class="img-responsive" alt=""/>
					 </c:if>
					 <c:if test="${whoViewedMyProfileInfoBean[incr.index]['gender'] == 'Female'}">
					 <img width="105" height="90" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" class="img-responsive" alt=""/>
					 </c:if>
			</c:otherwise>
			</c:choose>
        	   </li>
        	   <li class="profile_item-desc">
        	   	  <h4><s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['profileId']}" /></h4>
        	   	  <p><s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['age']}" /> Yrs, <s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['height']}" /> <s:property value="%{whoViewedMyProfileInfoBean[#incr.index]['religion']}" /></p>
        	   	  <h5>View Full Profile</h5>
        	   </li>
        	   <div class="clearfix"> </div>
        	  </a>
           </ul>
        	  </s:iterator>
         </div>
        </div>
       <div class="clearfix"> </div>
    </div>
  </div>
</div>
	<div id="popup1" class="overlay">
	<div class="popup">
		<h3 style="border-bottom: 2px solid #a56a96; box-sizing: border-box; color: #4c565e; display: inline-block;
    		font-size: 24px; margin: 0 auto 20px; padding: 0 30px 15px;">Upload your profile picture</h3>
		<a class="close" href="#">&times;</a>
		<div class="content">
		<form id="fileForm">
			<input type="hidden" name="photoType" id="photoType" value="P<s:property value="%{myPersonalDetailsBean['profileId']}" />" />
			<c:if test="${myPersonalDetailsBean['profilePictureId'] != null }">
			<input type="hidden" name="profilePic" id="profilePic" value="<s:property value="%{myPersonalDetailsBean['profilePictureId']}" />" />
			</c:if>
			<c:if test="${myPersonalDetailsBean['profilePictureId'] == null }">
			<input type="hidden" name="profilePic" id="profilePic" value="null" />
			</c:if>
			<div class="form-row">
			<label style="width: 100%; overflow: hidden;"> <span style="float: left; margin-right: 8px; margin-top: 10px;">Upload Photo</span> 
				<input id="userFile" name="userFile" type="file" onchange="isValidFile(this)" accept="image/png,image/gif,image/jpeg,image/pjpeg,image/jpg" />
			</label>
			</div>
		</form>
		</div>
		<div class="alerty-action">
			<a class="btn-cancel" href="#">Cancel</a>
			<a class="btn-ok" id="btnUpload">Confirm</a>
		</div>
		<div id="uploadingInfo" style="display: none; color: #541845;">Uploading... Please wait...</div>
	</div>
	</div>
  </body>
</html>