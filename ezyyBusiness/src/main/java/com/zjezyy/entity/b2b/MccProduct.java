package com.zjezyy.entity.b2b;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.utils.DateUtils;

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
	public MccProduct() {
		
	}

	
public MccProduct(String model,String sku,String location,int erpiproductid) {

	this.model = model;
	this.sku = sku;
	this.upc = "";
	this.ean = "";
	this.jan = "";
	this.isbn = "";
	this.mpn = "";
	this.location = location;
	this.quantity = new BigDecimal("999999");
	this.minimum = 1;
	this.subtract = true;
	this.stock_status_id = 5;
	
	this.manufacturer_id = 0;
	this.shipping = true;
	this.price =  new BigDecimal("0");
	this.points =0;
	this.weight = new BigDecimal("0");
	this.weight_class_id = 1;
	this.length =new BigDecimal("0");
	this.width =new BigDecimal("0");
	this.height = new BigDecimal("0");
	this.length_class_id = 1;
	this.status = 0;
	this.viewed=0;
	this.tax_class_id = 0; 
	this.sort_order = 1; 
	
	String now="2019-05-24";
	try {
		 now=DateUtils.dateFormat(new Date(), DateUtils.DATE_PATTERN);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	this.date_added = now;
	this.date_modified=now;
	this.date_available = now;
	this.erpiproductid=erpiproductid;
	
	}
	
}
