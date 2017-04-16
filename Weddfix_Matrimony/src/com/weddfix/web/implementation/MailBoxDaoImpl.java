package com.weddfix.web.implementation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.interfaces.MailBoxDao;
import com.weddfix.web.util.HibernateUtil;

/**
 * 
 * 
 * @author Shagul
 * 
 */
public class MailBoxDaoImpl implements MailBoxDao, SessionAware {

	Session conn;

	private static final Logger logger = Logger.getLogger(MailBoxDaoImpl.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Long user;

	public Long saveMailBoxDetails(MailBoxFormBean mailBoxDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert matches details Method--------------");
			
			List<?> matchesDetails = conn
					  .getNamedQuery("getMatchesDetailsByProfileIdAndUserId")
					  .setLong("profileId", mailBoxDetailsFormBean.getProfileId()).setLong("userId", mailBoxDetailsFormBean.getUserId()).list();
			
			Transaction tx = conn.beginTransaction();
			if (matchesDetails != null) {
				/*conn.getNamedQuery("updateUserProfileById")
						.setLong("id", matchesDetailsFormBean.getId())
						.setLong("profileId",
								matchesDetailsFormBean.getProfileId())
						.setString("fullName",
								matchesDetailsFormBean.getFullName())
						.setLong("genderId",
								matchesDetailsFormBean.getGenderId())
						.setDate("dob", matchesDetailsFormBean.getDob())
						.setLong("mobile", matchesDetailsFormBean.getMobile())
						.setLong("religionId",
								matchesDetailsFormBean.getReligionId())
						.setLong("motherTonqueId",
								matchesDetailsFormBean.getMotherTonqueId())
						.setLong("updatedBy",
								matchesDetailsFormBean.getUpdatedBy())
						.setDate("updatedDate", new Date()).executeUpdate();
				user = 1L;
				tx.commit();*/

			} else {
				mailBoxDetailsFormBean.setCreatedDate(new Date());
				user = (Long) conn.save(mailBoxDetailsFormBean);
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
	
	public Long updateReadMailDetails(MailBoxFormBean mailBoxDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Insert matches details Method--------------");
			
			Transaction tx = conn.beginTransaction();
			conn.getNamedQuery("updateReadMailByUserIdAndProfileId")
						.setLong("userId", mailBoxDetailsFormBean.getUserId())
						.setLong("profileId",
								mailBoxDetailsFormBean.getProfileId())
						.setBoolean("readMail", true)
						.setLong("updatedBy",
								mailBoxDetailsFormBean.getUpdatedBy())
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
	
	public Long saveAcceptedProfile(MailBoxFormBean mailBoxDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Save Accepted Profile details Method--------------");
			
			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateAcceptedMailByUserIdAndProfileId")
						.setLong("userId", mailBoxDetailsFormBean.getUserId())
						.setLong("profileId", mailBoxDetailsFormBean.getProfileId())
						.setBoolean("accepted", mailBoxDetailsFormBean.getAccepted())
						.setLong("updatedBy",
								mailBoxDetailsFormBean.getUpdatedBy())
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
	
	public Long saveNotInterestedProfile(MailBoxFormBean mailBoxDetailsFormBean) {

		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			logger.info("-----------Save Not Interested Profile details Method--------------");
			
			Transaction tx = conn.beginTransaction();
				conn.getNamedQuery("updateNotInterestedProfileByUserIdAndProfileId")
						.setLong("userId", mailBoxDetailsFormBean.getUserId())
						.setLong("profileId", mailBoxDetailsFormBean.getProfileId())
						.setBoolean("notInterested", mailBoxDetailsFormBean.getNotInterested())
						.setLong("updatedBy",
								mailBoxDetailsFormBean.getUpdatedBy())
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
	public List<MyPartnerPreferenceDetailsFormBean> loadInboxDetails(Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> inboxDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			inboxDetails = conn
					.getNamedQuery("getInboxDetailsByProfileId")
					.setLong("profileId", profileId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return inboxDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadAcceptedDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> acceptedDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			acceptedDetails = conn
					.getNamedQuery("getAcceptedDetailsByProfileId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return acceptedDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadNotInterestedDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> notInterestedDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			notInterestedDetails = conn
					.getNamedQuery("getNotInterestedDetailsByProfileId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return notInterestedDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyPartnerPreferenceDetailsFormBean> loadSentDetails(Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> sentDetails = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			sentDetails = conn
					.getNamedQuery("getSentDetailsByUserId")
					.setLong("userId", userId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return sentDetails;
	}
	
	public void setSession(Map<String, Object> arg0) {

	}

}
