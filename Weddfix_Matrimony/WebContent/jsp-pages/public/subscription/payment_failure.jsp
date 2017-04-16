<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center" style="height: 380px;">
	<div style="margin-top: 100px;">
		<div style="margin-bottom: 10px; color: darkred; font-size: 20px;"><strong>Transaction Failed.</strong></div>
		<div style="margin-bottom: 10px; color: #000;">Transaction id: <%=session.getAttribute("txnid") %></div>
		<div style="margin-bottom: 10px; color: #000;">Try again later.</div>
		<div>
			<a href="upgrade.action"><img src="images/goback.jpg"
				alt="goback" border="0" /></a>
		</div>
	</div>
</div>
