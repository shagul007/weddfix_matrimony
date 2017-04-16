package com.weddfix.web.implementation;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.interfaces.LoginDao;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;
import com.weddfix.web.util.InvalidLoginException;

/**
 * 
 * 
 * @author Shagul
 * 
 */
public class LoginDaoImpl implements LoginDao, SessionAware {

	Session conn;

	UserProfileFormBean registerFormBean = new UserProfileFormBean();

	private static final Logger logger = Logger.getLogger(LoginDaoImpl.class);

	Long user;

	public LoginFormBean checkLogin(String userName, String password)
			throws InvalidLoginException {
		CommonMasterDaoImpl commonMasterDaoImpl = new CommonMasterDaoImpl();
		LoginFormBean loginBean = commonMasterDaoImpl
				.getUserProfileByEmail(userName.toLowerCase());

		try {
			if (loginBean != null) {
				if (CommonConstants.generateEncryptedPwd(password).equals(
						loginBean.getPasswordHash()))
					return loginBean;
			}
		} catch (Exception e) {
			String msg = "Invalid Login Attempt made by : " + userName + ":"
					+ password;
			throw new InvalidLoginException(msg);
		}
		return null;
	}
	
	public LoginFormBean sessionPasswordHash(Long userId) {

		LoginFormBean loginFormBean = new LoginFormBean();
		try {
			logger.info("-----------Get Login details Method--------------");
				conn = HibernateUtil.getSessionFactory().openSession();

				List<?> userProfile = conn.getNamedQuery("getUserProfileByUserId")
						.setLong("userId", userId).list();
				Iterator<?> itr = userProfile.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					loginFormBean.setId(Long.parseLong(obj[0].toString()));
					loginFormBean.setProfileId(Long.parseLong(obj[1].toString()));
					loginFormBean.setFullName(obj[2].toString());
					loginFormBean.setEmail(obj[3].toString());
					loginFormBean.setPassword(obj[4].toString());
					loginFormBean.setPasswordHash(obj[5].toString());
					loginFormBean.setRole(obj[6].toString());
					loginFormBean.setStatus(obj[7].toString());
				}
				if (loginFormBean != null) {
					return loginFormBean;
				}

		} catch (HibernateException hibernateException) {

//			hibernateException.printStackTrace();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return loginFormBean;
	}
	
	public LoginFormBean loadProfileSessionDetails(String email) {
		LoginFormBean loginFormBean = new LoginFormBean();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn = HibernateUtil.getSessionFactory().openSession();

			List<?> userProfile = conn.getNamedQuery("getUserProfileByEmail")
					.setString("email", email).list();
			Iterator<?> itr = userProfile.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				loginFormBean.setId(Long.parseLong(obj[0].toString()));
				loginFormBean.setProfileId(Long.parseLong(obj[1].toString()));
				loginFormBean.setFullName(obj[2].toString());
				loginFormBean.setEmail(obj[3].toString());
				loginFormBean.setPassword(obj[4].toString());
				loginFormBean.setPasswordHash(obj[5].toString());
				loginFormBean.setRole(obj[6].toString());
				loginFormBean.setStatus(obj[7].toString());
				loginFormBean.setAccountType(obj[8].toString());
				if (obj[9] != null) {
					loginFormBean.setDeactivateProfileDays(Long.parseLong(obj[9].toString()));
				}
				if (obj[10] != null) {
					loginFormBean.setDeactivatedProfileDate(format.parse(obj[10].toString()));
				}
				if (obj[11] != null) {
					loginFormBean.setActivateProfileDate(format.parse(obj[11].toString()));
				}
				loginFormBean.setIsProfileActive(Boolean.valueOf(obj[12].toString()));
				if (obj[13] != null) {
					loginFormBean.setDeleteProfileReason(obj[13].toString());
				}
				loginFormBean.setIsProfileDeleted(Boolean.valueOf(obj[14].toString()));
				loginFormBean.setShowMyProfilePicture(Boolean.valueOf(obj[15].toString()));
				loginFormBean.setShowMyMobileNumber(Boolean.valueOf(obj[16].toString()));
				loginFormBean.setShowMyEmailId(Boolean.valueOf(obj[17].toString()));
				loginFormBean.setVerifyMobileNumber(obj[18].toString());
				loginFormBean.setVerifyEmailId(obj[19].toString());
				loginFormBean.setVerifyedMobileNumber(Boolean.valueOf(obj[20].toString()));
				loginFormBean.setVerifyedEmailId(Boolean.valueOf(obj[21].toString()));
				loginFormBean.setEmailCount(Long.parseLong(obj[22].toString()));
				loginFormBean.setMobileCount(Long.parseLong(obj[23].toString()));
				loginFormBean.setVideoCallCount(Long.parseLong(obj[24].toString()));
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
	
	public void setSession(Map<String, Object> arg0) {

	}

}
