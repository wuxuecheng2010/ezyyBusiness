package com.zjezyy.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbSalesNotice;
@Repository
@Mapper
public interface TbSalesNoticeMapper {
	
	String SELECT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,vcaddress,isendtype,flagurgent,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,dtsenddate,vcddbh,icustomeraidid,nummoney,isendtype,dtsenddate,flagapp,flagtowms";

	String INSERT_FIELDS="ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,vcaddress,isendtype,flagurgent,vcmemo,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,nummoney,dtsenddate";

	String INSERT_VALUES="#{ibillid,jdbcType=INTEGER},#{vcbillcode,jdbcType=VARCHAR},#{isourceid,jdbcType=INTEGER},#{itypeid,jdbcType=INTEGER},#{icustomerid,jdbcType=INTEGER},#{isalerid,jdbcType=NUMERIC},#{igathermode,jdbcType=INTEGER},#{vcaddress,jdbcType=VARCHAR},#{isendtype,jdbcType=INTEGER},#{flagurgent,jdbcType=CHAR},#{vcmemo,jdbcType=VARCHAR},sysdate,#{vccreatedby,jdbcType=VARCHAR},sysdate,#{vclastupdatedby,jdbcType=VARCHAR},#{nummoney,jdbcType=NUMERIC},sysdate+1";

	String TABLE_NAME="tb_salesnotice";
	
	@SelectKey(statement="select s_tb_salesnotice.nextval from dual", keyProperty="ibillid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbSalesNotice tbSalesNotice);
	
	
	@Update({"update  tb_salesnotice a set flagapp='Y'  where exists(select 1 from tb_salesorder b where a.isourceid=b.ibillid and  b.isourceid=#{impid} and b.itypeid=#{itypeid})"})
	int cancelTbSalesNotice(int impid,int itypeid);
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where ibillid=#{ibillid}"})
	TbSalesNotice getOne(int ibillid);
	
	//@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where isourceid=#{isourceid}"})
	//TbSalesNotice getOneBySourceID(int isourceid);
	
	@Select({"Select ",SELECT_FIELDS," from ",TABLE_NAME," where isourceid=#{isourceid}"})
	List<TbSalesNotice> getListBySourceID(int isourceid);
	
	@Select({"select ",SELECT_FIELDS," from tb_salesnotice where isourceid in (" , 
			"select ibillid from tb_salesorder where itypeid=#{itypeid} and   isourceid in (" , 
			" select impid from tb_mcc_order where mcc_order_id=#{mcc_order_id}))"})
	List<TbSalesNotice> getListByMccOrderID(int mcc_order_id,int itypeid);
	
	/**
	 * 调用存储过程，审核销售开票
	 * @param params={v_prefix 单据前缀}
	 * @return params={v_billcode 单据号}
	 */
	@Select({ "call App_SalesNotice(",
		     "#{DocNo_in,mode=IN,jdbcType=INTEGER},",
			 "#{AppUser_in,mode=IN,jdbcType=VARCHAR},",
			 "#{iResult_out,mode=OUT,jdbcType=INTEGER},",
			 "#{ErrMsg_Out,mode=OUT,jdbcType=VARCHAR}",
			  ")" })
	@Options(statementType=StatementType.CALLABLE)
	void approval(Map<String,Object> params);


	@Insert({"insert into tb_salesnotice_his b(ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,nummoney,numprintcount,flagapply,dtapply,", 
			"vcaddress,isendtype,flagnext,dtnext,flagurgent,vcmemo,flagapp,vcappuser,dtappdate,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,dtsenddate,flagpickover," , 
			"flagrecorded,dtrecorded,vcreccorded,flagtowms,icustomeraidid,flagpause,vcrepauser,dtrepause,flagcontrol,vcddbh,vcddname,vcaddresscode,hismemo)" , 
			"select " ,
			" ibillid,vcbillcode,isourceid,itypeid,icustomerid,isalerid,igathermode,nummoney,numprintcount,flagapply,dtapply,", 
			"vcaddress,isendtype,flagnext,dtnext,flagurgent,vcmemo,flagapp,vcappuser,dtappdate,dtcreationdate,vccreatedby,dtlastupdatedate,vclastupdatedby,dtsenddate,flagpickover," , 
			"flagrecorded,dtrecorded,vcreccorded,flagtowms,icustomeraidid,flagpause,vcrepauser,dtrepause,flagcontrol,vcddbh,vcddname,vcaddresscode,#{hismemo}" , 
			"from tb_salesnotice a where ibillid=#{ibillid}"})
	int insertTbSalesNoticeHis(int ibillid,String hismemo);

	@Insert({"insert into tb_salesnotices_his(" , 
			"isid,ibillid,numqueue,isourceid,iproductid,ilinkmanid,iunitid,ibatchid,numprice,numquantity,numwriteoff," , 
			"numreturn,numsaletaxrate,numinprice,numpurchasetaxrate,numassesscost,numlarge,numpiece,flagcantreturn,ipackingid," , 
			"vcmemo,dtcreationdate,vccreatedby,dtappdate,vcappuser,dtlastupdatedate,vclastupdatedby,istorepositionid," , 
			"vcbatchnumber,vcconfirmfile,vcdisinfection,dtdisinfection,istockid,iproviderid,dtmanufacture,dtusefullife," , 
			"numrealout,numguideprice,numlastprice,numlowprice,numcountryprice,flagpickover,flagfollow,numapplications," , 
			"numdelivery,ddmxbh,flagbargaining,nummle)" , 
			"select isid,ibillid,numqueue,isourceid,iproductid,ilinkmanid,iunitid,ibatchid,numprice,numquantity,numwriteoff," , 
			"numreturn,numsaletaxrate,numinprice,numpurchasetaxrate,numassesscost,numlarge,numpiece,flagcantreturn,ipackingid," , 
			"vcmemo,dtcreationdate,vccreatedby,dtappdate,vcappuser,dtlastupdatedate,vclastupdatedby,istorepositionid," , 
			"vcbatchnumber,vcconfirmfile,vcdisinfection,dtdisinfection,istockid,iproviderid,dtmanufacture,dtusefullife," , 
			"numrealout,numguideprice,numlastprice,numlowprice,numcountryprice,flagpickover,flagfollow,numapplications," , 
			"numdelivery,ddmxbh,flagbargaining,nummle from tb_salesnotices where ibillid=#{ibillid}"})
	int insertTbSalesNoticesHis(int ibillid);

    @Delete({"delete from tb_salesnotices where ibillid=#{ibillid}"})
	int deleteTbSalesNotices(int ibillid);

    @Delete({"delete from tb_salesnotice where ibillid=#{ibillid}"})
	int deleteTbSalesNotice(int ibillid);
	
}