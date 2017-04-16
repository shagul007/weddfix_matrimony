package com.weddfix.web.controller;

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
import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.services.LoginService;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.InvalidLoginException;
import com.weddfix.web.util.WeddingException;

public class LoginAction extends ActionSupport implements
		ModelDriven<LoginFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	// private static final Logger logger = Logger.getLogger(LoginAction.class);
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	LoginFormBean loginFormBean = new LoginFormBean();

	/**
	 * method is used to check login etails
	 * 
	 * @return
	 */
	public String login() throws InvalidLoginException {
		try {
			HttpSession session = this.request.getSession(true);
				LoginService loginService = new LoginService();
				loginFormBean = loginService.checkLogin(
						loginFormBean.getEmail(), loginFormBean.getPassword());

				this.session.put("loginId", loginFormBean.getId());
				this.session.put("authenticated", new Boolean(true));
				session.setAttribute("userId", loginFormBean.getId());
				session.setAttribute("loginProfileId", loginFormBean.getProfileId());
				session.setAttribute("email", loginFormBean.getEmail());
				session.setAttribute("mobile", loginFormBean.getMobile());
				session.setAttribute("loginGenderId", loginFormBean.getGenderId());
				session.setAttribute("role", loginFormBean.getRole());
				session.setAttribute("status", loginFormBean.getStatus());
				session.setAttribute("password", loginFormBean.getPassword());
				session.setAttribute("userName", loginFormBean.getFullName());
				session.setAttribute("loginType", "public");
				session.setAttribute("loginCheck", "loggedin");
				session.setAttribute("religionId", loginFormBean.getReligionId());
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
				session.setAttribute("partnerPreferenceId", loginFormBean.getPartnerPreferenceId());
				session.setAttribute("myPlanId", loginFormBean.getMyPlanId());
				session.setAttribute("url", CommonConstants.URL);
				
				if(loginFormBean.getStatus().equals(CommonConstants.INACTIVE_STR)) {
					addActionError(getText("error.login.inactive"));
					return "error";
				} else {
					return "success";
				}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(getText("error.login"));
			return "error";
		}

	}

	public LoginFormBean getLoginFormBean() {
		return this.loginFormBean;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	/*
	 * public void validate() {
	 * 
	 * String pwd = null; String userId = null; String pwdStatus = null;
	 * 
	 * if (loginBean != null) { pwd = loginBean.getPassword(); userId =
	 * loginBean.getUserName(); } userLoginDet =
	 * loginObject.retrieveRecord(loginBean
	 * .getUserName(),CommonConstants.generateEncryptedPwd
	 * (loginBean.getPassword()));
	 * 
	 * if(pwdStatus==null){
	 * addActionError("The userId or password you entered is incorrect.");
	 * return ; } }
	 */

	/**
	 * Method produce the functionality of log out technique
	 * 
	 * @return
	 * @throws Exception
	 */

	public String logout() throws WeddingException {
		HttpSession session = this.request.getSession(true);
		try {
			session.setAttribute("loginCheck", null);
			session.invalidate();
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(
					"Error",
					"Application Error ! Last request could not be processed due to an error condition.");
			session.setAttribute("hrefParamError", "PublicLogin");
			return "error";
		}
	}

	public void setLoginBean(LoginFormBean loginFormBean) {
		this.loginFormBean = loginFormBean;
	}

	public void setLoginSession(Map<String, Object> session) {
		this.session = session;
	}

	public void prepare() throws Exception {
		loginFormBean = new LoginFormBean();
	}

	public LoginFormBean getModel() {
		return loginFormBean;
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
}
