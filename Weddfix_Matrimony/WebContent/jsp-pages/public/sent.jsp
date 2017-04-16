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
                    <li><a href="inbox">Inbox</a></li>
                    <li><a href="accepted">Accepted</a></li>
                    <li><a href="not_interested">Not Interested</a></li>
                    <li class="active"><a href="sent">Sent</a></li>
                </ul>
            </nav>
        </div>
        <div class="col-sm-10">
        <div id="myTabContent" class="tab-content">
				  <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
				   <c:if test="${sentInfoBean.isEmpty()}">
					  	<p>Currently there is no communication in this folder.</p>
					  </c:if>
				  <table id="pagination">
					<c:if test="${!sentInfoBean.isEmpty() }">
						<thead> 
						<tr style="display: none;"><th></th></tr>
						</thead>
					</c:if>
        			<s:iterator value="sentInfoBean" status="incr">
        			<tr><td>
	                <div class="jobs-item with-thumb">
	                   <div class="thumb_top">
				        <div class="thumb"><a href="javascript:update(<s:property value="%{sentInfoBean[#incr.index]['userId']}" />, <s:property value="%{sentInfoBean[#incr.index]['profileId']}" />)">
				        <c:choose>
						<c:when test="${sentInfoBean[incr.index]['fileName'] != null}">
						<c:if test="${sentInfoBean[incr.index]['showProfilePicture'] == 'false'}">
					<c:if test="${sentInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Male_Pic_Producted.png" />
						</div>
					 </c:if>
					 <c:if test="${sentInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/Female_Pic_Producted.png" />
						</div>
					 </c:if>
					 </c:if>
					 <c:if test="${sentInfoBean[incr.index]['showProfilePicture'] == 'true'}">
								<div class="profile_left-top">
							    	<img width="200" height="150" src="<s:url action="ImageAction?imageId=%{sentInfoBean[#incr.index]['fileName']}" />" class="img-responsive" alt=""/>
							    </div>
							    </c:if>
						</c:when>
						<c:otherwise>
					 <c:if test="${sentInfoBean[incr.index]['gender'] == 'Male'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Male_Pic.png" />
						</div>
					 </c:if>
					 <c:if test="${sentInfoBean[incr.index]['gender'] == 'Female'}">
						<div class="profile_left-top">
							<img width="200" height="150" src="<%=request.getContextPath()%>/images/No_Female_Pic.png" />
						</div>
					 </c:if>
			</c:otherwise>
			</c:choose>
				        </a></div>
					    <div class="jobs_right">
							<h6 class="title"><a href="javascript:update(<s:property value="%{sentInfoBean[#incr.index]['userId']}" />, <s:property value="%{sentInfoBean[#incr.index]['profileId']}" />)"><s:property value="%{sentInfoBean[#incr.index]['fullName']}" /> (<s:property value="%{sentInfoBean[#incr.index]['profileId']}" />)</a></h6>
							<p class="description"><s:property value="%{sentInfoBean[#incr.index]['age']}" /> years, <s:property value="%{sentInfoBean[#incr.index]['height']}" /> | <span class="m_1">Reliogion</span> : <s:property value="%{sentInfoBean[#incr.index]['religion']}" /> | <span class="m_1">Education</span> : <s:property value="%{sentInfoBean[#incr.index]['education']}" /> | <span class="m_1">Occupation</span> : <s:property value="%{sentInfoBean[#incr.index]['occupation']}" /><br><a href="javascript:update(<s:property value="%{sentInfoBean[#incr.index]['userId']}" />, <s:property value="%{sentInfoBean[#incr.index]['profileId']}" />)" class="read-more">view full profile</a></p>
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
  </body>
</html>