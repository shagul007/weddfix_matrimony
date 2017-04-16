<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="inner-wrapper1">
	<div id="innerdiv">
		<div align="center"
			style="width: 85%; border-radius: 5px; height: 320px; float: left; margin: 20px 70px 20px 70px;">
			<div class="alert-new">
				<%-- <p>
					Exception Name:
					<s:property value="exception" />
				</p>
				<p>
					Exception Details:
					<s:property value="exceptionStack" />
				</p> --%>
				<div
					style="text-align: center; font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; margin: 100px 0 0 50px;">${Errormessage}</div>
				<div style="margin: 30px 0 0 50px;">
					<h1>404</h1>
					<h2>Requested page not available.....</h2>
				</div>
				<div style="margin: 30px 0 0 50px;">
					<a href="home.action"><img src="images/goback.jpg" alt="goback"
						border="0" /></a>
				</div>
			</div>
		</div>
	</div>
</div>
