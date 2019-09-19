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
   public TbProductinfo_Eo() {}
   public TbProductinfo_Eo(TbProductinfo tbProductinfo,BigDecimal numprice,BigDecimal numlowprice,BigDecimal numassesscost,BigDecimal numguidprice) {
	   setIproductid(tbProductinfo.getIproductid());
	   setIproductkindid(tbProductinfo.getIproductkindid());
	   setVcproductname(tbProductinfo.getVcproductname());
	   setVceasycode(tbProductinfo.getVceasycode());
	   setVcproductcode(tbProductinfo.getVcproductcode());
	   setVcuniversalname(tbProductinfo.getVcuniversalname());
	   setVcstandard(tbProductinfo.getVcstandard());
	   setIproductunitid(tbProductinfo.getIproductunitid());
	   setIproducerid(tbProductinfo.getIproducerid());
	   setNumsaletaxrate(tbProductinfo.getNumsaletaxrate());
	   setNumpurchasetaxrate(tbProductinfo.getNumpurchasetaxrate());
	   setNumcountryprice(tbProductinfo.getNumcountryprice());
	   setNumwarningdays(tbProductinfo.getNumwarningdays());
	   setImanagementid(tbProductinfo.getImanagementid());//经营分类
	   setIstorageoptionid(tbProductinfo.getIstorageoptionid());//储存条件
	   setIydstate(tbProductinfo.getIydstate());//导入标志  1入药店  2入b2b
	   setFlagcold(tbProductinfo.getFlagcold());//是否冷藏
	   setFlagfreezing(tbProductinfo.getFlagfreezing());//是否冷冻
	   
	   this.numprice=numprice;
	   this.numlowprice=numlowprice;
	   this.numassesscost=numassesscost;
	   this.numguidprice=numguidprice;
   }
   
   
   
}
