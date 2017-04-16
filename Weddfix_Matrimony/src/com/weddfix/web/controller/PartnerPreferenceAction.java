package com.weddfix.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.services.RegisterService;

public class PartnerPreferenceAction extends ActionSupport implements
		ModelDriven<PartnerPreferenceFormBean>, Preparable,
		ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private Map<String, Object> myPersonalDetailsBean = new HashMap<String, Object>();

	PartnerPreferenceFormBean partnerPreferenceFormBean = new PartnerPreferenceFormBean();
	RegisterService registerService = new RegisterService();

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public String savePartnerPreference() {
		HttpSession session = request.getSession(true);
		try {

			if (partnerPreferenceFormBean.getId() != null) {
				session.setAttribute("update", "UPDATE");
				partnerPreferenceFormBean.setUpdatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
			} else {
				session.setAttribute("update", null);
				Long profileId = 1000000 + Long.parseLong(session.getAttribute(
						"userId").toString());
				partnerPreferenceFormBean.setProfileId(profileId);
				partnerPreferenceFormBean.setUserId(Long.parseLong(session
						.getAttribute("userId").toString()));
			}

			Long status = registerService
					.savePartnerPreferenceDetails(partnerPreferenceFormBean);
			if (status != null) {
				if (session.getAttribute("update") != null) {
					session.setAttribute("successMessage",
							"Updated Successfully...");
					session.setAttribute("hrefParamSuccess", "user_home");
					System.out.println("Updated Successfully...");
				} else {
					partnerPreferenceFormBean.setMaritalStatusId(null);
					session.setAttribute("successMessage",
							"Registered Successfully...");
					session.setAttribute("hrefParamSuccess", "login");
					session.setAttribute("loginCheck", null);
					session.invalidate();
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
	}

	public String loadMyProfileDetails() {
		HttpSession session = request.getSession();
		try {

			MyPersonalDetailsFormBean myPersonalDetails = registerService
					.loadPersonalDetails((Long) session.getAttribute("userId"));

			myPersonalDetailsBean.put("profileFor",
					myPersonalDetails.getProfileFor());
			myPersonalDetailsBean.put("fullName",
					myPersonalDetails.getFullName());
			myPersonalDetailsBean.put("gender", myPersonalDetails.getGender());
			DecimalFormat format = new DecimalFormat("0.#");
			myPersonalDetailsBean.put("age",
					format.format(myPersonalDetails.getAge()));
			myPersonalDetailsBean.put("dob", myPersonalDetails.getDob());
			myPersonalDetailsBean.put("email", myPersonalDetails.getEmail());
			myPersonalDetailsBean.put("mobile", myPersonalDetails.getMobile());
			myPersonalDetailsBean.put("religion",
					myPersonalDetails.getReligion());
			myPersonalDetailsBean.put("motherTongue",
					myPersonalDetails.getMotherTongue());
			myPersonalDetailsBean.put("profileId",
					myPersonalDetails.getProfileId());
			session.setAttribute("profileId", myPersonalDetails.getProfileId());
			myPersonalDetailsBean.put("maritalStatus",
					myPersonalDetails.getMaritalStatus());
			myPersonalDetailsBean.put("caste", myPersonalDetails.getCaste());
			myPersonalDetailsBean
					.put("country", myPersonalDetails.getCountry());
			myPersonalDetailsBean.put("state", myPersonalDetails.getState());
			myPersonalDetailsBean.put("city", myPersonalDetails.getCity());
			myPersonalDetailsBean.put("height", myPersonalDetails.getHeight());
			myPersonalDetailsBean.put("weight", myPersonalDetails.getWeight());
			myPersonalDetailsBean.put("bodyType",
					myPersonalDetails.getBodyType());
			myPersonalDetailsBean.put("complexion",
					myPersonalDetails.getComplexion());
			myPersonalDetailsBean.put("physicalStatus",
					myPersonalDetails.getPhysicalStatus());
			myPersonalDetailsBean.put("education",
					myPersonalDetails.getEducation());
			myPersonalDetailsBean.put("occupation",
					myPersonalDetails.getOccupation());
			myPersonalDetailsBean.put("employedIn",
					myPersonalDetails.getEmployedIn());
			myPersonalDetailsBean.put("currency",
					myPersonalDetails.getCurrency());
			myPersonalDetailsBean.put("monthlyIncome",
					myPersonalDetails.getMonthlyIncome());
			myPersonalDetailsBean.put("food", myPersonalDetails.getFood());
			myPersonalDetailsBean
					.put("smoking", myPersonalDetails.getSmoking());
			myPersonalDetailsBean.put("drinking",
					myPersonalDetails.getDrinking());
			myPersonalDetailsBean.put("familyStatus",
					myPersonalDetails.getFamilyStatus());
			myPersonalDetailsBean.put("familyType",
					myPersonalDetails.getFamilyType());
			myPersonalDetailsBean.put("familyValues",
					myPersonalDetails.getFamilyValues());
			myPersonalDetailsBean.put("aboutYou",
					myPersonalDetails.getAboutYou());

			return "success";

		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public void prepare() throws Exception {
		partnerPreferenceFormBean = new PartnerPreferenceFormBean();
	}

	public PartnerPreferenceFormBean getModel() {
		return partnerPreferenceFormBean;
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

	public Map<String, Object> getMyPersonalDetailsBean() {
		return myPersonalDetailsBean;
	}

	public void setMyPersonalDetailsBean(
			Map<String, Object> myPersonalDetailsBean) {
		this.myPersonalDetailsBean = myPersonalDetailsBean;
	}

}
