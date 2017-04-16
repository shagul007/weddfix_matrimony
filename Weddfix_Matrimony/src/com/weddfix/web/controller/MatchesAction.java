package com.weddfix.web.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.weddfix.web.formbean.MatchesFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.MatchesService;
import com.weddfix.web.services.RegisterService;

public class MatchesAction extends ActionSupport implements
		ModelDriven<MatchesFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	private static final long MILLIS_IN_DAY = 60*60*24*1000;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private Map<String, Object> myPersonalDetailsBean = new HashMap<String, Object>();
	private List<Map<String, Object>> profilePicturesInfoBean = new ArrayList<Map<String, Object>>();
	private Map<String, Object> partnerPreferenceDetailsBean = new HashMap<String, Object>();
	private List<Map<String, Object>> newMatchesInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> searchMatchesInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> whoViewedMyProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> viewedAndNotContactedProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> recentlyViewedProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> shortlistedProfileInfoBean = new ArrayList<Map<String, Object>>();
	private Map<Object, Object> maritalStatusMap;
	private Map<Object, Object> genderMap;
	private Map<Object, Object> religionMap;
	private Map<Object, Object> motherTongueMap;
	private Map<Object, Object> stateMap;
	private Map<Object, Object> countryMap;
	private Long profileUserId;
	private Long userProfileId;
	private Long viewUserProfileId;
	private String statusName;
	private Long fromAge;
	private Long toAge;
	private Long genderId;
	private Long maritalStatusId;
	private Long countryId;
	private Long stateId;
	private Long religionId;
	private Long motherTongueId;
	MatchesFormBean matchesDetailsFormBean = new MatchesFormBean();
	RegisterService registerService = new RegisterService();
	MatchesService matchesService = new MatchesService();
	ClientInfoService clientInfoService = new ClientInfoService();
	CommonMasterService commonMasterService = new CommonMasterService();

	public String saveMatchesDetails() {
		HttpSession session = request.getSession(true);
		try {

			matchesDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("userProfileId").toString()));
			matchesDetailsFormBean.setUserId(Long.parseLong(session
					.getAttribute("userId").toString()));
			matchesDetailsFormBean.setNewMatches(true);
			matchesDetailsFormBean.setWhoViewedMyProfile(true);
			matchesDetailsFormBean.setViewedAndNotContacted(true);
			matchesDetailsFormBean.setRecentlyViewedProfiles(true);
			matchesDetailsFormBean.setCreatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = matchesService
					.saveMatchesDetails(matchesDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}

	public String saveShortlistedProfiles() {
		HttpSession session = request.getSession(true);
		try {

			matchesDetailsFormBean.setProfileId(getUserProfileId());
			matchesDetailsFormBean.setUserId(Long.parseLong(session
					.getAttribute("userId").toString()));
			if (getStatusName().equals("Shortlist")) {
				matchesDetailsFormBean.setShortlistedProfiles(true);
			} else {
				matchesDetailsFormBean.setShortlistedProfiles(false);
			}
			matchesDetailsFormBean.setCreatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = matchesService
					.saveShortlistedProfiles(matchesDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}

	public String saveSendInterestProfiles() {
		HttpSession session = request.getSession(true);
		try {

			matchesDetailsFormBean.setProfileId(getUserProfileId());
			matchesDetailsFormBean.setUserId(Long.parseLong(session
					.getAttribute("userId").toString()));
			if (getStatusName().equals("SendInterest")) {
				matchesDetailsFormBean.setSendInterested(true);
				matchesDetailsFormBean.setSendMail(true);
			} else {
				matchesDetailsFormBean.setSendInterested(false);
			}
			matchesDetailsFormBean.setCreatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = matchesService
					.saveSendInterestProfiles(matchesDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}

	public String loadProfilePictures() {
		HttpSession session = request.getSession();
		try {
			List<PhotoGalleryFormBean> photoGalleryInfoList = clientInfoService
					.loadProfilePictures((Long) session.getAttribute("userId"),
							(Long) session.getAttribute("loginProfileId"));

			Iterator<?> itr = photoGalleryInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("photoType", obj[1]);
				map.put("fileName", obj[3]);

				profilePicturesInfoBean.add(map);
			}
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadProfileDetails() {
		HttpSession session = request.getSession();
		try {

			MyPersonalDetailsFormBean myPersonalDetails = registerService
					.loadPersonalDetails((Long) session
							.getAttribute("userId"));

			myPersonalDetailsBean.put("userId",
					(Long) session
					.getAttribute("profileUserId"));
			myPersonalDetailsBean.put("profileFor",
					myPersonalDetails.getProfileFor());
			myPersonalDetailsBean.put("fullName",
					myPersonalDetails.getFullName());
			myPersonalDetailsBean.put("gender", myPersonalDetails.getGender());
			DecimalFormat format = new DecimalFormat("0.#");
			myPersonalDetailsBean.put("age",
					format.format(myPersonalDetails.getAge()));
			myPersonalDetailsBean.put("dob", myPersonalDetails.getDob());
			myPersonalDetailsBean.put("email", myPersonalDetails.getEmail());
			myPersonalDetailsBean.put("mobile", myPersonalDetails.getMobile());
			myPersonalDetailsBean.put("religion",
					myPersonalDetails.getReligion());
			myPersonalDetailsBean.put("motherTongue",
					myPersonalDetails.getMotherTongue());
			myPersonalDetailsBean.put("profileId",
					myPersonalDetails.getProfileId());
			session.setAttribute("profileId", myPersonalDetails.getProfileId());
			myPersonalDetailsBean.put("maritalStatus",
					myPersonalDetails.getMaritalStatus());
			myPersonalDetailsBean.put("caste", myPersonalDetails.getCaste());
			myPersonalDetailsBean
					.put("country", myPersonalDetails.getCountry());
			myPersonalDetailsBean.put("state", myPersonalDetails.getState());
			myPersonalDetailsBean.put("city", myPersonalDetails.getCity());
			myPersonalDetailsBean.put("height", myPersonalDetails.getHeight());
			myPersonalDetailsBean.put("weight", myPersonalDetails.getWeight());
			myPersonalDetailsBean.put("bodyType",
					myPersonalDetails.getBodyType());
			myPersonalDetailsBean.put("complexion",
					myPersonalDetails.getComplexion());
			myPersonalDetailsBean.put("physicalStatus",
					myPersonalDetails.getPhysicalStatus());
			myPersonalDetailsBean.put("education",
					myPersonalDetails.getEducation());
			myPersonalDetailsBean.put("occupation",
					myPersonalDetails.getOccupation());
			myPersonalDetailsBean.put("employedIn",
					myPersonalDetails.getEmployedIn());
			myPersonalDetailsBean.put("currency",
					myPersonalDetails.getCurrency());
			myPersonalDetailsBean.put("monthlyIncome",
					myPersonalDetails.getMonthlyIncome());
			myPersonalDetailsBean.put("food", myPersonalDetails.getFood());
			myPersonalDetailsBean
					.put("smoking", myPersonalDetails.getSmoking());
			myPersonalDetailsBean.put("drinking",
					myPersonalDetails.getDrinking());
			myPersonalDetailsBean.put("familyStatus",
					myPersonalDetails.getFamilyStatus());
			myPersonalDetailsBean.put("familyType",
					myPersonalDetails.getFamilyType());
			myPersonalDetailsBean.put("familyValues",
					myPersonalDetails.getFamilyValues());
			myPersonalDetailsBean.put("aboutYou",
					myPersonalDetails.getAboutYou());
			myPersonalDetailsBean.put("profilePictureId",
					myPersonalDetails.getProfilePictureId());

			loadUserProfilePictures(myPersonalDetails.getProfileId());
			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}
	
	public String loadViewProfileDetails() {
		HttpSession session = request.getSession();
		try {

			MyPersonalDetailsFormBean myPersonalDetails = registerService
					.loadViewProfileDetails((Long) session
							.getAttribute("profileUserId"), (Long) session
							.getAttribute("userProfileId"), (Long) session
							.getAttribute("userId"));

			myPersonalDetailsBean.put("userId",
					(Long) session
					.getAttribute("profileUserId"));
			myPersonalDetailsBean.put("profileFor",
					myPersonalDetails.getProfileFor());
			myPersonalDetailsBean.put("fullName",
					myPersonalDetails.getFullName());
			myPersonalDetailsBean.put("gender", myPersonalDetails.getGender());
			DecimalFormat format = new DecimalFormat("0.#");
			myPersonalDetailsBean.put("age",
					format.format(myPersonalDetails.getAge()));
			myPersonalDetailsBean.put("dob", myPersonalDetails.getDob());
			myPersonalDetailsBean.put("email", myPersonalDetails.getEmail());
			myPersonalDetailsBean.put("mobile", myPersonalDetails.getMobile());
			myPersonalDetailsBean.put("religion",
					myPersonalDetails.getReligion());
			myPersonalDetailsBean.put("motherTongue",
					myPersonalDetails.getMotherTongue());
			myPersonalDetailsBean.put("profileId",
					myPersonalDetails.getProfileId());
			session.setAttribute("profileId", myPersonalDetails.getProfileId());
			myPersonalDetailsBean.put("maritalStatus",
					myPersonalDetails.getMaritalStatus());
			myPersonalDetailsBean.put("caste", myPersonalDetails.getCaste());
			myPersonalDetailsBean.put("subCaste", myPersonalDetails.getSubCaste());
			myPersonalDetailsBean
					.put("country", myPersonalDetails.getCountry());
			myPersonalDetailsBean.put("state", myPersonalDetails.getState());
			myPersonalDetailsBean.put("city", myPersonalDetails.getCity());
			myPersonalDetailsBean.put("height", myPersonalDetails.getHeight());
			myPersonalDetailsBean.put("weight", myPersonalDetails.getWeight());
			myPersonalDetailsBean.put("bodyType",
					myPersonalDetails.getBodyType());
			myPersonalDetailsBean.put("complexion",
					myPersonalDetails.getComplexion());
			myPersonalDetailsBean.put("physicalStatus",
					myPersonalDetails.getPhysicalStatus());
			myPersonalDetailsBean.put("education",
					myPersonalDetails.getEducation());
			myPersonalDetailsBean.put("occupation",
					myPersonalDetails.getOccupation());
			myPersonalDetailsBean.put("employedIn",
					myPersonalDetails.getEmployedIn());
			myPersonalDetailsBean.put("currency",
					myPersonalDetails.getCurrency());
			myPersonalDetailsBean.put("monthlyIncome",
					myPersonalDetails.getMonthlyIncome());
			myPersonalDetailsBean.put("food", myPersonalDetails.getFood());
			myPersonalDetailsBean
					.put("smoking", myPersonalDetails.getSmoking());
			myPersonalDetailsBean.put("drinking",
					myPersonalDetails.getDrinking());
			myPersonalDetailsBean.put("familyStatus",
					myPersonalDetails.getFamilyStatus());
			myPersonalDetailsBean.put("familyType",
					myPersonalDetails.getFamilyType());
			myPersonalDetailsBean.put("familyValues",
					myPersonalDetails.getFamilyValues());
			myPersonalDetailsBean.put("fathersStatus",
					myPersonalDetails.getFathersStatus());
			myPersonalDetailsBean.put("mothersStatus",
					myPersonalDetails.getMothersStatus());
			myPersonalDetailsBean.put("numberOfBrothers",
					myPersonalDetails.getNumberOfBrothers());
			myPersonalDetailsBean.put("brothersMarried",
					myPersonalDetails.getBrothersMarried());
			myPersonalDetailsBean.put("numberOfSisters",
					myPersonalDetails.getNumberOfSisters());
			myPersonalDetailsBean.put("sistersMarried",
					myPersonalDetails.getSistersMarried());
			myPersonalDetailsBean.put("aboutYou",
					myPersonalDetails.getAboutYou());
			myPersonalDetailsBean.put("profilePictureId",
					myPersonalDetails.getProfilePictureId());
			myPersonalDetailsBean.put("accepted",
					myPersonalDetails.getAccepted());
			myPersonalDetailsBean.put("contactRequested",
					myPersonalDetails.getContactRequested());
			myPersonalDetailsBean.put("notInterested",
					myPersonalDetails.getNotInterested());
			myPersonalDetailsBean.put("emailCount",
					myPersonalDetails.getEmailCount());
			myPersonalDetailsBean.put("mobileCount",
					myPersonalDetails.getMobileCount());
			myPersonalDetailsBean.put("dontshowalreadyViewed",
					myPersonalDetails.getDontshowalreadyViewed());

			loadUserProfilePictures(myPersonalDetails.getProfileId());
			return "success";

		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}

	public String loadUserProfilePictures(Long profileId) {
		HttpSession session = request.getSession();
		try {
			List<PhotoGalleryFormBean> photoGalleryInfoList = clientInfoService
					.loadProfilePictures(
							(Long) session.getAttribute("profileUserId"),
							profileId);

			Iterator<?> itr = photoGalleryInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("photoType", obj[1]);
				map.put("fileName", obj[2]);
				map.put("showProfilePicture", obj[3]);

				profilePicturesInfoBean.add(map);
			}
			saveMatchesDetails();
			return "success";
		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}

	public String loadPartnerPreferenceDetails() {
		HttpSession session = request.getSession();
		try {

			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails = registerService
					.loadPartnerPreferenceDetails((Long) session
							.getAttribute("userId"));

			partnerPreferenceDetailsBean.put("fromAge",
					myPartnerPreferenceDetails.getFromAge());
			partnerPreferenceDetailsBean.put("toAge",
					myPartnerPreferenceDetails.getToAge());
			partnerPreferenceDetailsBean.put("maritalStatus",
					myPartnerPreferenceDetails.getMaritalStatus());
			partnerPreferenceDetailsBean.put("gender",
					myPartnerPreferenceDetails.getGender());
			partnerPreferenceDetailsBean.put("bodyType",
					myPartnerPreferenceDetails.getBodyType());
			partnerPreferenceDetailsBean.put("complexion",
					myPartnerPreferenceDetails.getComplexion());
			partnerPreferenceDetailsBean.put("fromHeight",
					myPartnerPreferenceDetails.getFromHeight());
			partnerPreferenceDetailsBean.put("toHeight",
					myPartnerPreferenceDetails.getToHeight());
			partnerPreferenceDetailsBean.put("fromHeightId",
					myPartnerPreferenceDetails.getFromHeightId());
			partnerPreferenceDetailsBean.put("toHeightId",
					myPartnerPreferenceDetails.getToHeightId());
			partnerPreferenceDetailsBean.put("food",
					myPartnerPreferenceDetails.getFood());
			partnerPreferenceDetailsBean.put("religion",
					myPartnerPreferenceDetails.getReligion());
			partnerPreferenceDetailsBean.put("caste",
					myPartnerPreferenceDetails.getCaste());
			partnerPreferenceDetailsBean.put("motherTongue",
					myPartnerPreferenceDetails.getMotherTongue());
			partnerPreferenceDetailsBean.put("education",
					myPartnerPreferenceDetails.getEducation());
			partnerPreferenceDetailsBean.put("occupation",
					myPartnerPreferenceDetails.getOccupation());
			partnerPreferenceDetailsBean.put("country",
					myPartnerPreferenceDetails.getCountry());
			partnerPreferenceDetailsBean.put("state",
					myPartnerPreferenceDetails.getState());
			partnerPreferenceDetailsBean.put("city",
					myPartnerPreferenceDetails.getCity());

			loadNewMatchesDetails(myPartnerPreferenceDetails);

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadNewMatchesDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails) {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> newMatchesInfoList = matchesService
					.loadNewMatchesDetails(myPartnerPreferenceDetails,
							(Long) session.getAttribute("userId"));

			Iterator<?> itr = newMatchesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("fileName", obj[11]);
				map.put("aboutMe", obj[12]);
				map.put("shortlisted", obj[13]);
				map.put("sendInterested", obj[14]);
				map.put("showProfilePicture", obj[15]);

				newMatchesInfoBean.add(map);
			}

			loadAccountValidity(); 
			
			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadShortlistedProfilesDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> shortlistedProfilesInfoList = matchesService
					.loadShortlistedProfilesDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = shortlistedProfilesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("fileName", obj[11]);
				map.put("aboutMe", obj[12]);
				map.put("shortlisted", obj[13]);
				map.put("sendInterested", obj[14]);
				map.put("showProfilePicture", obj[15]);

				shortlistedProfileInfoBean.add(map);
			}
			
			loadAccountValidity();

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadWhoViewedMyProfileDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileInfoList = matchesService
					.loadWhoViewedMyProfileDetails((Long) session
							.getAttribute("loginProfileId"));

			Iterator<?> itr = whoViewedMyProfileInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("fileName", obj[9]);
				map.put("shortlisted", obj[10]);
				map.put("sendInterested", obj[11]);
				map.put("showProfilePicture", obj[12]);

				whoViewedMyProfileInfoBean.add(map);
			}
			
			loadAccountValidity();

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadViewedAndNotContactedProfileDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> viewedAndNotContactedInfoList = matchesService
					.loadViewedAndNotContactedProfileDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = viewedAndNotContactedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("fileName", obj[11]);
				map.put("aboutMe", obj[12]);
				map.put("shortlisted", obj[13]);
				map.put("sendInterested", obj[14]);
				map.put("showProfilePicture", obj[15]);

				viewedAndNotContactedProfileInfoBean.add(map);
			}
			
			loadAccountValidity();

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadRecentlyViewedProfileDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> recentlyViewedProfilesInfoList = matchesService
					.loadRecentlyViewedProfileDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = recentlyViewedProfilesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("fileName", obj[11]);
				map.put("aboutMe", obj[12]);
				map.put("shortlisted", obj[13]);
				map.put("sendInterested", obj[14]);
				map.put("showProfilePicture", obj[15]);

				recentlyViewedProfileInfoBean.add(map);
			}

			loadAccountValidity();
			
			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String searchProfiles() {
		HttpSession session = request.getSession();
		try {
			List<MyPartnerPreferenceDetailsFormBean> searchMatchesInfoList = matchesService
					.searchProfiles((Long) session.getAttribute("searchFromAge"),
							(Long) session.getAttribute("searchToAge"),
							(Long) session.getAttribute("searchGenderId"),
							(Long) session.getAttribute("searchMaritalStatusId"),
							(Long) session.getAttribute("searchCountryId"),
							(Long) session.getAttribute("searchStateId"),
							(Long) session.getAttribute("userId"));

			Iterator<?> itr = searchMatchesInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("occupation", obj[11]);
				map.put("fileName", obj[12]);
				map.put("aboutMe", obj[13]);
				map.put("shortlisted", obj[14]);
				map.put("sendInterested", obj[15]);
				map.put("showProfilePicture", obj[16]);

				searchMatchesInfoBean.add(map);
			}

			loadSearchMasters();
			loadAccountValidity();

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}
	
	public String loadAccountValidity() {
		HttpSession session = request.getSession(true);
		List<UpgradePlanFormBean> mAccountDetailsList = commonMasterService
				.loadMyAccountDetails((Long) session.getAttribute("userId"));
		Iterator<?> itr = mAccountDetailsList.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();

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
	        	session.setAttribute("accountValidity", reminingDays);
	        } else {
	        	Date createdDate = DateUtils.addMonths(new Date(), Integer.valueOf(obj[3].toString()));
	        	//it's still active if the created date + 11 days is greater than the current time
	        	int daysIntoTrial = Math.round(((createdDate.getTime() - System.currentTimeMillis()) / MILLIS_IN_DAY));
	        	
	        	int reminingDays =  daysIntoTrial;
	        	session.setAttribute("accountValidity", reminingDays);
	        }
	        
		}

		return "success";
	}

	public String searchProfileSession() {
		HttpSession session = request.getSession(true);
		try {
			session.setAttribute("searchFromAge", getFromAge());
			session.setAttribute("searchToAge", getToAge());
			session.setAttribute("searchGenderId", getGenderId());
			session.setAttribute("searchMaritalStatusId", getMaritalStatusId());
			session.setAttribute("searchCountryId", getCountryId());
			session.setAttribute("searchStateId", getStateId());
			
			searchProfiles();
			
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
	}

	public String viewProfileSession() {
		HttpSession session = request.getSession(true);
		try {
			session.setAttribute("profileUserId", getProfileUserId());
			session.setAttribute("userProfileId", getUserProfileId());
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
	}

	public String loadMasters() {

		genderMap = commonMasterService.loadGender();
		maritalStatusMap = commonMasterService.loadMaritalStatus();
		religionMap = commonMasterService.loadReligion();
		motherTongueMap = commonMasterService.loadMotherTongue();
		stateMap = commonMasterService.loadState();
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + genderMap);
		System.out.println("****" + maritalStatusMap);
		System.out.println("****" + religionMap);
		System.out.println("****" + motherTongueMap);
		System.out.println("****" + stateMap);
		System.out.println("****" + countryMap);

		return "success";
	}
	
	public String loadSearchMasters() {

		HttpSession session = request.getSession(true);
		
		genderMap = commonMasterService.loadGenderById((Long) session.getAttribute("loginGenderId"));
		maritalStatusMap = commonMasterService.loadMaritalStatus();
		religionMap = commonMasterService.loadReligion();
		motherTongueMap = commonMasterService.loadMotherTongue();
		stateMap = commonMasterService.loadState();
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + genderMap);
		System.out.println("****" + maritalStatusMap);
		System.out.println("****" + religionMap);
		System.out.println("****" + motherTongueMap);
		System.out.println("****" + stateMap);
		System.out.println("****" + countryMap);

		return "success";
	}

	public String updateEmailAndMobileCount() {
		HttpSession session = request.getSession(true);
		try {
			matchesService.updateEmailAndMobileCount((Long) session
					.getAttribute("userId"), getViewUserProfileId());
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
	}
	
	public void prepare() throws Exception {
		matchesDetailsFormBean = new MatchesFormBean();
	}

	public MatchesFormBean getModel() {
		return matchesDetailsFormBean;
	}

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.response = httpServletResponse;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Long getProfileUserId() {
		return profileUserId;
	}

	public void setProfileUserId(Long profileUserId) {
		this.profileUserId = profileUserId;
	}

	public Map<String, Object> getMyPersonalDetailsBean() {
		return myPersonalDetailsBean;
	}

	public void setMyPersonalDetailsBean(
			Map<String, Object> myPersonalDetailsBean) {
		this.myPersonalDetailsBean = myPersonalDetailsBean;
	}

	public List<Map<String, Object>> getProfilePicturesInfoBean() {
		return profilePicturesInfoBean;
	}

	public void setProfilePicturesInfoBean(
			List<Map<String, Object>> profilePicturesInfoBean) {
		this.profilePicturesInfoBean = profilePicturesInfoBean;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Map<String, Object> getPartnerPreferenceDetailsBean() {
		return partnerPreferenceDetailsBean;
	}

	public void setPartnerPreferenceDetailsBean(
			Map<String, Object> partnerPreferenceDetailsBean) {
		this.partnerPreferenceDetailsBean = partnerPreferenceDetailsBean;
	}

	public List<Map<String, Object>> getNewMatchesInfoBean() {
		return newMatchesInfoBean;
	}

	public void setNewMatchesInfoBean(
			List<Map<String, Object>> newMatchesInfoBean) {
		this.newMatchesInfoBean = newMatchesInfoBean;
	}

	public List<Map<String, Object>> getWhoViewedMyProfileInfoBean() {
		return whoViewedMyProfileInfoBean;
	}

	public void setWhoViewedMyProfileInfoBean(
			List<Map<String, Object>> whoViewedMyProfileInfoBean) {
		this.whoViewedMyProfileInfoBean = whoViewedMyProfileInfoBean;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Map<String, Object>> getViewedAndNotContactedProfileInfoBean() {
		return viewedAndNotContactedProfileInfoBean;
	}

	public void setViewedAndNotContactedProfileInfoBean(
			List<Map<String, Object>> viewedAndNotContactedProfileInfoBean) {
		this.viewedAndNotContactedProfileInfoBean = viewedAndNotContactedProfileInfoBean;
	}

	public List<Map<String, Object>> getRecentlyViewedProfileInfoBean() {
		return recentlyViewedProfileInfoBean;
	}

	public void setRecentlyViewedProfileInfoBean(
			List<Map<String, Object>> recentlyViewedProfileInfoBean) {
		this.recentlyViewedProfileInfoBean = recentlyViewedProfileInfoBean;
	}

	public List<Map<String, Object>> getShortlistedProfileInfoBean() {
		return shortlistedProfileInfoBean;
	}

	public void setShortlistedProfileInfoBean(
			List<Map<String, Object>> shortlistedProfileInfoBean) {
		this.shortlistedProfileInfoBean = shortlistedProfileInfoBean;
	}

	public Long getFromAge() {
		return fromAge;
	}

	public void setFromAge(Long fromAge) {
		this.fromAge = fromAge;
	}

	public Long getToAge() {
		return toAge;
	}

	public void setToAge(Long toAge) {
		this.toAge = toAge;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public Long getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(Long maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getReligionId() {
		return religionId;
	}

	public void setReligionId(Long religionId) {
		this.religionId = religionId;
	}

	public Long getMotherTongueId() {
		return motherTongueId;
	}

	public void setMotherTongueId(Long motherTongueId) {
		this.motherTongueId = motherTongueId;
	}

	public List<Map<String, Object>> getSearchMatchesInfoBean() {
		return searchMatchesInfoBean;
	}

	public void setSearchMatchesInfoBean(
			List<Map<String, Object>> searchMatchesInfoBean) {
		this.searchMatchesInfoBean = searchMatchesInfoBean;
	}

	public Map<Object, Object> getMaritalStatusMap() {
		return maritalStatusMap;
	}

	public void setMaritalStatusMap(Map<Object, Object> maritalStatusMap) {
		this.maritalStatusMap = maritalStatusMap;
	}

	public Map<Object, Object> getGenderMap() {
		return genderMap;
	}

	public void setGenderMap(Map<Object, Object> genderMap) {
		this.genderMap = genderMap;
	}

	public Map<Object, Object> getReligionMap() {
		return religionMap;
	}

	public void setReligionMap(Map<Object, Object> religionMap) {
		this.religionMap = religionMap;
	}

	public Map<Object, Object> getMotherTongueMap() {
		return motherTongueMap;
	}

	public void setMotherTongueMap(Map<Object, Object> motherTongueMap) {
		this.motherTongueMap = motherTongueMap;
	}

	public Map<Object, Object> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<Object, Object> stateMap) {
		this.stateMap = stateMap;
	}

	public Map<Object, Object> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<Object, Object> countryMap) {
		this.countryMap = countryMap;
	}

	public Long getViewUserProfileId() {
		return viewUserProfileId;
	}

	public void setViewUserProfileId(Long viewUserProfileId) {
		this.viewUserProfileId = viewUserProfileId;
	}

}
