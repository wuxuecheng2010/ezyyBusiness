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
   private BigDecimal numpurchasetaxrate;
   private BigDecimal numcountryprice;
   private Integer numwarningdays;
   private Integer imanagementid;//经营分类
   private Integer istorageoptionid;//储存条件
   private char flagcold;//是否冷藏
   private char flagfreezing;//是否冷冻
   
   
}
