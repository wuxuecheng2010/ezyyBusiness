package com.zjezyy.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.b2b.MccPayResult;
import com.zjezyy.enums.BusinessType;
import com.zjezyy.enums.PayResult;
import com.zjezyy.enums.Payment;
import com.zjezyy.service.PayService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.DateUtils;
import com.zjezyy.utils.wx.HttpUtil;
import com.zjezyy.utils.wx.PayForUtil;
import com.zjezyy.utils.wx.XMLUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayServiceImpl implements PayService {

	// 微信交易查询
	public static String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	@Autowired
	SettingService SettingServiceImpl;

	@Override
	public MccPayResult payResultQuery(Integer order_id) throws RuntimeException {

		/**
		 * 账号信息
		 */
		String code = Payment.QRCODE_WXPAY.getCode();
		String appid = SettingServiceImpl.getMccSettingValue(code, "qrcode_wxpay_appid");// 微信服务号的appid
		String mch_id = SettingServiceImpl.getMccSettingValue(code, "qrcode_wxpay_mchid");// WeChatConfig.MCHID; //
																							// 微信支付商户号
		String key = SettingServiceImpl.getMccSettingValue(code, "qrcode_wxpay_key");// WeChatConfig.APIKEY; //
																						// 微信支付的API密钥
		String orderquery_url = ORDERQUERY_URL;// WeChatConfig.ORDERQUERY_URL;// 微信下单API地址

		/**
		 * 时间字符串
		 */
		String currTime = PayForUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = PayForUtil.buildRandom(4) + "";
		String nonce_str = strTime + strRandom;

		/**
		 * 参数封装
		 */
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();

		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("out_trade_no", String.valueOf(order_id));
		packageParams.put("nonce_str", nonce_str);// 随机字符串

		String sign = PayForUtil.createSign("UTF-8", packageParams, key); // 获取签名
		packageParams.put("sign", sign);

		String requestXML = PayForUtil.getRequestXml(packageParams);// 将请求参数转换成String类型
		log.info("微信支付请求参数的报文" + requestXML);
		String resXml = HttpUtil.postData(orderquery_url, requestXML); // 解析请求之后的xml参数并且转换成String类型
		Map map = null;
		try {
			map = XMLUtil.doXMLParse(resXml);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MccPayResult payinfo = null;
		if (map != null) {
			String trade_state = (String) map.get("trade_state");
			if ("SUCCESS".equals(trade_state)) {
				payinfo = new MccPayResult();
				payinfo.setAttach((String) map.get("attach"));
				payinfo.setOrder_id(order_id);
				String total_fee = (String) map.get("total_fee");
				BigDecimal fee = new BigDecimal(total_fee).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				payinfo.setFee(fee.toString());
				String time_end = (String) map.get("time_end");
				String payDate = DateUtils.formatSimpleStrToDefaultStr(time_end);
				payinfo.setPaydate(payDate);
				payinfo.setPayment(Payment.QRCODE_WXPAY.getCode());
				payinfo.setPlordercode((String) map.get("transaction_id"));
				payinfo.setBusstype(BusinessType.SALE.getMsg());
				payinfo.setTradestatus(PayResult.SUCCESS.getCode());
			}
		}

		log.info("微信支付响应参数的报文" + resXml);
		// String urlCode = (String) map.get("code_url");
		return payinfo;

	}

}
