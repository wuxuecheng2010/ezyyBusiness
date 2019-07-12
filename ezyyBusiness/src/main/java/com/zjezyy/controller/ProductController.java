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
import com.zjezyy.service.ProductService;
import com.zjezyy.utils.ResultUtil;

@RestController
@RequestMapping(value = "/product", method = RequestMethod.GET)
public class ProductController extends BaseController {


	@Autowired
	ProductService productServiceimpl;

	//接口  http://localhost:8099/bi/api/customer/copy?icustomerid=442&credit=0
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public Result copyCustomerForB2B(HttpServletRequest request, HttpServletResponse response) {
		int iproductid=-1;
		int store_id=0;
		int layout_id=0;
		String _iproductid=request.getParameter("iproductid");
		String _store_id=request.getParameter("store_id");
		String _layout_id=request.getParameter("layout_id");
		try {
			iproductid=Integer.valueOf(_iproductid);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ExceptionEnum.ERP_PRODUCT_ID_ERR);
		}
		try {
			store_id=Integer.valueOf(_store_id);
		}catch(NumberFormatException e) {
			//e.printStackTrace();
		}
		try {
			layout_id=Integer.valueOf(_layout_id);
		}catch(NumberFormatException e) {
			//e.printStackTrace();
		}
		
		productServiceimpl.doSynchronizeProductInfo(iproductid, store_id, layout_id);
		return ResultUtil.success();

	}
	
	
	


}
