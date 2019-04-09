package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MccProduct {
	
	public static final String Prefix_Redis_Key="MccProduct";
	public static final String Prefix_Redis_Key_Separtor="-";
	
	private Integer product_id;
	private String model;
	private String sku;
	private String upc;
	private String ean;
	private String jan;
	private String isbn;
	private String mpn;
	private String location;
	private BigDecimal quantity;
	private Integer stock_status_id;
	private String image;
	private Integer manufacturer_id;
	private Boolean shipping;
	private BigDecimal price;
	private Integer points;
	private Integer tax_class_id;
	private String date_available;
	private BigDecimal weight;
	private Integer weight_class_id;
	private BigDecimal length;
	private BigDecimal width;
	private BigDecimal height;
	private Integer length_class_id;
	private Boolean subtract;
	private Integer minimum;
	private Integer sort_order;
	private Integer status;
	private Integer viewed;
	private String  date_added;
	private String date_modified;
	private Integer erpiproductid;

}
