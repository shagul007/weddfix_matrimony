package com.weddfix.web.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.weddfix.web.formbean.AccountDetailsFormBean;
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.LoginService;
import com.weddfix.web.services.RegisterService;

public class UserProfileAction extends ActionSupport implements
		ModelDriven<PersonalDetailsFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private Map<Object, Object> profileMap;
	private Map<Object, Object> genderMap;
	private Map<Object, Object> religionMap;
	private Map<Object, Object> motherTongueMap;
	private Map<Object, Object> stateMap;
	private Map<Object, Object> countryMap;
	private Map<Object, Object> orgMap;
	private Map<Object, Object> roleMap;
	private Map<String, Object> myPersonalDetailsBean = new HashMap<String, Object>();
	private Map<String, Object> partnerPreferenceDetailsBean = new HashMap<String, Object>();
	private List<Map<String, Object>> profilePicturesInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> similarPartnerPreferenceInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> upgradePlanInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> whoViewedMyProfileInfoBean = new ArrayList<Map<String, Object>>();
	private Map<Object, Object> maritalStatusMap;
	private Map<Object, Object> heightMap;
	private Map<Object, Object> educationMap;
	private Map<Object, Object> occupationMap;
	private Map<Object, Object> cityMap;
	private Map<Object, Object> casteMap;
	private Long deactivateProfileDays;
	private String deleteProfileReason;
	private Long country_Id;
	private Long state_Id;
	private Long religion_Id;
	private String ppHide;

	PersonalDetailsFormBean personalDetailsFormBean = new PersonalDetailsFormBean();
	AccountDetailsFormBean accountDetailsFormBean = new AccountDetailsFormBean();
	RegisterService registerService = new RegisterService();
	CommonMasterService commonMasterService = new CommonMasterService();
	ClientInfoService clientInfoService = new ClientInfoService();

	public String savePersonalDetails() {
		HttpSession session = request.getSession(true);
		if (personalDetailsFormBean.getCasteId() != null) {
			try {

				if (personalDetailsFormBean.getId() != null) {
					session.setAttribute("update", "UPDATE");
					personalDetailsFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));
				} else {
					session.setAttribute("update", null);
					Long profileId = Long.parseLong(session
							.getAttribute("loginProfileId").toString());
					personalDetailsFormBean.setProfileId(profileId);
					personalDetailsFormBean.setUserId(Long.parseLong(session
							.getAttribute("userId").toString()));
				}

				Long status = registerService
						.savePersonalDetails(personalDetailsFormBean);
				if (status != null) {
					if (session.getAttribute("update") != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						System.out.println("Updated Successfully...");
					} else {
						personalDetailsFormBean.setCasteId(null);
						loadMasters();
						session.setAttribute("successMessage",
								"Registered Successfully...");
						session.setAttribute("hrefParamSuccess", "login");
						System.out.println("Registered Successfully...");
					}
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
		} else {
			loadMasters();
			return "success";
		}
	}

	public String deactivateProfile() {
		HttpSession session = request.getSession(true);
			try {

				Calendar c = Calendar.getInstance();    
				c.setTime(new Date());
				c.add(Calendar.DATE, Integer.valueOf(getDeactivateProfileDays().toString()));
				
				accountDetailsFormBean.setActivateProfileDate(c.getTime());
				
				accountDetailsFormBean.setDeactivateProfileDays(getDeactivateProfileDays());
				
				accountDetailsFormBean.setIsProfileActive(false);
				
				accountDetailsFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));
				

				Long status = registerService
						.deactivateProfile(accountDetailsFormBean, (Long) session
								.getAttribute("userId"));
				if (status != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						System.out.println("Updated Successfully...");
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
	
	public String activateProfile() {
		HttpSession session = request.getSession(true);
			try {

				accountDetailsFormBean.setIsProfileActive(true);
				
				accountDetailsFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));
				
				Long status = registerService
						.activateProfile(accountDetailsFormBean, (Long) session
								.getAttribute("userId"));
				if (status != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						System.out.println("Updated Successfully...");
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
	
	public String deleteProfile() {
		HttpSession session = request.getSession(true);
			try {

				accountDetailsFormBean.setDeleteProfileReason(getDeleteProfileReason());
				
				accountDetailsFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));

				Long status = registerService
						.deleteProfile(accountDetailsFormBean, (Long) session
								.getAttribute("userId"));
				if (status != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						System.out.println("Updated Successfully...");
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
	
	public String updateMyPrivacy() {
		HttpSession session = request.getSession(true);
			try {
				if(getPpHide().equals("Yes")) {
					accountDetailsFormBean.setShowMyProfilePicture(false);
				} else {
					accountDetailsFormBean.setShowMyProfilePicture(true);
				}
				
				accountDetailsFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));

				Long status = registerService
						.updateMyPrivacy(accountDetailsFormBean, (Long) session
								.getAttribute("userId"));
				if (status != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						loadProfileSessionDetails();
						System.out.println("Updated Successfully...");
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
	
	public String loadMasters() {

		genderMap = commonMasterService.loadGender();
		maritalStatusMap = commonMasterService.loadMaritalStatus();
		heightMap = commonMasterService.loadHeight();
		religionMap = commonMasterService.loadReligion();
		motherTongueMap = commonMasterService.loadMotherTongue();
		educationMap = commonMasterService.loadEducation();
		occupationMap = commonMasterService.loadOccupation();
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + genderMap);
		System.out.println("****" + maritalStatusMap);
		System.out.println("****" + heightMap);
		System.out.println("****" + religionMap);
		System.out.println("****" + motherTongueMap);
		System.out.println("****" + educationMap);
		System.out.println("****" + occupationMap);
		System.out.println("****" + countryMap);

		return "success";
	}
	
	public String loadCountryMaster() {
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + countryMap);

		return "success";
	}

	public String loadStateMaster() {
		stateMap = commonMasterService.loadState();
		System.out.println("****" + stateMap);

		return "success";
	}
	
	public String loadReligionMaster() {
		religionMap = commonMasterService.loadReligion();
		System.out.println("****" + religionMap);

		return "success";
	}
	
	public String loadMyPersonalDetails() {
		HttpSession session = request.getSession();
		try {

			MyPersonalDetailsFormBean myPersonalDetails = registerService
					.loadPersonalDetails((Long) session.getAttribute("userId"));

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

			loadPartnerPreferenceDetails();
			loadWhoViewedMyProfileDetails(myPersonalDetails.getProfileId());
			loadProfilePictures();
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
			partnerPreferenceDetailsBean.put("gender",
					myPartnerPreferenceDetails.getGender());

			loadSimilarPartnerPreferenceDetails(myPartnerPreferenceDetails);

			return "success";

		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}

	public String loadUpgradePlanDetails() {
		try {

			List<UpgradePlanFormBean> upgradePlanInfoList = registerService
					.loadUpgradePlanDetails();

			Iterator<?> itr = upgradePlanInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
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
				map.put("id", obj[11]);

				upgradePlanInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}
	
	public String loadSimilarPartnerPreferenceDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails) {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> partnerPreferenceInfoList = registerService
					.loadSimilarPartnerPreferenceDetails(
							myPartnerPreferenceDetails,
							(Long) session.getAttribute("userId"));

			Iterator<?> itr = partnerPreferenceInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("profileId", obj[1]);
				map.put("gender", obj[2]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[3]));
				map.put("height", obj[4]);
				map.put("religion", obj[5]);
				map.put("fileName", obj[6]);
				map.put("showProfilePicture", obj[7]);

				similarPartnerPreferenceInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}
	
	public String loadWhoViewedMyProfileDetails(Long profileId) {
		try {

			List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileInfoList = registerService
					.loadWhoViewedMyProfileDetails(profileId);

			Iterator<?> itr = whoViewedMyProfileInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("profileId", obj[1]);
				map.put("gender", obj[2]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[3]));
				map.put("height", obj[4]);
				map.put("religion", obj[5]);
				map.put("fileName", obj[6]);
				map.put("showProfilePicture", obj[7]);

				whoViewedMyProfileInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			 e.printStackTrace();
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
				map.put("fileName", obj[2]);

				profilePicturesInfoBean.add(map);
			}
			return "success";
		}

		catch (Exception e) {
			 e.printStackTrace();
			return "error";
		}
	}

	public String loadAllMasters() {

		profileMap = commonMasterService.loadProfile();
		genderMap = commonMasterService.loadGender();
		religionMap = commonMasterService.loadReligion();
		motherTongueMap = commonMasterService.loadMotherTongue();
		orgMap = commonMasterService.loadOrg();
		roleMap = commonMasterService.loadRole();
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + profileMap);
		System.out.println("****" + genderMap);
		System.out.println("****" + religionMap);
		System.out.println("****" + motherTongueMap);
		System.out.println("****" + orgMap);
		System.out.println("****" + roleMap);
		System.out.println("****" + countryMap);

		return "success";
	}
	
	public String loadProfileSessionDetails(){
		HttpSession session = request.getSession();
		try {
				LoginService loginService = new LoginService();
				LoginFormBean loginFormBean = loginService.loadProfileSessionDetails(session.getAttribute("email").toString());

				session.setAttribute("role", loginFormBean.getRole());
				session.setAttribute("status", loginFormBean.getStatus());
				session.setAttribute("password", loginFormBean.getPassword());
				session.setAttribute("userName", loginFormBean.getFullName());
				session.setAttribute("loginType", "public");
				session.setAttribute("loginCheck", "loggedin");
				session.setAttribute("accountType", loginFormBean.getAccountType());
				session.setAttribute("deactivateProfileDays", loginFormBean.getDeactivateProfileDays());
				session.setAttribute("deactivatedProfileDate", loginFormBean.getDeactivatedProfileDate());
				session.setAttribute("activateProfileDate", loginFormBean.getActivateProfileDate());
				session.setAttribute("isProfileActive", loginFormBean.getIsProfileActive());
				session.setAttribute("deleteProfileReason", loginFormBean.getDeleteProfileReason());
				session.setAttribute("isProfileDeleted", loginFormBean.getIsProfileDeleted());
				session.setAttribute("showMyProfilePicture", loginFormBean.getShowMyProfilePicture());
				session.setAttribute("showMyMobileNumber", loginFormBean.getShowMyMobileNumber());
				session.setAttribute("showMyEmailId", loginFormBean.getShowMyEmailId());
				session.setAttribute("verifyMobileNumber", loginFormBean.getVerifyMobileNumber());
				session.setAttribute("verifyEmailId", loginFormBean.getVerifyEmailId());
				session.setAttribute("verifyedMobileNumber", loginFormBean.getVerifyedMobileNumber());
				session.setAttribute("verifyedEmailId", loginFormBean.getVerifyedEmailId());
				session.setAttribute("emailCount", loginFormBean.getEmailCount());
				session.setAttribute("mobileCount", loginFormBean.getMobileCount());
				session.setAttribute("videoCallCount", loginFormBean.getVideoCallCount());
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}
	
	public String loadStateByCountryId(){
		try {
				stateMap = commonMasterService.loadState(getCountry_Id());

				return "success";
		} catch (Exception e) {
//			e.printStackTrace();
			return "error";
		}

	}
	
	public String loadCityByStateId(){
		try {
				cityMap = commonMasterService.loadCity(getState_Id());

				return "success";
		} catch (Exception e) {
//			e.printStackTrace();
			return "error";
		}

	}

	public String loadCasteByReligionId(){
		try {
				casteMap = commonMasterService.loadCaste(getReligion_Id());
				return "success";
		} catch (Exception e) {
//			e.printStackTrace();
			return "error";
		}

	}
	
	public void prepare() throws Exception {
		personalDetailsFormBean = new PersonalDetailsFormBean();
	}

	public PersonalDetailsFormBean getModel() {
		return personalDetailsFormBean;
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

	public Map<String, Object> getMyPersonalDetailsBean() {
		return myPersonalDetailsBean;
	}

	public void setMyPersonalDetailsBean(
			Map<String, Object> myPersonalDetailsBean) {
		this.myPersonalDetailsBean = myPersonalDetailsBean;
	}

	public Map<Object, Object> getProfileMap() {
		return profileMap;
	}

	public void setProfileMap(Map<Object, Object> profileMap) {
		this.profileMap = profileMap;
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

	public Map<Object, Object> getOrgMap() {
		return orgMap;
	}

	public void setOrgMap(Map<Object, Object> orgMap) {
		this.orgMap = orgMap;
	}

	public Map<Object, Object> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map<Object, Object> roleMap) {
		this.roleMap = roleMap;
	}

	public Map<Object, Object> getMaritalStatusMap() {
		return maritalStatusMap;
	}

	public void setMaritalStatusMap(Map<Object, Object> maritalStatusMap) {
		this.maritalStatusMap = maritalStatusMap;
	}

	public Map<Object, Object> getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(Map<Object, Object> heightMap) {
		this.heightMap = heightMap;
	}

	public Map<Object, Object> getEducationMap() {
		return educationMap;
	}

	public void setEducationMap(Map<Object, Object> educationMap) {
		this.educationMap = educationMap;
	}

	public Map<Object, Object> getOccupationMap() {
		return occupationMap;
	}

	public void setOccupationMap(Map<Object, Object> occupationMap) {
		this.occupationMap = occupationMap;
	}

	public List<Map<String, Object>> getProfilePicturesInfoBean() {
		return profilePicturesInfoBean;
	}

	public void setProfilePicturesInfoBean(
			List<Map<String, Object>> profilePicturesInfoBean) {
		this.profilePicturesInfoBean = profilePicturesInfoBean;
	}

	public Map<String, Object> getPartnerPreferenceDetailsBean() {
		return partnerPreferenceDetailsBean;
	}

	public void setPartnerPreferenceDetailsBean(
			Map<String, Object> partnerPreferenceDetailsBean) {
		this.partnerPreferenceDetailsBean = partnerPreferenceDetailsBean;
	}

	public List<Map<String, Object>> getSimilarPartnerPreferenceInfoBean() {
		return similarPartnerPreferenceInfoBean;
	}

	public void setSimilarPartnerPreferenceInfoBean(
			List<Map<String, Object>> similarPartnerPreferenceInfoBean) {
		this.similarPartnerPreferenceInfoBean = similarPartnerPreferenceInfoBean;
	}

	public List<Map<String, Object>> getWhoViewedMyProfileInfoBean() {
		return whoViewedMyProfileInfoBean;
	}

	public void setWhoViewedMyProfileInfoBean(
			List<Map<String, Object>> whoViewedMyProfileInfoBean) {
		this.whoViewedMyProfileInfoBean = whoViewedMyProfileInfoBean;
	}

	public Long getDeactivateProfileDays() {
		return deactivateProfileDays;
	}

	public void setDeactivateProfileDays(Long deactivateProfileDays) {
		this.deactivateProfileDays = deactivateProfileDays;
	}

	public String getDeleteProfileReason() {
		return deleteProfileReason;
	}

	public void setDeleteProfileReason(String deleteProfileReason) {
		this.deleteProfileReason = deleteProfileReason;
	}

	public Map<Object, Object> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<Object, Object> cityMap) {
		this.cityMap = cityMap;
	}

	public Map<Object, Object> getCasteMap() {
		return casteMap;
	}

	public void setCasteMap(Map<Object, Object> casteMap) {
		this.casteMap = casteMap;
	}

	public Long getCountry_Id() {
		return country_Id;
	}

	public void setCountry_Id(Long country_Id) {
		this.country_Id = country_Id;
	}

	public Long getState_Id() {
		return state_Id;
	}

	public void setState_Id(Long state_Id) {
		this.state_Id = state_Id;
	}

	public List<Map<String, Object>> getUpgradePlanInfoBean() {
		return upgradePlanInfoBean;
	}

	public void setUpgradePlanInfoBean(List<Map<String, Object>> upgradePlanInfoBean) {
		this.upgradePlanInfoBean = upgradePlanInfoBean;
	}

	public String getPpHide() {
		return ppHide;
	}

	public void setPpHide(String ppHide) {
		this.ppHide = ppHide;
	}

	public Long getReligion_Id() {
		return religion_Id;
	}

	public void setReligion_Id(Long religion_Id) {
		this.religion_Id = religion_Id;
	}

}
