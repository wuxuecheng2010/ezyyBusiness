package com.zjezyy.service;

import java.util.List;

public interface SystemService {
	

     
   //创建销售订单单号  不能用于事务管理的标签下
 	String genBillCode(String prefix)throws RuntimeException;
 	
 	//创建销售订单单号  采用Http方式获取
 	String genBillCodeForTransactional(String prefix)throws RuntimeException;

 	String getBillCodeURL(String prefix);
 	
 	//获取接口类型ID  
 	int getTypeId(String bif)throws RuntimeException;
 	
 	//发送手机短信
 	void sendTelMsg(String msg,List<String> tels);
 	
 	
}
