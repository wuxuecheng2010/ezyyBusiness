package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccOrderHistory;

@Repository
@Mapper
public interface MccOrderMapper {
	String SELECT_FIELDS="order_id,store_id,customer_id,store_name,customer_group_id,fullname,email,telephone,total,order_product_total,comment,shipping_zone,shipping_city,shipping_district,shipping_address,order_status_id,payment_method,payment_code,ordercode,copy_flag,import_order_code";
	String TABLE_NAME="mcc_order";
	
	//根据订单号查订单
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where order_id = #{order_id}"})
	MccOrder getOne(int order_id);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where import_order_code = #{import_order_code}"})
	MccOrder getOneByImportCode(String import_order_code);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ordercode = #{ordercode}"})
	MccOrder getOneByCode(String ordercode);
	

	//根据业务类型查询未付款订单集合  
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where  order_status_id=17 and date_added>=CURRENT_TIMESTAMP - INTERVAL (#{expire}+1) MINUTE order by order_id"})
	List<MccOrder> getUnPayMccOrderList( int expire);

	//根据业务类型查询超时订单集合  
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where  order_status_id=17 and date_added<CURRENT_TIMESTAMP - INTERVAL (#{expire}+0.3) MINUTE order by order_id"})
	List<MccOrder> getUnPayExpireMccOrderList( int expire);
	
	//将订单保存为已付款待发货状态
	@Update({"update mcc_order set order_status_id=#{order_status_id} where order_id=#{order_id}"})
	int updateMccOrderStatus(int order_id,String order_status_id);
	
	@Update({"update mcc_order set copy_flag=#{copy_flag} where order_id=#{order_id}"})
	int updateMccOrderCopyFlag(int order_id,int copy_flag);


	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"  where order_status_id=1 and ifnull(copy_flag,0)=0  and payment_code='cod' and customer_id in (select customer_id from mcc_customer where  flag_credit_user=1  )"})
	List<MccOrder> getCreditOrder();
	
	
	String insql="INSERT INTO mcc_order " 
			+" SET invoice_prefix = #{invoice_prefix,jdbcType=VARCHAR} ," 
			+" store_id = #{store_id,jdbcType=INTEGER}, "
			+" store_name = #{store_name,jdbcType=VARCHAR}, "
			+" store_url = #{store_url,jdbcType=VARCHAR}, "
			+" customer_id = #{customer_id,jdbcType=INTEGER}, "
			+" customer_group_id = #{customer_group_id,jdbcType=INTEGER}, "
			+" fullname = #{fullname,jdbcType=VARCHAR}, "
			+" email = #{email,jdbcType=VARCHAR}, "
			+" telephone = #{telephone,jdbcType=VARCHAR}, "
			+" fax =#{fax,jdbcType=VARCHAR} , order_product_total=#{order_product_total,jdbcType=INTEGER},"
			+" custom_field = #{custom_field,jdbcType=VARCHAR}, "
			+ "payment_fullname = #{payment_fullname,jdbcType=VARCHAR}, "
			+" payment_company = #{payment_company,jdbcType=VARCHAR}, "
			+" payment_address = #{payment_address,jdbcType=VARCHAR}, "
			+" payment_city = #{payment_city,jdbcType=VARCHAR}, "
			+ "payment_city_id = #{payment_city_id,jdbcType=INTEGER}, "
			+" payment_district = #{payment_district,jdbcType=VARCHAR}, "
			+" payment_district_id = #{payment_district_id,jdbcType=INTEGER}, "
			+" payment_postcode = #{payment_postcode,jdbcType=VARCHAR}, "
			+ "payment_country = #{payment_country,jdbcType=VARCHAR}, "
			+" payment_country_id = #{payment_country_id,jdbcType=INTEGER}, "
			+" payment_zone = #{payment_zone,jdbcType=VARCHAR}, "
			+" payment_zone_id = #{payment_zone_id,jdbcType=INTEGER}, "
			+ "payment_address_format = #{payment_address_format,jdbcType=VARCHAR}, "
			+" payment_custom_field = #{payment_custom_field,jdbcType=VARCHAR}, "
			+" payment_method = #{payment_method,jdbcType=VARCHAR}, "
			+" payment_code = #{payment_code,jdbcType=VARCHAR}, "
			+ "payment_telephone = #{payment_telephone,jdbcType=VARCHAR}, "
			+" shipping_fullname = #{shipping_fullname,jdbcType=VARCHAR}, "
			+" shipping_company = #{shipping_company,jdbcType=VARCHAR}, "
			+" shipping_telephone =#{shipping_telephone,jdbcType=VARCHAR}, "
			+" shipping_address = #{shipping_address,jdbcType=VARCHAR}, "
			+" shipping_city =#{shipping_city,jdbcType=VARCHAR}, "
			+" shipping_city_id =#{shipping_city_id,jdbcType=INTEGER}, "
			+ "shipping_district = #{shipping_district,jdbcType=VARCHAR}, "
			+" shipping_district_id = #{shipping_district_id,jdbcType=INTEGER}, "
			+" shipping_postcode = #{shipping_postcode,jdbcType=VARCHAR}, "
			+" shipping_country = #{shipping_country,jdbcType=VARCHAR}, "
			+" shipping_country_id = #{shipping_country_id,jdbcType=INTEGER}, "
			+" shipping_zone = #{shipping_zone,jdbcType=VARCHAR}, "
			+" shipping_zone_id = #{shipping_zone_id,jdbcType=INTEGER}, "
			+" shipping_address_format = #{shipping_address_format,jdbcType=VARCHAR}, "
			+" shipping_custom_field = #{shipping_custom_field,jdbcType=VARCHAR}, "
			+" shipping_method = #{shipping_method,jdbcType=VARCHAR}, "
			+" shipping_code = #{shipping_code,jdbcType=VARCHAR}, "
			+" comment = #{comment,jdbcType=VARCHAR}, total = #{total,jdbcType=DECIMAL}, "
			+" affiliate_id = #{affiliate_id,jdbcType=INTEGER}, "
			+" commission = #{commission,jdbcType=VARCHAR}, "
			+" marketing_id = #{marketing_id,jdbcType=INTEGER}, "
			+" tracking = #{tracking,jdbcType=VARCHAR}, "
			+" language_id = #{language_id,jdbcType=INTEGER}, "
			+ "currency_id = #{currency_id,jdbcType=INTEGER}, "
			+" currency_code = #{currency_code,jdbcType=VARCHAR}, "
			+" currency_value = #{currency_value,jdbcType=DECIMAL}, "
			+" ip = #{ip,jdbcType=VARCHAR}, "
			+" forwarded_ip = #{forwarded_ip,jdbcType=VARCHAR}, "
			+" user_agent = #{user_agent,jdbcType=VARCHAR}, "
			+" accept_language =#{accept_language,jdbcType=VARCHAR}, "
			+" date_added = NOW(), "
			+" date_modified = NOW() ,"
			+ "import_order_code=#{import_order_code,jdbcType=VARCHAR},"
			+ "order_status_id=#{order_status_id,jdbcType=INTEGER}, "
			+ "copy_flag=#{copy_flag,jdbcType=INTEGER} ";
	
	@Insert({insql})
	@Options(useGeneratedKeys=true,keyProperty="order_id",keyColumn="order_id")
	int insert(MccOrder mccOrder);
	
	
	
	
	
}
