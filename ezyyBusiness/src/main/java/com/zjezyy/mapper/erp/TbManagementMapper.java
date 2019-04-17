package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbManagement;
@Repository
@Mapper
public interface TbManagementMapper {
	
	String SELECT_FIELDS="imanagementid,vcmanagementname,flagzzrs,flagephedrine,flagspecial,flagnotcash";

	
	String TABLE_NAME="tb_management";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where imanagementid = #{imanagementid}"})
	TbManagement getOne(int imanagementid);
	
}