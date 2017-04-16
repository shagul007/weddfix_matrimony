<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

<script>
var hash='<%= hash %>';
function submitPayuForm() {
	
	if (hash == '')
		return;
      var payuForm = document.forms.payuForm;
      payuForm.submit();
    }
</script>

<body onload="submitPayuForm();">
<div class="container" style="min-height: 350px; margin-top: 100px;">
	<div class="row">
		<h3>Redirecting... Please wait...</h3>
        <div class="progress progress-striped active page-progress-bar">
            <div class="progress-bar" style="width: 100%;"></div>
        </div>
	</div>
</div>

<form action="<%= action1 %>" method="post" name="payuForm">
							<input type="hidden" name="key" value="<%= merchant_key %>" />
							      <input type="hidden" name="hash" value="<%= hash %>" />
							      <input type="hidden" name="txnid" value="<%= txnid %>" />
							      <input type="hidden" name="amount" id="payuamount" value="<%= request.getParameter("amount") %>" />
							      <input type="hidden" name="firstname" id="firstname" value="<%= request.getParameter("firstname") %>"/>
							      <input type="hidden" name="email" id="email" value="<%= request.getParameter("email") %>" />
							      <input type="hidden" name="phone" id="phone" value="<%= request.getParameter("phone") %>"/>
							      <input type="hidden" name="productinfo" id="productinfo" value="<%= request.getParameter("productinfo") %>" />
							      <input type="hidden" name="surl" id="surl" value="<%= session.getAttribute("url") %>/payment_success" />
							      <input type="hidden" name="furl" id="furl" value="<%= session.getAttribute("url") %>/payment_failure" />
<!-- 							      <a href="javascript:checkout();" class="btn btn-success btn-block"> Checkout <i class="fa fa-angle-right"></i></a> -->
							</form>
</body>
</html>