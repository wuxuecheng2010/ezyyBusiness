package com.zjezyy.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.enums.BusinessType;
import com.zjezyy.enums.Payment;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.DataUtil;
import com.zjezyy.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AliPayServiceImpl implements PayService {
	
	@Autowired
    SettingService settingServiceImpl;
	
	@Autowired
	OrderService orderServiceImpl;
	
	@Autowired
	MccOrderMapper mccOrderMapper;



	



	@Override
	public MccPayResult payResultQuery(String ordercode) throws RuntimeException {
		
		String code=Payment.QRCODE_ALIPAY.getCode();
		MccPayResult payinfo=null;
    	String tradeStatus="";
    	if(!"".equals(ordercode) && ordercode!=null){

    		AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder()
    				.setAlipayPublicKey(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_alipay_public_key"))
    				.setAppid(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_app_id"))
    				.setCharset(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_charset"))
    				.setGatewayUrl(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_gatewayUrl"))
    				.setPrivateKey(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_merchant_private_key"))
    				.setSignType(settingServiceImpl.getMccSettingValue(code, "qrcode_alipay_sign_type"))
    				.build();

    		// (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
            AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
            		.setOutTradeNo(ordercode);
    		AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
    		
    		switch (result.getTradeStatus()) {
    			case SUCCESS:
    				//log.info("查询返回该订单支付成功: )");
    				payinfo=new MccPayResult();
    				AlipayTradeQueryResponse resp = result.getResponse();
    				//tradeStatus=resp.getTradeStatus();
    				MccOrder mccOrder=mccOrderMapper.getOneByCode(ordercode);
	                payinfo.setOrder_id(mccOrder.getOrder_id());
	                payinfo.setOrdercode(ordercode);
	                payinfo.setFee(resp.getBuyerPayAmount());
	                payinfo.setPaydate(DateUtils.formatDateToDefaultStr(resp.getSendPayDate()));
	                payinfo.setPayment(Payment.QRCODE_ALIPAY.getCode());
	                payinfo.setPlordercode(resp.getTradeNo());
	                payinfo.setBusstype(BusinessType.SALE.getMsg());
	                payinfo.setTradestatus("SUCCESS");
    				break;

    			case FAILED:
    				log.error("查询返回该订单支付失败!!!");
    				break;

    			case UNKNOWN:
    				log.error("系统异常，订单支付状态未知!!!");
    				break;

    			default:
    				log.error("不支持的交易状态，交易返回异常!!!");
    				break;
    		}
 
    	}
    	return payinfo;
    
	}


	
	@Override
	public void notify(HttpServletRequest request,HttpServletResponse response,Payment payment) throws RuntimeException {

		//System.err.println("收到支付宝回调");
		log.info("收到支付宝回调");
		// 获取支付宝POST过来反馈信息
		try {
			Map<String, String> params= DataUtil.getParameterMap(request);
			
			for (Map.Entry<String, String> entry : params.entrySet()) {
				//System.err.println(entry.getKey() + " = " + entry.getValue());
				log.info(entry.getKey() + " = " + entry.getValue());
			}
			
			String code=payment.getCode();
			String alipayPublicKey=settingServiceImpl.getMccSettingValue(code, code+"_alipay_public_key");
			String charset=settingServiceImpl.getMccSettingValue(code, code+"_charset");
			String signType=settingServiceImpl.getMccSettingValue(code, code+"_sign_type");

			boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);

			if (verify_result) {// 验证成功
				// TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
				String ordercode=params.get("out_trade_no");
				this.payResultQuery(ordercode);
				//orderServiceImpl.payResultQuery(aliPayServiceImpl, Integer.valueOf(order_id));
				/*
				 * 字段对应的意思 在支付宝文档中对
				 * https://docs.open.alipay.com/270/105902/
				 * body = 订单描述
						subject = 订单标题
						sign_type = RSA2
						auth_app_id = 2018070260533140
						notify_type = trade_status_sync
						out_trade_no = 092623564015379
						point_amount = 0.00
						version = 1.0
						fund_bill_list = [{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}]
						passback_params = passback_params
						buyer_id = 2088302257843420
						total_amount = 0.01
						trade_no = 2018092622001443420535911693
						notify_time = 2018-09-26 23:56:50
						charset = UTF-8
						invoice_amount = 0.01
						trade_status = TRADE_SUCCESS
						gmt_payment = 2018-09-26 23:56:50
						sign = NmYuT6lnoL7AIkK1c6oDqWhcIvaaAYGq1mEnWL3qqvyoUznccm5LABesSM1ciS0RyXmoL3HX2HDB2+b9FPqgwoFrZ6QQk0h6cJWhGD3nRJoMKK3mBwW9croDcdMhpupIrqpNcOJNDqdgA/89JpRjPy0Z6RYafIAoo0zxG56CYguR0yQw9Mfr9O3rlRqyN0IjqFDC5jh8Rox72j1PP6h2AyvDqgccTwE8sOS1caukhYMkmKwjSsHJRRo2Z3lQyeTDggH4drGowz/MU8Dfnf4jTqTnfcRh77Ya/LuNYuV8mPRG5Ay1H3hKX9EC9uXLx/CvM0ttY++Q1YVojMjXQWS9Xg==
						gmt_create = 2018-09-26 23:56:45
						buyer_pay_amount = 0.01
						receipt_amount = 0.01
						app_id = 2018070260533140
						seller_id = 2088131608520239
						notify_id = 2018092600222235650043420521185261*/
				
				//System.err.println("notify_url 验证成功succcess");
				log.info("notify_url 验证成功succcess");
				return;
			} else {
				//System.err.println("notify_url 验证失败");
				log.info("notify_url 验证失败");
				return;
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	
		
	}

}
