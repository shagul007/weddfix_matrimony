package com.weddfix.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class ImageAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	byte[] imageInByte = null;
	String imageId;

	@SuppressWarnings("unused")
	private HttpServletRequest servletRequest;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public ImageAction() {

	}

	public String execute() {
		return SUCCESS;
	}

	public byte[] getCustomImageInBytes() {

//		System.out.println("imageId" + imageId);

		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(getImageFile(this.imageId));
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if(this.imageId.endsWith(".png")) {
				ImageIO.write(originalImage, "png", baos);
			} else if(this.imageId.endsWith(".gif")) {
				ImageIO.write(originalImage, "gif", baos);
			} else if(this.imageId.endsWith(".jpeg")) {
				ImageIO.write(originalImage, "jpeg", baos);
			} else {
				ImageIO.write(originalImage, "jpg", baos);
			}
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageInByte;
	}

	private File getImageFile(String imageId) {
//		String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
		File file = new File(getText("image.path"), imageId);
//		System.out.println(file.toString());
		return file;
	}

	public String getCustomContentType() {
		return "image/jpeg";
	}

	public String getCustomContentDisposition() {
		return "anyname.jpg";
	}

	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

}