package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProductDescription;

@Repository
@Mapper
public interface MccProductDescriptionMapper {
	
	String SELECT_FIELDS="product_id,name,meta_title,meta_keyword,meta_description,language_id,description,tag";

	String INSERT_FIELDS="product_id,name,meta_title,meta_keyword,meta_description,language_id,description,tag";

	String INSERT_VALUES="#{product_id,jdbcType=INTEGER},#{name,jdbcType=VARCHAR},#{meta_title,jdbcType=VARCHAR},#{meta_keyword,jdbcType=VARCHAR},#{meta_description,jdbcType=VARCHAR},#{language_id,jdbcType=INTEGER},#{description,jdbcType=VARCHAR},#{tag,jdbcType=VARCHAR}";
	
	String TABLE_NAME=" mcc_product_description ";
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert( MccProductDescription  mccProductDescription);


}
