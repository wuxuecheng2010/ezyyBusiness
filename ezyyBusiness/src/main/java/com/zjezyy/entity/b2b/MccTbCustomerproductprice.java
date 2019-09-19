package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.SysPriorPrice;

import lombok.Data;

@Data
public class MccTbCustomerproductprice {

	private Integer id;
	private Integer icustomerid;
	private Integer iproductid;
	private BigDecimal price;
	private String updatedate;
	private Integer customer_id;
	private Integer productid_id;
	
	public MccTbCustomerproductprice(MccCustomer mccCustomer, MccProduct mccProduct, BigDecimal priorPrice) {
		this.icustomerid=mccCustomer.getErp_icustomerid();
		this.iproductid=mccProduct.getErpiproductid();
		this.price=priorPrice;
		this.customer_id=mccCustomer.getCustomer_id();
		this.productid_id=mccProduct.getProduct_id();
	}
	
}
