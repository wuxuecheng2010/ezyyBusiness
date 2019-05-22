package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.b2b.MccTbCustomerKind;
@Repository
@Mapper
public interface MccTbCustomerKindMapper {
	
	String SELECT_FIELDS="id,icustomerkindid,vccustomerkindname";

	String INSERT_FIELDS="icustomerkindid,vccustomerkindname";

	String INSERT_VALUES="#{icustomerkindid},#{vccustomerkindname}";

	String TABLE_NAME="mcc_tb_customerkind";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerkindid = #{icustomerkindid}"})
	MccTbCustomerKind getOne(int icustomerkindid);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME})
	List<MccTbCustomerKind> getAllList();
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	int insert(MccTbCustomerKind mccTbCustomerKind);
	
}
