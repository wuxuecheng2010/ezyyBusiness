package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.pms.TmpPurchaseHTS;

import lombok.Data;

@Data
public class MccOrderProduct {
	private Integer order_product_id;
	private Integer order_id;
	private Integer product_id;
	private String name;
	private String model;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal total;
	private BigDecimal tax;

	public String toString() {
		String productname = new StringBuilder().append(this.getName()).append(" ").append(this.getModel()).toString();
		return productname;
	}

	public MccOrderProduct() {

	}

	public MccOrderProduct(Integer order_id,MccProduct_Eo mccProduct_Eo,TmpPurchaseHTS tmpPurchaseHTS) {
		this.order_id = order_id;
		this.product_id = mccProduct_Eo.getProduct_id();
		this.name = mccProduct_Eo.getName();
		this.model = mccProduct_Eo.getModel();
		this.quantity = tmpPurchaseHTS.getNumnumber();
		this.price = tmpPurchaseHTS.getNumprice();
		this.total = tmpPurchaseHTS.getNummoney();
	}

}
