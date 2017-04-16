package com.weddfix.web.mobile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.services.MailBoxService;

public class MessagesServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MailBoxService mailBoxService = new MailBoxService();
	MailBoxFormBean mailBoxDetailsFormBean = new MailBoxFormBean();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		Long profileId = 0L;
		Long userId = 0L;
		Long acceptedUserId = 0L;
		Long notInterestedUserId = 0L;
		
		if(request.getParameter("profileId") != null) {
			profileId = Long.parseLong(request.getParameter("profileId").toString());
		}
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		if(request.getParameter("acceptedUserId") != null) {
			acceptedUserId = Long.parseLong(request.getParameter("acceptedUserId").toString());
		}
		
		if(request.getParameter("notInterestedUserId") != null) {
			notInterestedUserId = Long.parseLong(request.getParameter("notInterestedUserId").toString());
		}
		
        String find = request.getParameter("find");
        
        String save = request.getParameter("save");
        
        if(find != null) {
    	if(find.equals("inbox") && profileId > 0) {
    		List<MyPartnerPreferenceDetailsFormBean> inboxInfoList = mailBoxService
					.loadInboxDetails(profileId);
					
			LinkedList<LinkedHashMap<String, Object>> inboxList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = inboxInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				if(obj[9] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[9]);
				} else {
					map.put("fileName", "");
				}
				if(obj[10] != null) {
					map.put("accepted", obj[10]);
				} else {
					map.put("accepted", false);
				}
				if(obj[11] != null) {
					map.put("notInterested", obj[11]);
				} else {
					map.put("notInterested", false);
				}
				if(obj[12] != null) {
					map.put("showProfilePicture", obj[12]);
				} else {
					map.put("showProfilePicture", false);
				}

				inboxList.add(map);
			}
			
			main.put("inbox", inboxList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
    	}
    	if(find.equals("accepted") && userId > 0) {
    		List<MyPartnerPreferenceDetailsFormBean> acceptedInfoList = mailBoxService
					.loadAcceptedDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> acceptedList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = acceptedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				if(obj[9] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[9]);
				} else {
					map.put("fileName", "");
				}
				if(obj[10] != null) {
					map.put("accepted", obj[10]);
				} else {
					map.put("accepted", false);
				}
				if(obj[11] != null) {
					map.put("notInterested", obj[11]);
				} else {
					map.put("notInterested", false);
				}
				if(obj[12] != null) {
					map.put("showProfilePicture", obj[12]);
				} else {
					map.put("showProfilePicture", false);
				}

				acceptedList.add(map);
			}
			
			main.put("accepted", acceptedList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
    	}
    	if(find.equals("notInterested") && userId > 0) {
    		List<MyPartnerPreferenceDetailsFormBean> notInterestedInfoList = mailBoxService
					.loadNotInterestedDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> notInterestedList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = notInterestedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				if(obj[9] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[9]);
				} else {
					map.put("fileName", "");
				}
				if(obj[10] != null) {
					map.put("accepted", obj[10]);
				} else {
					map.put("accepted", false);
				}
				if(obj[11] != null) {
					map.put("notInterested", obj[11]);
				} else {
					map.put("notInterested", false);
				}
				if(obj[12] != null) {
					map.put("showProfilePicture", obj[12]);
				} else {
					map.put("showProfilePicture", false);
				}

				notInterestedList.add(map);
			}
			
			main.put("notInterested", notInterestedList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
    	}
    	if(find.equals("sent") && userId > 0) {
    		List<MyPartnerPreferenceDetailsFormBean> sentInfoList = mailBoxService
					.loadSentDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> sentList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = sentInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				if(obj[9] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[9]);
				} else {
					map.put("fileName", "");
				}
				if(obj[10] != null) {
					map.put("showProfilePicture", obj[10]);
				} else {
					map.put("showProfilePicture", false);
				}

				sentList.add(map);
			}
			
			main.put("sent", sentList);
			
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
    		if(save.equals("accepted") && profileId > 0 && acceptedUserId > 0) {
    			
    			mailBoxDetailsFormBean.setProfileId(profileId);
    			mailBoxDetailsFormBean.setUserId(acceptedUserId);
    			mailBoxDetailsFormBean.setAccepted(true);
    			mailBoxDetailsFormBean.setUpdatedBy(userId);

    			Long status = mailBoxService
    					.saveAcceptedProfile(mailBoxDetailsFormBean);
    			if (status != null) {
    					rootMap.put("status", "success");
    	    			rootMap.put("message", "Accepted Successfully.");
    					main.put("accepted", rootMap);
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
    					main.put("accepted", rootMap);
    					Gson gson = new GsonBuilder().setPrettyPrinting().create();
    		            String json = gson.toJson(main);
    		            
    		            response.setContentType("application/json;charset=utf-8");
    		            byte[] out = json.getBytes("UTF-8");
    		            response.setContentLength(out.length);
    		            response.getOutputStream().write(out);
    					return;
    				}
    				}
    		
    		if(save.equals("notInterested") && profileId > 0 && notInterestedUserId > 0) {
    			
    			mailBoxDetailsFormBean.setProfileId(profileId);
    			mailBoxDetailsFormBean.setUserId(notInterestedUserId);
    			mailBoxDetailsFormBean.setNotInterested(true);
    			mailBoxDetailsFormBean.setUpdatedBy(userId);

    			Long status = mailBoxService
    					.saveNotInterestedProfile(mailBoxDetailsFormBean);
    			if (status != null) {
    					rootMap.put("status", "success");
    	    			rootMap.put("message", "Not Interested Sent Successfully.");
    					main.put("notInterested", rootMap);
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
	
}
	
