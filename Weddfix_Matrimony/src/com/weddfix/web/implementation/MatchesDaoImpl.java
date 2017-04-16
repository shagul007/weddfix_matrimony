package com.weddfix.web.implementation;

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
import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MatchesFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.interfaces.MatchesDao;
import com.weddfix.web.util.HibernateUtil;

/**
 * 
 * 
 * @author Shagul
 * 
 */
public class MatchesDaoImpl implements MatchesDao, SessionAware {

	Session conn;

	private static final Logger logger = Logger.getLogger(MatchesDaoImpl.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Long user;

	public Long saveMatchesDetails(MatchesFormBean matchesDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert matches details Method--------------");
			
			List<?> matchesDetails = conn
					  .getNamedQuery("getMatchesDetailsByProfileIdAndUserId")
					  .setLong("profileId", matchesDetailsFormBean.getProfileId()).setLong("userId", matchesDetailsFormBean.getUserId()).list();
			
			Iterator<?> itr = matchesDetails.iterator();
			MatchesFormBean matchesFormBean = null;
					
			while (itr.hasNext()) {
				matchesFormBean = new MatchesFormBean();
				Object[] obj = (Object[]) itr.next();
				matchesFormBean.setId(Long.parseLong(obj[0].toString()));
				matchesFormBean.setUserId(Long.parseLong(obj[2].toString()));
			}
			
			Transaction tx = conn.beginTransaction();
			if (matchesFormBean != null) {
				conn.getNamedQuery("updateMatchesWhoViewedMyProfileById")
						.setLong("id", matchesFormBean.getId())
						.setBoolean("newMatches", true)
						.setBoolean("whoViewedMyProfile", true)
						.setBoolean("recentlyViewedProfiles", true)
						.setLong("updatedBy",
								matchesFormBean.getUserId())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;
				tx.commit();

			} else {
				matchesDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(matchesDetailsFormBean);
				tx.commit();
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
	
	public Long saveShortlistedProfiles(MatchesFormBean matchesDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Save Shortlisted Profiles details Method--------------");
			
			List<?> matchesDetails = conn
					  .getNamedQuery("getMatchesDetailsByProfileIdAndUserId")
					  .setLong("profileId", matchesDetailsFormBean.getProfileId()).setLong("userId", matchesDetailsFormBean.getUserId()).list();
			
			Iterator<?> itr = matchesDetails.iterator();
			MatchesFormBean matchesFormBean = null;
					
			while (itr.hasNext()) {
				matchesFormBean = new MatchesFormBean();
				Object[] obj = (Object[]) itr.next();
				matchesFormBean.setId(Long.parseLong(obj[0].toString()));
				matchesFormBean.setUserId(Long.parseLong(obj[2].toString()));
			}
			
			Transaction tx = conn.beginTransaction();
			if (matchesFormBean != null) {
				conn.getNamedQuery("updateMatchesShortlistedById")
						.setLong("id", matchesFormBean.getId())
						.setBoolean("shortlisted",
								matchesDetailsFormBean.getShortlistedProfiles())
						.setLong("updatedBy",
								matchesFormBean.getUserId())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;
				tx.commit();

			} else {
				matchesDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(matchesDetailsFormBean);
				/*RoleDetailsFormBean roleDetailsFormBean = new RoleDetailsFormBean();
				roleDetailsFormBean.setUserId(user);
				roleDetailsFormBean.setRoleId(CommonConstants.USER_ROLE);
				roleDetailsFormBean.setDescription(CommonConstants.USER_DESC);
				roleDetailsFormBean.setCreatedBy(1l);
				roleDetailsFormBean.setCreatedDate(new Date());
				conn.save(roleDetailsFormBean);*/
				tx.commit();
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
	
	public Long saveSendInterestProfiles(MatchesFormBean matchesDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Save Send Interest Profiles details Method--------------");
			
			List<?> matchesDetails = conn
					  .getNamedQuery("getMatchesDetailsByProfileIdAndUserId")
					  .setLong("profileId", matchesDetailsFormBean.getProfileId()).setLong("userId", matchesDetailsFormBean.getUserId()).list();
			
			Iterator<?> itr = matchesDetails.iterator();
			MatchesFormBean matchesFormBean = null;
					
			while (itr.hasNext()) {
				matchesFormBean = new MatchesFormBean();
				Object[] obj = (Object[]) itr.next();
				matchesFormBean.setId(Long.parseLong(obj[0].toString()));
				matchesFormBean.setUserId(Long.parseLong(obj[2].toString()));
			}
			
			List<?> mailBoxDetails = conn
					  .getNamedQuery("getMailBoxDetailsByProfileIdAndUserId")
					  .setLong("profileId", matchesDetailsFormBean.getProfileId()).setLong("userId", matchesDetailsFormBean.getUserId()).list();
			
			Iterator<?> itr1 = mailBoxDetails.iterator();
			MailBoxFormBean mailBoxFormBean = null;
					
			while (itr1.hasNext()) {
				mailBoxFormBean = new MailBoxFormBean();
				Object[] obj = (Object[]) itr1.next();
				mailBoxFormBean.setId(Long.parseLong(obj[0].toString()));
				mailBoxFormBean.setUserId(Long.parseLong(obj[2].toString()));
			}
			
			Transaction tx = conn.beginTransaction();
			if (matchesFormBean != null) {
				conn.getNamedQuery("updateMatchesSendInterestedById")
				.setLong("id", matchesFormBean.getId())
				.setBoolean("viewedAndNotContacted",
						false)
				.setBoolean("sendMail",
						true)
				.setBoolean("sendInterested",
						matchesDetailsFormBean.getSendInterested())
				.setLong("updatedBy",
						matchesFormBean.getUserId())
				.setDate("updatedDate", new Date()).executeUpdate();
				if (mailBoxFormBean == null) {
					MailBoxFormBean mailBoxDetailsFormBean = new MailBoxFormBean();
					mailBoxDetailsFormBean.setUserId(matchesDetailsFormBean.getUserId());
					mailBoxDetailsFormBean.setProfileId(matchesDetailsFormBean.getProfileId());
					mailBoxDetailsFormBean.setInbox(true);
					mailBoxDetailsFormBean.setNewMail(true);
					mailBoxDetailsFormBean.setCreatedBy(matchesDetailsFormBean.getCreatedBy());
					mailBoxDetailsFormBean.setCreatedDate(new Date());
					conn.save(mailBoxDetailsFormBean);
				}
				user = 1L;
				tx.commit();

			} else {
				matchesDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(matchesDetailsFormBean);
				MailBoxFormBean mailBoxDetailsFormBean = new MailBoxFormBean();
				mailBoxDetailsFormBean.setUserId(matchesDetailsFormBean.getUserId());
				mailBoxDetailsFormBean.setProfileId(matchesDetailsFormBean.getProfileId());
				mailBoxDetailsFormBean.setInbox(true);
				mailBoxDetailsFormBean.setNewMail(true);
				mailBoxDetailsFormBean.setCreatedBy(matchesDetailsFormBean.getCreatedBy());
				mailBoxDetailsFormBean.setCreatedDate(new Date());
				conn.save(mailBoxDetailsFormBean);
				tx.commit();
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

	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadNewMatchesDetails(MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails, Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> newMatchesDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null 
					&& myPartnerPreferenceDetails.getMotherTongue() != null && myPartnerPreferenceDetails.getEducation() != null
					&& myPartnerPreferenceDetails.getOccupation() != null && myPartnerPreferenceDetails.getCountry() != null
					&& myPartnerPreferenceDetails.getState() != null && myPartnerPreferenceDetails.getCity() != null){
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByCity")
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
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByState")
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
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByCountry")
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
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByOccupation")
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
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByEducation")
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
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByMotherTongue")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setString("motherTongue", myPartnerPreferenceDetails.getMotherTongue())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null && myPartnerPreferenceDetails.getCaste() != null){
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByCaste")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setString("caste", myPartnerPreferenceDetails.getCaste())
						.setLong("userId", userId).list();
			} else if(myPartnerPreferenceDetails.getReligion() != null){
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByReligion")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("marutalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setString("religion", myPartnerPreferenceDetails.getReligion())
						.setLong("userId", userId).list();
			} else {
				newMatchesDetails = conn
						.getNamedQuery("getNewMatchesDetailsByAge")
						.setLong("fromAge", myPartnerPreferenceDetails.getFromAge()).setLong("toAge", myPartnerPreferenceDetails.getToAge())
						.setLong("fromHeightId", myPartnerPreferenceDetails.getFromHeightId()).setLong("toHeightId", myPartnerPreferenceDetails.getToHeightId())
						.setString("maritalStatus", myPartnerPreferenceDetails.getMaritalStatus()).setString("gender", myPartnerPreferenceDetails.getGender())
						.setLong("userId", userId).list();
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return newMatchesDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadShortlistedProfilesDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> shortlistedProfileDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			shortlistedProfileDetails = conn
					.getNamedQuery("getShortlistedProfileDetailsByUserId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
//			 e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return shortlistedProfileDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> viewedProfilesDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			viewedProfilesDetails = conn
					.getNamedQuery("getViewedProfilesByProfileId")
					.setLong("profileId", profileId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return viewedProfilesDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadViewedAndNotContactedProfileDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> viewedAndNotContactedProfileDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			viewedAndNotContactedProfileDetails = conn
					.getNamedQuery("getViewedAndNotContactedProfilesByUserId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return viewedAndNotContactedProfileDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadRecentlyViewedProfileDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> recentlyViewedProfileDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			recentlyViewedProfileDetails = conn
					.getNamedQuery("getRecentlyViewedProfilesByUserId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return recentlyViewedProfileDetails;
	}

	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> searchProfiles(
			Long fromAge, Long toAge, Long genderId, Long maritalStatusId,
			Long countryId, Long stateId, Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> searchMatchesDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			if(countryId != null && stateId != null){
				searchMatchesDetails = conn
						.getNamedQuery("getSearchMatchesDetailsByAgeOrFilter")
						.setLong("fromAge", fromAge).setLong("toAge", toAge)
						.setLong("maritalStatusId", maritalStatusId).setLong("genderId", genderId)
						.setLong("countryId", countryId).setLong("stateId", stateId)
						.setLong("userId", userId).list();
			} else if(countryId != null){
				searchMatchesDetails = conn
						.getNamedQuery("getSearchMatchesDetailsByCountry")
						.setLong("fromAge", fromAge).setLong("toAge", toAge)
						.setLong("maritalStatusId", maritalStatusId).setLong("genderId", genderId)
						.setLong("countryId", countryId)
						.setLong("userId", userId).list();
			} else if(stateId != null){
				searchMatchesDetails = conn
						.getNamedQuery("getSearchMatchesDetailsByState")
						.setLong("fromAge", fromAge).setLong("toAge", toAge)
						.setLong("maritalStatusId", maritalStatusId).setLong("genderId", genderId)
						.setLong("stateId", stateId)
						.setLong("userId", userId).list();
			} else {
				searchMatchesDetails = conn
						.getNamedQuery("getSearchMatchesDetailsByAge")
						.setLong("fromAge", fromAge).setLong("toAge", toAge)
						.setLong("maritalStatusId", maritalStatusId).setLong("genderId", genderId)
						.setLong("userId", userId).list();
			}

		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return searchMatchesDetails;
	}
	
	@SuppressWarnings("unchecked")
	public Long updateEmailAndMobileCount(Long userId, Long userProfileId) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Update account details Method--------------");
			
			Transaction tx = conn.beginTransaction();
			
			List<AccountDetailsFormBean> accountDetails = conn.getNamedQuery("getAccountDetailsEmailAndMobileCountByUserId")
			.setLong("userId", userId).setLong("profileId", userProfileId).list();
			
			Iterator<?> itr = accountDetails.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				
				boolean flag = false;
				if(obj[0] == null) {
					flag = false;
				} else if(Boolean.valueOf(obj[0].toString()) == true) {
					flag = true;
				}
				if(!flag) {
					Long emailCount = 0L;
					Long mailCount = 0L;
					if(Long.parseLong(obj[1].toString()) > 0) {
						emailCount = Long.parseLong(obj[1].toString())-1;
					} 
					if(Long.parseLong(obj[2].toString()) > 0) {
						mailCount = Long.parseLong(obj[2].toString())-1;
					} 
					conn.getNamedQuery("updateEmailAndMobileCountByUserId")
					.setLong("userId", userId)
					.setLong("emailCount", emailCount)
					.setLong("mobileCount", mailCount)
					.setLong("updatedBy", userId)
					.setDate("updatedDate", new Date()).executeUpdate();
					
					conn.getNamedQuery("updateMatchesViewedByUserId")
					.setLong("userId", userId)
					.setLong("profileId", userProfileId)
					.setBoolean("dontshowalreadyviewed", true)
					.setLong("updatedBy", userId)
					.setDate("updatedDate", new Date()).executeUpdate();
					
					user = 1L;
					tx.commit();
				}
			}
			

		} catch (HibernateException hibernateException) {

			hibernateException.printStackTrace();
			logger.error("Exception ocured while updating data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return user;
	}
	
	public void setSession(Map<String, Object> arg0) {

	}

}
