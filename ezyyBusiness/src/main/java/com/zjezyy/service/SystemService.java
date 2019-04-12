package com.zjezyy.service;

import java.util.List;

public interface SystemService {
	
	
	
	/**
	 * 
	* @Title: genToken
	* @Description: 根据用户名密码  创建token
	* @param @param userName  用户名
	* @param @param password  密码
	* @param @return    token 字符串
	* @author wuxuecheng
	* @return String    返回类型
	* @throws
	 */
     String genToken(String userName,String password)throws RuntimeException;
     
   /**
    * 
   * @Title: checkToken
   * @Description: 验证token是否有效
   * @param @param token
   * @param @return    参数
   * @author wuxuecheng
   * @return boolean    返回类型
   * @throws
    */
     boolean checkToken(String token)throws RuntimeException;
     
     
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
