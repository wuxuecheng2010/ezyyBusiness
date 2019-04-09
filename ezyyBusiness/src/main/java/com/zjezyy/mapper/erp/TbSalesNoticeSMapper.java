package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbSalesNoticeS;
@Repository
@Mapper
public interface TbSalesNoticeSMapper {
	
	String SELECT_FIELDS="isid,ibillid,numqueue,isourceid,iproductid,ilinkmanid,iunitid,ibatchid,numprice,numquantity,numapplications,numsaletaxrate,numinprice,numpurchasetaxrate,numlarge,ipackingid,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,istorepositionid,vcbatchnumber,vcconfirmfile,vcdisinfection,dtdisinfection,istockid,iproviderid,dtmanufacture,dtusefullife,numguideprice,numlastprice,numlowprice,numcountryprice,numassesscost,numwriteoff,numreturn,numpiece,numrealout";

	String INSERT_FIELDS="isid,ibillid,numqueue,isourceid,iproductid,ilinkmanid,iunitid,ibatchid,numprice,numquantity,numapplications,numsaletaxrate,numinprice,numpurchasetaxrate,numlarge,ipackingid,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,istorepositionid,vcbatchnumber,vcconfirmfile,vcdisinfection,dtdisinfection,istockid,iproviderid,dtmanufacture,dtusefullife,numguideprice,numlastprice,numlowprice,numcountryprice,numassesscost,numwriteoff,numreturn,numpiece,numrealout";

	String INSERT_VALUES="#{isid,jdbcType=INTEGER},#{ibillid,jdbcType=INTEGER},#{numqueue,jdbcType=REAL},#{isourceid,jdbcType=INTEGER},#{iproductid,jdbcType=INTEGER},#{ilinkmanid,jdbcType=INTEGER},#{iunitid,jdbcType=INTEGER},#{ibatchid,jdbcType=INTEGER},#{numprice,jdbcType=NUMERIC},#{numquantity,jdbcType=NUMERIC},#{numapplications,jdbcType=NUMERIC},#{numsaletaxrate,jdbcType=NUMERIC},#{numinprice,jdbcType=NUMERIC},#{numpurchasetaxrate,jdbcType=NUMERIC},#{numlarge,jdbcType=NUMERIC},#{ipackingid,jdbcType=INTEGER},#{vcmemo,jdbcType=VARCHAR},sysdate,#{vccreatedby,jdbcType=VARCHAR},sysdate,#{vclastupdatedby,jdbcType=VARCHAR},#{istorepositionid,jdbcType=INTEGER},#{vcbatchnumber,jdbcType=VARCHAR},#{vcconfirmfile,jdbcType=VARCHAR},#{vcdisinfection,jdbcType=VARCHAR},to_date(#{dtdisinfection,jdbcType=VARCHAR},'yyyy-mm-dd'),#{istockid,jdbcType=INTEGER},#{iproviderid,jdbcType=INTEGER},to_date(#{dtmanufacture,jdbcType=VARCHAR},'yyyy-mm-dd'),to_date(#{dtusefullife,jdbcType=VARCHAR},'yyyy-mm-dd'),#{numguideprice,jdbcType=NUMERIC},#{numlastprice,jdbcType=NUMERIC},#{numlowprice,jdbcType=NUMERIC},#{numcountryprice,jdbcType=NUMERIC},#{numassesscost,jdbcType=NUMERIC},#{numwriteoff,jdbcType=NUMERIC},#{numreturn,jdbcType=NUMERIC},#{numpiece,jdbcType=NUMERIC},#{numrealout,jdbcType=NUMERIC}";


	String TABLE_NAME="tb_salesnotices";
	
	@SelectKey(statement="select s_tb_salesnotices.nextval from dual", keyProperty="isid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbSalesNoticeS tbSalesNoticeS);
	
	@Select({" select nvl(max(t2.isid), 0) lastid from tb_salesnotices t2,tb_salesnotice t1 " , 
			" where t2.ibillid = t1.ibillid and t2.iproductid = #{iproductid} ", 
			" and t1.icustomerid = #{icustomerid} and nvl(t1.flagapp,'N')='Y'"})
	int getLastId(int iproductid,int icustomerid);
	
	@Select({"select t1.dtcreationdate, t2.numprice from  tb_salesnotices t2,tb_salesnotice t1" , 
			"where  t2.ibillid = t1.ibillid and t2.isid = #{isid} "})
	TbSalesNoticeS getOne(int isid);
	
}