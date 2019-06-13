package com.zjezyy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zjezyy.enums.Payment;
import com.zjezyy.service.PayService;
import com.zjezyy.service.SettingService;

//@RestController
//@RequestMapping(value = "/notify")
public class NotifyController extends BaseController{
	
	@Autowired
	SettingService settingServiceImpl;
	
	@Resource(name="aliPayServiceImpl")
	PayService  aliPayServiceImpl;
	
	@Resource(name = "wxPayServiceImpl")
	PayService wxPayServiceImpl;
	
	//测试
	@RequestMapping(value = "/test",method=RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response) {
		return "success";
	}
    
	//支付宝 手机网页支付 异步通知地址
	@RequestMapping(value = "/alipay_wap",method=RequestMethod.POST)
	public void alipayWapNotify(HttpServletRequest request, HttpServletResponse response) {
		aliPayServiceImpl.notify(request,response, Payment.ALIPAY_WAP);
	}
	
	//微信 手机网页支付 异步通知地址
	@RequestMapping(value = "/wxpay_wap",method=RequestMethod.POST)
	public void wxpayWapNotify(HttpServletRequest request, HttpServletResponse response) {
		wxPayServiceImpl.notify(request,response, Payment.WXPAY_WAP);
		
	}
	
	
	//支付宝 扫码支付 异步通知地址
	@RequestMapping(value = "/qrcode_alipay",method=RequestMethod.POST)
	public void qrcodeAlipayNotify(HttpServletRequest request, HttpServletResponse response) {
		aliPayServiceImpl.notify(request,response, Payment.QRCODE_ALIPAY);
	}
	
	
	//微信 扫码支付 异步通知地址
	@RequestMapping(value = "/qrcode_wxpay",method=RequestMethod.POST)
	public void qrcodeWxpayNotify(HttpServletRequest request, HttpServletResponse response) {
		wxPayServiceImpl.notify(request,response, Payment.QRCODE_WXPAY);
	}
	
	
}
