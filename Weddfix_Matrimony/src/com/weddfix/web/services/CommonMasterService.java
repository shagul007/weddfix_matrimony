package com.weddfix.web.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weddfix.web.formbean.CartDetailsFormBean;
import com.weddfix.web.formbean.CasteFormBean;
import com.weddfix.web.formbean.CityFormBean;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.formbean.CountryFormBean;
import com.weddfix.web.formbean.CurrencyFormBean;
import com.weddfix.web.formbean.EducationFormBean;
import com.weddfix.web.formbean.GenderFormBean;
import com.weddfix.web.formbean.HeightFormBean;
import com.weddfix.web.formbean.MaritalStatusFormBean;
import com.weddfix.web.formbean.MotherTonqueFormBean;
import com.weddfix.web.formbean.OccupationFormBean;
import com.weddfix.web.formbean.OrganizationFormBean;
import com.weddfix.web.formbean.ProfileFormBean;
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.formbean.ReligionFormBean;
import com.weddfix.web.formbean.RoleFormBean;
import com.weddfix.web.formbean.StateFormBean;
import com.weddfix.web.formbean.StatusFormBean;
import com.weddfix.web.formbean.SubscribersFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;
import com.weddfix.web.formbean.WeightFormBean;
import com.weddfix.web.implementation.CommonMasterDaoImpl;
import com.weddfix.web.interfaces.CommonMasterDao;

public class CommonMasterService {

	private Map<Object, Object> profileMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> genderMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> religionMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> motherTonqueMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> orgMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> roleMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> statusMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> maritalStatusMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> heightMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> weightMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> educationMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> occupationMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> currencyMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> countryMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> stateMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> cityMasterMap = new HashMap<Object, Object>();
	private Map<Object, Object> casteMasterMap = new HashMap<Object, Object>();

	List<ProfileFormBean> profileMasterList;
	List<GenderFormBean> genderMasterList;
	List<ReligionFormBean> religionMasterList;
	List<MotherTonqueFormBean> motherTonqueMasterList;
	List<RoleFormBean> roleMasterList;
	List<OrganizationFormBean> orgMasterList;
	List<StatusFormBean> statusMasterList;
	List<MaritalStatusFormBean> maritalStatusMasterList;
	List<HeightFormBean> heightMasterList;
	List<WeightFormBean> weightMasterList;
	List<EducationFormBean> educationMasterList;
	List<OccupationFormBean> occupationMasterList;
	List<CurrencyFormBean> currencyMasterList;
	List<CountryFormBean> countryMasterList;
	List<StateFormBean> stateMasterList;
	List<CityFormBean> cityMasterList;
	List<CasteFormBean> casteMasterList;
	List<UpgradePlanFormBean> upgradePlanMasterList;

	CommonMasterDao commonMasterDao = new CommonMasterDaoImpl();
	CommonMasterDaoImpl commonMasterDaoImpl = new CommonMasterDaoImpl();

	public Map<Object, Object> loadProfile() {

		profileMasterMap = commonMasterDao.loadProfileMaster();
		return profileMasterMap;
	}

	public Map<Object, Object> loadGender() {
		genderMasterMap = commonMasterDao.loadGenderMaster();
		return genderMasterMap;
	}
	
	public Map<Object, Object> loadGenderById(Long genderId) {
		genderMasterMap = commonMasterDao.loadGenderById(genderId);
		return genderMasterMap;
	}

	public Map<Object, Object> loadReligion() {

		religionMasterMap = commonMasterDao.loadReligionMaster();
		return religionMasterMap;
	}

	public Map<Object, Object> loadMotherTongue() {

		motherTonqueMasterMap = commonMasterDao.loadMotherTonqueMaster();
		return motherTonqueMasterMap;
	}

	public Map<Object, Object> loadOrg() {

		orgMasterMap = commonMasterDao.loadOrgMaster();
		return orgMasterMap;
	}

	public Map<Object, Object> loadRole() {

		roleMasterMap = commonMasterDao.loadRoleMaster();
		return roleMasterMap;
	}
	
	public Map<Object, Object> loadStatus() {

		statusMasterMap = commonMasterDao.loadStatusMaster();
		return statusMasterMap;
	}
	
	public Map<Object, Object> loadMaritalStatus() {

		maritalStatusMasterMap = commonMasterDao.loadMaritalStatusMaster();
		return maritalStatusMasterMap;
	}

	public Map<Object, Object> loadHeight() {

		heightMasterMap = commonMasterDao.loadHeightMaster();
		return heightMasterMap;
	}

	public Map<Object, Object> loadWeight() {

		weightMasterMap = commonMasterDao.loadWeightMaster();
		return weightMasterMap;
	}

	public Map<Object, Object> loadEducation() {

		educationMasterMap = commonMasterDao.loadEducationMaster();
		return educationMasterMap;
	}

	public Map<Object, Object> loadOccupation() {

		occupationMasterMap = commonMasterDao.loadOccupationMaster();
		return occupationMasterMap;
	}

	public Map<Object, Object> loadCurrency() {

		currencyMasterMap = commonMasterDao.loadCurrencyMaster();
		return currencyMasterMap;
	}

	public Map<Object, Object> loadState() {

		stateMasterMap = commonMasterDao.loadStateMaster();
		return stateMasterMap;
	}
	
	public Map<Object, Object> loadState(Long countryId) {

		stateMasterMap = commonMasterDao.loadStateMaster(countryId);
		return stateMasterMap;
	}
	
	public Map<Object, Object> loadCity(Long stateId) {

		cityMasterMap = commonMasterDao.loadCityMaster(stateId);
		return cityMasterMap;
	}
	
	public Map<Object, Object> loadCaste(Long religionId) {

		casteMasterMap = commonMasterDao.loadCasteMaster(religionId);
		return casteMasterMap;
	}

	public Map<Object, Object> loadCountry() {

		countryMasterMap = commonMasterDao.loadCountryMaster();
		return countryMasterMap;
	}

	public String checkUserAlreadyExist(String email) {
		String checkUser = commonMasterDao.checkUserAlreadyExist(email);
		return checkUser;
	}
	
	public String checkPartnerPreferenceAlreadyExist(Long userId) {
		String checkUser = commonMasterDao.checkPartnerPreferenceAlreadyExist(userId);
		return checkUser;
	}
	
	public String checkPersonalDetailsAlreadyExist(Long userId) {
		String checkUser = commonMasterDao.checkPersonalDetailsAlreadyExist(userId);
		return checkUser;
	}

	public Long saveCommentDetails(CommentsFormBean commentsFormBean) {
		Long id = commonMasterDao.saveCommentsDetails(commentsFormBean);
		return id;
	}

	public Long saveSubscriberDetails(SubscribersFormBean subscribersFormBean) {
		Long id = commonMasterDao.saveSubscriberDetails(subscribersFormBean);
		return id;
	}

	public String checkSubscriberEmailAlreadyExist(String emailId) {
		String checkUser = commonMasterDao
				.checkSubscriberEmailAlreadyExist(emailId);
		return checkUser;
	}

	public List<UserProfileFormBean> loadAllUserRoleDetails() {
		List<UserProfileFormBean> userProfileList = commonMasterDao
				.loadAllUserRoleDetails();
		return userProfileList;
	}
	
	public List<UpgradePlanFormBean> loadMyAccountDetails(Long userId) {
		List<UpgradePlanFormBean> myAccountDetails = commonMasterDao
				.loadMyAccountDetails(userId);
		return myAccountDetails;
	}
	
	public List<PromotionDetailsFormBean> loadPromotionDetails() {
		List<PromotionDetailsFormBean> promotionDetailsList = commonMasterDao
				.loadPromotionDetails();
		return promotionDetailsList;
	}
	
	public List<CartDetailsFormBean> loadCartDetails(Long userId) {
		List<CartDetailsFormBean> cartDetailsList = commonMasterDao
				.loadCartDetails(userId);
		return cartDetailsList;
	}

	public String updateUserRoleAndStatus(Long userId, Long userRole, String userRoleDesc,
			Long userStatus, Long updatedBy) {
		String status = commonMasterDao.updateUserRoleAndStatus(userId,
				userRole, userRoleDesc, userStatus, updatedBy);
		return status;
	}

	public String resetPassword(Long userId, String pwdHash) {
		String status = commonMasterDao.resetPassword(userId, pwdHash);
		return status;
	}

	public List<RoleFormBean> loadRoleMasterList() {
		roleMasterList = commonMasterDaoImpl.loadRoleMasterList();
		return roleMasterList;
	}

	public void updateRoleMaster(Long id, String roleName, String roleDesc,
			Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateRoleMaster(id, roleName, roleDesc, isActive,
				updatedBy);
	}

	public void addRoleMaster(RoleFormBean roleFormBean) {
		commonMasterDaoImpl.addRoleMaster(roleFormBean);
	}

	public void deleteRoleMaster(Long id) {
		commonMasterDaoImpl.deleteRoleMaster(id);
	}

	public List<ProfileFormBean> loadProfileMasterList() {
		profileMasterList = commonMasterDaoImpl.loadProfileMasterList();
		return profileMasterList;
	}

	public void updateProfileMaster(Long id, String profileName,
			String profileDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateProfileMaster(id, profileName, profileDesc,
				isActive, updatedBy);
	}

	public void addProfileMaster(ProfileFormBean profileFormBean) {
		commonMasterDaoImpl.addProfileMaster(profileFormBean);
	}

	public void deleteProfileMaster(Long id) {
		commonMasterDaoImpl.deleteProfileMaster(id);
	}

	public List<GenderFormBean> loadGenderMasterList() {
		genderMasterList = commonMasterDaoImpl.loadGenderMasterList();
		return genderMasterList;
	}

	public void updateGenderMaster(Long id, String genderName,
			String genderDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateGenderMaster(id, genderName, genderDesc,
				isActive, updatedBy);
	}

	public void addGenderMaster(GenderFormBean genderFormBean) {
		commonMasterDaoImpl.addGenderMaster(genderFormBean);
	}

	public void deleteGenderMaster(Long id) {
		commonMasterDaoImpl.deleteGenderMaster(id);
	}

	public List<ReligionFormBean> loadReligionMasterList() {
		religionMasterList = commonMasterDaoImpl.loadReligionMasterList();
		return religionMasterList;
	}

	public void updateReligionMaster(Long id, String religionName,
			String religionDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateReligionMaster(id, religionName,
				religionDesc, isActive, updatedBy);
	}

	public void addReligionMaster(ReligionFormBean religionFormBean) {
		commonMasterDaoImpl.addReligionMaster(religionFormBean);
	}

	public void deleteReligionMaster(Long id) {
		commonMasterDaoImpl.deleteReligionMaster(id);
	}

	public List<MotherTonqueFormBean> loadMotherTonqueMasterList() {
		motherTonqueMasterList = commonMasterDaoImpl
				.loadMotherTonqueMasterList();
		return motherTonqueMasterList;
	}

	public void updateMotherTonqueMaster(Long id, String motherTonqueName,
			String motherTonqueDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateMotherTonqueMaster(id, motherTonqueName,
				motherTonqueDesc, isActive, updatedBy);
	}

	public void addMotherTonqueMaster(MotherTonqueFormBean motherTonqueFormBean) {
		commonMasterDaoImpl.addMotherTonqueMaster(motherTonqueFormBean);
	}

	public void deleteMotherTonqueMaster(Long id) {
		commonMasterDaoImpl.deleteMotherTonqueMaster(id);
	}

	public List<StatusFormBean> loadStatusMasterList() {
		statusMasterList = commonMasterDaoImpl.loadStatusMasterList();
		return statusMasterList;
	}

	public void updateStatusMaster(Long id, String statusName,
			String statusDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateStatusMaster(id, statusName, statusDesc,
				isActive, updatedBy);
	}

	public void addStatusMaster(StatusFormBean statusFormBean) {
		commonMasterDaoImpl.addStatusMaster(statusFormBean);
	}

	public void deleteStatusMaster(Long id) {
		commonMasterDaoImpl.deleteStatusMaster(id);
	}

	public List<OrganizationFormBean> loadOrganizationMasterList() {
		orgMasterList = commonMasterDaoImpl.loadOrganizationMasterList();
		return orgMasterList;
	}

	public void updateOrganizationMaster(Long id, String orgName,
			String orgDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateOrganizationMaster(id, orgName, orgDesc,
				isActive, updatedBy);
	}

	public void addOrganizationMaster(OrganizationFormBean organizationFormBean) {
		commonMasterDaoImpl.addOrganizationMaster(organizationFormBean);
	}

	public void deleteOrganizationMaster(Long id) {
		commonMasterDaoImpl.deleteOrganizationMaster(id);
	}

	public List<MaritalStatusFormBean> loadMaritalStatusMasterList() {
		maritalStatusMasterList = commonMasterDaoImpl
				.loadMaritalStatusMasterList();
		return maritalStatusMasterList;
	}

	public void updateMaritalStatusMaster(Long id, String maritalStatusName,
			String maritalStatusDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateMaritalStatusMaster(id, maritalStatusName,
				maritalStatusDesc, isActive, updatedBy);
	}

	public void addMaritalStatusMaster(
			MaritalStatusFormBean maritalStatusFormBean) {
		commonMasterDaoImpl.addMaritalStatusMaster(maritalStatusFormBean);
	}

	public void deleteMaritalStatusMaster(Long id) {
		commonMasterDaoImpl.deleteMaritalStatusMaster(id);
	}

	public List<HeightFormBean> loadHeightMasterList() {
		heightMasterList = commonMasterDaoImpl.loadHeightMasterList();
		return heightMasterList;
	}

	public void updateHeightMaster(Long id, String feetInches,
			String feetInchesDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateHeightMaster(id, feetInches, feetInchesDesc,
				isActive, updatedBy);
	}

	public void addHeightMaster(HeightFormBean heightFormBean) {
		commonMasterDaoImpl.addHeightMaster(heightFormBean);
	}

	public void deleteHeightMaster(Long id) {
		commonMasterDaoImpl.deleteHeightMaster(id);
	}

	public List<WeightFormBean> loadWeightMasterList() {
		weightMasterList = commonMasterDaoImpl.loadWeightMasterList();
		return weightMasterList;
	}

	public void updateWeightMaster(Long id, String weight, String weightDesc,
			Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateWeightMaster(id, weight, weightDesc,
				isActive, updatedBy);
	}

	public void addWeightMaster(WeightFormBean weightFormBean) {
		commonMasterDaoImpl.addWeightMaster(weightFormBean);
	}

	public void deleteWeightMaster(Long id) {
		commonMasterDaoImpl.deleteWeightMaster(id);
	}

	public List<EducationFormBean> loadEducationMasterList() {
		educationMasterList = commonMasterDaoImpl.loadEducationMasterList();
		return educationMasterList;
	}

	public void updateEducationMaster(Long id, String education,
			String educationDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateEducationMaster(id, education, educationDesc,
				isActive, updatedBy);
	}

	public void addEducationMaster(EducationFormBean educationFormBean) {
		commonMasterDaoImpl.addEducationMaster(educationFormBean);
	}

	public void deleteEducationMaster(Long id) {
		commonMasterDaoImpl.deleteEducationMaster(id);
	}

	public List<OccupationFormBean> loadOccupationMasterList() {
		occupationMasterList = commonMasterDaoImpl.loadOccupationMasterList();
		return occupationMasterList;
	}

	public void updateOccupationMaster(Long id, String occupation,
			String occupationDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateOccupationMaster(id, occupation,
				occupationDesc, isActive, updatedBy);
	}

	public void addOccupationMaster(OccupationFormBean occupationFormBean) {
		commonMasterDaoImpl.addOccupationMaster(occupationFormBean);
	}

	public void deleteOccupationMaster(Long id) {
		commonMasterDaoImpl.deleteOccupationMaster(id);
	}

	public List<CurrencyFormBean> loadCurrencyMasterList() {
		currencyMasterList = commonMasterDaoImpl.loadCurrencyMasterList();
		return currencyMasterList;
	}

	public void updateCurrencyMaster(Long id, String currency,
			String currencyDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateCurrencyMaster(id, currency, currencyDesc,
				isActive, updatedBy);
	}

	public void addCurrencyMaster(CurrencyFormBean currencyFormBean) {
		commonMasterDaoImpl.addCurrencyMaster(currencyFormBean);
	}

	public void deleteCurrencyMaster(Long id) {
		commonMasterDaoImpl.deleteCurrencyMaster(id);
	}
	
	public List<CountryFormBean> loadCountryMasterList() {
		countryMasterList = commonMasterDaoImpl.loadCountryMasterList();
		return countryMasterList;
	}

	public void updateCountryMaster(Long id, String countryCode, String countryName, 
			String countryDesc, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateCountryMaster(id, countryCode, countryName, countryDesc,
				isActive, updatedBy);
	}

	public void addCountryMaster(CountryFormBean countryFormBean) {
		commonMasterDaoImpl.addCountryMaster(countryFormBean);
	}

	public void deleteCountryMaster(Long id) {
		commonMasterDaoImpl.deleteCountryMaster(id);
	}
	
	public List<StateFormBean> loadStateMasterList() {
		stateMasterList = commonMasterDaoImpl.loadStateMasterList();
		return stateMasterList;
	}

	public void updateStateMaster(Long id, String stateName, 
			Long countryId, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateStateMaster(id, stateName, countryId,
				isActive, updatedBy);
	}

	public void addStateMaster(StateFormBean stateFormBean) {
		commonMasterDaoImpl.addStateMaster(stateFormBean);
	}

	public void deleteStateMaster(Long id) {
		commonMasterDaoImpl.deleteStateMaster(id);
	}
	
	public List<CityFormBean> loadCityMasterList() {
		cityMasterList = commonMasterDaoImpl.loadCityMasterList();
		return cityMasterList;
	}

	public void updateCityMaster(Long id, String cityName, 
			Long stateId, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateCityMaster(id, cityName, stateId,
				isActive, updatedBy);
	}

	public void addCityMaster(CityFormBean cityFormBean) {
		commonMasterDaoImpl.addCityMaster(cityFormBean);
	}

	public void deleteCityMaster(Long id) {
		commonMasterDaoImpl.deleteCityMaster(id);
	}
	
	public List<CasteFormBean> loadCasteMasterList() {
		casteMasterList = commonMasterDaoImpl.loadCasteMasterList();
		return casteMasterList;
	}

	public void updateCasteMaster(Long id, String casteName, 
			Long religionId, Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateCasteMaster(id, casteName, religionId,
				isActive, updatedBy);
	}

	public void addCasteMaster(CasteFormBean casteFormBean) {
		commonMasterDaoImpl.addCasteMaster(casteFormBean);
	}

	public void deleteCasteMaster(Long id) {
		commonMasterDaoImpl.deleteCasteMaster(id);
	}
	
	public List<UpgradePlanFormBean> loadUpgradePlanMasterList() {
		upgradePlanMasterList = commonMasterDaoImpl.loadUpgradePlanMasterList();
		return upgradePlanMasterList;
	}

	public void updateUpgradePlanMaster(Long id, String planName, Long amount, Long validity, Long emailCount, Long mobileCount,
			Long videoCallCount, Boolean expressInterest, Boolean profileHighlight, Boolean viewProfile, Boolean protectPhoto, Boolean getSMSAlert,
			Boolean isActive, Long updatedBy) {
		commonMasterDaoImpl.updateUpgradePlanMaster(id, planName, amount, validity, emailCount, mobileCount,
				videoCallCount, expressInterest, profileHighlight, viewProfile, protectPhoto, getSMSAlert,
				isActive, updatedBy);
	}

	public void addUpgradePlanMaster(UpgradePlanFormBean upgradePlanFormBean) {
		commonMasterDaoImpl.addUpgradePlanMaster(upgradePlanFormBean);
	}

	public void deleteUpgradePlanMaster(Long id) {
		commonMasterDaoImpl.deleteUpgradePlanMaster(id);
	}

	public Long savePromotionDetails(
			PromotionDetailsFormBean promotionDetailsFormBean) {
		Long id = commonMasterDao.savePromotionDetails(promotionDetailsFormBean);
		return id;
	}

	public Long deletePromotion(Long promoId) {
		Long id = commonMasterDao.deletePromotion(promoId);
		return id;
	}

	public Long sendPromotion(String promoEmailId, String sendPromoCode, String sendPromoForAll, Long userId) {
		Long id = commonMasterDao.sendPromotion(promoEmailId, sendPromoCode, sendPromoForAll, userId);
		return id;
	}
	
	public Long saveCartDetails(
			CartDetailsFormBean cartDetailsFormBean) {
		Long id = commonMasterDao.saveCartDetails(cartDetailsFormBean);
		return id;
	}
	
	public List<PromotionDetailsFormBean> validatePromoCode(String promoCode) {
		List<PromotionDetailsFormBean> promotionDetailsList = commonMasterDao
				.validatePromoCode(promoCode);
		return promotionDetailsList;
	}

}
