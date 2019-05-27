package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.TbCustomerKindPrice;

import lombok.Data;
@Data
public class MccTbCustomerKindPrice {
	private Integer id;
	private Integer isid;
	private Integer icustomerkindid;
	private Integer iproductid;
	private BigDecimal numprice;
	private BigDecimal numlowprice;
	private BigDecimal numassesscost;
	private BigDecimal numguidprice;

	public MccTbCustomerKindPrice() {

	}

	public MccTbCustomerKindPrice(TbCustomerKindPrice tbCustomerKindPrice) {
        this.isid=tbCustomerKindPrice.getIsid();
        this.icustomerkindid=tbCustomerKindPrice.getIcustomerkindid();
        this.iproductid=tbCustomerKindPrice.getIproductid();
        this.numprice=tbCustomerKindPrice.getNumprice();
        this.numlowprice=tbCustomerKindPrice.getNumlowprice();
        this.numassesscost=tbCustomerKindPrice.getNumassesscost();
        this.numguidprice=tbCustomerKindPrice.getNumguidprice();
	}

}
