package com.zjezyy.mapper.erp;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;
@Repository
@Mapper
public interface SystemMapper {
	
	/**
	 * 调用存储过程，获取ERP单号
	 * @param params={v_prefix 单据前缀}
	 * @return params={v_billcode 单据号}
	 */
	@Select({ "call p_getbillno(#{v_prefix,mode=IN,jdbcType=VARCHAR},",
			 "#{v_billcode,mode=OUT,jdbcType=VARCHAR})" })
	@Options(statementType=StatementType.CALLABLE)
	void getERPBillcode(Map<String,String> params);

	
	
	/*以下2个方法根据配置参数去使用  参考ERP E:\Works\csharpworks\srcs\erpNew\CSFramework4.Server\CSFramework4.Server.DataAccess\DAL_DataDict\dalPRODUCTINFO.cs
	判断是否启用了新的开票规则---------------------------------------
    string sProducerName = "usp_CustomerProduct_Cansell";
    string sql = "select t.vcvalue  from sys_parameters t where t.vcparameter = 'UNIONSALE'";
    object oj = DataProvider.Instance.ExecuteScalar(_Loginer.DBName, sql);
    if(oj != DBNull.Value && oj != null)
    {
        if (oj.ToString() == "Y")
            sProducerName = "usp_cusproduct_wmscansell";
    }*/
	
	/**
	 * 
	* @Title: usp_CustomerProduct_Cansell
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param params    参数
	* @author wuxuecheng
	* @return void    返回类型
	* @throws
	 */
/*	@Select({ "call usp_CustomerProduct_Cansell(#{CustomerID_in,mode=IN,jdbcType=INTEGER},",
	 "#{ProductID_in,mode=IN,jdbcType=VARCHAR},",	
	 "#{SalesNoticeDetailID_in,mode=IN,jdbcType=INTEGER},",	
	 "#{ErrMsg_Out,mode=IN,jdbcType=VARCHAR},",	
	 "#{cur_Result,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=com.zjezyy.entity.erp.TbStocks2})" })
	@Options(statementType=StatementType.CALLABLE)
	void usp_CustomerProduct_Cansell(Map<String,Object> map);
	
	
	@Select({ "call usp_CusProduct_WMSCansell(#{CustomerID_in,mode=IN,jdbcType=INTEGER},",
		 "#{ProductID_in,mode=IN,jdbcType=VARCHAR},",	
		 "#{SalesNoticeDetailID_in,mode=IN,jdbcType=INTEGER},",	
		 "#{ErrMsg_Out,mode=IN,jdbcType=VARCHAR},",	
		 "#{cur_Result,mode=OUT,jdbcType=CURSOR})" })
		@Options(statementType=StatementType.CALLABLE)
		void usp_CusProduct_WMSCansell(Map<String,Object> map);*/
	
}