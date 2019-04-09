package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbStockControl;
@Repository
@Mapper
public interface TbStockControlMapper {
	
	String SELECT_FIELDS=" iproductid,numlow,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,numopen ";
	String TABLE_NAME="tb_stockcontrol";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where iproductid = #{iproductid}"})
	TbStockControl getOne(int iproductid);
	
}