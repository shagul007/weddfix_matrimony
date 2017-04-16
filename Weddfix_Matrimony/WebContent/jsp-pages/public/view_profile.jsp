<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="/matrimony/css/jquery.fancybox-1.3.4.css" />
<style type="text/css">
    a.fancybox img {
        border: none;
/*         box-shadow: 0 1px 7px rgba(0,0,0,0.6); */
        -o-transform: scale(1,1); -ms-transform: scale(1,1); -moz-transform: scale(1,1); -webkit-transform: scale(1,1); transform: scale(1,1); -o-transition: all 0.2s ease-in-out; -ms-transition: all 0.2s ease-in-out; -moz-transition: all 0.2s ease-in-out; -webkit-transition: all 0.2s ease-in-out; transition: all 0.2s ease-in-out;
    } 
    a.fancybox:hover img {
        position: relative; z-index: 999; -o-transform: scale(1.03,1.03); -ms-transform: scale(1.03,1.03); -moz-transform: scale(1.03,1.03); -webkit-transform: scale(1.03,1.03); transform: scale(1.03,1.03);
    }
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/1.3.4/jquery.fancybox-1.3.4.pack.min.js"></script>
<script type="text/javascript">
    $(function($){
        var addToAll = false;
        var gallery = true;
        var titlePosition = 'inside';
        $(addToAll ? 'img' : 'img.fancybox').each(function(){
            var $this = $(this);
            var title = $this.attr('title');
            var src = $this.attr('data-big') || $this.attr('src');
            var a = $('<a href="#" class="fancybox"></a>').attr('href', src).attr('title', title);
            $this.wrap(a);
        });
        if (gallery)
            $('a.fancybox').attr('rel', 'fancyboxgallery');
        $('a.fancybox').fancybox({
            titlePosition: titlePosition
        });
    });
    $.noConflict();
</script>
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
	        	  window.location.replace("my_home");
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
			if($("#dontshowalreadyViewed").val() == 'false') {
			$.ajax({
				data : {
					viewUserProfileId : $("#viewUserProfileId").val()
				},
				url : "updateEmailAndMobileCount.action",
				dataType : "json",
				success : function(data) {
					$("#dontshowalreadyViewed").val(true);
				},
				error : function(xhr, textStatus, errorThrown) {
					
				}
			});
			}
		}
	}
	
	function sendinterest(profileName, userId, profileId, status) {
		var accountValidity = <%=session.getAttribute("accountValidity")%>
		if(parseInt(accountValidity) <= 0) {
			$("#account_validity_expired").show();
		} else {
		$.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId,
				statusName : status
			},
			url : "send_interest.action",
			dataType : "json",
			success : function(data) {
				$("#sendInterest").hide();
				$("#sendInterested").show();
				$("#send_interested_profile").show();
				$("#send_interested_profile_name").text(profileName);
				$("#send_interested_profile_id").text(profileId);
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
		}
	}
	
	function sendreminder(profileName, userId, profileId, status) {
		var accountValidity = <%=session.getAttribute("accountValidity")%>
		if(parseInt(accountValidity) <= 0) {
			$("#account_validity_expired").show();
		} else {
			$("#send_reminder_profile").show();
			$("#send_reminder_profile_name").text(profileName);
			$("#send_reminder_profile_id").text(profileId);
		}
		/* $.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId,
				statusName : status
			},
			url : "send_interest.action",
			dataType : "json",
			success : function(data) {
				$("#sendInterest").hide();
				$("#sendInterested").show();
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		}); */
	}
	
	function closePopup() {
		$("#account_validity_expired").hide();
		$("#send_interested_profile").hide();
		$("#send_reminder_profile").hide();
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
        <li class="current-page">View Profile</li>
     </ul>
   </div>
   <div class="profile">
   	 <div class="col-md-6 profile_left">
   	 	<input type="hidden" id="viewUserProfileId" name="viewUserProfileId" value="<s:property value="%{myPersonalDetailsBean['profileId']}" />">
   	 	<input type="hidden" id="dontshowalreadyViewed" name="dontshowalreadyViewed" value="<s:property value="%{myPersonalDetailsBean['dontshowalreadyViewed']}" />">
   	 	<h2>Profile Id : <s:property value="%{myPersonalDetailsBean['profileId']}" /></h2>
   	 	<div class="col_3">
   	        <div class="col-sm-5 row_2">
				<div class="flexslider">
					 <ul class="slides">
			<c:choose>
			<c:when test="${profilePicturesInfoBean[0]['fileName'] != null}">
			<c:if test="${profilePicturesInfoBean[0]['showProfilePicture'] == 'false'}">
					<c:if test="${myPersonalDetailsBean['gender'] == 'Male'}">
							<img width="200" height="200" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
					 </c:if>
					 <c:if test="${myPersonalDetailsBean['gender'] == 'Female'}">
							<img width="200" height="200" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
					 </c:if>
					 </c:if>
					 <c:if test="${profilePicturesInfoBean[0]['showProfilePicture'] == 'true'}">
					<s:iterator value="profilePicturesInfoBean" status="incr">
					 <li data-thumb="<s:url action="ImageAction?imageId=%{profilePicturesInfoBean[#incr.index]['fileName']}" />">
							<a href="#"><img class="fancybox" width="200" height="200" src="<s:url action="ImageAction?imageId=%{profilePicturesInfoBean[#incr.index]['fileName']}" />" /></a>
					 </li>
					 </s:iterator>
					 </c:if>
			</c:when>
			<c:otherwise>
					 <c:if test="${myPersonalDetailsBean['gender'] == 'Male'}">
						<li data-thumb="<%=request.getContextPath()%>/images/No_Male_Photo.png">
							<a href="#"><img width="200" height="200" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" /></a>
						</li>
					 </c:if>
					 <c:if test="${myPersonalDetailsBean['gender'] == 'Female'}">
						<li data-thumb="<%=request.getContextPath()%>/images/No_Female_Photo.png">
							<a href="#"><img width="200" height="200" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" /></a>
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
			<div class="col-sm-6 row_1"  style="background-color: #ffefff; border: 2px solid;">
				<table class="table_working_hours" style="width: 115%;">
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
							<td class="day_value closed"><span><c:if test="${myPersonalDetailsBean['profileFor'] == 'Myself'}">Self</c:if><c:if test="${myPersonalDetailsBean['profileFor'] != 'Myself'}"><s:property value="%{myPersonalDetailsBean[#incr.index]['profileFor']}" /></c:if></span></td>
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
   	 </div>
   	 <div class="col-md-6 profile_right"  style="background-color: #ffefff; border: 2px solid;">
		    <div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
			   <ul id="myTab" class="nav nav-tabs nav-tabs1" role="tablist">
				  <li role="presentation" class="active"><a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true" onclick="hideTab('home');">About Myself</a></li>
				  <li role="presentation"><a href="#profile" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" onclick="hideTab('profile');">Family Details</a></li>
				  <li role="presentation"><a href="#profile1" role="tab" id="profile-tab1" data-toggle="tab" aria-controls="profile1" onclick="hideTab('profile1');">Contact Details</a></li>
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
								<%-- <tr class="opened">
									<td class="day_label">Email :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['email']}" /></td>
								</tr> --%>
								<tr class="opened">
									<td class="day_label">Height :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['height']}" /></td>
								</tr>
								<c:if test="${myPersonalDetailsBean['weight'] != null }">
								<tr class="opened">
									<td class="day_label">Weight :</td>
									<td class="day_value"><s:property value="%{myPersonalDetailsBean['weight']}" /> Kgs</td>
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
								<%-- <tr class="opened">
									<td class="day_label">Mobile :</td>
									<td class="day_value">+91 <s:property value="%{myPersonalDetailsBean['mobile']}" /></td>
								</tr> --%>
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
				    <div class="basic_3">
				    	<h4>Contact Details</h4>
				    	<div class="basic_1 basic_2">
				    	<div class="col-md-6 basic_1-left">
				    	<c:if test="${myPersonalDetailsBean['emailCount'] > 0 || myPersonalDetailsBean['mobileCount'] > 0}">
				    	  <table class="table_working_hours">
				        	<tbody>
				        	<c:if test="${myPersonalDetailsBean['mobileCount'] > 0}">
				        		<tr class="opened">
									<td class="day_label">Mobile :</td>
									<td class="day_value">+91 <s:property value="%{myPersonalDetailsBean['mobile']}" /></td>
								</tr>
							</c:if>
							<c:if test="${myPersonalDetailsBean['emailCount'] > 0}">
				        		<tr class="opened">
									<td class="day_label">Email :</td>
									<td class="day_value" style="text-transform: none;"><s:property value="%{myPersonalDetailsBean['email']}" /></td>
								</tr>
							</c:if>
							 </tbody>
				          </table>
				          </c:if>
				          <c:if test="${myPersonalDetailsBean['emailCount'] == 0 && myPersonalDetailsBean['mobileCount'] == 0}">
				    	<c:if test="${myPersonalDetailsBean['accepted'] == false && myPersonalDetailsBean['contactRequested'] == false}">
						   <div class="vertical" id="sendInterest" style="margin-bottom: 15px;">
						   <a style="color: #fff;" href="javascript:sendinterest('<s:property value="%{myPersonalDetailsBean['fullName']}" />', <s:property value="%{myPersonalDetailsBean['userId']}" />, <s:property value="%{myPersonalDetailsBean['profileId']}" />, 'SendInterest')">
						   Request Contact
						   </a></div>
						</c:if>
						<c:if test="${myPersonalDetailsBean['notInterested'] == true}">
						   <div class="vertical" id="sendInterest" style="margin-bottom: 15px;">
						   <a style="color: #fff;" href="javascript:sendinterest('<s:property value="%{myPersonalDetailsBean['fullName']}" />', <s:property value="%{myPersonalDetailsBean['userId']}" />, <s:property value="%{myPersonalDetailsBean['profileId']}" />, 'SendInterest')">
						   Your request has been rejected by <s:property value="%{myPersonalDetailsBean['fullName']}" />.
						   </a></div>
						</c:if>
						<c:if test="${myPersonalDetailsBean['accepted'] == false && myPersonalDetailsBean['contactRequested'] == true && myPersonalDetailsBean['notInterested'] == false}">
						   <div class="vertical" id="sendInterested" style="margin-bottom: 15px;">
						   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{myPersonalDetailsBean['fullName']}" />', <s:property value="%{myPersonalDetailsBean['userId']}" />, <s:property value="%{myPersonalDetailsBean['profileId']}" />, 'SendInterest')">
						   Contact Requested
						   </a></div>
						</c:if>
						   <div class="vertical" id="sendInterested" style="display: none; margin-bottom: 15px;">
						   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{myPersonalDetailsBean['fullName']}" />', <s:property value="%{myPersonalDetailsBean['userId']}" />, <s:property value="%{myPersonalDetailsBean['profileId']}" />, 'SendInterest')">
						   Contact Requested
						   </a></div>
						   <c:if test="${myPersonalDetailsBean['accepted'] == true}">
				    	  <table class="table_working_hours">
				        	<tbody>
				        		<tr class="opened">
									<td class="day_label">Mobile :</td>
									<td class="day_value">+91 <s:property value="%{myPersonalDetailsBean['mobile']}" /></td>
								</tr>
				        		<tr class="opened">
									<td class="day_label">Email :</td>
									<td class="day_value" style="text-transform: none;"><s:property value="%{myPersonalDetailsBean['email']}" /></td>
								</tr>
							 </tbody>
				          </table>
				          </c:if>
				          </c:if>
				         </div>
				       </div>
				    </div>
				 </div>
		     </div>
		  </div>
	   </div>
    </div>
  </div>
</div>
<div id="send_interested_profile" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your request has been sent to <span id="send_interested_profile_name"></span> (<span id="send_interested_profile_id"></span>)</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
<div id="send_reminder_profile" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>You have already requested <span id="send_reminder_profile_name"></span> (<span id="send_reminder_profile_id"></span>) contact details.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
<div id="account_validity_expired" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-edit"></i>Your account validity has been expired.</h4>
		</div>
		<div class="content">
			<p>Please upgrade your account to enable this feature. <a href="upgrade">Upgrade Now</a></p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
  </body>
</html>