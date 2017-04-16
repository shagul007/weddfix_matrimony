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
</head>
<body>
  <!-- Start slider -->
  <%if (ActionContext.getContext().getName() != null) {
	  if (ActionContext.getContext().getName().equalsIgnoreCase("home")) {%>
  <section id="slider">
    <div class="main-slider">
      <div class="single-slide">
        <img src="<%=request.getContextPath()%>/images/temp.jpg" alt="img">
        <div class="slide-content">
          <div class="container">
            <div class="row">
              <div class="col-md-6 col-sm-6">
                <div class="slide-article">
                  <h1 class="wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.5s">Wedding Collection</h1>
                  <p class="wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.75s">Wedding designs</p>
                  <a class="read-more-btn wow fadeInUp" data-wow-duration="1s" data-wow-delay="1s" href="#">Read More</a>
                </div>
              </div>
              <div class="col-md-6 col-sm-6">
                <div class="slider-img wow fadeInUp">
                  <img src="<%=request.getContextPath()%>/assets/images/person1.png" alt="business man">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="single-slide">
        <img src="<%=request.getContextPath()%>/images/temp1.jpg" alt="img">
        <div class="slide-content">
          <div class="container">
            <div class="row">
              <div class="col-md-6 col-sm-6">
                <div class="slide-article">
                  <h1 class="wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.5s">Wedding Collection</h1>
                  <p class="wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.75s">Wedding designs</p>
                  <a class="read-more-btn wow fadeInUp" data-wow-duration="1s" data-wow-delay="1s" href="#">Read More</a>
                </div>
              </div>
              <div class="col-md-6 col-sm-6">
                <div class="slider-img wow fadeInUp">
                  <img src="<%=request.getContextPath()%>/assets/images/person2.png" alt="business man">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>          
    </div>
  </section>
  <%}}%>
  <!-- End slider --> 

  <!-- Start Image table -->
  <section id="our-team">
  <c:if test="${weddingHalls[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{weddingHalls[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start weddingHalls images -->
<!--            		 <div id="lightgallery"> -->
              <s:iterator value="weddingHalls" status="incr">
<%-- 	                 <div data-src="<s:url action="ImageAction?imageId=%{weddingHalls[#incr.index]['fileName']}" />" data-sub-html="<h4><s:property value="%{weddingHalls[#incr.index]['name']}" /></h4><p>Rs. <s:property value="%{weddingHalls[#incr.index]['packageFrom']}" /></p>"> --%>
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
	                    <%-- <a href="">
	                        <img width="235" height="155" class="img-responsive" src="<s:url action="ImageAction?imageId=%{weddingHalls[#incr.index]['fileName']}" />">
	                    </a> --%>
	                    <a href="view_info?id=<s:property value="%{weddingHalls[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{weddingHalls[#incr.index]['fileName']}" />" alt="<s:property value="%{weddingHalls[#incr.index]['fileName']}" />" />
						</a>
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{weddingHalls[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{weddingHalls[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
<!-- 	                </div> -->
              </s:iterator>
                <!--  </div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${studios[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{studios[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start studios images -->
              <s:iterator value="studios" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
							<a href="view_info?id=<s:property value="%{studios[#incr.index]['id']}" />">
								<img src="<s:url action="ImageAction?imageId=%{studios[#incr.index]['fileName']}" />" alt="<s:property value="%{studios[#incr.index]['fileName']}" />" />
							</a>
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{studios[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{studios[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${decorations[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{decorations[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start decorations images -->
              <s:iterator value="decorations" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{decorations[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{decorations[#incr.index]['fileName']}" />" alt="<s:property value="%{decorations[#incr.index]['fileName']}" />" />
				 </a>		
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{decorations[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{decorations[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${beautyParlours[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{beautyParlours[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start beautyParlours images -->
              <s:iterator value="beautyParlours" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{beautyParlours[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{beautyParlours[#incr.index]['fileName']}" />" alt="<s:property value="%{beautyParlours[#incr.index]['fileName']}" />" />
				 </a>			
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{beautyParlours[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{beautyParlours[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${jewelShops[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{jewelShops[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start jewelShops images -->
              <s:iterator value="jewelShops" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{jewelShops[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{jewelShops[#incr.index]['fileName']}" />" alt="<s:property value="%{jewelShops[#incr.index]['fileName']}" />" />
				 </a>			
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{jewelShops[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{jewelShops[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${caterings[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{caterings[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start caterings images -->
              <s:iterator value="caterings" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{caterings[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{caterings[#incr.index]['fileName']}" />" alt="<s:property value="%{caterings[#incr.index]['fileName']}" />" />
				 </a>		
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{caterings[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{caterings[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${entertainments[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{entertainments[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start entertainments images -->
              <s:iterator value="entertainments" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{entertainments[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{entertainments[#incr.index]['fileName']}" />" alt="<s:property value="%{entertainments[#incr.index]['fileName']}" />" />
				 </a>
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{entertainments[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{entertainments[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    <c:if test="${textiles[1]['orgName'] != null}">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
			<div class="our-skill">
				<h3><s:property value="%{textiles[1]['orgName']}" /></h3>
				<div class="our-skill-content" style="margin: auto;"></div>
    		</div>
	   </div>
        <div class="col-md-12">
          <div class="our-team-content">
            <div class="row">
              <!-- Start textiles images -->
              <s:iterator value="textiles" status="incr">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="single-team-member">
                 <div class="team-member-img">
                 <a href="view_info?id=<s:property value="%{textiles[#incr.index]['id']}" />">
							<img height="155" width="235"
								src="<s:url action="ImageAction?imageId=%{textiles[#incr.index]['fileName']}" />" alt="<s:property value="%{textiles[#incr.index]['fileName']}" />" />
				 </a>		
                 </div>
                 <div class="price-header">
					<span class="price-title"><s:property value="%{textiles[#incr.index]['name']}" /></span>
				 <div class="price">
					<sup class="price-up">Rs.</sup>
						<s:property value="%{textiles[#incr.index]['packageFrom']}" />
<%-- 					<span class="price-down">/mo</span> --%>
					</div>
				 </div>
                </div>
              </div>
              </s:iterator>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
  </section>
  <!-- End Image table -->
  
  <!-- Start Testimonial section -->
  <section id="testimonial">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <div class="row">
            <div class="col-md-12">
              <div class="title-area">
                <h2 class="title">Whats Client Say</h2>
                <span class="line"></span>           
              </div>
            </div>
            <div class="col-md-12">
              <!-- Start testimonial slider -->
              <div class="testimonial-slider">
                <!-- Start single slider -->
                <div class="single-slider">
                  <div class="testimonial-img">
                    <img src="<%=request.getContextPath()%>/assets/images/testi1.jpg" alt="testimonial image">
                  </div>
                  <div class="testimonial-content">
                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.</p>
                    <h6>Bernice Neumann, <span>Designer</span></h6>
                  </div>
                </div>
                <!-- Start single slider -->
                <div class="single-slider">
                  <div class="testimonial-img">
                    <img src="<%=request.getContextPath()%>/assets/images/testi3.jpg" alt="testimonial image">
                  </div>
                  <div class="testimonial-content">
                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.</p>
                    <h6>John Dow, <span>CEO</span></h6>
                  </div>
                </div>
                <!-- Start single slider -->
                <div class="single-slider">
                  <div class="testimonial-img">
                    <img src="/matrimony" alt="testimonial image">
                  </div>
                  <div class="testimonial-content">
                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.</p>
                    <h6>Michel, <span>Developer</span></h6>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-6"></div>        
      </div>
    </div>
  </section>
  <!-- End Testimonial section -->

  <!-- Start subscribe us -->
  <section id="subscribe">
    <div class="subscribe-overlay">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="subscribe-area">
              <h2 class="wow fadeInUp">Subscribe Newsletter</h2>
              <div id="subscriberSuccess" style="display: none; text-align: center;"><h3 style="color: #a56a96;">Subscribed successfully.</h3></div>
              <div id="subscriberError" style="display: none; text-align: center;"><h3 style="color: #a56a96;">You have already subscribed.</h3></div>
              <form id="subscriber" name="subscriber" class="subscrib-form wow fadeInUp" data-wow-duration="0.5s" data-wow-delay="0.5s">
              <s:hidden id="validateUser" value="1" />
                <input type="text" id="subscriberEmail" name="subscriberEmail" placeholder="Enter Your E-mail..">
                <input type="button" class="subscribe-button" onclick="return validateSubscriberForm();" value="Submit" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End subscribe us -->

  </body>
</html>