package com.mulodo.miniblog.service;

import com.mulodo.miniblog.object.ResponseData;

public interface UserService  {
	
	public ResponseData register(String username, String firstname, String lastname,
			String email, String password);
	
	public ResponseData login(String username, String password);

	public ResponseData getUserInfo(String accessKey);
	
	public ResponseData editUserInfo(String firstname,
			String lastname, String email, String password, String status, String accessKey);

	public ResponseData changePassword(String currentPassword,
			String newPassword, String accessKey);

	public ResponseData searchUser(String name, String accessKey);
	
}
