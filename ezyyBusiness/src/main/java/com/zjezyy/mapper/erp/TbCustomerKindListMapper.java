package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbCustomerKindList;
@Repository
@Mapper
public interface TbCustomerKindListMapper {
	
	String SELECT_FIELDS="isid,icustomerkindid,icustomerid,dtend";
	
    String TABLE_NAME="tb_customerkindlist";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerid = #{icustomerid}"})
	TbCustomerKindList getOne(int icustomerid);
	

	
	

}
