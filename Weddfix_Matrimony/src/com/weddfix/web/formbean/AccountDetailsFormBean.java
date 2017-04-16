package com.weddfix.web.formbean;

import java.util.Date;

public class AccountDetailsFormBean {

	private Long id;
	private Long profileId;
	private Long userId;
	private Long accountType;
	private Long deactivateProfileDays;
	private Date deactivatedProfileDate;
	private Date activateProfileDate;
	private Boolean isProfileActive;
	private String deleteProfileReason;
	private Boolean isProfileDeleted;
	private Boolean showMyProfilePicture;
	private Boolean showMyMobileNumber;
	private Boolean showMyEmailId;
	private String verifyMobileNumber;
	private String verifyEmailId;
	private Boolean verifyedMobileNumber;
	private Boolean verifyedEmailId;
	private Long emailCount;
	private Long mobileCount;
	private Long videoCallCount;
	private Long status;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String transactionId;
	private String transactionStatus;

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
	 * @return the accountType
	 */
	public Long getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the deactivateProfileDays
	 */
	public Long getDeactivateProfileDays() {
		return deactivateProfileDays;
	}

	/**
	 * @param deactivateProfileDays
	 *            the deactivateProfileDays to set
	 */
	public void setDeactivateProfileDays(Long deactivateProfileDays) {
		this.deactivateProfileDays = deactivateProfileDays;
	}

	/**
	 * @return the deactivatedProfileDate
	 */
	public Date getDeactivatedProfileDate() {
		return deactivatedProfileDate;
	}

	/**
	 * @param deactivatedProfileDate
	 *            the deactivatedProfileDate to set
	 */
	public void setDeactivatedProfileDate(Date deactivatedProfileDate) {
		this.deactivatedProfileDate = deactivatedProfileDate;
	}

	/**
	 * @return the activateProfileDate
	 */
	public Date getActivateProfileDate() {
		return activateProfileDate;
	}

	/**
	 * @param activateProfileDate
	 *            the activateProfileDate to set
	 */
	public void setActivateProfileDate(Date activateProfileDate) {
		this.activateProfileDate = activateProfileDate;
	}

	/**
	 * @return the isProfileActive
	 */
	public Boolean getIsProfileActive() {
		return isProfileActive;
	}

	/**
	 * @param isProfileActive
	 *            the isProfileActive to set
	 */
	public void setIsProfileActive(Boolean isProfileActive) {
		this.isProfileActive = isProfileActive;
	}

	/**
	 * @return the deleteProfileReason
	 */
	public String getDeleteProfileReason() {
		return deleteProfileReason;
	}

	/**
	 * @param deleteProfileReason
	 *            the deleteProfileReason to set
	 */
	public void setDeleteProfileReason(String deleteProfileReason) {
		this.deleteProfileReason = deleteProfileReason;
	}

	/**
	 * @return the isProfileDeleted
	 */
	public Boolean getIsProfileDeleted() {
		return isProfileDeleted;
	}

	/**
	 * @param isProfileDeleted
	 *            the isProfileDeleted to set
	 */
	public void setIsProfileDeleted(Boolean isProfileDeleted) {
		this.isProfileDeleted = isProfileDeleted;
	}

	/**
	 * @return the showMyProfilePicture
	 */
	public Boolean getShowMyProfilePicture() {
		return showMyProfilePicture;
	}

	/**
	 * @param showMyProfilePicture
	 *            the showMyProfilePicture to set
	 */
	public void setShowMyProfilePicture(Boolean showMyProfilePicture) {
		this.showMyProfilePicture = showMyProfilePicture;
	}

	/**
	 * @return the showMyMobileNumber
	 */
	public Boolean getShowMyMobileNumber() {
		return showMyMobileNumber;
	}

	/**
	 * @param showMyMobileNumber
	 *            the showMyMobileNumber to set
	 */
	public void setShowMyMobileNumber(Boolean showMyMobileNumber) {
		this.showMyMobileNumber = showMyMobileNumber;
	}

	/**
	 * @return the showMyEmailId
	 */
	public Boolean getShowMyEmailId() {
		return showMyEmailId;
	}

	/**
	 * @param showMyEmailId
	 *            the showMyEmailId to set
	 */
	public void setShowMyEmailId(Boolean showMyEmailId) {
		this.showMyEmailId = showMyEmailId;
	}

	/**
	 * @return the verifyMobileNumber
	 */
	public String getVerifyMobileNumber() {
		return verifyMobileNumber;
	}

	/**
	 * @param verifyMobileNumber
	 *            the verifyMobileNumber to set
	 */
	public void setVerifyMobileNumber(String verifyMobileNumber) {
		this.verifyMobileNumber = verifyMobileNumber;
	}

	/**
	 * @return the verifyEmailId
	 */
	public String getVerifyEmailId() {
		return verifyEmailId;
	}

	/**
	 * @param verifyEmailId
	 *            the verifyEmailId to set
	 */
	public void setVerifyEmailId(String verifyEmailId) {
		this.verifyEmailId = verifyEmailId;
	}

	/**
	 * @return the verifyedMobileNumber
	 */
	public Boolean getVerifyedMobileNumber() {
		return verifyedMobileNumber;
	}

	/**
	 * @param verifyedMobileNumber
	 *            the verifyedMobileNumber to set
	 */
	public void setVerifyedMobileNumber(Boolean verifyedMobileNumber) {
		this.verifyedMobileNumber = verifyedMobileNumber;
	}

	/**
	 * @return the verifyedEmailId
	 */
	public Boolean getVerifyedEmailId() {
		return verifyedEmailId;
	}

	/**
	 * @param verifyedEmailId
	 *            the verifyedEmailId to set
	 */
	public void setVerifyedEmailId(Boolean verifyedEmailId) {
		this.verifyedEmailId = verifyedEmailId;
	}

	/**
	 * @return the emailCount
	 */
	public Long getEmailCount() {
		return emailCount;
	}

	/**
	 * @param emailCount
	 *            the emailCount to set
	 */
	public void setEmailCount(Long emailCount) {
		this.emailCount = emailCount;
	}

	/**
	 * @return the mobileCount
	 */
	public Long getMobileCount() {
		return mobileCount;
	}

	/**
	 * @param mobileCount
	 *            the mobileCount to set
	 */
	public void setMobileCount(Long mobileCount) {
		this.mobileCount = mobileCount;
	}

	/**
	 * @return the videoCallCount
	 */
	public Long getVideoCallCount() {
		return videoCallCount;
	}

	/**
	 * @param videoCallCount
	 *            the videoCallCount to set
	 */
	public void setVideoCallCount(Long videoCallCount) {
		this.videoCallCount = videoCallCount;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
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

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus
	 *            the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
