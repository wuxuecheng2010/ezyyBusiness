package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbCustomer;
@Repository
@Mapper
public interface TbCustomerMapper {
	
	//String SELECT_FIELDS="icustomerid,vccustomername,isalerid,vcregisteredaddress,vcaddress,vcstoreaddress";
	String SELECT_FIELDS="icustomerid,vccustomername,vclinkman,vclinkphone,vczipcode,isalerid,vcregisteredaddress,vcaddress,vcstoreaddress,flaglock,numcreditdays,numcreditdays1,numcreditdays2,numcreditdays3,numcreditmoney,vccustomercode,icredittype";

	
	String TABLE_NAME="tb_customer";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerid = #{icustomerid}"})
	TbCustomer getOne(int icustomerid);
	
}