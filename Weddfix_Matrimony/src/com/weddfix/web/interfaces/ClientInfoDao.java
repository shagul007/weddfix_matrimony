package com.weddfix.web.interfaces;

import java.util.List;

import com.weddfix.web.formbean.OrganizationFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;

public interface ClientInfoDao {

	public PhotoGalleryFormBean loadPhotoGalleryInfo(Long userId);

	public List<PhotoGalleryFormBean> loadProfilePictures(Long userId, Long profileId);

	public Long savePhotoGalleryDetails(PhotoGalleryFormBean photoGalleryFormBean);
	
	public Long saveProfilePicture(PhotoGalleryFormBean photoGalleryFormBean, String profilePic);

	public List<OrganizationFormBean> loadAllOrgInfos();
}
