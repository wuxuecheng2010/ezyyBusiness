package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrderProductReceiveDtl;

@Repository
@Mapper
public interface MccOrderProductReceiveDtlMapper {
	String SELECT_FIELDS="id,isid,ibillid,order_id,order_product_id,erpiproductid,quantity,numapplications,numrealout,numprice,vcbatchnumber,fbcredate,fbupdate,realtotal";

	String INSERT_FIELDS="isid,ibillid,order_id,order_product_id,erpiproductid,quantity,numapplications,numrealout,numprice,vcbatchnumber";

	String INSERT_VALUES="#{isid},#{ibillid},#{order_id},#{order_product_id},#{erpiproductid},#{quantity},#{numapplications},#{numrealout},#{numprice},#{vcbatchnumber}";


	String TABLE_NAME=" mcc_order_product_receive_dtl ";
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(MccOrderProductReceiveDtl mccOrderProductReceiveDtl);
	
	
}
