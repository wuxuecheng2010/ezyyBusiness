package com.zjezyy.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbProductPacking;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.enums.BusinessInterfaceType;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.enums.Payment;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.mapper.erp.TbProductPackingMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.ProductService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.business.LogUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Task_General {

	@Autowired
	ProductService productServiceImpl;

	@Autowired
	OrderService orderServiceImpl;
	
	@Autowired
	MccProductMapper mccProductMapper;
	
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	@Autowired
	TbProductPackingMapper tbProductPackingMapper;
	
	@Autowired
	SettingService settingServiceImpl;

	@Resource(name = "wxPayServiceImpl")
	PayService wxPayServiceImpl;
	
	@Autowired
	MccOrderMapper mccOrderMapper;
	
	
	@Resource(name = "aliPayServiceImpl")
	PayService aliPayServiceImpl;

	/*@Value("${erp.to.b2b.product.import.url}")
	private String productRemoteServiceUrl;*/
	
	@Value("${spring.profiles.active}")
	private String mode;


	// 1、低储信息、b2b价格是否维护 同步B2B上下架
	@Scheduled(initialDelay = 1000, fixedRate = 150000)
	public void lowStorage() throws Exception {
		
		
		// 获得当前类名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		// 获得当前方法名
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();

		// 1、查询b2b所有商品
		List<MccProduct> list = productServiceImpl.getAllMccProductID();
		// 2、遍历商品 是否为低储 如果低储下架商品
		for (MccProduct mccProduct : list) {
			try {
				productServiceImpl.doSynchronizeOnOff(mccProduct);
			} catch (Exception e) {
				LogUtil.logForB2BProduct(clazz, method, e, mccProduct);
			}

		}

	}

	// 2、同步B2B商品价格 如果发现ERP没有B2B价格集合 直接下架
	@Scheduled(initialDelay = 2000, fixedRate = 150000)
	public void b2bPrice() throws Exception {
		//测试模式不执行
		//if(!"dev".equals(mode)) {
					// 获得当前类名
					String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
					// 获得当前方法名
					String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			
					// 1、查询b2b所有商品
					List<MccProduct> list = productServiceImpl.getAllMccProductID();
			
					// 2、遍历商品 更新价格
					for (MccProduct mccProduct : list) {
						try {
							productServiceImpl.doSynchronizePrice(mccProduct);
						} catch (Exception e) {
							LogUtil.logForB2BProduct(clazz, method, e, mccProduct);
						}
					}
		//}
	}

	// 3、同步B2B商品信息 根据价格集合 有维护B2B商品价格体系的，同步到B2b来 上下架关系 由高低储和价格需信息来决定 此处只关心商品数据
	@Scheduled(initialDelay = 3000, fixedRate = 150000)
	public void b2bProduct() throws Exception {

		// 获得当前类名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		// 获得当前方法名
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();

		// 1、查询维护了B2B价格集合的商品 并且ERP商品信息表iydstate 为0或者null的商品信息
		
		List<TbProductinfo> list=new ArrayList<TbProductinfo>();
		List<TbProductinfo> list1 = productServiceImpl.getProductsForB2B();//加入到B2B集合的数据
		List<TbProductinfo> list2= productServiceImpl.getProductsForPM();//药店使用了ERP商品ID的数据
		list.addAll(list1);
		list.addAll(list2);
		// 2、修改商品信息表字段iydstate=1
		String into = "";
		for (TbProductinfo tbProductinfo : list) {
			try {
				productServiceImpl.doSynchronizeProductInfo(tbProductinfo.getIproductid(), 0, 0);
				//productServiceImpl.setTbProductinfoIydstate(tbProductinfo);
				into = String.format("ERP商品ID：%d修改iydstate状态为1，准备导入到B2B", tbProductinfo.getIproductid());
				log.info(into);
			} catch (Exception e) {
				LogUtil.logForERPProduct(clazz, method, e, tbProductinfo);
			}
			
			
		}

		/*// 3、调用http请求处理数据 php服务
		Result ress=HttpClientUtil.get(productRemoteServiceUrl,null);
		
		
		//String respStr = HttpClientUtil.get(productRemoteServiceUrl,null);
		if (ress != null)
			log.info(ress.getMsg());*/
	}
	
	
	//3.1、同步B2B的中包装信息
	@Scheduled(initialDelay = 300, fixedRate = 250000)
	public void b2bProductPacksize() throws Exception {
		//获取B2B商品列表
		List<MccProduct>list= mccProductMapper.getAll();
		for(MccProduct mccProduct : list) {
			if(mccProduct.getStatus()==1)
			try {
				Integer mcc_nummiddle= mccProduct.getNummiddle();
				Integer erpiproductid=mccProduct.getErpiproductid();
				//TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(erpiproductid);
				TbProductPacking tbProductPacking=tbProductPackingMapper.getOne(erpiproductid);
				Integer erp_nummiddle  =productServiceImpl.getProductNumMiddle(tbProductPacking);
				if(mcc_nummiddle ==null ||erp_nummiddle!=mcc_nummiddle) {
					mccProductMapper.setMccProductNumMiddle(mccProduct.getProduct_id(),erp_nummiddle);
					log.info("B2B商品中包装信息被更新，商品ID：{},规格:{} 中包装{}变更成{}",mccProduct.getProduct_id(),mccProduct.getModel(),mcc_nummiddle,erp_nummiddle);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("B2B商品信息的中包装更新出错，商品ID：{},错误信息：{}",mccProduct.getProduct_id(),e.getMessage());
			}
			
		}
		
		
	}

	// 4、同步B2B库存
	@Scheduled(initialDelay = 4000, fixedRate = 150000)
	public void b2bStock() throws Exception {

		// 获得当前类名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		// 获得当前方法名
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();

		// 1、获取B2B在售商品
		List<MccProduct> list = productServiceImpl.getAllB2BOnProduct();

		// 2、同步库存
		for (MccProduct mccProduct : list) {
			try {
				productServiceImpl.doSynchronizeStock(mccProduct);
			} catch (Exception e) {
				LogUtil.logForB2BProduct(clazz, method, e, mccProduct);
			}
		}

	}

	
	
	// 6、定时检查支付状态 超时未付款处理
		@Scheduled(initialDelay = 100, fixedRate = 3000)
		public void unpayExpireOrderStatusQuery() throws Exception {
			//log.info("6、定时检查支付状态 及相应处理");
			int order_expire_time=Integer.valueOf(settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_EXPIRE_TIME));
			int order_unpay_status=Integer.valueOf(settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_UNPAY_STATUS));
			// 获取未付款的订单数据   //超过预期时间的1分钟  我们才对他处理，此时用户的二维码已经失效
			List<MccOrder> list = orderServiceImpl.getUnPayExpireMccOrderList(order_expire_time+1);
			for (MccOrder mccOrder : list) {
				int order_status_id = mccOrder.getOrder_status_id();
				int order_id = mccOrder.getOrder_id();
				try {
					if (order_status_id == order_unpay_status) {
						orderServiceImpl.payExpired(order_id);
					}
				} catch (Exception e) {
					log.error(String.format("B2B单据ID：%s,付款查询失败，错误信息：%s", order_id,e.getMessage()));
				}

			}

		}
	
	
	
	//7、根据客户集合信息
	@Scheduled(initialDelay = 150, fixedRate = 3000)
	public void customerkind() throws Exception {
		
		productServiceImpl.doSynchronizeCustomerKind();

	}
	
	//8、根据客户集合  同步商品价格体系
	@Scheduled(initialDelay = 200, fixedRate = 3000)
	public void customerkindprice() throws Exception {
		
		productServiceImpl.doSynchronizeCustomerKindPrice();

	}
		
	
	//9、系统内或者授信客户单据传递ERP操作
	
	//@Scheduled(initialDelay = 300, fixedRate = 6000)
	public void creditOrderToERP() throws Exception {
		
		//1、获取授信客户订单列表  
				List<MccOrder> list=mccOrderMapper.getCreditOrder();
		//2、保存到ERP	
		        for (MccOrder mccOrder:list) {
		        	try {
		        		orderServiceImpl.orderPlaceCredit(mccOrder,BusinessInterfaceType.B2BToERPUnderLine.getCode());
		        	}catch(Exception e ) {
		        		log.error(String.format("B2B订单ID：%d,往ERP传输时，遇到以下问题:%s", mccOrder.getOrder_id(),e.getMessage()));
		        	}
		        	
		        	
		        }
		        
		        

	}
	
	
	//public void beat

}