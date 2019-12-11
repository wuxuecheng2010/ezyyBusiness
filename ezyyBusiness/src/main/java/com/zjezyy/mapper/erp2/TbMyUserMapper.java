package com.zjezyy.mapper.erp2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp2.TbMyUser;
@Repository
@Mapper
public interface TbMyUserMapper {
	
	String SELECT_FIELDS="isid,vcaccount,vcusername,vctel";
	
    String TABLE_NAME="tb_myuser";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where vcaccount = #{vcaccount}"})
	TbMyUser getOne(String  vcaccount);
	

}
