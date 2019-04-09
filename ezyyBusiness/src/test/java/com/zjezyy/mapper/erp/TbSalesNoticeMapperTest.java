package com.zjezyy.mapper.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbSalesNotice;
import com.zjezyy.entity.erp.TbSalesOrder;
import com.zjezyy.service.SystemService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbSalesNoticeMapperTest {
	@Autowired
	TbSalesNoticeMapper tbSalesNoticeMapper;
	
	@Autowired
	TbSalesOrderMapper   tbSalesOrderMapper;
	
	@Autowired
	SystemService systemServiceImpl;
	
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	@Value("${erp.salesnotice.vcbillcode.prefix}")
	private String prefix;
	
	@Test
	public void testInsert() {
		TbSalesOrder tbSalesOrder=tbSalesOrderMapper.getOne(6505897);
		TbCustomer tbCustomer =tbCustomerMapper.getOne(tbSalesOrder.getIcustomerid());
		String vcbillcode=systemServiceImpl.genBillCode(prefix);
		int itypeid=2 ;//单据类型 默认1 直接开票 2从订单导入 3招标网导入 4缺货导入,5销售冲价
		int isalerid=tbCustomer.getIsalerid();
		int igathermode=1;//收款方式 公共字典13  1现款 2非现款
		char flagurgent='N';
		TbSalesNotice tbSalesNotice=new TbSalesNotice( tbSalesOrder, vcbillcode,
				 itypeid, isalerid, igathermode, flagurgent);
		tbSalesNoticeMapper.insert(tbSalesNotice);
	}

}
