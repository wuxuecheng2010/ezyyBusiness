package com.zjezyy.entity.b2b;

import java.math.BigDecimal;
import java.util.List;

import com.zjezyy.entity.pms.TmpPurchaseHT;
import com.zjezyy.entity.pms.TmpPurchaseHTS;

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
	
	
	private String invoice_prefix;
	private String store_url;
	private String  payment_fullname;
	private String payment_company;
	private String payment_address;
	private String payment_city;
	private Integer payment_city_id;
	private String payment_district;
	private Integer payment_district_id;
	private String payment_postcode;
	private String payment_country;
	private Integer payment_country_id;
	private String payment_zone;
	private Integer payment_zone_id;
	private String payment_address_format;
	private String payment_custom_field;
	private String payment_telephone;
	private String shipping_fullname;
	private String shipping_company;
	private String shipping_telephone;
	private Integer shipping_city_id;
	private Integer shipping_district_id;
	private String shipping_postcode;
	private String shipping_country;
	private Integer shipping_country_id;
	private Integer shipping_zone_id;
	private String shipping_address_format;
	private String shipping_custom_field;
	private String shipping_method;
	private String shipping_code;
	private Integer affiliate_id;
	private String commission;
	private Integer marketing_id;
	private String tracking;
	private Integer language_id;
	private Integer currency_id;
	private String currency_code;
	private BigDecimal currency_value;
	private String ip;
	private String forwarded_ip;
	private String fax;
	private String user_agent;
	private String accept_language;
	private String import_order_code;
	
	private String custom_field;
	
	public static final  String Prefix_Redis_Key="MccOrder";
	public static final  String Prefix_Redis_Key_Separtor="-";
	
	
	public MccOrder() {
		
	}
	
	public MccOrder(TmpPurchaseHT tmpPurchaseHT,List<TmpPurchaseHTS> tmpPurchaseHTSList,MccCustomer mccCustomer,MccAddress_Eo mccAddress_Eo ,String store_name,int order_status_id,String payment_code,String payment_method,String import_order_code,String shipping_method,String shipping_code) {
		//this. order_id;
		this. store_id=0;
		this. customer_id=mccCustomer.getCustomer_id();
		this. store_name=store_name;
		this. customer_group_id=1;
		this. fullname=mccCustomer.getFullname();
		this. email=mccCustomer.getEmail();
		this. telephone=mccCustomer.getTelephone();
		this. total=tmpPurchaseHT.getNummoneyall();
		this. order_product_total=BigDecimal.ZERO;
		for(TmpPurchaseHTS tmpPurchaseHTS:tmpPurchaseHTSList) {
			this.order_product_total=order_product_total.add(tmpPurchaseHTS.getNummoney()==null?BigDecimal.ZERO:tmpPurchaseHTS.getNummoney());
		}
		
		//this. order_product_total=tmpPurchaseHTSList.isEmpty()?BigDecimal.ZERO:new BigDecimal(tmpPurchaseHTSList.size());
		this. comment=tmpPurchaseHT.getVcmemo()==null?"":tmpPurchaseHT.getVcmemo();
		this. shipping_zone=mccAddress_Eo.getZone_name();
		this. shipping_city=mccAddress_Eo.getCity_name();
		this. shipping_district=mccAddress_Eo.getDistrict_name();
		this. shipping_address=mccAddress_Eo.getAddress();
		this. order_status_id=order_status_id;
		this. payment_code=payment_code;
		this. payment_method=payment_method;
		this. ordercode="";//在线付款用的商家订单号
		this. copy_flag=-1;//是否复制到ERP
		
		
		this. invoice_prefix="INV-2016-00";
		this. store_url="http://ezyf.zjezyy.com/";
		this. payment_fullname=mccCustomer.getFullname();
		this. payment_company=mccAddress_Eo.getCompany();
		this. payment_address=mccAddress_Eo.getAddress();
		this. payment_city=mccAddress_Eo.getCity();
		this. payment_city_id=mccAddress_Eo.getCity_id();
		this. payment_district=mccAddress_Eo.getDistrict_name();
		this. payment_district_id=mccAddress_Eo.getDistrict_id();
		this. payment_postcode=mccAddress_Eo.getPostcode();
		this. payment_country=mccAddress_Eo.getCountry_name();
		this. payment_country_id=mccAddress_Eo.getCountry_id();
		this. payment_zone=mccAddress_Eo.getZone_name();
		this. payment_zone_id=mccAddress_Eo.getZone_id();
		this. payment_address_format="";
		this. payment_custom_field="[]";
		this. payment_telephone=mccAddress_Eo.getShipping_telephone();
		this. shipping_fullname=mccAddress_Eo.getFullname();
		this. shipping_company=mccAddress_Eo.getCompany();
		this. shipping_telephone=mccAddress_Eo.getShipping_telephone();
		this. shipping_city_id=mccAddress_Eo.getCity_id();
		this. shipping_district_id=mccAddress_Eo.getDistrict_id();
		this. shipping_postcode=mccAddress_Eo.getPostcode();
		this. shipping_country=mccAddress_Eo.getCountry_name();
		this. shipping_country_id=mccAddress_Eo.getCountry_id();
		this. shipping_zone_id=mccAddress_Eo.getZone_id();
		this. shipping_address_format="";
		this. shipping_custom_field="[]";
		this. shipping_method=shipping_method;
		this. shipping_code=shipping_code;
		this. affiliate_id=0;
		this. commission="0";
		this. marketing_id=0;
		this. tracking="";
		this. language_id=1;
		this. currency_id=4;
		this. currency_code="CNY";
		this. currency_value=BigDecimal.ONE;
		this. ip="::1";
		this. forwarded_ip="";
		this. fax="";
		this. user_agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2906.0 Safari/537.36";
		this. accept_language="zh-CN,zh;q=0.8";
		this. import_order_code=import_order_code;
		this.custom_field="";
	}
	
}