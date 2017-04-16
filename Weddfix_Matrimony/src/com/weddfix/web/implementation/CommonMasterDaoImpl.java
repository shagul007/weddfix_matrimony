package com.weddfix.web.implementation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionSupport;
import com.weddfix.web.formbean.CartDetailsFormBean;
import com.weddfix.web.formbean.CasteFormBean;
import com.weddfix.web.formbean.CityFormBean;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.formbean.CountryFormBean;
import com.weddfix.web.formbean.CurrencyFormBean;
import com.weddfix.web.formbean.EducationFormBean;
import com.weddfix.web.formbean.GenderFormBean;
import com.weddfix.web.formbean.HeightFormBean;
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.MaritalStatusFormBean;
import com.weddfix.web.formbean.MotherTonqueFormBean;
import com.weddfix.web.formbean.OccupationFormBean;
import com.weddfix.web.formbean.OrganizationFormBean;
import com.weddfix.web.formbean.ProfileFormBean;
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.formbean.PwResetFormBean;
import com.weddfix.web.formbean.ReligionFormBean;
import com.weddfix.web.formbean.RoleFormBean;
import com.weddfix.web.formbean.StateFormBean;
import com.weddfix.web.formbean.StatusFormBean;
import com.weddfix.web.formbean.SubscribersFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.formbean.WeightFormBean;
import com.weddfix.web.interfaces.CommonMasterDao;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;

/**
 * 
 * 
 * @author Shagul Hameed
 * 
 */
public class CommonMasterDaoImpl extends ActionSupport implements CommonMasterDao, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Session conn;

	private Map<Object, Object> profileMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> genderMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> religionMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> motherTonqueMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> orgMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> roleMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> statusMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> maritalStatusMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> heightMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> weightMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> educationMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> occupationMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> currencyMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> countryMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> stateMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> cityMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> casteMasterMap = new HashMap<Object, Object>();

	List<ProfileFormBean> profileMasterList;
	List<GenderFormBean> genderMasterList;
	List<ReligionFormBean> religionMasterList;
	List<MotherTonqueFormBean> motherTonqueMasterList;
	List<RoleFormBean> roleMasterList;
	List<OrganizationFormBean> orgMasterList;
	List<StatusFormBean> statusMasterList;
	List<MaritalStatusFormBean> maritalStatusMasterList;
	List<HeightFormBean> heightMasterList;
	List<WeightFormBean> weightMasterList;
	List<EducationFormBean> educationMasterList;
	List<OccupationFormBean> occupationMasterList;
	List<CurrencyFormBean> currencyMasterList;
	List<CountryFormBean> countryMasterList;
	List<StateFormBean> stateMasterList;
	List<CityFormBean> cityMasterList;
	List<CasteFormBean> casteMasterList;
	List<UpgradePlanFormBean> upgradePlanMasterList;

	ProfileFormBean profileMasterFormBean;
	GenderFormBean genderMasterFormBean;
	ReligionFormBean religionMasterFormBean;
	MotherTonqueFormBean motherTonqueMasterFormBean;
	OrganizationFormBean orgMasterFormBean;
	RoleFormBean roleMasterFormBean;
	StatusFormBean statusMasterFormBean;
	MaritalStatusFormBean maritalStatusMasterFormBean;
	HeightFormBean heightMasterFormBean;
	WeightFormBean weightMasterFormBean;
	EducationFormBean educationMasterFormBean;
	OccupationFormBean occupationMasterFormBean;
	CurrencyFormBean currencyMasterFormBean;
	CountryFormBean countryMasterFormBean;
	StateFormBean stateMasterFormBean;
	CityFormBean cityMasterFormBean;
	CasteFormBean casteMasterFormBean;
	UpgradePlanFormBean upgradePlanMasterFormBean;

	private static final Logger logger = Logger
			.getLogger(CommonMasterDaoImpl.class);

	public Map<Object, Object> loadProfileMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getProfileList = conn.getNamedQuery("getProfileMaster")
					.list();
			Iterator<?> itr = getProfileList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				profileMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(profileMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadGenderMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getGenderList = conn.getNamedQuery("getGenderMaster").list();
			Iterator<?> itr = getGenderList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				genderMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(genderMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadGenderById(Long genderId) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getGenderList = conn.getNamedQuery("getGenderMasterById").setLong("genderId", genderId).list();
			Iterator<?> itr = getGenderList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				genderMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(genderMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadReligionMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getReligionList = conn.getNamedQuery("getReligionMaster")
					.list();
			Iterator<?> itr = getReligionList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				religionMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(religionMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadMotherTonqueMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getMotherTonqueList = conn.getNamedQuery("getMotherTonqueMaster")
					.list();
			Iterator<?> itr = getMotherTonqueList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				motherTonqueMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(motherTonqueMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadRoleMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getRoleList = conn.getNamedQuery("getRoleMaster").list();
			Iterator<?> itr = getRoleList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				roleMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(roleMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadStatusMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getStatusList = conn.getNamedQuery("getStatusMaster").list();
			Iterator<?> itr = getStatusList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				statusMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(statusMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadOrgMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getOrgList = conn.getNamedQuery("getOrgMaster").list();
			Iterator<?> itr = getOrgList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				orgMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(orgMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadMaritalStatusMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getMaritalStatusList = conn.getNamedQuery("getMaritalStatusMaster").list();
			Iterator<?> itr = getMaritalStatusList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				maritalStatusMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(maritalStatusMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadHeightMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getHeightList = conn.getNamedQuery("getHeightMaster").list();
			Iterator<?> itr = getHeightList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				heightMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(heightMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadWeightMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getWeightList = conn.getNamedQuery("getWeightMaster").list();
			Iterator<?> itr = getWeightList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				weightMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(weightMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadEducationMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getEducationList = conn.getNamedQuery("getEducationMaster").list();
			Iterator<?> itr = getEducationList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				educationMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(educationMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadOccupationMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getOccupationList = conn.getNamedQuery("getOccupationMaster").list();
			Iterator<?> itr = getOccupationList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				occupationMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(occupationMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadCurrencyMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getCurrencyList = conn.getNamedQuery("getCurrencyMaster").list();
			Iterator<?> itr = getCurrencyList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				currencyMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(currencyMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadStateMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getStateList = conn.getNamedQuery("getStatesMaster").list();
			Iterator<?> itr = getStateList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				stateMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(stateMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadStateMaster(Long countryId) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getStateList = conn.getNamedQuery("getStateMaster").setLong("countryId", countryId).list();
			Iterator<?> itr = getStateList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				stateMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(stateMasterMap);
		return sortedMap;
	}

	public Map<Object, Object> loadCountryMaster() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getCountryList = conn.getNamedQuery("getCountryMaster")
					.list();
			Iterator<?> itr = getCountryList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				countryMasterMap.put(obj[0], obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(countryMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadCityMaster(Long stateId) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getCityList = conn.getNamedQuery("getCityMaster").setLong("stateId", stateId).list();
			Iterator<?> itr = getCityList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				cityMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(cityMasterMap);
		return sortedMap;
	}
	
	public Map<Object, Object> loadCasteMaster(Long religionId) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> getCasteList = conn.getNamedQuery("getCasteMaster").setLong("religionId", religionId).list();
			Iterator<?> itr = getCasteList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				casteMasterMap.put(obj[0], obj[1]);
				logger.info("DAO IMPL =" + obj[0] + "" + obj[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		Map<Object, Object> sortedMap = sortMapByValues(casteMasterMap);
		return sortedMap;
	}

	public LoginFormBean getUserProfileByEmail(String email) {
		LoginFormBean loginFormBean = new LoginFormBean();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getUserProfileByEmail")
					.setString("email", email.toLowerCase()).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				loginFormBean.setId(Long.parseLong(obj[0].toString()));
				if(obj[1] != null) {
					loginFormBean.setProfileId(Long.parseLong(obj[1].toString()));
				}
				loginFormBean.setFullName(obj[2].toString());
				loginFormBean.setEmail(obj[3].toString());
				loginFormBean.setPassword(obj[4].toString());
				loginFormBean.setPasswordHash(obj[5].toString());
				loginFormBean.setRole(obj[6].toString());
				loginFormBean.setStatus(obj[7].toString());
				if (obj[8] != null) {
					loginFormBean.setAccountType(obj[8].toString());
				}
				if (obj[9] != null) {
					loginFormBean.setDeactivateProfileDays(Long.parseLong(obj[9].toString()));
				}
				if (obj[10] != null) {
					loginFormBean.setDeactivatedProfileDate(format.parse(obj[10].toString()));
				}
				if (obj[11] != null) {
					loginFormBean.setActivateProfileDate(format.parse(obj[11].toString()));
				}
				if (obj[12] != null) {
					loginFormBean.setIsProfileActive(Boolean.valueOf(obj[12].toString()));
				}
				if (obj[13] != null) {
					loginFormBean.setDeleteProfileReason(obj[13].toString());
				}
				if (obj[14] != null) {
					loginFormBean.setIsProfileDeleted(Boolean.valueOf(obj[14].toString()));
				}
				
				if (obj[15] != null) {
					loginFormBean.setShowMyProfilePicture(Boolean.valueOf(obj[15].toString()));
				}
				if (obj[16] != null) {
					loginFormBean.setShowMyMobileNumber(Boolean.valueOf(obj[16].toString()));
				}
				if (obj[17] != null) {
					loginFormBean.setShowMyEmailId(Boolean.valueOf(obj[17].toString()));
				}
				if (obj[18] != null) {
					loginFormBean.setVerifyMobileNumber(obj[18].toString());
				}
				if (obj[19] != null) {
					loginFormBean.setVerifyEmailId(obj[19].toString());
				}
				if (obj[20] != null) {
					loginFormBean.setVerifyedMobileNumber(Boolean.valueOf(obj[20].toString()));
				}
				if (obj[21] != null) {
					loginFormBean.setVerifyedEmailId(Boolean.valueOf(obj[21].toString()));
				}
				if (obj[22] != null) {
					loginFormBean.setEmailCount(Long.parseLong(obj[22].toString()));
				}
				if (obj[23] != null) {
					loginFormBean.setMobileCount(Long.parseLong(obj[23].toString()));
				}
				if (obj[24] != null) {
					loginFormBean.setVideoCallCount(Long.parseLong(obj[24].toString()));
				}
				loginFormBean.setReligionId(Long.parseLong(obj[25].toString()));
				if (obj[26] != null) {
					loginFormBean.setPartnerPreferenceId(Long.parseLong(obj[26].toString()));
				}
				if (obj[27] != null) {
					loginFormBean.setMyPlanId(Long.parseLong(obj[27].toString()));
				}
				loginFormBean.setMobile(Long.parseLong(obj[28].toString()));
				loginFormBean.setGenderId(Long.parseLong(obj[29].toString()));
			}
			if (loginFormBean != null) {
				return loginFormBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return loginFormBean;
	}

	public String checkUserAlreadyExist(String email) {
		String checkUser = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getUserProfileByEmail")
					.setString("email", email).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				checkUser = obj[2].toString();
			}
			if (checkUser != null) {
				return checkUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return checkUser;
	}
	
	public String checkPartnerPreferenceAlreadyExist(Long userId) {
		String checkUser = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getPartnerPreferenceByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				checkUser = obj[0].toString();
			}
			if (checkUser != null) {
				return checkUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return checkUser;
	}
	
	public String checkPersonalDetailsAlreadyExist(Long userId) {
		String checkUser = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getMyPersonalDetailsByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				checkUser = obj[0].toString();
			}
			if (checkUser != null) {
				return checkUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return checkUser;
	}

	public String checkSubscriberEmailAlreadyExist(String email) {
		String checkUser = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getSubscriberByEmail")
					.setString("email", email)
					.setLong("statusId", CommonConstants.ACTIVE).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				checkUser = obj[1].toString();
			}
			if (checkUser != null) {
				return checkUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return checkUser;
	}

	Long id;

	public Long saveCommentsDetails(CommentsFormBean commentsFormBean) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert comment details Method--------------");

			Transaction tx = conn.beginTransaction();
			commentsFormBean.setCreatedDate(new Date());
			id = (Long) conn.save(commentsFormBean);

			tx.commit();

		} catch (HibernateException hibernateException) {

			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}

	public Long saveSubscriberDetails(SubscribersFormBean subscribersFormBean) {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert subscriber details Method--------------");

			Transaction tx = conn.beginTransaction();
			subscribersFormBean.setSubscriberEmail(subscribersFormBean.getSubscriberEmail().toLowerCase());
			subscribersFormBean.setStatusId(CommonConstants.ACTIVE);
			subscribersFormBean.setCreatedDate(new Date());
			id = (Long) conn.save(subscribersFormBean);

			tx.commit();

		} catch (HibernateException hibernateException) {

			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public List<UserProfileFormBean> loadAllUserRoleDetails() {
		List<UserProfileFormBean> userProfileList = new ArrayList<UserProfileFormBean>();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			userProfileList = conn.getNamedQuery("getAllUserProfiles").list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return userProfileList;
	}
	
	@SuppressWarnings("unchecked")
	public List<UpgradePlanFormBean> loadMyAccountDetails(Long userId) {
		List<UpgradePlanFormBean> myAccountDetailList = new ArrayList<UpgradePlanFormBean>();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			myAccountDetailList = conn.getNamedQuery("getAccountDetailsByUserId").setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return myAccountDetailList;
	}
	
	public String updateUserRoleAndStatus(Long userId, Long userRole, String userRoleDesc,
			Long userStatus, Long updatedBy) {
		String status = null;
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		try {
			Boolean isProfileDeleted = false;
			if(userStatus == CommonConstants.ACTIVE){
				isProfileDeleted = false;
			} else {
				isProfileDeleted = true;
			}
			conn.getNamedQuery("updateUserRoleAndStatusById")
					.setLong("roleId", userRole)
					.setLong("statusId", userStatus)
					.setLong("id", userId)
					.setLong("updatedBy", updatedBy)
					.setDate("updatedDate", new Date()).executeUpdate();
			conn.getNamedQuery("updateIsProfileDeletedByUserId")
					.setBoolean("isProfileDeleted", isProfileDeleted)
					.setLong("userId", userId)
					.setLong("updatedBy", updatedBy)
					.setDate("updatedDate", new Date()).executeUpdate();
			conn.getNamedQuery("updateRoleDetailsByUserId")
					.setLong("userId", userId)
					.setLong("roleId", userRole)
					.setString("description", userRoleDesc)
					.setLong("updatedBy", updatedBy)
					.setDate("updatedDate", new Date()).executeUpdate();
			tx.commit();
			status = "success";

		} catch (Exception e) {
			tx.rollback();
			status = null;
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return status;
	}

	public PwResetFormBean getUserByPasswordResetKey(String key)
			throws Exception {
		PwResetFormBean pwReset = new PwResetFormBean();

		conn = HibernateUtil.getSessionFactory().openSession();
		try {
			List<?> userProfile = conn.getNamedQuery("getUserIdByResetKey")
					.setString("resetKey", key).list();
			if (userProfile.isEmpty()) {
				pwReset = null;
			} else {
				Iterator<?> itr = userProfile.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					pwReset.setUserId(Long.parseLong(obj[1].toString()));
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return pwReset;
	}

	public String resetPassword(Long userId, String pwdHash) {
		String status = null;
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		try {
			conn.getNamedQuery("updateResetPasswordById")
					.setString("password", pwdHash)
					.setString("passwordHash", pwdHash).setLong("id", userId)
					.setLong("updatedBy", userId)
					.setDate("updatedDate", new Date())
					.executeUpdate();
			conn.getNamedQuery("deletePwResetByUserId")
					.setLong("userId", userId).executeUpdate();
			tx.commit();
			status = "success";

		} catch (Exception e) {
			tx.rollback();
			status = null;
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<RoleFormBean> loadRoleMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			roleMasterList = conn.createSQLQuery(
					"select id, rolename, roledesc, isactive from role").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return roleMasterList;
	}

	public void updateRoleMaster(Long id, String roleName, String roleDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			roleMasterFormBean = (RoleFormBean) conn
					.get(RoleFormBean.class, id);
			roleMasterFormBean.setRoleName(roleName);
			roleMasterFormBean.setRoleDesc(roleDesc);
			roleMasterFormBean.setIsActive(isActive);
			roleMasterFormBean.setUpdatedBy(updatedBy);
			roleMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addRoleMaster(RoleFormBean roleMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(roleMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteRoleMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			roleMasterFormBean = (RoleFormBean) conn
					.get(RoleFormBean.class, id);
			conn.delete(roleMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfileFormBean> loadProfileMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			profileMasterList = conn.createSQLQuery(
					"select id, profilename, profiledesc, isactive from profile").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return profileMasterList;
	}

	public void updateProfileMaster(Long id, String profileName, String profileDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			profileMasterFormBean = (ProfileFormBean) conn
					.get(ProfileFormBean.class, id);
			profileMasterFormBean.setProfileName(profileName);
			profileMasterFormBean.setProfileDesc(profileDesc);
			profileMasterFormBean.setIsActive(isActive);
			profileMasterFormBean.setUpdatedBy(updatedBy);
			profileMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addProfileMaster(ProfileFormBean profileMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(profileMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteProfileMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			profileMasterFormBean = (ProfileFormBean) conn
					.get(ProfileFormBean.class, id);
			conn.delete(profileMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<GenderFormBean> loadGenderMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			genderMasterList = conn.createSQLQuery(
					"select id, gendername, genderdesc, isactive from gender").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return genderMasterList;
	}

	public void updateGenderMaster(Long id, String genderName, String genderDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			genderMasterFormBean = (GenderFormBean) conn
					.get(GenderFormBean.class, id);
			genderMasterFormBean.setGenderName(genderName);
			genderMasterFormBean.setGenderDesc(genderDesc);
			genderMasterFormBean.setIsActive(isActive);
			genderMasterFormBean.setUpdatedBy(updatedBy);
			genderMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addGenderMaster(GenderFormBean genderMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(genderMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteGenderMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			genderMasterFormBean = (GenderFormBean) conn
					.get(GenderFormBean.class, id);
			conn.delete(genderMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReligionFormBean> loadReligionMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			religionMasterList = conn.createSQLQuery(
					"select id, religionname, religiondesc, isactive from religion").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return religionMasterList;
	}

	public void updateReligionMaster(Long id, String religionName, String religionDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			religionMasterFormBean = (ReligionFormBean) conn
					.get(ReligionFormBean.class, id);
			religionMasterFormBean.setReligionName(religionName);
			religionMasterFormBean.setReligionDesc(religionDesc);
			religionMasterFormBean.setIsActive(isActive);
			religionMasterFormBean.setUpdatedBy(updatedBy);
			religionMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addReligionMaster(ReligionFormBean religionMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(religionMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteReligionMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			religionMasterFormBean = (ReligionFormBean) conn
					.get(ReligionFormBean.class, id);
			conn.delete(religionMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MotherTonqueFormBean> loadMotherTonqueMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			motherTonqueMasterList = conn.createSQLQuery(
					"select id, mothertonque, mothertonquedesc, isactive from mothertonque").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return motherTonqueMasterList;
	}

	public void updateMotherTonqueMaster(Long id, String motherTonque, String motherTonqueDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			motherTonqueMasterFormBean = (MotherTonqueFormBean) conn
					.get(MotherTonqueFormBean.class, id);
			motherTonqueMasterFormBean.setMotherTonque(motherTonque);
			motherTonqueMasterFormBean.setMotherTonqueDesc(motherTonqueDesc);
			motherTonqueMasterFormBean.setIsActive(isActive);
			motherTonqueMasterFormBean.setUpdatedBy(updatedBy);
			motherTonqueMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addMotherTonqueMaster(MotherTonqueFormBean motherTonqueMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(motherTonqueMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteMotherTonqueMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			motherTonqueMasterFormBean = (MotherTonqueFormBean) conn
					.get(MotherTonqueFormBean.class, id);
			conn.delete(motherTonqueMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusFormBean> loadStatusMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			statusMasterList = conn.createSQLQuery(
					"select id, statusname, statusdesc, isactive from status").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return statusMasterList;
	}

	public void updateStatusMaster(Long id, String statusName, String statusDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			statusMasterFormBean = (StatusFormBean) conn
					.get(StatusFormBean.class, id);
			statusMasterFormBean.setStatusName(statusName);
			statusMasterFormBean.setStatusDesc(statusDesc);
			statusMasterFormBean.setIsActive(isActive);
			statusMasterFormBean.setUpdatedBy(updatedBy);
			statusMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addStatusMaster(StatusFormBean statusMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(statusMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteStatusMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			statusMasterFormBean = (StatusFormBean) conn
					.get(StatusFormBean.class, id);
			conn.delete(statusMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OrganizationFormBean> loadOrganizationMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			orgMasterList = conn.createSQLQuery(
					"select id, orgname, orgdesc, isactive from organization").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return orgMasterList;
	}

	public void updateOrganizationMaster(Long id, String orgName, String orgDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			orgMasterFormBean = (OrganizationFormBean) conn
					.get(OrganizationFormBean.class, id);
			orgMasterFormBean.setOrgName(orgName);
			orgMasterFormBean.setOrgDesc(orgDesc);
			orgMasterFormBean.setIsActive(isActive);
			orgMasterFormBean.setUpdatedBy(updatedBy);
			orgMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addOrganizationMaster(OrganizationFormBean organizationMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(organizationMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteOrganizationMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			orgMasterFormBean = (OrganizationFormBean) conn
					.get(OrganizationFormBean.class, id);
			conn.delete(orgMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MaritalStatusFormBean> loadMaritalStatusMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			maritalStatusMasterList = conn.createSQLQuery(
					"select id, maritalStatusname, maritalstatusdesc, isactive from maritalstatus").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return maritalStatusMasterList;
	}

	public void updateMaritalStatusMaster(Long id, String maritalStatusName, String maritalStatusDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			maritalStatusMasterFormBean = (MaritalStatusFormBean) conn
					.get(MaritalStatusFormBean.class, id);
			maritalStatusMasterFormBean.setMaritalStatusName(maritalStatusName);
			maritalStatusMasterFormBean.setMaritalStatusDesc(maritalStatusDesc);
			maritalStatusMasterFormBean.setIsActive(isActive);
			maritalStatusMasterFormBean.setUpdatedBy(updatedBy);
			maritalStatusMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addMaritalStatusMaster(MaritalStatusFormBean maritalStatusMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(maritalStatusMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteMaritalStatusMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			maritalStatusMasterFormBean = (MaritalStatusFormBean) conn
					.get(MaritalStatusFormBean.class, id);
			conn.delete(maritalStatusMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<HeightFormBean> loadHeightMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			heightMasterList = conn.createSQLQuery(
					"select id, feetinches, feetinchesdesc, isactive from height").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return heightMasterList;
	}

	public void updateHeightMaster(Long id, String feetInches, String feetInchesDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			heightMasterFormBean = (HeightFormBean) conn
					.get(HeightFormBean.class, id);
			heightMasterFormBean.setFeetInches(feetInches);
			heightMasterFormBean.setFeetInchesDesc(feetInchesDesc);
			heightMasterFormBean.setIsActive(isActive);
			heightMasterFormBean.setUpdatedBy(updatedBy);
			heightMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addHeightMaster(HeightFormBean heightMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(heightMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteHeightMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			heightMasterFormBean = (HeightFormBean) conn
					.get(HeightFormBean.class, id);
			conn.delete(heightMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WeightFormBean> loadWeightMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			weightMasterList = conn.createSQLQuery(
					"select id, weight, weightdesc, isactive from weight").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return weightMasterList;
	}

	public void updateWeightMaster(Long id, String weight, String weightDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			weightMasterFormBean = (WeightFormBean) conn
					.get(WeightFormBean.class, id);
			weightMasterFormBean.setWeight(weight);
			weightMasterFormBean.setWeightDesc(weightDesc);
			weightMasterFormBean.setIsActive(isActive);
			weightMasterFormBean.setUpdatedBy(updatedBy);
			weightMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addWeightMaster(WeightFormBean weightMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(weightMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteWeightMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			weightMasterFormBean = (WeightFormBean) conn
					.get(WeightFormBean.class, id);
			conn.delete(weightMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EducationFormBean> loadEducationMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			educationMasterList = conn.createSQLQuery(
					"select id, education, educationdesc, isactive from education").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return educationMasterList;
	}

	public void updateEducationMaster(Long id, String education, String educationDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			educationMasterFormBean = (EducationFormBean) conn
					.get(EducationFormBean.class, id);
			educationMasterFormBean.setEducation(education);
			educationMasterFormBean.setEducationDesc(educationDesc);
			educationMasterFormBean.setIsActive(isActive);
			educationMasterFormBean.setUpdatedBy(updatedBy);
			educationMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addEducationMaster(EducationFormBean educationMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(educationMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteEducationMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			educationMasterFormBean = (EducationFormBean) conn
					.get(EducationFormBean.class, id);
			conn.delete(educationMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OccupationFormBean> loadOccupationMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			occupationMasterList = conn.createSQLQuery(
					"select id, occupation, occupationdesc, isactive from occupation").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return occupationMasterList;
	}

	public void updateOccupationMaster(Long id, String occupation, String occupationDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			occupationMasterFormBean = (OccupationFormBean) conn
					.get(OccupationFormBean.class, id);
			occupationMasterFormBean.setOccupation(occupation);
			occupationMasterFormBean.setOccupationDesc(occupationDesc);
			occupationMasterFormBean.setIsActive(isActive);
			occupationMasterFormBean.setUpdatedBy(updatedBy);
			occupationMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addOccupationMaster(OccupationFormBean occupationMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(occupationMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteOccupationMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			occupationMasterFormBean = (OccupationFormBean) conn
					.get(OccupationFormBean.class, id);
			conn.delete(occupationMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrencyFormBean> loadCurrencyMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			currencyMasterList = conn.createSQLQuery(
					"select id, currency, currencydesc, isactive from currency").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return currencyMasterList;
	}

	public void updateCurrencyMaster(Long id, String currency, String currencyDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			currencyMasterFormBean = (CurrencyFormBean) conn
					.get(CurrencyFormBean.class, id);
			currencyMasterFormBean.setCurrency(currency);
			currencyMasterFormBean.setCurrencyDesc(currencyDesc);
			currencyMasterFormBean.setIsActive(isActive);
			currencyMasterFormBean.setUpdatedBy(updatedBy);
			currencyMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addCurrencyMaster(CurrencyFormBean currencyMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(currencyMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteCurrencyMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			currencyMasterFormBean = (CurrencyFormBean) conn
					.get(CurrencyFormBean.class, id);
			conn.delete(currencyMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CountryFormBean> loadCountryMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			countryMasterList = conn.createSQLQuery(
					"select id, countrycode, countryname, countrydesc, isactive from country").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return countryMasterList;
	}

	public void updateCountryMaster(Long id, String countryCode, String countryName, String countryDesc,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			countryMasterFormBean = (CountryFormBean) conn
					.get(CountryFormBean.class, id);
			countryMasterFormBean.setCountryCode(countryCode);
			countryMasterFormBean.setCountryName(countryName);
			countryMasterFormBean.setCountryDesc(countryDesc);
			countryMasterFormBean.setIsActive(isActive);
			countryMasterFormBean.setUpdatedBy(updatedBy);
			countryMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addCountryMaster(CountryFormBean countryMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(countryMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteCountryMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			countryMasterFormBean = (CountryFormBean) conn
					.get(CountryFormBean.class, id);
			conn.delete(currencyMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StateFormBean> loadStateMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			stateMasterList = conn.createSQLQuery(
					"SELECT s.id, s.countryid, s.statename, s.isactive, c.countryname FROM state s JOIN country c ON c.id = s.countryid").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return stateMasterList;
	}

	public void updateStateMaster(Long id, String stateName, Long countryId,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			stateMasterFormBean = (StateFormBean) conn
					.get(StateFormBean.class, id);
			stateMasterFormBean.setStateName(stateName);
//			stateMasterFormBean.setStateDesc(stateDesc);
			stateMasterFormBean.setCountryId(countryId);
			stateMasterFormBean.setIsActive(isActive);
			stateMasterFormBean.setUpdatedBy(updatedBy);
			stateMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addStateMaster(StateFormBean stateMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(stateMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteStateMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			stateMasterFormBean = (StateFormBean) conn
					.get(StateFormBean.class, id);
			conn.delete(stateMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CityFormBean> loadCityMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			cityMasterList = conn.createSQLQuery(
					"SELECT c.id, c.stateid, c.cityname, c.isactive, s.statename FROM city c JOIN state s ON s.id = c.stateid").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return cityMasterList;
	}

	public void updateCityMaster(Long id, String cityName, Long stateId,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			cityMasterFormBean = (CityFormBean) conn
					.get(CityFormBean.class, id);
			cityMasterFormBean.setCityName(cityName);
//			cityMasterFormBean.setCityDesc(cityDesc);
			cityMasterFormBean.setStateId(stateId);
			cityMasterFormBean.setIsActive(isActive);
			cityMasterFormBean.setUpdatedBy(updatedBy);
			cityMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addCityMaster(CityFormBean cityMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(cityMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteCityMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			cityMasterFormBean = (CityFormBean) conn
					.get(CityFormBean.class, id);
			conn.delete(cityMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CasteFormBean> loadCasteMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			casteMasterList = conn.createSQLQuery(
					"SELECT c.id, c.religionid, c.castename, c.isactive, r.religionname FROM caste c JOIN religion r ON r.id = c.religionid").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return casteMasterList;
	}

	public void updateCasteMaster(Long id, String casteName, Long religionId,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			casteMasterFormBean = (CasteFormBean) conn
					.get(CasteFormBean.class, id);
			casteMasterFormBean.setCasteName(casteName);
//			casteMasterFormBean.setCasteDesc(casteDesc);
			casteMasterFormBean.setReligionId(religionId);
			casteMasterFormBean.setIsActive(isActive);
			casteMasterFormBean.setUpdatedBy(updatedBy);
			casteMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addCasteMaster(CasteFormBean casteMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(casteMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteCasteMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			casteMasterFormBean = (CasteFormBean) conn
					.get(CasteFormBean.class, id);
			conn.delete(casteMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UpgradePlanFormBean> loadUpgradePlanMasterList() {
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			upgradePlanMasterList = conn.createSQLQuery(
					"SELECT id, planName, amount, validity, emailCount, mobileCount, videoCallCount, expressInterest, profileHighlight, viewProfile, protectPhoto, getSMSAlert, isActive FROM upgradeplandetails").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return upgradePlanMasterList;
	}

	public void updateUpgradePlanMaster(Long id, String planName, Long amount, Long validity, Long emailCount, Long mobileCount,
			Long videoCallCount, Boolean expressInterest, Boolean profileHighlight, Boolean viewProfile, Boolean protectPhoto, Boolean getSMSAlert,
			Boolean isActive, Long updatedBy) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			upgradePlanMasterFormBean = (UpgradePlanFormBean) conn
					.get(UpgradePlanFormBean.class, id);
			upgradePlanMasterFormBean.setPlanName(planName);
			upgradePlanMasterFormBean.setAmount(amount);
			upgradePlanMasterFormBean.setValidity(validity);
			upgradePlanMasterFormBean.setEmailCount(emailCount);
			upgradePlanMasterFormBean.setMobileCount(mobileCount);
			upgradePlanMasterFormBean.setVideoCallCount(videoCallCount);
			upgradePlanMasterFormBean.setExpressInterest(expressInterest);
			upgradePlanMasterFormBean.setProfileHighlight(profileHighlight);
			upgradePlanMasterFormBean.setViewProfile(viewProfile);
			upgradePlanMasterFormBean.setProtectPhoto(protectPhoto);
			upgradePlanMasterFormBean.setGetSMSAlert(getSMSAlert);
			upgradePlanMasterFormBean.setIsActive(isActive);
			upgradePlanMasterFormBean.setUpdatedBy(updatedBy);
			upgradePlanMasterFormBean.setUpdatedDate(new Date());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void addUpgradePlanMaster(UpgradePlanFormBean upgradePlanMasterFormBean) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = conn.beginTransaction();
			conn.save(upgradePlanMasterFormBean);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}

	public void deleteUpgradePlanMaster(Long id) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = conn.beginTransaction();
			upgradePlanMasterFormBean = (UpgradePlanFormBean) conn
					.get(UpgradePlanFormBean.class, id);
			conn.delete(upgradePlanMasterFormBean);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
	}
	
	public void setSession(Map<String, Object> arg0) {

	}

	private static Map<Object, Object> sortMapByValues(Map<Object, Object> heightMasterMap2) {
        
        Set<Entry<Object, Object>> mapEntries = heightMasterMap2.entrySet();
        
        /*System.out.println("Values and Keys before sorting ");
        for(Entry<Object, Object> entry : mapEntries) {
            System.out.println(entry.getValue() + " - "+ entry.getKey());
        }*/
        
        // used linked list to sort, because insertion of elements in linked list is faster than an array list. 
        List<Entry<Object, Object>> aList = new LinkedList<Entry<Object, Object>>(mapEntries);

        // sorting the List
        Collections.sort(aList, new Comparator<Entry<Object, Object>>() {

            public int compare(Entry<Object, Object> ele1,
                    Entry<Object, Object> ele2) {
                
                return ((String) ele1.getValue()).compareTo((String) ele2.getValue());
            }
        });
        
        // Storing the list into Linked HashMap to preserve the order of insertion. 
        Map<Object, Object> aMap2 = new LinkedHashMap<Object, Object>();
        for(Entry<Object, Object> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }
        
        // printing values after soring of map
        /*System.out.println("Value " + " - " + "Key");
        for(Entry<Object, Object> entry : aMap2.entrySet()) {
            System.out.println(entry.getValue() + " - " + entry.getKey());
        }*/
		return aMap2;
        
    }
	
	@SuppressWarnings("unchecked")
	public List<PromotionDetailsFormBean> loadPromotionDetails() {
		List<PromotionDetailsFormBean> promotionDetailsList = new ArrayList<PromotionDetailsFormBean>();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			promotionDetailsList = conn.getNamedQuery("getPromotionDetails").list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return promotionDetailsList;
	}
	
	Long user;
	
	public Long savePromotionDetails(PromotionDetailsFormBean promotionDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert promotion details Method--------------");

			Transaction tx = conn.beginTransaction();
			promotionDetailsFormBean.setStatusId(CommonConstants.ACTIVE);
			promotionDetailsFormBean.setCreatedDate(new Date());
			user = (Long) conn.save(promotionDetailsFormBean);
			tx.commit();

		} catch (Exception hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public Long deletePromotion(Long promoId) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		Long id = null;
		try {
			conn.getNamedQuery("deletePromotionById")
					.setLong("id", promoId).executeUpdate();
			tx.commit();
			id = 1L;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public Long sendPromotion(String promoEmailId, String promoCode, String sendPromoForAll, Long userId) {
		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		Long id = null;
		
		List<PromotionDetailsFormBean> promtionDetails = conn.getNamedQuery("getPromotionDetailsByPromoCode")
		.setString("promoCode", promoCode).list();
		
		try {
		if(!promtionDetails.isEmpty()) {
		
		Properties props = new Properties();
		Iterator<?> itr = promtionDetails.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
	        props.put("validity", obj[0].toString());
	        props.put("discount", obj[1].toString());
		}
		
		com.weddfix.web.util.MailMessage msg = null;
		
		String sentTo = null;
		if(sendPromoForAll.equals("true")) {
			sentTo = "All";
			List<String> emails = conn.getNamedQuery("getAllEmails").list();
			for(String email : emails) {
			props.put("promocode", promoCode);
	        props.put("email", email);
	        props.put("url", getText("url")+ "/promotion_check_valid.jsp?promocode="+promoCode);
	        msg = new com.weddfix.web.util.MailMessage(props, "promotion.vm", email, "Your Weddfix Promo Code");
	        msg.send();
			}
		} else {
			sentTo = promoEmailId;
			props.put("promocode", promoCode);
	        props.put("email", promoEmailId);
	        props.put("url", getText("url")+ "/promotion_check_valid.jsp?promocode="+promoCode);
	        msg = new com.weddfix.web.util.MailMessage(props, "promotion.vm", promoEmailId,"Your Weddfix Promo Code");
	        msg.send();
		}
		
			conn.getNamedQuery("updatePromoDetailsByPromoCode")
					.setString("emailTo", sentTo).setDate("sentDate", new Date()).setLong("updatedBy", userId).setDate("updatedDate", new Date()).setString("promoCode", promoCode).executeUpdate();
			tx.commit();
			id = 1L;
		}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CartDetailsFormBean> loadCartDetails(Long userId) {
		List<CartDetailsFormBean> cartDetailsList = new ArrayList<CartDetailsFormBean>();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			cartDetailsList = conn.getNamedQuery("getCartDetails").setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return cartDetailsList;
	}
	
	public Long saveCartDetails(CartDetailsFormBean cartDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert cart details Method--------------");

			Transaction tx = conn.beginTransaction();
			
			if(cartDetailsFormBean.getUpdatedBy() != null) {
				conn.getNamedQuery("updateCartDetailsById")
				.setLong("id", cartDetailsFormBean.getId())
				.setLong("planId",
						cartDetailsFormBean.getPlanId())
				.setLong("updatedBy",
						cartDetailsFormBean.getUpdatedBy())
				.setDate("updatedDate", new Date()).executeUpdate();
				tx.commit();
				user = 1L;
			} else {
				cartDetailsFormBean.setStatusId(CommonConstants.ACTIVE);
				cartDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(cartDetailsFormBean);
				tx.commit();
			}

		} catch (Exception hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<PromotionDetailsFormBean> validatePromoCode(String promoCode) {
		List<PromotionDetailsFormBean> promotionDetailsList = new ArrayList<PromotionDetailsFormBean>();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			promotionDetailsList = conn.getNamedQuery("validatePromoCode").setString("promoCode", promoCode).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return promotionDetailsList;
	}
	
	public void updateAccountDetails(String txnId, Long planId, Long userId, String acceptedPromoCode, Long profileId) {

		try {
			
			conn = HibernateUtil.getSessionFactory().openSession();
			
			logger.info("-----------Update account details Method--------------");
			
			Transaction tx = conn.beginTransaction();

			@SuppressWarnings("unchecked")
			List<UpgradePlanFormBean> planDetails = conn.getNamedQuery("getPlanDetailsByPlanId")
					.setLong("planId", planId).list();
			
			Iterator<?> itr = planDetails.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
		        conn.getNamedQuery("updateAccountDetailsByUserId")
		        .setLong("accountType", planId)
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
		        if(acceptedPromoCode != null) {
			        conn.getNamedQuery("updateAcceptedPromoDetailsByUserId")
			        .setString("acceptedPromoCode", acceptedPromoCode)
			        .setLong("userId", userId)
			        .setLong("profileId", profileId)
			        .setDate("acceptedDate", new Date())
			        .setLong("updatedBy", userId)
			        .setDate("updatedDate", new Date()).executeUpdate();
		        }
		        
		        tx.commit();
		        user = 1L;
			}
			
		} catch (Exception hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
	}
}
