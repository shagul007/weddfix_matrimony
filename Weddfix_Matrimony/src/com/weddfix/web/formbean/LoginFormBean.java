package com.weddfix.web.formbean;

import java.io.Serializable;
import java.util.Date;

public class LoginFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private String fullName;
	private String email;
	private Long mobile;
	private Long genderId;
	private String password;
	private String passwordHash;
	private String role;
	private Long religionId;
	private String status;
	private String accountType;
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
	private Long partnerPreferenceId;
	private Long myPlanId;

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
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
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
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * @return the mobile
	 */
	public Long getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the genderId
	 */
	public Long getGenderId() {
		return genderId;
	}

	/**
	 * @param genderId
	 *            the genderId to set
	 */
	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 *            the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the religionId
	 */
	public Long getReligionId() {
		return religionId;
	}

	/**
	 * @param religionId
	 *            the religionId to set
	 */
	public void setReligionId(Long religionId) {
		this.religionId = religionId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
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
	 * @return the partnerPreferenceId
	 */
	public Long getPartnerPreferenceId() {
		return partnerPreferenceId;
	}

	/**
	 * @param partnerPreferenceId
	 *            the partnerPreferenceId to set
	 */
	public void setPartnerPreferenceId(Long partnerPreferenceId) {
		this.partnerPreferenceId = partnerPreferenceId;
	}

	/**
	 * @return the myPlanId
	 */
	public Long getMyPlanId() {
		return myPlanId;
	}

	/**
	 * @param myPlanId
	 *            the myPlanId to set
	 */
	public void setMyPlanId(Long myPlanId) {
		this.myPlanId = myPlanId;
	}

	public String toString() {
		return "Datbase User Record loginid : " + id + "ProfileId : "
				+ profileId + "Password : " + password + "User Role : " + role
				+ "User Email : " + email + "User Status : " + status;
	}

}
