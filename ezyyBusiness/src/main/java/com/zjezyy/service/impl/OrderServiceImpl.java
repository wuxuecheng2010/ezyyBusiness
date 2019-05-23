package com.zjezyy.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.Result;
import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccOrderHistory;
import com.zjezyy.entity.b2b.MccOrderProduct;
import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbLisenceCustomer_Eo;
import com.zjezyy.entity.erp.TbManagement;
import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbReceivables_Eo;
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
import com.zjezyy.enums.StorageoptionType;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccOrderHistoryMapper;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.mapper.b2b.MccOrderProductMapper;
import com.zjezyy.mapper.b2b.MccPayResultMapper;
import com.zjezyy.mapper.erp.TbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbCustomerMapper;
import com.zjezyy.mapper.erp.TbLisenceCustomerMapper;
import com.zjezyy.mapper.erp.TbManagementMapper;
import com.zjezyy.mapper.erp.TbMccOrderMapper;
import com.zjezyy.mapper.erp.TbMccOrderProductMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.mapper.erp.TbReceivablesMapper;
import com.zjezyy.mapper.erp.TbSalesNoticeMapper;
import com.zjezyy.mapper.erp.TbSalesNoticeSMapper;
import com.zjezyy.mapper.erp.TbSalesOrderMapper;
import com.zjezyy.mapper.erp.TbSalesOrderSMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.service.CustomerService;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.ProductService;
import com.zjezyy.service.SettingService;
import com.zjezyy.service.SystemService;
import com.zjezyy.utils.DateUtils;
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
	TbLisenceCustomerMapper tbLisenceCustomerMapper;
	
	@Autowired
	MccOrderHistoryMapper mccOrderHistoryMapper;
	
	@Autowired
	MccPayResultMapper mccPayResultMapper;
	
	@Autowired
	SettingService settingServiceImpl;
	
	@Autowired
	TbReceivablesMapper tbReceivablesMapper;
	
	@Autowired
	TbManagementMapper tbManagementMapper;
	
	@Autowired
	AuthorityService authorityServiceImpl;
	

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
	
	@Value("${erp.customer.locked.canbuy}")
	private String customer_locked_canbuy;
	
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
	public void checkOrderPlaceParam(String oc_order_id) throws RuntimeException {
		if (oc_order_id == null || "".equals(oc_order_id)) {
			throw new BusinessException(ExceptionEnum.B2B_ORDER_ID_LACK);
		}

	}

	//同步处理 一次只能通过一个
	@Transactional
	@Override
	public synchronized void orderPlace(int order_id, int itypeid) throws RuntimeException {
		// 1、复制b2b订单到erp的接口表
		Integer impid = makeMccOrderToTbMccOrder(order_id,itypeid);

		// 2、根据税率 、是否冷藏、是否冷冻、是否麻黄碱、是否特殊、不同生成 销售订单
		Map<TbSalesOrder,List<TbSalesOrderS>> map=
		makeTbMccOrderToTbSalesOrder(impid, itypeid);
		
		// 3、根据 销售订单生成销售开票
		makeTbSalesOrderToTbSalesNotice(map);

		// 4、检查销售开票结果是否合法   如果不合法报异常
		//TODO
	}

	@Transactional
	@Override
	public Integer makeMccOrderToTbMccOrder(int order_id,int itypeid) throws RuntimeException {
		MccOrder mccOrder = mccOrderMapper.getOne(order_id);

		Integer customer_id = mccOrder.getCustomer_id();
		TbCustomer tbCustomer = customerServiceimpl.getTbCustomerByMccCustomerId(customer_id);

		//0、检查b2b订单状态    现金模式 只有状态为0的单据可以传递进来   b2b新增订单时，默认初始的状态为0
		if(itypeid==BusinessInterfaceType.B2BToERPOnLine.getCode()) 
			if(!String.valueOf(mccOrder.getOrder_status_id()).equals(settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_INIT_STATUS)))
				throw new BusinessException(ExceptionEnum.B2B_ORDER_IS_NOT_INIT_STATUS);
		
		
		//1、校验客户信息是否合法
		Map<String ,Boolean> map=new HashMap<>();
		checkCustomerRight(tbCustomer,itypeid,map);
		
		//2、现款不采用信用审核,否则会很怪异
		if(itypeid!=BusinessInterfaceType.B2BToERPOnLine.getCode())
		checkCustomerCredit( tbCustomer, true);
		
		// 主表保存到接口表
		TbMccOrder tbMccOrder=new TbMccOrder(mccOrder,tbCustomer.getIcustomerid(), tbCustomer.getVccustomername());
		tbMccOrderMapper.insert(tbMccOrder);
		Integer impid = tbMccOrder.getImpid();

		List<MccOrderProduct> list = mccOrderProductMapper.getListByOrderId(tbMccOrder.getMcc_order_id());
		for (MccOrderProduct mccOrderProduct : list) {
			Integer product_id = mccOrderProduct.getProduct_id();
			/*String productname=new StringBuilder().append(mccOrderProduct.getName())
					.append(" ")
					.append(mccOrderProduct.getModel())
					.toString();*/
					
					//mccOrderProduct.getName()+" "+mccOrderProduct.getModel();
			
			TbProductinfo_Eo tbProductinfo_Eo = productServiceImpl.getTbProductinfoEoByMccProductId(product_id);
			//判断商品价格是否和订单价格一致
			BigDecimal order_product_price=mccOrderProduct.getPrice();
			//根据系统设定的价格模式，获取价格
			//BigDecimal erp_product_price=productServiceImpl.getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo);
			BigDecimal erp_product_price=productServiceImpl.getERPProductPrice( tbProductinfo_Eo, tbCustomer);
			if(order_product_price.compareTo(erp_product_price)!=0) {
				throw new BusinessException(mccOrderProduct.toString(),ExceptionEnum.ERP_B2B_ORDER_PRICE_NOT_EQ);
			}
			//3、商品信息相关校验
			Map<String ,Boolean> map_p=new HashMap<>();
			map_p.put("erp_flagcold", false);
			map_p.put("erp_flagfreezing", false);
			map_p.put("flagzzrs", false);
			map_p.put("erp_flagephedrine", false);
			map_p.put("flagnotcash", false);
			map_p.put("erp_flagspecial", false);
			checkProduct(tbProductinfo_Eo,map_p);
			//现金模式  不允许非现金的药品交易 或者含有麻黄碱  也不能现金交易
			if(itypeid==BusinessInterfaceType.B2BToERPOnLine.getCode())
				if(map_p.get("flagnotcash")||map_p.get("erp_flagephedrine"))
			        throw new BusinessException(mccOrderProduct.toString(),ExceptionEnum.ERP_PRODUCT_NO_CASH_SALE);
			//终止妊娠药品集合许可证效期
			if(map_p.get("flagzzrs") && map.get("_bZZRSOver"))
				throw new BusinessException(mccOrderProduct.toString(),ExceptionEnum.ERP_PRODUCT_ZZRSOVER);
			
			//麻黄碱或者特殊药品 冷藏 冷冻  不能和其他药品开在一起 
			//if(map_p.get("erp_flagephedrine"))
				char erp_flagcold=map_p.get("erp_flagcold")?'Y':'N';
				char erp_flagfreezing=map_p.get("erp_flagfreezing")?'Y':'N';
				char erp_flagephedrine=map_p.get("erp_flagephedrine")?'Y':'N';
				char erp_flagspecial=map_p.get("erp_flagspecial")?'Y':'N';
			

			TbMccOrderProduct tbMccOrderProduct=new TbMccOrderProduct(mccOrderProduct,impid,
					tbProductinfo_Eo.getIproductid(), tbProductinfo_Eo.getNumsaletaxrate(),tbProductinfo_Eo.getNumpurchasetaxrate(),
					erp_flagcold, erp_flagfreezing, erp_flagephedrine, erp_flagspecial);
			
			
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
		//List<BigDecimal> taxRateList = tbMccOrderProductMapper.getSaleTaxRateList(impid);
		List<String> typeList = tbMccOrderProductMapper.getSaleTypeList(impid);
		
		// 初始化存放分组信息的map
		//Map<BigDecimal, List<TbMccOrderProduct>> map = makeTbMccOrderProductGroupByTaxRate(taxRateList,
		//		tbMccOrderProductList);
		Map<String, List<TbMccOrderProduct>> map = makeTbMccOrderProductGroupByType(typeList,
				tbMccOrderProductList);

		// 保存主表 保存细表
		// 创建销售订单主表数据
		Set<Entry<String, List<TbMccOrderProduct>>> set = map.entrySet();
		for (Entry<String, List<TbMccOrderProduct>> entry : set) {
			//销售订单主单
			String vcbillcode =systemServiceImpl.genBillCodeForTransactional(salesOrderBillPrefix);
			if("".equals(vcbillcode))
				throw new BusinessException(ExceptionEnum.ERP_ORDER_CODE_CREATE_HTTP_FAIL);//单据创建失败
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
	 * 同步处理  避免轮询和异步处理时  同时处理 
	 * 安全起见 这里还是不要事务回滚了  
	 */

	@Override
	public synchronized void payResultQuery(PayService payService, String ordercode) throws RuntimeException {
		
		//检验b2b单据状态是否为待付款状态
		//MccOrder mccOrder =mccOrderMapper.getOne(order_id);
		MccOrder mccOrder =mccOrderMapper.getOneByCode(ordercode);
		String order_unpay_status=settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_UNPAY_STATUS);
		
		if(mccOrder==null ||Integer.valueOf( order_unpay_status)!=mccOrder.getOrder_status_id())
			return ;//如果是空订单或者单据状态不为等待付款,就返回
			
			MccPayResult mccPayResult = payService.payResultQuery(ordercode);

			if(mccPayResult!=null && PayResult.SUCCESS.getCode().equals(mccPayResult.getTradestatus())) {//付款成功
				
				//1更新b2b订单为已付款  这里必须及时 涉及到用户端付款之后页面检查是否付款的字段
				 updateMccOrderPayedAndCreateResult(mccOrder.getOrder_id(), mccPayResult);
				
				//2、erp订单状态 扮演审核的角色  调用ERP存储过程
				 List<TbSalesNotice> list=tbSalesNoticeMapper.getListByMccOrderID(mccOrder.getOrder_id(),BusinessInterfaceType.B2BToERPOnLine.getCode());
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
		
		//获取头信息的token值
		Map<String,String > map_head=new HashMap<>();
		authorityServiceImpl.credateHeader(map_head);
		
		Result res=HttpClientUtil.postMap(salesnoticeApprovalUrl,map,map_head );
		JSONObject jsonObject=null;
		if(res!=null && res.getStatus()==0) {
			jsonObject = JSON.parseObject(res.getMsg());
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
			throw new BusinessException(res.getMsg(),ExceptionEnum.ERP_SALESNOTICE_HTTP_APPROVAL_FAIL);
		}
		
	}

	@Override
	public boolean checkCustomerRight(TbCustomer tbCustomer,int itypeid,Map<String,Boolean> map) throws RuntimeException {
		//客户是否锁定  
	    char flaglock=tbCustomer.getFlaglock();
	   
	    if(flaglock=='Y') {
	    	 //业务类型是4 并且 客户锁定可以买
		    if("N".equals(customer_locked_canbuy) ) {
		    	throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_LOCKED);
		    }else {
		    	if(itypeid!=BusinessInterfaceType.B2BToERPOnLine.getCode()) {
		    		throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_LOCKED);
		    	}
		    }
	    }
	    
	    
	    //客户证照信息是否过期
	    boolean _bSpecialManOver=false;
	    boolean  _bZZRSOver = false;
	    String sErrMsg="";
	    String sWaingMsg="";
	    List<TbLisenceCustomer_Eo> list=tbLisenceCustomerMapper.getListByIcustomerid(tbCustomer.getIcustomerid());
	   
	    try {
	    for(TbLisenceCustomer_Eo lisenceeo:list) {
	    	
	    	//获取特殊药品委托人效期是否过期
	    	if ( 'Y'==lisenceeo.getFlagspecial())
            {
                if (lisenceeo.getDtend()==null||"".equals(lisenceeo.getDtend()))
                {
                    _bSpecialManOver = true;
                    continue;
                }
                String dtend=lisenceeo.getDtend();
    	    	String dtnow = DateUtils.dateFormat(DateUtils.dateAdd(new Date(), -1, false), DateUtils.DATE_PATTERN);
				int i=DateUtils.dateCompare(DateUtils.dateParse(dtend,DateUtils.DATE_PATTERN),DateUtils.dateParse(dtnow,DateUtils.DATE_PATTERN));
    	    	
                if (lisenceeo.getDtend()!=null && !"".equals(lisenceeo.getDtend()) && i<1)
                {
                    _bSpecialManOver = true;
                    continue;
                }
            }
            //终止妊娠是否过期
            if ( 'Y'==lisenceeo.getFlagzzrs())
            {
                if (lisenceeo.getDtend()==null||"".equals(lisenceeo.getDtend()))
                {
                    _bZZRSOver = true;
                    continue;
                }
                String dtend=lisenceeo.getDtend();
    	    	String dtnow = DateUtils.dateFormat(DateUtils.dateAdd(new Date(), -1, false), DateUtils.DATE_PATTERN);
				int i=DateUtils.dateCompare(DateUtils.dateParse(dtend,DateUtils.DATE_PATTERN),DateUtils.dateParse(dtnow,DateUtils.DATE_PATTERN));
                if (lisenceeo.getDtend()!=null && !"".equals(lisenceeo.getDtend()) && i<1)
                {
                    _bZZRSOver = true;
                    continue;
                }
            }
            //其他证照类型判断
            if ('Y'==lisenceeo.getFlagusefullife())//效期判断标记
            {                    
                if (lisenceeo.getDtend()==null || "".equals(lisenceeo.getDtend()))
                {                       
                    continue;
                }
                Date dtEnd =DateUtils.dateParse(lisenceeo.getDtend(), DateUtils.DATE_PATTERN) ;//ConvertEx.ToDateTimeEx(row[Models.tb_Lisence_Customer.DTEND]);
            
/*                if ( dtEnd == DateTime.MinValue)
                {
                    continue;
                }*/
                
                String dtend=lisenceeo.getDtend();
    	    	String dtnow = DateUtils.dateFormat(DateUtils.dateAdd(new Date(), -1, false), DateUtils.DATE_PATTERN);
				int i=DateUtils.dateCompare(DateUtils.dateParse(dtend,DateUtils.DATE_PATTERN),DateUtils.dateParse(dtnow,DateUtils.DATE_PATTERN));
				
                if (i<1)
                {
                    sErrMsg += "当前客户的" + lisenceeo.getVclisencetypename() + "过期，其期限是" + lisenceeo.getDtend() + "\r\n";
                    continue;
                }
                String dtnow7 = DateUtils.dateFormat(DateUtils.dateAdd(new Date(), -7, false), DateUtils.DATE_PATTERN);
                int j=DateUtils.dateCompare(DateUtils.dateParse(dtend,DateUtils.DATE_PATTERN),DateUtils.dateParse(dtnow7,DateUtils.DATE_PATTERN));
                if (j<1)
                {
                    sWaingMsg += "当前客户的" +lisenceeo.getVclisencetypename() + "将在7天内过期，其期限是" + lisenceeo.getDtend() + "\r\n";
                    continue;
                }
            }
			
	    }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    
	    if(!"".equals(sWaingMsg)) {
	    	throw new BusinessException(sWaingMsg,-9997);
	    }
	    	
	    
	    if(!"".equals(sErrMsg)) {
	    	throw new BusinessException(sWaingMsg,-9996);
	    }
	    	
	    map.put("_bSpecialManOver", _bSpecialManOver);
	    map.put("_bZZRSOver", _bZZRSOver);
		return true;
	}

	@Override
	public boolean checkCustomerCredit(TbCustomer tbCustomer,boolean bWriteApprovalLog) throws RuntimeException {
		 String sErrMsg="";
		//信用判断和客户锁定判断
		 
		//1判断信用天数 最后欠款时间到现在
		Integer numcreditdays=tbCustomer.getNumcreditdays();
		//最早欠款时间
		Integer days=tbReceivablesMapper.getEarliest(tbCustomer.getIcustomerid());
		if(days>numcreditdays) {
            sErrMsg = tbCustomer.getVccustomername() + " 信用天数受限！限定天数是" + numcreditdays + ",未收款天数是" + days;
		}
		
		//2判断信用额度 期初应收加上当前应收
		TbReceivables_Eo tbReceivables_Eo =tbReceivablesMapper.getSummer(tbCustomer.getIcustomerid());
		if(tbReceivables_Eo.getNummoney().compareTo(tbReceivables_Eo.getNumcreditmoney())==1) {
			//欠款额度大于授信额度
			 if ("".equals(sErrMsg))
                 sErrMsg += tbCustomer.getVccustomername();
             else
                 sErrMsg += "\r\n ";
             sErrMsg += " 信用额度受限！限定额度是" + tbReceivables_Eo.getNumcreditmoney().toString() + ",目前未收款金额" + tbReceivables_Eo.getNummoney().toString();
		}
		
		if(!"".equals(sErrMsg))
			throw new BusinessException(sErrMsg,-9995);
		
            /*
             * 控制方法
             * 一类（县级医院）
                1、应收账款90天内（含90天）收回的，不控制
                2、应收账款91天至120天（含120天）收回的，信用额度内不控制，超信用额度的由销售总监和财务经理审批
                3、应收账款121天至150天（含150天）收回的，由销售总监和财务经理审批
                4、应收账款151天以上收回的，不能开票。
             * 一类 (中医院)
                1、应收账款120天内（含120天）收回的不控制
                2、应收账款121天至150天（含150天）收回的，由销售总监和财务经理审批
                3、应收账款151天以上收回的，不能开票。
             * 二类（乡镇医疗机构）
                1、应收账款60天内（含60天）收回的不控制
                2、应收账款61天至120天（含120天）收回的，信用额度内不控制，超信用额度的由销售总监和财务经理审批
                3、应收账款121天至150天（含150天）收回的，由销售总监和财务经理审批
                4、应收账款151天以上收回的，不能开票。
             * 三类（医药公司）
                1、应收账款75天内（含75天）收回的，信用额度内不控制，超信用额度的由销售总监和财务经理审批
                2、应收账款76天至90天（含90天）收回的，信用额度内不控制，超信用额度的由销售总监、财务经理、常务副总经理审批
                3、应收账款91天以上的，所有销售清单都需销售总监和财务经理审批。
             * 第四类（第三终端客户市场）
                1、以上单位全部为现款单位，所有销售清单都需销售总监和财务经理审批。
             * */

		/*
                //获取客户所属分类 明天待续
                Integer ICREDITTYPE =tbCustomer.getIcredittype();//客户信用类型
                int NUMCREDITDAYS =tbCustomer.getNumcreditdays();//客户信用考核天数
                BigDecimal NUMCREDITMONEY =tbCustomer.getNumcreditmoney();//客户信用额度
                Integer NUMCREDITDAYS1 =tbCustomer.getNumcreditdays1();//信用审核天数1
                Integer NUMCREDITDAYS2 =tbCustomer.getNumcreditdays2();//信用审核天数2
                Integer NUMCREDITDAYS3 =tbCustomer.getNumcreditdays3();//信用审核天数3
                Integer NOWCREDITDAYS = days;//实际欠款天数
                BigDecimal NUMMONEY = tbReceivables_Eo.getNummoney();//实际欠款金额

                string _MoneyMsg = "单据金额:"
                + _BLL.CurrentBusiness.Tables[tb_SalesNotice.__TableName].Rows[0][tb_SalesNotice.NUMMONEY].ToString(); 
                if (ICREDITTYPE == "1")//一类（县级医院）
                {
                    if (NOWCREDITDAYS > NUMCREDITDAYS1 && NOWCREDITDAYS <= NUMCREDITDAYS2 && NUMMONEY > NUMCREDITMONEY)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核" ;
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg+")"+ oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "4";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS2 && NOWCREDITDAYS <= NUMCREDITDAYS3)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "6";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS3)
                    {
                        Msg.Warning("当前客户是一类（县级医院），欠款天数:" + NOWCREDITDAYS.ToString() + "， 限定天数是" + NUMCREDITDAYS3.ToString() + ",不能开票！");
                        return false;
                    }
                }
                //一类 (中医院)
                if (ICREDITTYPE == "2")//
                {
                    if (NOWCREDITDAYS > NUMCREDITDAYS1 && NOWCREDITDAYS <= NUMCREDITDAYS3)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "7";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS3)
                    {
                        Msg.Warning("当前客户是一类 (中医院),欠款天数:" + NOWCREDITDAYS.ToString() + "， 限定天数是" + NUMCREDITDAYS2.ToString() + ",不能开票！");
                        return false;
                    }
                }

                if (ICREDITTYPE == "3")//二类（乡镇医疗机构）
                {
                    if (NOWCREDITDAYS > NUMCREDITDAYS1 && NOWCREDITDAYS <= NUMCREDITDAYS2 && NUMMONEY > NUMCREDITMONEY)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "8";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS2 && NOWCREDITDAYS <= NUMCREDITDAYS3)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "9";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS3)
                    {
                        Msg.Warning("当前客户是二类（乡镇医疗机构），欠款天数:" + NOWCREDITDAYS.ToString() + "， 限定天数是" + NUMCREDITDAYS3.ToString() + ",不能开票！");
                        return false;
                    }
                }

                if (ICREDITTYPE == "4")//三类（医药公司）
                {
                    if (NOWCREDITDAYS > NUMCREDITDAYS && NOWCREDITDAYS <= NUMCREDITDAYS1 && NUMMONEY > NUMCREDITMONEY)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "10";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS1 && NOWCREDITDAYS <= NUMCREDITDAYS2 && NUMMONEY > NUMCREDITMONEY)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监 常务副总经理";
                            drmsg1["parmvalue"] = "11";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                    if (NOWCREDITDAYS > NUMCREDITDAYS2)
                    {
                        if (bWriteApprovalLog)
                        {
                            DataRow drmsg1 = _dtMsg.NewRow();
                            drmsg1["vcproductcode"] = "信用审核";
                            drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ")" + oj.ErrMsg;
                            drmsg1["vcappuser"] = "财务经理 销售总监";
                            drmsg1["parmvalue"] = "12";
                            _dtMsg.Rows.Add(drmsg1);
                        }
                    }
                }

                if (ICREDITTYPE == "5")//第四类（第三终端客户市场）
                {
                    if (bWriteApprovalLog)
                    {
                        DataRow drmsg1 = _dtMsg.NewRow();
                        drmsg1["vcproductcode"] = "信用审核";
                        drmsg1["vccontent"] = "信用审核(" + _MoneyMsg + ")当前客户是现金客户 需要财务经理或销售总监审批！";
                        drmsg1["vcappcontent"] = "信用审核(" + _MoneyMsg + ") 当前客户是现金客户 需要财务经理或销售总监审批！";
                        drmsg1["vcappuser"] = "财务经理 销售总监";
                        drmsg1["parmvalue"] = "13";
                        _dtMsg.Rows.Add(drmsg1);
                    }
                }
            
            //
            if (bWriteApprovalLog)//需要写入日志，其实就是审核时调用本函数
            {
                if (Msg.AskQuestion("当前客户存在信用问题\r\n " + oj.ErrMsg + " \r\n还要继续审核吗？") == false)
                {
                    return false;
                }
            }
            else//不需要写入日志，其实就是新单时调用本函数
            {
                if (Msg.AskQuestion("当前客户存在信用问题\r\n " + oj.ErrMsg + " \r\n还要继续操作吗？") == false)
                {
                    return false;
                }
            }*/
        
        return true;
	}

	@Override
	public void checkProduct(TbProductinfo_Eo tbProductinfo_Eo, Map map_p)
			throws RuntimeException {
		//获取商品经营分类
		char flagcode=tbProductinfo_Eo.getFlagcold();
		char flagfreezing=tbProductinfo_Eo.getFlagfreezing();
		Integer istorageoptionid= tbProductinfo_Eo.getIstorageoptionid();
		
		if(flagfreezing!='Y') {
			System.out.println(flagcode=='Y');
			System.out.println(istorageoptionid==StorageoptionType.COLD.getCode());
			if( flagcode=='Y'||istorageoptionid==StorageoptionType.COLD.getCode())
			    map_p.put("erp_flagcold", true);
			if(istorageoptionid==StorageoptionType.FREEZING.getCode())
				map_p.put("erp_flagfreezing", true);
		}else {
			map_p.put("erp_flagfreezing", true);
		}
		
		
		Integer imanagementid=tbProductinfo_Eo.getImanagementid();
		TbManagement tbManagement=tbManagementMapper.getOne(imanagementid);
		if(tbManagement!=null) {
			if(tbManagement.getFlagzzrs()=='Y')
				map_p.put("flagzzrs", true);
			if(tbManagement.getFlagephedrine()=='Y')
				map_p.put("erp_flagephedrine", true);
			if(tbManagement.getFlagnotcash()=='Y')
				map_p.put("flagnotcash", true);
			if(tbManagement.getFlagspecial()=='Y')
				map_p.put("erp_flagspecial", true);
		}
		
	}

	@Override
	public Map<String, List<TbMccOrderProduct>> makeTbMccOrderProductGroupByType(List<String> typeList,
			List<TbMccOrderProduct> tbMccOrderProductList) throws RuntimeException {
		
		Map<String, List<TbMccOrderProduct>> map = new HashMap<String, List<TbMccOrderProduct>>();
		for (String type : typeList) {
			List<TbMccOrderProduct> _tbMccOrderProductList = new ArrayList<TbMccOrderProduct>();
			map.put(type, _tbMccOrderProductList);
			for (TbMccOrderProduct tbMccOrderProduct : tbMccOrderProductList) {
				BigDecimal erp_numsaletaxrate = tbMccOrderProduct.getErp_numsaletaxrate();
				String[] arrType=type.split("-");
				BigDecimal type_=new BigDecimal(arrType[0]);
				if (type_.compareTo(erp_numsaletaxrate) == 0) {
					_tbMccOrderProductList.add(tbMccOrderProduct);
				}
			}
		}
		return map;
	}




	

	

}
