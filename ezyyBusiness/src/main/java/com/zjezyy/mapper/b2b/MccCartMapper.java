package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MccCartMapper {
	
	@Delete({"delete from mcc_cart where customer_id=#{customer_id}"})
	int deleteByCustomerID(int customer_id);

}
