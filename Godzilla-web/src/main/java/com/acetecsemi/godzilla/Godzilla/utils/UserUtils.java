package com.acetecsemi.godzilla.Godzilla.utils;

import javax.inject.Inject;

import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class UserUtils {
	@Inject
	private UserApplication userApplication;
	
	public boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return isUserExisted(useraccount)
				&& userCheck(useraccount, password);
	}
	
	public boolean isUserExisted(String useraccount) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		return result == null ? false : true;
	}

	public boolean userCheck(String useraccount, String password) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		if (result == null) {
			return false;
		}
		if (result.getUserPassword().equals(password)) {
			return true;
		}
		return false;
	}
}
