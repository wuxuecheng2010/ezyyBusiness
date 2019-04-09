package com.zjezyy.mapper.wms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.TmsTrDoc;

@Repository
@Mapper
public interface TmsTrDocMapper {
	String SELECT_FIELDS="trid,warehid,credate,operationtype";
	String TABLE_NAME="Tms_Tr_Doc";
	 @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where trid = #{trid}"})
	TmsTrDoc getOne(int trid);
}