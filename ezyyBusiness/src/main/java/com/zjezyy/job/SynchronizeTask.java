package com.zjezyy.job;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.enums.Payment;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.ProductService;
import com.zjezyy.utils.HttpClientUtil;
import com.zjezyy.utils.business.LogUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SynchronizeTask {

	@Autowired
	ProductService productServiceImpl;

	@Autowired
	OrderService orderServiceImpl;

	@Resource(name = "wxPayServiceImpl")
	PayService wxPayServiceImpl;

	@Value("${erp.to.b2b.product.import.url}")
	private String productRemoteServiceUrl;

	@Value("${b2b.order.expire.time}")
	private int order_expire_time;// 订单从下单到付款的时间限制

	@Value("${b2b.order.unpay.status}")
	private int order_unpay_status;// 订单未付款的状态码

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
	}

	// 3、同步B2B商品信息 根据价格集合 有维护B2B商品价格体系的，同步到B2b来 上下架关系 由高低储和价格需信息来决定 此处只关心商品数据
	@Scheduled(initialDelay = 3000, fixedRate = 150000)
	public void b2bProduct() throws Exception {

		// 获得当前类名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		// 获得当前方法名
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();

		// 1、查询维护了B2B价格集合的商品 并且ERP商品信息表iydstate 为0或者null的商品信息
		List<TbProductinfo> list = productServiceImpl.getProductsForB2B();

		// 2、修改商品信息表字段iydstate=1
		String into = "";
		for (TbProductinfo tbProductinfo : list) {
			productServiceImpl.setTbProductinfoIydstate(tbProductinfo);
			into = String.format("ERP商品ID：%d修改iydstate状态为1，准备自动对接到B2B", tbProductinfo.getIproductid());
			log.info(into);
		}

		// 3、调用http请求处理数据 php服务
		String respStr = HttpClientUtil.get(productRemoteServiceUrl);
		if (respStr != null && "".equals(respStr))
			log.info(respStr);
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

	// 5、定时检查支付状态 及相应处理
	@Scheduled(initialDelay = 100, fixedRate = 3000)
	public void unpayOrderStatusQuery() throws Exception {

		// 获取未付款的订单数据
		List<MccOrder> list = orderServiceImpl.getUnPayMccOrderList(order_expire_time);
		for (MccOrder mccOrder : list) {
			String payment_code = mccOrder.getPayment_code();
			int order_status_id = mccOrder.getOrder_status_id();
			int order_id = mccOrder.getOrder_id();
			if (order_status_id == order_unpay_status) {

				if (Payment.QRCODE_WXPAY.getCode().equals(payment_code))
					orderServiceImpl.payResultQuery(wxPayServiceImpl, order_id);

			}

		}

	}

	// 6、定时检查支付状态 及相应处理
	@Scheduled(initialDelay = 50, fixedRate = 3000)
	public void unpayExpireOrderStatusQuery() throws Exception {

		// 获取未付款的订单数据
		List<MccOrder> list = orderServiceImpl.getUnPayExpireMccOrderList(order_expire_time);
		for (MccOrder mccOrder : list) {
			int order_status_id = mccOrder.getOrder_status_id();
			int order_id = mccOrder.getOrder_id();
			if (order_status_id == order_unpay_status) {
				
				orderServiceImpl.payExpired(order_id);

			}

		}

	}

}
