package com.zjezyy.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.erp.TbSalesOrder;
import com.zjezyy.entity.erp.TbSalesOrderS;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.enums.DeployPolicyEnum;
import com.zjezyy.enums.OrderComment;

public interface OrderService {

	// 校验参数传入是否合法
	void checkOrderPlaceParam(String order_id, String token) throws RuntimeException;

	// 下单到ERP
	void orderPlace(int order_id,int itypeid, String token) throws RuntimeException;
	
	//创建中间表
	Integer makeMccOrderToTbMccOrder(int order_id)throws RuntimeException;
	
	//创建销售订单
	Map<TbSalesOrder,List<TbSalesOrderS>> makeTbMccOrderToTbSalesOrder(int impid,int itypeid) throws RuntimeException;
	
	//按税率对订单明细数据进行分组
	Map<BigDecimal,List<TbMccOrderProduct>> makeTbMccOrderProductGroupByTaxRate(List<BigDecimal> taxRateList,List<TbMccOrderProduct> tbMccOrderProductList) throws RuntimeException;

	//销售订单导入到销售开票
	void makeTbSalesOrderToTbSalesNotice(Map<TbSalesOrder,List<TbSalesOrderS>> map)throws RuntimeException;
     
	//订单库存分配
	Map<BigDecimal ,TbStocks> stockDeploy(TbSalesOrderS tbSalesOrderS,List<TbStocks> stocklist,DeployPolicyEnum policy) throws RuntimeException;

	//获取未付款的订单数据
	List<MccOrder> getUnPayMccOrderList(int expire)throws RuntimeException;
	
	//获取付款超时订单数据
	List<MccOrder> getUnPayExpireMccOrderList(int expire)throws RuntimeException;
	
	//B2B订单状态
	void setMccOrderStatus(int order_id, String order_status_id,OrderComment comment,int notify) throws RuntimeException;

    //根据第三方付款信息  更新付款情况
    void payResultQuery(PayService payService,int order_id)throws RuntimeException;
    
    //付款超时
    void payExpired(int order_id) throws RuntimeException;
    
    //取消订单  修改订单状态  使他不占用库存
    void setTbSalesNoticeCancelByMccOrderID(int order_id);
    
    //判断销售订单是否已经使用过 
    boolean isTbSalesOrderFlagAppY(int salesorder_ibillid);
    
    //审核销售开票  即付款之后操作销售开票数据 ibillid:销售开票ID
    Map<String,Object> approval(int salesnotice_ibillid,String user)throws RuntimeException;
    
    //检查销售开票单据的数据合法性
    boolean checkSalesNoticeAppParams(String salesnotice_ibillid)throws RuntimeException;
    
    //根据销售订单ID查询b2b订单数据
    MccOrder getMccOrderBySalesOrderIbillid(int salesorder_ibillid) throws RuntimeException;
    
    //http方式审核销售开票
    void httpApproval(int salesnotice_ibillid,String user)throws RuntimeException;
    
}
