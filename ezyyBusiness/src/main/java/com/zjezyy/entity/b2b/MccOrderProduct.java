package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.TbMccOrderProduct;

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
		String productname=new StringBuilder().append(this.getName())
				.append(" ")
				.append(this.getModel())
				.toString();
		return productname;
	}
	
	
}
