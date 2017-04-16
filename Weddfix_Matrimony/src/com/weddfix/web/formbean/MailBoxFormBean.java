package com.weddfix.web.formbean;

import java.io.Serializable;
import java.util.Date;

public class MailBoxFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private Long userId;
	private Boolean inbox;
	private Boolean newMail;
	private Boolean read;
	private Boolean accepted;
	private Boolean notInterested;
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
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId
	 *            the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the inbox
	 */
	public Boolean getInbox() {
		return inbox;
	}

	/**
	 * @param inbox
	 *            the inbox to set
	 */
	public void setInbox(Boolean inbox) {
		this.inbox = inbox;
	}

	/**
	 * @return the newMail
	 */
	public Boolean getNewMail() {
		return newMail;
	}

	/**
	 * @param newMail
	 *            the newMail to set
	 */
	public void setNewMail(Boolean newMail) {
		this.newMail = newMail;
	}

	/**
	 * @return the read
	 */
	public Boolean getRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(Boolean read) {
		this.read = read;
	}

	/**
	 * @return the accepted
	 */
	public Boolean getAccepted() {
		return accepted;
	}

	/**
	 * @param accepted
	 *            the accepted to set
	 */
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * @return the notInterested
	 */
	public Boolean getNotInterested() {
		return notInterested;
	}

	/**
	 * @param notInterested
	 *            the notInterested to set
	 */
	public void setNotInterested(Boolean notInterested) {
		this.notInterested = notInterested;
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
