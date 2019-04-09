package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccSetting {
	
	public static final String Prefix_Redis_Key="MccSetting";
	public static final String Prefix_Redis_Key_Separtor="-";
	
	private Integer setting_id;
	private Integer store_id;
	private String code;
	private String key;
	private String value;

}
