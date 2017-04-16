package com.weddfix.web.interfaces;

import java.util.List;

import com.weddfix.web.formbean.AccountDetailsFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;

public interface RegisterDao {

	public Long saveRegisterDetails(UserProfileFormBean userProfileFormBean);

	public UserProfileFormBean loadUserProfileDetails(Long id);

	public UserProfileFormBean loadUserProfileByEmail(String email);

	public Long savePersonalDetails(
			PersonalDetailsFormBean personalDetailsFormBean);

	public Long savePartnerPreferenceDetails(
			PartnerPreferenceFormBean partnerPreferenceFormBean);

	public MyPartnerPreferenceDetailsFormBean loadPartnerPreferenceDetails(Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadSimilarPartnerPreferenceDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails, Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(
			Long profileId);

	public PartnerPreferenceFormBean loadUpdatePartnerPreferenceDetails(
			Long userId);

	public PersonalDetailsFormBean loadUpdatePersonalDetails(Long userId);

	public Long deactivateProfile(
			AccountDetailsFormBean accountDetailsFormBean, Long userId);

	public Long activateProfile(AccountDetailsFormBean accountDetailsFormBean,
			Long userId);

	public Long deleteProfile(AccountDetailsFormBean accountDetailsFormBean,
			Long userId);

	public List<UpgradePlanFormBean> loadUpgradePlanDetails();

	public MyPersonalDetailsFormBean loadViewProfileDetails(Long profileUserId,
			Long userProfileId, Long userId);

	public MyPersonalDetailsFormBean loadPersonalDetailsByUserId(Long userId);

	public String updateMobileVerificationCode(Long userId);

	public String verifyCodeAndUpdateMobile(Long mobile, Long userId);

	public String updateMobileVerificationCodeDetails(Long userId);

	public Long updateMyPrivacy(AccountDetailsFormBean accountDetailsFormBean, Long userId);
	
}
