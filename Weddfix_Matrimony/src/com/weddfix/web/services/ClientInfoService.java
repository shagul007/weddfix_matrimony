package com.weddfix.web.services;

import java.util.List;

import com.weddfix.web.formbean.OrganizationFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.implementation.ClientInfoDaoImpl;
import com.weddfix.web.interfaces.ClientInfoDao;

public class ClientInfoService {

	ClientInfoDao clientInfoDao = new ClientInfoDaoImpl();

	public List<OrganizationFormBean> loadAllOrgInfos() {
		List<OrganizationFormBean> clientInfoList = clientInfoDao.loadAllOrgInfos();
		return clientInfoList;
	}

	public PhotoGalleryFormBean loadPhotoGalleryInfo(Long userId) {
		PhotoGalleryFormBean photoGalleryInfo = clientInfoDao.loadPhotoGalleryInfo(userId);
		return photoGalleryInfo;
	}

	public Long savePhotoGalleryDetails(PhotoGalleryFormBean photoGalleryFormBean) {
		Long id = clientInfoDao.savePhotoGalleryDetails(photoGalleryFormBean);
		return id;
	}
	
	public Long saveProfilePicture(PhotoGalleryFormBean photoGalleryFormBean, String profilePic) {
		Long id = clientInfoDao.saveProfilePicture(photoGalleryFormBean, profilePic);
		return id;
	}

	public List<PhotoGalleryFormBean> loadProfilePictures(Long userId, Long profileId) {
		List<PhotoGalleryFormBean> photoGalleryInfo = clientInfoDao.loadProfilePictures(userId, profileId);
		return photoGalleryInfo;
	}

}
