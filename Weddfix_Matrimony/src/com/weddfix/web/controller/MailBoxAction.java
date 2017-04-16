package com.weddfix.web.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.MailBoxService;
import com.weddfix.web.services.RegisterService;

public class MailBoxAction extends ActionSupport implements
		ModelDriven<MailBoxFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private Map<String, Object> myPersonalDetailsBean = new HashMap<String, Object>();
	private List<Map<String, Object>> profilePicturesInfoBean = new ArrayList<Map<String, Object>>();
	private Map<String, Object> partnerPreferenceDetailsBean = new HashMap<String, Object>();
	private List<Map<String, Object>> newMatchesInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> whoViewedMyProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> viewedAndNotContactedProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> recentlyViewedProfileInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> inboxInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> acceptedInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> notInterestedInfoBean = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> sentInfoBean = new ArrayList<Map<String, Object>>();
	private Long profileUserId;
	private Long userProfileId;
	private String statusName;
	MailBoxFormBean mailBoxDetailsFormBean = new MailBoxFormBean();
	RegisterService registerService = new RegisterService();
	MailBoxService mailBoxService = new MailBoxService();
	ClientInfoService clientInfoService = new ClientInfoService();

	public String saveMatchesDetails() {
		HttpSession session = request.getSession(true);
		try {

			mailBoxDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("userProfileId").toString()));
			mailBoxDetailsFormBean.setUserId(Long.parseLong(session
					.getAttribute("userId").toString()));
//			mailBoxDetailsFormBean.setWhoViewedMyProfile(true);
//			mailBoxDetailsFormBean.setViewedAndNotContacted(true);
//			mailBoxDetailsFormBean.setRecentlyViewedProfiles(true);
			mailBoxDetailsFormBean.setCreatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = mailBoxService
					.saveMailBoxDetails(mailBoxDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Inserted Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
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


	public String loadInboxDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> inboxInfoList = mailBoxService
					.loadInboxDetails((Long) session
							.getAttribute("loginProfileId"));

			Iterator<?> itr = inboxInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				map.put("fileName", obj[9]);
				map.put("accepted", obj[10]);
				map.put("notInterested", obj[11]);
				map.put("showProfilePicture", obj[12]);

				inboxInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}


	public String saveAcceptedProfile() {
		HttpSession session = request.getSession(true);
		try {

			mailBoxDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("loginProfileId").toString()));
			mailBoxDetailsFormBean.setUserId(getProfileUserId());
			if (getStatusName().equals("Accept")) {
				mailBoxDetailsFormBean.setAccepted(true);
			} else {
				mailBoxDetailsFormBean.setAccepted(false);
			}
			mailBoxDetailsFormBean.setUpdatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = mailBoxService
					.saveAcceptedProfile(mailBoxDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
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

	public String saveNotInterestedProfile() {
		HttpSession session = request.getSession(true);
		try {

			mailBoxDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("loginProfileId").toString()));
			mailBoxDetailsFormBean.setUserId(getProfileUserId());
			if (getStatusName().equals("NotInterest")) {
				mailBoxDetailsFormBean.setNotInterested(true);
			} else {
				mailBoxDetailsFormBean.setNotInterested(false);
			}
			mailBoxDetailsFormBean.setUpdatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));

			Long status = mailBoxService
					.saveNotInterestedProfile(mailBoxDetailsFormBean);
			if (status != null) {
				session.setAttribute("successMessage",
						"Registered Successfully...");
				session.setAttribute("hrefParamSuccess", "login");
				System.out.println("Inserted Successfully...");
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

	public String loadAcceptedDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> acceptedInfoList = mailBoxService
					.loadAcceptedDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = acceptedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				map.put("fileName", obj[9]);
				map.put("accepted", obj[10]);
				map.put("notInterested", obj[11]);
				map.put("showProfilePicture", obj[12]);

				acceptedInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}
	
	public String loadNotInterestedDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> notInterestedInfoList = mailBoxService
					.loadNotInterestedDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = notInterestedInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				map.put("fileName", obj[9]);
				map.put("accepted", obj[10]);
				map.put("notInterested", obj[11]);
				map.put("showProfilePicture", obj[12]);

				notInterestedInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}
	
	public String loadSentDetails() {
		HttpSession session = request.getSession();
		try {

			List<MyPartnerPreferenceDetailsFormBean> sentInfoList = mailBoxService
					.loadSentDetails((Long) session
							.getAttribute("userId"));

			Iterator<?> itr = sentInfoList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", obj[0]);
				map.put("fullName", obj[1]);
				map.put("profileId", obj[2]);
				map.put("gender", obj[3]);
				DecimalFormat format = new DecimalFormat("0.#");
				map.put("age", format.format(obj[4]));
				map.put("height", obj[5]);
				map.put("religion", obj[6]);
				map.put("education", obj[7]);
				map.put("occupation", obj[8]);
				map.put("fileName", obj[9]);
				map.put("showProfilePicture", obj[10]);

				sentInfoBean.add(map);
			}

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public String viewProfileSession() {
		HttpSession session = request.getSession(true);
		try {
			session.setAttribute("profileUserId", getProfileUserId());
			session.setAttribute("userProfileId", getUserProfileId());
			mailBoxDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("loginProfileId").toString()));
			mailBoxDetailsFormBean.setUserId(getProfileUserId());
			mailBoxDetailsFormBean.setUpdatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));
			mailBoxService.updateReadMailDetails(mailBoxDetailsFormBean);
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
	}
	
	public void prepare() throws Exception {
		mailBoxDetailsFormBean = new MailBoxFormBean();
	}

	public MailBoxFormBean getModel() {
		return mailBoxDetailsFormBean;
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

	public Long getProfileUserId() {
		return profileUserId;
	}

	public void setProfileUserId(Long profileUserId) {
		this.profileUserId = profileUserId;
	}

	public Map<String, Object> getMyPersonalDetailsBean() {
		return myPersonalDetailsBean;
	}

	public void setMyPersonalDetailsBean(
			Map<String, Object> myPersonalDetailsBean) {
		this.myPersonalDetailsBean = myPersonalDetailsBean;
	}

	public List<Map<String, Object>> getProfilePicturesInfoBean() {
		return profilePicturesInfoBean;
	}

	public void setProfilePicturesInfoBean(
			List<Map<String, Object>> profilePicturesInfoBean) {
		this.profilePicturesInfoBean = profilePicturesInfoBean;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Map<String, Object> getPartnerPreferenceDetailsBean() {
		return partnerPreferenceDetailsBean;
	}

	public void setPartnerPreferenceDetailsBean(
			Map<String, Object> partnerPreferenceDetailsBean) {
		this.partnerPreferenceDetailsBean = partnerPreferenceDetailsBean;
	}

	public List<Map<String, Object>> getNewMatchesInfoBean() {
		return newMatchesInfoBean;
	}

	public void setNewMatchesInfoBean(
			List<Map<String, Object>> newMatchesInfoBean) {
		this.newMatchesInfoBean = newMatchesInfoBean;
	}

	public List<Map<String, Object>> getWhoViewedMyProfileInfoBean() {
		return whoViewedMyProfileInfoBean;
	}

	public void setWhoViewedMyProfileInfoBean(
			List<Map<String, Object>> whoViewedMyProfileInfoBean) {
		this.whoViewedMyProfileInfoBean = whoViewedMyProfileInfoBean;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Map<String, Object>> getViewedAndNotContactedProfileInfoBean() {
		return viewedAndNotContactedProfileInfoBean;
	}

	public void setViewedAndNotContactedProfileInfoBean(
			List<Map<String, Object>> viewedAndNotContactedProfileInfoBean) {
		this.viewedAndNotContactedProfileInfoBean = viewedAndNotContactedProfileInfoBean;
	}

	public List<Map<String, Object>> getRecentlyViewedProfileInfoBean() {
		return recentlyViewedProfileInfoBean;
	}

	public void setRecentlyViewedProfileInfoBean(
			List<Map<String, Object>> recentlyViewedProfileInfoBean) {
		this.recentlyViewedProfileInfoBean = recentlyViewedProfileInfoBean;
	}

	public List<Map<String, Object>> getInboxInfoBean() {
		return inboxInfoBean;
	}

	public void setInboxInfoBean(
			List<Map<String, Object>> inboxInfoBean) {
		this.inboxInfoBean = inboxInfoBean;
	}


	public List<Map<String, Object>> getAcceptedInfoBean() {
		return acceptedInfoBean;
	}


	public void setAcceptedInfoBean(List<Map<String, Object>> acceptedInfoBean) {
		this.acceptedInfoBean = acceptedInfoBean;
	}


	public List<Map<String, Object>> getNotInterestedInfoBean() {
		return notInterestedInfoBean;
	}


	public void setNotInterestedInfoBean(
			List<Map<String, Object>> notInterestedInfoBean) {
		this.notInterestedInfoBean = notInterestedInfoBean;
	}


	public List<Map<String, Object>> getSentInfoBean() {
		return sentInfoBean;
	}


	public void setSentInfoBean(List<Map<String, Object>> sentInfoBean) {
		this.sentInfoBean = sentInfoBean;
	}

}
