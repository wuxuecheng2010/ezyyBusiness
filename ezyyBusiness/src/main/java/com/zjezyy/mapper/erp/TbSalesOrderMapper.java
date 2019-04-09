package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbSalesOrder;
@Repository
@Mapper
public interface TbSalesOrderMapper {
	
	String SELECT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,nummoney,flagstop,numprintcount,flagperform,flagurgent,vcmemo,vcaddress,flagapp,dtcreationdate,vccreatedby,icustomeraidid,ifb";

	String INSERT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,nummoney,flagstop,numprintcount,flagperform,flagurgent,vcmemo,vcaddress,flagapp,dtcreationdate,vccreatedby,ifb";

	String INSERT_VALUES="#{ibillid},#{vcbillcode},#{isourceid},#{itypeid},#{icustomerid},#{nummoney},#{flagstop},#{numprintcount},#{flagperform},#{flagurgent},#{vcmemo},#{vcaddress},#{flagapp},sysdate,#{vccreatedby},#{ifb}";


	String TABLE_NAME="tb_salesorder";
	
	@SelectKey(statement="select s_tb_salesorder.nextval from dual", keyProperty="ibillid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbSalesOrder tbSalesOrder);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ibillid = #{ibillid}"})
	TbSalesOrder getOne(int ibillid);
	
}