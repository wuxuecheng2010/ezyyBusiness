package com.zjezyy.mapper.erp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbStocks;

@Repository
@Mapper
public interface TbStocksMapper {
	  String SELECT_FIELDS="iproductid,ibatchid,iproviderid,numprice,numpurchasetaxrate,numquantity,iunitid,ipackingid,vcbatchnumber,dtusefullife,dtmanufacture,vcconfirmfile,ilinkmanid,numlarge,nummiddle,flagtwo,numsaletaxrate";
	  
	  /**
	   * 
	  * @Title: getListByIproductid
	  * @Description: 不考虑转仓的库存   销售订单到销售开票过程的库存查询逻辑
	  * @param @param iproductid
	  * @param @return    参数
	  * @author wuxuecheng
	  * @return List<TbStocks>    返回类型
	  * @throws
	   */
	  @Select({"select ",SELECT_FIELDS, " from (select t.*,b.numlarge,b.nummiddle ,'Y' as flagTwo,q.NUMSALETAXRATE ",
			  " from vw_stockcansells t,tb_product_packing b,tb_provider p,tb_productinfo q",
              " where t.ipackingid=b.isid(+) and t.iproviderid=p.iproviderid and t.iproductid=q.iproductid",
              " and t.iproductid=#{iproductid}  and p.ipurchartype=3",
              " and numquantity>0   union all",
              " select t.*,b.numlarge,b.nummiddle ,decode(nvl(w.isid,0),0,'N','Y') flagTwo,q.NUMSALETAXRATE",
              " from vw_stockcansells t,tb_product_packing b,tb_provider p,tb_twoinvoicesystemcatalogs w,tb_productinfo q",
              "  where t.ipackingid=b.isid(+) and t.iproviderid=p.iproviderid and t.iproductid=q.iproductid",
              " and t.iproductid=w.iproductid(+) and   t.iproviderid=w.iproviderid(+)",
              " and t.iproductid=#{iproductid} and p.ipurchartype<>3 and numquantity>0) order by dtusefullife" })
	  List<TbStocks> getListByIproductid(int iproductid);
	  
	  
	  /**
	   * 
	  * @Title: getListByCustomerIproductid
	  * @Description:  考虑转仓的库存 （减去转仓库存）   销售开票查询库存逻辑 (没有批号信息 废纸   且销售开票库存查询窗口存在bug 例如 批号1=100,批号2=200  批号2转仓200但是未处理 结果开票库存查询界面这个商品信息就出不来了)
	  * @param @param iproductid
	  * @param @param icustomer
	  * @param @return    参数
	  * @author wuxuecheng
	  * @return List<TbStocks>    返回类型
	  * @throws
	   */
	  /*@Select({"select t01.iproductid,",
	     "  t01.vcproductcode,",
	     "  t01.vcuniversalname,",
	     "  t01.vcstandard,",
	     "  t01.vcproductname,",
	     "  t01.iproductunitid,",
	     "  t01.idrugformid,",
	      " t01.numcountryprice,",
	      " t01.iproducerid,",
	      " t02.vcproducername,",
	     "  t01.vceasycode,",
	     "  t01.numwarningdays,",
	     "  tmpLastSale.dtsaledate,",
	      " tmpCansell.numcansell,",
	      " decode(nvl(t01.flagsaleslimit, 'N'),",
	          "    'Y',",
	           "   tmpCansell.numLimitquantity,",
	           "   null) as numlimitquantity,",
	     "  t03.numlarge,",
	     "  t01.flaghardtoget,",
	      " t01.flagsaleslimit,",
	       "decode(nvl(t01.flagquantum, 'N'),",
	         "     'N',",
	            "  decode(nvl(t04.numopen, -1),",
	               "      -1,",
	                "     -1,",
	                "     0,",
	                "     0,",
	                 "    nvl(t04.numopen, -1) - tmpCansell.numwillout),",
	            "  decode(sign(nvl(t07.numquantiy, 0)), 1, t07.numquantiy, 0)) as numopen,",
	      " nvl(t01.flagquantum, 'N') as flagquantum",
	 " from tb_productinfo t01,",
	    "   tb_producer t02,",
	     "  tb_stockcontrol t04, ",
	     "  (select m.iproductid,",
	            "   (nvl(m.numquantiy, 0) + nvl(n.numapplications, 0)) numquantiy",
	      "    from (select q.iproductid, sum(numquantity) as numquantiy",
	                "  from tb_productquantuminfo q,",
	                 "      tb_productinfo        p,",
	                   "    tb_customer           c",
	                " where q.iproductid = p.iproductid",
	                "   and q.icustomerid = c.icustomerid",
	                "   and (c.icustomerid = ${icustomerid} or",
	                 "      (nvl(c.iparentid, 0) = ${icustomerid} and",
	                "       nvl(c.FLAGSETTLEMENT, 'N') = 'Y'))",
	                "   and nvl(p.flagquantum, 'N') = 'Y'",
	                "   and p.iproductid=#{iproductid} ",
	               "  group by q.iproductid) m,",
	              " (select t1.iproductid,",
	                  "     -sum(t1.numapplications) as numapplications",
	                  "from tb_salesnotices t1,",
	                  "     tb_salesnotice  t2,",
	                  "     tb_productinfo  t3,",
	                   "    tb_customer     t4",
	                " where t1.ibillid = t2.ibillid",
	                 "  and t1.iproductid = t3.iproductid",
	                 "  and t2.icustomerid = t4.icustomerid",
	                 "  and nvl(t2.flagapp, 'N') = 'N'",
	                 "  and (t4.icustomerid = ${icustomerid} or",
	                     "  (nvl(t4.iparentid, 0) = ${icustomerid} and",
	                    "   nvl(t4.flagsettlement, 'N') = 'Y'))",
	                  " and t1.iproductid=#{iproductid} ",
	                  " and nvl(t3.flagquantum, 'N') = 'Y'",
	                " group by t1.iproductid) n",
	        " where m.iproductid = n.iproductid(+)) t07,",
	       "(select max(numlarge) as numLarge, iproductid",
	         " from tb_product_packing",
	       "  where nvl(flagusing, 'N') = 'Y'",
	       "  group by iproductid) t03,",
	      " (select max(t2.dtappdate) as dtSaleDate, tp.iproductid",
	       "   from tb_salesnotices t3, tb_salesnotice t2, tb_productinfo tp",
	       "  where t3.ibillid = t2.ibillid",
	         "  and t3.iproductid = tp.iproductid",
	         "  and t2.icustomerid = ${icustomerid}",
	          " and t2.flagapp = 'Y'",
	           "and t3.iproductid=#{iproductid} ",
	         "group by tp.iproductid) tmpLastSale,",
	       "(select iproductid,",
	               "sum(numquantity) as numCanSell,",
	               "sum(numLimitquantity) as numLimitquantity,",
	               "sum(numWillOut) as numWillOut",
	          "from (select t1.iproductid,",
	                      " t1.numquantity,",
	                       "decode(nvl(tp.flagsaleslimit, 'N'),",
	                           "   'Y',",
	                             " decode(sign(nvl(t1.numlocks, 0) - t1.numquantity),",
	                                 "    1,",
	                                   "  t1.numquantity,",
	                                 "    t1.numlocks),",
	                             " t1.numquantity) as numLimitquantity,",
	                       "0 as numWillOut",
	                  "from tb_stocks t1,",
	                       "(select * from vw_storeandposition where istoretype = 1) t2,",
	                      " tb_productinfo tp",
	                " where t1.iproductid = tp.iproductid",
	                   "and t1.istorepositionid = t2.istorepositionid",
	                  " and t1.numquantity > 0",
	                   "and (nvl(t1.flaglock, 'N') <> 'Y' and",
	                       "nvl(t1.flagstop, 'N') <> 'Y')",
	                   "and t1.iproductid=#{iproductid} ",
	                "union all",
	                "select t1.iproductid,",
	                      " nvl(t1.numrealout, 0) - nvl(t1.numapplications, 0) as numquantity,",
	                       "nvl(t1.numrealout, 0) - nvl(t1.numapplications, 0) as numquantity2,",
	                       "t1.numapplications - nvl(t1.numrealout, 0) as numWillOut",
	                  "from tb_salesnotices t1,",
	                      " tb_salesnotice  t2,",
	                      " tb_productinfo  tp",
	                " where t1.ibillid = t2.ibillid",
	                  " and t1.iproductid = tp.iproductid",
	                   "and t1.isid <> 0",
	                   "and t2.dtcreationdate >= sysdate - 30",
	                   "and t1.iproductid=#{iproductid}",
	                   "and nvl(t1.flagpickover, 'N') = 'N' ",
	                "union all ",
	               " select t1.iproductid,",
	                      " 0 - t1.numquantity as numquantity,",
	                       "0 - t1.numquantity as numquantity2,",
	                       "0",
	                  "from tb_storepositionchange  t0,",
	                       "tb_storepositionchanges t1,",
	                       "vw_storeandposition     t2,",
	                       "tb_productinfo          tp,",
	                       "tb_stocks               t6",
	                 "where t0.ibillid = t1.ibillid",
	                  " and t1.iproductid = tp.iproductid",
	                  " and t1.istorepositionid = t2.istorepositionid(+)",
	                   "and t2.istoretype <> 1",
	                  " and t1.iproductid = t6.iproductid",
	                   "and nvl(t1.ibatchid, 0) = nvl(t6.ibatchid, 0)",
	                   "and t1.vcbatchnumber = t6.vcbatchnumber",
	                   "and t1.iprestorepositionid = t6.istorepositionid",
	                  " and nvl(t6.flaglock, 'N') = 'N'",
	                   "and nvl(t6.FLAGSTOP, 'N') = 'N'",
	                   "and nvl(t1.iproviderid, 0) = nvl(t6.iproviderid, 0)",
	                   "and nvl(t0.flagapp, 'N') <> 'Y'",
	                   "and t0.dtcreationdate >= sysdate - 90",
	                   "and t1.iproductid=#{iproductid}",
	               " union all ",
	                "select t1.iproductid,",
	                       "－t1.numquantity,",
	                       "－t1.numquantity as numquantity2,",
	                      " －t1.numquantity as numWillOut",
	                 " from tb_stocks t1, tb_increasedprice t2, tb_productinfo t3",
	                " where t1.iproductid = t2.iproductid",
	                  " and t1.iproductid = t3.iproductid",
	                   "and t1.ibatchid = t2.ibatchid",
	                   "and nvl(t2.flagsure, 'N') = 'N'",
	                   "and t1.iproductid=#{iproductid}",
	                       ")",
	         "group by iproductid) tmpCansell",
	 "where t01.iproductid = tmpLastSale.iproductid(+)",
	   "and t01.iproducerid = t02.iproducerid(+)",
	   "and t01.iproductid = t03.iproductid(+)",
	   "and t01.iproductid = t04.iproductid(+)",
	   "and t01.iproductid = t07.iproductid(+)",
	   "and t01.iproductid = tmpCansell.iproductid",
	   "and tmpCansell.numCanSell > 0 ",
	   "and t01.iproductid=#{iproductid}"})
	  List<TbStocks> getListByCustomerIproductid(int iproductid,int icustomerid);*/
}
