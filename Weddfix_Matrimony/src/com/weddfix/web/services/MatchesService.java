package com.weddfix.web.services;

import java.util.List;

import com.weddfix.web.formbean.MatchesFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.implementation.MatchesDaoImpl;
import com.weddfix.web.interfaces.MatchesDao;

public class MatchesService {

	MatchesDao matchesDao = new MatchesDaoImpl();

	public Long saveMatchesDetails(MatchesFormBean matchesDetailsFormBean) {
		Long id = matchesDao.saveMatchesDetails(matchesDetailsFormBean);
		return id;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadNewMatchesDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails,
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> myPartnerPreferenceDetailsFormBean = matchesDao
				.loadNewMatchesDetails(myPartnerPreferenceDetails, userId);
		return myPartnerPreferenceDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(
			Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> viewedProfilesDetailsFormBean = matchesDao
				.loadWhoViewedMyProfileDetails(profileId);
		return viewedProfilesDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadViewedAndNotContactedProfileDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> viewedAndNotContactedProfileDetailsFormBean = matchesDao
				.loadViewedAndNotContactedProfileDetails(userId);
		return viewedAndNotContactedProfileDetailsFormBean;
	}

	public Long saveShortlistedProfiles(MatchesFormBean matchesDetailsFormBean) {
		Long id = matchesDao.saveShortlistedProfiles(matchesDetailsFormBean);
		return id;
	}

	public Long saveSendInterestProfiles(MatchesFormBean matchesDetailsFormBean) {
		Long id = matchesDao.saveSendInterestProfiles(matchesDetailsFormBean);
		return id;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadShortlistedProfilesDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> myPartnerPreferenceDetailsFormBean = matchesDao
				.loadShortlistedProfilesDetails(userId);
		return myPartnerPreferenceDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadRecentlyViewedProfileDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> recentlyViewedProfileDetailsFormBean = matchesDao
				.loadRecentlyViewedProfileDetails(userId);
		return recentlyViewedProfileDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> searchProfiles(
			Long fromAge, Long toAge, Long genderId, Long maritalStatusId,
			Long countryId, Long stateId, Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> searchProfileDetailsFormBean = matchesDao
				.searchProfiles(fromAge, toAge, genderId, maritalStatusId,
						countryId, stateId, userId);
		return searchProfileDetailsFormBean;
	}

	public Long updateEmailAndMobileCount(Long userId, Long userProfileId) {
		Long id = matchesDao.updateEmailAndMobileCount(userId, userProfileId);
		return id;
	}

}
