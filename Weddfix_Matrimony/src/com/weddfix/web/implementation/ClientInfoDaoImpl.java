package com.weddfix.web.implementation;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.weddfix.web.formbean.OrganizationFormBean;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.interfaces.ClientInfoDao;
import com.weddfix.web.util.CommonConstants;
import com.weddfix.web.util.HibernateUtil;

/**
 * 
 * 
 * @author Shagul
 * 
 */
public class ClientInfoDaoImpl implements ClientInfoDao, SessionAware {

	Session conn;

	private static final Logger logger = Logger
			.getLogger(ClientInfoDaoImpl.class);

	Long id;

	public void setSession(Map<String, Object> arg0) {

	}
	
	@SuppressWarnings("unchecked")
	public List<OrganizationFormBean> loadAllOrgInfos() {
		List<OrganizationFormBean> orgInfoList = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			orgInfoList = conn
						.getNamedQuery("getAllOrganization").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return orgInfoList;
	}

	public PhotoGalleryFormBean loadPhotoGalleryInfo(Long userId) {
		PhotoGalleryFormBean photoGalleryInfo = new PhotoGalleryFormBean();
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			List<?> photoGalleryInfos = conn
					.getNamedQuery("getPhotoGalleryInfoByUserId")
					.setLong("userId", userId).list();
			Iterator<?> itr = photoGalleryInfos.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				photoGalleryInfo.setId(Long.parseLong(obj[0].toString()));
				photoGalleryInfo.setOrgId(Long.parseLong(obj[2].toString()));
				photoGalleryInfo.setFileName(obj[3].toString());
			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return photoGalleryInfo;
	}

	public Long savePhotoGalleryDetails(
			PhotoGalleryFormBean photoGalleryFormBean) {

		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		try {

			logger.info("-----------Insert photo gallery details Method--------------");

			if (photoGalleryFormBean.getId() != null) {
				if (photoGalleryFormBean.getFileName() != null) {
					conn.getNamedQuery("updatePhotoGalleryImageById")
							.setLong("id", photoGalleryFormBean.getId())
							.setLong("orgId", photoGalleryFormBean.getOrgId())
							.setString("fileName",
									photoGalleryFormBean.getFileName())
							.setString("fileType",
									photoGalleryFormBean.getFileType())
							.setLong("fileSize",
									photoGalleryFormBean.getFileSize())
							.setString("filePath",
									photoGalleryFormBean.getFilePath())
							.setLong("updatedBy",
									photoGalleryFormBean.getUpdatedBy())
							.setDate("updatedDate", new Date()).executeUpdate();
				} else {
					conn.getNamedQuery("updatePhotoGalleryById")
							.setLong("id", photoGalleryFormBean.getId())
							.setLong("orgId", photoGalleryFormBean.getOrgId())
							.setLong("updatedBy",
									photoGalleryFormBean.getUpdatedBy())
							.setDate("updatedDate", new Date()).executeUpdate();
				}
				id = 1L;
			} else {
				photoGalleryFormBean.setStatusId(CommonConstants.ACTIVE);
				photoGalleryFormBean.setCreatedDate(new Date());
				id = (Long) conn.save(photoGalleryFormBean);
			}

			tx.commit();

		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			tx.rollback();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}

	public Long saveProfilePicture(
			PhotoGalleryFormBean photoGalleryFormBean, String profilePic) {

		conn = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = conn.beginTransaction();
		try {

			logger.info("-----------Insert photo gallery details Method--------------");

			if (!profilePic.equals("null")) {
					conn.getNamedQuery("updateProfilePictureById")
							.setLong("id", Long.parseLong(profilePic))
							.setString("fileName",
									photoGalleryFormBean.getFileName())
							.setString("fileType",
									photoGalleryFormBean.getFileType())
							.setLong("fileSize",
									photoGalleryFormBean.getFileSize())
							.setString("filePath",
									photoGalleryFormBean.getFilePath())
							.setLong("updatedBy",
									photoGalleryFormBean.getUpdatedBy())
							.setDate("updatedDate", new Date()).executeUpdate();
				id = 1L;
			} else {
				photoGalleryFormBean.setStatusId(CommonConstants.ACTIVE);
				photoGalleryFormBean.setCreatedDate(new Date());
				id = (Long) conn.save(photoGalleryFormBean);
					conn.getNamedQuery("updatePersonalDetailsByUserId")
							.setLong("userId",
									photoGalleryFormBean.getCreatedBy())
							.setLong("profilePictureId", id)
							.setLong("updatedBy",
									photoGalleryFormBean.getCreatedBy())
							.setDate("updatedDate", new Date()).executeUpdate();
			}

			tx.commit();

		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			tx.rollback();
			logger.error("Exception ocured while inserting data into database");

		} finally {
			conn.flush();
			conn.close();
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<PhotoGalleryFormBean> loadProfilePictures(Long userId,
			Long profileId) {
		List<PhotoGalleryFormBean> photoGalleryInfo = null;
		try {
			conn = HibernateUtil.getSessionFactory().openSession();
			photoGalleryInfo = conn.getNamedQuery("getProfilePicturesByUserId")
					.setLong("userId", userId)
					.setString("profileId", "P" + profileId).list();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			conn.flush();
			conn.close();
		}
		return photoGalleryInfo;
	}

}
