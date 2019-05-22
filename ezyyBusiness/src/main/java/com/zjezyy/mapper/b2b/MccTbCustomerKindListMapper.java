package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.b2b.MccTbCustomerKind;
import com.zjezyy.entity.b2b.MccTbCustomerKindList;
@Repository
@Mapper
public interface MccTbCustomerKindListMapper {
	
	String SELECT_FIELDS="id,isid,icustomerkindid,icustomerid,dtend";

	String INSERT_FIELDS="isid,icustomerkindid,icustomerid,dtend";

	String INSERT_VALUES="#{isid,jdbcType=INTEGER},#{icustomerkindid,jdbcType=INTEGER},#{icustomerid,jdbcType=INTEGER},#{dtend,jdbcType=DATE}";




	String TABLE_NAME="mcc_tb_customerkindlist";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerid = #{icustomerid}"})
	MccTbCustomerKindList getOne(int icustomerid);
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	int insert(MccTbCustomerKindList mccTbCustomerKindList);
	
}
