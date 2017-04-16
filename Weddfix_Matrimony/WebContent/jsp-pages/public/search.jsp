<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
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
		validateSearch([ 'fromAge', 'toAge', 'validateFromAgeToAge', 'maritalStatusId' ]);
	}
	
	function validateSearch(elementIds) {
		var count = 0;
		var length = elementIds.length;
		var flag = false;
		$.each( elementIds, function( index, id ){
			var elementValue = document.forms["form"][id].value;
			
			if (elementValue == null || elementValue == "" || elementValue == "-1") {
				if(elementValue == null || elementValue == "" || elementValue == "-1"){
					$('#' + id).addClass( 'error' );
					if($("#errorValue").val() == undefined){
						$('#error').append(
						'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
					}
				} else {
					count = count + 1;
					$('#' + id).removeClass('error');
				}
			
				if(id == 'validateFromAgeToAge'){
					var fromAge = document.forms["form"]["fromAge"].value;
					var toAge = document.forms["form"]["toAge"].value;
					if (parseInt(fromAge) > parseInt(toAge)) {
						count = count - 1;
							$('#toAge').addClass( 'error' );
						if($("#fromAgeMessageValue").val() == undefined){
							$('#toAge').after(
							'<span id="fromAgeMessage" style="width: auto; display: block; color: red;">Sorry, Invalid age range. '+fromAge+' to '+toAge+'.</span><input type="hidden" id="fromAgeMessageValue" value="1">').html;
						}
					} else {
						count = count + 1;
						$('#fromAgeMessage').remove();
						$('#fromAgeMessageValue').remove();
					}
				}
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
//			alert(count+" "+length);			
			if(length == count && $('#validateUser').val() == undefined){
				$('#error').hide();
				flag = true;
			}
		});
		
		if(flag){
//			alert("Success");
			search();
		}
		
	}
	
	$(document).ready(function() {
		$("#country_Id option[value=98]").attr('selected', 'selected');
		$("#countryId").val($("#country_Id").val());
		onLoadStateByCountryId();
	});
	
	function onLoadStateByCountryId() {
		var id = $("#country_Id").val();
		$("#stateId option").remove();
		$("#stateId").append(
				$("<option></option>").attr("value", "").text(
						"--- ANY ---"));
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
	
	function search() {
		$("#searchResult").text("Searching...");
		$.ajax({
			data : {
				fromAge : $("#fromAge").val(),
				toAge : $("#toAge").val(),
				genderId : $("#genderId").val(),
				maritalStatusId : $("#maritalStatusId").val(),
				countryId : $("#countryId").val(),
				stateId : $("#stateId").val(),
			},
			url : "searchProfileSession.action",
			dataType : "json",
			success : function(data) {
				if(data.searchMatchesInfoBean[0] != undefined){
					window.location.href = "search_result";
				}
				else {
					$("#searchResult").text("No Result Found.");
					$("#pagination_wrapper").remove();
				}
					
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				return false;
			}
		});
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
	 <div class="main-content">
		<s:actionerror />
		<s:form style="font: bold 14px sans-serif; float: left;" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Regular Search</h1>
			</div>
			<s:hidden name="countryId" id="countryId" />
			<s:hidden name="validateFromAgeToAge" id="validateFromAgeToAge" />
			<div class="form-row">
			<label> <span>Age</span> 
				<input id="fromAge" name="fromAge" type="text" maxlength="2" onkeyup="checkNumeric(this);" style="width: 50px;" />
				To <input id="toAge" name="toAge" type="text" maxlength="2" onkeyup="checkNumeric(this);" style="width: 50px;" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Gender</span> 
			<select id="genderId" name="genderId">
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
			<label> <span>Country</span> 
			<select id="country_Id" name="country_Id" onchange="setCountryId(); onLoadStateByCountryId();" onkeyup="setCountryId()">
					<option value="">--- ANY ---</option>
				<s:iterator value="countryMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>State</span> 
			<select id="stateId" name="stateId">
					<option value="">--- ANY ---</option>
				<%-- <s:iterator value="stateMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			  <%-- <div class="form-row">
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId">
					<option value="">--- ANY ---</option>
				<s:iterator value="religionMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Mother Tongue</span> 
			<select id="motherTongueId" name="motherTongueId">
					<option value="">--- ANY ---</option>
				<s:iterator value="motherTongueMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div> --%>
			<div class="form-row">
				<button type="button" onclick="return validateForm(); search();">Search</button>
			</div>
			</div>
			</div>
		</s:form>
	</div>
  <div class="container col-sm-offset-4">
   <div class="breadcrumb1">
     <ul>
        <a href="my_home"><i class="fa fa-home home_1"></i></a>
        <span class="divider">&nbsp;|&nbsp;</span>
        <li class="current-page">Search Result</li>
     </ul>
   </div>
   <div class="col-sm-8">
   <span id="searchResult" style="margin-left: 50%; color: sienna;"></span>
   <table id="pagination">
	<c:if test="${!searchMatchesInfoBean.isEmpty() }">
		<thead> 
		<tr style="display: none;"><th></th></tr>
		</thead>
	</c:if>
   <s:iterator value="searchMatchesInfoBean" status="incr">
   <tr><td>
	                <div class="jobs-item with-thumb">
	                   <div class="thumb_top">
				        <div class="thumb"><a href="javascript:update(<s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />)">
				        <c:choose>
						<c:when test="${searchMatchesInfoBean[incr.index]['fileName'] != null}">
						<c:if test="${searchMatchesInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${searchMatchesInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
						</div>
					 </c:if>
					 <c:if test="${searchMatchesInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
						</div>
					 </c:if>
					 </c:if>
					 <c:if test="${searchMatchesInfoBean[incr.index]['showProfilePicture'] == 'true'}">
								<div class="profile_left-top">
							    	<img width="200" height="150" src="<s:url action="ImageAction?imageId=%{searchMatchesInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
							    </div>
							    </c:if>
						</c:when>
						<c:otherwise>
					 <c:if test="${searchMatchesInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" />
						</div>
					 </c:if>
					 <c:if test="${searchMatchesInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" />
						</div>
					 </c:if>
			</c:otherwise>
			</c:choose>
				        </a></div>
					    <div class="jobs_right">
							<h6 class="title"><a href="javascript:update(<s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />)"><s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" /> (<s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />)</a></h6>
							<p class="description"><s:property value="%{searchMatchesInfoBean[#incr.index]['age']}" /> years, <s:property value="%{searchMatchesInfoBean[#incr.index]['height']}" /> | <span class="m_1">Reliogion</span> : <s:property value="%{searchMatchesInfoBean[#incr.index]['religion']}" /> | <span class="m_1">Education</span> : <s:property value="%{searchMatchesInfoBean[#incr.index]['education']}" /> | <span class="m_1">Occupation</span> : <s:property value="%{searchMatchesInfoBean[#incr.index]['occupation']}" /><br><a href="javascript:update(<s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />)" class="read-more">view full profile</a></p>
						</div>
						<div class="clearfix"> </div>
					   </div>
					   <div class="buttons">
<!-- 			   <div class="vertical">Send Mail</div> -->
			<c:if test="${searchMatchesInfoBean[incr.index]['shortlisted'] != true}">
			   <div class="vertical" id="shortlist<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:shortlist('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlist', <s:property value="%{#incr.index}" />)">
			   Shortlist
			   </a>
			   </div>
			</c:if>
			<c:if test="${searchMatchesInfoBean[incr.index]['shortlisted'] == true}">
			   <div class="vertical" id="shortlisted<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:shortlisted('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlisted', <s:property value="%{#incr.index}" />)">
			   Shortlisted
			   </a>
			   </div>
			</c:if>
			<div class="vertical" id="shortlist<s:property value="%{#incr.index}" />"  style="display: none;">
			   <a style="color: #fff;" href="javascript:shortlist('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlist', <s:property value="%{#incr.index}" />)">
			   Shortlist
			   </a>
			   </div>
			<div class="vertical" id="shortlisted<s:property value="%{#incr.index}" />" style="display: none;">
			   <a style="color: #fff;" href="javascript:shortlisted('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'Shortlisted', <s:property value="%{#incr.index}" />)">
			   Shortlisted
			   </a>
			   </div>
			<c:if test="${searchMatchesInfoBean[incr.index]['sendInterested'] != true}">
			   <div class="vertical" id="sendInterest<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:sendinterest('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterest', <s:property value="%{#incr.index}" />)">
			   Send Interest
			   </a></div>
			</c:if>
			<c:if test="${searchMatchesInfoBean[incr.index]['sendInterested'] == true}">
			   <div class="vertical" id="sendInterested<s:property value="%{#incr.index}" />">
			   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />',<s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterested', <s:property value="%{#incr.index}" />)">
			   Send Reminder
			   </a></div>
			</c:if>
			<div class="vertical" id="sendInterested<s:property value="%{#incr.index}" />" style="display: none;">
			   <a style="color: #fff;" href="javascript:sendreminder('<s:property value="%{searchMatchesInfoBean[#incr.index]['fullName']}" />', <s:property value="%{searchMatchesInfoBean[#incr.index]['userId']}" />, <s:property value="%{searchMatchesInfoBean[#incr.index]['profileId']}" />, 'SendInterested', <s:property value="%{#incr.index}" />)">
			   Send Reminder
			   </a></div>			   
		   </div>
					  </div>
					  </td></tr>
					  </s:iterator>
					  </table>
	    <div class="clearfix" style="margin-bottom: 30px;"> </div>
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
