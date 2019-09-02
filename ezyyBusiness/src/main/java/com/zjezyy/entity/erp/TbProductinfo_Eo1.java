package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbProductinfo_Eo1 extends TbProductinfo {
   
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
