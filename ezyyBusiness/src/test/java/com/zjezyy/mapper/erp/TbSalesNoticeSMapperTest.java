package com.zjezyy.mapper.erp;


import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbSalesNotice;
import com.zjezyy.entity.erp.TbSalesNoticeS;
import com.zjezyy.entity.erp.TbSalesOrder;
import com.zjezyy.entity.erp.TbSalesOrderS;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.service.SystemService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbSalesNoticeSMapperTest {
	@Autowired
	TbSalesNoticeMapper tbSalesNoticeMapper;
	
	@Autowired
	TbSalesNoticeSMapper tbSalesNoticeSMapper;
	
	@Autowired
	TbSalesOrderMapper   tbSalesOrderMapper;
	
	@Autowired
	TbSalesOrderSMapper   tbSalesOrderSMapper;
	
	@Autowired
	TbCustomerKindPriceMapper tbCustomerKindPriceMapper;
	
	@Autowired
	TbStocksMapper tbStocksMapper;
	
	
	@Autowired
	SystemService systemServiceImpl;
	
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	
	@Value("${erp.salesnotice.vcbillcode.prefix}")
	private String prefix;
	
	@Value("${b2b.product.price.icustomerkindid}")
	private int icustomerkindid;
	@Test
	public void testInsert() {
		int ibillid=6505897;
		TbSalesOrder tbSalesOrder=tbSalesOrderMapper.getOne(ibillid);
		TbCustomer tbCustomer =tbCustomerMapper.getOne(tbSalesOrder.getIcustomerid());
		String vcbillcode=systemServiceImpl.genBillCode(prefix);
		int itypeid=2 ;//单据类型 默认1 直接开票 2从订单导入 3招标网导入 4缺货导入,5销售冲价
		int isalerid=tbCustomer.getIsalerid();
		int igathermode=1;//收款方式 公共字典13  1现款 2非现款
		char flagurgent='N';
		TbSalesNotice tbSalesNotice=new TbSalesNotice( tbSalesOrder, vcbillcode,
				 itypeid, isalerid, igathermode, flagurgent);
		tbSalesNoticeMapper.insert(tbSalesNotice);
		
		List<TbSalesOrderS> list=tbSalesOrderSMapper.getTbSalesOrderSList(ibillid);
		float numqueue=1.0f;
		
		
		
		for(TbSalesOrderS tbSalesOrderS:list) {
			BigDecimal numquantity=new BigDecimal(1);
			List<TbStocks> stocklist= tbStocksMapper.getListByIproductid(tbSalesOrderS.getIproductid());
			TbCustomerKindPrice tbCustomerKindPrice=tbCustomerKindPriceMapper.getOne(tbSalesOrderS.getIproductid(), icustomerkindid);
			int lastid=tbSalesNoticeSMapper.getLastId(tbSalesOrderS.getIproductid(), tbCustomer.getIcustomerid());
			TbSalesNoticeS tbSalesNoticeS=tbSalesNoticeSMapper.getOne(lastid);
			BigDecimal numlastprice=tbSalesNoticeS==null?null:tbSalesNoticeS.getNumprice();
			
			TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(tbSalesOrderS.getIproductid());
			BigDecimal numcountryprice=tbProductinfo.getNumcountryprice();
			TbSalesNoticeS _tbSalesNoticeS=new TbSalesNoticeS( 
					ibillid, numqueue, numquantity,
					 tbSalesOrderS, stocklist.get(0),
					 tbCustomerKindPrice,
					 numlastprice, numcountryprice
					);
			tbSalesNoticeSMapper.insert(_tbSalesNoticeS);
			numqueue+=0.1;
		}
	}

}
