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
import com.weddfix.web.formbean.StateFormBean;
import com.weddfix.web.services.CommonMasterService;

public class StateMasterAction extends ActionSupport implements
		ServletRequestAware {
	HttpServletRequest servletRequest = null;
	private static final long serialVersionUID = 1L;
	private List<StateFormBean> gridModel;
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
	StateFormBean stateMasterFormBean;

	private String oper;
	private String grid;
	private Long id;
	private Long countryId;
	private String countryName;
	private String stateName;
	private String stateDesc;
	private Boolean isActive;

	public String populateStateMasterList() {
		return populateStateMasterData();
	}

	public String populateStateMasterData() {
		try {
			List<StateFormBean> users = stateMasterListFromDB();
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

	private List<StateFormBean> stateMasterListFromDB() {
		// MOCKED List for now
		List<StateFormBean> users = new ArrayList<StateFormBean>();

		List<StateFormBean> stateMasterList = null;
		CommonMasterService commonMasterService = new CommonMasterService();
		stateMasterList = commonMasterService.loadStateMasterList();

		// BankMasterBean bankMasterBean[] = new BankMasterBean[users.size()];
		StateFormBean stateMasterFormBean = new StateFormBean();
		Iterator<?> iterator = stateMasterList.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			stateMasterFormBean = new StateFormBean();
			stateMasterFormBean.setId((Long.parseLong(obj[0].toString())));
			stateMasterFormBean.setCountryId(Long.parseLong(obj[1].toString()));
			stateMasterFormBean.setStateName((((obj[2].toString()))));
			stateMasterFormBean.setIsActive((Boolean.parseBoolean(obj[3]
					.toString())));
			stateMasterFormBean.setCountryName(obj[4].toString());
			users.add(stateMasterFormBean);
		}

		return users;
	}

	public String editStateMasterList() {
		HttpSession sess = servletRequest.getSession(true);
		if (oper.equalsIgnoreCase("add")) {
			stateMasterFormBean = new StateFormBean();
			stateMasterFormBean.setId(id);
			stateMasterFormBean.setStateName(stateName);
//			stateMasterFormBean.setStateDesc(stateDesc);
			stateMasterFormBean.setCountryId(Long.parseLong(countryName));
			stateMasterFormBean.setIsActive(isActive);
			stateMasterFormBean.setCreatedBy(Long.parseLong(sess
					.getAttribute("userId").toString()));
			stateMasterFormBean.setCreatedDate(new Date());
			commonMasterService.addStateMaster(stateMasterFormBean);
		} else if (oper.equalsIgnoreCase("edit")) {
			commonMasterService.updateStateMaster(getId(), getStateName(), 
					Long.parseLong(getCountryName()), getIsActive(),
					Long.parseLong(sess.getAttribute("userId").toString()));
		} else if (oper.equalsIgnoreCase("del")) {
			commonMasterService.deleteStateMaster(getId());
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

	public List<StateFormBean> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<StateFormBean> gridModel) {
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

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

}
