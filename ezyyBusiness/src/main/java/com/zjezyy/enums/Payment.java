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
	COD("cod","货到付款"),
	QRCODE_WXPAY("qrcode_wxpay", "微信扫码支付"),
	QRCODE_ALIPAY("qrcode_alipay","支付宝扫码支付"),
	ALIPAY_WAP("alipay_wap","支付宝手机网页支付"),
	WXPAY_WAP("wxpay_wap","微信手机网页支付"),
	WXPAY_JSAPI("wxpay_jsapi","微信网页支付JSAPI"),
	;

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
