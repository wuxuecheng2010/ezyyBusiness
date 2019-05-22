package com.zjezyy.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.Result;
import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.b2b.MccCustomer;
import com.zjezyy.entity.b2b.MccTbCustomerKind;
import com.zjezyy.entity.b2b.MccTbCustomerKindList;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKind;
import com.zjezyy.entity.erp.TbCustomerKindList;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccCustomerMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindListMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindMapper;
import com.zjezyy.mapper.erp.TbCustomerKindListMapper;
import com.zjezyy.mapper.erp.TbCustomerKindMapper;
import com.zjezyy.mapper.erp.TbCustomerMapper;
import com.zjezyy.service.AddressService;
import com.zjezyy.service.CustomerService;
import com.zjezyy.utils.HttpClientUtil;
import com.zjezyy.utils.ResultUtil;

@Service
public class CustomerServiceimpl implements CustomerService {
	@Autowired
	MccCustomerMapper mccCustomerMapper;
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	@Autowired
	AddressService AddressServiceImpl;
	@Autowired
	TbCustomerKindListMapper tbCustomerKindListMapper;
	@Autowired
	TbCustomerKindMapper tbCustomerKindMapper;
	@Autowired
	MccTbCustomerKindMapper mccTbCustomerKindMapper;
	@Autowired
	MccTbCustomerKindListMapper mccTbCustomerKindListMapper;

	@Value("${b2b.api.url}")
	private String customer_receive_url;

	@Value("${b2b.api.customer.receive.url.controller.name}")
	private String request_name;

	@Value("${b2b.api.customer.receive.url.controller.value}")
	private String request_value;

	@Override
	public TbCustomer getTbCustomerByMccCustomerId(int customer_id) throws RuntimeException {
		MccCustomer mccCustomer = mccCustomerMapper.getOne(customer_id);
		Integer erp_icustomerid = mccCustomer.getErp_icustomerid();
		// 客户信息校验
		if (erp_icustomerid == null) {
			throw new BusinessException(ExceptionEnum.B2B_CUSTOMER_UNRELATED);
		}
		TbCustomer tbCustomer = tbCustomerMapper.getOne(erp_icustomerid);
		if (tbCustomer == null) {
			throw new BusinessException(ExceptionEnum.B2B_CUSTOMER_RELATED_ERROR);
		}
		return tbCustomer;
	}

	@Transactional
	@Override
	public Result makeTbCustomerToMccCustomer(int icustomerid, int flag_credit_user) throws RuntimeException {
		Result resule = null;
		// 获取ERP客户信息,并检查信息的完整性
		TbCustomer tbCustomer = tbCustomerMapper.getOne(icustomerid);
		checkTbCustomer(tbCustomer);

		// 检查B2B客户信息是否存在
		MccCustomer mccCustomer = mccCustomerMapper.getOneByERPIcustomerID(tbCustomer.getIcustomerid());

		if (mccCustomer == null)
		// 创建b2b客户账号 邮件和手机号码留空 等用户登入自行维护
		{
			// 以http的形式创建用户信息
			Map<String, String> map = new HashMap<>();
			fillCustomerMap(tbCustomer, flag_credit_user, map);
			Result res = HttpClientUtil.getMap(customer_receive_url, map, null);

			if (res != null && res.getStatus() == 0) {

				// res.getData().toString()={"code":"success","msg":"B2B\u7528\u6237\u521b\u5efa\u6210\u529f","data":{"account":"0060048","password":"7723","fullname":"\u5929\u53f0\u53bf\u8d64\u57ce\u8857\u9053\u5999\u5c71\u793e\u533a\u536b\u751f\u670d\u52a1\u7ad9"}}
				// String s=res.getData().toString();
				JSONObject jsonobject = JSON.parseObject(res.getData().toString());
				String code = jsonobject.getString("code");
				String msg = jsonobject.getString("msg");

				if ("success".equals(code)) {
					JSONObject jsonobjectdata = jsonobject.getJSONObject("data");
					int customer_id = jsonobjectdata.getInteger("customer_id");
					String account = jsonobjectdata.getString("account");
					String password = jsonobjectdata.getString("password");
					String fullname = jsonobjectdata.getString("fullname");

					try {

						// 创建客户地址
						// int address_id=saveAddress(customer_id,tbCustomer);
						MccAddress mccAddress = AddressServiceImpl.saveMccAddress(customer_id, tbCustomer);

						// 更新客户默认的客户地址
						mccCustomerMapper.updateAddressID(customer_id, mccAddress.getAddress_id());
						
						//根据ERP客户ID 获取客户的客户集合信息  集合列表
						makeTbCustomerKindToMccTbCustomerKindByICustomerid(icustomerid);
						
						

					} catch (Exception e) {
						// 因为客户地址数据保存出错，删除本次创建的客户信息 保持数据的一致性
						mccCustomerMapper.delete(customer_id);
						throw e;
					}

					String info = String.format("%s %s 账号:%s 初始密码:%s 请务必让客户修改密码", fullname, msg, account, password);
					resule = ResultUtil.success(info);
				} else {
					String info = msg;
					resule = ResultUtil.success(info);
				}

				return resule;

			} else {
				throw new BusinessException(res.getMsg(), ExceptionEnum.HTTP_ERROR);
			}

		} else {
			// 已经存在 提示不允许重复创建
			throw new BusinessException(ExceptionEnum.B2B_CUSTOMER_IS_EXISIT);
		}

	}

	// 检查ERP客户信息的数据完整性
	private void checkTbCustomer(TbCustomer tbCustomer)throws RuntimeException  {
		if(tbCustomer==null)
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_ID_NOT_EXISIT);
		if(tbCustomer.getVclinkman()==null||"".equals(tbCustomer.getVclinkman().trim()))
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_LACK_VCLINKMAN);
	}

	// 创建B2B账户参数
	private void fillCustomerMap(TbCustomer tbCustomer, int flag_credit_user, Map<String, String> map) {
		map.put(request_name, request_value);
		map.put("fullname", tbCustomer.getVccustomername());
		map.put("account", tbCustomer.getVccustomercode());
		int password = (int) (Math.random() * 8999 + 1000);// 随机4位数字
		map.put("password", String.valueOf(password));
		map.put("flag_credit_user", String.valueOf(flag_credit_user));
		map.put("erp_icustomerid", String.valueOf(tbCustomer.getIcustomerid()));
	}

	//根据ERP客户ID 创建客户集合  和  客户集合列表
	@Transactional
	@Override
	public void makeTbCustomerKindToMccTbCustomerKindByICustomerid(int icustomerid) throws RuntimeException {
		//获取客户所在的价格集合
		TbCustomerKindList tbCustomerKindList =tbCustomerKindListMapper.getOne(icustomerid);
		
		if(tbCustomerKindList!=null) {
			
			//客户集合ID  
			Integer icustomerkindid=tbCustomerKindList.getIcustomerkindid();
			
			//获取集合信息  
			TbCustomerKind tbCustomerKind=tbCustomerKindMapper.getOne(icustomerkindid);
			
			//保存mcc_tb_customerkind
			MccTbCustomerKind mccTbCustomerKind=mccTbCustomerKindMapper.getOne(icustomerkindid);
			if(mccTbCustomerKind==null) {
				MccTbCustomerKind _mccTbCustomerKind=new MccTbCustomerKind(tbCustomerKind);
				mccTbCustomerKindMapper.insert(_mccTbCustomerKind);
			}
			
			//保存mcc_tb_customerkindlist
			MccTbCustomerKindList mccTbCustomerKindList=mccTbCustomerKindListMapper.getOne(icustomerid);
			if(mccTbCustomerKindList==null) {
				MccTbCustomerKindList _mccTbCustomerKindList=new MccTbCustomerKindList(tbCustomerKindList);
				mccTbCustomerKindListMapper.insert(_mccTbCustomerKindList);
			}
			
			
		}else {
			
			throw new BusinessException(String.format("客户ID:%d", icustomerid),ExceptionEnum.ERP_CUSTOMER_KIND_NOT_SET);
		}
		
			
		
	}

}
