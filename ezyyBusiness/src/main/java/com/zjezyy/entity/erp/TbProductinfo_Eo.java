package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbProductinfo_Eo extends TbProductinfo {
   private BigDecimal numprice;//销售中标价格
   private BigDecimal numlowprice;//最低售价
   private BigDecimal numassesscost;//成本价
   private BigDecimal numguidprice;//销售指导价
   
   private String vcunitname;
   private String vcproducername;
   
   
   public String toString() {
		String productname=new StringBuilder().append(this.getVcuniversalname())
				.append(" ")
				.append(this.getVcstandard())
				.toString();
		return productname;
	}
}
