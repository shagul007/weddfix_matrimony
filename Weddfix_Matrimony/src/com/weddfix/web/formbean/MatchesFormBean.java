package com.weddfix.web.formbean;

import java.io.Serializable;
import java.util.Date;

public class MatchesFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long profileId;
	private Long userId;
	private Boolean newMatches;
	private Boolean whoViewedMyProfile;
	private Boolean viewedAndNotContacted;
	private Boolean premiumMembers;
	private Boolean shortlistedProfiles;
	private Boolean recentlyViewedProfiles;
	private Boolean dontShowAlreadyViewed;
	private Boolean dontShowAlreadyContacted;
	private Boolean showProfilesWithPhoto;
	private Boolean sendMail;
	private Boolean sendInterested;
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
	 * @return the newMatches
	 */
	public Boolean getNewMatches() {
		return newMatches;
	}

	/**
	 * @param newMatches
	 *            the newMatches to set
	 */
	public void setNewMatches(Boolean newMatches) {
		this.newMatches = newMatches;
	}

	/**
	 * @return the whoViewedMyProfile
	 */
	public Boolean getWhoViewedMyProfile() {
		return whoViewedMyProfile;
	}

	/**
	 * @param whoViewedMyProfile
	 *            the whoViewedMyProfile to set
	 */
	public void setWhoViewedMyProfile(Boolean whoViewedMyProfile) {
		this.whoViewedMyProfile = whoViewedMyProfile;
	}

	/**
	 * @return the viewedAndNotContacted
	 */
	public Boolean getViewedAndNotContacted() {
		return viewedAndNotContacted;
	}

	/**
	 * @param viewedAndNotContacted
	 *            the viewedAndNotContacted to set
	 */
	public void setViewedAndNotContacted(Boolean viewedAndNotContacted) {
		this.viewedAndNotContacted = viewedAndNotContacted;
	}

	/**
	 * @return the premiumMembers
	 */
	public Boolean getPremiumMembers() {
		return premiumMembers;
	}

	/**
	 * @param premiumMembers
	 *            the premiumMembers to set
	 */
	public void setPremiumMembers(Boolean premiumMembers) {
		this.premiumMembers = premiumMembers;
	}

	/**
	 * @return the shortlistedProfiles
	 */
	public Boolean getShortlistedProfiles() {
		return shortlistedProfiles;
	}

	/**
	 * @param shortlistedProfiles
	 *            the shortlistedProfiles to set
	 */
	public void setShortlistedProfiles(Boolean shortlistedProfiles) {
		this.shortlistedProfiles = shortlistedProfiles;
	}

	/**
	 * @return the recentlyViewedProfiles
	 */
	public Boolean getRecentlyViewedProfiles() {
		return recentlyViewedProfiles;
	}

	/**
	 * @param recentlyViewedProfiles
	 *            the recentlyViewedProfiles to set
	 */
	public void setRecentlyViewedProfiles(Boolean recentlyViewedProfiles) {
		this.recentlyViewedProfiles = recentlyViewedProfiles;
	}

	/**
	 * @return the dontShowAlreadyViewed
	 */
	public Boolean getDontShowAlreadyViewed() {
		return dontShowAlreadyViewed;
	}

	/**
	 * @param dontShowAlreadyViewed
	 *            the dontShowAlreadyViewed to set
	 */
	public void setDontShowAlreadyViewed(Boolean dontShowAlreadyViewed) {
		this.dontShowAlreadyViewed = dontShowAlreadyViewed;
	}

	/**
	 * @return the dontShowAlreadyContacted
	 */
	public Boolean getDontShowAlreadyContacted() {
		return dontShowAlreadyContacted;
	}

	/**
	 * @param dontShowAlreadyContacted
	 *            the dontShowAlreadyContacted to set
	 */
	public void setDontShowAlreadyContacted(Boolean dontShowAlreadyContacted) {
		this.dontShowAlreadyContacted = dontShowAlreadyContacted;
	}

	/**
	 * @return the showProfilesWithPhoto
	 */
	public Boolean getShowProfilesWithPhoto() {
		return showProfilesWithPhoto;
	}

	/**
	 * @param showProfilesWithPhoto
	 *            the showProfilesWithPhoto to set
	 */
	public void setShowProfilesWithPhoto(Boolean showProfilesWithPhoto) {
		this.showProfilesWithPhoto = showProfilesWithPhoto;
	}

	/**
	 * @return the sendMail
	 */
	public Boolean getSendMail() {
		return sendMail;
	}

	/**
	 * @param sendMail
	 *            the sendMail to set
	 */
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * @return the sendInterested
	 */
	public Boolean getSendInterested() {
		return sendInterested;
	}

	/**
	 * @param sendInterested
	 *            the sendInterested to set
	 */
	public void setSendInterested(Boolean sendInterested) {
		this.sendInterested = sendInterested;
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
