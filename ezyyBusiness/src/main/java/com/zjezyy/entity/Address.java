package com.zjezyy.entity;

import lombok.Data;

//百度地址简析后的地址 
@Data
public class Address {
	private String province;//省
	private String city;//市
	private String district;//县
	private String street;//街道
	

	public Address() {
		
	}

	public Address(String province, String city, String district, String street) {
		super();
		this.province = province;
		this.city = city;
		this.district = district;
		this.street = street;

	}

}
