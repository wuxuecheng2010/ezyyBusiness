package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccProductToLayout;
import com.zjezyy.entity.b2b.MccProductToStore;

@Mapper
@Repository
public interface MccProductToLayoutMapper {
	
	String SELECT_FIELDS="product_id,store_id,layout_id";

	String INSERT_FIELDS="product_id,store_id,layout_id";

	String INSERT_VALUES="#{product_id,jdbcType=INTEGER},#{store_id,jdbcType=INTEGER},#{layout_id,jdbcType=INTEGER}";
	
	String TABLE_NAME=" mcc_product_to_layout ";
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert( MccProductToLayout  mccProductToLayout);

}
