package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccDistrict;
@Repository
@Mapper
public interface MccDistrictMapper {
	
	String SELECT_FIELDS="district_id,country_id,zone_id,city_id,name,status";

	String TABLE_NAME="mcc_district";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name = #{name} "})
	MccDistrict getOnebyName(String name);
	
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where  left(name,CHAR_LENGTH(name)-1)=#{shortname}"})
	MccDistrict getOnebyShortName(String shortname);
	
	
}
