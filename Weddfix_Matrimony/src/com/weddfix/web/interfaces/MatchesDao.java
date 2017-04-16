package com.weddfix.web.interfaces;

import java.util.List;

import com.weddfix.web.formbean.MatchesFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;

public interface MatchesDao {

	public Long saveMatchesDetails(MatchesFormBean matchesDetailsFormBean);

	public List<MyPartnerPreferenceDetailsFormBean> loadNewMatchesDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails,
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(
			Long profileId);

	public Long saveShortlistedProfiles(MatchesFormBean matchesDetailsFormBean);

	public Long saveSendInterestProfiles(MatchesFormBean matchesDetailsFormBean);

	public List<MyPartnerPreferenceDetailsFormBean> loadShortlistedProfilesDetails(
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadViewedAndNotContactedProfileDetails(
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadRecentlyViewedProfileDetails(
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> searchProfiles(
			Long fromAge, Long toAge, Long genderId, Long maritalStatusId,
			Long countryId, Long stateId, Long userId);

	public Long updateEmailAndMobileCount(Long userId, Long userProfileId);

}
