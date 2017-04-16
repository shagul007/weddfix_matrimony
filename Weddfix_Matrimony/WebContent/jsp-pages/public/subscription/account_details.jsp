<%@ page import="au.com.ourbodycorp.HTML" %>
<%@ page import="au.com.ourbodycorp.Util" %>
<%@ page import="au.com.ourbodycorp.model.*" %>
<%@ page import="au.com.ourbodycorp.model.managers.LibraryManager" %>
<%@ page import="au.com.ourbodycorp.model.managers.CorporationManager" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="au.com.ourbodycorp.model.managers.CountryManager" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="au.com.ourbodycorp.Config" %>
<%@ page import="au.com.ourbodycorp.MailMessage" %>
<%@page import="com.eway.xml.bean.GetAccessCodeResultResponse"%>
<%@page import="com.eway.process.RapidAPI"%>
<%@page import="com.eway.process.SOAPClientJava"%>
<%@page import="javax.xml.namespace.QName"%>
<%@page import="javax.xml.soap.*"%>
<%@page import="javax.xml.transform.*"%>
<%@page import="javax.xml.transform.stream.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CorporationUser cu = (CorporationUser) request.getAttribute("corpUser");

    if (cu == null) {
        response.sendRedirect(Util.loginUrl(Constants.LoginReason.login, request));
        return;
    }
    
    User u = cu.getUser();
    Corporation corp = cu.getCorporation();

    LibraryManager lm = new LibraryManager();

    CorporationManager cm = new CorporationManager();
    
	SOAPClientJava soap = new SOAPClientJava();
	
	Connection conn = Util.getConnection();

	if(session.getAttribute("eWayAccessCode") !=null){
		   String access = new RapidAPI().CallHttpGetURL(session.getAttribute("eWayAccessCode").toString(),"https://api.ewaypayments.com/AccessCode/");
		   JSONObject jsonObject = new JSONObject(access);
		   String accessCode = jsonObject.getString("AccessCode");
		   String resp = access;
		   double totamt = jsonObject.getDouble("TotalAmount");
		   String invoiceNumber = jsonObject.getString("InvoiceNumber");
		   String responseCode = jsonObject.getString("ResponseCode");
		   int transId = 0;
		   if(responseCode.equals("00")){
		   	   transId = jsonObject.getInt("TransactionID");
		   }
		   boolean transStatus = jsonObject.getBoolean("TransactionStatus");
		   double tamt = (totamt / 100);
		   java.math.BigDecimal totalamt = new java.math.BigDecimal(tamt);
		   
		    PaymentHistory ph;
	        	
	        	ph = new PaymentHistory();
	        	ph.setUserid(u.getId());
	        	ph.setCorp_id(corp.getId());
	        	ph.setCompany(corp.getCompanyId());
	        	ph.setAccess_code(accessCode);
	        	ph.setResponse(resp);
	        	ph.setInvoice_number(invoiceNumber);
	        	ph.setTotal_amount(totalamt);
	        	ph.setTrans_id(transId);
	        	ph.setTrans_status(transStatus);
	        	ph.setCreated(new Date());
	        	lm.savePaymentHistoryDetails(ph);
	        	session.setAttribute("eWayAccessCode", null);
	        	
	        	String[] cusId = access.split(",");
	        	String[] cusId1 = cusId[9].split(":");
	        	String ctId = cusId1[1];
	        	
	        	if(!ctId.equals("null")){
	        		
	        		String Customer = new RapidAPI().CallHttpGetURL(ctId,"https://api.ewaypayments.com/Customer/");

		            String[] num = Customer.split(",");
		        	String[] num1 = num[0].split(":");
		        	
		        	String[] nam = Customer.split(",");
		        	String[] nam1 = nam[1].split(":");
		        	
		        	String[] mo = Customer.split(",");
		        	String[] mo1 = mo[2].split(":");
		        	
		        	String[] ye = Customer.split(",");
		        	String[] ye1 = ye[3].split(":");
		        	
		        	String[] cid2 = Customer.split(",");
		        	String[] cid3 = cid2[7].split(":");
		        	
		        	String Number = num1[3].replace("\"","");
		        	String Name = nam1[1].replace("\"","");
		        	String ExpiryMonth = mo1[1].replace("\"","");
		        	String ExpiryYear = ye1[1].replace("\"","");
		        	String TokenCusID = cid3[1].replace("\"","");
		        	
		        	CardDetails card = new CardDetails();
		    		card.setTokencustomerid(Long.parseLong(TokenCusID));
		        	card.setAccountno(Number);
		        	card.setMonth(Long.parseLong(ExpiryMonth));
		        	card.setYear(Long.parseLong(ExpiryYear));
		        	if (Number.substring(0,2).startsWith("4")) {
		        		card.setCard_type("Visa");
		            } else{
		            	card.setCard_type("MC");
		            }
		        	card.setName_on_card(Name);
		        	card.setCorp_id(corp.getId());
		        	card.setCompany(corp.getCompanyId());
		        	card.setUserid(u.getId());
		        	card.setCreated(new Date());
		        	lm.saveCardDetails(card);
	        	}
	        	
	            PreparedStatement pst = conn.prepareStatement("select rebill_customer_id,rebill_id from cancel_subscription where corp_id = ? and canceled_date is null");
	            pst.setLong(1, corp.getId());
	            ResultSet rs = pst.executeQuery();
	            if(rs.next()){
	            	session.setAttribute("CustomerId", rs.getLong("rebill_customer_id"));
	            	session.setAttribute("RebillId", rs.getLong("rebill_id"));
		      	}else{
		    			session.setAttribute("CustomerId", null);
		    			session.setAttribute("RebillId", null);
		    	} rs.close(); pst.close();

				SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyyy hh:mm a");

				String no = String.valueOf(ph.getId());
	            String orderdate = String.valueOf(sdf.format(ph.getCreated()));
			    String transactionno = String.valueOf(ph.getTrans_id());

				String planename = session.getAttribute("plan_name").toString();
	            String price = session.getAttribute("price").toString();
			    String tax = session.getAttribute("tax").toString();
			    String total = session.getAttribute("totalamt").toString();

	            Properties props = new Properties();
	            props.put("no", no);
	            props.put("orderdate", orderdate);
	            props.put("billedto", u.getFirstName()+" "+u.getLastName());
	            props.put("transactionno", transactionno);
	            props.put("company", corp.getName());
	            props.put("address1", corp.getAddress1());
	            props.put("address2", corp.getAddress2());
	            props.put("city", corp.getSuburb());
	            props.put("state", corp.getState());
	            props.put("pincode", corp.getPostcode());
	            props.put("country", corp.getCountryName());
	            props.put("itemname", planename);
	            props.put("price", price);
	            props.put("tax", tax);
	            props.put("total", total);
	            MailMessage msg = new MailMessage(props, "new_order.vm", "shagul001@gmail.com","New Order "+ph.getId()+"");

	            try {
	                msg.send();
	            }
	            catch (Exception e) {
// 	                e.printStackTrace();
// 	                throw e;
	            }
	            
	            if(session.getAttribute("savedpromocode") != null){
	            	
	            String promocode = session.getAttribute("savedpromocode").toString();
	            

	            PreparedStatement pst1 = conn.prepareStatement("update promotion set company = ?, accepted = current_timestamp where promo_code = ?");
	            pst1.setLong(1, corp.getCompanyId());
	            pst1.setString(2, promocode);
	            pst1.executeUpdate();
	            pst1.close();
	            
	            }
	            
	            if(session.getAttribute("type").equals("upgrade")){
	            	
	            if(session.getAttribute("EWAY_CARDNUMBER") !=null){
	            	
	            	if(session.getAttribute("CustomerId") !=null){
	            		
	            		try {
	            			String cId = session.getAttribute("CustomerId").toString();
		                    // Create SOAP Connection
		                    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		                    SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		                    // Send SOAP Message to SOAP Server
		                    String url = "https://www.eway.com.au/gateway/rebill/manageRebill.asmx";
//		                    String url = "https://www.eway.com.au/gateway/rebill/test/manageRebill_test.asmx";
		                    SOAPMessage soapResponseUpdateCustomer = soapConnection.call(soap.createSOAPUpdateCustomerRequest(cId, u.getFirstName(), u.getLastName(), corp.getAddress1(), corp.getAddress2(), corp.getSuburb(), corp.getState(), corp.getName(), corp.getPostcode(), corp.getCountry(), u.getEmail(), u.getMobile()), url);

		                    // Process the SOAP Response
		                    soap.printSOAPResponse(soapResponseUpdateCustomer);
		                    
		                    String RebillCustomerID = soapResponseUpdateCustomer.getSOAPBody().getElementsByTagName("RebillCustomerID").item(0).getFirstChild().getNodeValue(); 
		                    System.out.print("\nRebillCustomerID "+RebillCustomerID);
		                    
		                    if(RebillCustomerID != null){
		                    	String rId = session.getAttribute("RebillId").toString();
		                    	
		                    	Date date = new Date();
		                		Calendar cal = Calendar.getInstance();
		                		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		                		String strDate = sdf1.format(cal.getTime());
		                		
		                		 String[] curDate = strDate.split("/");
		                		 int day = Integer.parseInt(curDate[0]) + 1;
		                		 int month = Integer.parseInt(curDate[1]) + 1;
		                		 int addfiveYear = Integer.parseInt(curDate[2]);
		                		 
		               		 	 cal.set(Calendar.MONTH, month - 1);
		               		 	 String nextMonth = sdf1.format(cal.getTime());
		               		 	 
		               		 	 cal.set(Calendar.MONTH, month);
		               		 	 String nextstartMonth = sdf1.format(cal.getTime());
		               		 	 
		                   		 cal.set(Calendar.DATE, day);
		                   		 cal.set(Calendar.MONTH, month - 3);
		                   		 cal.set(Calendar.YEAR, addfiveYear + 5);
		             		 	 String fiveYear = sdf1.format(cal.getTime());
		                		
		                    	String InvRef = session.getAttribute("invoice_number").toString();
		                    	String InvDes = "1 x "+session.getAttribute("plan_name").toString();
		                    	String CCName = session.getAttribute("EWAY_CARDNAME").toString();
		                    	String CCNumber = session.getAttribute("EWAY_CARDNUMBER").toString();
		                    	String CCMM = session.getAttribute("EWAY_CARDEXPIRYMONTH").toString();
		                    	String CCYY = session.getAttribute("EWAY_CARDEXPIRYYEAR").toString();
		                    	String TotAmt = session.getAttribute("TotalAmountforRebiiling").toString();
		                    	
		                    	SOAPMessage soapResponseUpdateRebillEvent = soapConnection.call(soap.createSOAPUpdateRebillEventRequest(RebillCustomerID, rId, InvRef, InvDes, CCName, CCNumber, CCMM, CCYY, TotAmt, nextMonth, nextstartMonth, fiveYear), url);

		                        // Process the SOAP Response
		                        soap.printSOAPResponse(soapResponseUpdateRebillEvent);
		                        
		                        String RebillID = soapResponseUpdateRebillEvent.getSOAPBody().getElementsByTagName("RebillID").item(0).getFirstChild().getNodeValue(); 
			                    System.out.print("\nRebillID "+RebillID);
			                    
			    	            PreparedStatement pst2 = conn.prepareStatement("update cancel_subscription set order_date = current_timestamp where rebill_customer_id = ?");
			    	            pst2.setLong(1, Long.parseLong(RebillCustomerID));
			    	            pst2.executeUpdate();
			    	            pst2.close();
		                    }

		                    soapConnection.close();
		                } catch (Exception e) {
		                    System.err.println("Error occurred while sending SOAP Request to Server");
		                    e.printStackTrace();
		                }
	            		
	            	}else{
	            		
	            	try {
	                    // Create SOAP Connection
	                    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	                    SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	                    // Send SOAP Message to SOAP Server
	                    String url = "https://www.eway.com.au/gateway/rebill/manageRebill.asmx";
//	                    String url = "https://www.eway.com.au/gateway/rebill/test/manageRebill_test.asmx";
	                    SOAPMessage soapResponseCreateCustomer = soapConnection.call(soap.createSOAPCreateCustomerRequest(u.getFirstName(), u.getLastName(), corp.getAddress1(), corp.getAddress2(), corp.getSuburb(), corp.getState(), corp.getName(), corp.getPostcode(), corp.getCountry(), u.getEmail(), u.getMobile()), url);

	                    // Process the SOAP Response
	                    soap.printSOAPResponse(soapResponseCreateCustomer);
	                    
	                    String RebillCustomerID = soapResponseCreateCustomer.getSOAPBody().getElementsByTagName("RebillCustomerID").item(0).getFirstChild().getNodeValue(); 
	                    System.out.print("\nRebillCustomerID "+RebillCustomerID);
	                    
	                    if(RebillCustomerID != null){
	                    	Date date = new Date();
	                		Calendar cal = Calendar.getInstance();
	                		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	                		String strDate = sdf1.format(cal.getTime());
	                		
	                		 String[] curDate = strDate.split("/");
	                		 int day = Integer.parseInt(curDate[0]) + 1;
	                		 int month = Integer.parseInt(curDate[1]) + 1;
	                		 int addfiveYear = Integer.parseInt(curDate[2]);
	                		 
	               		 	 cal.set(Calendar.MONTH, month - 1);
	               		 	 String nextMonth = sdf1.format(cal.getTime());
	               		 	 
	               		 	 cal.set(Calendar.MONTH, month);
	               		 	 String nextstartMonth = sdf1.format(cal.getTime());
	               		 	 
	                   		 cal.set(Calendar.DATE, day);
	                   		 cal.set(Calendar.MONTH, month - 3);
	                   		 cal.set(Calendar.YEAR, addfiveYear + 5);
	             		 	 String fiveYear = sdf1.format(cal.getTime());
	                		
	                    	String InvRef = session.getAttribute("invoice_number").toString();
	                    	String InvDes = "1 x "+session.getAttribute("plan_name").toString();
	                    	String CCName = session.getAttribute("EWAY_CARDNAME").toString();
	                    	String CCNumber = session.getAttribute("EWAY_CARDNUMBER").toString();
	                    	String CCMM = session.getAttribute("EWAY_CARDEXPIRYMONTH").toString();
	                    	String CCYY = session.getAttribute("EWAY_CARDEXPIRYYEAR").toString();
	                    	String TotAmt = session.getAttribute("TotalAmountforRebiiling").toString();
	                    	
	                    	SOAPMessage soapResponseCreateRebillEvent = soapConnection.call(soap.createSOAPCreateRebillEventRequest(RebillCustomerID, InvRef, InvDes, CCName, CCNumber, CCMM, CCYY, TotAmt, nextMonth, nextstartMonth, fiveYear), url);

	                        // Process the SOAP Response
	                        soap.printSOAPResponse(soapResponseCreateRebillEvent);
	                        
	                        String RebillID = soapResponseCreateRebillEvent.getSOAPBody().getElementsByTagName("RebillID").item(0).getFirstChild().getNodeValue(); 
		                    System.out.print("\nRebillID "+RebillID);
		                    
		                    CancelSubscription cs = new CancelSubscription();
		                    cs.setRebill_customer_id(Long.parseLong(RebillCustomerID));
		                    cs.setRebill_id(Long.parseLong(RebillID));
		                    cs.setOrder_date(new Date());
		                    cs.setCanceled_date(null);
		                    cs.setReason(null);
		                    cs.setCorp_id(corp.getId());
		                    cs.setCompany(corp.getCompanyId());
		                    cs.setUserid(u.getId());
		                    cs.setCreated(new Date());
		                	lm.saveCancelSubscriptionDetails(cs);
	                    }

	                    soapConnection.close();
	                } catch (Exception e) {
	                    System.err.println("Error occurred while sending SOAP Request to Server");
	                    e.printStackTrace();
	                }
	            }
	            }
	            }
	       
 	}
	
	if(session.getAttribute("eWayTokenCode") !=null){
		String Token = session.getAttribute("ResponceTokenText").toString();
// 		System.out.println("token"+Token);
		   
			String[] tra = Token.split(",");
			String[] tra1 = tra[3].split(":");
			
			String[] cid = Token.split(",");
			String[] cid1 = cid[19].split(":");
			
			String[] inv = Token.split(",");
			String[] inv1 = inv[43].split(":");
			
			String[] tamt = Token.split(",");
			String[] tamt1 = tamt[42].split(":");
			
			String[] trast = Token.split(",");
			String[] trast1 = trast[4].split(":");
			
			String Trans_id = tra1[1].replace("\"","");
			String Token_CusID = cid1[1].replace("\"","");
			String Inv_no = inv1[1].replace("\"","");
			String Tot_tmt = tamt1[2].replace("\"","");
			String traStatus = trast1[1].replace("\"","");
		   
		   double totamt = Double.parseDouble(Tot_tmt);
		   double t_amt = (totamt / 100);
		   java.math.BigDecimal totalamt = new java.math.BigDecimal(t_amt);
		   boolean transStatus = Boolean.parseBoolean(traStatus);
		   
		    PaymentHistory ph;
	        	
	        	ph = new PaymentHistory();
	        	ph.setUserid(u.getId());
	        	ph.setCorp_id(corp.getId());
	        	ph.setCompany(corp.getCompanyId());
	        	ph.setAccess_code(Token_CusID);
	        	ph.setResponse(Token_CusID);
	        	ph.setInvoice_number(Inv_no);
	        	ph.setTotal_amount(totalamt);
	        	ph.setTrans_id(Long.parseLong(Trans_id));
	        	ph.setTrans_status(transStatus);
	        	ph.setCreated(new Date());
	        	lm.savePaymentHistoryDetails(ph);
	        	session.setAttribute("eWayTokenCode", null);
	        	session.setAttribute("ResponceTokenText", null);

				SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyyy hh:mm a");

				String no = String.valueOf(ph.getId());
	            String orderdate = String.valueOf(sdf.format(ph.getCreated()));
			    String transactionno = String.valueOf(ph.getTrans_id());

				String planename = session.getAttribute("plan_name").toString();
	            String price = session.getAttribute("price").toString();
			    String tax = session.getAttribute("tax").toString();
			    String total = session.getAttribute("totalamt").toString();
			    
	            PreparedStatement pst3 = conn.prepareStatement("select rebill_customer_id,rebill_id from cancel_subscription where corp_id = ? and canceled_date is null");
	            pst3.setLong(1, corp.getId());
	            ResultSet rs3 = pst3.executeQuery();
	            if(rs3.next()){
	            	session.setAttribute("CustomerId", rs3.getLong("rebill_customer_id"));
	            	session.setAttribute("RebillId", rs3.getLong("rebill_id"));
		      	}else{
		    			session.setAttribute("CustomerId", null);
		    			session.setAttribute("RebillId", null);
		    	} rs3.close(); pst3.close();

	            Properties props = new Properties();
	            props.put("no", no);
	            props.put("orderdate", orderdate);
	            props.put("billedto", u.getFirstName()+" "+u.getLastName());
	            props.put("transactionno", transactionno);
	            props.put("company", corp.getName());
	            props.put("address1", corp.getAddress1());
	            props.put("address2", corp.getAddress2());
	            props.put("city", corp.getSuburb());
	            props.put("state", corp.getState());
	            props.put("pincode", corp.getPostcode());
	            props.put("country", corp.getCountryName());
	            props.put("itemname", planename);
	            props.put("price", price);
	            props.put("tax", tax);
	            props.put("total", total);
	            MailMessage msg = new MailMessage(props, "new_order.vm", "shagul001@gmail.com","New Order "+ph.getId()+"");

	            try {
	                msg.send();
	            }
	            catch (Exception e) {
//	                e.printStackTrace();
//	                throw e;
	            }
	            
	            if(session.getAttribute("savedpromocode") != null){
	            	
	            String promocode = session.getAttribute("savedpromocode").toString();
	            
	            PreparedStatement pst4 = conn.prepareStatement("update promotion set company = ?, accepted = current_timestamp where promo_code = ?");
	            pst4.setLong(1, corp.getCompanyId());
	            pst4.setString(2, promocode);
	            pst4.executeUpdate();
	            pst4.close();
	            
	            }
	            if(session.getAttribute("type").equals("upgrade")){
	            
	            	try {
            			String cId = session.getAttribute("CustomerId").toString();
	                    // Create SOAP Connection
	                    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	                    SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	                    // Send SOAP Message to SOAP Server
	                    String url = "https://www.eway.com.au/gateway/rebill/manageRebill.asmx";
//	                    String url = "https://www.eway.com.au/gateway/rebill/test/manageRebill_test.asmx";
	                    SOAPMessage soapResponseUpdateCustomer = soapConnection.call(soap.createSOAPUpdateCustomerRequest(cId, u.getFirstName(), u.getLastName(), corp.getAddress1(), corp.getAddress2(), corp.getSuburb(), corp.getState(), corp.getName(), corp.getPostcode(), corp.getCountry(), u.getEmail(), u.getMobile()), url);

	                    // Process the SOAP Response
	                    soap.printSOAPResponse(soapResponseUpdateCustomer);
	                    
	                    String RebillCustomerID = soapResponseUpdateCustomer.getSOAPBody().getElementsByTagName("RebillCustomerID").item(0).getFirstChild().getNodeValue(); 
	                    System.out.print("\nRebillCustomerID "+RebillCustomerID);
	                    
	                    if(RebillCustomerID != null){
	                    	String rId = session.getAttribute("RebillId").toString();
	                    	
	                    	Date date = new Date();
	                		Calendar cal = Calendar.getInstance();
	                		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	                		String strDate = sdf1.format(cal.getTime());
	                		
	                		 String[] curDate = strDate.split("/");
	                		 int day = Integer.parseInt(curDate[0]) + 1;
	                		 int month = Integer.parseInt(curDate[1]) + 1;
	                		 int addfiveYear = Integer.parseInt(curDate[2]);
	                		 
	               		 	 cal.set(Calendar.MONTH, month - 1);
	               		 	 String nextMonth = sdf1.format(cal.getTime());
	               		 	 
	               		 	 cal.set(Calendar.MONTH, month);
	               		 	 String nextstartMonth = sdf1.format(cal.getTime());
	               		 	 
	                   		 cal.set(Calendar.DATE, day);
	                   		 cal.set(Calendar.MONTH, month - 3);
	                   		 cal.set(Calendar.YEAR, addfiveYear + 5);
	             		 	 String fiveYear = sdf1.format(cal.getTime());
	                		
	             		 	 String CCName = null;
	                    	String CCNumber = null;
	                    	String CCMM = null;
	                    	String CCYY = null;
	                    			
	        	            PreparedStatement pst5 = conn.prepareStatement("select name_on_card,accountno,month,year from card_details where tokencustomerid = ?");
	        	            pst5.setLong(1, Long.parseLong(session.getAttribute("tkCusId").toString()));
	        	            ResultSet rs5 = pst5.executeQuery();
	        	            if(rs5.next()){
	        	            	CCName = rs5.getString("name_on_card");
		                    	CCNumber = rs5.getString("accountno");
		                    	CCMM = rs5.getString("month");
		                    	if(rs5.getLong("month") < 10){
		                    		CCMM = "0"+rs5.getString("month");
		                    	}
		                    	CCYY = rs5.getString("year");
		                    	if(rs5.getLong("year") < 10){
		                    		CCYY = "0"+rs5.getString("year");
		                    	}
	        	            }
	        	            rs5.close();
	        	            pst5.close();
	        	            
	                    	String InvRef = session.getAttribute("invoice_number").toString();
	                    	String InvDes = "1 x "+session.getAttribute("plan_name").toString();
	                    	String TotAmt = session.getAttribute("TotalAmountforRebiiling").toString();
	                    	
	                    	SOAPMessage soapResponseUpdateRebillEvent = soapConnection.call(soap.createSOAPUpdateRebillEventRequest(RebillCustomerID, rId, InvRef, InvDes, CCName, CCNumber, CCMM, CCYY, TotAmt, nextMonth, nextstartMonth, fiveYear), url);

	                        // Process the SOAP Response
	                        soap.printSOAPResponse(soapResponseUpdateRebillEvent);
	                        
	                        String RebillID = soapResponseUpdateRebillEvent.getSOAPBody().getElementsByTagName("RebillID").item(0).getFirstChild().getNodeValue(); 
		                    System.out.print("\nRebillID "+RebillID);
		                    
		    	            PreparedStatement pst6 = conn.prepareStatement("update cancel_subscription set order_date = current_timestamp where rebill_customer_id = ?");
		    	            pst6.setLong(1, Long.parseLong(RebillCustomerID));
		    	            pst6.executeUpdate();
		    	            pst6.close();
	                    }

	                    soapConnection.close();
	                } catch (Exception e) {
	                    System.err.println("Error occurred while sending SOAP Request to Server");
	                    e.printStackTrace();
	                }
	            }
	       
	}
    
    int trialDaysLeft = cm.trialDaysLeft(corp.getId());
    
    session.setAttribute("Success", null);
    
    boolean addLink = false;
    if(trialDaysLeft < 0){
         if (corp.isExpiredTrial() || cm.hasTrialExpired(corp.getId())) {
             corp.setLocked("Your trial period has expired.");
             addLink = true;
         }
   	 	 if (corp.getLocked() != null || corp.isExpiredTrial()) {
	   		 session.setAttribute("AccountExpired", "Expired");
	     }
	     else if(cm.hasTrialExpired(corp.getId())){
	    	 session.setAttribute("AccountExpired", "Expired");
	     }
	     else{
	    	 session.setAttribute("AccountExpired", null);
	     }
    }else{
    	session.setAttribute("AccountExpired", null);
    }
    
    session.setAttribute("eWayErrorMessage", null);
    
    List<PatientGroup> patientGroups = lm.getPatientGroups(corp.getId());

    String[][] breadcrumbs = {{"/corp/", corp.getName()}, {null, "Subscription"}};
    
%>
<%if(session.getAttribute("AccountExpired") != null){ %>
<html>
<head><title>Account Locked</title></head>
<body>
<div class="page-header">
    <h1>Account locked</h1>
</div>
<%@include file="/WEB-INF/includes/breadcrumbs.jsp"%>
<div class="row">
    <div class="span4">
        <p style="font-size: 17px; margin: 40px 0;">
            <span style="font-weight: bold; font-size: 25px;"><%=corp.getName()%></span> has been locked, because:
        </p>
        <p class="well" style="font-size: 15px;"> <%=corp.getLocked()%>
        <% if (addLink){ %>
            <br/><br/>To continue using MyPhysio please <a href="/corp/subscription/pricing.jsp">check our pricing page</a> and sign up for a plan. We hope to see you again soon.</p>
        <% } else { %>
        <p style="font-size: 17px;"> Please contact support to resolve the issue. </p>
        <% } %>
    </div>
</div>
</body>
</html>
<%}else{ %>
<html>
<head><title>MyPhysio Subscription</title>
<style type="text/css">
.form-horizontal .control-label {
    float: left;
    padding-top: 5px;
    text-align: left;
}
</style>
</head>
<body>
<div class="page-header">
    <h1>MyPhysio Subscription</h1>
</div>
<%@include file="/WEB-INF/includes/breadcrumbs.jsp"%>
<%
        PreparedStatement pst7 = conn.prepareStatement("select id,accountno,month,year,card_type,name_on_card from card_details where corp_id = ? order by id DESC");
        pst7.setLong(1, corp.getId());
        ResultSet rs7 = pst7.executeQuery();
        if(rs7.next() == false){rs7.close();pst7.close();
        %>
<!-- 		    	<p style="color: darkred; margin: 10px 0 0;">There is no saved cards in your account.</p> -->
        <%}else{ %>
<div class="span8 well">
<!-- <fieldset style="width: 200px; float: left;">
  		<legend style="margin: 0px 0px 10px;">Card Details:</legend>
  		<a href="/corp/subscription/card_details.jsp" class="btn btn-primary"><i class="icon-plus icon-white"></i> Add Card</a>
  		<fieldset style="width: 200px; float: left;">
  		<legend style="margin: 0px 0px 10px;">Payment History:</legend>
  		<a href="/corp/subscription/payment_history.jsp" class="btn btn-primary">View</a>
		</fieldset>
		</fieldset> -->
		<fieldset>
		<legend style="margin:0;">Saved Cards:</legend>
		<table class="table" style="width: 75%;" align="center">
	    <tr>
	    	<th>Account Number</th>
	        <th>Card Type</th>
	        <th>Delete Card</th>
	    </tr>
        <%
        conn.setAutoCommit(false);
        PreparedStatement pst8 = conn.prepareStatement("select id,accountno,month,year,card_type,name_on_card from card_details where corp_id = ? order by id DESC");
        pst8.setLong(1, corp.getId());
        ResultSet rs8 = pst8.executeQuery();
        while(rs8.next()){
        	String accno = rs8.getString("accountno");
        	Long id = rs8.getLong("id");
        %>
    <tr>
        <td><%=accno.substring(0,4)%>-XXXX-XXXX-<%=accno.substring(Math.max(accno.length() - 4, 0))%></td>
        <td><%if(rs8.getString("card_type").equals("MC")){%>Master Card<%}else{ %><%=rs8.getString("card_type")%><%} %> </td>
		<td>
		<p>
		<a class="btn btn-danger btn-mini fancybox" href="inline/delete_carddetails.jsp?id=<%=id%>">
		<i class="icon icon-trash icon-white"></i>
		Delete
		</a>
		</p>
		</td>
   	</tr>

<%}rs8.close();pst8.close();%>
</table>
</fieldset>
</div>
<%} %>
  <div class="span8 well">
  <form class="form-horizontal" method="post">
  <fieldset>
  		<legend>Billing Details:</legend>
  <%
        PreparedStatement pst9 = conn.prepareStatement("select c.max_practitioners,cm.max_corp,c.sub_type,c.sms_count from corporation c join company cm on cm.id = c.company where c.id = ?");
        pst9.setLong(1, corp.getId());
        ResultSet rs9 = pst9.executeQuery();
        while(rs9.next()){
  %>
  <div class="control-group">
  <%if(rs9.getString("sub_type") != null){ %>
	<label style="text-align: right; width: 423px; float: left;"><b>Plan name :</b>&nbsp;&nbsp;</label>
	<span><%=rs9.getString("sub_type").substring(0, 1).toUpperCase()+rs9.getString("sub_type").substring(1) %><%if(rs9.getString("sub_type").equals("solo")){ %> Practitioner<%}else{ %> Practice<%} %></span>
	<%session.setAttribute("Plan_Name", rs9.getString("sub_type").substring(0, 1).toUpperCase()+rs9.getString("sub_type").substring(1)+" Practice"); 
// 	System.out.println("p"+session.getAttribute("Plan_Name").toString());
	%>
	<br><br>
	<label style="text-align: right; width: 423px; float: left;"><b>Maximum number of users :</b>&nbsp;&nbsp;</label>
	<span><%=rs9.getLong("max_practitioners") %></span> users
	<br><br>
	<label style="text-align: right; width: 423px; float: left;"><b>Number of practice locations available in that company :</b>&nbsp;&nbsp;</label>
	<span><%=rs9.getLong("max_corp") %></span> locations
	<br><br>
	<label style="text-align: right; width: 423px; float: left;"><b>In-App Marketing and Push Messaging availability :</b>&nbsp;&nbsp;</label>
	<span>
	<%if(rs9.getString("sub_type").equals("small") || rs9.getString("sub_type").equals("large") || rs9.getString("sub_type").equals("unlimited")){ %>
		Available
	<%}else{ %>
		Not Available
	<%} %>
	</span>
	<br><br>
	<label style="text-align: right; width: 423px; float: left;"><b>Monthly fee (plus GST for Australia customers) :</b>&nbsp;&nbsp;</label>
	<span>
	<% if(rs9.getString("sub_type").equals("solo")){ %>
		$39.00
	<%}if(rs9.getString("sub_type").equals("small")){ %>
		$79.00
	<%}if(rs9.getString("sub_type").equals("large")){ %>
		$129.00
	<%}if(rs9.getString("sub_type").equals("unlimited")){ %>
		$129.00
	<%}if(rs9.getString("sub_type").equals("null") || rs9.getString("sub_type").equals("trial")){ %>
		$0.00
	<%} %>
	</span>
	<br><br>
	<% if (trialDaysLeft > 0){ %>
	<label style="text-align: right; width: 423px; float: left;"><b>Trial left :</b>&nbsp;&nbsp;</label>
	You have <span><%=trialDaysLeft %></span> <%=(trialDaysLeft > 1)? "days" : "day"%> left of your trial. <a href="/corp/subscription/pricing.jsp">Subscribe here</a>.
	<br><br>
	<% } else if (trialDaysLeft == 0){ %>
	<label style="text-align: right; width: 423px; float: left;"><b>Trial left :</b>&nbsp;&nbsp;</label>
	<span style="color: red; float: left;">Trial - Expiring Today!</span>
    <p style="width: 245px; float: left; margin: 0 0 15px;">This is the last day of your trial period. We'd love to keep working with you, please <a href="/corp/subscription/pricing.jsp">Subscribe here</a>.</p>
	<br><br>
	<% } %>
	<label style="text-align: right; width: 423px; float: left;"><b>Available SMS credits :</b>&nbsp;&nbsp;</label>
	You have<span> <%=rs9.getLong("sms_count") %> </span>SMS remaining. <a href="/corp/subscription/pricing_sms.jsp">Need more credits? Click here</a>.
	<%}else{ %>
		<label style="text-align: right; width: 275px; float: left;"><b>Available SMS credits :</b>&nbsp;&nbsp;</label>
	You have<span> <%=rs9.getLong("sms_count") %> </span>SMS remaining. <a href="/corp/subscription/pricing_sms.jsp">Need more credits? Click here</a>.
	<%} %>
  </div>
 			<fieldset>
 <%if(rs9.getString("sub_type") != null){ %>
			 </fieldset>
  <div class="form-actions">
	<a href="/corp/subscription/pricing.jsp" class="btn btn-primary">Upgrade Account</a>
	 <%
        PreparedStatement pst10 = conn.prepareStatement("select id,rebill_customer_id,rebill_id,order_date,canceled_date from cancel_subscription where corp_id = ? and canceled_date is null");
        pst10.setLong(1, corp.getId());
        ResultSet rs10 = pst10.executeQuery();
        if(rs10.next()){
        	session.setAttribute("CustomerId", rs10.getLong("rebill_customer_id"));
        	session.setAttribute("RebillId", rs10.getLong("rebill_id"));
  	%>
	&nbsp;or <a href="inline/cancel_subscription.jsp?customerid=<%=rs10.getLong("rebill_customer_id")%>&rebillid=<%=rs10.getLong("rebill_id")%>" class="fancybox">cancel subscription</a>
	<%}else{
			session.setAttribute("CustomerId", null);
			session.setAttribute("RebillId", null);
	} rs10.close();pst10.close(); 
// 	System.out.println("c"+session.getAttribute("CustomerId"));
// 	System.out.println("r"+session.getAttribute("RebillId"));
	}%>
	<%} rs9.close();pst9.close();%>
  </div>
  </fieldset>
  </form>
  </div>
</body>
</html>
<%} conn.close();%>