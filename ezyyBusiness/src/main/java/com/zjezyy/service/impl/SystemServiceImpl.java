package com.zjezyy.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.Result;
import com.zjezyy.entity.erp.SysPriorPrice;
import com.zjezyy.entity.im.TMessage;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.enums.MessageGroup;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.erp.SystemMapper;
import com.zjezyy.mapper.im.TMessageMapper;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.service.SystemService;
import com.zjezyy.utils.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
	
	static int socketTimeout = 30000;// 请求超时时间
	static int connectTimeout = 30000;// 传输超时时间
	
	@Autowired
	TMessageMapper tMessageMapper;

	@Autowired
	SystemMapper systemMapper;

	@Autowired
	AuthorityService authorityServiceImpl;

	@Value("${erp.billcode.create.url}")
	String vcbillcodeURL;

	// 此方法不能用于@Transactional下面，否则不行；
	@Override
	public String genBillCode(String prefix) throws RuntimeException {

		Map<String, String> map = new HashMap<>();
		map.put("v_prefix", prefix);
		map.put("v_billcode", "");
		systemMapper.getERPBillcode(map);
		String vcbillcode = map.get("v_billcode");
		return vcbillcode;
	}

	@Override
	public String genBillCodeForTransactional(String prefix) throws RuntimeException {

		// 获取头信息的token值
		Map<String, String> map_head = new HashMap<>();
		authorityServiceImpl.credateHeader(map_head);

		String url = getBillCodeURL(prefix);
		String code = "";
		Result res = HttpClientUtil.get(url, map_head);

		JSONObject jsonObject = null;
		if (res != null && res.getStatus() == 0) {
			String data = (String) res.getData();

			if (data != null && !"".equals(data)) {
				int status = JSON.parseObject(data).getIntValue("status");
				if (status == 0)
					code = JSON.parseObject(data).getString("data");
			}

		} else {
			throw new BusinessException(res.getMsg(), ExceptionEnum.ERP_ORDER_CODE_CREATE_HTTP_FAIL);
		}

		return code;
	}

	@Override
	public String getBillCodeURL(String prefix) {
		// return "http://localhost:8099/bi/order/fetchcode?prefix="+prefix;
		return vcbillcodeURL + prefix;
	}

	@Override
	public int getTypeId(String bif) throws RuntimeException {
		int itypeid = 0;
		switch (bif) {
		case "b2b-underline":
			itypeid = 3;
			break;

		case "b2b-online":
			itypeid = 4;
			break;

		default:
			itypeid = 0;
			break;
		}
		return itypeid;
	}

	@Override
	public SysPriorPrice getSysPriorPrice(int icustomerid, int iproductid) throws RuntimeException {
		SysPriorPrice sysPriorPrice = null;
		Map<String, Object> map = new HashMap<>();
		map.put("customerid_in", icustomerid);
		map.put("productid_in", iproductid);
		map.put("cur_result", null);
		systemMapper.getCustomerProductPrice(map);
		List<SysPriorPrice> list = (List<SysPriorPrice>) map.get("cur_result");
		if (list != null && list.size() > 0) {
			sysPriorPrice = list.get(0);
		}
		return sysPriorPrice;
	}
	
	
	//获取客户可以销售的商品库存数据
	@Override
	public Map<String, Object> getCustomerCanBuyProduct(int icustomerid, int iproductid) throws RuntimeException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("customerid_in", String.valueOf(icustomerid));
		map.put("productid_in", String.valueOf(iproductid));
		map.put("salesnoticedetailid_in", "-1");
		map.put("errmsg_out", null);
		map.put("cur_result", null);
		systemMapper.getCustomerCanBuyProduct(map);
		//List<SysCustomerProductCanSale> list = (List<SysCustomerProductCanSale>) map.get("cur_result");
		//String errmsg_out=(String) map.get("errmsg_out");
		return map;
	}
	
	

	@Override
	public void sendTelMsg(MessageGroup  messageGroup,String msg, String  tel) {
		//短信业务不能影响主体业务 所以本地捕获了  有问题查日志吧
		try {
			//webservice地址
	        String postUrl = "http://192.168.0.118:8081/ydweb/Service1.asmx?wsdl";
	        //SOAPAction
	        String SOAPAction = "http://tempuri.org/SendMsg";
	        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
	                "  <soap:Body>\n" +
	                "    <SendMsg xmlns=\"http://tempuri.org/\">\n" +
	                "      <number>"+ tel +"</number>\n" +
	                "      <MsgInfo>"+ msg +"</MsgInfo>\n" +
	                "    </SendMsg>\n" +
	                "  </soap:Body>\n" +
	                "</soap:Envelope>";
	        //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
	        String reStr=doPostSoap1_1(postUrl, xml, SOAPAction);
	        TMessage tMessage=new TMessage(messageGroup.toString(),tel,msg);
	        sendTelMsgResponse(reStr,tMessage);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	        

	       /* String soap12 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
	                "  <soap12:Body>\n" +
	                "    <HostAddOrUpdateByOtherSys xmlns=\"http://www.uradiosys.com/\">\n" +
	                "      <tagMac>"+ tagMac +"</tagMac>\n" +
	                "      <hostExternalId>"+ hostExternalId +"</hostExternalId>\n" +
	                "      <hostName>"+ hostName +"</hostName>\n" +
	                "      <description></description>\n" +
	                "      <ImagePath></ImagePath>\n" +
	                "    </HostAddOrUpdateByOtherSys>\n" +
	                "  </soap12:Body>\n" +
	                "</soap12:Envelope>";
	        //采用SOAP1.2调用服务端，这种方式只能调用服务端为soap1.2的服务
	        doPostSoap1_2(postUrl, soap12, "");*/
	    
	}

	/**
	* @Title: sendTelMsgResponse
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param reStr    参数
	* @author wuxuecheng
	* @return void    返回类型
	* @throws
	*/
	public void sendTelMsgResponse(String reStr,TMessage tMessage) {
		SAXReader reader = new SAXReader();
		try {
			InputStream byteArrayInputStream=new ByteArrayInputStream(reStr.getBytes());
			Document document = reader.read(byteArrayInputStream);
			Element string = document.getRootElement();
			String subXml=string.getStringValue();
			document = reader.read(new ByteArrayInputStream(subXml.getBytes()));
			Element response=document.getRootElement();
			Element error= response.element("error");
			tMessage.setFlagerr(error.getStringValue());
			tMessageMapper.insert(tMessage);
			if(!"0".equals(error.getStringValue())) {
				//写报错日志
				log.error("以下短信发送失败："+tMessage.toString());
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	
	private String doPostSoap1_1(String postUrl, String soapXml, String soapAction) {
		
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("response:" + retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			System.out.println("exception in doPostSoap1_1" + e);
		}
		return retStr;
	}

	private String doPostSoap1_2(String postUrl, String soapXml, String soapAction) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "application/soap+xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				System.out.println("response:" + retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			System.out.println("exception in doPostSoap1_2" + e);
		}
		return retStr;
	}

}
