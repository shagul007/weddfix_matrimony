package com.weddfix.web.mobile;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.SendSMS;

public class SendInfoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	SendSMS sms = new SendSMS();
	RegisterService registerService = new RegisterService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
        try {
        	
        	Long userId = 0L;
        	String verifyMobile = null;

			if(request.getParameter("userId") != null) {
				userId = Long.parseLong(request.getParameter("userId").toString());
			} 
			if(request.getParameter("verifyMobile") != null) {
				verifyMobile = request.getParameter("verifyMobile");
			} 
			
			String verificationCode = request.getParameter("verificationCode");

			if(verificationCode != null) {
				if(verificationCode.equals("send") && userId > 0 && verifyMobile != null) {
							String code = registerService
									.updateMobileVerificationCode(userId);

							String msg = "Your Weddfix mobile verification code is "+code;
							sms.sendSms(msg, verifyMobile);
							
							rootMap.put("status", "success");
							rootMap.put("message", "Verification code sent successfully.");
							rootMap.put("mobile", verifyMobile);
							rootMap.put("verifyMobileNumber", code);
							main.put("verification", rootMap);
							Gson gson = new GsonBuilder().setPrettyPrinting().create();
				            String json = gson.toJson(main);
				            
				            response.setContentType("application/json;charset=utf-8");
				            byte[] out = json.getBytes("UTF-8");
				            response.setContentLength(out.length);
				            response.getOutputStream().write(out);
							return;
						}
				}
			
        } catch (Exception e) {
        	rootMap.put("status", "failure");
        	rootMap.put("message", e);
			main.put("verification", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
        }
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();

		try {
			
			Long userId = 0L;
			Long mobile = 0L;
        	String verifyMobileNumber = null;
        	String verifyCode = null;

			if(request.getParameter("userId") != null) {
				userId = Long.parseLong(request.getParameter("userId").toString());
			} 
			if(request.getParameter("verifyMobileNumber") != null) {
				verifyMobileNumber = request.getParameter("verifyMobileNumber").toString();
			} 
			if(request.getParameter("verifyCode") != null) {
				verifyCode = request.getParameter("verifyCode").toString();
			} 
			if(request.getParameter("mobile") != null) {
				mobile = Long.parseLong(request.getParameter("mobile").toString());
			} 
			
			String verificationCode = request.getParameter("verificationCode");
			
			if(verificationCode.equals("verify")) {
				if(verifyMobileNumber.equals(verifyCode)) {
					String status = registerService
							.verifyCodeAndUpdateMobile(mobile, userId);
					if(status != null) {
						rootMap.put("status", "success");
						rootMap.put("message", "Mobile Number Verified successfully.");
						rootMap.put("mobile", mobile);
						rootMap.put("verifiedMobileNumber", true);
						main.put("verification", rootMap);
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
			            String json = gson.toJson(main);
			            
			            response.setContentType("application/json;charset=utf-8");
			            byte[] out = json.getBytes("UTF-8");
			            response.setContentLength(out.length);
			            response.getOutputStream().write(out);
						return;
					} else {
						rootMap.put("status", "failure");
	        			rootMap.put("message", "Something went wrong. Please try again later.");
						main.put("verification", rootMap);
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
			            String json = gson.toJson(main);
			            
			            response.setContentType("application/json;charset=utf-8");
			            byte[] out = json.getBytes("UTF-8");
			            response.setContentLength(out.length);
			            response.getOutputStream().write(out);
						return;
					}
				} else {
					rootMap.put("status", "failure");
        			rootMap.put("message", "Invalid Verification Code.");
					main.put("verification", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
				}

		} catch (Exception e) {
			e.printStackTrace();
        	rootMap.put("status", "failure");
        	rootMap.put("message", e);
			main.put("booking", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
        }
        
    }
    
}
