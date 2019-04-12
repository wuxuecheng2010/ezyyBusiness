package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrder;

@Repository
@Mapper
public interface MccOrderMapper {
	String SELECT_FIELDS="order_id,store_id,customer_id,store_name,customer_group_id,fullname,email,telephone,total,comment,shipping_zone,shipping_city,shipping_district,shipping_address,order_status_id,payment_code";
	String TABLE_NAME="mcc_order";
	
	//根据订单号查订单
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where order_id = #{order_id}"})
	MccOrder getOne(int order_id);
	

	//根据业务类型查询未付款订单集合  
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where  order_status_id=17 and date_added>=CURRENT_TIMESTAMP - INTERVAL (#{expire}+100000) MINUTE order by order_id"})
	List<MccOrder> getUnPayMccOrderList( int expire);

	//根据业务类型查询超时订单集合  
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where  order_status_id=17 and date_added<CURRENT_TIMESTAMP - INTERVAL (#{expire}+0.3) MINUTE order by order_id"})
	List<MccOrder> getUnPayExpireMccOrderList( int expire);
	
	//将订单保存为已付款待发货状态
	@Update({"update mcc_order set order_status_id=#{order_status_id} where order_id=#{order_id}"})
	int updateMccOrderStatus(int order_id,String order_status_id);
	
}
