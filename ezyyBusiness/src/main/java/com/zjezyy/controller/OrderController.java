package com.zjezyy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zjezyy.entity.Result;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.SystemService;
import com.zjezyy.utils.ResultUtil;

@RestController
@RequestMapping(value = "/api/order", method = RequestMethod.GET)
public class OrderController extends BaseController {

	@Autowired
	OrderService orderServiceImpl;
	
	@Autowired
	SystemService systemServiceImpl;
	

	//下单接口
	//http://localhost:8099/bi/order/place?oc_order_id=95&bif=b2b-online&token=123456
	@RequestMapping(value = "/place", method = RequestMethod.POST)
	public Result place(HttpServletRequest request, HttpServletResponse response) {

		String oc_order_id = request.getParameter("oc_order_id");
		String bif=request.getParameter("bif");//来自b2b  换车文字表示  然后文字指向ID 防止代码ID选错 
		
		String token = request.getParameter("token");
		//b2b向erp下单  参数校验  通过继续  不通过则异常返回
		orderServiceImpl.checkOrderPlaceParam(oc_order_id, token);
		int order_id=Integer.valueOf(oc_order_id);
		int typeid=systemServiceImpl.getTypeId(bif);
		//开始下单
		orderServiceImpl.orderPlace(order_id, typeid,token);

		return ResultUtil.success();
	}
	
	
	
	//获取ERP单据号
	@RequestMapping(value = "/fetchcode", method = RequestMethod.GET)
	public Result fetchCode(HttpServletRequest request, HttpServletResponse response) {
		String prefix = request.getParameter("prefix");
		String code=systemServiceImpl.genBillCode(prefix);
		return ResultUtil.success(code);
	}
	
	//审核销售开票
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	public Result approval(HttpServletRequest request, HttpServletResponse response) {
		//1、获取参数
		String ibillid = request.getParameter("ibillid");
		String user=request.getParameter("user");
		//2、检查参数的合法性
		orderServiceImpl.checkSalesNoticeAppParams(ibillid);
		//3、执行审核并返回结果集
		Map<String,Object> map=orderServiceImpl.approval(Integer.valueOf(ibillid), user);
		return ResultUtil.success();
	}
	


}
