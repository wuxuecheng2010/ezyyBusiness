package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccCustomer;
@Repository
@Mapper
public interface MccCustomerMapper {
	
	String SELECT_FIELDS="customer_id,customer_group_id,fullname,email,telephone,erp_icustomerid";
	String TABLE_NAME="mcc_customer";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where customer_id = #{customer_id}"})
	MccCustomer getOne(int customer_id);
}
