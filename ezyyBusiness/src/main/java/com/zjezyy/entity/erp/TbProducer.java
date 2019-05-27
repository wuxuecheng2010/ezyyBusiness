package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbProducer {
	
	public static final String Prefix_Redis_Key="TbProducer";
	public static final String Prefix_Redis_Key_Separtor="-";
	
	private Integer iproducerid;
	private String vcproducername;
	private String vceasycode;

}
