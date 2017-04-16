package com.weddfix.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.PwResetFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.services.LoginService;
import com.weddfix.web.services.RegisterService;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;
import com.weddfix.web.util.MailMessage;
import com.weddfix.web.util.SendSMS;

public class RegisterAction extends ActionSupport implements
		ModelDriven<UserProfileFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	private static final long MILLIS_IN_DAY = 60*60*24*1000;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private Map<Object, Object> profileMap;
	private Map<Object, Object> genderMap;
	private Map<Object, Object> religionMap;
	private Map<Object, Object> motherTongueMap;
	private Map<Object, Object> maritalStatusMap;
	private Map<Object, Object> heightMap;
	private Map<Object, Object> weightMap;
	private Map<Object, Object> educationMap;
	private Map<Object, Object> occupationMap;
	private Map<Object, Object> currencyMap;
	private Map<Object, Object> countryMap;
	private Map<Object, Object> orgMap;
	private Map<Object, Object> roleMap;
	private Map<Object, Object> statusMap;
	private Map<String, Object> userProfileBean = new HashMap<String, Object>();
	private Map<String, Object> personalDetailsBean = new HashMap<String, Object>();
	private Map<String, Object> partnerPreferenceBean = new HashMap<String, Object>();
	private List<Map<String, Object>> userProfiles = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> myAccountDetails = new ArrayList<Map<String, Object>>();
	private String emailId;
	private String dateOfBirth;
	private Long userId;
	private Long userRole;
	private String userRoleDesc;
	private Long userStatus;
	private String userPassword;
	private String verifyMobile;
	private String verifyCode;
	private String verifyMsg;

	UserProfileFormBean userProfileFormBean = new UserProfileFormBean();
	RegisterService registerService = new RegisterService();
	CommonMasterService commonMasterService = new CommonMasterService();
	SendSMS sms = new SendSMS();

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public String saveRegisterDetails() {
		HttpSession session = request.getSession(true);
		if (userProfileFormBean.getMobile() != null) {
			String pwdHash = null;
			Date dob = null;
			try {
				dob = sdf.parse(dateOfBirth);
				userProfileFormBean.setDob(dob);
			} catch (ParseException e) {
				// e.printStackTrace();
			}
			try {
				if (userProfileFormBean.getPassword() != null) {
					pwdHash = CommonConstants
							.generateEncryptedPwd(userProfileFormBean
									.getPassword());
				}
				if (pwdHash != null) {
					userProfileFormBean.setPassword(pwdHash);
					userProfileFormBean.setPasswordHash(pwdHash);
				}

				if (userProfileFormBean.getId() != null) {
					session.setAttribute("update", "UPDATE");
					userProfileFormBean.setUpdatedBy(Long.parseLong(session
							.getAttribute("userId").toString()));
				} else {
					session.setAttribute("update", null);
				}

				Long userId = registerService
						.saveRegisterDetails(userProfileFormBean);
				if (userId != null) {
					if (session.getAttribute("update") != null) {
						session.setAttribute("successMessage",
								"Updated Successfully...");
						session.setAttribute("hrefParamSuccess", "user_home");
						if(!userProfileFormBean.getMobile().toString().equals(session.getAttribute("mobile").toString())) {
							session.setAttribute("mobile", userProfileFormBean.getMobile());
							String code = registerService
									.updateMobileVerificationCodeDetails((Long) session.getAttribute("userId"));
							session.setAttribute("verifyMobileNumber", code);
							session.setAttribute("verifyedMobileNumber", false);
						}
						System.out.println("Updated Successfully...");
					} else {
						session.setAttribute("userId", userId);
						session.setAttribute("religionId",
								userProfileFormBean.getReligionId());
						userProfileFormBean.setPassword(null);
						loadMasters();
						session.setAttribute("successMessage",
								"Registered Successfully...");
						session.setAttribute("hrefParamSuccess", "register_2");
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

	public String loadUserProfileDetails() {
		HttpSession session = request.getSession();
		try {
			UserProfileFormBean userProfile = registerService
					.loadUserProfileDetails((Long) session
							.getAttribute("userId"));

			session.setAttribute("userProfileId", userProfile.getUserProfileId());
			session.setAttribute("genderId", userProfile.getGenderId());
			session.setAttribute("maritalStatusId", userProfile.getMaritalStatusId());
			session.setAttribute("heightId", userProfile.getHeightId());
			session.setAttribute("educationId", userProfile.getEducationId());
			session.setAttribute("occupationId", userProfile.getOccupationId());
			session.setAttribute("religionId", userProfile.getReligionId());
			session.setAttribute("cityId", userProfile.getCityId());
			session.setAttribute("stateId", userProfile.getStateId());
			session.setAttribute("countryId", userProfile.getCountryId());

			userProfileBean.put("id", userProfile.getId());
			userProfileBean.put("fullName", userProfile.getFullName());
			userProfileBean.put("dob", userProfile.getDobStr());
			userProfileBean.put("email", userProfile.getEmail());
			userProfileBean.put("mobile", userProfile.getMobile());
			userProfileBean.put("aboutYou", userProfile.getAboutYou());
			loadRegisterMasters();
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadUpdatePartnerPreferenceDetails() {
		HttpSession session = request.getSession();
		try {
			PartnerPreferenceFormBean partnerPreferenceInfoBean = registerService
					.loadUpdatePartnerPreferenceDetails((Long) session
							.getAttribute("userId"));

			session.setAttribute("fromAge",
					partnerPreferenceInfoBean.getFromAge());
			session.setAttribute("toAge", partnerPreferenceInfoBean.getToAge());
			session.setAttribute("botyType",
					partnerPreferenceInfoBean.getBodyType());
			session.setAttribute("complexion",
					partnerPreferenceInfoBean.getComplexion());
			session.setAttribute("food", partnerPreferenceInfoBean.getFood());
			session.setAttribute("maritalStatusId",
					partnerPreferenceInfoBean.getMaritalStatusId());
			session.setAttribute("fromHeightId",
					partnerPreferenceInfoBean.getFromHeightId());
			session.setAttribute("toHeightId",
					partnerPreferenceInfoBean.getToHeightId());
			session.setAttribute("religionId",
					partnerPreferenceInfoBean.getReligionId());
			session.setAttribute("casteId",
					partnerPreferenceInfoBean.getCasteId());
			session.setAttribute("motherTongueId",
					partnerPreferenceInfoBean.getMotherTongueId());
			session.setAttribute("educationId",
					partnerPreferenceInfoBean.getEducationId());
			session.setAttribute("occupationId",
					partnerPreferenceInfoBean.getOccupationId());
			session.setAttribute("countryId",
					partnerPreferenceInfoBean.getCountryId());
			session.setAttribute("stateId",
					partnerPreferenceInfoBean.getStateId());
			session.setAttribute("cityId",
					partnerPreferenceInfoBean.getCityId());

			partnerPreferenceBean.put("id", partnerPreferenceInfoBean.getId());
			partnerPreferenceBean.put("fromAge",
					partnerPreferenceInfoBean.getFromAge());
			partnerPreferenceBean.put("toAge",
					partnerPreferenceInfoBean.getToAge());

			loadRegister2Masters();
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadUpdatePersonalDetails() {
		HttpSession session = request.getSession();
		try {
			PersonalDetailsFormBean personalDetailsInfoBean = registerService
					.loadUpdatePersonalDetails((Long) session
							.getAttribute("userId"));

			session.setAttribute("casteId",
					personalDetailsInfoBean.getCasteId());
			session.setAttribute("motherTongueId",
					personalDetailsInfoBean.getMotherTongueId());
			session.setAttribute("weightId",
					personalDetailsInfoBean.getWeightId());
			session.setAttribute("botyType",
					personalDetailsInfoBean.getBodyType());
			session.setAttribute("complexion",
					personalDetailsInfoBean.getComplexion());
			session.setAttribute("physicalStatus",
					personalDetailsInfoBean.getPhysicalStatus());
			session.setAttribute("employedIn",
					personalDetailsInfoBean.getEmployedIn());
			session.setAttribute("currencyId",
					personalDetailsInfoBean.getCurrencyId());
			session.setAttribute("food", personalDetailsInfoBean.getFood());
			session.setAttribute("smoking",
					personalDetailsInfoBean.getSmoking());
			session.setAttribute("drinking",
					personalDetailsInfoBean.getDrinking());
			session.setAttribute("familyStatus",
					personalDetailsInfoBean.getFamilyStatus());
			session.setAttribute("familyType",
					personalDetailsInfoBean.getFamilyType());
			session.setAttribute("familyValues",
					personalDetailsInfoBean.getFamilyValues());
			session.setAttribute("fathersStatus",
					personalDetailsInfoBean.getFathersStatus());
			session.setAttribute("mothersStatus",
					personalDetailsInfoBean.getMothersStatus());
			session.setAttribute("numberOfBrothers",
					personalDetailsInfoBean.getNumberOfBrothers());
			session.setAttribute("brothersMarried",
					personalDetailsInfoBean.getBrothersMarried());
			session.setAttribute("numberOfSisters",
					personalDetailsInfoBean.getNumberOfSisters());
			session.setAttribute("sistersMarried",
					personalDetailsInfoBean.getSistersMarried());

			personalDetailsBean.put("id", personalDetailsInfoBean.getId());
			personalDetailsBean.put("monthlyIncome",
					personalDetailsInfoBean.getMonthlyIncome());
			personalDetailsBean.put("subCaste",
					personalDetailsInfoBean.getSubCaste());

			loadRegister2Masters();
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String sessionPasswordHash() {
		HttpSession session = request.getSession();
		try {
			LoginService loginService = new LoginService();
			LoginFormBean loginFormBean = loginService
					.sessionPasswordHash((Long) session.getAttribute("userId"));

			session.setAttribute("password", loginFormBean.getPassword());
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String loadRegisterMasters() {

		try {
			profileMap = commonMasterService.loadProfile();
			genderMap = commonMasterService.loadGender();
			maritalStatusMap = commonMasterService.loadMaritalStatus();
			heightMap = commonMasterService.loadHeight();
			educationMap = commonMasterService.loadEducation();
			religionMap = commonMasterService.loadReligion();
			occupationMap = commonMasterService.loadOccupation();
			countryMap = commonMasterService.loadCountry();
			/*motherTongueMap = commonMasterService.loadMotherTongue();
			orgMap = commonMasterService.loadOrg();
			roleMap = commonMasterService.loadRole();*/
			System.out.println("****" + profileMap);
			System.out.println("****" + genderMap);
			System.out.println("****" + maritalStatusMap);
			System.out.println("****" + heightMap);
			System.out.println("****" + religionMap);
			System.out.println("****" + educationMap);
			System.out.println("****" + religionMap);
			System.out.println("****" + occupationMap);
			System.out.println("****" + countryMap);
			/*System.out.println("****" + orgMap);
			System.out.println("****" + roleMap);*/

			return "success";
		} catch (Exception e) {
			// e.printStackTrace();
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

	public String loadRoleAndStatusMasters() {

		roleMap = commonMasterService.loadRole();
		statusMap = commonMasterService.loadStatus();
		System.out.println("****" + roleMap);
		System.out.println("****" + statusMap);

		return "success";
	}

	public String loadRegister2Masters() {

		maritalStatusMap = commonMasterService.loadMaritalStatus();
		heightMap = commonMasterService.loadHeight();
		religionMap = commonMasterService.loadReligion();
		motherTongueMap = commonMasterService.loadMotherTongue();
		weightMap = commonMasterService.loadWeight();
		educationMap = commonMasterService.loadEducation();
		occupationMap = commonMasterService.loadOccupation();
		currencyMap = commonMasterService.loadCurrency();
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

	public String loadStateAndCountryMasters() {

		HttpSession session = request.getSession(true);
		if (ActionContext.getContext().getName()
				.equals("wedding_hall_register")) {
			session.setAttribute("orgName", CommonConstants.WEDDING_HALLS);
		} else if (ActionContext.getContext().getName()
				.equals("studio_register")) {
			session.setAttribute("orgName", CommonConstants.STUDIOS);
		} else if (ActionContext.getContext().getName()
				.equals("decoration_register")) {
			session.setAttribute("orgName", CommonConstants.DECORATIONS);
		} else if (ActionContext.getContext().getName()
				.equals("beauty_parlour_register")) {
			session.setAttribute("orgName", CommonConstants.BEAUTY_PARLOURS);
		} else if (ActionContext.getContext().getName()
				.equals("jewel_shop_register")) {
			session.setAttribute("orgName", CommonConstants.JEWEL_SHOPS);
		} else if (ActionContext.getContext().getName()
				.equals("catering_register")) {
			session.setAttribute("orgName", CommonConstants.CATERINGS);
		} else if (ActionContext.getContext().getName()
				.equals("entertainment_register")) {
			session.setAttribute("orgName", CommonConstants.ENTERTAINMENTS);
		} else if (ActionContext.getContext().getName()
				.equals("textiles_register")) {
			session.setAttribute("orgName", CommonConstants.TEXTILES);
		}
		countryMap = commonMasterService.loadCountry();
		System.out.println("****" + countryMap);

		return "success";
	}

	public String checkUserAlreadyExist() {
		String userExist = commonMasterService.checkUserAlreadyExist(emailId.toLowerCase());
		if (userExist != null) {
			return "success";
		} else {
			setEmailId(null);
			return "error";
		}
	}

	public String loadAllUserRoleDetails() {
		List<UserProfileFormBean> userprofileList = commonMasterService
				.loadAllUserRoleDetails();
		Iterator<?> itr = userprofileList.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", obj[0]);
			map.put("fullName", obj[1]);
			map.put("email", obj[2]);
			map.put("mobile", obj[3]);
			map.put("roleId", obj[4]);
			map.put("statusId", obj[5]);
			map.put("role", obj[6]);
			map.put("status", obj[7]);
			map.put("profileId", obj[8]);

			userProfiles.add(map);
		}

		loadRoleAndStatusMasters();

		return "success";
	}
	
	public String loadMyAccountDetails() {
		HttpSession session = request.getSession(true);
		List<UpgradePlanFormBean> mAccountDetailsList = commonMasterService
				.loadMyAccountDetails((Long) session.getAttribute("userId"));
		Iterator<?> itr = mAccountDetailsList.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();
			Map<String, Object> map = new HashMap<String, Object>();
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

			myAccountDetails.add(map);
		}

		loadRoleAndStatusMasters();

		return "success";
	}

	public String updateUserRoleAndStatus() {
		HttpSession session = request.getSession(true);
		Long updatedBy = (Long) session.getAttribute("userId");
		String status = commonMasterService.updateUserRoleAndStatus(userId,
				userRole, userRoleDesc, userStatus, updatedBy);
		if (status != null) {
			return "success";
		} else {
			return "error";
		}
	}

	public String sendForgotPassword() {
		HttpSession session = request.getSession(true);
		try {
			UserProfileFormBean userProfile = registerService
					.loadUserProfileByEmail(emailId);
			String key = startPasswordReset(userProfile.getId());
			Properties props = new Properties();
			props.put("fullName", userProfile.getFullName());
			props.put("email", userProfile.getEmail());
			props.put("url", getText("url") + "/reset?key=" + key);
			MailMessage msg = new MailMessage(props, "pwreset.vm",
					userProfile.getEmail(), "Password reset request");
			msg.send();
			session.setAttribute("successMessage", "We've sent a password reset link to your email address.");
			session.setAttribute("hrefParamSuccess", "home");
			System.out.println("We've sent a password reset link to your email address.");
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "forgot_password");
			return "error";
		}
	}

	public String startPasswordReset(Long userId) throws Exception {
		String key = UUID.randomUUID().toString();
		Session conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		try {
			PwResetFormBean pwResetFormBean = new PwResetFormBean();
			conn.getNamedQuery("deletePwResetByUserId")
					.setLong("userId", userId).executeUpdate();
			pwResetFormBean.setUserId(userId);
			pwResetFormBean.setResetKey(key);
			conn.save(pwResetFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return key;
	}

	public String resetPassword() {
		HttpSession session = request.getSession(true);
		try {
			String pwdHash = CommonConstants.generateEncryptedPwd(userPassword);
			commonMasterService.resetPassword(userId, pwdHash);
			session.setAttribute("successMessage",
					"Your password has been changed...");
			session.setAttribute("hrefParamSuccess", "login");
			System.out.println("Your password has been changed...");
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "forgot_password");
			return "error";
		}
	}
	
	public String reSendCode() {
		HttpSession session = request.getSession(true);
		try {
			if(!getVerifyMobile().equals(session.getAttribute("mobile").toString())) {
				session.setAttribute("mobile", getVerifyMobile());
				String code = registerService
						.updateMobileVerificationCode((Long) session.getAttribute("userId"));
				session.setAttribute("verifyMobileNumber", code);
			}
			String msg = "Your Weddfix mobile verification code is "+session.getAttribute("verifyMobileNumber").toString();
			sms.sendSms(msg, getVerifyMobile());
			System.out.println("Verification code sent successfully...");
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String verifyCodeAndUpdateMobile() {
		HttpSession session = request.getSession(true);
		try {
			if(session.getAttribute("verifyMobileNumber").toString().equals(getVerifyCode())) {
				String status = registerService
						.verifyCodeAndUpdateMobile((Long) session.getAttribute("mobile"), (Long) session.getAttribute("userId"));
				if(status != null) {
					session.setAttribute("verifyedMobileNumber", true);
					verifyMsg = "success";
					return "success";
				} else {
					verifyMsg = "error";
					return "error";
				}
			} else {
				verifyMsg = "error";
				return "error";
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			verifyMsg = "error";
			return "error";
		}
	}

	public void prepare() throws Exception {
		userProfileFormBean = new UserProfileFormBean();
	}

	public UserProfileFormBean getModel() {
		return userProfileFormBean;
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

	public Map<Object, Object> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<Object, Object> countryMap) {
		this.countryMap = countryMap;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Map<String, Object> getUserProfileBean() {
		return userProfileBean;
	}

	public void setUserProfileBean(Map<String, Object> userProfileBean) {
		this.userProfileBean = userProfileBean;
	}

	public List<Map<String, Object>> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(List<Map<String, Object>> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserRole() {
		return userRole;
	}

	public void setUserRole(Long userRole) {
		this.userRole = userRole;
	}

	public Long getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Long userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public Map<Object, Object> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(Map<Object, Object> weightMap) {
		this.weightMap = weightMap;
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

	public Map<Object, Object> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(Map<Object, Object> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public Map<String, Object> getPartnerPreferenceBean() {
		return partnerPreferenceBean;
	}

	public void setPartnerPreferenceBean(
			Map<String, Object> partnerPreferenceBean) {
		this.partnerPreferenceBean = partnerPreferenceBean;
	}

	public Map<String, Object> getPersonalDetailsBean() {
		return personalDetailsBean;
	}

	public void setPersonalDetailsBean(Map<String, Object> personalDetailsBean) {
		this.personalDetailsBean = personalDetailsBean;
	}

	public Map<Object, Object> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Object, Object> statusMap) {
		this.statusMap = statusMap;
	}

	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	public List<Map<String, Object>> getMyAccountDetails() {
		return myAccountDetails;
	}

	public void setMyAccountDetails(List<Map<String, Object>> myAccountDetails) {
		this.myAccountDetails = myAccountDetails;
	}

	public String getVerifyMobile() {
		return verifyMobile;
	}

	public void setVerifyMobile(String verifyMobile) {
		this.verifyMobile = verifyMobile;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getVerifyMsg() {
		return verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}

}
