package com.weddfix.web.mobile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.CommonConstants;

public class RegisterServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RegisterService registerService = new RegisterService();
	CommonMasterService commonMasterService = new CommonMasterService();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
		try {
			
			Long countryId = 0L;
			Long stateId = 0L;
			Long religionId = 0L;
			if(request.getParameter("country_Id") != null) {
				countryId = Long.parseLong(request.getParameter("country_Id").toString());
			}
			
			if(request.getParameter("state_Id") != null) {
				stateId = Long.parseLong(request.getParameter("state_Id").toString());
			}
			
			if(request.getParameter("religion_Id") != null) {
				religionId = Long.parseLong(request.getParameter("religion_Id").toString());
			}
			
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
        
        }
        catch (Exception e) {
        	rootMap.put("status", "failure");
			rootMap.put("message", e);
			main.put("register", rootMap);
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
		
        String save = request.getParameter("save");
        
		if(save != null) {
		if(save.equals("register")) {
			UserProfileFormBean userProfile = new UserProfileFormBean();
			RegisterService registerService = new RegisterService();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			String dateOfBirth = request.getParameter("dateOfBirth");
			String pwdHash = null;
			Date dob = null;
			try {
				dob = sdf.parse(dateOfBirth);
				userProfile.setDob(dob);
			} catch (ParseException e) {
				// e.printStackTrace();
			}
			
			userProfile.setUserProfileId(Long.parseLong(request.getParameter("userProfileId").toString()));
			userProfile.setFullName(request.getParameter("fullName"));
			userProfile.setGenderId(Long.parseLong(request.getParameter("genderId").toString()));
			userProfile.setMaritalStatusId(Long.parseLong(request.getParameter("maritalStatusId").toString()));
			userProfile.setEmail(request.getParameter("email"));
			userProfile.setPassword(request.getParameter("password"));
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
			
			try {
				if (userProfile.getPassword() != null) {
					pwdHash = CommonConstants
							.generateEncryptedPwd(userProfile
									.getPassword());
				}
				if (pwdHash != null) {
					userProfile.setPassword(pwdHash);
					userProfile.setPasswordHash(pwdHash);
				}

				String userExist = commonMasterService.checkUserAlreadyExist(userProfile.getEmail().toLowerCase());
				if (userExist != null) {
					rootMap.put("status", "failure");
					rootMap.put("message", userProfile.getEmail()+" User Alerady Exist.");
					main.put("register", rootMap);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            String json = gson.toJson(main);
		            
		            response.setContentType("application/json;charset=utf-8");
		            byte[] out = json.getBytes("UTF-8");
		            response.setContentLength(out.length);
		            response.getOutputStream().write(out);
					return;
				} else {
					
				Long userId = registerService
						.saveRegisterDetails(userProfile);
				if (userId != null) {
					rootMap.put("status", "success");
					rootMap.put("message", "Inserted Successfully.");
					rootMap.put("userId", userId);
					main.put("register", rootMap);
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
					main.put("register", rootMap);
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
					main.put("register", rootMap);
		    }
				
		}
		
		if(save.equals("partnerpreference")) {
			PartnerPreferenceFormBean partnerPreferenceDetails = new PartnerPreferenceFormBean();
			Long profileId = 1000000 + Long.parseLong(request.getParameter(
					"userId").toString());
			partnerPreferenceDetails.setProfileId(profileId);
			partnerPreferenceDetails.setUserId(Long.parseLong(request.getParameter(
					"userId").toString()));
			partnerPreferenceDetails.setFromAge(Long.parseLong(request.getParameter("fromAge").toString()));
			partnerPreferenceDetails.setToAge(Long.parseLong(request.getParameter("toAge").toString()));
			partnerPreferenceDetails.setMaritalStatusId(Long.parseLong(request.getParameter("maritalStatusId").toString()));
			partnerPreferenceDetails.setBodyType(request.getParameter("bodyType").toString());
			partnerPreferenceDetails.setComplexion(request.getParameter("complextion").toString());
			partnerPreferenceDetails.setFromHeightId(Long.parseLong(request.getParameter("fromHeightId").toString()));
			partnerPreferenceDetails.setToHeightId(Long.parseLong(request.getParameter("toHeightId").toString()));
			partnerPreferenceDetails.setFood(request.getParameter("food").toString());
			partnerPreferenceDetails.setReligionId(Long.parseLong(request.getParameter("religionId").toString()));
			partnerPreferenceDetails.setCasteId(Long.parseLong(request.getParameter("casteId").toString()));
			partnerPreferenceDetails.setMotherTongueId(Long.parseLong(request.getParameter("motherTongueId").toString()));
			partnerPreferenceDetails.setEducationId(Long.parseLong(request.getParameter("educationId").toString()));
			partnerPreferenceDetails.setOccupationId(Long.parseLong(request.getParameter("occupationId").toString()));
			partnerPreferenceDetails.setCountryId(Long.parseLong(request.getParameter("countryId").toString()));
			partnerPreferenceDetails.setStateId(Long.parseLong(request.getParameter("stateId").toString()));
			partnerPreferenceDetails.setCityId(Long.parseLong(request.getParameter("cityId").toString()));
	
			String userExist = commonMasterService.checkPartnerPreferenceAlreadyExist(partnerPreferenceDetails.getUserId());
			if (userExist != null) {
				rootMap.put("status", "failure");
				rootMap.put("message", "Already Inserted Partner Preference.");
				main.put("partnerPreference", rootMap);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);
				return;
			} else {
			Long status = registerService
					.savePartnerPreferenceDetails(partnerPreferenceDetails);
			if (status != null) {
				rootMap.put("status", "success");
				rootMap.put("message", "Registered Successfully.");
				main.put("partnerPreference", rootMap);
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
				main.put("partnerPreference", rootMap);
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
		} else {
			rootMap.put("status", "failure");
			rootMap.put("message", "Missing Parameters.");
			main.put("partnerPreference", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String json = gson.toJson(main);
	        
	        response.setContentType("application/json;charset=utf-8");
	        byte[] out = json.getBytes("UTF-8");
	        response.setContentLength(out.length);
	        response.getOutputStream().write(out);
			return;
		}
		
	 }
    catch (Exception e) {
    	rootMap.put("status", "failure");
		rootMap.put("message", e);
		main.put("register", rootMap);
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
