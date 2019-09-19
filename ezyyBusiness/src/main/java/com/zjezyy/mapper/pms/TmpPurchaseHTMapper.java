package com.zjezyy.mapper.pms;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.pms.TmpPurchaseHT;

@Mapper
@Repository
public interface TmpPurchaseHTMapper {
	
	String SELECT_FIELDS="isid,ibillid,vcbillno,dtbilldate,vcperiod,vcmemo,creationdate,createdby,lastupdatedate,lastupdatedby,flagapp,appuser,nummoneyall,iproviderid,appdate,idepartid,itype,ierpid,dtinsert,ocflag";

	//String INSERT_FIELDS="isid,ibillid,vcbillno,dtbilldate,vcperiod,vcmemo,creationdate,createdby,lastupdatedate,lastupdatedby,flagapp,appuser,nummoneyall,iproviderid,appdate,idepartid,itype,ierpid,dtinsert,ocflag";

	//String INSERT_VALUES="#{isid},#{ibillid},#{vcbillno},#{dtbilldate},#{vcperiod},#{vcmemo},#{creationdate},#{createdby},#{lastupdatedate},#{lastupdatedby},#{flagapp},#{appuser},#{nummoneyall},#{iproviderid},#{appdate},#{idepartid},#{itype},#{ierpid},#{dtinsert},#{ocflag}";
	
	String TABLE_NAME="tmp_purchaseht";

	//根据订单号查订单 select * from tmp_PurchaseHT where  isnull(ocflag,0)=0 and dtinsert<getdate()-1/(24*60)
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where isnull(ocflag,0)=0 and dtinsert<getdate()-1/(24*60)"})
	List<TmpPurchaseHT> getListNotSendToMcc();
	
	@Update({" update ",TABLE_NAME," set ocflag=#{ocflag} where vcbillno =#{vcbillno} "})
	int updateOcFlag(String vcbillno,int ocflag);
	
}
