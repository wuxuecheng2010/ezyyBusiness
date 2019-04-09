package com.zjezyy.mapper.erp;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbMccOrder;
@Repository
@Mapper
public interface TbMccOrderMapper {
	
	
	String SELECT_FIELDS="impid,mcc_order_id,mcc_store_id,mcc_store_name,mcc_customer_id,impdate,mcc_customer_group_id,mcc_fullname,mcc_email,mcc_telephone,mcc_total,erp_icustomerid,erp_vccustomername,mcc_shipping_zone,mcc_shipping_city,mcc_shipping_district,mcc_shipping_address,mcc_comment";
	
	String INSERT_FIELDS="impid,mcc_order_id,mcc_store_id,mcc_store_name,mcc_customer_id,impdate,mcc_customer_group_id,mcc_fullname,mcc_email,mcc_telephone,mcc_total,erp_icustomerid,erp_vccustomername,mcc_shipping_zone,mcc_shipping_city,mcc_shipping_district,mcc_shipping_address,mcc_comment";
	
	String INSERT_VALUES="#{impid},#{mcc_order_id},#{mcc_store_id},#{mcc_store_name},#{mcc_customer_id},sysdate,#{mcc_customer_group_id},#{mcc_fullname},#{mcc_email},#{mcc_telephone},#{mcc_total},#{erp_icustomerid},#{erp_vccustomername},#{mcc_shipping_zone},#{mcc_shipping_city},#{mcc_shipping_district},#{mcc_shipping_address},#{mcc_comment}";
	
	String TABLE_NAME="tb_mcc_order";
	
	@SelectKey(statement="select s_tb_mcc_order.nextval from dual", keyProperty="impid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbMccOrder tbMccOrder);
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where impid=#{impid}"})
	TbMccOrder getOne(int impid);
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where mcc_order_id=#{order_id}"})
	TbMccOrder getOneByOrderID(int order_id);
	

	
}