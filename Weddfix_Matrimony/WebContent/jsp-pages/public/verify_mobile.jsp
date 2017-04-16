<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
var role = '<%=session.getAttribute("role") %>';

if(role == 'null') {
	window.location.href = "login";
}

function validateForm() {
	var elementValue = document.forms["form"]["verifyCode"].value;
	if(elementValue == null || elementValue == ""){
		$('#verifyCode').addClass( 'error' );
		if($("#errorValue").val() == undefined){
			$('#error').append(
			'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please enter verification code.</span><input type="hidden" id="errorValue" value="1">').html;
		}
	} else {
		$('#error').hide();
		$('#verifyCode').removeClass('error');
		
		$.ajax({
			data : {
				verifyCode : $("#verifyCode").val()
			},
			url : "verifyCodeAndUpdateMobile.action",
			dataType : "json",
			type: "POST",
	        async: false,
			success : function(data) {
				if(data.verifyMsg == "success") {
					$('#sentCodeMessage').remove();
					$('#sentCode').append(
					'<span id="sentCodeMessage" style="color: green; margin-bottom: 20px; float: left;">Verified successfully.</span>').html;
					window.location.href = "my_home";
				} else {
					$('#sentCodeMessage').remove();
					$('#sentCode').append(
					'<span id="sentCodeMessage" style="color: red; margin-bottom: 20px; float: left;">Invalid verification code.</span>').html;
				}
			
			},
			error : function(xhr, textStatus, errorThrown) {
				
			}
		});
	}
}

function send() {
	var elementValue = document.forms["form"]["mobile"].value;
	if(elementValue == null || elementValue == ""){
		$('#mobile').addClass( 'error' );
		if($("#errorValue").val() == undefined){
			$('#error').append(
			'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please enter mobile number.</span><input type="hidden" id="errorValue" value="1">').html;
		}
	} else {
		$('#error').hide();
		$('#mobile').removeClass('error');
		
	if($("#mobileMessageValue").val() == undefined && elementValue != null && elementValue != ""){
		if (elementValue.length < 10) {
			$('#mobile').after(
			'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
		 } else {
				$('#mobileMessage').remove();
				$('#mobileMessageValue').remove();
				sendCode();
		 }
	 } else if(elementValue != null && elementValue != "") {
		 if (elementValue.length < 10) {
			 if($("#mobileMessageValue").val() == undefined){
				$('#mobile').after(
				'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
			 }
			 } else {
					$('#mobileMessage').remove();
					$('#mobileMessageValue').remove();
					sendCode();
			 }
		}
	}
}

function sendCode() {
	$("#mobileText").hide();
	$("#sendButton").hide();
	$("#reSendLink").hide();
	$("#reCounter").hide();
	$("#counter").show();
	$("#counterMsg").show();
	$("#verifyText").show();
	$("#verifyButton").show();
	$("#reSendButton").show();
	countdown();
	
	$.ajax({
		data : {
			verifyMobile : $("#mobile").val()
		},
		url : "reSendCode.action",
		dataType : "json",
		type: "POST",
        async: false,
		success : function(data) {
			$('#sentCodeMessage').remove();
			$('#sentCode').append(
			'<span id="sentCodeMessage" style="color: green; margin-bottom: 20px; float: left;">Verification code sent successfully.</span>').html;
		
		},
		error : function(xhr, textStatus, errorThrown) {
			
		}
	});
}

function countdown() {
    var seconds = 60;
    function tick() {
        var counter = document.getElementById("counter");
        seconds--;
        counter.innerHTML = "0:" + (seconds < 10 ? "0" : "") + String(seconds);
        if( seconds > 0 ) {
            setTimeout(tick, 1000);
        } else {
        	$("#counterMsg").hide();
        	$("#counter").hide();
        	$("#reCounter").show();
        	$("#reSendLink").show();
        }
    }
    tick();
}
</script>
</head>
<body>
	<section id="form-container">
		<div class="main-content">
			<s:form cssClass="form" action="user_home" name="form"
				id="form" method="post">
				<div class="form-innner-wrraper">
					<div class="form-color-background">
						<div id="error"></div>
						<div id="sentCode"></div>
						<div class="form-title-row">
							<h1>Verify your mobile number</h1>
						</div>
						<div class="form-row" id="mobileText">
							<label> <span>Mobile</span> 
								<input id="mobile" name="mobile" type="text" maxlength="10" onkeyup="checkNumeric(this);" value="<%=session.getAttribute("mobile") %>"/>
							</label>
						</div>
						<div class="form-row" id="verifyText" style="display: none;">
							<label> <span>Code</span> <input id="verifyCode" name="verifyCode"
								type="text" maxlength="6" onkeyup="checkNumeric(this);" />
							</label>
						</div>
						<div class="form-row" id="sendButton">
							<button type="button" onclick="return send();">Send verification code</button>
						</div>
						<div class="form-row" id="verifyButton" style="display: none;">
							<button type="button" onclick="return validateForm();">Verify</button>
						</div>
						<div class="form-row" id="reSendButton" style="display: none;">
						<span style="color: #000; font-weight: normal; float: left;" id="counterMsg">Not received code? wait for</span> <span id="counter" style="margin-left: 5px; color: #000; width: 30px;">1:00</span>
						<span style="color: #000; font-weight: normal;" id="reCounter">Not received code? </span><a href="javascript:send();" id="reSendLink">Re-send</a>
						</div>
					</div>
				</div>
			</s:form>
		</div>
	</section>
</body>
</html>
<c:if test="${verifyedMobileNumber == true }">
			<script type="text/javascript">
				window.location.href = "my_home";
			</script>
</c:if>