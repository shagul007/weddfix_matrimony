package com.weddfix.web.mobile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.MatchesFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.MatchesService;
import com.weddfix.web.services.RegisterService;

public class MatchesServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long MILLIS_IN_DAY = 60*60*24*1000;
	
	RegisterService registerService = new RegisterService();
	MatchesService matchesService = new MatchesService();
	CommonMasterService commonMasterService = new CommonMasterService();
	ClientInfoService clientInfoService = new ClientInfoService();
	MatchesFormBean matchesDetailsFormBean = new MatchesFormBean();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		Long userId = 0L;
		Long myProfileId = 0L;
		Long shorlistedProfileId = 0L;
		Long unShorlistedProfileId = 0L;
		Long sendInterestProfileId = 0L;
		Long viewProfileId = 0L;
		Long viewUserId = 0L;
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		if(request.getParameter("myProfileId") != null) {
			myProfileId = Long.parseLong(request.getParameter("myProfileId").toString());
		}
		
		if(request.getParameter("shorlistedProfileId") != null) {
			shorlistedProfileId = Long.parseLong(request.getParameter("shorlistedProfileId").toString());
		}
		
		if(request.getParameter("unShorlistedProfileId") != null) {
			unShorlistedProfileId = Long.parseLong(request.getParameter("unShorlistedProfileId").toString());
		}
		
		if(request.getParameter("sendInterestProfileId") != null) {
			sendInterestProfileId = Long.parseLong(request.getParameter("sendInterestProfileId").toString());
		}
		
		if(request.getParameter("viewProfileId") != null) {
			viewProfileId = Long.parseLong(request.getParameter("viewProfileId").toString());
		}
		
		if(request.getParameter("viewUserId") != null) {
			viewUserId = Long.parseLong(request.getParameter("viewUserId").toString());
		}
		
        String find = request.getParameter("find");
        
        String save = request.getParameter("save");
        
        String update = request.getParameter("update");
        
        if(find != null) {
		if(find.equals("newMatches") && userId > 0) {
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails = registerService
					.loadPartnerPreferenceDetails(userId);
					
			List<MyPartnerPreferenceDetailsFormBean> newMatchesInfoList = matchesService
					.loadNewMatchesDetails(myPartnerPreferenceDetails, userId);

			LinkedList<LinkedHashMap<String, Object>> newMatchesList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = newMatchesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("profileFor", obj[3]);
				map.put("gender", obj[4]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[5]));
				map.put("height", obj[6]);
				map.put("religion", obj[7]);
				map.put("maritalStatus", obj[8]);
				map.put("country", obj[9]);
				map.put("education", obj[10]);
				if(obj[11] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[11]);
				} else {
					map.put("fileName", "");
				}
				map.put("aboutMe", obj[12]);
				if(obj[13] != null) {
					map.put("shortlisted", obj[13]);
				} else {
					map.put("shortlisted", false);
				}
				if(obj[14] != null) {
					map.put("sendInterested", obj[14]);
				} else {
					map.put("sendInterested", false);
				}
				if(obj[15] != null) {
					map.put("showProfilePicture", obj[15]);
				} else {
					map.put("showProfilePicture", false);
				}

				newMatchesList.add(map);
			}
			
			main.put("newMatches", newMatchesList);
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find.equals("whoViewedMyProfile") && myProfileId > 0) {
			List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileInfoList = matchesService
					.loadWhoViewedMyProfileDetails(myProfileId);

			LinkedList<LinkedHashMap<String, Object>> whoViewedMyProfileList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = whoViewedMyProfileInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("profileFor", obj[13]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("maritalStatus", obj[14]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				if(obj[9] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[9]);
				} else {
					map.put("fileName", "");
				}
				map.put("aboutMe", obj[15]);
				if(obj[10] != null) {
					map.put("shortlisted", obj[10]);
				} else {
					map.put("shortlisted", false);
				}
				if(obj[11] != null) {
					map.put("sendInterested", obj[11]);
				} else {
					map.put("sendInterested", false);
				}
				if(obj[12] != null) {
					map.put("showProfilePicture", obj[12]);
				} else {
					map.put("showProfilePicture", false);
				}

				whoViewedMyProfileList.add(map);
			}
			
			main.put("whoViewedMyProfile", whoViewedMyProfileList);
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find.equals("viewedAndNotContacted") && userId > 0) {
			List<MyPartnerPreferenceDetailsFormBean> viewedAndNotContactedInfoList = matchesService
					.loadViewedAndNotContactedProfileDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> viewedAndNotContactedList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = viewedAndNotContactedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("profileFor", obj[3]);
				map.put("gender", obj[4]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[5]));
				map.put("height", obj[6]);
				map.put("religion", obj[7]);
				map.put("maritalStatus", obj[8]);
				map.put("country", obj[9]);
				map.put("education", obj[10]);
				if(obj[11] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[11]);
				} else {
					map.put("fileName", "");
				}
				map.put("aboutMe", obj[12]);
				if(obj[13] != null) {
					map.put("shortlisted", obj[13]);
				} else {
					map.put("shortlisted", false);
				}
				if(obj[14] != null) {
					map.put("sendInterested", obj[14]);
				} else {
					map.put("sendInterested", false);
				}
				if(obj[15] != null) {
					map.put("showProfilePicture", obj[15]);
				} else {
					map.put("showProfilePicture", false);
				}

				viewedAndNotContactedList.add(map);
			}
			
			main.put("viewedAndNotContacted", viewedAndNotContactedList);
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find.equals("recentlyViewedProfiles") && userId > 0) {
			List<MyPartnerPreferenceDetailsFormBean> recentlyViewedProfilesInfoList = matchesService
					.loadRecentlyViewedProfileDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> recentlyViewedProfilesList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = recentlyViewedProfilesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("profileFor", obj[3]);
				map.put("gender", obj[4]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[5]));
				map.put("height", obj[6]);
				map.put("religion", obj[7]);
				map.put("maritalStatus", obj[8]);
				map.put("country", obj[9]);
				map.put("education", obj[10]);
				if(obj[11] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[11]);
				} else {
					map.put("fileName", "");
				}
				map.put("aboutMe", obj[12]);
				if(obj[13] != null) {
					map.put("shortlisted", obj[13]);
				} else {
					map.put("shortlisted", false);
				}
				if(obj[14] != null) {
					map.put("sendInterested", obj[14]);
				} else {
					map.put("sendInterested", false);
				}
				if(obj[15] != null) {
					map.put("showProfilePicture", obj[15]);
				} else {
					map.put("showProfilePicture", false);
				}

				recentlyViewedProfilesList.add(map);
			}
			
			main.put("recentlyViewedProfiles", recentlyViewedProfilesList);
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find.equals("shortlistedProfiles") && userId > 0) {
			List<MyPartnerPreferenceDetailsFormBean> shortlistedProfilesInfoList = matchesService
					.loadShortlistedProfilesDetails(userId);
					
			LinkedList<LinkedHashMap<String, Object>> shortlistedProfilesList = new LinkedList<LinkedHashMap<String, Object>>();
			
			Iterator<?> itr = shortlistedProfilesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("profileFor", obj[3]);
				map.put("gender", obj[4]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[5]));
				map.put("height", obj[6]);
				map.put("religion", obj[7]);
				map.put("maritalStatus", obj[8]);
				map.put("country", obj[9]);
				map.put("education", obj[10]);
				if(obj[11] != null) {
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[11]);
				} else {
					map.put("fileName", "");
				}
				map.put("aboutMe", obj[12]);
				if(obj[13] != null) {
					map.put("shortlisted", obj[13]);
				} else {
					map.put("shortlisted", false);
				}
				if(obj[14] != null) {
					map.put("sendInterested", obj[14]);
				} else {
					map.put("sendInterested", false);
				}
				if(obj[15] != null) {
					map.put("showProfilePicture", obj[15]);
				} else {
					map.put("showProfilePicture", false);
				}

				shortlistedProfilesList.add(map);
			}
			
			main.put("shortlistedProfiles", shortlistedProfilesList);
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find.equals("viewProfile") && viewUserId > 0 && viewProfileId > 0 && userId > 0) {
			
			MyPersonalDetailsFormBean viewProfileDetails = registerService
					.loadViewProfileDetails(viewUserId, viewProfileId, userId);
					
			LinkedHashMap<String, Object> viewProfileDetailsMap = new LinkedHashMap<String, Object>();
			
			viewProfileDetailsMap.put("profileUserId", viewUserId);
			viewProfileDetailsMap.put("profileFor",
					viewProfileDetails.getProfileFor());
			viewProfileDetailsMap.put("fullName",
					viewProfileDetails.getFullName());
			viewProfileDetailsMap.put("gender", viewProfileDetails.getGender());
			DecimalFormat format = new DecimalFormat("0.#");
			viewProfileDetailsMap.put("age",
					format.format(viewProfileDetails.getAge()));
			viewProfileDetailsMap.put("dob", viewProfileDetails.getDob());
			viewProfileDetailsMap.put("email", viewProfileDetails.getEmail());
			viewProfileDetailsMap.put("mobile", viewProfileDetails.getMobile());
			viewProfileDetailsMap.put("religion",
					viewProfileDetails.getReligion());
			if(viewProfileDetails.getMotherTongue() != null && viewProfileDetails.getMotherTongue() != "") {
				viewProfileDetailsMap.put("motherTongue", viewProfileDetails.getMotherTongue());
			} else {
				viewProfileDetailsMap.put("motherTongue", "");
			}
			viewProfileDetailsMap.put("profileId",
					viewProfileDetails.getProfileId());
			viewProfileDetailsMap.put("maritalStatus",
					viewProfileDetails.getMaritalStatus());
			if(viewProfileDetails.getCaste() != null && viewProfileDetails.getCaste() != "") {
				viewProfileDetailsMap.put("caste", viewProfileDetails.getCaste());
			} else {
				viewProfileDetailsMap.put("caste", "");
			}
			if(viewProfileDetails.getSubCaste() != null && viewProfileDetails.getSubCaste() != "") {
				viewProfileDetailsMap.put("subCaste", viewProfileDetails.getSubCaste());
			} else {
				viewProfileDetailsMap.put("subCaste", "");
			}
			viewProfileDetailsMap
					.put("country", viewProfileDetails.getCountry());
			viewProfileDetailsMap.put("state", viewProfileDetails.getState());
			viewProfileDetailsMap.put("city", viewProfileDetails.getCity());
			viewProfileDetailsMap.put("height", viewProfileDetails.getHeight());
			if(viewProfileDetails.getWeight() != null && viewProfileDetails.getWeight() != "") {
				viewProfileDetailsMap.put("weight", viewProfileDetails.getWeight());
			} else {
				viewProfileDetailsMap.put("weight", "");
			}
			if(viewProfileDetails.getBodyType() != null && viewProfileDetails.getBodyType() != "") {
				viewProfileDetailsMap.put("bodyType",
						viewProfileDetails.getBodyType());
			} else {
				viewProfileDetailsMap.put("bodyType", "");
			}
			if(viewProfileDetails.getComplexion() != null && viewProfileDetails.getComplexion() != "") {
				viewProfileDetailsMap.put("complexion",
						viewProfileDetails.getComplexion());
			} else {
				viewProfileDetailsMap.put("complexion", "");
			}
			if(viewProfileDetails.getPhysicalStatus() != null && viewProfileDetails.getPhysicalStatus() != "") {
				viewProfileDetailsMap.put("physicalStatus",
						viewProfileDetails.getPhysicalStatus());
			} else {
				viewProfileDetailsMap.put("physicalStatus", "");
			}
			if(viewProfileDetails.getEducation() != null && viewProfileDetails.getEducation() != "") {
				viewProfileDetailsMap.put("education",
						viewProfileDetails.getEducation());
			} else {
				viewProfileDetailsMap.put("education", "");
			}
			if(viewProfileDetails.getOccupation() != null && viewProfileDetails.getOccupation() != "") {
				viewProfileDetailsMap.put("occupation",
						viewProfileDetails.getOccupation());
			} else {
				viewProfileDetailsMap.put("occupation", "");
			}
			if(viewProfileDetails.getEmployedIn() != null && viewProfileDetails.getEmployedIn() != "") {
				viewProfileDetailsMap.put("employedIn",
						viewProfileDetails.getEmployedIn());
			} else {
				viewProfileDetailsMap.put("employedIn", "");
			}
			if(viewProfileDetails.getCurrency() != null && viewProfileDetails.getCurrency() != "") {
				viewProfileDetailsMap.put("currency",
						viewProfileDetails.getCurrency());
			} else {
				viewProfileDetailsMap.put("currency", "");
			}
			if(viewProfileDetails.getMonthlyIncome() != null) {
			if(viewProfileDetails.getMonthlyIncome() > 0) {
				viewProfileDetailsMap.put("monthlyIncome",
						viewProfileDetails.getMonthlyIncome());
			}} else {
				viewProfileDetailsMap.put("monthlyIncome", 0);
			}
			if(viewProfileDetails.getFood() != null && viewProfileDetails.getFood() != "") {
				viewProfileDetailsMap.put("food",
						viewProfileDetails.getFood());
			} else {
				viewProfileDetailsMap.put("food", "");
			}
			if(viewProfileDetails.getSmoking() != null && viewProfileDetails.getSmoking() != "") {
				viewProfileDetailsMap.put("smoking",
						viewProfileDetails.getSmoking());
			} else {
				viewProfileDetailsMap.put("smoking", "");
			}
			if(viewProfileDetails.getDrinking() != null && viewProfileDetails.getDrinking() != "") {
				viewProfileDetailsMap.put("drinking",
						viewProfileDetails.getDrinking());
			} else {
				viewProfileDetailsMap.put("drinking", "");
			}
			if(viewProfileDetails.getFamilyStatus() != null && viewProfileDetails.getFamilyStatus() != "") {
				viewProfileDetailsMap.put("familyStatus",
						viewProfileDetails.getFamilyStatus());
			} else {
				viewProfileDetailsMap.put("familyStatus", "");
			}
			if(viewProfileDetails.getFamilyType() != null && viewProfileDetails.getFamilyType() != "") {
				viewProfileDetailsMap.put("familyType",
						viewProfileDetails.getFamilyType());
			} else {
				viewProfileDetailsMap.put("familyType", "");
			}
			if(viewProfileDetails.getFamilyValues() != null && viewProfileDetails.getFamilyValues() != "") {
				viewProfileDetailsMap.put("familyValues",
						viewProfileDetails.getFamilyValues());
			} else {
				viewProfileDetailsMap.put("familyValues", "");
			}
			if(viewProfileDetails.getFathersStatus() != null && viewProfileDetails.getFathersStatus() != "") {
				viewProfileDetailsMap.put("fathersStatus",
						viewProfileDetails.getFathersStatus());
			} else {
				viewProfileDetailsMap.put("fathersStatus", "");
			}
			if(viewProfileDetails.getMothersStatus() != null && viewProfileDetails.getMothersStatus() != "") {
				viewProfileDetailsMap.put("mothersStatus",
						viewProfileDetails.getMothersStatus());
			} else {
				viewProfileDetailsMap.put("mothersStatus", "");
			}
			if(viewProfileDetails.getNumberOfBrothers() != null && viewProfileDetails.getNumberOfBrothers() != "") {
				viewProfileDetailsMap.put("numberOfBrothers",
						viewProfileDetails.getNumberOfBrothers());
			} else {
				viewProfileDetailsMap.put("numberOfBrothers", "");
			}
			if(viewProfileDetails.getBrothersMarried() != null && viewProfileDetails.getBrothersMarried() != "") {
				viewProfileDetailsMap.put("brothersMarried",
						viewProfileDetails.getBrothersMarried());
			} else {
				viewProfileDetailsMap.put("brothersMarried", "");
			}
			if(viewProfileDetails.getNumberOfSisters() != null && viewProfileDetails.getNumberOfSisters() != "") {
				viewProfileDetailsMap.put("numberOfSisters",
						viewProfileDetails.getNumberOfSisters());
			} else {
				viewProfileDetailsMap.put("numberOfSisters", "");
			}
			if(viewProfileDetails.getSistersMarried() != null && viewProfileDetails.getSistersMarried() != "") {
				viewProfileDetailsMap.put("sistersMarried",
						viewProfileDetails.getSistersMarried());
			} else {
				viewProfileDetailsMap.put("sistersMarried", "");
			}
			viewProfileDetailsMap.put("aboutYou",
					viewProfileDetails.getAboutYou());
			if(viewProfileDetails.getProfilePictureId() != null) {
			if(viewProfileDetails.getProfilePictureId() > 0) {
				viewProfileDetailsMap.put("profilePictureId",
						viewProfileDetails.getProfilePictureId());
			}} else {
				viewProfileDetailsMap.put("profilePictureId", 0);
			}
			if(viewProfileDetails.getAccepted() == true && viewProfileDetails.getAccepted() != null) {
				viewProfileDetailsMap.put("accepted",
						viewProfileDetails.getAccepted());
			} else {
				viewProfileDetailsMap.put("accepted", false);
			}
			if(viewProfileDetails.getContactRequested() == true && viewProfileDetails.getContactRequested() != null) {
				viewProfileDetailsMap.put("contactRequested",
						viewProfileDetails.getContactRequested());
			} else {
				viewProfileDetailsMap.put("contactRequested", false);
			}
			if(viewProfileDetails.getNotInterested() == true && viewProfileDetails.getNotInterested() != null) {
				viewProfileDetailsMap.put("notInterested",
						viewProfileDetails.getNotInterested());
			} else {
				viewProfileDetailsMap.put("notInterested", false);
			}
			if(viewProfileDetails.getEmailCount() != null) {
			if(viewProfileDetails.getEmailCount() > 0) {
				viewProfileDetailsMap.put("emailCount",
						viewProfileDetails.getEmailCount());
			}} else {
				viewProfileDetailsMap.put("emailCount", 0);
			}
			if(viewProfileDetails.getEmailCount() != null) {
			if(viewProfileDetails.getMobileCount() > 0) {
				viewProfileDetailsMap.put("mobileCount",
						viewProfileDetails.getMobileCount());
			}} else {
				viewProfileDetailsMap.put("mobileCount", 0);
			}
			if(viewProfileDetails.getDontshowalreadyViewed() == true && viewProfileDetails.getDontshowalreadyViewed() != null) {
				viewProfileDetailsMap.put("dontshowalreadyViewed",
						viewProfileDetails.getDontshowalreadyViewed());
			} else {
				viewProfileDetailsMap.put("dontshowalreadyViewed", false);
			}

			main.put("viewProfileDetails", viewProfileDetailsMap);
			
			//add profilePictures
            List<PhotoGalleryFormBean> profilePictureInfoList = clientInfoService
					.loadProfilePictures(viewUserId, viewProfileId);
            
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
			
			List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
					.loadMyAccountDetails(userId);
			
			Iterator<?> itrMyAccountDetails = myAccountDetailsInfoList.iterator();
			while (itrMyAccountDetails.hasNext()) {

				Object[] obj = (Object[]) itrMyAccountDetails.next();

				if (obj[1].toString().equalsIgnoreCase("FREE")) {
					DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
					Date cDate = null;
					try {
						cDate = dateFormat.parse(obj[4].toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date createdDate = DateUtils.addMonths(cDate, 3);
					//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial - 1;
		        	main.put("accountValidity", reminingDays);
		        } else {
		        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
		        	//it's still active if the created date + 11 days is greater than the current time
		        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
		        	
		        	int reminingDays =  daysIntoTrial;
		        	main.put("accountValidity", reminingDays);
		        }
			}
			
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
		if(save.equals("shortlist") && userId > 0 && shorlistedProfileId > 0) {
			matchesDetailsFormBean.setProfileId(shorlistedProfileId);
			matchesDetailsFormBean.setUserId(userId);
			matchesDetailsFormBean.setShortlistedProfiles(true);
			matchesDetailsFormBean.setCreatedBy(userId);

			Long status = matchesService
					.saveShortlistedProfiles(matchesDetailsFormBean);
			if (status != null) {
					rootMap.put("status", "success");
	    			rootMap.put("message", "Shortlisted Successfully.");
					main.put("shortlist", rootMap);
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
					main.put("shortlist", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
				}
				
		if(save.equals("unShortlist") && userId > 0 && unShorlistedProfileId > 0) {
			matchesDetailsFormBean.setProfileId(unShorlistedProfileId);
			matchesDetailsFormBean.setUserId(userId);
			matchesDetailsFormBean.setShortlistedProfiles(false);
			matchesDetailsFormBean.setCreatedBy(userId);

			Long status = matchesService
					.saveShortlistedProfiles(matchesDetailsFormBean);
			if (status != null) {
					rootMap.put("status", "success");
	    			rootMap.put("message", "UnShortlisted Successfully.");
					main.put("unShortlist", rootMap);
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
					main.put("unShortlist", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
				}
		
		if(save.equals("sendInterest") && userId > 0 && sendInterestProfileId > 0) {
			matchesDetailsFormBean.setProfileId(sendInterestProfileId);
			matchesDetailsFormBean.setUserId(userId);
			matchesDetailsFormBean.setSendInterested(true);
			matchesDetailsFormBean.setSendMail(true);
			matchesDetailsFormBean.setCreatedBy(userId);

			Long status = matchesService
					.saveSendInterestProfiles(matchesDetailsFormBean);
			if (status != null) {
					rootMap.put("status", "success");
	    			rootMap.put("message", "Interest Sent Successfully.");
					main.put("sendInterest", rootMap);
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
					main.put("sendInterest", rootMap);
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
		
		if(update != null) {
			if(update.equals("emailAndMobileCount") && userId > 0 && viewProfileId > 0) {
				try {
					matchesService.updateEmailAndMobileCount(userId, viewProfileId);
					
					rootMap.put("status", "success");
        			rootMap.put("message", "Updated Email And Mobile Count Successfully.");
					main.put("emailAndMobileCount", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}

				catch (Exception e) {
						rootMap.put("status", "failure");
	        			rootMap.put("message", "Something went wrong. Please try again later.");
						main.put("emailAndMobileCount", rootMap);
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
