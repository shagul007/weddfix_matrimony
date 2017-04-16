package com.weddfix.web.services;

import java.util.List;

import com.weddfix.web.formbean.MailBoxFormBean;
import com.weddfix.web.formbean.MyPartnerPreferenceDetailsFormBean;
import com.weddfix.web.implementation.MailBoxDaoImpl;
import com.weddfix.web.interfaces.MailBoxDao;

public class MailBoxService {

	MailBoxDao mailBoxDao = new MailBoxDaoImpl();

	public Long saveMailBoxDetails(MailBoxFormBean mailBoxDetailsFormBean) {
		Long id = mailBoxDao.saveMailBoxDetails(mailBoxDetailsFormBean);
		return id;
	}
	
	public Long updateReadMailDetails(MailBoxFormBean mailBoxDetailsFormBean) {
		Long id = mailBoxDao.updateReadMailDetails(mailBoxDetailsFormBean);
		return id;
	}
	
	public List<MyPartnerPreferenceDetailsFormBean> loadInboxDetails(
			Long profileId) {
		List<MyPartnerPreferenceDetailsFormBean> inboxDetailsFormBean = mailBoxDao.loadInboxDetails(profileId);
		return inboxDetailsFormBean;
	}
	
	public List<MyPartnerPreferenceDetailsFormBean> loadAcceptedDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> acceptedDetailsFormBean = mailBoxDao.loadAcceptedDetails(userId);
		return acceptedDetailsFormBean;
	}
	
	public List<MyPartnerPreferenceDetailsFormBean> loadNotInterestedDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> notInterestedDetailsFormBean = mailBoxDao.loadNotInterestedDetails(userId);
		return notInterestedDetailsFormBean;
	}
	
	public List<MyPartnerPreferenceDetailsFormBean> loadSentDetails(
			Long userId) {
		List<MyPartnerPreferenceDetailsFormBean> sentDetailsFormBean = mailBoxDao.loadSentDetails(userId);
		return sentDetailsFormBean;
	}
	
	public Long saveAcceptedProfile(MailBoxFormBean mailBoxDetailsFormBean) {
		Long id = mailBoxDao.saveAcceptedProfile(mailBoxDetailsFormBean);
		return id;
	}
	
	public Long saveNotInterestedProfile(MailBoxFormBean mailBoxDetailsFormBean) {
		Long id = mailBoxDao.saveNotInterestedProfile(mailBoxDetailsFormBean);
		return id;
	}

}
