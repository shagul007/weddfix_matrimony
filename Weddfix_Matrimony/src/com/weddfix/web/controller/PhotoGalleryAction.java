package com.weddfix.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.weddfix.web.formbean.PhotoGalleryFormBean;
import com.weddfix.web.services.ClientInfoService;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.util.FilesUtil;
import com.weddfix.web.util.Util;

public class PhotoGalleryAction extends ActionSupport implements
		ModelDriven<PhotoGalleryFormBean>, Preparable, ServletRequestAware,
		ServletResponseAware, SessionAware, ServletContextAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private Map<String, Object> session;

	private Map<String, Object> photoGalleryInfoBean = new HashMap<String, Object>();

	@SuppressWarnings("unused")
	private ServletContext context;
	private String profilePic;
	private String filesPath;
	private String userFileFileName;
	private File userFile[] = new File[1];
	private String filenames[] = new String[1];
	private Long fileSize;
	private String fileContentType;

	PhotoGalleryFormBean photoGalleryFormBean = new PhotoGalleryFormBean();
	ClientInfoService clientInfoService = new ClientInfoService();
	CommonMasterService commonMasterService = new CommonMasterService();

	public String savePhotoGalleryDetails() {
		HttpSession session = request.getSession(true);
		try {
			uploadPhotoGalleryFile();
			if (photoGalleryFormBean.getId() != null) {
				session.setAttribute("update", "UPDATE");
				photoGalleryFormBean.setUpdatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
			} else {
				session.setAttribute("update", null);
				photoGalleryFormBean.setCreatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
			}
			Long status = clientInfoService
					.savePhotoGalleryDetails(photoGalleryFormBean);
			loadPhotoGallery();
			if (status != null) {
				if (session.getAttribute("update") != null) {
					session.setAttribute("successMessage",
							"Updated Successfully...");
					session.setAttribute("hrefParamSuccess", "client_info");
					System.out.println("Updated Successfully...");
				} else {
					session.setAttribute("successMessage",
							"Registered Successfully...");
					session.setAttribute("hrefParamSuccess", "home");
					System.out.println("Registered Successfully...");
				}

				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}

	public String uploadProfilePicture() {
		HttpSession session = request.getSession(true);
		try {
			uploadPhotoGalleryFile();
			if (!getProfilePic().equals("null")) {
				photoGalleryFormBean.setUpdatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
			} else {
				session.setAttribute("update", null);
				photoGalleryFormBean.setCreatedBy(Long.parseLong(session
						.getAttribute("userId").toString()));
			}
			Long status = clientInfoService
					.saveProfilePicture(photoGalleryFormBean, getProfilePic());
			if (status != null) {
				if (session.getAttribute("update") != null) {
					session.setAttribute("successMessage",
							"Updated Successfully...");
					session.setAttribute("hrefParamSuccess", "client_info");
					System.out.println("Updated Successfully...");
				} else {
					session.setAttribute("successMessage",
							"Inserted Successfully...");
					session.setAttribute("hrefParamSuccess", "home");
					System.out.println("Inserted Successfully...");
				}

				return "success";
			} else {
				session.setAttribute("errorMessage",
						"Something went wrong. Please try again later.");
				session.setAttribute("hrefParamError", "home");
				return "error";
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage",
					"Something went wrong. Please try again later.");
			session.setAttribute("hrefParamError", "home");
			return "error";
		}
	}

	public void uploadPhotoGalleryFile() {

		try {
			String fileName = "";
			String filePath = getText("image.path");
			// creates the directory if it does not exist
			File uploadDir = new File(filePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String[] nameOfImages = this.userFileFileName.split(",");

			for (int i = 0; i < userFile.length; i++) {
				fileName = nameOfImages[i].trim();
				String fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
				filenames[i] = nameOfImages[i];
				Long imageId = Util.getSequenceId("next_image_id_seq");
				String customFileName = null;
				if (photoGalleryFormBean.getOrgId() != null) {
					customFileName = photoGalleryFormBean.getOrgId() + "_"
							+ imageId + "." + fileType;
				} else {
					customFileName = photoGalleryFormBean.getPhotoType() + "_"
							+ imageId + "." + fileType;
				}
				String filePathAndFileName = filePath + customFileName;
				// System.out.println("filepath >>" + filePathAndFileName);
				FilesUtil.saveFile(userFile[i], customFileName, filePath);
				// FileUtils.copyFile(this.userFile[0],new File(getPath1));
				setFileSize(userFile[i].length());
				if (this.userFileFileName.endsWith(".jpeg")
						|| this.userFileFileName.endsWith(".jpg")) {
					fileContentType = "Image File";
				} else if (this.userFileFileName.endsWith(".png")
						|| this.userFileFileName.endsWith(".gif")) {
					fileContentType = "Image File";
				}
				if (i == 0) {
					photoGalleryFormBean.setFileName(customFileName);
					photoGalleryFormBean.setFileType(fileContentType);
					photoGalleryFormBean.setFileSize(fileSize);
					photoGalleryFormBean.setFilePath(filePathAndFileName);

				}
				// FileUtils.copyFile(userFile[i], new File(getPath1));
			}
			// FileUtils.copyFile(this.userFile[0], fileToCreate);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public String loadPhotoGallery() {
		HttpSession session = request.getSession();
		try {
			PhotoGalleryFormBean photoGalleryInfo = clientInfoService
					.loadPhotoGalleryInfo((Long) session.getAttribute("userId"));

			photoGalleryInfoBean.put("id", photoGalleryInfo.getId());
			photoGalleryInfoBean.put("orgId", photoGalleryInfo.getOrgId());
			photoGalleryInfoBean
					.put("fileName", photoGalleryInfo.getFileName());
			return "success";
		}

		catch (Exception e) {
			// e.printStackTrace();
			return "error";
		}
	}

	public void prepare() throws Exception {
		photoGalleryFormBean = new PhotoGalleryFormBean();
	}

	public PhotoGalleryFormBean getModel() {
		return photoGalleryFormBean;
	}

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.response = httpServletResponse;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public String getFilesPath() {
		return filesPath;
	}

	public void setFilesPath(String filesPath) {
		this.filesPath = filesPath;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public File[] getUserFile() {
		return userFile;
	}

	public void setUserFile(File[] userFile) {
		this.userFile = userFile;
	}

	public String[] getFilenames() {
		return filenames;
	}

	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public String getUserFileFileName() {
		return userFileFileName;
	}

	public void setUserFileFileName(String userFileFileName) {
		this.userFileFileName = userFileFileName;
	}

	public Map<String, Object> getPhotoGalleryInfoBean() {
		return photoGalleryInfoBean;
	}

	public void setPhotoGalleryInfoBean(Map<String, Object> photoGalleryInfoBean) {
		this.photoGalleryInfoBean = photoGalleryInfoBean;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
