package com.zjezyy.entity.erp;

import java.math.BigDecimal;
import java.util.Date;

import com.zjezyy.entity.b2b.MccOrder;

import lombok.Data;

@Data
public class TbMccOrder {
	private Integer impid;
	private String impdate;
	private Integer erp_icustomerid;
	private String erp_vccustomername;

	private Integer mcc_order_id;
	private Integer mcc_store_id;
	private String mcc_store_name;
	private Integer mcc_customer_id;
	private Integer mcc_customer_group_id;
	private String mcc_fullname;
	private String mcc_email;
	private String mcc_telephone;
	private BigDecimal mcc_total;
	private BigDecimal mcc_order_product_total;
	private String mcc_shipping_zone;
	private String mcc_shipping_city;
	private String mcc_shipping_district;
	private String mcc_shipping_address;
	private String mcc_comment;
	
	public static final  String Prefix_Redis_Key="TbMccOrder";
	public static final  String Prefix_Redis_Key_Separtor="-";
	
	public TbMccOrder() {
		super();
	}
	
	public TbMccOrder(MccOrder mccOrder,int icustomerid, String vccustomername) {
		
		
		//private Integer impid;
		//private String impdate;
		this.erp_icustomerid=icustomerid;
		this.erp_vccustomername=vccustomername;

		this.mcc_order_id=mccOrder.getOrder_id();
		this.mcc_store_id=mccOrder.getStore_id();
		this.mcc_store_name=mccOrder.getStore_name();
		this.mcc_customer_id=mccOrder.getCustomer_id();
		this.mcc_customer_group_id=mccOrder.getCustomer_group_id();
		this.mcc_fullname=mccOrder.getFullname();
		this.mcc_email=mccOrder.getEmail();
		this.mcc_telephone=mccOrder.getTelephone();
		this.mcc_total=mccOrder.getTotal();
		this.mcc_order_product_total=mccOrder.getOrder_product_total();
		this.mcc_shipping_zone=mccOrder.getShipping_zone();
		this.mcc_shipping_city=mccOrder.getShipping_city();
		this.mcc_shipping_district=mccOrder.getShipping_district();
		this.mcc_shipping_address=mccOrder.getShipping_address();;
		this.mcc_comment=mccOrder.getComment();

	}

	

}
