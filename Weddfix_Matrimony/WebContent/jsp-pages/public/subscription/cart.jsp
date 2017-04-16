<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.apache.http.client.ClientProtocolException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="java.security.*" %>

<%!
public boolean empty(String s)
	{
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}
%>
<%!
	public String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
            
		
		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }
		
		return hexString.toString();
	}
%>
<% 	
	String merchant_key="HmJtAS";
	String salt="uLvBgXgD";
	String action1 ="";
	String base_url="https://secure.payu.in";
	int error=0;
	String hashString="";
	
 
	
	Enumeration paramNames = request.getParameterNames();
	Map<String,String> params= new HashMap<String,String>();
    	while(paramNames.hasMoreElements()) 
	{
      		String paramName = (String)paramNames.nextElement();
      
      		String paramValue = request.getParameter(paramName);
		params.put(paramName,paramValue);
	}
	String txnid ="";
	if(empty(params.get("txnid"))){
		Random rand = new Random();
		String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
		txnid=hashCal("SHA-256",rndm).substring(0,20);
		session.setAttribute("txnid", txnid);
	}
	else
		txnid=params.get("txnid");
	String txn="abcd";
	String hash="";
	String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	if(empty(params.get("hash")) && params.size()>0)
	{
		if( empty(params.get("key"))
			|| empty(params.get("txnid"))
			|| empty(params.get("amount"))
			|| empty(params.get("firstname"))
			|| empty(params.get("email"))
			|| empty(params.get("phone"))
			|| empty(params.get("productinfo"))
			|| empty(params.get("surl"))
			|| empty(params.get("furl"))	)
			
			error=1;
		else{
			String[] hashVarSeq=hashSequence.split("\\|");
			
			for(String part : hashVarSeq)
			{
				hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
				hashString=hashString.concat("|");
			}
			hashString=hashString.concat(salt);
			
			 hash=hashCal("SHA-512",hashString);
			action1=base_url.concat("/_payment");
		}
	}
	else if(!empty(params.get("hash")))
	{
		hash=params.get("hash");
		action1=base_url.concat("/_payment");
	}
		
%>
<html>
<head>
<style type="text/css">
.table>tbody>tr>td, .table>tfoot>tr>td{
    vertical-align: middle;
}
@media screen and (max-width: 600px) {
    table#cart tbody td .form-control{
		width:20%;
		display: inline !important;
	}
	.actions .btn{
		width:36%;
		margin:1.5em 0;
	}
	
	.actions .btn-info{
		float:left;
	}
	.actions .btn-danger{
		float:right;
	}
	
	table#cart thead { display: none; }
	table#cart tbody td { display: block; padding: .6rem; min-width:320px;}
	table#cart tbody tr td:first-child { background: #CF6AC8; color: #fff; }
	table#cart tbody td:before {
		content: attr(data-th); font-weight: bold;
		display: inline-block; width: 8rem;
	}
	
	
	
	table#cart tfoot td{display:block; }
	table#cart tfoot td .btn{display:block;}
	
}
</style>
<script type="text/javascript">
var role = '<%=session.getAttribute("role") %>';
var myPlanId = '<%=session.getAttribute("myPlanId") %>';
if(role == 'null') {
	window.location.href = "login";
}

if(myPlanId == 'null') {
	window.location.href = "upgrade";
}

function validatePromoCode() {
// 	$('#sendUpload').attr('onclick','').unbind('click');
	$('#invalidPromo').hide();
	$("#promoExpired").hide();
	$("#promoUsed").hide();
	$("#promoDisc").hide();
	$("#discountPercent").text("");
	$("#discountMinus").text(0);
	$("#totalDiscountAmount").text($("#amnt").val());
	$("#payuamount").val($("#amnt").val());
	
	if($("#promocode").val() == null || $("#promocode").val() == ""){
			$('#enterPromoCode').show();
	} else {
		$('#enterPromoCode').hide();
	$.ajax({
		data : {
			promoCodeText : $("#promocode").val()
		},
		url : "validatePromoCode.action",
		type: "POST",
        async: false,
		dataType : "json",
		success : function(data) {

			if(data.promotionDetails[0]  == undefined) {
				$('#invalidPromo').show();
			} else {
				$('#invalidPromo').hide();
				/* var q = new Date();
				var m = q.getMonth();
				var d = q.getDay();
				var y = q.getFullYear(); */

				var date = new Date();

				mydate=new Date(data.promotionDetails[0].expires);

				if(date>mydate) {
					$("#promoExpired").show();
				} else {
					$("#promoExpired").hide();
				
				if(data.promotionDetails[0].userId != null && data.promotionDetails[0].sentTo == null) {
					$("#promoDisc").hide();
					$("#promoUsed").show();
				} else {
					$("#promoUsed").hide();
					$("#promoDisc").show();
					$("#discountPercent").text(" ("+data.promotionDetails[0].discount+"%)");
					var discountAmount = (data.promotionDetails[0].discount / 100) * data.cartDetails[0].amount;
					$("#discountMinus").text("- "+Math.round(discountAmount));
					$("#promoDiscount").text(data.promotionDetails[0].discount);
					$("#totalDiscountAmount").text(Math.round(data.cartDetails[0].amount-discountAmount));
					$("#payuamount").val(Math.round(data.cartDetails[0].amount-discountAmount));
				}
				}
			}
			
		},
		error : function(xhr, textStatus, errorThrown) {
			alert("Error");
			$('#invalidPromo').hide();
			$("#promoExpired").hide();
			$("#promoUsed").hide();
			$("#promoDisc").hide();
		}
		});
	}
}
</script>
<script>
var hash='<%= hash %>';
function submitPayuForm() {
	
	if (hash == '')
		return;
      var payuForm = document.forms.payuForm;
      payuForm.submit();
    }
function checkout() {
	  $.ajax({
			data : {
				promoCodeText : $("#promocode").val()
			},
			url : "validatePromoCode.action",
			type: "POST",
	        async: false,
			dataType : "json",
			success : function(data) {

				if(data.promotionDetails[0]  == undefined) {
					
					$("#payuamount").val(data.cartDetails[0].amount);
					var payuForm = document.forms.payuForm;
// 					https://secure.payu.in/_payment
				      payuForm.submit();
				} else {
					if(data.promotionDetails[0].userId != null && data.promotionDetails[0].sentTo == null) {
						$("#payuamount").val(data.cartDetails[0].amount);
					} else {
						var discountAmount = (data.promotionDetails[0].discount / 100) * data.cartDetails[0].amount;
						var totalDisAMt = Math.round(data.cartDetails[0].amount-discountAmount);
						$("#payuamount").val(totalDisAMt);
					}
					var payuForm = document.forms.payuForm;
// 					https://secure.payu.in/_payment
				      payuForm.submit();
				}
				
			},
			error : function(xhr, textStatus, errorThrown) {
// 				alert("Error");
			}
			});
    }
</script>
</head>
<body onload="submitPayuForm();">
<div class="container" style="min-height: 438px; padding: 0;">
<input type="hidden" id="amt">
	<table id="cart" class="table" style="background-color: ffb4ff; margin-top: 50px;">
    				<thead>
						<tr>
							<th style="width:50%">Cart Item</th>
							<th style="width:10%" class="text-right">Item Price</th>
<!-- 							<th style="width:8%">Quantity</th> -->
							<th style="width:10%" class="text-right">Item Total</th>
<!-- 							<th style="width:10%"></th> -->
						</tr>
					</thead>
					<tbody>
					<s:iterator value="cartDetails">
						<tr>
							<td data-th="Cart Item">
							<input type="hidden" id="amnt" name="amnt" value="<s:property value="%{amount}" />">
								<div class="row">
									<div class="col-sm-2 hidden-xs"><img src="<%=request.getContextPath()%>/images/cart_logo.png" alt="Weddfix_Logo" class="img-responsive"/></div>
									<div class="col-sm-10">
										<h4 class="nomargin"><s:property value="%{planName}" /></h4>
										<p>Upgrade your account to get more features.</p>
									</div>
								</div>
							</td>
							<td align="right">Rs. <s:property value="%{amount}" /></td>
							<!-- <td data-th="Quantity">
								<input type="number" class="form-control text-center" value="1">
							</td> -->
							<td align="right">Rs. <s:property value="%{amount}" /></td>
							<!-- <td class="actions" data-th="">
								<button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>
								<button class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></button>								
							</td> -->
						</tr>
						<%-- <tr class="visible-xs">
							<td class="text-center"><strong>Total 1.99</strong></td>
						</tr> --%>
						<tr>
							<td><input id="promocode" type="text" name="promocode" maxlength="20" size="20" value="" style="margin: 10px 10px 10px 0;">
							<a href="javascript:validatePromoCode();" class="btn btn-warning"><i class="fa fa-angle-left"></i> Apply Promo Code</a>
							<p style="color: darkred; margin: 10px 0 0; display: none;" id="enterPromoCode">Please enter a promo code.</p>
							<p style="color: darkred; margin: 10px 0 0; display: none;" id="invalidPromo">Please enter a valid promo code.</p>
							<p style="color: darkred; font-weight: bold; display: none;" id="promoExpired">This promo code has expired.</p>
							<p style="color: darkred; font-weight: bold; display: none;" id="promoUsed">This promo code already used.</p>
							<p style="color: darkgreen; font-weight: bold; display: none;" id="promoDisc">Congratulations! <span id="promoDiscount"></span>% Discount Promo Code Applied.</p>
							</td>
							<td align="right"><strong>Subtotal</strong></td>
							<td align="right"><strong>Rs. <s:property value="%{amount}" /></strong></td>
						</tr>
						<tr>
							<td></td>
							<td align="right"><strong>Discount<span id="discountPercent"></span></strong></td>
							<td align="right"><strong><span id="discountMinus">0</span></strong></td>
						</tr>
						<tr>
							<td></td>
							<td align="right"><strong>Total</strong></td>
							<td align="right"><strong>Rs. <span id="totalDiscountAmount"><s:property value="%{amount}" /></span></strong></td>
						</tr>
						</s:iterator>
						<tr>
							<td></td>
							<td></td>
							<td><form action="payment_details" method="post" name="payuForm">
							<input type="hidden" name="key" value="<%= merchant_key %>" />
							      <input type="hidden" name="hash" value="<%= hash %>" />
							      <input type="hidden" name="txnid" value="<%= txnid %>" />
							      <input type="hidden" name="amount" id="payuamount" value="<s:property value="%{cartDetails[0].amount}" />" />
							      <input type="hidden" name="firstname" id="firstname" value="<%= session.getAttribute("userName") %>"/>
							      <input type="hidden" name="email" id="email" value="<%= session.getAttribute("email") %>" />
							      <input type="hidden" name="phone" id="phone" value="<%= session.getAttribute("mobile") %>"/>
							      <input type="hidden" name="productinfo" id="productinfo" value="<s:property value="%{cartDetails[0].planName}" />" />
							      <input type="hidden" name="surl" id="surl" value="<%= session.getAttribute("url") %>/payment_success" />
							      <input type="hidden" name="furl" id="furl" value="<%= session.getAttribute("url") %>/payment_failure" />
							      <a href="javascript:checkout();" class="btn btn-success btn-block"> Checkout <i class="fa fa-angle-right"></i></a>
							</form>
							</td>
						</tr>
					</tbody>
				</table>
</div>
</body>
</html>