package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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
	
	//销售订单->销售开票之后对销售订单的处理
	@Update({"update tb_salesorder t set t.FLAGPERFORM = 'Y',t.VCAPPUSER = #{vcappuser},FLAGAPP = 'Y' where ibillid =#{ibillid}"})
	int update(int ibillid,String vcappuser);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where isourceid = #{impid} and itypeid=#{itypeid}"})
	List<TbSalesOrder> getListByImpidAndTypeID(int impid,int itypeid);
	
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where itypeid = #{itypeid} and ifb=#{ifb} and flagperform='Y' and dtcreationdate>sysdate-#{days}"})
	List<TbSalesOrder> getPerformListByTypeIDAndIFBAndDays(int itypeid,int ifb,int days);
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where isourceid=#{isourceid} and itypeid=#{itypeid} and dtcreationdate>sysdate-90"})
	List<TbSalesOrder> getListBySourceID(int isourceid,int itypeid);
	
	
	@Update({"update tb_salesorder t set t.ifb = #{ifb} where ibillid =#{ibillid}"})
	int updateIFBState(int ibillid,int ifb);
	
}