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
	String SELECT_FIELDS = "impdtlid,impid,mcc_order_id,mcc_order_product_id,mcc_product_id, " + 
			"			erp_iproductid,mcc_name,mcc_model,mcc_quantity,mcc_price,mcc_total,mcc_tax,erp_numsaletaxrate,erp_numpurchasetaxrate,erp_flagcold, erp_flagfreezing, erp_flagephedrine, erp_flagspecial ";

	String INSERT_FIELDS = "impdtlid,impid,mcc_order_id,mcc_order_product_id,mcc_product_id, " + 
			"			erp_iproductid,mcc_name,mcc_model,mcc_quantity,mcc_price,mcc_total,mcc_tax,erp_numsaletaxrate,erp_numpurchasetaxrate,erp_flagcold, erp_flagfreezing, erp_flagephedrine, erp_flagspecial";

	String INSERT_VALUES = "#{impdtlid ,jdbcType=INTEGER},#{impid ,jdbcType=INTEGER},#{mcc_order_id,jdbcType=INTEGER},#{mcc_order_product_id,jdbcType=INTEGER},#{mcc_product_id,jdbcType=INTEGER},  " + 
			"			#{erp_iproductid,jdbcType=INTEGER},#{mcc_name,jdbcType=VARCHAR},#{mcc_model,jdbcType=VARCHAR},#{mcc_quantity,jdbcType=NUMERIC},#{mcc_price,jdbcType=NUMERIC},#{mcc_total,jdbcType=NUMERIC},#{mcc_tax,jdbcType=NUMERIC},#{erp_numsaletaxrate,jdbcType=NUMERIC},#{erp_numpurchasetaxrate,jdbcType=NUMERIC},#{erp_flagcold,jdbcType=CHAR}, #{erp_flagfreezing,jdbcType=CHAR}, #{erp_flagephedrine,jdbcType=CHAR}, #{erp_flagspecial,jdbcType=CHAR}";

	String TABLE_NAME = "tb_mcc_order_product";
	
	@SelectKey(statement="select s_tb_mcc_order_product.nextval from dual", keyProperty="impdtlid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbMccOrderProduct tbMccOrderProduct);
	
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where impid=#{impid}"})
	List<TbMccOrderProduct> getListByImpid(int impid);
	
	@Select({"Select distinct erp_numsaletaxrate"," from ",TABLE_NAME," where impid=#{impid}"})
	List<BigDecimal> getSaleTaxRateList(int impid);
	
	@Select({"Select distinct erp_numsaletaxrate||'-'||erp_flagcold||'-'||erp_flagfreezing||'-'|| erp_flagephedrine||'-'|| erp_flagspecial "," from ",TABLE_NAME," where impid=#{impid}"})
	List<String> getSaleTypeList(int impid);

}