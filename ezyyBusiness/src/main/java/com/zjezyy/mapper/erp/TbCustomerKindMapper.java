package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbCustomerKind;
@Repository
@Mapper
public interface TbCustomerKindMapper {
	
	String SELECT_FIELDS="icustomerkindid,vccustomerkindname";
	
    String TABLE_NAME="tb_customerkind";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerkindid = #{icustomerkindid}"})
	TbCustomerKind getOne(int icustomerkindid);
	

	
	

}
