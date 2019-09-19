package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccOrderProduct;

@Repository
@Mapper
public interface MccOrderProductMapper {
	String SELECT_FIELDS="order_product_id,order_id,product_id,name,model,quantity,price,total,tax";
	String TABLE_NAME="mcc_order_product";
	
	//根据订单号查订单
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where order_product_id = #{order_product_id}"})
	MccOrderProduct getOne(int order_product_id);
	
	//根据订单号查明细
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where order_id = #{order_id}"})
	List<MccOrderProduct> getListByOrderId(int order_id);
	
	
	String insql="INSERT INTO mcc_order_product SET order_id = #{order_id,jdbcType=INTEGER}, product_id = #{product_id,jdbcType=INTEGER}, name = #{name,jdbcType=VARCHAR}, model = #{model,jdbcType=VARCHAR}, quantity = #{quantity,jdbcType=DECIMAL}, price = #{price,jdbcType=DECIMAL}, total = #{total,jdbcType=DECIMAL}, tax = '0', reward = '0'";
	@Insert({insql})
	int insert(MccOrderProduct mccOrderProduct);
	
}
