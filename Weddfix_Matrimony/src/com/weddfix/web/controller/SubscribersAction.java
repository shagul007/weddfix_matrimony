package com.weddfix.web.controller;

import java.text.SimpleDateFormat;
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
import com.weddfix.web.formbean.SubscribersFormBean;
import com.weddfix.web.services.CommonMasterService;

public class SubscribersAction extends ActionSupport implements
		ModelDriven<SubscribersFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private String emailId;

	SubscribersFormBean subscribersFormBean = new SubscribersFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public String saveSubscriberDetails() {
		HttpSession session = request.getSession(true);
		try {

			Long status = commonMasterService
					.saveSubscriberDetails(subscribersFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "home");
				System.out.println("Registered Successfully...");
				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {

			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}
	
	public String checkSubscriberEmailAlreadyExist() {
		String userExist = commonMasterService
				.checkSubscriberEmailAlreadyExist(emailId);
		if (userExist != null) {
			return "success";
		} else {
			setEmailId(null);
			return "error";
		}
	}

	public void prepare() throws Exception {
		subscribersFormBean = new SubscribersFormBean();
	}

	public SubscribersFormBean getModel() {
		return subscribersFormBean;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
