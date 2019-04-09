package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbProductinfo {
	
	public static final String Prefix_Redis_Key="TbProductinfo";
	public static final String Prefix_Redis_Key_Separtor="-";
	
   private Integer iproductid;
   private String vcproductname;
   private String vcproductcode;
   private String vcuniversalname;
   private String vcstandard;
   private Integer iproductunitid;
   private BigDecimal numsaletaxrate;
   private BigDecimal numcountryprice;
   private Integer numwarningdays;
}
