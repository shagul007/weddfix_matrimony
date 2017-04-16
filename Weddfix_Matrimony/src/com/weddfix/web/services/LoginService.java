package com.weddfix.web.services;

import com.weddfix.web.formbean.LoginFormBean;
import com.weddfix.web.implementation.LoginDaoImpl;
import com.weddfix.web.interfaces.LoginDao;
import com.weddfix.web.util.InvalidLoginException;

public class LoginService {

	LoginDao loginDao = new LoginDaoImpl();

	public LoginFormBean checkLogin(String email, String password) throws InvalidLoginException {
		LoginFormBean loginBean = loginDao.checkLogin(email, password);
		return loginBean;
	}
	
	public LoginFormBean sessionPasswordHash(Long userId) {
		LoginFormBean loginBean = loginDao.sessionPasswordHash(userId);
		return loginBean;
	}

	public LoginFormBean loadProfileSessionDetails(String email) {
		LoginFormBean loginBean = loginDao.loadProfileSessionDetails(email);
		return loginBean;
	}

}
