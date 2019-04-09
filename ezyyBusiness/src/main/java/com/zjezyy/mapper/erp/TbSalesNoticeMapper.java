package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbSalesNotice;
@Repository
@Mapper
public interface TbSalesNoticeMapper {
	
	String SELECT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,vcaddress,isendtype,flagurgent,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,dtsenddate,vcddbh,icustomeraidid,nummoney,isendtype,dtsenddate";

	String INSERT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,vcaddress,isendtype,flagurgent,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,nummoney,dtsenddate";

	String INSERT_VALUES="#{ibillid,jdbcType=INTEGER},#{vcbillcode,jdbcType=VARCHAR},#{isourceid,jdbcType=INTEGER},#{itypeid,jdbcType=INTEGER},#{icustomerid,jdbcType=INTEGER},#{isalerid,jdbcType=NUMERIC},#{igathermode,jdbcType=INTEGER},#{vcaddress,jdbcType=VARCHAR},#{isendtype,jdbcType=INTEGER},#{flagurgent,jdbcType=CHAR},#{vcmemo,jdbcType=VARCHAR},sysdate,#{vccreatedby,jdbcType=VARCHAR},sysdate,#{vclastupdatedby,jdbcType=VARCHAR},#{nummoney,jdbcType=NUMERIC},sysdate+1";

	String TABLE_NAME="tb_salesnotice";
	
	@SelectKey(statement="select s_tb_salesnotice.nextval from dual", keyProperty="ibillid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbSalesNotice tbSalesNotice);
	
	
	@Update({"update  tb_salesnotice a set flagapp='Y'  where exists(select 1 from tb_salesorder b where a.isourceid=b.ibillid and  b.isourceid=#{impid} and b.itypeid=#{itypeid})"})
	int cancelTbSalesNotice(int impid,int itypeid);
	
	
	
}