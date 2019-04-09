package com.zjezyy.mapper.erp;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;
@Repository
@Mapper
public interface SystemMapper {
	
	/**
	 * 调用存储过程，获取ERP单号
	 * @param params={v_prefix 单据前缀}
	 * @return params={v_billcode 单据号}
	 */
	@Select({ "call p_getbillno(#{v_prefix,mode=IN,jdbcType=VARCHAR},",
			 "#{v_billcode,mode=OUT,jdbcType=VARCHAR})" })
	@Options(statementType=StatementType.CALLABLE)
	void getERPBillcode(Map<String,String> params);

	
	
}