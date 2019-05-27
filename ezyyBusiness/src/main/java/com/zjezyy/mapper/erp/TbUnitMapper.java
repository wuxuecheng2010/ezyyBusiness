package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbUnit;
@Repository
@Mapper
public interface TbUnitMapper {
	
	String SELECT_FIELDS="iunitid,vcunitname,vceasycode";
	
    String TABLE_NAME="tb_unit";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where iunitid = #{iunitid}"})
	TbUnit getOne(int iunitid);
	

}
