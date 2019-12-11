package com.zjezyy.entity.im;

import lombok.Data;

@Data
public class TMessage {
	public TMessage(String groupname,String telephone, String message) {
		this.groupname=groupname;
		this.telephone = telephone;
		this.message = message;
	}

	private int id;
	private String groupname;
	private String telephone;
	private String message;
	private String credate;
	private String flagerr;//发送失败 0成功   其他失败
}
