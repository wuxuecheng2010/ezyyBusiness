package com.zjezyy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjezyy.entity.b2b.MccCustomer;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccCustomerMapper;
import com.zjezyy.mapper.erp.TbCustomerMapper;
import com.zjezyy.service.CustomerService;

@Service
public class CustomerServiceimpl implements CustomerService {
	@Autowired
	MccCustomerMapper mccCustomerMapper;
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	@Override
	public TbCustomer getTbCustomerByMccCustomerId(int customer_id) throws RuntimeException {
		MccCustomer mccCustomer =mccCustomerMapper.getOne(customer_id);
		Integer erp_icustomerid=mccCustomer.getErp_icustomerid();
		//客户信息校验
		if(erp_icustomerid==null) {
			throw new BusinessException(ExceptionEnum.B2B_CUSTOMER_UNRELATED);
		}
		TbCustomer tbCustomer=tbCustomerMapper.getOne(erp_icustomerid);
		if(tbCustomer==null) {
			throw new BusinessException(ExceptionEnum.B2B_CUSTOMER_RELATED_ERROR);
		}
		return tbCustomer;
	}

}
