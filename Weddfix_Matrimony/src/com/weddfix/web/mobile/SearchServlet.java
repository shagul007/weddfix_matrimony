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
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.MatchesService;
import com.weddfix.web.services.RegisterService;

public class SearchServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long MILLIS_IN_DAY = 60*60*24*1000;
	
	RegisterService registerService = new RegisterService();
	MatchesService matchesService = new MatchesService();
	CommonMasterService commonMasterService = new CommonMasterService();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		
		Long genderId = 0L;
		Long countryId = 0L;
		
		if(request.getParameter("genderId") != null) {
			genderId = Long.parseLong(request.getParameter("genderId").toString());
		}
		
		if(request.getParameter("countryId") != null) {
			countryId = Long.parseLong(request.getParameter("countryId").toString());
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
        
				Map<Object, Object> genderMap = commonMasterService.loadGenderById(genderId);
	            
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

	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String json = gson.toJson(main);
	            
	            response.setContentType("application/json;charset=utf-8");
	            byte[] out = json.getBytes("UTF-8");
	            response.setContentLength(out.length);
	            response.getOutputStream().write(out);

		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		Long fromAge = 0L;
		Long toAge = 0L;
		Long genderId = 0L;
		Long maritalStatusId = 0L;
		Long countryId = 0L;
		Long stateId = 0L;
		Long userId = 0L;
		
		if(request.getParameter("fromAge") != null) {
			fromAge = Long.parseLong(request.getParameter("fromAge").toString());
		}
		
		if(request.getParameter("toAge") != null) {
			toAge = Long.parseLong(request.getParameter("toAge").toString());
		}
		
		if(request.getParameter("genderId") != null) {
			genderId = Long.parseLong(request.getParameter("genderId").toString());
		}
		
		if(request.getParameter("maritalStatusId") != null) {
			maritalStatusId = Long.parseLong(request.getParameter("maritalStatusId").toString());
		}
		
		if(request.getParameter("countryId") != null) {
			countryId = Long.parseLong(request.getParameter("countryId").toString());
		}
		
		if(request.getParameter("stateId") != null) {
			stateId = Long.parseLong(request.getParameter("stateId").toString());
		}
		
		if(request.getParameter("userId") != null) {
			userId = Long.parseLong(request.getParameter("userId").toString());
		}
		
		List<MyPartnerPreferenceDetailsFormBean> searchMatchesInfoList = matchesService
				.searchProfiles(fromAge, toAge, genderId, maritalStatusId,
						countryId, stateId, userId);
				
		LinkedList<LinkedHashMap<String, Object>> searchMatchesList = new LinkedList<LinkedHashMap<String, Object>>();
		
		Iterator<?> itr = searchMatchesInfoList.iterator();
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

			searchMatchesList.add(map);
		}
		
		main.put("searchProfiles", searchMatchesList);
		
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
	
