package com.weddfix.web.mobile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.controller.RegisterAction;
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.LoginService;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.MailMessage;

public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	LoginFormBean loginFormBean = new LoginFormBean();
    LoginService loginService = new LoginService();
    RegisterService registerService = new RegisterService();
    RegisterAction registerAction = new RegisterAction();
    ClientInfoService clientInfoService = new ClientInfoService();
    CommonMasterService commonMasterService = new CommonMasterService();
    LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
        try {
        	
        	Long userId = 0L;
        	
        	if(request.getParameter("userId") != null) {
    			userId = Long.parseLong(request.getParameter("userId").toString());
    		}
        	
        	String email = request.getParameter("forgotPasswordEmailId");
        	
        	String find = request.getParameter("find");
        	
        	if(email != null) {
        		
        		String userExist = commonMasterService.checkUserAlreadyExist(email.toLowerCase());
        		if (userExist != null) {
        			UserProfileFormBean userProfile = registerService
    						.loadUserProfileByEmail(email);
    				String key = registerAction.startPasswordReset(userProfile.getId());
    				Properties props = new Properties();
    				props.put("fullName", userProfile.getFullName());
    				props.put("email", userProfile.getEmail());
    				props.put("url", rb.getString("url") + "/reset?key=" + key);
    				MailMessage msg = new MailMessage(props, "pwreset.vm",
    						userProfile.getEmail(), "Password reset request");
    				msg.send();
    				
    				rootMap.put("status", "success");
    				rootMap.put("message", "We've sent a password reset link to your email address.");
    				main.put("forgotPassword", rootMap);
    				Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	            String json = gson.toJson(main);
    	            
    	            response.setContentType("application/json;charset=utf-8");
    	            byte[] out = json.getBytes("UTF-8");
    	            response.setContentLength(out.length);
    	            response.getOutputStream().write(out);
    				return;
        		} else {
        			rootMap.put("status", "failure");
        			rootMap.put("message", "User does not exist.");
        			main.put("forgotPassword", rootMap);
    				Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	            String json = gson.toJson(main);
    	            
    	            response.setContentType("application/json;charset=utf-8");
    	            byte[] out = json.getBytes("UTF-8");
    	            response.setContentLength(out.length);
    	            response.getOutputStream().write(out);
    				return;
        		}
        	}
        		
        	if(find.equals("myHome") && userId > 0) {

        		MyPersonalDetailsFormBean myPersonalDetails = registerService
    					.loadPersonalDetails(userId);
                
                //add MyPersonalDetails
                LinkedHashMap<String, Object> myPersonalDetailsMap = new LinkedHashMap<String, Object>();
                myPersonalDetailsMap.put("profileFor",
    					myPersonalDetails.getProfileFor());
    			myPersonalDetailsMap.put("fullName",
    					myPersonalDetails.getFullName());
    			myPersonalDetailsMap.put("gender", myPersonalDetails.getGender());
    			DecimalFormat format = new DecimalFormat("0.#");
    			myPersonalDetailsMap.put("age",
    					format.format(myPersonalDetails.getAge()));
    			myPersonalDetailsMap.put("dob", myPersonalDetails.getDob());
    			myPersonalDetailsMap.put("email", myPersonalDetails.getEmail());
    			myPersonalDetailsMap.put("mobile", myPersonalDetails.getMobile());
    			myPersonalDetailsMap.put("religion",
    					myPersonalDetails.getReligion());
    			if(myPersonalDetails.getMotherTongue() != null && myPersonalDetails.getMotherTongue() != "") {
    				myPersonalDetailsMap.put("motherTongue", myPersonalDetails.getMotherTongue());
    			} else {
    				myPersonalDetailsMap.put("motherTongue", "");
    			}
    			myPersonalDetailsMap.put("profileId",
    					myPersonalDetails.getProfileId());
    			myPersonalDetailsMap.put("maritalStatus",
    					myPersonalDetails.getMaritalStatus());
    			if(myPersonalDetails.getCaste() != null && myPersonalDetails.getCaste() != "") {
    				myPersonalDetailsMap.put("caste", myPersonalDetails.getCaste());
    			} else {
    				myPersonalDetailsMap.put("caste", "");
    			}
    			if(myPersonalDetails.getSubCaste() != null && myPersonalDetails.getSubCaste() != "") {
    				myPersonalDetailsMap.put("subCaste", myPersonalDetails.getSubCaste());
    			} else {
    				myPersonalDetailsMap.put("subCaste", "");
    			}
    			myPersonalDetailsMap
    					.put("country", myPersonalDetails.getCountry());
    			myPersonalDetailsMap.put("state", myPersonalDetails.getState());
    			myPersonalDetailsMap.put("city", myPersonalDetails.getCity());
    			myPersonalDetailsMap.put("height", myPersonalDetails.getHeight());
    			if(myPersonalDetails.getWeight() != null && myPersonalDetails.getWeight() != "") {
    				myPersonalDetailsMap.put("weight", myPersonalDetails.getWeight());
    			} else {
    				myPersonalDetailsMap.put("weight", "");
    			}
    			if(myPersonalDetails.getBodyType() != null && myPersonalDetails.getBodyType() != "") {
    				myPersonalDetailsMap.put("bodyType",
    						myPersonalDetails.getBodyType());
    			} else {
    				myPersonalDetailsMap.put("bodyType", "");
    			}
    			if(myPersonalDetails.getComplexion() != null && myPersonalDetails.getComplexion() != "") {
    				myPersonalDetailsMap.put("complexion",
    						myPersonalDetails.getComplexion());
    			} else {
    				myPersonalDetailsMap.put("complexion", "");
    			}
    			if(myPersonalDetails.getPhysicalStatus() != null && myPersonalDetails.getPhysicalStatus() != "") {
    				myPersonalDetailsMap.put("physicalStatus",
    						myPersonalDetails.getPhysicalStatus());
    			} else {
    				myPersonalDetailsMap.put("physicalStatus", "");
    			}
    			if(myPersonalDetails.getEducation() != null && myPersonalDetails.getEducation() != "") {
    				myPersonalDetailsMap.put("education",
    						myPersonalDetails.getEducation());
    			} else {
    				myPersonalDetailsMap.put("education", "");
    			}
    			if(myPersonalDetails.getOccupation() != null && myPersonalDetails.getOccupation() != "") {
    				myPersonalDetailsMap.put("occupation",
    						myPersonalDetails.getOccupation());
    			} else {
    				myPersonalDetailsMap.put("occupation", "");
    			}
    			if(myPersonalDetails.getEmployedIn() != null && myPersonalDetails.getEmployedIn() != "") {
    				myPersonalDetailsMap.put("employedIn",
    						myPersonalDetails.getEmployedIn());
    			} else {
    				myPersonalDetailsMap.put("employedIn", "");
    			}
    			if(myPersonalDetails.getCurrency() != null && myPersonalDetails.getCurrency() != "") {
    				myPersonalDetailsMap.put("currency",
    						myPersonalDetails.getCurrency());
    			} else {
    				myPersonalDetailsMap.put("currency", "");
    			}
    			if(myPersonalDetails.getMonthlyIncome() != null) {
    			if(myPersonalDetails.getMonthlyIncome() > 0) {
    				myPersonalDetailsMap.put("monthlyIncome",
    						myPersonalDetails.getMonthlyIncome());
    			}} else {
    				myPersonalDetailsMap.put("monthlyIncome", 0);
    			}
    			if(myPersonalDetails.getFood() != null && myPersonalDetails.getFood() != "") {
    				myPersonalDetailsMap.put("food",
    						myPersonalDetails.getFood());
    			} else {
    				myPersonalDetailsMap.put("food", "");
    			}
    			if(myPersonalDetails.getSmoking() != null && myPersonalDetails.getSmoking() != "") {
    				myPersonalDetailsMap.put("smoking",
    						myPersonalDetails.getSmoking());
    			} else {
    				myPersonalDetailsMap.put("smoking", "");
    			}
    			if(myPersonalDetails.getDrinking() != null && myPersonalDetails.getDrinking() != "") {
    				myPersonalDetailsMap.put("drinking",
    						myPersonalDetails.getDrinking());
    			} else {
    				myPersonalDetailsMap.put("drinking", "");
    			}
    			if(myPersonalDetails.getFamilyStatus() != null && myPersonalDetails.getFamilyStatus() != "") {
    				myPersonalDetailsMap.put("familyStatus",
    						myPersonalDetails.getFamilyStatus());
    			} else {
    				myPersonalDetailsMap.put("familyStatus", "");
    			}
    			if(myPersonalDetails.getFamilyType() != null && myPersonalDetails.getFamilyType() != "") {
    				myPersonalDetailsMap.put("familyType",
    						myPersonalDetails.getFamilyType());
    			} else {
    				myPersonalDetailsMap.put("familyType", "");
    			}
    			if(myPersonalDetails.getFamilyValues() != null && myPersonalDetails.getFamilyValues() != "") {
    				myPersonalDetailsMap.put("familyValues",
    						myPersonalDetails.getFamilyValues());
    			} else {
    				myPersonalDetailsMap.put("familyValues", "");
    			}
    			if(myPersonalDetails.getFathersStatus() != null && myPersonalDetails.getFathersStatus() != "") {
    				myPersonalDetailsMap.put("fathersStatus",
    						myPersonalDetails.getFathersStatus());
    			} else {
    				myPersonalDetailsMap.put("fathersStatus", "");
    			}
    			if(myPersonalDetails.getMothersStatus() != null && myPersonalDetails.getMothersStatus() != "") {
    				myPersonalDetailsMap.put("mothersStatus",
    						myPersonalDetails.getMothersStatus());
    			} else {
    				myPersonalDetailsMap.put("mothersStatus", "");
    			}
    			if(myPersonalDetails.getNumberOfBrothers() != null && myPersonalDetails.getNumberOfBrothers() != "") {
    				myPersonalDetailsMap.put("numberOfBrothers",
    						myPersonalDetails.getNumberOfBrothers());
    			} else {
    				myPersonalDetailsMap.put("numberOfBrothers", "");
    			}
    			if(myPersonalDetails.getBrothersMarried() != null && myPersonalDetails.getBrothersMarried() != "") {
    				myPersonalDetailsMap.put("brothersMarried",
    						myPersonalDetails.getBrothersMarried());
    			} else {
    				myPersonalDetailsMap.put("brothersMarried", "");
    			}
    			if(myPersonalDetails.getNumberOfSisters() != null && myPersonalDetails.getNumberOfSisters() != "") {
    				myPersonalDetailsMap.put("numberOfSisters",
    						myPersonalDetails.getNumberOfSisters());
    			} else {
    				myPersonalDetailsMap.put("numberOfSisters", "");
    			}
    			if(myPersonalDetails.getSistersMarried() != null && myPersonalDetails.getSistersMarried() != "") {
    				myPersonalDetailsMap.put("sistersMarried",
    						myPersonalDetails.getSistersMarried());
    			} else {
    				myPersonalDetailsMap.put("sistersMarried", "");
    			}
    			myPersonalDetailsMap.put("aboutYou",
    					myPersonalDetails.getAboutYou());
    			if(myPersonalDetails.getProfilePictureId() != null) {
    			if(myPersonalDetails.getProfilePictureId() > 0) {
    				myPersonalDetailsMap.put("profilePictureId",
    						myPersonalDetails.getProfilePictureId());
    			}} else {
    				myPersonalDetailsMap.put("profilePictureId", 0);
    			}
                
                main.put("myPersonalDetails", myPersonalDetailsMap);
                
              //add MyPartnerPreferenceDetails
                MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails = registerService
                		.loadPartnerPreferenceDetails(userId);
                
                LinkedHashMap<String, Object> myPartnerPreferenceDetailsListMap = new LinkedHashMap<String, Object>();
                
                myPartnerPreferenceDetailsListMap.put("fromAge",
    					myPartnerPreferenceDetails.getFromAge());
    			myPartnerPreferenceDetailsListMap.put("toAge",
    					myPartnerPreferenceDetails.getToAge());
    			myPartnerPreferenceDetailsListMap.put("maritalStatus",
    					myPartnerPreferenceDetails.getMaritalStatus());
    			myPartnerPreferenceDetailsListMap.put("bodyType",
    					myPartnerPreferenceDetails.getBodyType());
    			myPartnerPreferenceDetailsListMap.put("complexion",
    					myPartnerPreferenceDetails.getComplexion());
    			myPartnerPreferenceDetailsListMap.put("fromHeight",
    					myPartnerPreferenceDetails.getFromHeight());
    			myPartnerPreferenceDetailsListMap.put("toHeight",
    					myPartnerPreferenceDetails.getToHeight());
    			myPartnerPreferenceDetailsListMap.put("food",
    					myPartnerPreferenceDetails.getFood());
    			if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getReligion() != "") {
    				myPartnerPreferenceDetailsListMap.put("religion",
    						myPartnerPreferenceDetails.getReligion());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("religion", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getCaste() != null && myPartnerPreferenceDetails.getCaste() != "") {
    				myPartnerPreferenceDetailsListMap.put("caste",
    						myPartnerPreferenceDetails.getCaste());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("caste", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getMotherTongue() != "") {
    				myPartnerPreferenceDetailsListMap.put("motherTongue",
    						myPartnerPreferenceDetails.getMotherTongue());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("motherTongue", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getEducation() != null && myPartnerPreferenceDetails.getEducation() != "") {
    				myPartnerPreferenceDetailsListMap.put("education",
    						myPartnerPreferenceDetails.getEducation());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("education", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getOccupation() != "") {
    				myPartnerPreferenceDetailsListMap.put("occupation",
    						myPartnerPreferenceDetails.getOccupation());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("occupation", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getCountry() != null && myPartnerPreferenceDetails.getCountry() != "") {
    				myPartnerPreferenceDetailsListMap.put("country",
    						myPartnerPreferenceDetails.getCountry());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("country", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getState() != null && myPartnerPreferenceDetails.getState() != "") {
    				myPartnerPreferenceDetailsListMap.put("state",
    						myPartnerPreferenceDetails.getState());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("state", "ANY");
    			}
    			if(myPartnerPreferenceDetails.getCity() != null && myPartnerPreferenceDetails.getCity() != "") {
    				myPartnerPreferenceDetailsListMap.put("city",
    						myPartnerPreferenceDetails.getCity());
    			} else {
    				myPartnerPreferenceDetailsListMap.put("city", "ANY");
    			}
    			myPartnerPreferenceDetailsListMap.put("gender",
    					myPartnerPreferenceDetails.getGender());
    			
                main.put("myPartnerPreferenceDetails", myPartnerPreferenceDetailsListMap);
                
                //add partnerPreference
                List<MyPartnerPreferenceDetailsFormBean> partnerPreferenceInfoList = registerService
    					.loadSimilarPartnerPreferenceDetails(
    							myPartnerPreferenceDetails,
    							userId);

                LinkedList<LinkedHashMap<String, Object>> partnerPreferenceList = new LinkedList<LinkedHashMap<String, Object>>();
                Iterator<?> itr = partnerPreferenceInfoList.iterator();
    			while (itr.hasNext()) {

    				Object[] obj = (Object[]) itr.next();
    				LinkedHashMap<String, Object> partnerPreferenceMap = new LinkedHashMap<String, Object>();
                    partnerPreferenceMap.put("userId", obj[0]);
                    partnerPreferenceMap.put("profileId", obj[1]);
                    partnerPreferenceMap.put("gender", obj[2]);
    				DecimalFormat formater = new DecimalFormat("0.#");
    				partnerPreferenceMap.put("age", formater.format(obj[3]));
    				partnerPreferenceMap.put("height", obj[4]);
    				partnerPreferenceMap.put("religion", obj[5]);
    				if(obj[6] != null) {
    					partnerPreferenceMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[6]);
    				} else {
    					partnerPreferenceMap.put("fileName", "");
    				}
                    partnerPreferenceList.add(partnerPreferenceMap);
                }
    			main.put("partnerPreference", partnerPreferenceList);
                
    			//add whoViewedMyProfile
                List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileInfoList = registerService
    					.loadWhoViewedMyProfileDetails(myPersonalDetails.getProfileId());
                
                LinkedList<LinkedHashMap<String, Object>> whoViewedMyProfileList = new LinkedList<LinkedHashMap<String, Object>>();
                
                Iterator<?> itr1 = whoViewedMyProfileInfoList.iterator();
    			while (itr1.hasNext()) {

    				Object[] obj = (Object[]) itr1.next();
                    LinkedHashMap<String, Object> whoViewedMyProfileMap = new LinkedHashMap<String, Object>();
                    whoViewedMyProfileMap.put("userId", obj[0]);
                    whoViewedMyProfileMap.put("profileId", obj[1]);
                    whoViewedMyProfileMap.put("gender", obj[2]);
                    whoViewedMyProfileMap.put("age", format.format(obj[3]));
                    whoViewedMyProfileMap.put("height", obj[4]);
                    whoViewedMyProfileMap.put("religion", obj[5]);
                    if(obj[6] != null) {
                    	whoViewedMyProfileMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[6]);
    				} else {
    					whoViewedMyProfileMap.put("fileName", "");
    				}
    				whoViewedMyProfileList.add(whoViewedMyProfileMap);
                }
                
    			main.put("whoViewedMyProfile", whoViewedMyProfileList);
    			
    			//add profilePictures
                List<PhotoGalleryFormBean> profilePictureInfoList = clientInfoService
    					.loadProfilePictures(userId,
    							loginFormBean.getProfileId());
                
                LinkedList<LinkedHashMap<String, Object>> profilePictureList = new LinkedList<LinkedHashMap<String, Object>>();
                
                Iterator<?> itr2 = profilePictureInfoList.iterator();
    			while (itr2.hasNext()) {

    				Object[] obj = (Object[]) itr2.next();
                    LinkedHashMap<String, Object> profilePictureMap = new LinkedHashMap<String, Object>();
                    profilePictureMap.put("id", obj[0]);
                    profilePictureMap.put("photoType", obj[1]);
                    if(obj[2] != null) {
                    	profilePictureMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[2]);
    				} else {
    					profilePictureMap.put("fileName", "");
    				}
                    profilePictureMap.put("showMyProfilePicture", obj[3]);
                    profilePictureList.add(profilePictureMap);
                }
    			
    			main.put("profilePictures", profilePictureList);
                
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(main);
                
                response.setContentType("application/json;charset=utf-8");
                byte[] out = json.getBytes("UTF-8");
                response.setContentLength(out.length);
                response.getOutputStream().write(out);
        		
        		
        	} else {
        		rootMap.put("status", "failure");
        		rootMap.put("message", "Missing Parameters.");
        		main.put("forgotPassword", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
        	}
			
        } catch (Exception e) {
        	rootMap.put("status", "failure");
        	rootMap.put("message", e);
			main.put("forgotPassword", rootMap);
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
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
        try {
        	
        	String username = request.getParameter("username");
            String password = request.getParameter("password");
            
           /* Enumeration<String> paramEnumeration= request.getParameterNames();
            StringBuffer buffer = new StringBuffer();
            while(paramEnumeration.hasMoreElements())
            {
                String paramName= paramEnumeration.nextElement();
                String paramValue =request.getParameter(paramName);
                buffer.append(paramName + ":" + paramValue);
                buffer.append("<br/>");
            }*/
            
            if (username == null || username.trim().length() == 0) {
            	rootMap.put("status", "failure");
            	rootMap.put("message", "No Username provided.");
            	main.put("login", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
            }
            
            if (password == null || password.trim().length() == 0) {
            	rootMap.put("status", "failure");
            	rootMap.put("message", "No Password provided.");
            	main.put("login", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
            }
            
            try {
			loginFormBean = loginService.checkLogin(
					username, password);
			
			if(loginFormBean.getStatus().equals(CommonConstants.INACTIVE_STR)) {
				rootMap.put("status", "failure");
				rootMap.put("message", "Your account is currently inactive. Please contact your administrator.");
    			main.put("login", rootMap);
    			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
			} else {
//				return "success";
			}
            } catch (Exception e) {
//    			e.printStackTrace();
            	rootMap.put("status", "failure");
            	rootMap.put("message", "The email or password you entered wrong.");
            	main.put("login", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
    		}
            
            rootMap.put("status", "success");
        	main.put("login", rootMap);
			
            main.put("baseUrl", rb.getString("url"));
            
          //add CommonDetails
            LinkedHashMap<String, Object> commonDetailsMap = new LinkedHashMap<String, Object>();
            
            commonDetailsMap.put("userId", loginFormBean.getId());
            if(loginFormBean.getProfileId() != null) {
            	commonDetailsMap.put("profileId", loginFormBean.getProfileId());
			} else {
				commonDetailsMap.put("profileId", "");
			}
			commonDetailsMap.put("email", loginFormBean.getEmail());
			commonDetailsMap.put("mobile", loginFormBean.getMobile());
			commonDetailsMap.put("genderId", loginFormBean.getGenderId());
			commonDetailsMap.put("role", loginFormBean.getRole());
			commonDetailsMap.put("status", loginFormBean.getStatus());
			commonDetailsMap.put("passwordHash", loginFormBean.getPassword());
			commonDetailsMap.put("userName", loginFormBean.getFullName());
			commonDetailsMap.put("religionId", loginFormBean.getReligionId());
			commonDetailsMap.put("accountType", loginFormBean.getAccountType());
			commonDetailsMap.put("verifyMobileNumber", loginFormBean.getVerifyMobileNumber());
			commonDetailsMap.put("verifyEmailId", loginFormBean.getVerifyEmailId());
			if(loginFormBean.getVerifyedMobileNumber().equals(true)) {
            	commonDetailsMap.put("verifyedMobileNumber", loginFormBean.getVerifyedMobileNumber());
			} else {
				commonDetailsMap.put("verifyedMobileNumber", false);
			}
			if(loginFormBean.getVerifyedEmailId().equals(true)) {
            	commonDetailsMap.put("verifyedEmailId", loginFormBean.getVerifyedEmailId());
			} else {
				commonDetailsMap.put("verifyedEmailId", false);
			}
			commonDetailsMap.put("emailCount", loginFormBean.getEmailCount());
			commonDetailsMap.put("mobileCount", loginFormBean.getMobileCount());
			commonDetailsMap.put("videoCallCount", loginFormBean.getVideoCallCount());
			if(loginFormBean.getPartnerPreferenceId() != null) {
				commonDetailsMap.put("partnerPreferenceId", loginFormBean.getPartnerPreferenceId());
			} else {
				commonDetailsMap.put("partnerPreferenceId", "");
			}
			if(loginFormBean.getMyPlanId() != null) {
				commonDetailsMap.put("myPlanId", loginFormBean.getMyPlanId());
			} else {
				commonDetailsMap.put("myPlanId", "");
			}
			
			main.put("loginDetails", commonDetailsMap);

            MyPersonalDetailsFormBean myPersonalDetails = registerService
					.loadPersonalDetails(loginFormBean.getId());
            
            //add MyPersonalDetails
            LinkedHashMap<String, Object> myPersonalDetailsMap = new LinkedHashMap<String, Object>();
            myPersonalDetailsMap.put("profileFor",
					myPersonalDetails.getProfileFor());
			myPersonalDetailsMap.put("fullName",
					myPersonalDetails.getFullName());
			myPersonalDetailsMap.put("gender", myPersonalDetails.getGender());
			DecimalFormat format = new DecimalFormat("0.#");
			myPersonalDetailsMap.put("age",
					format.format(myPersonalDetails.getAge()));
			myPersonalDetailsMap.put("dob", myPersonalDetails.getDob());
			myPersonalDetailsMap.put("email", myPersonalDetails.getEmail());
			myPersonalDetailsMap.put("mobile", myPersonalDetails.getMobile());
			myPersonalDetailsMap.put("religion",
					myPersonalDetails.getReligion());
			if(myPersonalDetails.getMotherTongue() != null && myPersonalDetails.getMotherTongue() != "") {
				myPersonalDetailsMap.put("motherTongue", myPersonalDetails.getMotherTongue());
			} else {
				myPersonalDetailsMap.put("motherTongue", "");
			}
			myPersonalDetailsMap.put("profileId",
					myPersonalDetails.getProfileId());
			myPersonalDetailsMap.put("maritalStatus",
					myPersonalDetails.getMaritalStatus());
			if(myPersonalDetails.getCaste() != null && myPersonalDetails.getCaste() != "") {
				myPersonalDetailsMap.put("caste", myPersonalDetails.getCaste());
			} else {
				myPersonalDetailsMap.put("caste", "");
			}
			if(myPersonalDetails.getSubCaste() != null && myPersonalDetails.getSubCaste() != "") {
				myPersonalDetailsMap.put("subCaste", myPersonalDetails.getSubCaste());
			} else {
				myPersonalDetailsMap.put("subCaste", "");
			}
			myPersonalDetailsMap
					.put("country", myPersonalDetails.getCountry());
			myPersonalDetailsMap.put("state", myPersonalDetails.getState());
			myPersonalDetailsMap.put("city", myPersonalDetails.getCity());
			myPersonalDetailsMap.put("height", myPersonalDetails.getHeight());
			if(myPersonalDetails.getWeight() != null && myPersonalDetails.getWeight() != "") {
				myPersonalDetailsMap.put("weight", myPersonalDetails.getWeight());
			} else {
				myPersonalDetailsMap.put("weight", "");
			}
			if(myPersonalDetails.getBodyType() != null && myPersonalDetails.getBodyType() != "") {
				myPersonalDetailsMap.put("bodyType",
						myPersonalDetails.getBodyType());
			} else {
				myPersonalDetailsMap.put("bodyType", "");
			}
			if(myPersonalDetails.getComplexion() != null && myPersonalDetails.getComplexion() != "") {
				myPersonalDetailsMap.put("complexion",
						myPersonalDetails.getComplexion());
			} else {
				myPersonalDetailsMap.put("complexion", "");
			}
			if(myPersonalDetails.getPhysicalStatus() != null && myPersonalDetails.getPhysicalStatus() != "") {
				myPersonalDetailsMap.put("physicalStatus",
						myPersonalDetails.getPhysicalStatus());
			} else {
				myPersonalDetailsMap.put("physicalStatus", "");
			}
			if(myPersonalDetails.getEducation() != null && myPersonalDetails.getEducation() != "") {
				myPersonalDetailsMap.put("education",
						myPersonalDetails.getEducation());
			} else {
				myPersonalDetailsMap.put("education", "");
			}
			if(myPersonalDetails.getOccupation() != null && myPersonalDetails.getOccupation() != "") {
				myPersonalDetailsMap.put("occupation",
						myPersonalDetails.getOccupation());
			} else {
				myPersonalDetailsMap.put("occupation", "");
			}
			if(myPersonalDetails.getEmployedIn() != null && myPersonalDetails.getEmployedIn() != "") {
				myPersonalDetailsMap.put("employedIn",
						myPersonalDetails.getEmployedIn());
			} else {
				myPersonalDetailsMap.put("employedIn", "");
			}
			if(myPersonalDetails.getCurrency() != null && myPersonalDetails.getCurrency() != "") {
				myPersonalDetailsMap.put("currency",
						myPersonalDetails.getCurrency());
			} else {
				myPersonalDetailsMap.put("currency", "");
			}
			if(myPersonalDetails.getMonthlyIncome() != null) {
			if(myPersonalDetails.getMonthlyIncome() > 0) {
				myPersonalDetailsMap.put("monthlyIncome",
						myPersonalDetails.getMonthlyIncome());
			}} else {
				myPersonalDetailsMap.put("monthlyIncome", 0);
			}
			if(myPersonalDetails.getFood() != null && myPersonalDetails.getFood() != "") {
				myPersonalDetailsMap.put("food",
						myPersonalDetails.getFood());
			} else {
				myPersonalDetailsMap.put("food", "");
			}
			if(myPersonalDetails.getSmoking() != null && myPersonalDetails.getSmoking() != "") {
				myPersonalDetailsMap.put("smoking",
						myPersonalDetails.getSmoking());
			} else {
				myPersonalDetailsMap.put("smoking", "");
			}
			if(myPersonalDetails.getDrinking() != null && myPersonalDetails.getDrinking() != "") {
				myPersonalDetailsMap.put("drinking",
						myPersonalDetails.getDrinking());
			} else {
				myPersonalDetailsMap.put("drinking", "");
			}
			if(myPersonalDetails.getFamilyStatus() != null && myPersonalDetails.getFamilyStatus() != "") {
				myPersonalDetailsMap.put("familyStatus",
						myPersonalDetails.getFamilyStatus());
			} else {
				myPersonalDetailsMap.put("familyStatus", "");
			}
			if(myPersonalDetails.getFamilyType() != null && myPersonalDetails.getFamilyType() != "") {
				myPersonalDetailsMap.put("familyType",
						myPersonalDetails.getFamilyType());
			} else {
				myPersonalDetailsMap.put("familyType", "");
			}
			if(myPersonalDetails.getFamilyValues() != null && myPersonalDetails.getFamilyValues() != "") {
				myPersonalDetailsMap.put("familyValues",
						myPersonalDetails.getFamilyValues());
			} else {
				myPersonalDetailsMap.put("familyValues", "");
			}
			if(myPersonalDetails.getFathersStatus() != null && myPersonalDetails.getFathersStatus() != "") {
				myPersonalDetailsMap.put("fathersStatus",
						myPersonalDetails.getFathersStatus());
			} else {
				myPersonalDetailsMap.put("fathersStatus", "");
			}
			if(myPersonalDetails.getMothersStatus() != null && myPersonalDetails.getMothersStatus() != "") {
				myPersonalDetailsMap.put("mothersStatus",
						myPersonalDetails.getMothersStatus());
			} else {
				myPersonalDetailsMap.put("mothersStatus", "");
			}
			if(myPersonalDetails.getNumberOfBrothers() != null && myPersonalDetails.getNumberOfBrothers() != "") {
				myPersonalDetailsMap.put("numberOfBrothers",
						myPersonalDetails.getNumberOfBrothers());
			} else {
				myPersonalDetailsMap.put("numberOfBrothers", "");
			}
			if(myPersonalDetails.getBrothersMarried() != null && myPersonalDetails.getBrothersMarried() != "") {
				myPersonalDetailsMap.put("brothersMarried",
						myPersonalDetails.getBrothersMarried());
			} else {
				myPersonalDetailsMap.put("brothersMarried", "");
			}
			if(myPersonalDetails.getNumberOfSisters() != null && myPersonalDetails.getNumberOfSisters() != "") {
				myPersonalDetailsMap.put("numberOfSisters",
						myPersonalDetails.getNumberOfSisters());
			} else {
				myPersonalDetailsMap.put("numberOfSisters", "");
			}
			if(myPersonalDetails.getSistersMarried() != null && myPersonalDetails.getSistersMarried() != "") {
				myPersonalDetailsMap.put("sistersMarried",
						myPersonalDetails.getSistersMarried());
			} else {
				myPersonalDetailsMap.put("sistersMarried", "");
			}
			myPersonalDetailsMap.put("aboutYou",
					myPersonalDetails.getAboutYou());
			if(myPersonalDetails.getProfilePictureId() != null) {
			if(myPersonalDetails.getProfilePictureId() > 0) {
				myPersonalDetailsMap.put("profilePictureId",
						myPersonalDetails.getProfilePictureId());
			}} else {
				myPersonalDetailsMap.put("profilePictureId", 0);
			}
            
            main.put("myPersonalDetails", myPersonalDetailsMap);
            
          //add MyPartnerPreferenceDetails
            MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails = registerService
            		.loadPartnerPreferenceDetails(loginFormBean.getId());
            
            LinkedHashMap<String, Object> myPartnerPreferenceDetailsListMap = new LinkedHashMap<String, Object>();
            
            myPartnerPreferenceDetailsListMap.put("fromAge",
					myPartnerPreferenceDetails.getFromAge());
			myPartnerPreferenceDetailsListMap.put("toAge",
					myPartnerPreferenceDetails.getToAge());
			myPartnerPreferenceDetailsListMap.put("maritalStatus",
					myPartnerPreferenceDetails.getMaritalStatus());
			myPartnerPreferenceDetailsListMap.put("bodyType",
					myPartnerPreferenceDetails.getBodyType());
			myPartnerPreferenceDetailsListMap.put("complexion",
					myPartnerPreferenceDetails.getComplexion());
			myPartnerPreferenceDetailsListMap.put("fromHeight",
					myPartnerPreferenceDetails.getFromHeight());
			myPartnerPreferenceDetailsListMap.put("toHeight",
					myPartnerPreferenceDetails.getToHeight());
			myPartnerPreferenceDetailsListMap.put("food",
					myPartnerPreferenceDetails.getFood());
			if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getReligion() != "") {
				myPartnerPreferenceDetailsListMap.put("religion",
						myPartnerPreferenceDetails.getReligion());
			} else {
				myPartnerPreferenceDetailsListMap.put("religion", "ANY");
			}
			if(myPartnerPreferenceDetails.getCaste() != null && myPartnerPreferenceDetails.getCaste() != "") {
				myPartnerPreferenceDetailsListMap.put("caste",
						myPartnerPreferenceDetails.getCaste());
			} else {
				myPartnerPreferenceDetailsListMap.put("caste", "ANY");
			}
			if(myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getMotherTongue() != "") {
				myPartnerPreferenceDetailsListMap.put("motherTongue",
						myPartnerPreferenceDetails.getMotherTongue());
			} else {
				myPartnerPreferenceDetailsListMap.put("motherTongue", "ANY");
			}
			if(myPartnerPreferenceDetails.getEducation() != null && myPartnerPreferenceDetails.getEducation() != "") {
				myPartnerPreferenceDetailsListMap.put("education",
						myPartnerPreferenceDetails.getEducation());
			} else {
				myPartnerPreferenceDetailsListMap.put("education", "ANY");
			}
			if(myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getOccupation() != "") {
				myPartnerPreferenceDetailsListMap.put("occupation",
						myPartnerPreferenceDetails.getOccupation());
			} else {
				myPartnerPreferenceDetailsListMap.put("occupation", "ANY");
			}
			if(myPartnerPreferenceDetails.getCountry() != null && myPartnerPreferenceDetails.getCountry() != "") {
				myPartnerPreferenceDetailsListMap.put("country",
						myPartnerPreferenceDetails.getCountry());
			} else {
				myPartnerPreferenceDetailsListMap.put("country", "ANY");
			}
			if(myPartnerPreferenceDetails.getState() != null && myPartnerPreferenceDetails.getState() != "") {
				myPartnerPreferenceDetailsListMap.put("state",
						myPartnerPreferenceDetails.getState());
			} else {
				myPartnerPreferenceDetailsListMap.put("state", "ANY");
			}
			if(myPartnerPreferenceDetails.getCity() != null && myPartnerPreferenceDetails.getCity() != "") {
				myPartnerPreferenceDetailsListMap.put("city",
						myPartnerPreferenceDetails.getCity());
			} else {
				myPartnerPreferenceDetailsListMap.put("city", "ANY");
			}
			myPartnerPreferenceDetailsListMap.put("gender",
					myPartnerPreferenceDetails.getGender());
			
            main.put("myPartnerPreferenceDetails", myPartnerPreferenceDetailsListMap);
            
            //add partnerPreference
            List<MyPartnerPreferenceDetailsFormBean> partnerPreferenceInfoList = registerService
					.loadSimilarPartnerPreferenceDetails(
							myPartnerPreferenceDetails,
							loginFormBean.getId());

            LinkedList<LinkedHashMap<String, Object>> partnerPreferenceList = new LinkedList<LinkedHashMap<String, Object>>();
            Iterator<?> itr = partnerPreferenceInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> partnerPreferenceMap = new LinkedHashMap<String, Object>();
                partnerPreferenceMap.put("userId", obj[0]);
                partnerPreferenceMap.put("profileId", obj[1]);
                partnerPreferenceMap.put("gender", obj[2]);
				DecimalFormat formater = new DecimalFormat("0.#");
				partnerPreferenceMap.put("age", formater.format(obj[3]));
				partnerPreferenceMap.put("height", obj[4]);
				partnerPreferenceMap.put("religion", obj[5]);
				if(obj[6] != null) {
					partnerPreferenceMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[6]);
				} else {
					partnerPreferenceMap.put("fileName", "");
				}
                partnerPreferenceList.add(partnerPreferenceMap);
            }
			main.put("partnerPreference", partnerPreferenceList);
            
			//add whoViewedMyProfile
            List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileInfoList = registerService
					.loadWhoViewedMyProfileDetails(myPersonalDetails.getProfileId());
            
            LinkedList<LinkedHashMap<String, Object>> whoViewedMyProfileList = new LinkedList<LinkedHashMap<String, Object>>();
            
            Iterator<?> itr1 = whoViewedMyProfileInfoList.iterator();
			while (itr1.hasNext()) {

				Object[] obj = (Object[]) itr1.next();
                LinkedHashMap<String, Object> whoViewedMyProfileMap = new LinkedHashMap<String, Object>();
                whoViewedMyProfileMap.put("userId", obj[0]);
                whoViewedMyProfileMap.put("profileId", obj[1]);
                whoViewedMyProfileMap.put("gender", obj[2]);
                whoViewedMyProfileMap.put("age", format.format(obj[3]));
                whoViewedMyProfileMap.put("height", obj[4]);
                whoViewedMyProfileMap.put("religion", obj[5]);
                if(obj[6] != null) {
                	whoViewedMyProfileMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[6]);
				} else {
					whoViewedMyProfileMap.put("fileName", "");
				}
				whoViewedMyProfileList.add(whoViewedMyProfileMap);
            }
            
			main.put("whoViewedMyProfile", whoViewedMyProfileList);
			
			//add profilePictures
            List<PhotoGalleryFormBean> profilePictureInfoList = clientInfoService
					.loadProfilePictures(loginFormBean.getId(),
							loginFormBean.getProfileId());
            
            LinkedList<LinkedHashMap<String, Object>> profilePictureList = new LinkedList<LinkedHashMap<String, Object>>();
            
            Iterator<?> itr2 = profilePictureInfoList.iterator();
			while (itr2.hasNext()) {

				Object[] obj = (Object[]) itr2.next();
                LinkedHashMap<String, Object> profilePictureMap = new LinkedHashMap<String, Object>();
                profilePictureMap.put("id", obj[0]);
                profilePictureMap.put("photoType", obj[1]);
                if(obj[2] != null) {
                	profilePictureMap.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[2]);
				} else {
					profilePictureMap.put("fileName", "");
				}
                profilePictureMap.put("showMyProfilePicture", obj[3]);
                profilePictureList.add(profilePictureMap);
            }
			
			main.put("profilePictures", profilePictureList);
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
        
        

        }
        catch (Exception e) {
        	rootMap.put("status", "failure");
        	rootMap.put("message", e);
        	main.put("login", rootMap);
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
