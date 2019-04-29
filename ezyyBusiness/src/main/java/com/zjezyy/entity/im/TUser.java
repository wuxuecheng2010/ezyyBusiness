package com.zjezyy.entity.im;

import lombok.Data;

@Data
public class TUser {
	private Integer id;
	private String username;
	private String password;
	private char flagstop;
	private String memo;
}
