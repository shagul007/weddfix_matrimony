<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
	function validateSubscriberForm() {
		validateSubscriberFileds([ 'subscriberEmail' ]);
	
	}
	
	function update(id) {
		$.ajax({
			data : {
				clientInfoId : id
			},
			url : "client_info_session.action",
			dataType : "json",
			success : function(data) {
				window.location.href = "view_info";
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
</script>
<style type="text/css">
@media only screen and (min-width:768px) and (max-width: 2500px) {
.weddfix_text {
	color: #531844;
    margin-top: 15px;
    text-align: center;
}
.text1 {
	color: #531844; 
	margin-top: 100px; 
	width: 250px; 
	margin-left: 50px; 
	float: left;
}
.text2 {
	color: #531844; 
	margin-top: 100px; 
	width: 250px; 
	margin-left: 80px; 
	float: left;
	text-align: right;
}
.text3 {
	color: #531844; 
	width: 450px; 
	margin-left: 50px; 
	float: left;
	text-align: right;
}
.service_text1 {
	color: #531844; 
	margin-left: 25px; 
	text-decoration: underline;
}
.service_text2 {
	color: #531844; 
	margin-left: 25px;
}
.service_text_info {
	color: #A0522D;
    font-weight: bolder;
    margin-left: 25px;
    margin-top: 90px;
}
.sub_text {	
	color: #531844; 
	text-align: center;
}
}
@media only screen and (min-width:0px) and (max-width: 767px) {
.weddfix_text {
	color: #531844;
    font-size: 30px;
    margin-top: 15px;
    text-align: center;
}
.text1 {
	color: #531844;
	width: 250px; 
	float: left; 
	font-size: 15px;
}
.text2 {
	color: #531844; 
	width: 250px; 
	float: left; 
	font-size: 15px;
}
.text3 {
	color: #531844; 
	float: left; 
	font-size: 15px;
}
.sub_text {	
	color: #531844; 
	font-size: 14px;
	text-align: center;
}
.service_text1 {
	color: #531844; 
	margin-left: 25px; 
	text-decoration: underline;
	font-size: 18px;
}
.service_text2 {
	color: #531844; 
	margin-left: 25px;
	font-size: 12px;
}
.service_text_info {
	color: #A0522D;
    font-size: 9px;
    font-weight: bolder;
    margin-left: 25px;
}
}
@media only screen and (min-width:320px) and (max-width: 359px) {
.banner, .bg, .service_bg {
	min-height: 180px;
}
}
@media only screen and (min-width:360px) and (max-width: 767px) {
.banner, .bg, .service_bg {
	min-height: 203px;
}
}
@media only screen and (min-width:768px) and (max-width: 799px) {
.banner, .bg, .service_bg {
	min-height: 432px;
}
.bg_left {
	width: 198px;
}
}
@media only screen and (min-width:800px) and (max-width: 979px) {
.banner, .bg, .service_bg {
	min-height: 450px;
}
.service_bg_box {
    padding-top: 25px;
}
.bg_left {
	width: 198px;
}
}
@media only screen and (min-width:980px) and (max-width: 1279px) {
.banner, .bg, .service_bg {
	min-height: 550px;
}
.service_bg_box {
	margin-left: 125px;
    padding-top: 25px;
    width: 900px;
}
.text3 {
	margin-top: 100px !important;
}
.bg_left {
	width: 230px;
}
}
@media only screen and (min-width:1280px) and (max-width: 1919px) {
.banner, .bg, .service_bg {
	min-height: 660px;
}
.service_bg_box {
	margin-left: 125px;
    padding-top: 25px;
    width: 900px;
}
.text3 {
	margin-top: 100px !important;
}
}
@media only screen and (min-width:1920px) and (max-width: 2500px) {
.banner, .bg, .service_bg {
	min-height: 940px;
}
.service_bg_box {
	margin-left: 125px;
    padding-top: 135px;
    width: 900px;
}
.service_text1 {
	font-size: 45px;
	margin-bottom: 30px;
}
.service_text2 {
	font-size: 30px;
}
.text3 {
	margin-top: 100px !important;
}  
}
</style>
</head>
<body>
 <div class="banner">
  <div class="container">
  		<h1 class="weddfix_text">Welcome to Weddfix</h1>
       	<div class="heart-divider">
			<span class="grey-line"></span>
			<i class="fa fa-heart pink-heart"></i>
			<i class="fa fa-heart grey-heart"></i>
			<span class="grey-line"></span>
        </div>
        <%-- <ul id="flexiselDemo3">
	      <li><div class="col_1"><a href="register">
			<img src="images/1.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Him</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
		  <li><div class="col_1"><a href="view_profile.html">
			<img src="images/2.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Her</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
		  <li><div class="col_1"><a href="view_profile.html">
			<img src="images/3.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Him</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
		  <li><div class="col_1"><a href="view_profile.html">
			<img src="images/4.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Her</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
		  <li><div class="col_1"><a href="view_profile.html">
			<img src="images/5.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Him</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
		  <li><div class="col_1"><a href="view_profile.html">
			<img src="images/6.jpg" alt="" class="hover-animation image-zoom-in img-responsive"/>
             <div class="layer m_1 hidden-link hover-animation delay1 fade-in">
                <div class="center-middle">About Her</div>
             </div>
             <h3><span class="m_3">Profile ID : MI-387412</span><br>28, Christian, Australia<br>Corporate</h3></a></div>
          </li>
	    </ul> --%>
    <div class="banner_info">
      <h3 style="color: #531844;">Make your perfect match</h3>
      <a href="register" class="hvr-shutter-out-horizontal">Create your Profile</a>
    </div>
    <div class="heart-divider" style="margin: 0px;">
			<span class="grey-line"></span>
			<i class="fa fa-heart pink-heart"></i>
			<i class="fa fa-heart grey-heart"></i>
			<span class="grey-line"></span>
        </div>
     <h3 class="text1">No too much fee</h3>
     <h3 class="text2">Choose yourself</h3>
     <h3 class="text3">1st three months free registration</h3>
          </div>
</div> 
   <div class="bg">
		<div class="container"> 
			<h3 style="color: #531844;">Mobile Weddfix</h3>
			<h2 class="sub_text">The next generation of matchmaking is here!</h2>
			<div class="heart-divider">
				<span class="grey-line"></span>
				<i class="fa fa-heart pink-heart"></i>
				<i class="fa fa-heart grey-heart"></i>
				<span class="grey-line"></span>
            </div>
            <div class="col-sm-3"><!-- <div class="bg_left"><img src="<%=request.getContextPath()%>/images/mobiloud.png" alt=""/></div> --></div>
            <div class="col-sm-3">
            	<div class="bg_left">
					<img src="<%=request.getContextPath()%>/images/weddfix-android-app-icon.gif" alt=""/>
            	</div>
            </div>
            <div class="col-sm-3">
            	<div class="bg_left">
					<img src="<%=request.getContextPath()%>/images/weddfix-ios-app-icon.gif" alt=""/>
            	</div>
            </div>
            <div class="clearfix"> </div>
            <!-- Start subscribe us -->
  <section id="subscribe">
    <div style="margin: 50px 0px 25px;">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="subscribe-area">
              <h2>Subscribe Newsletter</h2>
              <div id="subscriberSuccess" style="display: none; text-align: center;"><h3 style="color: #a56a96;">Subscribed successfully.</h3></div>
              <div id="subscriberError" style="display: none; text-align: center;"><h3 style="color: #a56a96;">You have already subscribed.</h3></div>
              <form id="subscriber" name="subscriber" class="subscrib-form wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.5s">
              <s:hidden id="validateUser" value="1" />
                <input type="text" id="subscriberEmail" name="subscriberEmail" placeholder="Enter Your E-mail..">
                <input padding: 0px;" type="button" class="subscribe-button" onclick="return validateSubscriberForm();" value="Submit" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End subscribe us -->
     </div>
     </div>
            <!-- <div class="service_bg">
            <div class="service_bg_box">
            <h2 class="service_text1">Upcoming Services</h2>
            <h3 class="service_text2">Wedding Halls</h3>
            <h3 class="service_text2">Wedding Clothes</h3>
            <h3 class="service_text2">Studios</h3>
            <h3 class="service_text2">Decorations</h3>
            <h3 class="service_text2">Beauty Parlours</h3>
            <h3 class="service_text2">Jewel Shops</h3>
            <h3 class="service_text2">Caterings</h3>
            <h3 class="service_text2">Entertainments</h3>
            <h3 class="service_text2">Textiles</h3>
            <h3 class="service_text2">Travels</h3>
            <h3 class="service_text2">Hotels</h3>
            <h3 class="service_text2">Wedding Astrologers</h3>
            <h2 class="service_text_info">Book your wedding services from anywhere to any locations</h2>
            </div>
            <div class="clearfix"> </div>
		
</div> -->
  </body>
</html>