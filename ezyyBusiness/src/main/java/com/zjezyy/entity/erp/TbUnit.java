package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbUnit {
	
	public static final String Prefix_Redis_Key="TbUnit";
	public static final String Prefix_Redis_Key_Separtor="-";
	
	private Integer iunitid;
	private String vcunitname;
	private String vceasycode;

}
