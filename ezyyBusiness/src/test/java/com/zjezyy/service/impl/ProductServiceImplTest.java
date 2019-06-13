package com.zjezyy.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {
	@Autowired
	ProductService productServiceImpl;
	@Autowired
	TbStocksMapper tbStocksMapper;
	@Autowired
	MccProductMapper mccProductMapper;
	@Autowired
	MccTbCustomerKindPriceMapper mccTbCustomerKindPriceMapper;
	@Autowired
	TbCustomerKindPriceMapper tbCustomerKindPriceMapper;
	@Test
	public void testGetTbProductinfoByMccProductId() {
		//TbProductinfo_Eo tbProductinfo_Eo = productServiceImpl.getTbProductinfoEoByMccProductId(40);
		//System.out.println("tbProductinfo_Eo:" + tbProductinfo_Eo.getVcuniversalname());
	}

	@Test
	public void testIsNearEffective() {
		// TbProductinfo tbProductinfo,TbStocks tbStocks
		TbProductinfo tbProductinfo = productServiceImpl.getTbProductinfoById(249);
		List<TbStocks> list = tbStocksMapper.getListByIproductid(249);
		for (TbStocks s : list)
			System.out.println(productServiceImpl.isNearEffective(tbProductinfo, s));
	}

	@Test
	public void testDoSynchronizeOnOff() {
		int erpIproductid=1380;
		MccProduct	mccProduct =mccProductMapper.getOneByErpIproductid(erpIproductid);
		productServiceImpl.doSynchronizeOnOff(mccProduct);
		
	}
	
	@Test
	public void testDoSynchronizePrice() {
		int erpIproductid=1380;
		MccProduct	mccProduct =mccProductMapper.getOneByErpIproductid(erpIproductid);
		productServiceImpl.doSynchronizePrice(mccProduct);
		
	}
	
	@Test
	public void testGetAllMccProductID() {
		//int erpIproductid=1380;
		//MccProduct	mccProduct =mccProductMapper.getOneByErpIproductid(erpIproductid);
		//productServiceImpl.doSynchronizePrice(mccProduct);
		List<MccProduct> list= productServiceImpl.getAllMccProductID();
		//for(MccProduct s:list)
		//	System.out.println(s.getModel());
		System.out.println(list.size());
	}
	
	@Test
	public void testDoSynchronizeCustomerKindPrice() {
		
		productServiceImpl.doSynchronizeCustomerKindPrice();
	}
	
	
	@Test
	public void testupdateMccProductByTbProductinfo() {
		TbProductinfo tbProductinfo=	productServiceImpl.getTbProductinfoById(982);
		productServiceImpl.updateMccProductByTbProductinfo(tbProductinfo);
	}
	
	@Test
	public void testx() {
		//MccTbCustomerKindPrice mccTbCustomerKindPrice= mccTbCustomerKindPriceMapper.getOne(74866);
		//获取ERP价格集合信息 //如果没有则删除B2b的价格集合  
 	   TbCustomerKindPrice tbCustomerKindPrice= tbCustomerKindPriceMapper.getOneByISID(74866);
 	   if(tbCustomerKindPrice==null)
 		   mccTbCustomerKindPriceMapper.deleteByISID(74866);
		
	}

}
