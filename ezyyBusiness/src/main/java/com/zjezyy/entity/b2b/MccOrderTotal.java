package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MccOrderTotal {
	private Integer order_total_id;
	private Integer order_id;
	private String code;
	private String title;
	private BigDecimal value;
	private Integer sort_order;
	
	public MccOrderTotal() {}
	
	public MccOrderTotal(Integer order_id,String code,String title,BigDecimal value,Integer sort_order) {
		this.order_id=order_id;
		this.code=code;
		this.title=title;
		this.value=value;
		this.sort_order=sort_order;
	}
}
