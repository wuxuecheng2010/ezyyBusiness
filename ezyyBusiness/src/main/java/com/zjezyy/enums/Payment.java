package com.zjezyy.enums;

/**
 * 
 * @ClassName: PayType
 * @Description: 支付方式
 * @author wuxuecheng
 * @date 2019年3月5日
 *
 */
public enum Payment {
	//对应B2B的支付setting
	QRCODE_WXPAY("qrcode_wxpay", "微信扫码支付"),
	QRCODE_ALIPAY("qrcode_alipay","支付宝扫码支付"),;

	private String code;
	private String msg;

	Payment(String code, String msg) {
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
