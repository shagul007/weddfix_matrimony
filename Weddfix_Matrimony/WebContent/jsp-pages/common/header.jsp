<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Marital Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="keywords" content="cmyk, rgb, cmyk rgb, converter, rgb colors, color profile, cmyk converter, imagemagick cmyk convert"/>
<head>

<!-- Favicon -->
    <link rel="shortcut icon" type="image/icon" href="/matrimony/images/favicon.png"/>
	<!-- Calendar -->
    <link href="/matrimony/css/calendar.css" rel="stylesheet"> 

    <link href="/matrimony/css/common.css" rel="stylesheet">
    
    <link href="/matrimony/css/jquery.dataTables.min.css" rel="stylesheet">
    
    <link href="/matrimony/css/flexslider.css" rel='stylesheet' type='text/css' />
    
    <link rel="stylesheet" type="text/css" media="print,screen,embossed,all" href="/matrimony/resources/css/jqgrid/ui.jqgrid.css" />
  
  	<link rel="stylesheet" type="text/css" media="print,screen,embossed,all" href="/matrimony/resources/css/jquery/ui-lightness/jquery-ui-1.8.14.custom.css" />
    
    <!-- Fancybox css -->
	<!--     <link href="/matrimony/css/lightgallery.css" rel="stylesheet"> -->
 
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="/matrimony/js/jquery-1.9.1.js"></script>  
  <script src="/matrimony/js/jquery-ui.js"></script>
  <script type="text/javascript" src="/matrimony/resources/js/jquery/jquery-1.7.1.min.js" charset="utf-8"></script> 
  <script type="text/javascript" src="/matrimony/resources/js/jquery/jquery-ui-1.8.6.custom.min.js" charset="utf-8"></script>
  <script type="text/javascript" src="/matrimony/resources/js/jquery/jquery-ui-1.8.14.custom.min.js" charset="utf-8"></script> 
  <script type="text/javascript" src="/matrimony/resources/js/jqgrid/grid.locale-en.js" charset="utf-8"></script>
  <script type="text/javascript" src="/matrimony/resources/js/jqgrid/jquery.jqGrid.min.js" charset="utf-8"></script>
  <script src="/matrimony/js/bootstrap-3.2.0.min.js"></script>
  <script src="/matrimony/js/validation.js"></script>
  <script src="/matrimony/js/common.js"></script>
  <script type="text/javascript" src="/matrimony/js/jquery.flexisel.js"></script>
  <script src="/matrimony/js/jquery.flexslider.js"></script>
  <!-- Custom js -->
  <script src="/matrimony/js/jquery.dataTables.min.js"></script>
  <script src="/matrimony/js/md5.js"></script>
<!-- Custom Theme files -->
<link href="/matrimony/css/bootstrap-3.2.0.min.css" rel='stylesheet' type='text/css' />
<link href="/matrimony/css/form.style.css" rel='stylesheet' type='text/css' />
<link href="/matrimony/css/style.css" rel='stylesheet' type='text/css' />
<link href='//fonts.googleapis.com/css?family=Oswald:300,400,700' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Ubuntu:300,400,500,700' rel='stylesheet' type='text/css'>
<!----font-Awesome----->
<link href="/matrimony/css/font-awesome.css" rel="stylesheet"> 

<style type="text/css">
/**
 * Override feedback icon position
 * See http://formvalidation.io/examples/adjusting-feedback-icon-position/
 */
#form .form-control-feedback {
    /* top: 0;
    right: -30px; */
    right: 122px;
    top: 110px;
}
</style>
<!----font-Awesome----->
<script>
$(document).ready(function(){
    $(".dropdown").hover(            
        function() {
            $('.dropdown-menu', this).stop( true, true ).slideDown("fast");
            $(this).toggleClass('open');        
        },
        function() {
            $('.dropdown-menu', this).stop( true, true ).slideUp("fast");
            $(this).toggleClass('open');       
        }
    );
});
</script>
<script>
// Can also be used with $(document).ready()
$(window).load(function() {
  $('.flexslider').flexslider({
    animation: "slide",
    controlNav: "thumbnails"
  });
});
</script> 
 <script type="text/javascript">
		 $(window).load(function() {
			$("#flexiselDemo3").flexisel({
				visibleItems: 6,
				animationSpeed: 1000,
				autoPlay:false,
				autoPlaySpeed: 3000,    		
				pauseOnHover: true,
				enableResponsiveBreakpoints: true,
		    	responsiveBreakpoints: { 
		    		portrait: { 
		    			changePoint:480,
		    			visibleItems: 1
		    		}, 
		    		landscape: { 
		    			changePoint:640,
		    			visibleItems: 2
		    		},
		    		tablet: { 
		    			changePoint:768,
		    			visibleItems: 3
		    		}
		    	}
		    });
		    
		});
	   </script>
</head>
<body>
 <!-- header bottom -->
    <div class="header-bottom">
      <div class="container">
        <div class="row">
          <div class="col-md-6 col-sm-6 col-xs-6">
            <div class="header-contact">
              <ul>
                <li>
                  <div class="phone">
                    <i class="fa fa-phone"></i>
                    <a style="color: #531844;">+91 96989-26990</a>
                  </div>
                </li>
                <li>
                  <div class="mail">
                    <i class="fa fa-envelope"></i>
                    <a href="mailto:admin@weddfix.com" style="color: #531844;">admin@weddfix.com</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <c:choose>
			<c:when test="${userName != null}">
			<c:if test="${verifyedMobileNumber == false }">
			<script type="text/javascript">
			var pathname = window.location.pathname.substr(window.location.pathname.lastIndexOf('/') + 1);
			if(pathname != 'verify_mobile') {
				window.location.href = "verify_mobile";
			}
			</script>
			</c:if>
				<div class="col-md-6 col-sm-6 col-xs-6">
	            <div class="header-login">
	              <a class="login modal-form" href="profile_settings">Profile Settings</a>
				<a class="login modal-form" style="cursor: pointer;" href="cart"><i class="glyphicon glyphicon-shopping-cart"></i>
				<c:if test="${myPlanId != null }">
              		<span class="itemCount">1</span>
              	</c:if>
              	</a>
	              <a class="signup modal-form" href="logout">Logout</a>
	            </div>
	          	</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-6 col-sm-6 col-xs-6">
	            <div class="header-login">
	              <a class="login modal-form" href="login">Login</a>
				  <a class="signup modal-form" href="register">Sign Up</a>
	            </div>
	          	</div>
			</c:otherwise>
		  </c:choose>
        </div>
      </div>
    </div>
  </header>
  <!-- End header -->
<!-- ============================  Navigation Start =========================== -->
 <div class="navbar navbar-inverse-blue navbar">
    <!--<div class="navbar navbar-inverse-blue navbar-fixed-top">-->
      <div class="navbar-inner">
        <div class="container">
           <c:choose>
			<c:when test="${userName == null}">
           		<a class="brand" href="home"><img height="70" width="90" src="/matrimony/images/weddfix_logo.png" alt="logo"></a>
			</c:when>
			<c:otherwise>
				<a class="brand" href="my_home"><img height="70" width="90" src="/matrimony/images/weddfix_logo.png" alt="logo"></a>
			</c:otherwise>
			</c:choose>
           <div class="pull-right">
          	<nav class="navbar nav_bottom" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
		  <div class="navbar-header nav_2">
		      <button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse" data-target="#bs-megadropdown-tabs">Menu
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="#"></a>
		   </div> 
		   <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
		            <c:choose>
			<c:when test="${userName == null}">
		        <ul class="nav navbar-nav nav_1">
		            <li><a href="home">Home</a></li>
		            <li><a href="contact">About</a></li>
		            <li class="last"><a href="contact">Contact Us</a></li>
		        </ul>
			</c:when>
			<c:otherwise>
			 <ul class="nav navbar-nav nav_1">
			 <c:if test="${role == 'ADMIN' }">
			    <li><a href="admin_home">Admin Home</a></li>
			 </c:if>
				<li><a href="my_home">My Home</a></li>
				<li class="dropdown">
		              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Matches<span class="caret"></span></a>
		              <ul class="dropdown-menu" role="menu">
		                <li><a href="new_matches">New Matches</a></li>
		                <li><a href="viewed_profiles">Who Viewed my Profile</a></li>
		                <li><a href="viewed_not_contacted">Viewed &amp; not Contacted</a></li>
		                <!-- <li><a href="members.html">Premium Members</a></li> -->
		                <li><a href="shortlisted_profiles">Shortlisted Profiles</a></li>
		              </ul>
		            </li>
					<li class="dropdown">
		              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Search<span class="caret"></span></a>
		              <ul class="dropdown-menu" role="menu">
		                <li><a href="search">Regular Search</a></li>
		                <li><a href="recently_viewed_profiles">Recently Viewed Profiles</a></li>
<!-- 		                <li><a href="search-id.html">Search By Profile ID</a></li> -->
		                <li><a href="faq">Faq</a></li>
<!-- 		                <li><a href="shortcodes.html">Shortcodes</a></li> -->
		              </ul>
		            </li>
		            <li class="dropdown">
		              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Messages<span class="caret"></span></a>
		              <ul class="dropdown-menu" role="menu">
		                <li><a href="inbox">Inbox</a></li>
		                <li><a href="accepted">Accepted</a></li>
		                <li><a href="not_interested">Not Interested</a></li>
		                <li><a href="sent">Sent</a></li>
		              </ul>
		            </li>
		            <li><a href="upgrade">Upgrade</a></li>
<!-- 		            <li><a href="contact">About</a></li> -->
		            <li class="last"><a href="contact">Contact Us</a></li>
		        </ul>
			</c:otherwise>
		  </c:choose>
		     </div><!-- /.navbar-collapse -->
		    </nav>
		   </div> <!-- end pull-right -->
          <div class="clearfix"> </div>
        </div> <!-- end container -->
      </div> <!-- end navbar-inner -->
    </div> <!-- end navbar-inverse-blue -->
<!-- ============================  Navigation End ============================ -->
