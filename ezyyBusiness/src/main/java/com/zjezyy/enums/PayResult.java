package com.zjezyy.enums;

/**
 * 
 * @ClassName: PayType
 * @Description: 支付方式
 * @author wuxuecheng
 * @date 2019年3月5日
 *
 */
public enum PayResult {
	//对应B2B的支付setting
	SUCCESS("SUCCESS", "成功"),
	FAIL("FAIL","失败"),;

	private String code;
	private String msg;

	PayResult(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	public String getCode() {
		return code;
	}

}
