package com.zjezyy.entity;

import lombok.Data;

@Data
public class UserInfo {
	private String username;
	private String password;

	public UserInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserInfo() {

	}

}
