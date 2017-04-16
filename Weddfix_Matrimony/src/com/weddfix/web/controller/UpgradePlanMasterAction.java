package com.weddfix.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.services.CommonMasterService;

public class UpgradePlanMasterAction extends ActionSupport implements
		ServletRequestAware {
	HttpServletRequest servletRequest = null;
	private static final long serialVersionUID = 1L;
	private List<UpgradePlanFormBean> gridModel;
	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// Your Total Pages
	private Integer total = 0;
	// All Records
	private Integer records = 0;
	// sorting order - asc or desc
	private String sord;
	// get index row - i.e. user click to sort.
	private String sidx;

	private String roleList;

	CommonMasterService commonMasterService = new CommonMasterService();
	UpgradePlanFormBean upgradePlanMasterFormBean;

	private String oper;
	private String grid;
	private Long id;
	private String planName;
	private Long amount;
	private Long validity;
	private Long emailCount;
	private Long mobileCount;
	private Long videoCallCount;
	private Boolean expressInterest;
	private Boolean profileHighlight;
	private Boolean viewProfile;
	private Boolean protectPhoto;
	private Boolean getSMSAlert;
	private Boolean isActive;

	public String populateUpgradePlanMasterList() {
		return populateUpgradePlanMasterData();
	}

	public String populateUpgradePlanMasterData() {
		try {
			List<UpgradePlanFormBean> users = upgradePlanMasterListFromDB();
			if (users != null && getSord() != null
					&& getSord().equalsIgnoreCase("asc")) {
				// Collections.sort(users, null);
			}
			if (getSord() != null && "desc".equalsIgnoreCase(getSord())) {
				// Collections.sort(users, null);
				Collections.reverse(users);
			}
			setRecord(users.size());
			int to = (getRows() * getPage());
			if (to > getRecord())
				to = getRecord();

			setGridModel(users);
			setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));

			if (hasActionMessages() || hasActionErrors()) {
				return INPUT;
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	private List<UpgradePlanFormBean> upgradePlanMasterListFromDB() {
		// MOCKED List for now
		List<UpgradePlanFormBean> users = new ArrayList<UpgradePlanFormBean>();

		List<UpgradePlanFormBean> upgradePlanMasterList = null;
		CommonMasterService commonMasterService = new CommonMasterService();
		upgradePlanMasterList = commonMasterService.loadUpgradePlanMasterList();

		// BankMasterBean bankMasterBean[] = new BankMasterBean[users.size()];
		UpgradePlanFormBean upgradePlanMasterFormBean = new UpgradePlanFormBean();
		Iterator<?> iterator = upgradePlanMasterList.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			upgradePlanMasterFormBean = new UpgradePlanFormBean();
			upgradePlanMasterFormBean
					.setId((Long.parseLong(obj[0].toString())));
			upgradePlanMasterFormBean.setPlanName(obj[1].toString());
			upgradePlanMasterFormBean.setAmount(Long.parseLong(obj[2]
					.toString()));
			upgradePlanMasterFormBean.setValidity(Long.parseLong(obj[3]
					.toString()));
			upgradePlanMasterFormBean.setEmailCount((Long.parseLong(obj[4]
					.toString())));
			upgradePlanMasterFormBean.setMobileCount((Long.parseLong(obj[5]
					.toString())));
			upgradePlanMasterFormBean.setVideoCallCount((Long.parseLong(obj[6]
					.toString())));
			upgradePlanMasterFormBean.setExpressInterest((Boolean
					.parseBoolean(obj[7].toString())));
			upgradePlanMasterFormBean.setProfileHighlight((Boolean
					.parseBoolean(obj[8].toString())));
			upgradePlanMasterFormBean.setViewProfile((Boolean
					.parseBoolean(obj[9].toString())));
			upgradePlanMasterFormBean.setProtectPhoto((Boolean
					.parseBoolean(obj[10].toString())));
			upgradePlanMasterFormBean.setGetSMSAlert((Boolean
					.parseBoolean(obj[11].toString())));
			upgradePlanMasterFormBean.setIsActive((Boolean.parseBoolean(obj[12]
					.toString())));
			users.add(upgradePlanMasterFormBean);
		}

		return users;
	}

	public String editUpgradePlanMasterList() {
		HttpSession sess = servletRequest.getSession(true);
		if (oper.equalsIgnoreCase("add")) {
			upgradePlanMasterFormBean = new UpgradePlanFormBean();
			upgradePlanMasterFormBean.setId(id);
			upgradePlanMasterFormBean.setPlanName(planName);
			upgradePlanMasterFormBean.setAmount(amount);
			upgradePlanMasterFormBean.setValidity(validity);
			upgradePlanMasterFormBean.setEmailCount(emailCount);
			upgradePlanMasterFormBean.setMobileCount(mobileCount);
			upgradePlanMasterFormBean.setVideoCallCount(videoCallCount);
			upgradePlanMasterFormBean.setExpressInterest(expressInterest);
			upgradePlanMasterFormBean.setProfileHighlight(profileHighlight);
			upgradePlanMasterFormBean.setViewProfile(viewProfile);
			upgradePlanMasterFormBean.setProtectPhoto(protectPhoto);
			upgradePlanMasterFormBean.setGetSMSAlert(getSMSAlert);
			upgradePlanMasterFormBean.setIsActive(isActive);
			upgradePlanMasterFormBean.setCreatedBy(Long.parseLong(sess
					.getAttribute("userId").toString()));
			upgradePlanMasterFormBean.setCreatedDate(new Date());
			commonMasterService.addUpgradePlanMaster(upgradePlanMasterFormBean);
		} else if (oper.equalsIgnoreCase("edit")) {
			commonMasterService.updateUpgradePlanMaster(getId(),
					getPlanName(), getAmount(), getValidity(), getEmailCount(), getMobileCount(), getVideoCallCount(),
					getExpressInterest(), getProfileHighlight(), getViewProfile(), getProtectPhoto(), getGetSMSAlert(),
					getIsActive(), Long.parseLong(sess.getAttribute("userId").toString()));
		} else if (oper.equalsIgnoreCase("del")) {
			commonMasterService.deleteUpgradePlanMaster(getId());
		}

		return SUCCESS;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecord() {
		return records;
	}

	public void setRecord(Integer records) {
		this.records = records;
		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records
					/ (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	public List<UpgradePlanFormBean> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<UpgradePlanFormBean> gridModel) {
		this.gridModel = gridModel;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public void setSearchField(String searchField) {
	}

	public void setSearchString(String searchString) {
	}

	public void setSearchOper(String searchOper) {
	}

	public void setLoadonce(boolean loadonce) {
	}

	public void setSession(Map<String, Object> session) {
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getMaritalStatusList() {
		return roleList;
	}

	public void setMaritalStatusList(String maritalStatusList) {
		this.roleList = maritalStatusList;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getValidity() {
		return validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	public Long getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(Long emailCount) {
		this.emailCount = emailCount;
	}

	public Long getMobileCount() {
		return mobileCount;
	}

	public void setMobileCount(Long mobileCount) {
		this.mobileCount = mobileCount;
	}

	public Long getVideoCallCount() {
		return videoCallCount;
	}

	public void setVideoCallCount(Long videoCallCount) {
		this.videoCallCount = videoCallCount;
	}

	public Boolean getExpressInterest() {
		return expressInterest;
	}

	public void setExpressInterest(Boolean expressInterest) {
		this.expressInterest = expressInterest;
	}

	public Boolean getProfileHighlight() {
		return profileHighlight;
	}

	public void setProfileHighlight(Boolean profileHighlight) {
		this.profileHighlight = profileHighlight;
	}

	public Boolean getViewProfile() {
		return viewProfile;
	}

	public void setViewProfile(Boolean viewProfile) {
		this.viewProfile = viewProfile;
	}

	public Boolean getProtectPhoto() {
		return protectPhoto;
	}

	public void setProtectPhoto(Boolean protectPhoto) {
		this.protectPhoto = protectPhoto;
	}

	public Boolean getGetSMSAlert() {
		return getSMSAlert;
	}

	public void setGetSMSAlert(Boolean getSMSAlert) {
		this.getSMSAlert = getSMSAlert;
	}

}
