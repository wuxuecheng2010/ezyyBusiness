package com.zjezyy.enums;

public enum ShippingMethod {
	按每件计运费率("item.ite"),免运费("free.free");
	
	private String code;
	
	ShippingMethod(String code){
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	
	
	
	
}
