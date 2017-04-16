package com.weddfix.web.mobile;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.CartDetailsFormBean;
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;

public class UpgradeServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RegisterService registerService = new RegisterService();
	CommonMasterService commonMasterService = new CommonMasterService();
	CartDetailsFormBean cartDetailsFormBean = new CartDetailsFormBean();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	Session conn;
	
	private static final Logger logger = Logger
			.getLogger(UpgradeServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
		Long userId = 0L;
		Long profileId = 0L;
		Long planId = 0L;
		String promoCode = null;
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		if(request.getParameter("profileId") != null) {
			profileId = Long.parseLong(request.getParameter("profileId").toString());
		}
		
		if(request.getParameter("planId") != null) {
			planId = Long.parseLong(request.getParameter("planId").toString());
		}
		
		if(request.getParameter("promoCode") != null) {
			promoCode = request.getParameter("promoCode").toString();
		}
		
        String find = request.getParameter("find");
        String save = request.getParameter("save");
        String validate = request.getParameter("validate");
        
        if(find != null) {
        	if(find.equals("cart") && userId > 0) {
        		
        		List<CartDetailsFormBean> cartDetailsInfoList = commonMasterService
        				.loadCartDetails(userId);
        		
        		LinkedHashMap<String, Object> cartDetailsMap = new LinkedHashMap<String, Object>();
        		
        		Iterator<?> itr = cartDetailsInfoList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					cartDetailsMap.put("id", obj[0]);
					cartDetailsMap.put("planId", obj[1]);
					cartDetailsMap.put("planName", obj[2]);
					cartDetailsMap.put("planType", obj[3]);
					cartDetailsMap.put("amount", obj[4]);
					cartDetailsMap.put("validity", obj[5]);
					cartDetailsMap.put("status", obj[6]);

			}
				
				main.put("cartDetails", cartDetailsMap);
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
	            
				return;
        	}
        }
        
        if(validate != null) {
        	if(validate.equals("promoCode") && promoCode != null) {
        		
        		List<PromotionDetailsFormBean> promotionDetailsInfoList = commonMasterService
    					.validatePromoCode(promoCode);
        		
        		LinkedHashMap<String, Object> promotionDetailsMap = new LinkedHashMap<String, Object>();
        		
        		Iterator<?> itr = promotionDetailsInfoList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					promotionDetailsMap.put("id", obj[0]);
					promotionDetailsMap.put("promoCode", obj[1]);
					promotionDetailsMap.put("expires", obj[2]);
					promotionDetailsMap.put("discount", obj[3]);
					if(obj[4] != null) {
						promotionDetailsMap.put("email", obj[4]);
					} else {
						promotionDetailsMap.put("email", "");
					}
					if(obj[5] != null) {
						promotionDetailsMap.put("sent", obj[5]);
					} else {
						promotionDetailsMap.put("sent", "");
					}
					promotionDetailsMap.put("status", obj[6]);
					if(obj[7] != null) {
						promotionDetailsMap.put("userId", obj[7]);
					} else {
						promotionDetailsMap.put("userId", "");
					}
					if(obj[8] != null) {
						promotionDetailsMap.put("sentTo", obj[8]);
					} else {
						promotionDetailsMap.put("sentTo", "");
					}
					if(obj[7] != null && obj[8] == null) {
						promotionDetailsMap.put("alreadyUsedPromoCode", true);
					} else {
						promotionDetailsMap.put("alreadyUsedPromoCode", false);
					}

			}
				
				main.put("promoDetails", promotionDetailsMap);
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
	            
				return;
        	}
        }
        if(save != null) {
        	if(save.equals("cart") && profileId > 0 && userId > 0 && planId > 0) {
        		
        		List<CartDetailsFormBean> cartDetailsList = commonMasterService
        				.loadCartDetails(userId);
        		if(!cartDetailsList.isEmpty()) {
        		Iterator<?> itr = cartDetailsList.iterator();
        		while (itr.hasNext()) {

        			Object[] obj = (Object[]) itr.next();
        			
        			if(Long.parseLong(obj[1].toString()) != planId) {
        				
        				cartDetailsFormBean.setId(Long.parseLong(obj[0].toString()));
        				cartDetailsFormBean.setPlanId(planId);
        				cartDetailsFormBean.setUpdatedBy(userId);
        				Long status = commonMasterService
        						.saveCartDetails(cartDetailsFormBean);
        				if (status != null) {
        					List<CartDetailsFormBean> cartDetailsInfoList = commonMasterService
        	        				.loadCartDetails(userId);
            				
            				LinkedHashMap<String, Object> cartDetailsMap = new LinkedHashMap<String, Object>();
        	        		
        	        		Iterator<?> itr1 = cartDetailsInfoList.iterator();
        					while (itr1.hasNext()) {

        						Object[] obj1 = (Object[]) itr1.next();
        						cartDetailsMap.put("id", obj1[0]);
        						cartDetailsMap.put("planId", obj1[1]);
        						cartDetailsMap.put("planName", obj1[2]);
        						cartDetailsMap.put("planType", obj1[3]);
        						cartDetailsMap.put("amount", obj1[4]);
        						cartDetailsMap.put("validity", obj1[5]);
        						cartDetailsMap.put("status", obj1[6]);

        					}
        					
        					main.put("cartDetails", cartDetailsMap);
        					
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
        					main.put("notInterested", rootMap);
        					Gson gson = new GsonBuilder().setPrettyPrinting().create();
        		            String json = gson.toJson(main);
        		            
        		            response.setContentType("application/json;charset=utf-8");
        		            byte[] out = json.getBytes("UTF-8");
        		            response.setContentLength(out.length);
        		            response.getOutputStream().write(out);
        					return;
            		}
        		} else {
        			List<CartDetailsFormBean> cartDetailsInfoList = commonMasterService
	        				.loadCartDetails(userId);
    				
    				LinkedHashMap<String, Object> cartDetailsMap = new LinkedHashMap<String, Object>();
	        		
	        		Iterator<?> itr2 = cartDetailsInfoList.iterator();
					while (itr2.hasNext()) {

						Object[] obj2 = (Object[]) itr2.next();
						cartDetailsMap.put("id", obj2[0]);
						cartDetailsMap.put("planId", obj2[1]);
						cartDetailsMap.put("planName", obj2[2]);
						cartDetailsMap.put("planType", obj2[3]);
						cartDetailsMap.put("amount", obj2[4]);
						cartDetailsMap.put("validity", obj2[5]);
						cartDetailsMap.put("status", obj2[6]);

					}
					
					main.put("cartDetails", cartDetailsMap);
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
        			
        		}}} else {
        			cartDetailsFormBean.setProfileId(profileId);
            		cartDetailsFormBean.setUserId(userId);
            		cartDetailsFormBean.setPlanId(planId);
            		cartDetailsFormBean.setPlanType("UPGRADE");
            		cartDetailsFormBean.setCreatedBy(userId);

        			Long status = commonMasterService
        					.saveCartDetails(cartDetailsFormBean);
        			
        			if (status != null) {
        				List<CartDetailsFormBean> cartDetailsInfoList = commonMasterService
    	        				.loadCartDetails(userId);
        				
        				LinkedHashMap<String, Object> cartDetailsMap = new LinkedHashMap<String, Object>();
    	        		
    	        		Iterator<?> itr2 = cartDetailsInfoList.iterator();
    					while (itr2.hasNext()) {

    						Object[] obj2 = (Object[]) itr2.next();
    						cartDetailsMap.put("id", obj2[0]);
    						cartDetailsMap.put("planId", obj2[1]);
    						cartDetailsMap.put("planName", obj2[2]);
    						cartDetailsMap.put("planType", obj2[3]);
    						cartDetailsMap.put("amount", obj2[4]);
    						cartDetailsMap.put("validity", obj2[5]);
    						cartDetailsMap.put("status", obj2[6]);

    					}
    					
    					main.put("cartDetails", cartDetailsMap);
    					
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
    					main.put("notInterested", rootMap);
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
        }
        
        List<UpgradePlanFormBean> upgradePlanInfoList = registerService
				.loadUpgradePlanDetails();
				
		LinkedList<LinkedHashMap<String, Object>> upgradePlanList = new LinkedList<LinkedHashMap<String, Object>>();
		
		Iterator<?> itr = upgradePlanInfoList.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("id", obj[11]);
			map.put("planName", obj[0]);
			map.put("amount", obj[1]);
			if(Long.parseLong(obj[2].toString()) > 1 && Long.parseLong(obj[2].toString()) < 11) {
				map.put("validity", obj[2]+" Months");
			} else if(Long.parseLong(obj[2].toString()) > 11 && Long.parseLong(obj[2].toString()) < 23) {
				int months = Integer.valueOf(obj[2].toString());
				int years = months / 12; // 1
				int remainingMonths = months % 12; // 6
				if(remainingMonths == 0) {
					map.put("validity", years+" Year");
				} else if(remainingMonths > 0 && remainingMonths < 2) {
					map.put("validity", years+" Year"+remainingMonths+" Month");
				} else {
					map.put("validity", years+" Year"+remainingMonths+" Months");
				}
			} else if(Long.parseLong(obj[2].toString()) > 23) {
				int months = Integer.valueOf(obj[2].toString());
				int years = months / 12; // 1
				int remainingMonths = months % 12; // 6
				if(remainingMonths == 0) {
					map.put("validity", years+" Years");
				} else if(remainingMonths > 0 && remainingMonths < 2) {
					map.put("validity", years+" Years"+remainingMonths+" Month");
				} else {
					map.put("validity", years+" Years"+remainingMonths+" Months");
				}
			} else {
				map.put("validity", obj[2]+" Month");
			}
			map.put("emailCount", obj[3]);
			map.put("mobileCount", obj[4]);
			map.put("videoCallCount", obj[5]);
			map.put("expressInterest", Boolean.valueOf(obj[6].toString()));
			map.put("profileHighlight", Boolean.valueOf(obj[7].toString()));
			map.put("viewProfile", Boolean.valueOf(obj[8].toString()));
			map.put("protectPhoto", Boolean.valueOf(obj[9].toString()));
			map.put("getSMSAlert", Boolean.valueOf(obj[10].toString()));

			upgradePlanList.add(map);
		}
		
		main.put("upgradePlans", upgradePlanList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(main);
        
        response.setContentType("application/json;charset=utf-8");
        byte[] out = json.getBytes("UTF-8");
        response.setContentLength(out.length);
        response.getOutputStream().write(out);
        
		return;
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
		Long userId = 0L;
		Long profileId = 0L;
		Long myPlanId = 0L;
		String txnId = null;
		String acceptedPromoCode = null;
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		if(request.getParameter("profileId") != null) {
			profileId = Long.parseLong(request.getParameter("profileId").toString());
		}
		
		if(request.getParameter("myPlanId") != null) {
			myPlanId = Long.parseLong(request.getParameter("myPlanId").toString());
		}
		
		if(request.getParameter("txnId") != null) {
			txnId = request.getParameter("txnId").toString();
		}
		
		if(request.getParameter("acceptedPromoCode") != null) {
			acceptedPromoCode = request.getParameter("acceptedPromoCode").toString();
		}
		
        String save = request.getParameter("save");
		
		if(save != null) {
        	if(save.equals("payment") && txnId != null && myPlanId > 0 && userId > 0 && profileId > 0) {
        		try {
        		conn = HibernateUtil.getSessionFactory().openSession();
    			
    			logger.info("-----------Update account details Method--------------");
    			
    			Transaction tx = conn.beginTransaction();

    			@SuppressWarnings("unchecked")
    			List<UpgradePlanFormBean> planDetails = conn.getNamedQuery("getPlanDetailsByPlanId")
    					.setLong("planId", myPlanId).list();
    			
    			Iterator<?> itr = planDetails.iterator();
    			while (itr.hasNext()) {
    				Object[] obj = (Object[]) itr.next();
    		        conn.getNamedQuery("updateAccountDetailsByUserId")
    		        .setLong("accountType", myPlanId)
    		        .setLong("emailCount", Long.parseLong(obj[0].toString()))
    		        .setLong("mobileCount", Long.parseLong(obj[1].toString()))
    		        .setLong("videocallCount", Long.parseLong(obj[2].toString()))
    		        .setString("txnId", txnId)
    		        .setString("txnStatus", CommonConstants.SUCCESS)
    		        .setDate("createdDate", new Date())
    		        .setLong("updatedBy", userId)
    		        .setDate("updatedDate", new Date())
    		        .setLong("userId", userId).executeUpdate();
    		        conn.getNamedQuery("deleteCartDetailsByUserId")
    		        .setLong("userId", userId).executeUpdate();
    		        if(!acceptedPromoCode.equals(null) && !acceptedPromoCode.equals("")) {
    			        conn.getNamedQuery("updateAcceptedPromoDetailsByUserId")
    			        .setString("acceptedPromoCode", acceptedPromoCode)
    			        .setLong("userId", userId)
    			        .setLong("profileId", profileId)
    			        .setDate("acceptedDate", new Date())
    			        .setLong("updatedBy", userId)
    			        .setDate("updatedDate", new Date()).executeUpdate();
    		        }
    		        
    		        tx.commit();
    			}
        		} catch (Exception e) {

        			logger.error("Exception ocured while inserting data into database");
        			rootMap.put("status", "failure");
    				rootMap.put("message", e);
    				main.put("payment", rootMap);
    				Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	            String json = gson.toJson(main);
    	            
    	            response.setContentType("application/json;charset=utf-8");
    	            byte[] out = json.getBytes("UTF-8");
    	            response.setContentLength(out.length);
    	            response.getOutputStream().write(out);
    				return;

        		} finally {
        			conn.flush();
        			conn.close();
        		}
    			
    			rootMap.put("status", "success");
				rootMap.put("message", "Payment Details Inserted Successfully.");
				main.put("payment", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
			
        	} else {
				rootMap.put("status", "failure");
				rootMap.put("message", "Missing Parameters.");
				main.put("payment", rootMap);
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
}
	
