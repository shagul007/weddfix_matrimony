package com.weddfix.web.util;

import java.io.Serializable;

public class InvalidLoginException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String msg;

	public InvalidLoginException(final String msg) {
		this.msg = msg;
		System.out.println("INVALID LOGIN EXCEPTION ARISES");
	}

	@Override
	public String toString() {
		return "Invalid Login Exception.....Invalid Login Access ------->>"
				+ this.msg;
	}


}
