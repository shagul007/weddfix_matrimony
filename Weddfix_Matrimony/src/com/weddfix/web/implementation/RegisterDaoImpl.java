package com.weddfix.web.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.weddfix.web.formbean.AccountDetailsFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.RoleDetailsFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.interfaces.RegisterDao;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;
import com.weddfix.web.util.Util;

/**
 * 
 * 
 * @author Shagul
 * 
 */
public class RegisterDaoImpl implements RegisterDao, SessionAware {

	Session conn;

	private static final Logger logger = Logger
			.getLogger(RegisterDaoImpl.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Long user;

	public Long saveRegisterDetails(UserProfileFormBean userProfileFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert register details Method--------------");

			Transaction tx = conn.beginTransaction();
			if (userProfileFormBean.getId() != null) {
				conn.getNamedQuery("updateUserProfileDetailsById")
						.setLong("id", userProfileFormBean.getId())
						.setLong("userProfileId",
								userProfileFormBean.getUserProfileId())
						.setString("fullName",
								userProfileFormBean.getFullName())
						.setLong("genderId", userProfileFormBean.getGenderId())
						.setLong("maritalStatusId", userProfileFormBean.getMaritalStatusId())
						.setDate("dob", userProfileFormBean.getDob())
						.setLong("mobile", userProfileFormBean.getMobile())
						.setLong("heightId",
								userProfileFormBean.getHeightId())
						.setLong("educationId",
								userProfileFormBean.getEducationId())
						.setLong("occupationId",
								userProfileFormBean.getOccupationId())
						.setLong("religionId",
								userProfileFormBean.getReligionId())
						.setLong("cityId",
								userProfileFormBean.getCityId())
						.setLong("stateId",
								userProfileFormBean.getStateId())
						.setLong("countryId",
								userProfileFormBean.getCountryId())
						.setString("aboutYou",
								userProfileFormBean.getAboutYou())
						.setLong("updatedBy",
								userProfileFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				tx.commit();
				user = 1L;

			} else {
				userProfileFormBean.setEmail(userProfileFormBean.getEmail().toLowerCase());
				userProfileFormBean.setRoleId(CommonConstants.USER_ROLE);
				userProfileFormBean.setStatusId(CommonConstants.ACTIVE);
				userProfileFormBean.setCreatedBy(1l);
				userProfileFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(userProfileFormBean);
				tx.commit();
				Long profileId = 1000000 + user;
				conn.getNamedQuery("updateProfileId")
				.setLong("profileId", profileId).setLong("id", user).executeUpdate();
				Transaction tx1 = conn.beginTransaction();
				RoleDetailsFormBean roleDetailsFormBean = new RoleDetailsFormBean();
				roleDetailsFormBean.setUserId(user);
				roleDetailsFormBean.setRoleId(CommonConstants.USER_ROLE);
				roleDetailsFormBean.setDescription(CommonConstants.USER_DESC);
				roleDetailsFormBean.setCreatedBy(1l);
				roleDetailsFormBean.setCreatedDate(new Date());
				conn.save(roleDetailsFormBean);
				AccountDetailsFormBean accountDetailsFormBean = new AccountDetailsFormBean();
				accountDetailsFormBean.setProfileId(profileId);
				accountDetailsFormBean.setUserId(user);
				accountDetailsFormBean.setAccountType(CommonConstants.FREE_ID);
				accountDetailsFormBean.setIsProfileActive(true);
				accountDetailsFormBean.setIsProfileDeleted(false);
				accountDetailsFormBean.setShowMyProfilePicture(true);
				accountDetailsFormBean.setShowMyMobileNumber(true);
				accountDetailsFormBean.setShowMyEmailId(true);
				accountDetailsFormBean.setVerifyMobileNumber(String.valueOf(Util.generatePin()));
				accountDetailsFormBean.setVerifyEmailId(String.valueOf(Util.generatePin()));
				accountDetailsFormBean.setVerifyedMobileNumber(false);
				accountDetailsFormBean.setVerifyedEmailId(false);
				accountDetailsFormBean.setMobileCount(CommonConstants.ZERO);
				accountDetailsFormBean.setEmailCount(CommonConstants.ZERO);
				accountDetailsFormBean.setVideoCallCount(CommonConstants.ZERO);
				accountDetailsFormBean.setStatus(CommonConstants.ACTIVE);
				accountDetailsFormBean.setCreatedBy(user);
				accountDetailsFormBean.setCreatedDate(new Date());
				conn.save(accountDetailsFormBean);
				tx1.commit();
			}

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}

	public UserProfileFormBean loadUserProfileDetails(Long id) {
		UserProfileFormBean userProfile = new UserProfileFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn.getNamedQuery("getUserProfileById")
					.setLong("id", id).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				Date dob = null;
				String dobStr = null;
				try {
					dob = sdf.parse(obj[6].toString());
					sdf = new SimpleDateFormat("dd/MM/yyyy");
					dobStr = sdf.format(dob);
				} catch (ParseException e) {
					// e.printStackTrace();
				}
				userProfile.setId(Long.parseLong(obj[0].toString()));
				userProfile.setProfileId(Long.parseLong(obj[1].toString()));
				userProfile.setUserProfileId(Long.parseLong(obj[2].toString()));
				userProfile.setFullName(obj[3].toString());
				userProfile.setGenderId(Long.parseLong(obj[4].toString()));
				userProfile.setMaritalStatusId(Long.parseLong(obj[5].toString()));
				userProfile.setDobStr(dobStr);
				userProfile.setEmail(obj[7].toString());
				userProfile.setMobile(Long.parseLong(obj[10].toString()));
				userProfile.setHeightId(Long.parseLong(obj[11].toString()));
				userProfile
						.setEducationId(Long.parseLong(obj[12].toString()));
				userProfile.setOccupationId(Long.parseLong(obj[13].toString()));
				userProfile.setReligionId(Long.parseLong(obj[14].toString()));
				userProfile.setCityId(Long.parseLong(obj[15].toString()));
				userProfile.setStateId(Long.parseLong(obj[16].toString()));
				userProfile.setCountryId(Long.parseLong(obj[17].toString()));
				userProfile.setAboutYou(obj[18].toString());
				userProfile.setProfilePictureId(Long.parseLong(obj[19].toString()));
				userProfile.setBirthHoroScopePhotoId(Long.parseLong(obj[20].toString()));
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return userProfile;
	}

	public UserProfileFormBean loadUserProfileByEmail(String email) {
		UserProfileFormBean userProfile = new UserProfileFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn.getNamedQuery("getUserProfileByEmail")
					.setString("email", email).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				userProfile.setId(Long.parseLong(obj[0].toString()));
				userProfile.setProfileId(Long.parseLong(obj[1].toString()));
				userProfile.setFullName(obj[2].toString());
				userProfile.setEmail(obj[3].toString());
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return userProfile;
	}

	public Long savePersonalDetails(
			PersonalDetailsFormBean personalDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert personal details Method--------------");

			Transaction tx = conn.beginTransaction();
			if (personalDetailsFormBean.getId() != null) {
				conn.getNamedQuery("updatePersonalDetailsById")
						.setLong("id", personalDetailsFormBean.getId())
						.setLong("casteId", personalDetailsFormBean.getCasteId())
						.setString("subCaste",
								personalDetailsFormBean.getSubCaste())
						.setLong("weightId",
								personalDetailsFormBean.getWeightId())
						.setString("bodyType",
								personalDetailsFormBean.getBodyType())
						.setString("complexion",
								personalDetailsFormBean.getComplexion())
						.setString("physicalStatus",
								personalDetailsFormBean.getPhysicalStatus())
						.setString("employedIn",
								personalDetailsFormBean.getEmployedIn())
						.setLong("currencyId",
								personalDetailsFormBean.getCurrencyId())
						.setLong("monthlyIncome",
								personalDetailsFormBean.getMonthlyIncome())
						.setString("bodyType",
								personalDetailsFormBean.getBodyType())
						.setString("food", personalDetailsFormBean.getFood())
						.setString("smoking",
								personalDetailsFormBean.getSmoking())
						.setString("drinking",
								personalDetailsFormBean.getDrinking())
						.setString("familyStatus",
								personalDetailsFormBean.getFamilyStatus())
						.setString("familyType",
								personalDetailsFormBean.getFamilyType())
						.setString("familyValues",
								personalDetailsFormBean.getFamilyValues())
						.setString("fathersStatus",
								personalDetailsFormBean.getFathersStatus())
						.setString("mothersStatus",
								personalDetailsFormBean.getMothersStatus())
						.setString("numberOfBrothers",
								personalDetailsFormBean.getNumberOfBrothers())
						.setString("brothersMarried",
								personalDetailsFormBean.getBrothersMarried())
						.setString("numberOfSisters",
								personalDetailsFormBean.getNumberOfSisters())
						.setString("sistersMarried",
								personalDetailsFormBean.getSistersMarried())
						.setLong("updatedBy",
								personalDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			} else {
				personalDetailsFormBean.setStatusId(CommonConstants.ACTIVE);
				personalDetailsFormBean.setCreatedBy(personalDetailsFormBean.getUserId());
				personalDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(personalDetailsFormBean);
			}
			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}

	public MyPersonalDetailsFormBean loadPersonalDetailsByUserId(Long userId) {
		MyPersonalDetailsFormBean myPersonalDetails = new MyPersonalDetailsFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn
					.getNamedQuery("getPersonalDetailsByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				myPersonalDetails.setProfileId(Long.parseLong(obj[0].toString()));
				myPersonalDetails.setProfileFor(obj[1].toString());
				myPersonalDetails.setFullName(obj[2].toString());
				myPersonalDetails.setGender(obj[3].toString());
				myPersonalDetails.setMaritalStatus(obj[4].toString());
				myPersonalDetails.setAge(new Double((obj[5].toString())));
				myPersonalDetails.setDob(obj[6].toString());
				myPersonalDetails.setEmail(obj[7].toString());
				myPersonalDetails.setMobile(Long.parseLong(obj[8].toString()));
				myPersonalDetails.setHeight(obj[9].toString());
				myPersonalDetails.setEducation(obj[10].toString());
				myPersonalDetails.setOccupation(obj[11].toString());
				myPersonalDetails.setReligion(obj[12].toString());
				myPersonalDetails.setCountry(obj[13].toString());
				myPersonalDetails.setState(obj[14].toString());
				myPersonalDetails.setCity(obj[15].toString());
				myPersonalDetails.setAboutYou(obj[16].toString());
				if(obj[17] != null) {
					myPersonalDetails.setProfilePictureId(Long.parseLong(obj[17].toString()));
				}
				if(obj[18] != null) {
					myPersonalDetails.setCaste(obj[18].toString());
				}
				if(obj[19] != null) {
					myPersonalDetails.setSubCaste(obj[19].toString());
				}
				if(obj[20] != null) {
					myPersonalDetails.setMotherTongue(obj[20].toString());
				}
				if(obj[21] != null) {
					myPersonalDetails.setWeight(obj[21].toString());
				}
				if(obj[22] != null) {
					myPersonalDetails.setBodyType(obj[22].toString());
				}
				if(obj[23] != null) {
					myPersonalDetails.setComplexion(obj[23].toString());
				}
				if(obj[24] != null) {
					myPersonalDetails.setPhysicalStatus(obj[24].toString());
				}
				if(obj[25] != null) {
					myPersonalDetails.setEmployedIn(obj[25].toString());
				}
				if(obj[26] != null) {
					myPersonalDetails.setCurrency(obj[26].toString());
				}
				if(obj[27] != null) {
					myPersonalDetails.setMonthlyIncome(Long.parseLong(obj[27]
						.toString()));
				}
				if(obj[28] != null) {
					myPersonalDetails.setFood(obj[28].toString());
				}
				if(obj[29] != null) {
					myPersonalDetails.setSmoking(obj[29].toString());
				}
				if(obj[30] != null) {
					myPersonalDetails.setDrinking(obj[30].toString());
				}
				if(obj[31] != null) {
					myPersonalDetails.setFamilyStatus(obj[31].toString());
				}
				if(obj[32] != null) {
					myPersonalDetails.setFamilyType(obj[32].toString());
				}
				if(obj[33] != null) {
					myPersonalDetails.setFamilyValues(obj[33].toString());
				}
				if(obj[34] != null) {
					myPersonalDetails.setFathersStatus(obj[34].toString());
				}
				if(obj[35] != null) {
					myPersonalDetails.setMothersStatus(obj[35].toString());
				}
				if(obj[36] != null) {
					myPersonalDetails.setNumberOfBrothers(obj[36].toString());
				}
				if(obj[37] != null) {
					myPersonalDetails.setBrothersMarried(obj[37].toString());
				}
				if(obj[38] != null) {
					myPersonalDetails.setNumberOfSisters(obj[38].toString());
				}
				if(obj[39] != null) {
					myPersonalDetails.setSistersMarried(obj[39].toString());
				}
			}

		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return myPersonalDetails;
	}
	
	public MyPersonalDetailsFormBean loadViewProfileDetails(Long profileUserId, Long userProfileId, Long userId) {
		MyPersonalDetailsFormBean myPersonalDetails = new MyPersonalDetailsFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn
					.getNamedQuery("getViewPersonalDetailsByUserId")
					.setLong("profileUserId", profileUserId).setLong("userProfileId", userProfileId).setLong("userId", userId).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				myPersonalDetails.setProfileFor(obj[0].toString());
				myPersonalDetails.setFullName(obj[1].toString());
				myPersonalDetails.setGender(obj[2].toString());
				myPersonalDetails.setAge(new Double((obj[3].toString())));
				myPersonalDetails.setDob(obj[4].toString());
				myPersonalDetails.setEmail(obj[5].toString());
				myPersonalDetails.setMobile(Long.parseLong(obj[6].toString()));
				myPersonalDetails.setReligion(obj[7].toString());
				if(obj[8] != null) {
					myPersonalDetails.setMotherTongue(obj[8].toString());
				}
				myPersonalDetails
						.setProfileId(Long.parseLong(obj[9].toString()));
				myPersonalDetails.setMaritalStatus(obj[10].toString());
				if(obj[11] != null) {
					myPersonalDetails.setCaste(obj[11].toString());
				}
				myPersonalDetails.setCountry(obj[12].toString());
				myPersonalDetails.setState(obj[13].toString());
				myPersonalDetails.setCity(obj[14].toString());
				myPersonalDetails.setHeight(obj[15].toString());
				if(obj[16] != null) {
					myPersonalDetails.setWeight(obj[16].toString());
				}
				if(obj[17] != null) {
					myPersonalDetails.setBodyType(obj[17].toString());
				}
				if(obj[18] != null) {
				myPersonalDetails.setComplexion(obj[18].toString());
				}
				if(obj[19] != null) {
				myPersonalDetails.setPhysicalStatus(obj[19].toString());
				}
				myPersonalDetails.setEducation(obj[20].toString());
				myPersonalDetails.setOccupation(obj[21].toString());
				if(obj[22] != null) {
				myPersonalDetails.setEmployedIn(obj[22].toString());
				}
				if(obj[23] != null) {
				myPersonalDetails.setCurrency(obj[23].toString());
				}
				if(obj[24] != null) {
				myPersonalDetails.setMonthlyIncome(Long.parseLong(obj[24]
						.toString()));
				}
				if(obj[25] != null) {
				myPersonalDetails.setFood(obj[25].toString());
				}
				if(obj[26] != null) {
				myPersonalDetails.setSmoking(obj[26].toString());
				}
				if(obj[27] != null) {
				myPersonalDetails.setDrinking(obj[27].toString());
				}
				if(obj[28] != null) {
				myPersonalDetails.setFamilyStatus(obj[28].toString());
				}
				if(obj[29] != null) {
				myPersonalDetails.setFamilyType(obj[29].toString());
				}
				if(obj[30] != null) {
				myPersonalDetails.setFamilyValues(obj[30].toString());
				}
				myPersonalDetails.setAboutYou(obj[31].toString());
				if (obj[32] != null) {
					myPersonalDetails.setProfilePictureId(Long.parseLong(obj[32].toString()));
				}
				if (obj[33] != null) {
					myPersonalDetails.setAccepted(Boolean.valueOf(obj[33].toString()));
				} else {
					myPersonalDetails.setAccepted(false);
				}
				if (obj[34] != null) {
					myPersonalDetails.setContactRequested(true);
				} else {
					myPersonalDetails.setContactRequested(false);
				}
				if (obj[35] != null) {
					myPersonalDetails.setNotInterested(Boolean.valueOf(obj[35].toString()));
				} else {
					myPersonalDetails.setNotInterested(false);
				}
				if(obj[36] != null) {
					myPersonalDetails.setSubCaste(obj[36].toString());
				}
				if(obj[37] != null) {
					myPersonalDetails.setFathersStatus(obj[37].toString());
				}
				if(obj[38] != null) {
					myPersonalDetails.setMothersStatus(obj[38].toString());
				}
				if(obj[39] != null) {
					myPersonalDetails.setNumberOfBrothers(obj[39].toString());
				}
				if(obj[40] != null) {
					myPersonalDetails.setBrothersMarried(obj[40].toString());
				}
				if(obj[41] != null) {
					myPersonalDetails.setNumberOfSisters(obj[41].toString());
				}
				if(obj[42] != null) {
					myPersonalDetails.setSistersMarried(obj[42].toString());
				}
				if(obj[43] != null) {
					myPersonalDetails.setEmailCount(Long.parseLong(obj[43].toString()));
				}
				if(obj[44] != null) {
					myPersonalDetails.setMobileCount(Long.parseLong(obj[44].toString()));
				}
				if (obj[45] != null) {
					myPersonalDetails.setDontshowalreadyViewed(Boolean.valueOf(obj[45].toString()));
				} else {
					myPersonalDetails.setDontshowalreadyViewed(false);
				}
			}

		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return myPersonalDetails;
	}

	public Long savePartnerPreferenceDetails(
			PartnerPreferenceFormBean partnerPreferenceFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert personal details Method--------------");

			Transaction tx = conn.beginTransaction();
			if (partnerPreferenceFormBean.getId() != null) {
				conn.getNamedQuery("updatePartnerPreferenceById")
						.setLong("id", partnerPreferenceFormBean.getId())
						.setLong("fromAge",
								partnerPreferenceFormBean.getFromAge())
						.setLong("toAge", partnerPreferenceFormBean.getToAge())
						.setLong("maritalStatusId",
								partnerPreferenceFormBean.getMaritalStatusId())
						.setString("bodyType",
								partnerPreferenceFormBean.getBodyType())
						.setString("complexion",
								partnerPreferenceFormBean.getComplexion())
						.setLong("fromHeightId",
								partnerPreferenceFormBean.getFromHeightId())
						.setLong("toHeightId",
								partnerPreferenceFormBean.getToHeightId())
						.setString("food", partnerPreferenceFormBean.getFood())
						.setLong("religionId",
								partnerPreferenceFormBean.getReligionId())
						.setLong("casteId",
								partnerPreferenceFormBean.getCasteId())
						.setLong("motherTongueId",
								partnerPreferenceFormBean.getMotherTongueId())
						.setLong("educationId",
								partnerPreferenceFormBean.getEducationId())
						.setLong("occupationId",
								partnerPreferenceFormBean.getOccupationId())
						.setLong("countryId",
								partnerPreferenceFormBean.getCountryId())
						.setLong("stateId",
								partnerPreferenceFormBean.getStateId())
						.setLong("cityId",
								partnerPreferenceFormBean.getCityId())
						.setLong("updatedBy",
								partnerPreferenceFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			} else {
				partnerPreferenceFormBean.setStatusId(CommonConstants.ACTIVE);
				partnerPreferenceFormBean.setCreatedBy(1l);
				partnerPreferenceFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(partnerPreferenceFormBean);
			}
			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public MyPartnerPreferenceDetailsFormBean loadPartnerPreferenceDetails(Long userId) {
		MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails = new MyPartnerPreferenceDetailsFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn
					.getNamedQuery("getPartnerPreferenceDetailsByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				myPartnerPreferenceDetails.setFromAge(Long.parseLong(obj[0].toString()));
				myPartnerPreferenceDetails.setToAge(Long.parseLong(obj[1].toString()));
				myPartnerPreferenceDetails.setMaritalStatus(obj[2].toString());
				myPartnerPreferenceDetails.setBodyType(obj[3].toString());
				myPartnerPreferenceDetails.setComplexion(obj[4].toString());
				if(obj[5] != null) {
					myPartnerPreferenceDetails.setFromHeight(obj[5].toString());
				}
				myPartnerPreferenceDetails.setFood(obj[6].toString());
				if(obj[7] != null) {
					myPartnerPreferenceDetails.setReligion(obj[7].toString());
				}
				if(obj[8] != null) {
					myPartnerPreferenceDetails.setCaste(obj[8].toString());
				}
				if(obj[9] != null) {
					myPartnerPreferenceDetails.setMotherTongue(obj[9].toString());
				}
				if(obj[10] != null) {
					myPartnerPreferenceDetails.setEducation(obj[10].toString());
				}
				if(obj[11] != null) {
					myPartnerPreferenceDetails.setOccupation(obj[11].toString());
				}
				if(obj[12] != null) {
					myPartnerPreferenceDetails.setCountry(obj[12].toString());
				}
				if(obj[13] != null) {
					myPartnerPreferenceDetails.setState(obj[13].toString());
				}
				if(obj[14] != null) {
					myPartnerPreferenceDetails.setCity(obj[14].toString());
				}
				myPartnerPreferenceDetails.setGender(obj[15].toString());
				if(obj[16] != null) {
					myPartnerPreferenceDetails.setToHeight(obj[16].toString());
				}
				if(obj[17] != null) {
					myPartnerPreferenceDetails.setFromHeightId(Long.parseLong(obj[17].toString()));
				}
				if(obj[18] != null) {
					myPartnerPreferenceDetails.setToHeightId(Long.parseLong(obj[18].toString()));
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return myPartnerPreferenceDetails;
	}
	
	public PartnerPreferenceFormBean loadUpdatePartnerPreferenceDetails(Long userId) {
		PartnerPreferenceFormBean partnerPreferenceDetails = new PartnerPreferenceFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn
					.getNamedQuery("getUpdatePartnerPreferenceDetailsByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				partnerPreferenceDetails.setId(Long.parseLong(obj[0].toString()));
				partnerPreferenceDetails.setFromAge(Long.parseLong(obj[1].toString()));
				partnerPreferenceDetails.setToAge(Long.parseLong(obj[2].toString()));
				partnerPreferenceDetails.setMaritalStatusId(Long.parseLong(obj[3].toString()));
				partnerPreferenceDetails.setBodyType(obj[4].toString());
				partnerPreferenceDetails.setComplexion(obj[5].toString());
				partnerPreferenceDetails.setFromHeightId(Long.parseLong(obj[6].toString()));
				partnerPreferenceDetails.setFood(obj[7].toString());
				partnerPreferenceDetails.setReligionId(Long.parseLong(obj[8].toString()));
				partnerPreferenceDetails.setCasteId(Long.parseLong(obj[9].toString()));
				partnerPreferenceDetails.setMotherTongueId(Long.parseLong(obj[10].toString()));
				partnerPreferenceDetails.setEducationId(Long.parseLong(obj[11].toString()));
				partnerPreferenceDetails.setOccupationId(Long.parseLong(obj[12].toString()));
				partnerPreferenceDetails.setCountryId(Long.parseLong(obj[13].toString()));
				partnerPreferenceDetails.setStateId(Long.parseLong(obj[14].toString()));
				partnerPreferenceDetails.setCityId(Long.parseLong(obj[15].toString()));
				partnerPreferenceDetails.setToHeightId(Long.parseLong(obj[16].toString()));
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return partnerPreferenceDetails;
	}
	
	public PersonalDetailsFormBean loadUpdatePersonalDetails(Long userId) {
		PersonalDetailsFormBean personalDetails = new PersonalDetailsFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> userProfiles = conn
					.getNamedQuery("getUpdatePersonalDetailsByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = userProfiles.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				personalDetails.setId(Long.parseLong(obj[0].toString()));
				personalDetails.setCasteId(Long.parseLong(obj[1].toString()));
				personalDetails.setSubCaste(obj[2].toString());
				personalDetails.setMotherTongueId(Long.parseLong(obj[3].toString()));
				personalDetails.setWeightId(Long.parseLong(obj[4].toString()));
				personalDetails.setBodyType(obj[5].toString());
				personalDetails.setComplexion(obj[6].toString());
				personalDetails.setPhysicalStatus(obj[7].toString());
				personalDetails.setEmployedIn(obj[8].toString());
				personalDetails.setCurrencyId(Long.parseLong(obj[9].toString()));
				personalDetails.setMonthlyIncome(Long.parseLong(obj[10]
						.toString()));
				personalDetails.setFood(obj[11].toString());
				personalDetails.setSmoking(obj[12].toString());
				personalDetails.setDrinking(obj[13].toString());
				personalDetails.setFamilyStatus(obj[14].toString());
				personalDetails.setFamilyType(obj[15].toString());
				personalDetails.setFamilyValues(obj[16].toString());
				personalDetails.setFathersStatus(obj[17].toString());
				personalDetails.setMothersStatus(obj[18].toString());
				personalDetails.setNumberOfBrothers(obj[19].toString());
				personalDetails.setBrothersMarried(obj[20].toString());
				personalDetails.setNumberOfSisters(obj[21].toString());
				personalDetails.setSistersMarried(obj[22].toString());
				personalDetails.setReligionId(Long.parseLong(obj[23].toString()));
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return personalDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadSimilarPartnerPreferenceDetails(MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails, Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> similarPartnerPreferenceDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null
					&& myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getCountry() != null
					&& myPartnerPreferenceDetails.getState() != null && myPartnerPreferenceDetails.getCity() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByCity")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setString("education", myPartnerPreferenceDetails.getEducation())
						.setString("occupation", myPartnerPreferenceDetails.getOccupation())
						.setString("country", myPartnerPreferenceDetails.getCountry())
						.setString("state", myPartnerPreferenceDetails.getState())
						.setString("city", myPartnerPreferenceDetails.getCity())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null
					&& myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getCountry() != null
					&& myPartnerPreferenceDetails.getState() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByState")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setString("education", myPartnerPreferenceDetails.getEducation())
						.setString("occupation", myPartnerPreferenceDetails.getOccupation())
						.setString("country", myPartnerPreferenceDetails.getCountry())
						.setString("state", myPartnerPreferenceDetails.getState())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null
					&& myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getCountry() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByCountry")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setString("education", myPartnerPreferenceDetails.getEducation())
						.setString("occupation", myPartnerPreferenceDetails.getOccupation())
						.setString("country", myPartnerPreferenceDetails.getCountry())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null
					&& myPartnerPreferenceDetails.getOccupation() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByOccupation")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setString("education", myPartnerPreferenceDetails.getEducation())
						.setString("occupation", myPartnerPreferenceDetails.getOccupation())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByEducation")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setString("education", myPartnerPreferenceDetails.getEducation())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByMotherTongue")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByCaste")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null){
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByReligion")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setLong("userId", userId).list();
			} else {
				similarPartnerPreferenceDetails = conn
						.getNamedQuery("getSimilarPartnerPreferenceDetailsByAge")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setLong("userId", userId).list();
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return similarPartnerPreferenceDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> whoViewedMyProfileDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			whoViewedMyProfileDetails = conn
					.getNamedQuery("getWhoViewedMyProfileDetailsByProfileId")
					.setLong("profileId", profileId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return whoViewedMyProfileDetails;
	}
	
	public Long deactivateProfile(AccountDetailsFormBean accountDetailsFormBean, Long userId) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update deactivate profile Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateDeactivateProfileByUserId")
						.setLong("deactivateProfileDays", accountDetailsFormBean.getDeactivateProfileDays())
						.setDate("deactivatedProfileDate", new Date())
						.setDate("activateProfileDate", accountDetailsFormBean.getActivateProfileDate())
						.setBoolean("isProfileActive",
								accountDetailsFormBean.getIsProfileActive())
						.setLong("userId", userId)
						.setLong("updatedBy",
								accountDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public Long activateProfile(AccountDetailsFormBean accountDetailsFormBean, Long userId) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update activate profile Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateActivateProfileByUserId")
						.setBoolean("isProfileActive",
								accountDetailsFormBean.getIsProfileActive())
						.setLong("userId", userId)
						.setLong("updatedBy",
								accountDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public Long deleteProfile(AccountDetailsFormBean accountDetailsFormBean, Long userId) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update delete profile Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateDeleteProfileByUserId")
						.setString("deleteProfileReason", accountDetailsFormBean.getDeleteProfileReason())
						.setBoolean("isProfileDeleted", true)
						.setLong("userId", userId)
						.setLong("updatedBy",
								accountDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				conn.getNamedQuery("updateUserProfileInActiveByUserId")
						.setLong("statusId", CommonConstants.INACTIVE)
						.setLong("userId", userId)
						.setLong("updatedBy", accountDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public Long updateMyPrivacy(AccountDetailsFormBean accountDetailsFormBean, Long userId) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update delete profile Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updatePrivacySettingsByUserId")
						.setBoolean("showmyProfilepicture", accountDetailsFormBean.getShowMyProfilePicture())
						.setLong("userId", userId)
						.setLong("updatedBy",
								accountDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<UpgradePlanFormBean> loadUpgradePlanDetails() {
		List<UpgradePlanFormBean> upgradePlanDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			upgradePlanDetails = conn
					.getNamedQuery("getUpgradePlanDetails").list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return upgradePlanDetails;
	}
	
	public String updateMobileVerificationCode(Long userId) {
		String verifyCode = String.valueOf(Util.generatePin());
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update verification code Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateMobileVerificationCodeByUserId")
						.setString("verifyCode", verifyCode)
						.setLong("userId", userId)
						.setLong("updatedBy", userId)
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return verifyCode;
	}
	
	public String verifyCodeAndUpdateMobile(Long mobile, Long userId) {
		String status = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update verification code Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("verifyCodeAndUpdateMobileByUserId")
						.setLong("mobile", mobile)
						.setLong("userId", userId)
						.setLong("updatedBy", userId)
						.setDate("updatedDate", new Date()).executeUpdate();
				conn.getNamedQuery("updateVerifyedMobileNumberByUserId")
						.setBoolean("verifyedMobileNumber", true)
						.setLong("userId", userId)
						.setLong("updatedBy", userId)
						.setDate("updatedDate", new Date()).executeUpdate();
				status = "success";

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return status;
	}
	
	public String updateMobileVerificationCodeDetails(Long userId) {
		String verifyCode = String.valueOf(Util.generatePin());
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update verification code details Method--------------");

			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateMobileVerificationCodeDetailsByUserId")
						.setString("verifyCode", verifyCode)
						.setBoolean("verifyedMobileNumber", false)
						.setLong("userId", userId)
						.setLong("updatedBy", userId)
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;

			tx.commit();

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return verifyCode;
	}
	
	public void setSession(Map<String, Object> arg0) {

	}

}
