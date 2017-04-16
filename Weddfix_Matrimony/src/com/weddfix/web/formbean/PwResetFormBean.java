package com.weddfix.web.formbean;

import java.io.Serializable;

public class PwResetFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long userId;
	private String resetKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

}
