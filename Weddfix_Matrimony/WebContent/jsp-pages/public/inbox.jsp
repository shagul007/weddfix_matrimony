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
	
	function update(userId, profileId) {
		$.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId,
			},
			url : "view_mail_session.action",
			dataType : "json",
			success : function(data) {
				window.location.href = "view_profile";
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function accept(profileName, userId, profileId, status, index) {
		$.ajax({
			data : {
				profileUserId : userId,
				userProfileId : profileId,
				statusName : status
			},
			url : "accept.action",
			dataType : "json",
			success : function(data) {
				$("#accept"+index).hide();
				$("#accepted"+index).show();
				$("#notInterest"+index).hide();
				$("#profile_accepted").show();
				$("#profile_name").text(profileName);
				$("#profile_id").text(profileId);
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function notInterest(profileName, userId, profileId, status, index) {
				$("#not_interested_profile").show();
				$("#not_interested_profile_name").text(profileName);
				$("#not_interested_profile_id").text(profileId);
				$("#profileName").val(profileName);
				$("#userId").val(userId);
				$("#profileId").val(profileId);
				$("#status").val(status);
				$("#index").val(index);
	}
	
	function notInterested() {
		var index = $("#index").val();
		$.ajax({
			data : {
				profileUserId : $("#userId").val(),
				userProfileId : $("#profileId").val(),
				statusName : $("#status").val()
			},
			url : "not_interest.action",
			dataType : "json",
			success : function(data) {
				$("#not_interested_profile").hide();
				$("#notInterest"+index).hide();
				$("#accept"+index).hide();
				$("#notInterested"+index).show();
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
	
	function closePopup() {
		$("#profile_accepted").hide();
		$("#not_interested_profile").hide();
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
 <div class="container" style="min-height: 428px;">
 <br />
	<h3>Messages<br />
<%--         <small>A simple, sheek navigation bar style!</small> --%>
    </h3>
    <br />
    
    <div class="row">
        <div class="col-sm-2">
            <nav class="nav-sidebar">
                <ul class="nav">
                    <li class="active"><a href="inbox">Inbox</a></li>
                    <li><a href="accepted">Accepted</a></li>
                    <li><a href="not_interested">Not Interested</a></li>
                    <li><a href="sent">Sent</a></li>
                </ul>
            </nav>
        </div>
        <div class="col-sm-10">
        <div id="myTabContent" class="tab-content">
				  <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
				   <c:if test="${inboxInfoBean.isEmpty()}">
					  	<p>Currently there is no communication in this folder.</p>
					  </c:if>
        			<table id="pagination">
					<c:if test="${!inboxInfoBean.isEmpty() }">
						<thead> 
						<tr style="display: none;"><th></th></tr>
						</thead>
					</c:if>
        			<s:iterator value="inboxInfoBean" status="incr">
        			<tr><td>
	                <div class="jobs-item with-thumb">
	                   <div class="thumb_top">
				        <div class="thumb"><a href="javascript:update(<s:property value="%{inboxInfoBean[#incr.index]['userId']}" />, <s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />)">
				        <c:choose>
						<c:when test="${inboxInfoBean[incr.index]['fileName'] != null}">
						<c:if test="${inboxInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${inboxInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
						</div>
					 </c:if>
					 <c:if test="${inboxInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
						</div>
					 </c:if>
					 </c:if>
					 <c:if test="${inboxInfoBean[incr.index]['showProfilePicture'] == 'true'}">
								<div class="profile_left-top">
							    	<img width="200" height="150" src="<s:url action="ImageAction?imageId=%{inboxInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
							    </div>
							    </c:if>
						</c:when>
						<c:otherwise>
					 <c:if test="${inboxInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" />
						</div>
					 </c:if>
					 <c:if test="${inboxInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" />
						</div>
					 </c:if>
			</c:otherwise>
			</c:choose>
				        </a></div>
					    <div class="jobs_right">
							<h6 class="title"><a href="javascript:update(<s:property value="%{inboxInfoBean[#incr.index]['userId']}" />, <s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />)"><s:property value="%{inboxInfoBean[#incr.index]['fullName']}" /> (<s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />)</a></h6>
							<p class="description"><s:property value="%{inboxInfoBean[#incr.index]['age']}" /> years, <s:property value="%{inboxInfoBean[#incr.index]['height']}" /> | <span class="m_1">Reliogion</span> : <s:property value="%{inboxInfoBean[#incr.index]['religion']}" /> | <span class="m_1">Education</span> : <s:property value="%{inboxInfoBean[#incr.index]['education']}" /> | <span class="m_1">Occupation</span> : <s:property value="%{inboxInfoBean[#incr.index]['occupation']}" /><br><a href="javascript:update(<s:property value="%{inboxInfoBean[#incr.index]['userId']}" />, <s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />)" class="read-more">view full profile</a></p>
						</div>
						 <div class="buttons">
<!-- 			   <div class="vertical">Send Mail</div> -->
			  		<c:if test="${inboxInfoBean[incr.index]['accepted'] != true && inboxInfoBean[incr.index]['notInterested'] != true}">
			   <div class="vertical" id="accept<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:accept('<s:property value="%{inboxInfoBean[#incr.index]['fullName']}" />', <s:property value="%{inboxInfoBean[#incr.index]['userId']}" />, <s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />, 'Accept', <s:property value="%{#incr.index}" />)">
			   Accept
			   </a>
			   </div>
			</c:if>
			<c:if test="${inboxInfoBean[incr.index]['accepted'] == true}">
			   <div class="vertical" id="accepted<s:property value="%{#incr.index}" />">
			   You have accepted
			   </div>
			</c:if>
			<div class="vertical" id="accepted<s:property value="%{#incr.index}" />" style="display: none;">
			   You have accepted
			   </div>
			<c:if test="${inboxInfoBean[incr.index]['notInterested'] != true && inboxInfoBean[incr.index]['accepted'] != true}">
			   <div class="vertical" id="notInterest<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:notInterest('<s:property value="%{inboxInfoBean[#incr.index]['fullName']}" />', <s:property value="%{inboxInfoBean[#incr.index]['userId']}" />, <s:property value="%{inboxInfoBean[#incr.index]['profileId']}" />, 'NotInterest', <s:property value="%{#incr.index}" />)">
			   Not Interest
			   </a></div>
			</c:if>
			<c:if test="${inboxInfoBean[incr.index]['notInterested'] == true}">
			   <div class="vertical" id="notInterested<s:property value="%{#incr.index}" />">
			   You are not interested
			   </div>
			</c:if>
			<div class="vertical" id="notInterested<s:property value="%{#incr.index}" />" style="display: none;">
			   You are not interested
			   </div>			   
		   </div>
					   </div>
					  </div>
					  </td></tr>
					  </s:iterator>
					  </table>
					  </div>
</div>
</div>
</div>
</div>
<div id="profile_accepted" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p><span id="profile_name"></span> (<span id="profile_id"></span>) has been accepted</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
<div id="not_interested_profile" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<input type="hidden" id="profileName" />
		<input type="hidden" id="userId" />
		<input type="hidden" id="profileId" />
		<input type="hidden" id="status" />
		<input type="hidden" id="index" />
		<div class="content">
			<p>You are not interested in <span id="not_interested_profile_name"></span> (<span id="not_interested_profile_id"></span>)</p>
		</div>
		<div class="alerty-action">
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
			<a class="btn-ok" id="btnUpload" onclick="notInterested();">Send</a>
		</div>
	</div>
	</div>
  </body>
</html>