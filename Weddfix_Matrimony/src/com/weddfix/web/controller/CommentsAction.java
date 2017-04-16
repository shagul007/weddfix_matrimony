package com.weddfix.web.controller;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.util.MailMessage;

public class CommentsAction extends ActionSupport implements
		ModelDriven<CommentsFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;

	CommentsFormBean commentsFormBean = new CommentsFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public String saveCommentDetails() {
		HttpSession session = request.getSession(true);
		try {

			Long status = commonMasterService
					.saveCommentDetails(commentsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Comment Inserted Successfully...");
				session.setAttribute("hrefParamSuccess", "home");
				System.out.println("Comment Inserted Successfully...");
				Properties props = new Properties();
				props.put("subject", commentsFormBean.getSubject());
				props.put("fullName", commentsFormBean.getName());
				props.put("email", commentsFormBean.getEmail());
				props.put("comments", commentsFormBean.getComment());
				MailMessage msg = new MailMessage(props, "feedback.vm",
						getText("contact.admin.email"), "Feedback of Weddfix Matrimony");
				msg.send();
				System.out.println("Comment sent successfully...");
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

	public void prepare() throws Exception {
		commentsFormBean = new CommentsFormBean();
	}

	public CommentsFormBean getModel() {
		return commentsFormBean;
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

}
