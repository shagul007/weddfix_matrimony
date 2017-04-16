<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.apache.http.client.ClientProtocolException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
var role = '<%=session.getAttribute("role") %>';
if(role == 'null') {
	window.location.href = "login";
}

if(role != 'ADMIN') {
	window.location.href = "home";
}
</script>
<style type="text/css">
a {
	color: #000;
}
</style>
</head>
<body>
	<section id="service">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="box-content">
					<div class="row">
						<div class="col-md-3 col-sm-6 col-xs-12">
							<div class="single-team-member">
								<div class="team-member-img">
									<a href="client_home_panel"> <img width="235"
										height="155" alt="No Image"
										src="<%=request.getContextPath()%>/images/temp.jpg"></a>
								</div>
								<div class="team-member-link">
									<a href="client_home_panel">Client Info Register</a>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12">
							<div class="single-team-member">
								<div class="team-member-img">
									<a href="client_info"> <img width="235" height="155"
										alt="No Image"
										src="<%=request.getContextPath()%>/images/temp.jpg"></a>
								</div>
								<div class="team-member-link">
									<a href="client_info">Client Info Update</a>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12">
							<div class="single-team-member">
								<div class="team-member-img">
									<a href="user_role"> <img width="235" height="155"
										alt="No Image"
										src="<%=request.getContextPath()%>/images/temp.jpg"></a>
								</div>
								<div class="team-member-link">
									<a href="user_role">User Role Setting</a>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12">
							<div class="single-team-member">
								<div class="team-member-img">
									<a href="photo_gallery"> <img width="235" height="155"
										alt="No Image"
										src="<%=request.getContextPath()%>/images/temp.jpg"></a>
								</div>
								<div class="team-member-link">
									<a href="photo_gallery">Manage Photo Gallery</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
</body>
</html>