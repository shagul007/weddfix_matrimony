<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	function validateSubscriberForm() {
		validateSubscriberFileds([ 'subscriberEmail' ]);
	
	}

	function validateForm() {
		validateFileds([ 'name', 'email', 'subject', 'commentTextarea' ]);

	}
	
	function validateFileds(elementIds) {
		var count = 0;
		var length = elementIds.length;
		var flag = false;
		$.each( elementIds, function( index, id ){
			var elementValue = document.forms["comment"][id].value;
			
			if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'email') {
				if(elementValue == null || elementValue == "" || elementValue == "-1"){
					$('#' + id).addClass('error');
					if($("#errorValue").val() == undefined){
						$('#error').append(
						'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill all mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
					}
				} else {
					count = count + 1;
					$('#' + id).removeClass('error');
				}
			
				if(id == 'email' && $("#emailMessageValue").val() == undefined && elementValue != null && elementValue != ""){
					var atpos = elementValue.indexOf("@");
					var dotpos = elementValue.lastIndexOf(".");
					if (atpos < 1 || dotpos < atpos + 2
							|| dotpos + 2 > elementValue.length) {
						count = count - 1;
						$('#' +id).after(
						'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
					} else {
						$('#emailMessage').remove();
						$('#emailMessageValue').remove();
					}
				} else if(id == 'email' && elementValue != null && elementValue != "") {
					var atpos = elementValue.indexOf("@");
					var dotpos = elementValue.lastIndexOf(".");
					if (atpos < 1 || dotpos < atpos + 2
							|| dotpos + 2 > elementValue.length) {
						count = count - 1;
						if($("#emailMessageValue").val() == undefined){
						$('#' +id).after(
						'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
						}
					} else {
						$('#emailMessage').remove();
						$('#emailMessageValue').remove();
					}
				}
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
			if(length == count){
				$('#error').hide();
				flag = true;
			}
		});
		if(flag){
			submitCommentForm();
		}
		
	}
	
	function submitCommentForm() {
		var form = $("#comment").serialize();
		$.ajax({
			data : form,
			url : "comment_success.action",
			type : "post",
			dataType : "json",
			success : function(data) {
				$("#comment").hide();
				$("#commentSuccess").show();
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				$("#comment").show();
				$("#commentSuccess").hide();
			}
		});
	}
</script>
</head>
<body>
  <!-- Start contact section  -->
  <section id="contact">
     <div class="container">
       <div class="row">
         <div class="col-md-12">
           <div class="title-area">
              <h2 class="title">Have any Questions?</h2>
              <span class="line"></span>
            </div>
         </div>
         <div class="col-md-12">
           <div class="cotact-area">
             <div class="row">
               <div class="col-md-4">
                 <div class="contact-area-left">
                   <h4>Contact Info</h4>
                   <address class="single-address">
                     <p>#2/199(1) Sivamani street,</p>
                     <p>Narayanapuram, New natham main road,</p>
                     <p>Madurai, Tamilnadu, India - 625014</p>
                     <p>admin@weddfix.com</p>
                     <p>+91 96989-26990</p>
                   </address> 
                   <div class="footer-right contact-social">
                      <a href="https://facebook.com/weddfix/" target="_blank"><i class="fa fa-facebook"></i></a>
            		  <a href="https://twitter.com/info_weddfix" target="_blank"><i class="fa fa-twitter"></i></a>
                      <!-- <a href="#"><i class="fa fa-google-plus"></i></a>
                      <a href="#"><i class="fa fa-linkedin"></i></a>
                      <a href="#"><i class="fa fa-pinterest"></i></a> -->
                    </div>                
                 </div>
               </div>
               <div class="col-md-8">
                 <div class="contact-area-right">
                   <div id="commentSuccess" style="display: none; text-align: center; margin: 100px 0px 0px;"><h3 style="color: #a56a96;">Thanks for your comment.</h3></div>
                   <form name="comment" id="comment" class="comments-form contact-form">
                    <div id="error"></div>
                    <div class="form-group">                        
                      <input type="text" class="form-control" id="name" name="name" placeholder="Your Name">
                    </div>
                    <div class="form-group">                        
                      <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                    </div>
                     <div class="form-group">                        
                      <input type="text" class="form-control" id="subject" name="subject" placeholder="Subject">
                    </div>
                    <div class="form-group">                        
                      <textarea placeholder="Comment" rows="3" id="commentTextarea" name="comment" class="form-control"></textarea>
                    </div>
                    <input type="button" class="comment-btn" onclick="return validateForm();" value="Submit Comment" />
                  </form>
                 </div>
               </div>
             </div>
           </div>
         </div>
       </div>
     </div>
  </section>
  <!-- End contact section  -->

  <!-- Start google map -->
  <section id="google-map">
    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3929.5774925077285!2d78.1355013140476!3d9.969066492872381!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3b00c6746c2b5ef3%3A0x80338087e3b7af58!2sNatham+Rd%2C+Madurai%2C+Tamil+Nadu+625014!5e0!3m2!1sen!2sin!4v1476119107052" width="100%" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
  </section>
  <!-- End google map -->

  <!-- Start subscribe us -->
  <section id="subscribe">
    <div class="subscribe-overlay">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="subscribe-area newsletter">
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
</body>
</html>