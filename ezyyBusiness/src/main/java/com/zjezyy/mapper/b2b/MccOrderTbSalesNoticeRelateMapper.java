package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrderTbSalesNoticeRelate;

@Repository
@Mapper
public interface MccOrderTbSalesNoticeRelateMapper {
	String SELECT_FIELDS="id,order_id,fullname,ordercode,payment_method,payment_code,total_fee,salesnotice_ibillid,salesnotice_vcbillcode,salesorder_ibillid,salesorder_vcbillcode,salesnotice_nummoney,flagpay";

	String INSERT_FIELDS="order_id,fullname,ordercode,payment_method,payment_code,total_fee,salesnotice_ibillid,salesnotice_vcbillcode,salesorder_ibillid,salesorder_vcbillcode,salesnotice_nummoney,flagpay";

	String INSERT_VALUES="#{order_id,jdbcType=INTEGER},#{fullname,jdbcType=VARCHAR},#{ordercode,jdbcType=VARCHAR},#{payment_method,jdbcType=VARCHAR},#{payment_code,jdbcType=VARCHAR},#{total_fee,jdbcType=DECIMAL},#{salesnotice_ibillid,jdbcType=INTEGER},#{salesnotice_vcbillcode,jdbcType=VARCHAR},#{salesorder_ibillid,jdbcType=INTEGER},#{salesorder_vcbillcode,jdbcType=VARCHAR},#{salesnotice_nummoney,jdbcType=DECIMAL},#{flagpay,jdbcType=INTEGER}";

	String TABLE_NAME="mcc_order_tb_salesnotice_relate";
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	int insert(MccOrderTbSalesNoticeRelate mccOrderTbSalesNoticeRelate);
	
	@Update({"update ",TABLE_NAME," set flagpay=1 where salesnotice_ibillid=#{salesnotice_ibillid}"})
	int update(int salesnotice_ibillid);

}
