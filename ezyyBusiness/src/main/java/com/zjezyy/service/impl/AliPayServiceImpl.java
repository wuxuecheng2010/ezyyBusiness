package com.zjezyy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.enums.BusinessType;
import com.zjezyy.enums.Payment;
import com.zjezyy.service.PayService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AliPayServiceImpl implements PayService {
	
	@Autowired
    SettingService settingServiceImpl;


	@Override
	public MccPayResult payResultQuery(Integer order_id) throws RuntimeException {
		
		String code=Payment.QRCODE_ALIPAY.getCode();
		MccPayResult payinfo=null;
    	String tradeStatus="";
    	if(!"".equals(order_id) && order_id!=null){

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
            		.setOutTradeNo(String.valueOf(order_id));
    		AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
    		
    		switch (result.getTradeStatus()) {
    			case SUCCESS:
    				//log.info("查询返回该订单支付成功: )");
    				payinfo=new MccPayResult();
    				AlipayTradeQueryResponse resp = result.getResponse();
    				//tradeStatus=resp.getTradeStatus();
	                payinfo.setOrder_id(order_id);
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

}
