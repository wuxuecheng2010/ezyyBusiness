package com.zjezyy.enums;


public enum OrderComment {
	//对应B2B的支付setting
	EXPIRED("客户未在规定时间内付款,超时自动取消处理."),
	SUCCESS("付款成功"),
	SHIPPED("已配送"),
	EXECING("订单处理中"),
	WAITEXE("等待处理"),
	;

	private String comment;

	OrderComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
	

}
