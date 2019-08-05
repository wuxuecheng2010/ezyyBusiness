package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MccOrder {
	private Integer order_id;
	private Integer store_id;
	private Integer customer_id;
	private String store_name;
	private Integer customer_group_id;
	private String fullname;
	private String email;
	private String telephone;
	private BigDecimal total;
	private BigDecimal order_product_total;
	private String comment;
	private String shipping_zone;
	private String shipping_city;
	private String shipping_district;
	private String shipping_address;
	private Integer order_status_id;
	private String payment_code;
	private String payment_method;
	private String ordercode;
	private Integer copy_flag;//是否复制到ERP
	
	
}