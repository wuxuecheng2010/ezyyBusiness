package com.zjezyy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zjezyy.entity.Result;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.service.CustomerService;
import com.zjezyy.utils.ResultUtil;

@RestController
@RequestMapping(value = "/api/customer", method = RequestMethod.GET)
public class CustomerController extends BaseController {


	@Autowired
	CustomerService customerServiceimpl;

	//下单接口  http://localhost:8099/bi/api/customer/copy?icustomerid=442&credit=0
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public Result copyCustomerForB2B(HttpServletRequest request, HttpServletResponse response) {

		String icustomeridStr=request.getParameter("icustomerid");//
		String credit=request.getParameter("credit");//授信客户  0或者1  1代表授信客户
		if(icustomeridStr==null || "".equals("icustomeridStr")) {
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_ID_NOT_SUPPLY);
		}
		Integer flag_credit_user=credit==null || "".equals("credit")?0:Integer.valueOf(credit);
		
		Integer icustomerid=Integer.valueOf(icustomeridStr);
		return customerServiceimpl.makeTbCustomerToMccCustomer(icustomerid,flag_credit_user);

	}
	
	
	


}
