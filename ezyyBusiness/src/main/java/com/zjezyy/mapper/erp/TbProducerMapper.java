package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbProducer;
@Repository
@Mapper
public interface TbProducerMapper {
	
	String SELECT_FIELDS="iproducerid,vcproducername,vceasycode";
	
    String TABLE_NAME="tb_producer";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where iproducerid = #{iproducerid}"})
	TbProducer getOne(int iproducerid);
	

}
