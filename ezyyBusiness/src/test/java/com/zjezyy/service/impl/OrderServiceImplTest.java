package com.zjezyy.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbSalesOrderS;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.enums.BusinessInterfaceType;
import com.zjezyy.enums.DeployPolicyEnum;
import com.zjezyy.mapper.erp.TbCustomerMapper;
import com.zjezyy.mapper.erp.TbSalesOrderSMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;
import com.zjezyy.service.SystemService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

	@Autowired
	OrderService orderServiceImpl;
	@Autowired
	SystemService systemServiceImpl;
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	@Resource(name="wxPayServiceImpl")
	PayService wxPayServiceImpl;
	
	@Autowired
	TbSalesOrderSMapper tbSalesOrderSMapper;
	
	@Autowired
	TbStocksMapper tbStocksMapper;

	@Test
	public void testOrderPlace() {
		orderServiceImpl.orderPlace(58, 3,"123455token");
	}

	@Test
	public void testMakeTbMccOrderToTbSalesOrder() {

		orderServiceImpl.makeTbMccOrderToTbSalesOrder(22, 3);
	}

	@Test
	public void testcreateSalesOrderCode() {
		String x=systemServiceImpl.genBillCode("XSDD");
		System.out.println(x);
	}
	
	@Test
	public void testmakeTbMccOrderToTbSalesOrder() {
		orderServiceImpl.makeTbMccOrderToTbSalesOrder(43, 3);
		
	}
	
	
	@Test
	public void testStockDeploy() {
		int ibillid=6505897;
		TbSalesOrderS tbSalesOrderS=tbSalesOrderSMapper.getTbSalesOrderSList(ibillid).get(0);
		tbSalesOrderS.setNumquantity(new BigDecimal("26"));
		List<TbStocks> stocklist=tbStocksMapper.getListByIproductid(tbSalesOrderS.getIproductid());
		for(TbStocks s:stocklist)
			 s.setDtusefullife("2019-04-15");
		Map<BigDecimal, TbStocks> map=orderServiceImpl.stockDeploy(tbSalesOrderS, stocklist,  DeployPolicyEnum.FIFOAN);
		Set<Entry<BigDecimal, TbStocks>> set =map.entrySet();
		for(Entry<BigDecimal, TbStocks> e:set)
			System.out.println(e.getValue().getVcbatchnumber());
	}
	
	
	
	@Test
	public void testPayResultQuery() {
		orderServiceImpl.payResultQuery(wxPayServiceImpl, 100);
	}
	

	@Test
	public void testpayExpired() {
		orderServiceImpl.payExpired(95);
	}
	
	@Test
	public void testApproval() {
		Map<String, Object>  map=orderServiceImpl.approval(731642, "2011");
		log.info("iResult_out:"+(Integer)map.get("iResult_out"));
		log.info("ErrMsg_Out:"+(String)map.get("ErrMsg_Out"));
	}
	
	
	@Test
	public void testcheckCustomerRight() {
		TbCustomer  tbCustomer=tbCustomerMapper.getOne(4443);
		Map<String,Boolean > map=new HashMap<>();
		boolean f=orderServiceImpl.checkCustomerRight(tbCustomer, BusinessInterfaceType.B2BToERPOnLine.getCode(), map);
		System.out.println(f);
	}
	
	
	@Test
	public void testcheckCustomerCredit() {
		TbCustomer  tbCustomer=tbCustomerMapper.getOne(4443);
		Map<String,Boolean > map=new HashMap<>();
		boolean f=orderServiceImpl.checkCustomerCredit(tbCustomer, true);
		System.out.println(f);
		
	}
	
	
	
	
	
	
	
}
