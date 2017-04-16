package com.weddfix.web.controller;

import java.text.SimpleDateFormat;
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
import com.weddfix.web.formbean.CartDetailsFormBean;
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.services.CommonMasterService;

public class CartDetailsAction extends ActionSupport implements
		ModelDriven<CartDetailsFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private List<Map<String, Object>> cartDetails = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> promotionDetails = new ArrayList<Map<String, Object>>();
	private String promoCodeText;
	private Long myPlanId;

	CartDetailsFormBean cartDetailsFormBean = new CartDetailsFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public String validatePromoCode() {
		try {
			
			HttpSession session = request.getSession(true);
			
			List<PromotionDetailsFormBean> promotionDetailsList = commonMasterService
					.validatePromoCode(promoCodeText);
			Iterator<?> itr = promotionDetailsList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("promoCode", obj[1]);
				map.put("expires", obj[2]);
				map.put("discount", obj[3]);
				map.put("email", obj[4]);
				map.put("sent", obj[5]);
				map.put("status", obj[6]);
				map.put("userId", obj[7]);
				map.put("sentTo", obj[8]);
				if(obj[7] == null || obj[8] != null) {
					session.setAttribute("acceptPromoCode", promoCodeText);
				} else {
					session.setAttribute("acceptPromoCode", null);
				}

				promotionDetails.add(map);
			}
			
			List<CartDetailsFormBean> cartDetailsList = commonMasterService
					.loadCartDetails((Long) session.getAttribute("userId"));
			itr = cartDetailsList.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj[0]);
				map.put("planId", obj[1]);
				map.put("planName", obj[2]);
				map.put("planType", obj[3]);
				map.put("amount", obj[4]);
				map.put("validity", obj[5]);
				map.put("status", obj[6]);

				cartDetails.add(map);
			}
				
			if (!promotionDetailsList.isEmpty()) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String saveOrUpdateAndLoadCartDetails() {
		HttpSession session = request.getSession(true);
		try {
		List<CartDetailsFormBean> cartDetailsList = commonMasterService
				.loadCartDetails((Long) session.getAttribute("userId"));
		if(!cartDetailsList.isEmpty()) {
		Iterator<?> itr = cartDetailsList.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(Long.parseLong(obj[1].toString()) != Long.parseLong(session.getAttribute("myPlanId").toString())) {
				
				cartDetailsFormBean.setId(Long.parseLong(obj[0].toString()));
				cartDetailsFormBean.setPlanId(Long.parseLong(session
						.getAttribute("myPlanId").toString()));
				cartDetailsFormBean.setUpdatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
				Long id = commonMasterService
						.saveCartDetails(cartDetailsFormBean);
				if (id != null) {
					cartDetailsList = commonMasterService
							.loadCartDetails((Long) session.getAttribute("userId"));
					itr = cartDetailsList.iterator();
					while (itr.hasNext()) {

						obj = (Object[]) itr.next();
						map = new HashMap<String, Object>();
						map.put("id", obj[0]);
						map.put("planId", obj[1]);
						map.put("planName", obj[2]);
						map.put("planType", obj[3]);
						map.put("amount", obj[4]);
						map.put("validity", obj[5]);
						map.put("status", obj[6]);

						cartDetails.add(map);
					}
				}
				} else {
			
					map.put("id", obj[0]);
					map.put("planId", obj[1]);
					map.put("planName", obj[2]);
					map.put("planType", obj[3]);
					map.put("amount", obj[4]);
					map.put("validity", obj[5]);
					map.put("status", obj[6]);
		
					cartDetails.add(map);
			}
		}
		} else {
			cartDetailsFormBean.setProfileId(Long.parseLong(session
					.getAttribute("loginProfileId").toString()));
			cartDetailsFormBean.setUserId(Long.parseLong(session
					.getAttribute("userId").toString()));
			cartDetailsFormBean.setPlanId(Long.parseLong(session
					.getAttribute("myPlanId").toString()));
			cartDetailsFormBean.setPlanType("UPGRADE");
			cartDetailsFormBean.setCreatedBy(Long.parseLong(session
					.getAttribute("userId").toString()));
			Long id = commonMasterService
					.saveCartDetails(cartDetailsFormBean);
			if (id != null) {
				cartDetailsList = commonMasterService
						.loadCartDetails((Long) session.getAttribute("userId"));
				Iterator<?> itr = cartDetailsList.iterator();
				while (itr.hasNext()) {

					Object[] obj = (Object[]) itr.next();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj[0]);
					map.put("planId", obj[1]);
					map.put("planName", obj[2]);
					map.put("planType", obj[3]);
					map.put("amount", obj[4]);
					map.put("validity", obj[5]);
					map.put("status", obj[6]);

					cartDetails.add(map);
			}
		}
		}
		
		return "success";
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String viewCartSession() {
		HttpSession session = request.getSession(true);
		try {
			session.setAttribute("myPlanId", getMyPlanId());
			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "success";
		}
	}
	
	public void prepare() throws Exception {
		cartDetailsFormBean = new CartDetailsFormBean();
	}

	public CartDetailsFormBean getModel() {
		return cartDetailsFormBean;
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

	public List<Map<String, Object>> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<Map<String, Object>> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public String getPromoCodeText() {
		return promoCodeText;
	}

	public void setPromoCodeText(String promoCodeText) {
		this.promoCodeText = promoCodeText;
	}

	public Long getMyPlanId() {
		return myPlanId;
	}

	public void setMyPlanId(Long myPlanId) {
		this.myPlanId = myPlanId;
	}

	public List<Map<String, Object>> getPromotionDetails() {
		return promotionDetails;
	}

	public void setPromotionDetails(List<Map<String, Object>> promotionDetails) {
		this.promotionDetails = promotionDetails;
	}
	
}
