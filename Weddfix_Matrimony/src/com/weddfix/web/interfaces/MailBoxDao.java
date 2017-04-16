package com.weddfix.web.interfaces;

import java.util.List;

import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;

public interface MailBoxDao {

	public Long saveMailBoxDetails(MailBoxFormBean mailBoxDetailsFormBean);

	public List<MyPartnerPreferenceDetailsFormBean> loadInboxDetails(
			Long profileId);

	public Long saveAcceptedProfile(MailBoxFormBean mailBoxDetailsFormBean);

	public Long saveNotInterestedProfile(MailBoxFormBean mailBoxDetailsFormBean);

	public Long updateReadMailDetails(MailBoxFormBean mailBoxDetailsFormBean);

	public List<MyPartnerPreferenceDetailsFormBean> loadAcceptedDetails(
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadNotInterestedDetails(
			Long userId);

	public List<MyPartnerPreferenceDetailsFormBean> loadSentDetails(
			Long userId);

}
