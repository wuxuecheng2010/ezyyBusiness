package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbProductPacking;
@Repository
@Mapper
public interface TbProductPackingMapper {
	
	String SELECT_FIELDS="isid,iproductid,iunitid,flagusing,numlarge,nummiddle,numsmall";
	
    String TABLE_NAME="tb_product_packing";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where iproductid = #{iproductid} and nvl(flagusing,'N')='Y'"})
	TbProductPacking getOne(int iproductid);
	

	
	

}
