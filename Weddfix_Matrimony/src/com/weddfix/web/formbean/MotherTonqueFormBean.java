package com.weddfix.web.formbean;

import java.util.Date;

public class MotherTonqueFormBean {

	private Long id;
	private String motherTonque;
	private String motherTonqueDesc;
	private Boolean isActive;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the motherTonque
	 */
	public String getMotherTonque() {
		return motherTonque;
	}

	/**
	 * @param motherTonque
	 *            the motherTonque to set
	 */
	public void setMotherTonque(String motherTonque) {
		this.motherTonque = motherTonque;
	}

	/**
	 * @return the motherTonqueDesc
	 */
	public String getMotherTonqueDesc() {
		return motherTonqueDesc;
	}

	/**
	 * @param motherTonqueDesc
	 *            the motherTonqueDesc to set
	 */
	public void setMotherTonqueDesc(String motherTonqueDesc) {
		this.motherTonqueDesc = motherTonqueDesc;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
