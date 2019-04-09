package com.zjezyy.mapper.erp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccCustomer;
import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.mapper.b2b.MccCustomerMapper;
import com.zjezyy.mapper.b2b.MccOrderMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbMccOrderMapperTest {

	@Autowired
	TbMccOrderMapper tbMccOrderMapper;
	@Autowired
	TbMccOrderProductMapper tbMccOrderProductMapper;
	@Autowired
	MccOrderMapper mccOrderMapper;
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	@Autowired
	MccCustomerMapper  mccCustomerMapper;
	
	
	@Test
	public void testInsert() {
		MccOrder mccOrder=	mccOrderMapper.getOne(57);
		
		Integer customer_id=mccOrder.getCustomer_id();
		MccCustomer mccCustomer =mccCustomerMapper.getOne(customer_id);
		int erp_icustomerid=mccCustomer.getErp_icustomerid();
		TbCustomer tbCustomer=tbCustomerMapper.getOne(erp_icustomerid);

		TbMccOrder tbMccOrder=new TbMccOrder();
		tbMccOrder=new TbMccOrder(mccOrder,erp_icustomerid, tbCustomer.getVccustomername());
		
		tbMccOrderMapper.insert(tbMccOrder);
		System.out.println("Impid:"+tbMccOrder.getImpid());
	}
	
	@Test
	public void testGetOne() {
		TbMccOrder tbMccOrder=	tbMccOrderMapper.getOne(22);
		System.out.println(tbMccOrder.getErp_vccustomername());
		
		List<TbMccOrderProduct> list=tbMccOrderProductMapper.getListByImpid(22);
		System.out.println(list.size());
	}
	@Test
	public void testGetSaleTaxRateList() {
		List<BigDecimal> list =tbMccOrderProductMapper.getSaleTaxRateList(22);
		System.out.println(list.size());
	}
	
	@Test
	public void testCreateSalesOrderCode() {
		Map params=new HashMap<String,String>();
		params.put("prefix", "XSDD");
		params.put("vcbillno", "");
		//String code=tbMccOrderMapper.createSalesOrderCode(params);
		
	}
}
