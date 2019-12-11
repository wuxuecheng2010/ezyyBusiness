package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbStorePositionChangeS;

@Repository
@Mapper
public interface TbStorePositionChangeSMapper {
	
	String TABLE_NAME="Tb_StorePositionChangeS";
	
	String SELECT_FIELDS="isid,ibillid,iproductid,vcbatchnumber,iproviderid,numquantity";

	//String INSERT_FIELDS="isid,ibillid,iproductid,vcbatchnumber,iproviderid,numquantity";

	//String INSERT_VALUES="#{isid},#{ibillid},#{iproductid},#{vcbatchnumber},#{iproviderid},#{numquantity}";
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where ibillid=#{ibillid}" })
	List<TbStorePositionChangeS> getListByIbillid(int ibillid);
	
	
	
}
