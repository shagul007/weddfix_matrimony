<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.nio.channels.SeekableByteChannel"%>
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
	
	function shortlist(profileName, userId, profileId, status, index) {
		$.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId,
				statusName : status
			},
			url : "shortlist.action",
			dataType : "json",
			success : function(data) {
				$("#shortlist"+index).hide();
				$("#shortlisted"+index).show();
				$("#profile_shortlisted").show();
				$("#profile_name").text(profileName);
				$("#profile_id").text(profileId);
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function shortlisted(profileName, userId, profileId, status, index) {
				$("#remove_profile_shortlisted").show();
				$("#remove_profile_name").text(profileName);
				$("#remove_profile_id").text(profileId);
				$("#profileName").val(profileName);
				$("#userId").val(userId);
				$("#profileId").val(profileId);
				$("#status").val(status);
				$("#index").val(index);
	}
	
	function deleteShortlisted() {
		var index = $("#index").val();
		$.ajax({
			data : {
				profileUserId : $("#userId").val(),
				userProfileId : $("#profileId").val(),
				statusName : $("#status").val()
			},
			url : "shortlist.action",
			dataType : "json",
			success : function(data) {
				$("#remove_profile_shortlisted").hide();
				$("#shortlisted"+index).hide();
				$("#shortlist"+index).show();
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function sendinterest(profileName, userId, profileId, status, index) {
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
				$("#sendInterest"+index).hide();
				$("#sendInterested"+index).show();
				$("#send_interested_profile").show();
				$("#send_interested_profile_name").text(profileName);
				$("#send_interested_profile_id").text(profileId);
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
		}
	}
	
	function sendreminder(profileName, userId, profileId, status, index) {
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
		$("#profile_shortlisted").hide();
		$("#remove_profile_shortlisted").hide();
		$("#send_interested_profile").hide();
		$("#send_reminder_profile").hide();
	}
	
	$( document ).ready(function() {
		$("#pagination").dataTable(
				{
					bFilter: false,
					bInfo: false,
// 					"aoColumns": [{"bSortable": false}, null],
// 					"aaSorting": [{"bSortable": false}],
					"order": [[ 0, "desc" ]], 
					"bLengthChange": false,
					"aLengthMenu": [2, 5],
			        "iDisplayLength": 2
				}		
		);
	});
	
</script>
</head>
<body>
 <div class="grid_3">
  <div class="container">
   <div class="breadcrumb1">
     <ul>
        <a href="my_home"><i class="fa fa-home home_1"></i></a>
        <span class="divider">&nbsp;|&nbsp;</span>
        <li class="current-page">New Matches</li>
     </ul>
   </div>
   <div class="col-md-9 profile_left2">
     <form>
	  <div class="form_but2">
		<p class="control-lable1" for="sex">You need more matches. Check your <a href="update_partner_preference">Partner Preference</a> </p>
		<div class="clearfix"> </div>
	  </div>
	 </form>
	<table id="pagination">
	<c:if test="${!newMatchesInfoBean.isEmpty() }">
		<thead> 
		<tr style="display: none;"><th></th></tr>
		</thead>
	</c:if>
	 <s:iterator value="newMatchesInfoBean" status="incr">
		<tr><td>
	 <div class="profile_top"><a href="javascript:update(<s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />)">
      <span style="font-size: 20px;"><s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" /></span>
      <h2><s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" /> | Profile Created for <c:if test="${newMatchesInfoBean[incr.index]['profileFor'] == 'Myself'}">Self</c:if><c:if test="${newMatchesInfoBean[incr.index]['profileFor'] != 'Myself'}"><s:property value="%{newMatchesInfoBean[#incr.index]['profileFor']}" /></c:if></h2>
	    <c:choose>
			<c:when test="${newMatchesInfoBean[incr.index]['fileName'] != null}">
			<c:if test="${newMatchesInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${newMatchesInfoBean[incr.index]['gender'] == 'Male'}">
					<div class="col-sm-3 profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
					</div>
					 </c:if>
					 <c:if test="${newMatchesInfoBean[incr.index]['gender'] == 'Female'}">
					 <div class="col-sm-3 profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
					</div>
					 </c:if>
					 </c:if>
					 <c:if test="${newMatchesInfoBean[incr.index]['showProfilePicture'] == 'true'}">
					<div class="col-sm-3 profile_left-top">
				    	<img width="200" height="150" src="<s:url action="ImageAction?imageId=%{newMatchesInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
				    </div>
				    </c:if>
			</c:when>
			<c:otherwise>
					 <c:if test="${newMatchesInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="col-sm-3 profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" />
						</div>
					 </c:if>
					 <c:if test="${newMatchesInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="col-sm-3 profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" />
						</div>
					 </c:if>
			</c:otherwise>
			</c:choose>
	    </a>
	    <div class="col-sm-3">
	      <ul class="login_details1">
			 <li>About me :</li>
			 <li><p><s:property value="%{newMatchesInfoBean[#incr.index]['aboutMe']}" /></p></li>
		  </ul>
	    </div>
	    <div class="col-sm-6">
	    	<table class="table_working_hours">
	        	<tbody>
	        		<tr class="opened_1">
						<td class="day_label1">Age / Height :</td>
						<td class="day_value"><s:property value="%{newMatchesInfoBean[#incr.index]['age']}" /> Yrs, <s:property value="%{newMatchesInfoBean[#incr.index]['height']}" /></td>
					</tr>
				    <tr class="opened">
						<td class="day_label1">Religion :</td>
						<td class="day_value"><s:property value="%{newMatchesInfoBean[#incr.index]['religion']}" /></td>
					</tr>
				    <tr class="opened">
						<td class="day_label1">Marital Status :</td>
						<td class="day_value"><s:property value="%{newMatchesInfoBean[#incr.index]['maritalStatus']}" /></td>
					</tr>
				    <tr class="opened">
						<td class="day_label1">Location :</td>
						<td class="day_value"><s:property value="%{newMatchesInfoBean[#incr.index]['country']}" /></td>
					</tr>
				    <tr class="closed">
						<td class="day_label1">Profile Created by :</td>
						<td class="day_value closed"><span><c:if test="${newMatchesInfoBean[incr.index]['profileFor'] == 'Myself'}">Self</c:if><c:if test="${newMatchesInfoBean[incr.index]['profileFor'] != 'Myself'}"><s:property value="%{newMatchesInfoBean[#incr.index]['profileFor']}" /></c:if></span></td>
					</tr>
				    <tr class="closed">
						<td class="day_label1">Education :</td>
						<td class="day_value closed"><span><s:property value="%{newMatchesInfoBean[#incr.index]['education']}" /></span></td>
					</tr>
			    </tbody>
		   </table>
		   <div class="buttons">
<!-- 			   <div class="vertical">Send Mail</div> -->
			<c:if test="${newMatchesInfoBean[incr.index]['shortlisted'] != true}">
			   <div class="vertical" id="shortlist<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:shortlist('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlist', <s:property value="%{#incr.index}" />)">
			   Shortlist
			   </a>
			   </div>
			</c:if>
			<c:if test="${newMatchesInfoBean[incr.index]['shortlisted'] == true}">
			   <div class="vertical" id="shortlisted<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:shortlisted('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlisted', <s:property value="%{#incr.index}" />)">
			   Shortlisted
			   </a>
			   </div>
			</c:if>
			<div class="vertical" id="shortlist<s:property value="%{#incr.index}" />"  style="display: none;">
			   <a style="color: #fff;" href="javascript:shortlist('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlist', <s:property value="%{#incr.index}" />)">
			   Shortlist
			   </a>
			   </div>
			<div class="vertical" id="shortlisted<s:property value="%{#incr.index}" />" style="display: none;">
			   <a style="color: #fff;" href="javascript:shortlisted('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlisted', <s:property value="%{#incr.index}" />)">
			   Shortlisted
			   </a>
			   </div>
			<c:if test="${newMatchesInfoBean[incr.index]['sendInterested'] != true}">
			   <div class="vertical" id="sendInterest<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:sendinterest('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterest', <s:property value="%{#incr.index}" />)">
			   Send Interest
			   </a></div>
			</c:if>
			<c:if test="${newMatchesInfoBean[incr.index]['sendInterested'] == true}">
			   <div class="vertical" id="sendInterested<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />',<s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterested', <s:property value="%{#incr.index}" />)">
			   Send Reminder
			   </a></div>
			</c:if>
			<div class="vertical" id="sendInterested<s:property value="%{#incr.index}" />" style="display: none;">
			   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{newMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{newMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{newMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterested', <s:property value="%{#incr.index}" />)">
			   Send Reminder
			   </a></div>			   
		   </div>
	    </div>
	    <div class="clearfix"> </div>
    </div>
	</td>
    </tr>
    </s:iterator>
    </table>
  </div>
   <div class="clearfix"> </div>
</div>
</div>
<div id="profile_shortlisted" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p><span id="profile_name"></span> (<span id="profile_id"></span>) has been shortlisted</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
<div id="remove_profile_shortlisted" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<input type="hidden" id="profileName" />
		<input type="hidden" id="userId" />
		<input type="hidden" id="profileId" />
		<input type="hidden" id="status" />
		<input type="hidden" id="index" />
		<div class="content">
			<p>You have shortlisted <span id="remove_profile_name"></span> (<span id="remove_profile_id"></span>)</p>
		</div>
		<div class="alerty-action">
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
			<a class="btn-ok" id="btnUpload" onclick="deleteShortlisted();">Delete</a>
		</div>
	</div>
	</div>
<div id="send_interested_profile" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your interest has been sent to <span id="send_interested_profile_name"></span> (<span id="send_interested_profile_id"></span>)</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
<div id="send_reminder_profile" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Please wait for 24 hours from the time you sent your interest before sending a reminder.</p>
			<p>Get more details. <a href="inbox">Check your inbox.</a></p>
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