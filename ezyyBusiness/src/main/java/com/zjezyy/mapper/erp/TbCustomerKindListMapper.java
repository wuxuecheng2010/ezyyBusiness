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
	
	/*@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerid = #{icustomerid} and icustomerkindid in (select icustomerkindid from tb_customerkind where  nvl(flagstop,'N')='N')"})
	TbCustomerKindList getOne(int icustomerid);*/
    
    
    
	@Select({"select * from tb_customerkindlist a where a.icustomerid = #{icustomerid}" , 
			"   and exists(" ,
			"   select 1 from tb_customerkind b where a.icustomerkindid=icustomerkindid and   nvl(b.flagstop,'N')='N'" , 
			"   )"})
	TbCustomerKindList getOne(int icustomerid);

	
	

}
