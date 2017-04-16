package com.weddfix.web.interfaces;

import java.util.List;
import java.util.Map;

import com.weddfix.web.formbean.CartDetailsFormBean;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.formbean.PromotionDetailsFormBean;
import com.weddfix.web.formbean.SubscribersFormBean;
import com.weddfix.web.formbean.UpgradePlanFormBean;
import com.weddfix.web.formbean.UserProfileFormBean;

public interface CommonMasterDao {

	public Map<Object, Object> loadStateMaster(Long countryId);

	public Map<Object, Object> loadCountryMaster();

	public String checkUserAlreadyExist(String email);

	public Long saveCommentsDetails(CommentsFormBean commentsFormBean);

	public Long saveSubscriberDetails(SubscribersFormBean subscribersFormBean);

	public String checkSubscriberEmailAlreadyExist(String emailId);

	public List<UserProfileFormBean> loadAllUserRoleDetails();

	public String updateUserRoleAndStatus(Long userId, Long userRole, String userRoleDesc, 
			Long userStatus, Long updatedBy);

	public String resetPassword(Long userId, String pwdHash);

	public Map<Object, Object> loadProfileMaster();

	public Map<Object, Object> loadGenderMaster();
	
	public Map<Object, Object> loadGenderById(Long genderId);

	public Map<Object, Object> loadReligionMaster();

	public Map<Object, Object> loadMotherTonqueMaster();

	public Map<Object, Object> loadOrgMaster();

	public Map<Object, Object> loadRoleMaster();

	public Map<Object, Object> loadMaritalStatusMaster();

	public Map<Object, Object> loadHeightMaster();

	public Map<Object, Object> loadWeightMaster();

	public Map<Object, Object> loadEducationMaster();

	public Map<Object, Object> loadOccupationMaster();

	public Map<Object, Object> loadCurrencyMaster();

	public Map<Object, Object> loadCityMaster(Long stateId);
	
	public Map<Object, Object> loadCasteMaster(Long religionId);

	public Map<Object, Object> loadStateMaster();

	public Map<Object, Object> loadStatusMaster();

	public String checkPartnerPreferenceAlreadyExist(Long userId);

	public List<PromotionDetailsFormBean> loadPromotionDetails();

	public Long savePromotionDetails(
			PromotionDetailsFormBean promotionDetailsFormBean);

	public Long deletePromotion(Long promoId);

	public Long sendPromotion(String promoEmailId, String sendPromoCode,
			String sendPromoForAll, Long userId);

	public List<CartDetailsFormBean> loadCartDetails(Long userId);

	public Long saveCartDetails(CartDetailsFormBean cartDetailsFormBean);

	public List<PromotionDetailsFormBean> validatePromoCode(String promoCode);

	public List<UpgradePlanFormBean> loadMyAccountDetails(Long userId);

	public String checkPersonalDetailsAlreadyExist(Long userId);

}
