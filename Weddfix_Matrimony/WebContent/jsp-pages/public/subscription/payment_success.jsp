<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.weddfix.web.implementation.CommonMasterDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%if(session.getAttribute("txnid") != null) {
	
	String txnid= session.getAttribute("txnid").toString();
	Long myPlanId= Long.parseLong(session.getAttribute("myPlanId").toString());
	Long userId= Long.parseLong(session.getAttribute("userId").toString());
	Long profileId= Long.parseLong(session.getAttribute("loginProfileId").toString());
	String acceptedPromoCode= (String) session.getAttribute("acceptPromoCode");

	CommonMasterDaoImpl commonMasterDaoImpl = new CommonMasterDaoImpl();
	commonMasterDaoImpl.updateAccountDetails(txnid, myPlanId, userId, acceptedPromoCode, profileId);
}
%>
<<html>
<head>
<script type="text/javascript">
var role = '<%=session.getAttribute("role") %>';
var txnid = '<%=session.getAttribute("txnid") %>';
if(role == 'null') {
	window.location.href = "login";
}

if(txnid == 'null') {
	window.location.href = "upgrade";
}
</script>
</head>
<body>

<div align="center" style="height: 380px;">
	<div style="margin-top: 100px;">
		<div style="margin-bottom: 10px; color: green; font-size: 20px;"><strong>Congratulations! Your account has been upgraded successfully.</strong></div>
		<div style="margin-bottom: 10px; color: #000;">Transaction id: <%=session.getAttribute("txnid") %></div>
		<div>
			<a href="profile_settings.action">Check your account details</a>
		</div>
	</div>
</div>
<%
session.setAttribute("myPlanId", null);
session.setAttribute("txnid", null);
%>

</body>
</html>