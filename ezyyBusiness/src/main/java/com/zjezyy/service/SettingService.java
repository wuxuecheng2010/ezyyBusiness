package com.zjezyy.service;

import java.util.Map;

import com.zjezyy.entity.b2b.MccSetting;
import com.zjezyy.enums.EzyySettingKey;

public interface SettingService {
	
	/**
	 * 
	* @Title: getMccSetting
	* @Description: 获取B2B配置表信息
	* @param @param code
	* @param @param key
	* @param @return
	* @param @throws RuntimeException    参数
	* @author wuxuecheng
	* @return MccSetting    返回类型
	* @throws
	 */
	MccSetting getMccSetting(String code, String key) throws RuntimeException;
	
	
	
	/**
	 * 
	* @Title: getMccSetting
	* @Description: 获取B2B配置表信息
	* @param @param code
	* @param @param key
	* @param @return
	* @param @throws RuntimeException    参数
	* @author wuxuecheng
	* @return MccSetting    返回类型
	* @throws
	 */
	String getMccSettingValue(String code, String key)throws RuntimeException;
	
	/**
	 * 
	* @Title: getAllMccSetting
	* @Description: 获取所有setting值  根据code
	* @param @param code
	* @param @return
	* @param @throws RuntimeException    参数
	* @author wuxuecheng
	* @return Map<String,String>    返回类型
	* @throws
	 */
	Map<String ,String> getEzyySettings()throws RuntimeException;
	
	
	String getEzyySettingValue(EzyySettingKey key) throws RuntimeException;
	
}
