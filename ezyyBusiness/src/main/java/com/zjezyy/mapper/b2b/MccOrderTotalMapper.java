package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrderTotal;

@Mapper
@Repository
public interface MccOrderTotalMapper {
	
	String SELECT_FIELDS="order_total_id,order_id,code,title,value,sort_order";

	String INSERT_FIELDS="order_id,code,title,value,sort_order";

	String INSERT_VALUES="#{order_id,jdbcType=INTEGER},#{code,jdbcType=VARCHAR},#{title,jdbcType=VARCHAR},#{value,jdbcType=DECIMAL},#{sort_order,jdbcType=INTEGER}";

	String TABLE_NAME="mcc_order_total";
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="order_total_id",keyColumn="order_total_id")
	int insert(MccOrderTotal mccOrderTotal);

}
