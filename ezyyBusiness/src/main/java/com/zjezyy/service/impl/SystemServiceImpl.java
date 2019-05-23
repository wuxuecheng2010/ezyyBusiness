package com.zjezyy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.Result;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.erp.SystemMapper;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.service.SystemService;
import com.zjezyy.utils.HttpClientUtil;

@Service
public class SystemServiceImpl implements SystemService {

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
			String data=(String)res.getData();
			
			if(data!=null && !"".equals(data)) {
				int status=JSON.parseObject(data).getIntValue("status");
				if(status==0)
					code=JSON.parseObject(data).getString("data");
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
	public void sendTelMsg(String msg, List<String> tels) {
		// TODO Auto-generated method stub

	}

}
