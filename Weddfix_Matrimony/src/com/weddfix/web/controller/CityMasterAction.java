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
import com.weddfix.web.formbean.CityFormBean;
import com.weddfix.web.services.CommonMasterService;

public class CityMasterAction extends ActionSupport implements
		ServletRequestAware {
	HttpServletRequest servletRequest = null;
	private static final long serialVersionUID = 1L;
	private List<CityFormBean> gridModel;
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
	CityFormBean cityMasterFormBean;

	private String oper;
	private String grid;
	private Long id;
	private Long stateId;
	private String stateName;
	private String cityName;
	private String cityDesc;
	private Boolean isActive;

	public String populateCityMasterList() {
		return populateCityMasterData();
	}

	public String populateCityMasterData() {
		try {
			List<CityFormBean> users = cityMasterListFromDB();
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

	private List<CityFormBean> cityMasterListFromDB() {
		// MOCKED List for now
		List<CityFormBean> users = new ArrayList<CityFormBean>();

		List<CityFormBean> cityMasterList = null;
		CommonMasterService commonMasterService = new CommonMasterService();
		cityMasterList = commonMasterService.loadCityMasterList();

		// BankMasterBean bankMasterBean[] = new BankMasterBean[users.size()];
		CityFormBean cityMasterFormBean = new CityFormBean();
		Iterator<?> iterator = cityMasterList.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			cityMasterFormBean = new CityFormBean();
			cityMasterFormBean.setId((Long.parseLong(obj[0].toString())));
			cityMasterFormBean.setStateId(Long.parseLong(obj[1].toString()));
			cityMasterFormBean.setCityName((((obj[2].toString()))));
			cityMasterFormBean.setIsActive((Boolean.parseBoolean(obj[3]
					.toString())));
			cityMasterFormBean.setStateName(obj[4].toString());
			users.add(cityMasterFormBean);
		}

		return users;
	}

	public String editCityMasterList() {
		HttpSession sess = servletRequest.getSession(true);
		if (oper.equalsIgnoreCase("add")) {
			cityMasterFormBean = new CityFormBean();
			cityMasterFormBean.setId(id);
			cityMasterFormBean.setCityName(cityName);
			// stateMasterFormBean.setCityDesc(cityDesc);
			cityMasterFormBean.setStateId(Long.parseLong(getStateName()));
			cityMasterFormBean.setIsActive(isActive);
			cityMasterFormBean.setCreatedBy(Long.parseLong(sess.getAttribute(
					"userId").toString()));
			cityMasterFormBean.setCreatedDate(new Date());
			commonMasterService.addCityMaster(cityMasterFormBean);
		} else if (oper.equalsIgnoreCase("edit")) {
			commonMasterService.updateCityMaster(getId(), getCityName(),
					Long.parseLong(getStateName()), getIsActive(),
					Long.parseLong(sess.getAttribute("userId").toString()));
		} else if (oper.equalsIgnoreCase("del")) {
			commonMasterService.deleteCityMaster(getId());
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

	public List<CityFormBean> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<CityFormBean> gridModel) {
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

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

}
