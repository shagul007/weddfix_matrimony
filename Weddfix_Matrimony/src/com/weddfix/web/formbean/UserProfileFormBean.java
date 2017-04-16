package com.weddfix.web.formbean;

import java.io.Serializable;
import java.util.Date;

public class UserProfileFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private Long userProfileId;
	private String fullName;
	private Long genderId;
	private Long maritalStatusId;
	private Date dob;
	private String email;
	private String password;
	private String passwordHash;
	private Long mobile;
	private Long heightId;
	private Long educationId;
	private Long occupationId;
	private Long religionId;
	private Long cityId;
	private Long stateId;
	private Long countryId;
	private String aboutYou;
	private Long profilePictureId;
	private Long birthHoroScopePhotoId;
	private String dobStr;
	private Long roleId;
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
	 * @return the userProfileId
	 */
	public Long getUserProfileId() {
		return userProfileId;
	}

	/**
	 * @param userProfileId
	 *            the userProfileId to set
	 */
	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * @return the maritalStatusId
	 */
	public Long getMaritalStatusId() {
		return maritalStatusId;
	}

	/**
	 * @param maritalStatusId
	 *            the maritalStatusId to set
	 */
	public void setMaritalStatusId(Long maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
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
	 * @return the heightId
	 */
	public Long getHeightId() {
		return heightId;
	}

	/**
	 * @param heightId
	 *            the heightId to set
	 */
	public void setHeightId(Long heightId) {
		this.heightId = heightId;
	}

	/**
	 * @return the educationId
	 */
	public Long getEducationId() {
		return educationId;
	}

	/**
	 * @param educationId
	 *            the educationId to set
	 */
	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	/**
	 * @return the occupationId
	 */
	public Long getOccupationId() {
		return occupationId;
	}

	/**
	 * @param occupationId
	 *            the occupationId to set
	 */
	public void setOccupationId(Long occupationId) {
		this.occupationId = occupationId;
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
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the countryId
	 */
	public Long getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the aboutYou
	 */
	public String getAboutYou() {
		return aboutYou;
	}

	/**
	 * @param aboutYou
	 *            the aboutYou to set
	 */
	public void setAboutYou(String aboutYou) {
		this.aboutYou = aboutYou;
	}

	/**
	 * @return the profilePictureId
	 */
	public Long getProfilePictureId() {
		return profilePictureId;
	}

	/**
	 * @param profilePictureId
	 *            the profilePictureId to set
	 */
	public void setProfilePictureId(Long profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	/**
	 * @return the birthHoroScopePhotoId
	 */
	public Long getBirthHoroScopePhotoId() {
		return birthHoroScopePhotoId;
	}

	/**
	 * @param birthHoroScopePhotoId
	 *            the birthHoroScopePhotoId to set
	 */
	public void setBirthHoroScopePhotoId(Long birthHoroScopePhotoId) {
		this.birthHoroScopePhotoId = birthHoroScopePhotoId;
	}

	/**
	 * @return the dobStr
	 */
	public String getDobStr() {
		return dobStr;
	}

	/**
	 * @param dobStr
	 *            the dobStr to set
	 */
	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
