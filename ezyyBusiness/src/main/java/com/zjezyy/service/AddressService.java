package com.zjezyy.service;

import com.zjezyy.entity.Address;
import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.erp.TbCustomer;

public interface AddressService {
	// 保存b2b地址
	MccAddress saveMccAddress(int customer_id, TbCustomer tbCustomer) throws RuntimeException;

	// 中文地址转 纬度,经度字符串
	String addressToLatLngString(String address) throws RuntimeException;

	// 根据经纬度 获取标准的县级名称
	Address latLngStringToAddress(String latLngString) throws RuntimeException;
}
