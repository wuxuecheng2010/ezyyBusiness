package com.zjezyy.job;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.enums.Payment;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.ProductService;
import com.zjezyy.service.SettingService;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class Task_Fast {

	@Autowired
	ProductService productServiceImpl;

	@Autowired
	OrderService orderServiceImpl;
	
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


	

	// 5、定时检查支付状态 及相应处理
	@Scheduled(initialDelay = 50, fixedRate = 3000)
	public void unpayOrderStatusQuery() throws Exception {
		int order_expire_time=Integer.valueOf(settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_EXPIRE_TIME));
		int order_unpay_status=Integer.valueOf(settingServiceImpl.getEzyySettingValue(EzyySettingKey.ORDER_UNPAY_STATUS));
		// 获取未付款的订单数据
		List<MccOrder> list = orderServiceImpl.getUnPayMccOrderList(order_expire_time);
		for (MccOrder mccOrder : list) {
			String payment_code = mccOrder.getPayment_code();
			int order_status_id = mccOrder.getOrder_status_id();
			//int order_id = mccOrder.getOrder_id();
			String ordercode = mccOrder.getOrdercode();
			try {
				if (order_status_id == order_unpay_status) {

					if (Payment.QRCODE_WXPAY.getCode().equals(payment_code))
						orderServiceImpl.payResultQuery(wxPayServiceImpl, ordercode);
					if (Payment.WXPAY_WAP.getCode().equals(payment_code))
						orderServiceImpl.payResultQuery(wxPayServiceImpl, ordercode);
					
					if (Payment.QRCODE_ALIPAY.getCode().equals(payment_code))
						orderServiceImpl.payResultQuery(aliPayServiceImpl, ordercode);
					if (Payment.ALIPAY_WAP.getCode().equals(payment_code))
						orderServiceImpl.payResultQuery(aliPayServiceImpl, ordercode);

				} 
			} catch (Exception e) {
				
				log.error(String.format("B2B单据号：%s,付款查询失败，错误信息：%s", ordercode,e.getMessage()));
			}

		}

	}

	


}
