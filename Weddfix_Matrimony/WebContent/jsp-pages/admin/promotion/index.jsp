<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.apache.http.client.ClientProtocolException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
.dataTables_wrapper .dataTables_paginate {
    float: right;
    padding-top: 0.25em;
    text-align: right;
}
</style>
<script type="text/javascript">
var role = '<%=session.getAttribute("role") %>';
if(role == 'null') {
	window.location.href = "login";
}

if(role != 'ADMIN') {
	window.location.href = "login";
}

function loadPopupForm(id, roleId, statusId) {
	$("#savePopup").show();
	$("#success").hide();
	$("#error").hide();
}

function savePromotion() {
	var promoCode = $("#promoCode").val();
	var discount = $("#discount").val();
	var expires = $("#expiresDate").val();
	var promoForAll = $("#promoForAll").is(":checked");

	var promoCodeValue = document.forms["saveForm"]["promoCode"].value;
	var discountValue = document.forms["saveForm"]["discount"].value;
	var expiresValue = document.forms["saveForm"]["expiresDate"].value;
	
	if(promoCodeValue == null || promoCodeValue == ""){
		$('#promoCode').addClass( 'error' );
	} else {
		$('#promoCode').removeClass('error');
	}
	
	if(discountValue == null || discountValue == ""){
		$('#discount').addClass( 'error' );
	} else {
		$('#discount').removeClass('error');
	}
	
	if(expiresValue == null || expiresValue == ""){
		$('#expiresDate').addClass( 'error' );
	} else {
		$('#promoCode').removeClass('error');
		$('#discount').removeClass('error');
		$('#expiresDate').removeClass('error');
		$("#btnUpload").prop('disabled', true);
		$("#uploadingInfo").show();
	
	$.ajax({
		data : {
			promoCodeText : promoCode,
			discountText : discount,
			expiresDate : expires,
			promoForAll : promoForAll
		},
		url : "savePromotion.action",
		dataType : "json",
		success : function(data) {
			$("#error").hide();
			$("#success").show();
			window.location.href = "promotion_details";
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			$("#success").hide();
			$("#error").show();
		}
	});
	}
}

$(document).ready(function(){
    $('#myTable').dataTable(
			{
//					bFilter: false,
//					bInfo: false,
//					"aoColumns": [{"bSortable": false}, null],
//					"aaSorting": [{"bSortable": false}],
//					"bLengthChange": false,
				"aLengthMenu": [5, 10],
		        "iDisplayLength": 5
			}		
	);
    	
});

function deletePromoConfirm(id) {
	$("#id").val(id);
	$("#delete_promotion").show();
}

function deletePromotion() {
	$.ajax({
	data : {
		promoId : $("#id").val()
	},
	url : "deletePromotion.action",
	dataType : "json",
	success : function(data) {
		$("#deleteError").hide();
		$("#deleteSuccess").show();
		window.location.href = "promotion_details";
	},
	error : function(xhr, textStatus, errorThrown) {
		$("#deleteSuccess").hide();
		$("#deleteError").show();
	}
	});
}

function sendPromoCode(promoCode, sentTo) {
	$("#sendPromoCode").val(promoCode);
	$("#send_promotion").show();
	if(sentTo == 'All') {
		$("#sendALL").show();
	} else {
		$("#sendALL").hide();
	}
}

function sentPromotion() {
	var sendPromoCode = $("#sendPromoCode").val();
	var sendPromoForAll = $("#sendPromoForAll").is(":checked");

	var promoEmailIdValue = document.forms["sendForm"]["promoEmailId"].value;
	
	if(promoEmailIdValue == null || promoEmailIdValue == "" && sendPromoForAll == false){
			$('#promoEmailId').addClass( 'error' );
	} else if($("#emailMessageValue").val() == undefined && promoEmailIdValue != null && promoEmailIdValue != "" && sendPromoForAll == false){
		$('#promoEmailId').removeClass('error');
		var atpos = promoEmailIdValue.indexOf("@");
		var dotpos = promoEmailIdValue.lastIndexOf(".");
		if (atpos < 1 || dotpos < atpos + 2
				|| dotpos + 2 > promoEmailIdValue.length) {
			$('#promoEmailId').after(
			'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
		} else {
			$('#emailMessage').remove();
			$('#emailMessageValue').remove();
			
			$('#promoEmailId').removeClass('error');
			$('#sendUpload').attr('onclick','').unbind('click');
			$("#sendingEmailInfo").show();
			
		$.ajax({
		data : {
			promoEmailId : $("#promoEmailId").val(),
			sendPromoCode : sendPromoCode,
			sendPromoForAll : sendPromoForAll
			
		},
		url : "sendPromotion.action",
		dataType : "json",
		success : function(data) {
			$("#sendingEmailInfo").hide();
			$("#sendError").hide();
			$("#sendSuccess").show();
			window.location.href = "promotion_details";
		},
		error : function(xhr, textStatus, errorThrown) {
			$("#sendingEmailInfo").hide();
			$("#sendSuccess").hide();
			$("#sendError").show();
			$('#sendUpload').attr('onclick','sentPromotion();').unbind('click');
		}
		});
		}
	} else if(promoEmailIdValue != null && promoEmailIdValue != "" && sendPromoForAll == false) {
		var atpos = promoEmailIdValue.indexOf("@");
		var dotpos = promoEmailIdValue.lastIndexOf(".");
		if (atpos < 1 || dotpos < atpos + 2
				|| dotpos + 2 > promoEmailIdValue.length) {
			if($("#emailMessageValue").val() == undefined){
			$('#promoEmailId').after(
			'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
			}
		} else {
			$('#emailMessage').remove();
			$('#emailMessageValue').remove();
			
			$('#promoEmailId').removeClass('error');
			$('#sendUpload').attr('onclick','').unbind('click');
			$("#sendingEmailInfo").show();
			
		$.ajax({
		data : {
			promoEmailId : $("#promoEmailId").val(),
			sendPromoCode : sendPromoCode,
			sendPromoForAll : sendPromoForAll
			
		},
		url : "sendPromotion.action",
		type: "POST",
        async: false,
		dataType : "json",
		success : function(data) {
			$("#sendingEmailInfo").hide();
			$("#sendError").hide();
			$("#sendSuccess").show();
			window.location.href = "promotion_details";
		},
		error : function(xhr, textStatus, errorThrown) {
			$("#sendingEmailInfo").hide();
			$("#sendSuccess").hide();
			$("#sendError").show();
			$('#sendUpload').attr('onclick','sentPromotion();').unbind('click');
		}
		});
		}
	} else {
		$('#emailMessage').remove();
		$('#emailMessageValue').remove();
		
		$('#promoEmailId').removeClass('error');
		$('#sendUpload').attr('onclick','').unbind('click');
		$("#sendingEmailInfo").show();
		
		$.ajax({
		data : {
			promoEmailId : $("#promoEmailId").val(),
			sendPromoCode : sendPromoCode,
			sendPromoForAll : sendPromoForAll
			
		},
		url : "sendPromotion.action",
		type: "POST",
        async: false,
		dataType : "json",
		success : function(data) {
			$("#sendingEmailInfo").hide();
			$("#sendError").hide();
			$("#sendSuccess").show();
			window.location.href = "promotion_details";
		},
		error : function(xhr, textStatus, errorThrown) {
			$("#sendingEmailInfo").hide();
			$("#sendSuccess").hide();
			$("#sendError").show();
			$('#sendUpload').attr('onclick','sentPromotion();').unbind('click');
		}
		});
	}
}

function closePopup() {
	$("#savePopup").hide();
	$("#send_promotion").hide();
	$("#delete_promotion").hide();
}
</script>
</head>
<body>
<div style="min-height: 428px;">
	<h2 style="text-align: center; color: #531844;">Promotions</h2>
	<a href="#" onclick="loadPopupForm();" data-toggle="modal" data-target="#popup-form" class="custom-button">Add Promotion</a>
	<table border="1" id="myTable" class="table table-striped" align="center">
	<thead> 
		<tr>
			<th>Promo Code</th>
	        <th>Expires</th>
	        <th>Discount(%)</th>
	        <th>Sent To</th>
	        <th>Sent Date</th>
	        <th>Status</th>
	        <th>Send</th>
	        <th>Delete</th>
		</tr>
		</thead>
		<tbody> 
		<s:iterator value="promotionDetails">
			<tr>
				<td style="text-align: left;"><s:property value="%{promoCode}" /></td>
				<td style="text-align: center;"><s:property value="%{expires}" /></td>
				<td style="text-align: center;"><s:property value="%{discount}" />%</td>
				<td style="text-align: left;"><s:property value="%{email}" /></td>
				<td style="text-align: center;"><s:property value="%{sent}" /></td>
				<td style="text-align: center;"><s:property value="%{status}" /></td>
				<td style="text-align: center;"><a href="#" onclick="sendPromoCode('<s:property value="%{promoCode}" />', '<s:property value="%{sentTo}" />');" data-toggle="modal" data-target="#popup-form">Send</a></td>
				<td style="text-align: center;"><a href="#" onclick="deletePromoConfirm('<s:property value="%{id}" />');" data-toggle="modal" data-target="#popup-form">Delete</a></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	
	<!-- Start popup modal window -->
	<div id="savePopup" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-edit"></i>Add Promotion</h4>
		</div>
		<div class="content">
			<form id="saveForm">
            <div class="form-row">
			<label> <span>Promo Code</span> 
				<input id="promoCode" name="promoCode" type="text" maxlength="100"  onkeyup="checkName(this);" style="width: 200px; margin-left: 54px;"/>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Discount(%)</span> 
				<input id="discount" name="discount" type="text" maxlength="2" onkeyup="checkNumeric(this);" style="width: 30px; margin-left: 53px;" />
			</label>
			</div>
            <div class="form-row">
			<label> <span>Expires</span> 
				<input id="expiresDate" name="expiresDate" type="text" class="futureCalendar" style="width: 100px; margin-left: 90px;" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Promo Code For All</span> 
				<input id="promoForAll" name="promoForAll" type="checkbox">
			</label>
			</div>
          </form>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="savePromotion();">Save</a>
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
              <span id="success" style="display: none; color: #a56a96; float: left;">Saved successfully...</span>
              <span id="error" style="display: none; color: red; float: left;">Error while saving...</span>
		</div>
	</div>
	</div>
	<!-- End -->
	</div>
	<div id="send_promotion" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-edit"></i>Send Promotion</h4>
		</div>
		<form id="sendForm">
		<input type="hidden" id="sendPromoCode" name="sendPromoCode"/>
		<div class="form-row">
			<label> <span>Email</span> 
				<input id="promoEmailId" name="promoEmailId" type="text" maxlength="100" style="width: 200px;"/>
			</label>
		</div>
		<div class="form-row" id="sendALL">
			<label> <span>Send Promo Code For All</span> 
				<input id="sendPromoForAll" name="sendPromoForAll" type="checkbox">
			</label>
			</div>
		<div class="alerty-action">
			<a class="btn-ok" id="sendUpload" onclick="sentPromotion();">Send</a>
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
			<span id="sendingEmailInfo" style="display: none; color: #a56a96; float: left;">Sending Email...</span>
			 <span id="sendSuccess" style="display: none; color: #a56a96; float: left;">Sent successfully...</span>
             <span id="sendError" style="display: none; color: red; float: left;">Error while send...</span>
		</div>
		</form>
	</div>
	</div>
	<div id="delete_promotion" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-edit"></i>Delete Promotion</h4>
		</div>
		<input type="hidden" id="id" name="promoId"/>
		<div class="content">
			<p>Delete Promotion. Are you sure?</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="deletePromotion();">Delete</a>
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
			 <span id="deleteSuccess" style="display: none; color: #a56a96; float: left;">Deleted successfully...</span>
             <span id="deleteError" style="display: none; color: red; float: left;">Error while delete...</span>
		</div>
	</div>
	</div>
</body>
</html>