package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.VwMccSalesNotice;

@Repository
@Mapper
public interface VwMccSalesNoticeMapper {
	
	
	/**
	 * 
	* @Title: getListByMccOrderIDAndType
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param mcc_order_id  B2B订单ID
	* @param @param itypeid  销售订单 单据类型  B2B在线支付订单为4
	* @param @return    参数
	* @author wuxuecheng
	* @return List<VwMccSalesNotice>    返回类型
	* @throws
	 */
	@Select({"select a.mcc_order_id ,a.mcc_total ,b.ibillid salesorder_ibillid,c.ibillid salesnotice_ibillid," , 
			"a.mcc_fullname ,b.vcbillcode salesorder_vcbillcode ,c.vcbillcode salesnotice_vcbillcode,", 
			"c.nummoney salesnotice_nummoney  from tb_mcc_order a,", 
			"tb_salesorder b," , 
			"tb_salesnotice c" , 
			"where a.impid=b.isourceid(+)" ,
			"and b.ibillid=c.isourceid(+)" , 
			"and b.itypeid=#{itypeid}" , 
			"and a.mcc_order_id  =#{mcc_order_id}", 
			"    order by c.vcbillcode "})
	List<VwMccSalesNotice>  getListByMccOrderIDAndType(int mcc_order_id,int itypeid);
	

}
