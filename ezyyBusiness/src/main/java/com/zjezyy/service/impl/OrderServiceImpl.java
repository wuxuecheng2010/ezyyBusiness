package com.zjezyy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccOrderHistory;
import com.zjezyy.entity.b2b.MccOrderProduct;
import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbSalesNotice;
import com.zjezyy.entity.erp.TbSalesNoticeS;
import com.zjezyy.entity.erp.TbSalesOrder;
import com.zjezyy.entity.erp.TbSalesOrderS;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.enums.BusinessInterfaceType;
import com.zjezyy.enums.DeployPolicyEnum;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.enums.OrderComment;
import com.zjezyy.enums.PayResult;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccOrderHistoryMapper;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.mapper.b2b.MccOrderProductMapper;
import com.zjezyy.mapper.b2b.MccPayResultMapper;
import com.zjezyy.mapper.erp.TbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbCustomerMapper;
import com.zjezyy.mapper.erp.TbMccOrderMapper;
import com.zjezyy.mapper.erp.TbMccOrderProductMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.mapper.erp.TbSalesNoticeMapper;
import com.zjezyy.mapper.erp.TbSalesNoticeSMapper;
import com.zjezyy.mapper.erp.TbSalesOrderMapper;
import com.zjezyy.mapper.erp.TbSalesOrderSMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.CustomerService;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.ProductService;
import com.zjezyy.service.SettingService;
import com.zjezyy.service.SystemService;
import com.zjezyy.utils.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	SystemService systemServiceImpl;

	@Autowired
	MccOrderMapper mccOrderMapper;
	@Autowired
	TbMccOrderMapper tbMccOrderMapper;

	@Autowired
	MccOrderProductMapper mccOrderProductMapper;
	@Autowired
	TbMccOrderProductMapper tbMccOrderProductMapper;

	@Autowired
	CustomerService customerServiceimpl;
	
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	@Autowired
	ProductService productServiceImpl;
	
	@Autowired
	TbSalesOrderMapper tbSalesOrderMapper;
	@Autowired
	TbSalesOrderSMapper tbSalesOrderSMapper;
	
	@Autowired
	TbSalesNoticeMapper tbSalesNoticeMapper;
	@Autowired
	TbSalesNoticeSMapper tbSalesNoticeSMapper;
	
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	
	@Autowired
	TbCustomerKindPriceMapper tbCustomerKindPriceMapper;
	
	@Autowired
	TbStocksMapper tbStocksMapper;
	
	@Autowired
	MccOrderHistoryMapper mccOrderHistoryMapper;
	
	@Autowired
	MccPayResultMapper mccPayResultMapper;
	
	@Autowired
	SettingService settingServiceImpl;
	

	@Value("${erp.system.default.user}")
	private String  defaultuser;//vccreatedby;// 默认的系统建单账号
	
	@Value("${erp.salesorder.vcbillcode.prefix}")
	private String salesOrderBillPrefix;//
	
	@Value("${erp.salesnotice.vcbillcode.prefix}")
	private String salesnoticeBillPrefix;
	
	@Value("${b2b.product.price.icustomerkindid}")
	private Integer icustomerkindid;
	
	@Value("${erp.salesnotice.approval.url}")
	private String salesnoticeApprovalUrl;
	
	
/*	@Value("${setting.ezyy.code}")
	private String setting_code;
	
	@Value("${setting.ezyy.key.b2b.order.expire.status}")
	private String setting_key_order_expire_status;
	
	@Value("${setting.ezyy.key.b2b.order.payed.status}")
	private String setting_key_order_payed_status;*/
	
	/*@Value("${b2b.order.payed.status}")
	private Integer order_payed_status;
	
	@Value("${b2b.order.expire.status}")
	private Integer order_expire_status;*/

	@Override
	public void checkOrderPlaceParam(String oc_order_id, String token) throws RuntimeException {
		if (oc_order_id == null || "".equals(oc_order_id)) {
			throw new BusinessException(ExceptionEnum.B2B_ORDER_ID_LACK);
		}
		if (token == null || "".equals(token)) {
			throw new BusinessException(ExceptionEnum.TOKEN_LACK);
		}
	}

	//同步处理 一次只能通过一个
	@Transactional
	@Override
	public synchronized void orderPlace(int order_id, int itypeid, String token) throws RuntimeException {
		// 1、复制b2b订单到erp的接口表
		Integer impid = makeMccOrderToTbMccOrder(order_id);

		// 2、根据税率不同生成 销售订单
		Map<TbSalesOrder,List<TbSalesOrderS>> map=
		makeTbMccOrderToTbSalesOrder(impid, itypeid);
		
		// 3、根据 销售订单生成销售开票
		makeTbSalesOrderToTbSalesNotice(map);

		// 4、检查销售开票结果是否合法   如果不合法报异常
		//TODO
	}

	@Transactional
	@Override
	public Integer makeMccOrderToTbMccOrder(int order_id) throws RuntimeException {
		MccOrder mccOrder = mccOrderMapper.getOne(order_id);

		Integer customer_id = mccOrder.getCustomer_id();
		TbCustomer tbCustomer = customerServiceimpl.getTbCustomerByMccCustomerId(customer_id);

		// 主表保存到接口表
		//TbMccOrder tbMccOrder = mccOrder.toTbMccOrder(tbCustomer.getIcustomerid(), tbCustomer.getVccustomername());
		TbMccOrder tbMccOrder=new TbMccOrder(mccOrder,tbCustomer.getIcustomerid(), tbCustomer.getVccustomername());
		tbMccOrderMapper.insert(tbMccOrder);
		Integer impid = tbMccOrder.getImpid();

		List<MccOrderProduct> list = mccOrderProductMapper.getListByOrderId(tbMccOrder.getMcc_order_id());
		for (MccOrderProduct mccOrderProduct : list) {
			Integer product_id = mccOrderProduct.getProduct_id();
			TbProductinfo_Eo tbProductinfo_Eo = productServiceImpl.getTbProductinfoEoByMccProductId(product_id);
			//TbMccOrderProduct tbMccOrderProduct = mccOrderProduct.toTbMccOrderProduct(impid,
			//		tbProductinfo_Eo.getIproductid(), tbProductinfo_Eo.getNumsaletaxrate());
			TbMccOrderProduct tbMccOrderProduct=new TbMccOrderProduct(mccOrderProduct,impid,
					tbProductinfo_Eo.getIproductid(), tbProductinfo_Eo.getNumsaletaxrate());
			// 细表保存到接口细表
			tbMccOrderProductMapper.insert(tbMccOrderProduct);
		}
		return impid;
	}

	@Transactional
	@Override
	public Map<TbSalesOrder,List<TbSalesOrderS>> makeTbMccOrderToTbSalesOrder(int impid, int itypeid) throws RuntimeException {

		//返回销售订单集合
		Map<TbSalesOrder,List<TbSalesOrderS>> mapres=new HashMap<>();
		// 获取临时表数据
		TbMccOrder tbMccOrder = tbMccOrderMapper.getOne(impid);
		List<TbMccOrderProduct> tbMccOrderProductList = tbMccOrderProductMapper.getListByImpid(impid);
		// 按照税率分组订单明细
		// 1获取税率列表;
		List<BigDecimal> taxRateList = tbMccOrderProductMapper.getSaleTaxRateList(impid);
		// 初始化存放分组信息的map
		Map<BigDecimal, List<TbMccOrderProduct>> map = makeTbMccOrderProductGroupByTaxRate(taxRateList,
				tbMccOrderProductList);

		// 保存主表 保存细表
		// 创建销售订单主表数据
		Set<Entry<BigDecimal, List<TbMccOrderProduct>>> set = map.entrySet();
		for (Entry<BigDecimal, List<TbMccOrderProduct>> entry : set) {
			//销售订单主单
			String vcbillcode =systemServiceImpl.genBillCodeForTransactional(salesOrderBillPrefix);
			TbSalesOrder tbSalesOrder=new TbSalesOrder(tbMccOrder,vcbillcode, itypeid, defaultuser);
			tbSalesOrderMapper.insert(tbSalesOrder);
			
			//销售订单明细
			List<TbMccOrderProduct> list=entry.getValue();
			
			
			float f=(float) 1.0;
			List<TbSalesOrderS> tbSalesOrderSList=new ArrayList<>();
			for(TbMccOrderProduct tbMccOrderProduct:list) {
				int ibillid=tbSalesOrder.getIbillid();
				float numqueue=f;f+=0.1;
				int iproductid=tbMccOrderProduct.getErp_iproductid();
				TbProductinfo_Eo tbProductinfo_Eo=tbProductinfoMapper.getOneEo(iproductid, icustomerkindid);
				
				TbSalesOrderS tbSalesOrderS=new TbSalesOrderS(tbMccOrderProduct,ibillid, numqueue, tbProductinfo_Eo.getIproductunitid(), 
						defaultuser, tbProductinfo_Eo.getVcproductcode(), tbProductinfo_Eo.getVcuniversalname(), 
						tbProductinfo_Eo.getVcstandard(), tbProductinfo_Eo.getVcunitname(), tbProductinfo_Eo.getVcproducername());
				tbSalesOrderSMapper.insert(tbSalesOrderS);
				tbSalesOrderSList.add(tbSalesOrderS);
			}
			
			mapres.put(tbSalesOrder, tbSalesOrderSList);
			
		}
		return mapres;

	}

	@Override
	public Map<BigDecimal, List<TbMccOrderProduct>> makeTbMccOrderProductGroupByTaxRate(List<BigDecimal> taxRateList,
			List<TbMccOrderProduct> tbMccOrderProductList) throws RuntimeException {
		Map<BigDecimal, List<TbMccOrderProduct>> map = new HashMap<BigDecimal, List<TbMccOrderProduct>>();
		for (BigDecimal taxRate : taxRateList) {
			List<TbMccOrderProduct> _tbMccOrderProductList = new ArrayList<TbMccOrderProduct>();
			map.put(taxRate, _tbMccOrderProductList);
			for (TbMccOrderProduct tbMccOrderProduct : tbMccOrderProductList) {
				BigDecimal erp_numsaletaxrate = tbMccOrderProduct.getErp_numsaletaxrate();
				if (taxRate.compareTo(erp_numsaletaxrate) == 0) {
					_tbMccOrderProductList.add(tbMccOrderProduct);
				}
			}
		}
		return map;
	}

	@Transactional
	@Override
	public void makeTbSalesOrderToTbSalesNotice(Map<TbSalesOrder, List<TbSalesOrderS>> map) throws RuntimeException {
		Set<Entry<TbSalesOrder, List<TbSalesOrderS>>> set= map.entrySet();
		for(Entry<TbSalesOrder, List<TbSalesOrderS>> entry:set) {
			TbSalesOrder tbSalesOrder=entry.getKey();
			
			//验证销售订单flagapp状态  如果为Y则抛出订单不能重复生成销售开票
			if(isTbSalesOrderFlagAppY(tbSalesOrder.getIbillid())) {
				throw new BusinessException(ExceptionEnum.ERP_ORDER_IS_USED_TO_NOTICE);
			}
			
			
			TbCustomer tbCustomer =tbCustomerMapper.getOne(tbSalesOrder.getIcustomerid());
			String vcbillcode=systemServiceImpl.genBillCodeForTransactional(salesnoticeBillPrefix);
			int itypeid=2 ;//单据类型 默认1 直接开票 2从订单导入 3招标网导入 4缺货导入,5销售冲价
			int isalerid=tbCustomer.getIsalerid();
			int igathermode=1;//收款方式 公共字典13  1现款 2非现款
			char flagurgent='N';
			TbSalesNotice tbSalesNotice=new TbSalesNotice( tbSalesOrder, vcbillcode,
					 itypeid, isalerid, igathermode, flagurgent);
			tbSalesNoticeMapper.insert(tbSalesNotice);
			int ibillid=tbSalesNotice.getIbillid();
					
			//List<TbSalesOrderS> list=tbSalesOrderSMapper.getTbSalesOrderSList(ibillid);
			List<TbSalesOrderS> list=entry.getValue();
			float numqueue=1.0f;
			for(TbSalesOrderS tbSalesOrderS:list) {
				//BigDecimal _price=tbSalesOrderS.getNumprice();
				//BigDecimal _quantity=tbSalesOrderS.getNumquantity()
				//BigDecimal numquantity=new BigDecimal(1);
				//获取库存列表
				List<TbStocks> stocklist= tbStocksMapper.getListByIproductid(tbSalesOrderS.getIproductid());
				
				//获取库存分配
				Map<BigDecimal ,TbStocks> stockMap= stockDeploy(tbSalesOrderS,stocklist,DeployPolicyEnum.FIFO);
				Set<Entry<BigDecimal, TbStocks>> stockset =stockMap.entrySet();
				
				TbCustomerKindPrice tbCustomerKindPrice=tbCustomerKindPriceMapper.getOne(tbSalesOrderS.getIproductid(), icustomerkindid);
				int lastid=tbSalesNoticeSMapper.getLastId(tbSalesOrderS.getIproductid(), tbCustomer.getIcustomerid());
				TbSalesNoticeS tbSalesNoticeS=tbSalesNoticeSMapper.getOne(lastid);
				BigDecimal numlastprice=tbSalesNoticeS==null?null:tbSalesNoticeS.getNumprice();
				
				TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(tbSalesOrderS.getIproductid());
				BigDecimal numcountryprice=tbProductinfo.getNumcountryprice();
				
				
				for(Entry<BigDecimal, TbStocks> e :stockset) {
					
					BigDecimal numapplications=e.getKey();
					
					
					TbSalesNoticeS _tbSalesNoticeS=new TbSalesNoticeS( 
							ibillid, numqueue, numapplications,
							 tbSalesOrderS, e.getValue(),
							 tbCustomerKindPrice,
							 numlastprice, numcountryprice
							);
					tbSalesNoticeSMapper.insert(_tbSalesNoticeS);
				}
				numqueue+=0.1;
			}
			
			//销售订单->销售开票之后的处理
			tbSalesOrderMapper.update(tbSalesOrder.getIbillid(), defaultuser);
			
		}
		
	}
	
	@Override
	public Map<BigDecimal ,TbStocks> stockDeploy(TbSalesOrderS tbSalesOrderS,List<TbStocks> stocklist,DeployPolicyEnum policy)throws RuntimeException{
		BigDecimal numquantity=tbSalesOrderS.getNumquantity();
		Map<BigDecimal,TbStocks> map=new HashMap<>();
		BigDecimal needqty=numquantity;
		BigDecimal nearstockqty=BigDecimal.ZERO; //统计近效期库存
		for(TbStocks tbStocks:stocklist) {
			BigDecimal stockqty=tbStocks.getNumquantity();//库存数量
			boolean isnear=false;
			if(DeployPolicyEnum.FIFO.equals(policy)) {//判断是否为单纯先进先出
				isnear=false;//如果是单纯先进先出  直接将近效期标志为false  意思为不考虑近效期了
			}else if(DeployPolicyEnum.FIFOAN.equals(policy)) {
				//如果考虑近效期   判断药品是否真的近效期
				TbProductinfo tbProductinfo=productServiceImpl.getTbProductinfoById(tbStocks.getIproductid());//商品信息
				isnear=productServiceImpl.isNearEffective(tbProductinfo, tbStocks);
			}
			
			if(!isnear) {//当前非近效期限制
				BigDecimal _needqty=needqty;//当前循环需要的库存
				needqty=needqty.subtract(stockqty);
				int x=needqty.compareTo(BigDecimal.ZERO);
				if(x==0||x==-1) {
					map.put(_needqty, tbStocks);
					break;
				}else {
					map.put(stockqty, tbStocks);
				}
			}else {
				nearstockqty=nearstockqty.add(stockqty);//统计近效期库存
			}
			
		}
		int x=needqty.compareTo(BigDecimal.ZERO);
		if(x==1) {
			int _x=needqty.compareTo(nearstockqty);//更多需求值与近效期数量相比
			String productinfo="";
			if(_x==1){
				productinfo=String.format("%s %s 缺%d", tbSalesOrderS.getVcuniversalname(),tbSalesOrderS.getVcstandard(),Integer.valueOf(needqty.toString()));
			}else {
				productinfo=String.format("%s %s 缺%d 但有近效期库存%d可购", tbSalesOrderS.getVcuniversalname(),tbSalesOrderS.getVcstandard(),Integer.valueOf(needqty.toString()),Integer.valueOf(nearstockqty.toString()));
			}
			
			throw new BusinessException(ExceptionEnum.ERP_PRODUCT_STOCK_SHORT.getMsg()+":"+productinfo+ "",ExceptionEnum.ERP_PRODUCT_STOCK_SHORT.getCode());
		}
		return map;
	}

	

	@Override
	public List<MccOrder> getUnPayMccOrderList(int expire) throws RuntimeException {
		List<MccOrder> list=mccOrderMapper.getUnPayMccOrderList(expire);
		return list;
	}
	
	@Override
	public List<MccOrder> getUnPayExpireMccOrderList(int expire) throws RuntimeException {
		List<MccOrder> list=mccOrderMapper.getUnPayExpireMccOrderList(expire);
		return list;
	} 

	/**
	 * 安全起见 这里还是不要事务回滚了 
	 */

	@Override
	public void payResultQuery(PayService payService, int order_id) throws RuntimeException {
		MccPayResult mccPayResult = payService.payResultQuery(order_id);

		if(mccPayResult!=null && PayResult.SUCCESS.getCode().equals(mccPayResult.getTradestatus())) {//付款成功
			
			//1更新b2b订单为已付款  这里必须及时 涉及到用户端付款之后页面检查是否付款的字段
			 updateMccOrderPayedAndCreateResult(order_id, mccPayResult);
			
			//2、erp订单状态 扮演审核的角色  调用ERP存储过程
			 List<TbSalesNotice> list=tbSalesNoticeMapper.getListByMccOrderID(order_id,BusinessInterfaceType.B2BToERPOnLine.getCode());
			 for(TbSalesNotice tbSalesNotice:list) {
				 httpApproval(tbSalesNotice.getIbillid(),defaultuser);
			 }
			 
			 
		}
		
	}
	
	@Transactional
	private void updateMccOrderPayedAndCreateResult(int order_id, MccPayResult mccPayResult) {
		//1、更新b2b订单状态 支付成功
		 String order_payed_status=settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_PAYED_STATUS);
		 setMccOrderStatus( order_id,  order_payed_status, OrderComment.SUCCESS, 0);
		
		//2、保存付款记录
		mccPayResultMapper.insert( mccPayResult );
		log.info(String.format("订单：%d %s", order_id,OrderComment.SUCCESS));
	}
	
	
	
	
	
	
	@Transactional
	@Override
	public void payExpired(int order_id) throws RuntimeException {
		
	   String order_expire_status=settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_EXPIRE_STATUS);
	   // 1、更新b2b订单状态为取消
	   setMccOrderStatus( order_id,  order_expire_status, OrderComment.EXPIRED, 0);
	   
	   // 2、更新ERP销售开票作废
	   setTbSalesNoticeCancelByMccOrderID(order_id);
	   
	   log.info(String.format("订单：%d %s", order_id,OrderComment.EXPIRED));
	   
	}
	
	@Override
	public void setMccOrderStatus(int order_id, String order_status_id, OrderComment comment, int notify)
			throws RuntimeException {

		//更新订单状态
		mccOrderMapper.updateMccOrderStatus(order_id, order_status_id);
		
		//创建订单历史信息
		MccOrderHistory mccOrderHistory=new MccOrderHistory();
		mccOrderHistory.setComment(comment.getComment());
		mccOrderHistory.setNotify(notify);
		mccOrderHistory.setOrder_id(order_id);
		mccOrderHistory.setOrder_status_id(Integer.valueOf(order_status_id));
		mccOrderHistoryMapper.insert(mccOrderHistory);
		
	}

	@Override
	public void setTbSalesNoticeCancelByMccOrderID(int order_id) {
		
		TbMccOrder tbMccOrder=tbMccOrderMapper.getOneByOrderID(order_id);
		if(tbMccOrder==null) {
			String msg=String.format("B2B订单：%d %s",order_id, ExceptionEnum.B2B_ORDER_NOT_IN_ERP_TMP.getMsg());
			log.error(msg);
		}else {
			Integer impid= tbMccOrder.getImpid();
			//获取销售订单、销售开票  并设置订单状态
			//BusinessInterfaceType
			tbSalesNoticeMapper.cancelTbSalesNotice(impid,BusinessInterfaceType.B2BToERPOnLine.getCode());
		}
		
		
		
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean isTbSalesOrderFlagAppY(int salesorder_ibillid) {
		TbSalesOrder tbSalesOrder=tbSalesOrderMapper.getOne(salesorder_ibillid);
		return tbSalesOrder!=null&&"Y".equals(tbSalesOrder.getFlagapp())?true:false;
	}

	@Override
	public Map<String,Object> approval(int salesnotice_ibillid,String user ) throws RuntimeException {
		
		Map<String ,Object > map =new HashMap<String,Object>();
		map.put("DocNo_in", salesnotice_ibillid);
		map.put("AppUser_in", user);
		map.put("iResult_out", 0);
		map.put("ErrMsg_Out", "");
		tbSalesNoticeMapper.approval(map);
		//分析执行结果信息  该抛出异常的抛出异常
		if((Integer)map.get("iResult_out")!=0) {
			throw new BusinessException((String)map.get("ErrMsg_Out"),-9999);
		}
		return map;
	}

	@Override
	public boolean checkSalesNoticeAppParams(String salesnotice_ibillid) throws RuntimeException {
		//获取销售开票订单信息 ，检查单据状态
		TbSalesNotice tbSalesNotice=tbSalesNoticeMapper.getOne(Integer.valueOf(salesnotice_ibillid));
		if(tbSalesNotice==null ) 
			throw new BusinessException(ExceptionEnum.ERP_SALESNOTICE_NOT_EXISIT);
		
		if("Y".equals(tbSalesNotice.getFlagapp())) 
			throw new BusinessException(ExceptionEnum.ERP_SALESNOTICE_IS_APPED);
		
		if("Y".equals(tbSalesNotice.getFlagtowms()))
			throw new BusinessException(ExceptionEnum.ERP_SALESNOTICE_IS_TO_WMS);
		
		//追溯b2b订单是否为已付款状态
		Integer salesorder_ibillid=tbSalesNotice.getIsourceid();
		if(salesorder_ibillid==null)
			throw new BusinessException(ExceptionEnum.ERP_SALESNOTICE_HAS_NO_SOURCEID);
		MccOrder mccOrder=getMccOrderBySalesOrderIbillid(salesorder_ibillid);
		return mccOrder!=null;
	}
	
	
	public MccOrder getMccOrderBySalesOrderIbillid(int salesorder_ibillid) throws RuntimeException {
		
		//订单不存在
		TbSalesOrder tbSalesOrder=tbSalesOrderMapper.getOne(salesorder_ibillid);
		if(tbSalesOrder==null){
			throw new BusinessException(ExceptionEnum.ERP_ORDER_NOT_EXISIT);
		}
		
		//订单单据类型不对
		Integer impid=tbSalesOrder.getIsourceid();
		Integer itypeid=tbSalesOrder.getItypeid();
		if(impid==null || BusinessInterfaceType.B2BToERPOnLine.getCode()!=itypeid) {
			throw new BusinessException(ExceptionEnum.ERP_ORDER_ITYPEID_WRONG);
		}
		
		//b2b到erp中间表数据不存在
		TbMccOrder  tbMccOrder =tbMccOrderMapper.getOne(impid);
		if(tbMccOrder==null)
			throw new BusinessException(ExceptionEnum.ERP_ORDER_TBMCC_NOT_EXISIT);
		
		//检查订单状态
		Integer mcc_order_id=tbMccOrder.getMcc_order_id();
		MccOrder mccOrder=mccOrderMapper.getOne(mcc_order_id);
		Integer order_status_id=mccOrder.getOrder_status_id();
		String payed_status_id=settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_PAYED_STATUS);
		if(order_status_id==null ||!payed_status_id.equals(order_status_id.toString()))
			throw new BusinessException(ExceptionEnum.B2B_ORDER_NOT_PAYED);
		
		return mccOrder;
		
	}

	@Override
	public void httpApproval(int salesnotice_ibillid, String user) throws RuntimeException {
		//获取管理员电话号码
		String tels=settingServiceImpl.getEzyySettingValue(EzyySettingKey.B2B_MANAGER_TELS);
		tels=tels==null||"".equals(tels)?"15990683720":tels;
		String[] arr=tels.split(",") ;
		List<String> tellist=new ArrayList<String>();
		for(String s:arr)
			tellist.add(s);
		
		Map<String ,String > map=new HashMap<String ,String >();
		map.put("ibillid",String.valueOf(salesnotice_ibillid));
		map.put("user", user);
		String resStr=HttpClientUtil.postMap(salesnoticeApprovalUrl,map );
		JSONObject jsonObject=null;
		if(resStr!=null) {
			jsonObject = JSON.parseObject(resStr);
			int status=jsonObject.getInteger("status");
			String msg=jsonObject.getString("msg");
			if(status!=0) {
				String error=String.format("B2B订单成功付款，但是销售开票 ibillid：%d 审核失败，影响发货，请人工退款或者继续人工审核,原因：%s", salesnotice_ibillid,msg);
				log.error(error);
				systemServiceImpl.sendTelMsg(error, tellist);
				throw new BusinessException(msg,-9998);
			}
		}else {
			String error=String.format("B2B订单成功付款，但是销售开票 ibillid：%d 审核失败，原因：%s", salesnotice_ibillid,ExceptionEnum.ERP_SALESNOTICE_HTTP_APPROVAL_FAIL.getMsg());
			log.error(error);
			systemServiceImpl.sendTelMsg(error, tellist);
			throw new BusinessException(ExceptionEnum.ERP_SALESNOTICE_HTTP_APPROVAL_FAIL);
		}
		
	}




	

	

}
