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
	
	function upgrade() {
		window.location.href = "upgrade";
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
                    <li class="active"><a href="<%=request.getContextPath()%>/profile_settings">Account Details</a></li>
                    <li><a href="<%=request.getContextPath()%>/change_password">Change Password</a></li>
                    <li><a href="<%=request.getContextPath()%>/profile_picture">Profile Picture</a></li>
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
			<s:form cssClass="form" action="upgrade" name="form"
				id="form" method="post">
				<s:hidden id="userId" name="userId"/>
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"><s:actionerror /></div>
						<div class="form-title-row">
							<h1>Account Details</h1>
						</div>
						<s:iterator value="myAccountDetails">
						<div class="form-row">
							<label> <span>Plan Name</span> <s:property value="%{planName}" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>Paid Amount</span> Rs. <s:property value="%{amount}" />
							</label>
						</div>
						<div class="form-row">
							<label> <span>Validity</span> <c:if test="${validity > 0 }"><s:property value="%{validity}" /> days left </c:if> <c:if test="${validity <= 0 }">Expired</c:if>
							</label>
						</div>
						<div class="form-row">
							<label> <span>Get SMS Notification</span> <c:if test="${smsAlert == true }">Yes</c:if><c:if test="${smsAlert == false }">No</c:if>
							</label>
						</div>
						<div class="form-row">
							<label> <span>Available Email Credits</span> You have <c:if test="${emailCount > 0 }"><s:property value="%{emailCount}" /></c:if><c:if test="${emailCount <= 0 }">0</c:if> email credits
							</label>
						</div>
						<div class="form-row">
							<label> <span>Available Mobile Credits</span> You have <c:if test="${mobileCount > 0 }"><s:property value="%{mobileCount}" /></c:if><c:if test="${mobileCount <= 0 }">0</c:if> mobile credits
							</label>
						</div>
						</s:iterator>
						<div class="form-row">
							<button type="button" onclick="upgrade();">Upgrade Account</button>
						</div>
					</div>
				</div>
			</s:form>
		</div>
	</section>
        </div>
    </div>
</div>
</html>