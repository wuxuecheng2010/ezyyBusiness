package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbStorePositionChange;

@Repository
@Mapper
public interface TbStorePositionChangeMapper {
	String TABLE_NAME="Tb_StorePositionChange";
	
	String SELECT_FIELDS="ibillid,vcbillcode,vcmemo,dtcreationdate,vccreatedby,nummoney";

	//String INSERT_FIELDS="ibillid,vcbillcode,vcmemo,dtcreationdate,vccreatedby,nummoney";

	//String INSERT_VALUES="#{ibillid},#{vcbillcode},#{vcmemo},#{dtcreationdate},#{vccreatedby},#{nummoney}";
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where nvl(flagapp,'N')='N' and dtcreationdate>sysdate-35 and dtcreationdate<=sysdate-25" })
	List<TbStorePositionChange> getListTimerout();
	
	
	
}
