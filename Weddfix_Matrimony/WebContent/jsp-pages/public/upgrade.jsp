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

	function upgrade(id) {
		$.ajax({
			data : {
				myPlanId : id
			},
			url : "cart_session.action",
			dataType : "json",
			success : function(data) {
				window.location.href = "cart";
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
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
        <li class="current-page">Upgrade</li>
     </ul>
   </div>
   <s:iterator value="upgradePlanInfoBean" status="incr">
   <div class="col-md-3 pricing-table">
	  <div class="pricing-table-grid">
		<h3><span class="dollar">Rs.</span><s:property value="%{upgradePlanInfoBean[#incr.index]['amount']}" /><br><span class="month1"><s:property value="%{upgradePlanInfoBean[#incr.index]['validity']}" /></span></h3>
		<ul>
			<li><span><s:property value="%{upgradePlanInfoBean[#incr.index]['planName']}" /></span></li>
			<li><a href="#"><i class="fa fa-envelope-o icon_3"></i>E-Mails (<s:property value="%{upgradePlanInfoBean[#incr.index]['emailCount']}" />)</a></li>
			<li><a href="#"><i class="fa fa-phone icon_3"></i>Phone Number (<s:property value="%{upgradePlanInfoBean[#incr.index]['mobileCount']}" />)</a></li>
			<c:if test="${upgradePlanInfoBean[incr.index]['videoCallCount'] > 0}">
			<li><a href="#"><i class="fa fa-video-camera icon_3"></i>Video Call (<s:property value="%{upgradePlanInfoBean[#incr.index]['videoCallCount']}" />)</a></li>
			</c:if>
			<c:if test="${upgradePlanInfoBean[incr.index]['expressInterest'] == true}">
			<li><a href="#"><i class="fa fa-eye icon_3"></i>Express Interest</a></li>
			</c:if>
			<c:if test="${upgradePlanInfoBean[incr.index]['profileHighlight'] == true}">
			<li><a href="#"><i class="fa fa-user icon_3"></i>Profile Highlight</a></li>
			</c:if>
			<c:if test="${upgradePlanInfoBean[incr.index]['viewProfile'] == true}">
			<li><a href="#"><i class="fa fa-smile-o icon_3"></i>View Profile</a></li>
			</c:if>
			<c:if test="${upgradePlanInfoBean[incr.index]['protectPhoto'] == true}">
			<li><a href="#"><i class="fa fa-lock icon_3"></i>Protect Photo</a></li>
			</c:if>
			<c:if test="${upgradePlanInfoBean[incr.index]['getSMSAlert'] == true}">
			<li><a href="#"><i class="fa fa-smile-o icon_3"></i>Get SMS Alert</a></li>
			</c:if>
		</ul>
		<a class="popup-with-zoom-anim order-btn" href="#" onclick="upgrade(<s:property value="%{upgradePlanInfoBean[#incr.index]['id']}" />);">Upgrade</a>
	  </div>
	  </div>
	  </s:iterator>
	  <div class="clearfix"> </div>
    </div>
</div>
  </body>
</html>