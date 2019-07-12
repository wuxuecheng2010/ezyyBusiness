package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.SysParameters;
@Repository
@Mapper
public interface SysParametersMapper {

	String SELECT_FIELDS="isysparametersid,vcparameter,vcvalue,vcmemo";
	
    String TABLE_NAME="sys_parameters";
    
	@Select({ "select ", SELECT_FIELDS ," from ",TABLE_NAME," where vcvalue=#{vcvalue}" })
	SysParameters getOne(String  vcvalue);

	
}
