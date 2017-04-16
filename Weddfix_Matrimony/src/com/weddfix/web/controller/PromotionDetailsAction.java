package com.weddfix.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.services.CommonMasterService;

public class PromotionDetailsAction extends ActionSupport implements
		ModelDriven<PromotionDetailsFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;
	private List<Map<String, Object>> promotionDetails = new ArrayList<Map<String, Object>>();
	private String promoCodeText;
	private Long discountText;
	private Long promoId;
	private String expiresDate;
	private String promoForAll;
	private String promoEmailId;
	private String sendPromoCode;
	private String sendPromoForAll;

	PromotionDetailsFormBean promotionDetailsFormBean = new PromotionDetailsFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public String savePromotionDetails() {
		HttpSession session = request.getSession(true);
		Date expires = null;
		try {
			expires = sdf.parse(expiresDate);
			promotionDetailsFormBean.setExpires(expires);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		promotionDetailsFormBean.setCreatedBy(Long.parseLong(session
				.getAttribute("userId").toString()));
		promotionDetailsFormBean.setPromoCode(promoCodeText);
		promotionDetailsFormBean.setDiscount(discountText);
		if(promoForAll.equals("true")) {
			promotionDetailsFormBean.setSentTo("All");
		}
		Long id = commonMasterService
				.savePromotionDetails(promotionDetailsFormBean);
		if (id != null) {
			return "success";
		} else {
			return "error";
		}
	}

	public String deletePromotion() {
		try {
			Long status = commonMasterService
					.deletePromotion(promoId);
			if (status != null) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String sendPromotion() {
		HttpSession session = request.getSession(true);
		try {
			
			Long status = commonMasterService
					.sendPromotion(promoEmailId, sendPromoCode, sendPromoForAll, (Long) session.getAttribute("userId"));
			if (status != null) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String loadPromotionDetails() {
		List<PromotionDetailsFormBean> promotionDetailsList = commonMasterService
				.loadPromotionDetails();
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
			map.put("sentTo", obj[6]);
			map.put("status", obj[7]);

			promotionDetails.add(map);
		}

		return "success";
	}
	
	
	public void prepare() throws Exception {
		promotionDetailsFormBean = new PromotionDetailsFormBean();
	}

	public PromotionDetailsFormBean getModel() {
		return promotionDetailsFormBean;
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

	public List<Map<String, Object>> getPromotionDetails() {
		return promotionDetails;
	}

	public void setPromotionDetails(List<Map<String, Object>> promotionDetails) {
		this.promotionDetails = promotionDetails;
	}

	public String getPromoCodeText() {
		return promoCodeText;
	}

	public void setPromoCodeText(String promoCodeText) {
		this.promoCodeText = promoCodeText;
	}

	public Long getDiscountText() {
		return discountText;
	}

	public void setDiscountText(Long discountText) {
		this.discountText = discountText;
	}

	public String getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(String expiresDate) {
		this.expiresDate = expiresDate;
	}

	public String getPromoForAll() {
		return promoForAll;
	}

	public void setPromoForAll(String promoForAll) {
		this.promoForAll = promoForAll;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public String getPromoEmailId() {
		return promoEmailId;
	}

	public void setPromoEmailId(String promoEmailId) {
		this.promoEmailId = promoEmailId;
	}

	public String getSendPromoCode() {
		return sendPromoCode;
	}

	public void setSendPromoCode(String sendPromoCode) {
		this.sendPromoCode = sendPromoCode;
	}

	public String getSendPromoForAll() {
		return sendPromoForAll;
	}

	public void setSendPromoForAll(String sendPromoForAll) {
		this.sendPromoForAll = sendPromoForAll;
	}
	
}
