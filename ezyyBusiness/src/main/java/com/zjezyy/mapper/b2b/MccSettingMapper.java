package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccSetting;

@Repository
@Mapper
public interface MccSettingMapper {
	String SELECT_FIELDS="setting_id,store_id,code,`key`,`value`";
	String TABLE_NAME="mcc_setting";
	
	//根据订单号查订单
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where code=#{code}  and  `key` = #{key}"})
	MccSetting getSettingByKey(String code,String  key);
	

	//根据订单号查订单
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where code=#{code} "})
	List<MccSetting> getSettingListByCode(String code);
	
}
