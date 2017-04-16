package com.weddfix.web.services;

import java.util.List;

import com.weddfix.web.formbean.AccountDetailsFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.formbean.MyPersonalDetailsFormBean;
import com.weddfix.web.formbean.PartnerPreferenceFormBean;
import com.weddfix.web.formbean.PersonalDetailsFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.implementation.RegisterDaoImpl;
import com.weddfix.web.interfaces.RegisterDao;

public class RegisterService {

	RegisterDao registerDao = new RegisterDaoImpl();

	public Long saveRegisterDetails(UserProfileFormBean userProfileFormBean) {
		Long id = registerDao.saveRegisterDetails(userProfileFormBean);
		return id;
	}

	public UserProfileFormBean loadUserProfileDetails(Long id) {
		UserProfileFormBean userProfileFormBean = registerDao.loadUserProfileDetails(id);
		return userProfileFormBean;
	}

	public UserProfileFormBean loadUserProfileByEmail(String email) {
		UserProfileFormBean userProfileFormBean = registerDao.loadUserProfileByEmail(email);
		return userProfileFormBean;
	}

	public Long savePersonalDetails(PersonalDetailsFormBean personalDetailsFormBean) {
		Long id = registerDao.savePersonalDetails(personalDetailsFormBean);
		return id;
	}

	public MyPersonalDetailsFormBean loadPersonalDetails(Long userId) {
		MyPersonalDetailsFormBean myPersonalDetailsFormBean = registerDao.loadPersonalDetailsByUserId(userId);
		return myPersonalDetailsFormBean;
	}

	public MyPersonalDetailsFormBean loadViewProfileDetails(Long profileUserId, Long userProfileId, Long userId) {
		MyPersonalDetailsFormBean myPersonalDetailsFormBean = registerDao.loadViewProfileDetails(profileUserId, userProfileId, userId);
		return myPersonalDetailsFormBean;
	}

	public Long savePartnerPreferenceDetails(PartnerPreferenceFormBean partnerPreferenceFormBean) {
		Long id = registerDao.savePartnerPreferenceDetails(partnerPreferenceFormBean);
		return id;
	}
	
	public PartnerPreferenceFormBean loadUpdatePartnerPreferenceDetails(Long userId) {
		PartnerPreferenceFormBean partnerPreferenceDetailsFormBean = registerDao.loadUpdatePartnerPreferenceDetails(userId);
		return partnerPreferenceDetailsFormBean;
	}

	public PersonalDetailsFormBean loadUpdatePersonalDetails(Long userId) {
		PersonalDetailsFormBean personalDetailsFormBean = registerDao.loadUpdatePersonalDetails(userId);
		return personalDetailsFormBean;
	}

	public MyPartnerPreferenceDetailsFormBean loadPartnerPreferenceDetails(Long userId) {
		MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetailsFormBean = registerDao.loadPartnerPreferenceDetails(userId);
		return myPartnerPreferenceDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadSimilarPartnerPreferenceDetails(
			MyPartnerPreferenceDetailsFormBean myPartnerPreferenceDetails, Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> myPartnerPreferenceDetailsFormBean = registerDao.loadSimilarPartnerPreferenceDetails(myPartnerPreferenceDetails, userId);
		return myPartnerPreferenceDetailsFormBean;
	}

	public List<MyPartnerPreferenceDetailsFormBean> loadWhoViewedMyProfileDetails(
			Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> myPartnerPreferenceDetailsFormBean = registerDao.loadWhoViewedMyProfileDetails(profileId);
		return myPartnerPreferenceDetailsFormBean;
	}

	public Long deactivateProfile(AccountDetailsFormBean accountDetailsFormBean, Long userId) {
		Long id = registerDao.deactivateProfile(accountDetailsFormBean, userId);
		return id;
	}

	public Long activateProfile(AccountDetailsFormBean accountDetailsFormBean,
			Long userId) {
		Long id = registerDao.activateProfile(accountDetailsFormBean, userId);
		return id;
	}
	
	public Long deleteProfile(AccountDetailsFormBean accountDetailsFormBean,
			Long userId) {
		Long id = registerDao.deleteProfile(accountDetailsFormBean, userId);
		return id;
	}
	
	public Long updateMyPrivacy(AccountDetailsFormBean accountDetailsFormBean, Long userId) {
		Long id = registerDao.updateMyPrivacy(accountDetailsFormBean, userId);
		return id;
	}

	public List<UpgradePlanFormBean> loadUpgradePlanDetails() {
		List<UpgradePlanFormBean> upgradePlanDetailsFormBean = registerDao.loadUpgradePlanDetails();
		return upgradePlanDetailsFormBean;
	}

	public String updateMobileVerificationCode(Long userId) {
		String id = registerDao.updateMobileVerificationCode(userId);
		return id;
	}

	public String verifyCodeAndUpdateMobile(Long mobile, Long userId) {
		String id = registerDao.verifyCodeAndUpdateMobile(mobile, userId);
		return id;
	}
	
	public String updateMobileVerificationCodeDetails(Long userId) {
		String id = registerDao.updateMobileVerificationCodeDetails(userId);
		return id;
	}

}
