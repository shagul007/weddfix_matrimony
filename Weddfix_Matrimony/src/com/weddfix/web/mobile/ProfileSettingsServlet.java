package com.weddfix.web.mobile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.AccountDetailsFormBean;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.LoginService;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.CommonConstants;

public class ProfileSettingsServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long MILLIS_IN_DAY = 60*60*24*1000;
	
	CommentsFormBean commentsFormBean = new CommentsFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();
	ClientInfoService clientInfoService = new ClientInfoService();
	AccountDetailsFormBean accountDetailsFormBean = new AccountDetailsFormBean();
	RegisterService registerService = new RegisterService();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		Long userId = 0L;
		String password = null;
		String email = null;
		Long profileId = 0L;
		String profilePicHide = null;
		String deleteProfileReason = null;
		Long countryId = 0L;
		Long stateId = 0L;
		Long religionId = 0L;
		Long deactivateProfileDays = 0L;
		
		if(request.getParameter("deactivateProfileDays") != null) {
			deactivateProfileDays = Long.parseLong(request.getParameter("deactivateProfileDays").toString());
		}
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		if(request.getParameter("password") != null) {
			password = request.getParameter("password").toString();
		}
		
		if(request.getParameter("deleteProfileReason") != null) {
			deleteProfileReason = request.getParameter("deleteProfileReason").toString();
		}
		
		if(request.getParameter("email") != null) {
			email = request.getParameter("email").toString();
		}
		
		if(request.getParameter("profileId") != null) {
			profileId = Long.parseLong(request.getParameter("profileId").toString());
		}
		
		if(request.getParameter("profilePicHide") != null) {
			profilePicHide = request.getParameter("profilePicHide").toString();
		}
		
		if(request.getParameter("country_Id") != null) {
			countryId = Long.parseLong(request.getParameter("country_Id").toString());
		}
		
		if(request.getParameter("state_Id") != null) {
			stateId = Long.parseLong(request.getParameter("state_Id").toString());
		}
		
		if(request.getParameter("religion_Id") != null) {
			religionId = Long.parseLong(request.getParameter("religion_Id").toString());
		}
		
		String find = request.getParameter("find");
		String update = request.getParameter("update");
		
		if(countryId > 0) {
			Map<Object, Object> stateMap = commonMasterService.loadState(countryId);
			
			LinkedList<LinkedHashMap<String, Object>> stateMapList = new LinkedList<LinkedHashMap<String, Object>>();
            Iterator<?> itrStateMap = stateMap.entrySet().iterator();
            while (itrStateMap.hasNext()) {
                @SuppressWarnings({ "rawtypes" })
				Map.Entry pair = (Map.Entry)itrStateMap.next();
				LinkedHashMap<String, Object> statesMap = new LinkedHashMap<String, Object>();
				statesMap.put("id", pair.getKey());
				statesMap.put("stateName", pair.getValue());
				stateMapList.add(statesMap);
				itrStateMap.remove(); // avoids a ConcurrentModificationException
            }
            
			main.put("state", stateMapList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
	
		if(stateId > 0) {
			Map<Object, Object> cityMap = commonMasterService.loadCity(stateId);
			
			LinkedList<LinkedHashMap<String, Object>> cityMapList = new LinkedList<LinkedHashMap<String, Object>>();
            Iterator<?> itrCityMap = cityMap.entrySet().iterator();
            while (itrCityMap.hasNext()) {
                @SuppressWarnings({ "rawtypes" })
				Map.Entry pair = (Map.Entry)itrCityMap.next();
				LinkedHashMap<String, Object> citysMap = new LinkedHashMap<String, Object>();
				citysMap.put("id", pair.getKey());
				citysMap.put("cityName", pair.getValue());
				cityMapList.add(citysMap);
				itrCityMap.remove(); // avoids a ConcurrentModificationException
            }
            
			main.put("city", cityMapList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(religionId > 0) {
			Map<Object, Object> casteMap = commonMasterService.loadCaste(religionId);
			
			LinkedList<LinkedHashMap<String, Object>> casteMapList = new LinkedList<LinkedHashMap<String, Object>>();
            Iterator<?> itrCasteMap = casteMap.entrySet().iterator();
            while (itrCasteMap.hasNext()) {
                @SuppressWarnings({ "rawtypes" })
				Map.Entry pair = (Map.Entry)itrCasteMap.next();
				LinkedHashMap<String, Object> castesMap = new LinkedHashMap<String, Object>();
				castesMap.put("id", pair.getKey());
				castesMap.put("casteName", pair.getValue());
				casteMapList.add(castesMap);
				itrCasteMap.remove(); // avoids a ConcurrentModificationException
            }
            
			main.put("caste", casteMapList);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
            
			return;
		}
		
		if(find != null) {
			if(find.equals("accountDetails") && userId > 0) {
				
				List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
						.loadMyAccountDetails(userId);
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				Iterator<?> itr = myAccountDetailsInfoList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					map.put("id", obj[0]);
					map.put("planName", obj[1]);
					map.put("amount", obj[2]);


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
			        	map.put("validity", reminingDays);
			        } else {
			        	DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
						Date cDate = null;
						try {
							cDate = dateFormat.parse(obj[4].toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
			        	Date createdDate = DateUtils.addMonths(cDate, Integer.valueOf(obj[3].toString()));
			        	//it's still active if the created date + 11 days is greater than the current time
			        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
			        	
			        	int reminingDays =  daysIntoTrial;
			        	map.put("validity", reminingDays);
			        }
			        
					map.put("createdDate", obj[4]);
					map.put("emailCount", obj[5]);
					map.put("mobileCount", obj[6]);
					map.put("videocallCount", obj[7]);
					map.put("expressInterest", obj[8]);
					map.put("profileHighlight", obj[9]);
					map.put("viewProfile", obj[10]);
					map.put("protectPhoto", obj[11]);
					map.put("smsAlert", obj[12]);
				}
				
				main.put("accountDetails", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("changePassword") && userId > 0) {
				
				LoginService loginService = new LoginService();
				LoginFormBean loginFormBean = loginService
						.sessionPasswordHash(userId);

		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				map.put("password", loginFormBean.getPassword());
				
				main.put("changePassword", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("profilePicture") && userId > 0 && profileId > 0) {
				
				List<PhotoGalleryFormBean> photoGalleryInfoList = clientInfoService
						.loadProfilePictures(userId, profileId);
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				Iterator<?> itr = photoGalleryInfoList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					map.put("id", obj[0]);
					map.put("photoType", obj[1]);
					map.put("fileName", rb.getString("url")+"/ImageAction.action?imageId="+obj[2]);

				}
				
				main.put("profilePicture", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("privacySettings") && userId > 0) {
				
				List<UpgradePlanFormBean> myAccountDetailsInfoList = commonMasterService
						.loadMyAccountDetails(userId);
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				Iterator<?> itr = myAccountDetailsInfoList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					map.put("id", obj[0]);
					map.put("planName", obj[1]);
					map.put("amount", obj[2]);


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
			        	map.put("validity", reminingDays);
			        } else {
			        	DateFormat dateFormat= new SimpleDateFormat("dd MM yyyy");
						Date cDate = null;
						try {
							cDate = dateFormat.parse(obj[4].toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
			        	Date createdDate = DateUtils.addMonths(cDate, Integer.valueOf(obj[3].toString()));
			        	//it's still active if the created date + 11 days is greater than the current time
			        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
			        	
			        	int reminingDays =  daysIntoTrial;
			        	map.put("validity", reminingDays);
			        }
			        
					map.put("createdDate", obj[4]);
					map.put("emailCount", obj[5]);
					map.put("mobileCount", obj[6]);
					map.put("videocallCount", obj[7]);
					map.put("expressInterest", obj[8]);
					map.put("profileHighlight", obj[9]);
					map.put("viewProfile", obj[10]);
					map.put("protectPhoto", obj[11]);
					map.put("smsAlert", obj[12]);
				}
				
				main.put("privacySettings", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("myProfileDetails") && userId > 0) {
				
				UserProfileFormBean userProfile = registerService
						.loadUserProfileDetails(userId);
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				map.put("id", userProfile.getId());
				map.put("fullName", userProfile.getFullName());
				map.put("dob", userProfile.getDobStr());
				map.put("email", userProfile.getEmail());
				map.put("mobile", userProfile.getMobile());
				map.put("profileForId", userProfile.getUserProfileId());
				map.put("genderId", userProfile.getGenderId());
				map.put("maritalStatusId", userProfile.getMaritalStatusId());
				map.put("heightId", userProfile.getHeightId());
				map.put("educationId", userProfile.getEducationId());
				map.put("occupationId", userProfile.getOccupationId());
				map.put("religionId", userProfile.getReligionId());
				map.put("cityId", userProfile.getCityId());
				map.put("stateId", userProfile.getStateId());
				map.put("countryId", userProfile.getCountryId());
				map.put("aboutYou", userProfile.getAboutYou());
				
				main.put("myProfileDetails", map);
				
				Map<Object, Object> profileMap = commonMasterService.loadProfile();
	            
	            LinkedList<LinkedHashMap<String, Object>> profileMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrProfileMap = profileMap.entrySet().iterator();
	            while (itrProfileMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrProfileMap.next();
					LinkedHashMap<String, Object> profilesMap = new LinkedHashMap<String, Object>();
					profilesMap.put("id", pair.getKey());
					profilesMap.put("profileFor", pair.getValue());
					profileMapList.add(profilesMap);
	                itrProfileMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("profile", profileMapList);
	            
				
	            Map<Object, Object> genderMap = commonMasterService.loadGender();
	            
	            LinkedList<LinkedHashMap<String, Object>> genderMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrGenderMap = genderMap.entrySet().iterator();
	            while (itrGenderMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrGenderMap.next();
					LinkedHashMap<String, Object> gendersMap = new LinkedHashMap<String, Object>();
					gendersMap.put("id", pair.getKey());
					gendersMap.put("genderName", pair.getValue());
					genderMapList.add(gendersMap);
					itrGenderMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("gender", genderMapList);
	            
	            Map<Object, Object> maritalStatusMap = commonMasterService.loadMaritalStatus();
	            
	            LinkedList<LinkedHashMap<String, Object>> maritalStatusMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrMaritalStatusMap = maritalStatusMap.entrySet().iterator();
	            while (itrMaritalStatusMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrMaritalStatusMap.next();
					LinkedHashMap<String, Object> maritalStatusMaps = new LinkedHashMap<String, Object>();
					maritalStatusMaps.put("id", pair.getKey());
					maritalStatusMaps.put("maritalStatusName", pair.getValue());
					maritalStatusMapList.add(maritalStatusMaps);
					itrMaritalStatusMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("maritalStatus", maritalStatusMapList);
	            
	            Map<Object, Object> heightMap = commonMasterService.loadHeight();
	            
	            LinkedList<LinkedHashMap<String, Object>> heightMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrHeightMap = heightMap.entrySet().iterator();
	            while (itrHeightMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrHeightMap.next();
					LinkedHashMap<String, Object> heightsMap = new LinkedHashMap<String, Object>();
					heightsMap.put("id", pair.getKey());
					heightsMap.put("feetInches", pair.getValue());
					heightMapList.add(heightsMap);
					itrHeightMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("height", heightMapList);
	            
	            Map<Object, Object> educationMap = commonMasterService.loadEducation();
	            
	            LinkedList<LinkedHashMap<String, Object>> educationMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrEducationMap = educationMap.entrySet().iterator();
	            while (itrEducationMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrEducationMap.next();
					LinkedHashMap<String, Object> educationsMap = new LinkedHashMap<String, Object>();
					educationsMap.put("id", pair.getKey());
					educationsMap.put("educationName", pair.getValue());
					educationMapList.add(educationsMap);
					itrEducationMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("education", educationMapList);
	            
	            Map<Object, Object> religionMap = commonMasterService.loadReligion();
	            
	            LinkedList<LinkedHashMap<String, Object>> religionMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrReligionMap = religionMap.entrySet().iterator();
	            while (itrReligionMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrReligionMap.next();
					LinkedHashMap<String, Object> religionsMap = new LinkedHashMap<String, Object>();
					religionsMap.put("id", pair.getKey());
					religionsMap.put("religionName", pair.getValue());
					religionMapList.add(religionsMap);
					itrReligionMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("religion", religionMapList);
	            
	            Map<Object, Object> occupationMap = commonMasterService.loadOccupation();
	            
	            LinkedList<LinkedHashMap<String, Object>> occupationMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrOccupationMap = occupationMap.entrySet().iterator();
	            while (itrOccupationMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrOccupationMap.next();
					LinkedHashMap<String, Object> occupationsMap = new LinkedHashMap<String, Object>();
					occupationsMap.put("id", pair.getKey());
					occupationsMap.put("occupationName", pair.getValue());
					occupationMapList.add(occupationsMap);
					itrOccupationMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("occupation", occupationMapList);
	            
	            Map<Object, Object> countryMap = commonMasterService.loadCountry();
	            
	            LinkedList<LinkedHashMap<String, Object>> countryMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrCountryMap = countryMap.entrySet().iterator();
	            while (itrCountryMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrCountryMap.next();
					LinkedHashMap<String, Object> countrysMap = new LinkedHashMap<String, Object>();
					countrysMap.put("id", pair.getKey());
					countrysMap.put("countryName", pair.getValue());
					countryMapList.add(countrysMap);
					itrCountryMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("country", countryMapList);
	            
	            Map<Object, Object> stateMap = commonMasterService.loadState(userProfile.getCountryId());
				
				LinkedList<LinkedHashMap<String, Object>> stateMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrStateMap = stateMap.entrySet().iterator();
	            while (itrStateMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrStateMap.next();
					LinkedHashMap<String, Object> statesMap = new LinkedHashMap<String, Object>();
					statesMap.put("id", pair.getKey());
					statesMap.put("stateName", pair.getValue());
					stateMapList.add(statesMap);
					itrStateMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
				main.put("state", stateMapList);
				
	            Map<Object, Object> cityMap = commonMasterService.loadCity(userProfile.getStateId());
				
				LinkedList<LinkedHashMap<String, Object>> cityMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrCityMap = cityMap.entrySet().iterator();
	            while (itrCityMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrCityMap.next();
					LinkedHashMap<String, Object> citysMap = new LinkedHashMap<String, Object>();
					citysMap.put("id", pair.getKey());
					citysMap.put("cityName", pair.getValue());
					cityMapList.add(citysMap);
					itrCityMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
				main.put("city", cityMapList);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("myPartnerPreferenceDetails") && userId > 0) {
				
				PartnerPreferenceFormBean partnerPreferenceInfoBean = registerService
						.loadUpdatePartnerPreferenceDetails(userId);
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				map.put("id", partnerPreferenceInfoBean.getId());
				
				map.put("fromAge",
						partnerPreferenceInfoBean.getFromAge());
				map.put("toAge", partnerPreferenceInfoBean.getToAge());
				map.put("botyType",
						partnerPreferenceInfoBean.getBodyType());
				map.put("complexion",
						partnerPreferenceInfoBean.getComplexion());
				map.put("food", partnerPreferenceInfoBean.getFood());
				map.put("maritalStatusId",
						partnerPreferenceInfoBean.getMaritalStatusId());
				map.put("fromHeightId",
						partnerPreferenceInfoBean.getFromHeightId());
				map.put("toHeightId",
						partnerPreferenceInfoBean.getToHeightId());
				map.put("religionId",
						partnerPreferenceInfoBean.getReligionId());
				map.put("casteId",
						partnerPreferenceInfoBean.getCasteId());
				map.put("motherTongueId",
						partnerPreferenceInfoBean.getMotherTongueId());
				map.put("educationId",
						partnerPreferenceInfoBean.getEducationId());
				map.put("occupationId",
						partnerPreferenceInfoBean.getOccupationId());
				map.put("countryId",
						partnerPreferenceInfoBean.getCountryId());
				map.put("stateId",
						partnerPreferenceInfoBean.getStateId());
				map.put("cityId",
						partnerPreferenceInfoBean.getCityId());

				main.put("myPartnerPreferenceDetails", map);
				
	            Map<Object, Object> maritalStatusMap = commonMasterService.loadMaritalStatus();
	            
	            LinkedList<LinkedHashMap<String, Object>> maritalStatusMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrMaritalStatusMap = maritalStatusMap.entrySet().iterator();
	            while (itrMaritalStatusMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrMaritalStatusMap.next();
					LinkedHashMap<String, Object> maritalStatusMaps = new LinkedHashMap<String, Object>();
					maritalStatusMaps.put("id", pair.getKey());
					maritalStatusMaps.put("maritalStatusName", pair.getValue());
					maritalStatusMapList.add(maritalStatusMaps);
					itrMaritalStatusMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("maritalStatus", maritalStatusMapList);
	            
	            Map<Object, Object> heightMap = commonMasterService.loadHeight();
	            
	            LinkedList<LinkedHashMap<String, Object>> heightMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrHeightMap = heightMap.entrySet().iterator();
	            while (itrHeightMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrHeightMap.next();
					LinkedHashMap<String, Object> heightsMap = new LinkedHashMap<String, Object>();
					heightsMap.put("id", pair.getKey());
					heightsMap.put("feetInches", pair.getValue());
					heightMapList.add(heightsMap);
					itrHeightMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("height", heightMapList);
	            
	            Map<Object, Object> religionMap = commonMasterService.loadReligion();
	            
	            LinkedList<LinkedHashMap<String, Object>> religionMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrReligionMap = religionMap.entrySet().iterator();
	            while (itrReligionMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrReligionMap.next();
					LinkedHashMap<String, Object> religionsMap = new LinkedHashMap<String, Object>();
					religionsMap.put("id", pair.getKey());
					religionsMap.put("religionName", pair.getValue());
					religionMapList.add(religionsMap);
					itrReligionMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("religion", religionMapList);
	            
	            Map<Object, Object> casteMap = commonMasterService.loadCaste(partnerPreferenceInfoBean.getReligionId());
				
				LinkedList<LinkedHashMap<String, Object>> casteMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrCasteMap = casteMap.entrySet().iterator();
	            while (itrCasteMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrCasteMap.next();
					LinkedHashMap<String, Object> castesMap = new LinkedHashMap<String, Object>();
					castesMap.put("id", pair.getKey());
					castesMap.put("casteName", pair.getValue());
					casteMapList.add(castesMap);
					itrCasteMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
				main.put("caste", casteMapList);
				
				Map<Object, Object> motherTongueMap = commonMasterService.loadMotherTongue();
	            
	            LinkedList<LinkedHashMap<String, Object>> motherTongueList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrMotherTongueMap = motherTongueMap.entrySet().iterator();
	            while (itrMotherTongueMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrMotherTongueMap.next();
					LinkedHashMap<String, Object> motherTonguesMap = new LinkedHashMap<String, Object>();
					motherTonguesMap.put("id", pair.getKey());
					motherTonguesMap.put("motherTongueName", pair.getValue());
					motherTongueList.add(motherTonguesMap);
					itrMotherTongueMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("motherTongue", motherTongueList);
	            
	            Map<Object, Object> educationMap = commonMasterService.loadEducation();
	            
	            LinkedList<LinkedHashMap<String, Object>> educationMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrEducationMap = educationMap.entrySet().iterator();
	            while (itrEducationMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrEducationMap.next();
					LinkedHashMap<String, Object> educationsMap = new LinkedHashMap<String, Object>();
					educationsMap.put("id", pair.getKey());
					educationsMap.put("educationName", pair.getValue());
					educationMapList.add(educationsMap);
					itrEducationMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("education", educationMapList);
	            
	            Map<Object, Object> occupationMap = commonMasterService.loadOccupation();
	            
	            LinkedList<LinkedHashMap<String, Object>> occupationMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrOccupationMap = occupationMap.entrySet().iterator();
	            while (itrOccupationMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrOccupationMap.next();
					LinkedHashMap<String, Object> occupationsMap = new LinkedHashMap<String, Object>();
					occupationsMap.put("id", pair.getKey());
					occupationsMap.put("occupationName", pair.getValue());
					occupationMapList.add(occupationsMap);
					itrOccupationMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("occupation", occupationMapList);
	            
	            Map<Object, Object> countryMap = commonMasterService.loadCountry();
	            
	            LinkedList<LinkedHashMap<String, Object>> countryMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrCountryMap = countryMap.entrySet().iterator();
	            while (itrCountryMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrCountryMap.next();
					LinkedHashMap<String, Object> countrysMap = new LinkedHashMap<String, Object>();
					countrysMap.put("id", pair.getKey());
					countrysMap.put("countryName", pair.getValue());
					countryMapList.add(countrysMap);
					itrCountryMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
	            main.put("country", countryMapList);
	            
	            Map<Object, Object> stateMap = commonMasterService.loadState(partnerPreferenceInfoBean.getCountryId());
				
				LinkedList<LinkedHashMap<String, Object>> stateMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrStateMap = stateMap.entrySet().iterator();
	            while (itrStateMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrStateMap.next();
					LinkedHashMap<String, Object> statesMap = new LinkedHashMap<String, Object>();
					statesMap.put("id", pair.getKey());
					statesMap.put("stateName", pair.getValue());
					stateMapList.add(statesMap);
					itrStateMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
				main.put("state", stateMapList);
				
	            Map<Object, Object> cityMap = commonMasterService.loadCity(partnerPreferenceInfoBean.getStateId());
				
				LinkedList<LinkedHashMap<String, Object>> cityMapList = new LinkedList<LinkedHashMap<String, Object>>();
	            Iterator<?> itrCityMap = cityMap.entrySet().iterator();
	            while (itrCityMap.hasNext()) {
	                @SuppressWarnings({ "rawtypes" })
					Map.Entry pair = (Map.Entry)itrCityMap.next();
					LinkedHashMap<String, Object> citysMap = new LinkedHashMap<String, Object>();
					citysMap.put("id", pair.getKey());
					citysMap.put("cityName", pair.getValue());
					cityMapList.add(citysMap);
					itrCityMap.remove(); // avoids a ConcurrentModificationException
	            }
	            
				main.put("city", cityMapList);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("deactiveProfile") && email != null) {
				
				LoginService loginService = new LoginService();
				LoginFormBean loginFormBean = loginService.loadProfileSessionDetails(email);
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				if(loginFormBean.getDeactivateProfileDays() != null) {
					map.put("deactivateProfileDays", loginFormBean.getDeactivateProfileDays());
				} else {
					map.put("deactivateProfileDays", -1);
				}
				if(loginFormBean.getDeactivatedProfileDate() != null) {
					map.put("deactivatedProfileDate", loginFormBean.getDeactivatedProfileDate());
				} else {
					map.put("deactivatedProfileDate", "");
				}
				if(loginFormBean.getActivateProfileDate() != null) {
					map.put("activateProfileDate", loginFormBean.getActivateProfileDate());
				} else {
					map.put("activateProfileDate", "");
				}
				map.put("isProfileActive", loginFormBean.getIsProfileActive());
				
				main.put("deactiveProfile", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("deleteProfile") && email != null) {
				
				LoginService loginService = new LoginService();
				LoginFormBean loginFormBean = loginService.loadProfileSessionDetails(email);
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				if(loginFormBean.getDeleteProfileReason() != null) {
					map.put("deleteProfileReason", loginFormBean.getDeleteProfileReason());
				} else {
					map.put("deleteProfileReason", "");
				}
				map.put("isProfileActive", loginFormBean.getIsProfileDeleted());
				
				main.put("deleteProfile", map);
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}
			
			if(find.equals("personalDetailsInfoBean") && userId > 0) {
				
				PersonalDetailsFormBean personalDetailsInfoBean = registerService
						.loadUpdatePersonalDetails(userId);
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				
				if(personalDetailsInfoBean.getId() != null) {
					map.put("id", personalDetailsInfoBean.getId());
				} else {
					map.put("id", "");
				}
				if(personalDetailsInfoBean.getReligionId() != null) {
					map.put("religionId",
							personalDetailsInfoBean.getReligionId());
				} else {
					map.put("religionId", -1);
				}
				if(personalDetailsInfoBean.getCasteId() != null) {
					map.put("casteId",
							personalDetailsInfoBean.getCasteId());
				} else {
					map.put("casteId", -1);
				}
				if(personalDetailsInfoBean.getSubCaste() != null) {
					map.put("subCaste",
							personalDetailsInfoBean.getSubCaste());
				} else {
					map.put("subCaste", "");
				}
				if(personalDetailsInfoBean.getMotherTongueId() != null) {
					map.put("motherTongueId",
							personalDetailsInfoBean.getMotherTongueId());
				} else {
					map.put("motherTongueId", -1);
				}
				if(personalDetailsInfoBean.getBodyType() != null) {
					map.put("botyType",
							personalDetailsInfoBean.getBodyType());
				} else {
					map.put("botyType", "");
				}
				if(personalDetailsInfoBean.getComplexion() != null) {
					map.put("complexion",
							personalDetailsInfoBean.getComplexion());
				} else {
					map.put("complexion", "");
				}
				if(personalDetailsInfoBean.getPhysicalStatus() != null) {
					map.put("physicalStatus",
							personalDetailsInfoBean.getPhysicalStatus());
				} else {
					map.put("physicalStatus", "");
				}
				if(personalDetailsInfoBean.getEmployedIn() != null) {
					map.put("employedIn",
							personalDetailsInfoBean.getEmployedIn());
				} else {
					map.put("employedIn", "");
				}
				if(personalDetailsInfoBean.getMonthlyIncome() != null) {
					map.put("monthlyIncome",
							personalDetailsInfoBean.getMonthlyIncome());
				} else {
					map.put("monthlyIncome", "");
				}
				if(personalDetailsInfoBean.getCurrencyId() != null) {
					map.put("currencyId",
							personalDetailsInfoBean.getCurrencyId());
				} else {
					map.put("currencyId", "");
				}
				if(personalDetailsInfoBean.getFood() != null && personalDetailsInfoBean.getFood() != "") {
					map.put("food",
							personalDetailsInfoBean.getFood());
				} else {
					map.put("food", "");
				}
				if(personalDetailsInfoBean.getSmoking() != null && personalDetailsInfoBean.getSmoking() != "") {
					map.put("smoking",
							personalDetailsInfoBean.getSmoking());
				} else {
					map.put("smoking", "");
				}
				if(personalDetailsInfoBean.getDrinking() != null && personalDetailsInfoBean.getDrinking() != "") {
					map.put("drinking",
							personalDetailsInfoBean.getDrinking());
				} else {
					map.put("drinking", "");
				}
				if(personalDetailsInfoBean.getFamilyStatus() != null && personalDetailsInfoBean.getFamilyStatus() != "") {
					map.put("familyStatus",
							personalDetailsInfoBean.getFamilyStatus());
				} else {
					map.put("familyStatus", "");
				}
				if(personalDetailsInfoBean.getFamilyType() != null && personalDetailsInfoBean.getFamilyType() != "") {
					map.put("familyType",
							personalDetailsInfoBean.getFamilyType());
				} else {
					map.put("familyType", "");
				}
				if(personalDetailsInfoBean.getFamilyValues() != null && personalDetailsInfoBean.getFamilyValues() != "") {
					map.put("familyValues",
							personalDetailsInfoBean.getFamilyValues());
				} else {
					map.put("familyValues", "");
				}
				if(personalDetailsInfoBean.getFathersStatus() != null && personalDetailsInfoBean.getFathersStatus() != "") {
					map.put("fathersStatus",
							personalDetailsInfoBean.getFathersStatus());
				} else {
					map.put("fathersStatus", "");
				}
				if(personalDetailsInfoBean.getMothersStatus() != null && personalDetailsInfoBean.getMothersStatus() != "") {
					map.put("mothersStatus",
							personalDetailsInfoBean.getMothersStatus());
				} else {
					map.put("mothersStatus", "");
				}
				if(personalDetailsInfoBean.getNumberOfBrothers() != null && personalDetailsInfoBean.getNumberOfBrothers() != "") {
					map.put("numberOfBrothers",
							personalDetailsInfoBean.getNumberOfBrothers());
				} else {
					map.put("numberOfBrothers", "");
				}
				if(personalDetailsInfoBean.getBrothersMarried() != null && personalDetailsInfoBean.getBrothersMarried() != "") {
					map.put("brothersMarried",
							personalDetailsInfoBean.getBrothersMarried());
				} else {
					map.put("brothersMarried", "");
				}
				if(personalDetailsInfoBean.getNumberOfSisters() != null && personalDetailsInfoBean.getNumberOfSisters() != "") {
					map.put("numberOfSisters",
							personalDetailsInfoBean.getNumberOfSisters());
				} else {
					map.put("numberOfSisters", "");
				}
				if(personalDetailsInfoBean.getSistersMarried() != null && personalDetailsInfoBean.getSistersMarried() != "") {
					map.put("sistersMarried",
							personalDetailsInfoBean.getSistersMarried());
				} else {
					map.put("sistersMarried", "");
				}

				main.put("myPersonalDetails", map);
				
				 Map<Object, Object> maritalStatusMap = commonMasterService.loadMaritalStatus();
		            
		            LinkedList<LinkedHashMap<String, Object>> maritalStatusMapList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrMaritalStatusMap = maritalStatusMap.entrySet().iterator();
		            while (itrMaritalStatusMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrMaritalStatusMap.next();
						LinkedHashMap<String, Object> maritalStatusMaps = new LinkedHashMap<String, Object>();
						maritalStatusMaps.put("id", pair.getKey());
						maritalStatusMaps.put("maritalStatusName", pair.getValue());
						maritalStatusMapList.add(maritalStatusMaps);
						itrMaritalStatusMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
		            main.put("maritalStatus", maritalStatusMapList);
		            
		            Map<Object, Object> weightMap = commonMasterService.loadWeight();
		            
		            LinkedList<LinkedHashMap<String, Object>> weightMapList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrWeightMap = weightMap.entrySet().iterator();
		            while (itrWeightMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrWeightMap.next();
						LinkedHashMap<String, Object> weightsMap = new LinkedHashMap<String, Object>();
						weightsMap.put("id", pair.getKey());
						weightsMap.put("feetInches", pair.getValue());
						weightMapList.add(weightsMap);
						itrWeightMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
		            main.put("weight", weightMapList);
		            
		            Map<Object, Object> currencyMap = commonMasterService.loadCurrency();
		            
		            LinkedList<LinkedHashMap<String, Object>> currencyMapList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrCurrencyMap = currencyMap.entrySet().iterator();
		            while (itrCurrencyMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrCurrencyMap.next();
						LinkedHashMap<String, Object> currencysMap = new LinkedHashMap<String, Object>();
						currencysMap.put("id", pair.getKey());
						currencysMap.put("currencyName", pair.getValue());
						currencyMapList.add(currencysMap);
						itrCurrencyMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
		            main.put("weight", weightMapList);
		            
		            Map<Object, Object> religionMap = commonMasterService.loadReligion();
		            
		            LinkedList<LinkedHashMap<String, Object>> religionMapList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrReligionMap = religionMap.entrySet().iterator();
		            while (itrReligionMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrReligionMap.next();
						LinkedHashMap<String, Object> religionsMap = new LinkedHashMap<String, Object>();
						religionsMap.put("id", pair.getKey());
						religionsMap.put("religionName", pair.getValue());
						religionMapList.add(religionsMap);
						itrReligionMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
		            main.put("religion", religionMapList);
		            
		            Map<Object, Object> casteMap = commonMasterService.loadCaste(personalDetailsInfoBean.getReligionId());
					
					LinkedList<LinkedHashMap<String, Object>> casteMapList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrCasteMap = casteMap.entrySet().iterator();
		            while (itrCasteMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrCasteMap.next();
						LinkedHashMap<String, Object> castesMap = new LinkedHashMap<String, Object>();
						castesMap.put("id", pair.getKey());
						castesMap.put("casteName", pair.getValue());
						casteMapList.add(castesMap);
						itrCasteMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
					main.put("caste", casteMapList);
					
					Map<Object, Object> motherTongueMap = commonMasterService.loadMotherTongue();
		            
		            LinkedList<LinkedHashMap<String, Object>> motherTongueList = new LinkedList<LinkedHashMap<String, Object>>();
		            Iterator<?> itrMotherTongueMap = motherTongueMap.entrySet().iterator();
		            while (itrMotherTongueMap.hasNext()) {
		                @SuppressWarnings({ "rawtypes" })
						Map.Entry pair = (Map.Entry)itrMotherTongueMap.next();
						LinkedHashMap<String, Object> motherTonguesMap = new LinkedHashMap<String, Object>();
						motherTonguesMap.put("id", pair.getKey());
						motherTonguesMap.put("motherTongueName", pair.getValue());
						motherTongueList.add(motherTonguesMap);
						itrMotherTongueMap.remove(); // avoids a ConcurrentModificationException
		            }
		            
		            main.put("motherTongue", motherTongueList);
		            
				
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
			}

		} 
		
		if(update != null) {
			if(update.equals("changePassword") && userId > 0 && password != null) {
		
				String pwdHash = CommonConstants.generateEncryptedPwd(password);
				commonMasterService.resetPassword(userId, pwdHash);
				
				rootMap.put("status", "success");
				rootMap.put("message", "Password Changed Successfully.");
				main.put("changePassword", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
			}
			
			if(update.equals("myPrivacy") && userId > 0 && profilePicHide != null) {
				
				if(profilePicHide.equals("Yes")) {
					accountDetailsFormBean.setShowMyProfilePicture(false);
				} else {
					accountDetailsFormBean.setShowMyProfilePicture(true);
				}
				
				accountDetailsFormBean.setUpdatedBy(userId);
						
				Long status = registerService
						.updateMyPrivacy(accountDetailsFormBean, userId);
				
				if (status != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "My Privacy Updated Successfully.");
					main.put("myPrivacy", rootMap);
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
					main.put("myPrivacy", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
			}
			
			if(update.equals("deactivateProfile") && userId > 0 && deactivateProfileDays > 0) {
				
				Calendar c = Calendar.getInstance();    
				c.setTime(new Date());
				c.add(Calendar.DATE, Integer.valueOf(deactivateProfileDays.toString()));
				
				accountDetailsFormBean.setActivateProfileDate(c.getTime());
				
				accountDetailsFormBean.setDeactivateProfileDays(deactivateProfileDays);
				
				accountDetailsFormBean.setIsProfileActive(false);
				
				accountDetailsFormBean.setUpdatedBy(userId);
				

				Long status = registerService
						.deactivateProfile(accountDetailsFormBean, userId);
				
				if (status != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "Profile Deactivated Successfully.");
					main.put("deactivateProfile", rootMap);
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
					main.put("deactivateProfile", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
			}
			
			if(update.equals("activateProfile") && userId > 0) {
				
				accountDetailsFormBean.setIsProfileActive(true);
				
				accountDetailsFormBean.setUpdatedBy(userId);
				

				Long status = registerService
						.activateProfile(accountDetailsFormBean, userId);
				
				if (status != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "Profile Activated Successfully.");
					main.put("activateProfile", rootMap);
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
					main.put("activateProfile", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				}
			}
			
			if(update.equals("deleteProfile") && userId > 0 && deleteProfileReason != null) {
				
				accountDetailsFormBean.setDeleteProfileReason(deleteProfileReason);
				
				accountDetailsFormBean.setUpdatedBy(userId);
				

				Long status = registerService
						.deleteProfile(accountDetailsFormBean, userId);
				
				if (status != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "Profile Deleted Successfully.");
					main.put("deleteProfile", rootMap);
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
					main.put("deleteProfile", rootMap);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
		String update = request.getParameter("update");
		
		if(update != null) {
			if(update.equals("profile")) {
				UserProfileFormBean userProfile = new UserProfileFormBean();
				RegisterService registerService = new RegisterService();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				String dateOfBirth = request.getParameter("dateOfBirth");
				Date dob = null;
				try {
					dob = sdf.parse(dateOfBirth);
					userProfile.setDob(dob);
				} catch (ParseException e) {
					// e.printStackTrace();
				}
				
				userProfile.setId(Long.parseLong(request.getParameter("id").toString()));
				userProfile.setUserProfileId(Long.parseLong(request.getParameter("userProfileId").toString()));
				userProfile.setFullName(request.getParameter("fullName"));
				userProfile.setGenderId(Long.parseLong(request.getParameter("genderId").toString()));
				userProfile.setMaritalStatusId(Long.parseLong(request.getParameter("maritalStatusId").toString()));
				userProfile.setMobile(Long.parseLong(request.getParameter("mobile").toString()));
				userProfile.setHeightId(Long.parseLong(request.getParameter("heightId").toString()));
				userProfile
						.setEducationId(Long.parseLong(request.getParameter("educationId").toString()));
				userProfile.setOccupationId(Long.parseLong(request.getParameter("occupationId").toString()));
				userProfile.setReligionId(Long.parseLong(request.getParameter("religionId").toString()));
				userProfile.setCityId(Long.parseLong(request.getParameter("cityId").toString()));
				userProfile.setStateId(Long.parseLong(request.getParameter("stateId").toString()));
				userProfile.setCountryId(Long.parseLong(request.getParameter("countryId").toString()));
				userProfile.setAboutYou(request.getParameter("aboutYou"));
				userProfile.setUpdatedBy(Long.parseLong(request.getParameter("id")));
				
				try {
					
					Long status = registerService
							.saveRegisterDetails(userProfile);
					if (status != null) {
						rootMap.put("status", "success");
						rootMap.put("message", "Profile Updated Successfully.");
						main.put("profile", rootMap);
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
						main.put("profile", rootMap);
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
						main.put("profile", rootMap);
			    }
			}
			
			if(update.equals("personalDetails")) {
				PersonalDetailsFormBean personalDetailsFormBean = new PersonalDetailsFormBean();
				if(request.getParameter("id") != null && request.getParameter("id") != "") {
					personalDetailsFormBean.setId(Long.parseLong(request.getParameter("id").toString()));
					personalDetailsFormBean.setUpdatedBy(Long.parseLong(request.getParameter("id")));
				}
				Long profileId = 1000000 + Long.parseLong(request.getParameter(
						"userId").toString());
				personalDetailsFormBean.setProfileId(profileId);
				personalDetailsFormBean.setUserId(Long.parseLong(request.getParameter(
						"userId").toString()));
				personalDetailsFormBean.setCasteId(Long.parseLong(request.getParameter("casteId").toString()));
				personalDetailsFormBean.setSubCaste(request.getParameter("subCaste"));
				personalDetailsFormBean.setMotherTongueId(Long.parseLong(request.getParameter("motherTongueId").toString()));
				personalDetailsFormBean.setWeightId(Long.parseLong(request.getParameter("weightId").toString()));
				personalDetailsFormBean.setBodyType(request.getParameter("bodyType").toString());
				personalDetailsFormBean.setComplexion(request.getParameter("complextion").toString());
				personalDetailsFormBean.setPhysicalStatus(request.getParameter("physicalStatus").toString());
				personalDetailsFormBean.setEmployedIn(request.getParameter("employedIn").toString());
				personalDetailsFormBean.setCurrencyId(Long.parseLong(request.getParameter("currencyId").toString()));
				personalDetailsFormBean.setMonthlyIncome(Long.parseLong(request.getParameter("monthlyIncome").toString()));
				personalDetailsFormBean.setFood(request.getParameter("food").toString());
				personalDetailsFormBean.setSmoking(request.getParameter("smoking").toString());
				personalDetailsFormBean.setDrinking(request.getParameter("drinking").toString());
				personalDetailsFormBean.setFamilyStatus(request.getParameter("familyStatus"));
				personalDetailsFormBean.setFamilyType(request.getParameter("familyType"));
				personalDetailsFormBean.setFamilyValues(request.getParameter("familyValues"));
				personalDetailsFormBean.setFathersStatus(request.getParameter("fathersStatus"));
				personalDetailsFormBean.setMothersStatus(request.getParameter("mothersStatus"));
				personalDetailsFormBean.setNumberOfBrothers(request.getParameter("numberOfBrothers"));
				personalDetailsFormBean.setBrothersMarried(request.getParameter("brothersMarried"));
				personalDetailsFormBean.setNumberOfSisters(request.getParameter("numberOfSisters"));
				personalDetailsFormBean.setSistersMarried(request.getParameter("sistersMarried"));
				
				String userExist = commonMasterService.checkPersonalDetailsAlreadyExist(personalDetailsFormBean.getUserId());
				if (userExist != null && request.getParameter("id") == null && request.getParameter("id") == "") {
					rootMap.put("status", "failure");
					rootMap.put("message", "Already Personal Details Inserted.");
					main.put("personalDetails", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				} else {
				Long status = registerService
						.savePersonalDetails(personalDetailsFormBean);
				if (status != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "Personal Details Updated Successfully.");
					main.put("personalDetails", rootMap);
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
					main.put("personalDetails", rootMap);
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
			
			if(update.equals("profile")) {
				UserProfileFormBean userProfile = new UserProfileFormBean();
				
				UserProfileFormBean oldUser = registerService
						.loadUserProfileDetails(Long.parseLong(request.getParameter("id").toString()));
				
				RegisterService registerService = new RegisterService();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				String dateOfBirth = request.getParameter("dateOfBirth");
				Date dob = null;
				try {
					dob = sdf.parse(dateOfBirth);
					userProfile.setDob(dob);
				} catch (ParseException e) {
					// e.printStackTrace();
				}
				
				userProfile.setId(Long.parseLong(request.getParameter("id").toString()));
				userProfile.setUserProfileId(Long.parseLong(request.getParameter("userProfileId").toString()));
				userProfile.setFullName(request.getParameter("fullName"));
				userProfile.setGenderId(Long.parseLong(request.getParameter("genderId").toString()));
				userProfile.setMaritalStatusId(Long.parseLong(request.getParameter("maritalStatusId").toString()));
				userProfile.setMobile(Long.parseLong(request.getParameter("mobile").toString()));
				userProfile.setHeightId(Long.parseLong(request.getParameter("heightId").toString()));
				userProfile
						.setEducationId(Long.parseLong(request.getParameter("educationId").toString()));
				userProfile.setOccupationId(Long.parseLong(request.getParameter("occupationId").toString()));
				userProfile.setReligionId(Long.parseLong(request.getParameter("religionId").toString()));
				userProfile.setCityId(Long.parseLong(request.getParameter("cityId").toString()));
				userProfile.setStateId(Long.parseLong(request.getParameter("stateId").toString()));
				userProfile.setCountryId(Long.parseLong(request.getParameter("countryId").toString()));
				userProfile.setAboutYou(request.getParameter("aboutYou"));
				userProfile.setUpdatedBy(Long.parseLong(request.getParameter("id")));
				
				try {
					
					Long status = registerService
							.saveRegisterDetails(userProfile);
					if (status != null) {
						String code = "";
						Boolean verifyedMobileNumber = true;
						if(!userProfile.getMobile().toString().equals(oldUser.getMobile().toString())) {
							code = registerService
									.updateMobileVerificationCodeDetails(userProfile.getId());
							verifyedMobileNumber = false;
						}						
						
						rootMap.put("status", "success");
						rootMap.put("message", "Profile Updated Successfully.");
						rootMap.put("mobile", userProfile.getMobile());
						rootMap.put("verifyMobileNumber", code);
						rootMap.put("verifyedMobileNumber", verifyedMobileNumber);
						main.put("profile", rootMap);

						
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
						main.put("profile", rootMap);
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
						main.put("profile", rootMap);
			    }
			}
		} else {
			rootMap.put("status", "failure");
			rootMap.put("message", "Missing Parameters.");
			main.put("comment", rootMap);
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
	
