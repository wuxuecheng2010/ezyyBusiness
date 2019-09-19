package com.zjezyy.mapper.pms;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.pms.TmpPurchaseHTS;

@Mapper
@Repository
public interface TmpPurchaseHTSMapper {
	
	String SELECT_FIELDS="isid,vcbillno,vcmemo,creationdate,createdby,lastupdatedate,lastupdatedby,iproductid,numnumber,numprice,nummoney,numnumberdh,isourceid,numlow,numhigh,numstock,sumsales,erpproductid";

	//String INSERT_FIELDS="isid,vcbillno,vcmemo,creationdate,createdby,lastupdatedate,lastupdatedby,iproductid,numnumber,numprice,nummoney,numnumberdh,isourceid,numlow,numhigh,numstock,sumsales,erpproductid";

	//String INSERT_VALUES="#{isid},#{vcbillno},#{vcmemo},#{creationdate},#{createdby},#{lastupdatedate},#{lastupdatedby},#{iproductid},#{numnumber},#{numprice},#{nummoney},#{numnumberdh},#{isourceid},#{numlow},#{numhigh},#{numstock},#{sumsales},#{erpproductid}";


	String TABLE_NAME="tmp_purchasehts";

	//根据订单号查订单 select * from tmp_PurchaseHT where  isnull(ocflag,0)=0 and dtinsert<getdate()-1/(24*60)
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where vcbillno=#{vcbillno}"})
	List<TmpPurchaseHTS> getListVcbillno(String vcbillno);
}
