package com.zjezyy.mapper.erp;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbMccOrderProduct;

@Repository
@Mapper
public interface TbMccOrderProductMapper {
	String SELECT_FIELDS = "impdtlid,impid,mcc_order_id,mcc_order_product_id,mcc_product_id,\n" + 
			"			erp_iproductid,mcc_name,mcc_model,mcc_quantity,mcc_price,mcc_total,mcc_tax,erp_numsaletaxrate";

	String INSERT_FIELDS = "impdtlid,impid,mcc_order_id,mcc_order_product_id,mcc_product_id,\n" + 
			"			erp_iproductid,mcc_name,mcc_model,mcc_quantity,mcc_price,mcc_total,mcc_tax,erp_numsaletaxrate";

	String INSERT_VALUES = "#{impdtlid},#{impid},#{mcc_order_id},#{mcc_order_product_id},#{mcc_product_id},\n" + 
			"			#{erp_iproductid},#{mcc_name},#{mcc_model},#{mcc_quantity},#{mcc_price},#{mcc_total},#{mcc_tax},#{erp_numsaletaxrate}";

	String TABLE_NAME = "tb_mcc_order_product";
	
	@SelectKey(statement="select s_tb_mcc_order_product.nextval from dual", keyProperty="impdtlid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbMccOrderProduct tbMccOrderProduct);
	
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where impid=#{impid}"})
	List<TbMccOrderProduct> getListByImpid(int impid);
	
	@Select({"Select distinct erp_numsaletaxrate"," from ",TABLE_NAME," where impid=#{impid}"})
	List<BigDecimal> getSaleTaxRateList(int impid);

}