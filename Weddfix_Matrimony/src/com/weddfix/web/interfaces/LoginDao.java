package com.weddfix.web.interfaces;

import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.util.InvalidLoginException;

public interface LoginDao {

	public LoginFormBean checkLogin(String email, String password) throws InvalidLoginException;

	public LoginFormBean sessionPasswordHash(Long userId);

	public LoginFormBean loadProfileSessionDetails(String email);
}
