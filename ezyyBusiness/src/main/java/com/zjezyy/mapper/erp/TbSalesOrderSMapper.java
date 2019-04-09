package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbSalesNoticeS;
import com.zjezyy.entity.erp.TbSalesOrderS;
@Repository
@Mapper
public interface TbSalesOrderSMapper {
	

	
	
	
	String SELECT_FIELDS="isid,ibillid,isourceid,iproductid,numqueue,numprice,numquantity,iunitid,vcmemo,dtcreationdate,vccreatedby,vcproductcode,vcuniversalname,vcstandard,vcproductunit,vcproducername,istate";

	String INSERT_FIELDS="isid,ibillid,isourceid,iproductid,numqueue,numprice,numquantity,iunitid,vcmemo,dtcreationdate,vccreatedby,vcproductcode,vcuniversalname,vcstandard,vcproductunit,vcproducername,istate";

	String INSERT_VALUES="#{isid},#{ibillid},#{isourceid},#{iproductid},#{numqueue},#{numprice},#{numquantity},#{iunitid},#{vcmemo,jdbcType=VARCHAR},sysdate,#{vccreatedby},#{vcproductcode},#{vcuniversalname},#{vcstandard},#{vcproductunit},#{vcproducername},#{istate}";

	String TABLE_NAME="tb_salesorders";
	
	@SelectKey(statement="select s_tb_salesorders.nextval from dual", keyProperty="isid", before=true, resultType=Integer.class)
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	int insert(TbSalesOrderS tbSalesOrderS);
	
	
	@Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,"where ibillid=#{ibillid}"})
	List<TbSalesOrderS>  getTbSalesOrderSList(int ibillid);
	
}