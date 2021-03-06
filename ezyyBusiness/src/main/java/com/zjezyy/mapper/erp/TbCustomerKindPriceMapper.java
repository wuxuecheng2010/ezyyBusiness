package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbCustomerKindPrice;
@Repository
@Mapper
public interface TbCustomerKindPriceMapper {
	
	String SELECT_FIELDS="isid,icustomerkindid,iproductid,flagrate,numprice,numlowprice,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,numassesscost,numguidprice";

	String TABLE_NAME="tb_customerkindprice";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where iproductid = #{iproductid} and icustomerkindid=#{icustomerkindid}"})
	TbCustomerKindPrice getOne(int iproductid,int icustomerkindid);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerkindid = #{icustomerkindid} "})
	List<TbCustomerKindPrice> getListByKindID(int icustomerkindid);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where isid = #{isid} "})
	TbCustomerKindPrice getOneByISID(int isid);
	
	
	@Select({"select b.* from tb_customer a left join tb_customerkindprice b " , 
			" on a.icustomerkindid = b.icustomerkindid where a.icustomerid=#{icustomerid} and b.iproductid=#{iproductid}  "})
	TbCustomerKindPrice getOneByKindIDAndIcustomerID(int iproductid,int icustomerid);
	
}