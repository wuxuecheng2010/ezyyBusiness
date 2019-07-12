package com.zjezyy.mapper.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.zjezyy.entity.b2b.MccTbSalesNotice;

public interface MccTbSalesNoticeMapper {
	
	String SELECT_FIELDS="mcc_salsenotice_id,mcc_order_id,mcc_total,mcc_fullname,salesorder_ibillid,salesorder_vcbillcode,salesnotice_ibillid,salesnotice_vcbillcode,salesnotice_nummoney";

	String INSERT_FIELDS="mcc_order_id,mcc_total,mcc_fullname,salesorder_ibillid,salesorder_vcbillcode,salesnotice_ibillid,salesnotice_vcbillcode,salesnotice_nummoney";

	String INSERT_VALUES="#{mcc_order_id ,jdbcType=INTEGER},#{mcc_total,jdbcType=DECIMAL},#{mcc_fullname,jdbcType=VARCHAR},#{salesorder_ibillid,jdbcType=INTEGER},#{salesorder_vcbillcode,jdbcType=VARCHAR},#{salesnotice_ibillid,jdbcType=INTEGER},#{salesnotice_vcbillcode,jdbcType=VARCHAR},#{salesnotice_nummoney,jdbcType=DECIMAL}";


	String TABLE_NAME="mcc_tb_salesnotice";

	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME, " where mcc_order_id=#{mcc_order_id}"})
	List<MccTbSalesNotice> getListByMccOrderID(int mcc_order_id);

	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="mcc_salsenotice_id",keyColumn="mcc_salsenotice_id")
	int insert(MccTbSalesNotice mccTbSalesNotice);

}
