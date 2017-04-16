package com.weddfix.web.formbean;

import java.util.Date;

public class PromotionDetailsFormBean {

	private Long id;
	private String promoCode;
	private Date expires;
	private Long discount;
	private String email;
	private String sentTo;
	private Date sent;
	private Long profileId;
	private Long userId;
	private Date accepted;
	private Long statusId;
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
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode
	 *            the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	/**
	 * @return the expires
	 */
	public Date getExpires() {
		return expires;
	}

	/**
	 * @param expires
	 *            the expires to set
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	/**
	 * @return the discount
	 */
	public Long getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the sentTo
	 */
	public String getSentTo() {
		return sentTo;
	}

	/**
	 * @param sentTo
	 *            the sentTo to set
	 */
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	/**
	 * @return the sent
	 */
	public Date getSent() {
		return sent;
	}

	/**
	 * @param sent
	 *            the sent to set
	 */
	public void setSent(Date sent) {
		this.sent = sent;
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
	 * @return the accepted
	 */
	public Date getAccepted() {
		return accepted;
	}

	/**
	 * @param accepted
	 *            the accepted to set
	 */
	public void setAccepted(Date accepted) {
		this.accepted = accepted;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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
