package com.zjezyy.service;

import com.zjezyy.entity.b2b.MccPayResult;

//支付业务接口类
public interface PayService {
	
	MccPayResult payResultQuery(Integer order_id)  throws RuntimeException;
	
}
