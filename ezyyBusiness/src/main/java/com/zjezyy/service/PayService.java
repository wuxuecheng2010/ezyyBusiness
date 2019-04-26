package com.zjezyy.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.enums.Payment;

//支付业务接口类
public interface PayService {
	
	
	MccPayResult payResultQuery(String  ordercode)  throws RuntimeException;
	
	//异步通知处理
	void notify(HttpServletRequest request,HttpServletResponse response,Payment payment) throws RuntimeException;
	
}
