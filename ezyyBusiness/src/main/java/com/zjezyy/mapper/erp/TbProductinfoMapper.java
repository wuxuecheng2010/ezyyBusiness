package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbProductinfo_Eo1;
@Repository
@Mapper
public interface TbProductinfoMapper {
	


	String SELECT_FIELDS="a.iproductid,a.iproductkindid,a.vcproductname,a.vceasycode,a.vcproductcode,a.vcuniversalname,a.istorageoptionid,nvl(a.flagcold,'N') flagcold,nvl(a.flagfreezing,'N')flagfreezing,"
			+ "a.vcstandard,a.iproductunitid,a.iproducerid,a.numsaletaxrate,a.numpurchasetaxrate,a.numcountryprice,a.numwarningdays,a.imanagementid,a.iydstate ";
	String TABLE_NAME="tb_productinfo a";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where a.iproductid = #{iproductid}"})
	TbProductinfo getOne(int iproductid);
	
	@Select({"select ", SELECT_FIELDS, ",numprice,numlowprice,numassesscost,numguidprice,c.vcunitname,d.vcproducername"," from ", TABLE_NAME, 
		     " ,tb_customerkindprice b,tb_unit c,tb_producer d ",
		  " where a.iproductid=b.iproductid(+) "
		  + "and a.iproductunitid=c.iunitid(+)"
		  + "and a.iproducerid=d.iproducerid(+)"
		  + " and a.iproductid = #{iproductid} "
		  + " and b.icustomerkindid=#{icustomerkindid}"
		  })
	TbProductinfo_Eo getOneEo_(int iproductid,int icustomerkindid);
	
	
	//为了填补数据  无所谓取哪一行了 随便取  反正不显示  不能用做计算金额用
	@Select({"select ", SELECT_FIELDS, ",numprice,numlowprice,numassesscost,numguidprice,c.vcunitname,d.vcproducername"," from ", TABLE_NAME, 
	     " ,tb_customerkindprice b,tb_unit c,tb_producer d ",
	  " where a.iproductid=b.iproductid(+) "
	  + "and a.iproductunitid=c.iunitid(+) "
	  + "and a.iproducerid=d.iproducerid(+) "
	  + " and a.iproductid = #{iproductid} "
	  + " and rownum=1 order by b.isid "
	//  + " and b.icustomerkindid=#{icustomerkindid}"
	  })
   TbProductinfo_Eo getOneEoDirty(int iproductid);
	
	
	//不含价格的商品扩展信息
	@Select({"select ", SELECT_FIELDS, ",c.vcunitname,d.vcproducername",
	   " from ", TABLE_NAME, " ,tb_unit c,tb_producer d ",
	   " where a.iproductunitid=c.iunitid(+)","and a.iproducerid=d.iproducerid(+) ",
	   " and a.iproductid = #{iproductid} " })
  TbProductinfo_Eo1 getOneEo1(int iproductid);
	

	//不含价格的商品扩展信息
	@Select({"select ", SELECT_FIELDS, ",c.vcunitname,d.vcproducername",
	   " from ", TABLE_NAME, " ,tb_unit c,tb_producer d ",
	   " where   a.iproductunitid=c.iunitid(+)","and a.iproducerid=d.iproducerid(+) ",
	   " and a.iproductid = #{iproductid} and exists (select 1 from tb_customersetproduct where iproductid=#{iproductid} and icustomersetid=#{icustomersetid} ) " })
  TbProductinfo_Eo1 getOneEo1ByCustomerSet(int iproductid,int icustomersetid);
	
	
	
	
/*	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,",tb_customerkindprice b" , 
			"where a.iproductid=b.iproductid and  b.icustomerkindid=#{icustomerkindid}" , 
			"and nvl(a.iydstate,0)=0" })*/
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,", tb_customersetproduct b" , 
		"where a.iproductid=b.iproductid and  b.icustomersetid=#{icustomersetid}" , 
		"and nvl(a.iydstate,0)=0" })
	List<TbProductinfo> getProductListForB2B(int icustomersetid);
	
	@Update({"update tb_productinfo set iydstate=#{iydstate} where iproductid=#{iproductid} "})
	void updateTbProductinfoIydstate(int iproductid,int iydstate);
	
	
	@Select({"select ",SELECT_FIELDS," from " ,TABLE_NAME," order by iproductid"})
	List<TbProductinfo> getProductListAll();
	
	
	@Select({"select ",SELECT_FIELDS," from " ,TABLE_NAME," where nvl(a.iydstate,0)=1"," order by iproductid"})
	List<TbProductinfo> getProductListForYD();
	
	@Select({"select ","count(*)"," from " ,TABLE_NAME})
	int  getProductListCount();
	
	
	
	
}