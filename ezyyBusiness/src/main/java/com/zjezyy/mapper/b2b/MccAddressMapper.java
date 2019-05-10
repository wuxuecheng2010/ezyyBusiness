package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccAddress;
@Repository
@Mapper
public interface MccAddressMapper {
	
	String SELECT_FIELDS="address_id,customer_id,fullname,address,company,city,postcode,country_id,zone_id,city_id,district_id,shipping_telephone,custom_field";

	String INSERT_FIELDS="customer_id,fullname,address,company,city,postcode,country_id,zone_id,city_id,district_id,shipping_telephone,custom_field";

	String INSERT_VALUES="#{customer_id,jdbcType=INTEGER},#{fullname,jdbcType=VARCHAR},#{address,jdbcType=VARCHAR},#{company,jdbcType=VARCHAR},#{city,jdbcType=VARCHAR},#{postcode,jdbcType=VARCHAR},#{country_id,jdbcType=INTEGER},#{zone_id,jdbcType=INTEGER},#{city_id,jdbcType=INTEGER},#{district_id,jdbcType=INTEGER},#{shipping_telephone,jdbcType=VARCHAR},#{custom_field,jdbcType=VARCHAR}";


	String TABLE_NAME="mcc_address";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where address_id = #{address_id}"})
	MccAddress getOne(int address_id);
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="address_id",keyColumn="address_id")
	int insert(MccAddress mccAddress);
	
}
