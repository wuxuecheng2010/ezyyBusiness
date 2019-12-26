package com.zjezyy.mapper.erp;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
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

	
/**将返回类型为cursor的存储过程放在普通放入存储过程中调用   应对返回类型为cursor的存储
 * 测试  可以  但是cursor列名有变  需要跟着修改  这个很麻烦  不用这种方式了
 * 
CREATE OR REPLACE PROCEDURE getunittest_ (
 i_iunitid   IN  NUMBER,
  vcunitname   OUT VARCHAR2
 ) 
 is
  o_cursor    SYS_REFCURSOR;
  
  cursor c_unit is SELECT iunitid,vcunitname,vceasycode from tb_unit where 1=2;
  r           c_unit%rowtype;
 BEGIN
 -- 调用存储过程的 存储过程
 vcunitname:='';
 getunittest(i_iunitid,o_cursor  );

  loop
     fetch o_cursor into r;
     EXIT WHEN o_cursor%NOTFOUND;
     vcunitname:=r.vcunitname;
    --return ;
   end loop;

 END getunittest_;
 */
	
	@Select({ "call getUnitTest_(#{i_iunitid,mode=IN,jdbcType=INTEGER},"
		,"#{vcunitname,mode=OUT,jdbcType=VARCHAR})" })
	@Options(statementType=StatementType.CALLABLE)
	void getUnitTest_(Map<String,String> params);
	

	
	
/*	//带返回类型为cursor的存储过程  可以
 * //参考  https://blog.csdn.net/weixin_43532158/article/details/95320159
 * 存储过程如下：
 * create or replace procedure getUnitTest(
			  i_iunitid   IN  NUMBER,
			  o_cursor   OUT SYS_REFCURSOR
			) is
			begin
			   OPEN  o_cursor FOR SELECT iunitid,vcunitname,vceasycode from tb_unit where iunitid= i_iunitid ;
			end getUnitTest;
			*/
	@Results(id="tbunit", value={
			@Result(column="iunitid", property="iunitid", id=true,jdbcType=JdbcType.INTEGER),
		    @Result(column="vcunitname", property="vcunitname",jdbcType=JdbcType.VARCHAR),
		    @Result(column="vceasycode ", property="vceasycode",jdbcType=JdbcType.VARCHAR)
	})
	@ResultType(com.zjezyy.entity.erp.TbUnit.class)
	@Options(statementType=StatementType.CALLABLE)
	@Select({ "call getunittest(#{i_iunitid,mode=IN,jdbcType=INTEGER},",
	"#{o_cursor,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=tbunit})" })
	void getUnitTest(Map<String,Object> params);
	
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
	
	
	
	@Results(id="syspriorprice", value={
			@Result(column="numbargainingprice", property="numbargainingprice", jdbcType=JdbcType.DECIMAL),
		    @Result(column="numguidprice", property="numguidprice",jdbcType=JdbcType.DECIMAL)
	})
	@ResultType(com.zjezyy.entity.erp.SysPriorPrice.class)
	@Options(statementType=StatementType.CALLABLE)
	@Select({ "call usp_customerproduct_sellprice(#{customerid_in,mode=IN,jdbcType=INTEGER},",
	"#{productid_in,mode=IN,jdbcType=INTEGER},",
	"#{cur_result,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=syspriorprice})" })
	void getCustomerProductPrice(Map<String,Object> params);
	
	
	
	@Results(id="syscuspro", value={
			@Result(column="numopen", property="numopen", jdbcType=JdbcType.DECIMAL),
		    @Result(column="vcuniversalname", property="vcuniversalname",jdbcType=JdbcType.VARCHAR)
	})
	@ResultType(com.zjezyy.entity.erp.SysCustomerProductCanSale.class)
	@Options(statementType=StatementType.CALLABLE)
	@Select({ "call usp_CusProduct_WMSCansell(#{customerid_in,mode=IN,jdbcType=INTEGER},",
	"#{productid_in,mode=IN,jdbcType=INTEGER},",
	"#{salesnoticedetailid_in,mode=IN,jdbcType=INTEGER},",
	 "#{errmsg_out,mode=OUT,jdbcType=VARCHAR},",	
	"#{cur_result,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=syscuspro})" })
	void getCustomerCanBuyProduct(Map<String,Object> params);
	
	
	
}