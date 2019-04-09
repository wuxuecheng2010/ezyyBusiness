package com.zjezyy.mapper.erp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zjezyy.entity.b2b.MccCustomer;
import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.entity.b2b.MccOrderProduct;
import com.zjezyy.entity.b2b.MccProduct_Eo;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.mapper.b2b.MccCustomerMapper;
import com.zjezyy.mapper.b2b.MccOrderMapper;
import com.zjezyy.mapper.b2b.MccOrderProductMapper;
import com.zjezyy.mapper.b2b.MccProductMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbMccOrderProductMapperTest {
	@Autowired
	TbMccOrderMapper tbMccOrderMapper;
	@Autowired
	MccOrderMapper mccOrderMapper;
	
	@Autowired
	MccOrderProductMapper mccOrderProductMapper;
	
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	@Autowired
	MccCustomerMapper  mccCustomerMapper;
	@Autowired
	TbMccOrderProductMapper tbMccOrderProductMapper;
	@Autowired
	MccProductMapper mccProductMapper;
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	
	//@Transactional
	@Test
	public void testInsert() {

		MccOrder mccOrder=	mccOrderMapper.getOne(57);
		Integer customer_id=mccOrder.getCustomer_id();
		MccCustomer mccCustomer =mccCustomerMapper.getOne(customer_id);
		int erp_icustomerid=mccCustomer.getErp_icustomerid();
		TbCustomer tbCustomer=tbCustomerMapper.getOne(erp_icustomerid);
		TbMccOrder tbMccOrder=new TbMccOrder();
		tbMccOrder=new TbMccOrder(mccOrder,erp_icustomerid, tbCustomer.getVccustomername());
		
		//主表保存到就表
		tbMccOrderMapper.insert(tbMccOrder);
		Integer impid=tbMccOrder.getImpid();
		
		List<MccOrderProduct> list=mccOrderProductMapper.getListByOrderId(tbMccOrder.getMcc_order_id());
		for(MccOrderProduct mccOrderProduct:list) {
			Integer product_id=mccOrderProduct.getProduct_id();
			MccProduct_Eo mccProduct_Eo =mccProductMapper.getOne(product_id, 1);
			Integer erpiproductid=mccProduct_Eo.getErpiproductid();
			TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(erpiproductid);
			TbMccOrderProduct tbMccOrderProduct=new TbMccOrderProduct(mccOrderProduct,impid, erpiproductid, tbProductinfo.getNumsaletaxrate());
			tbMccOrderProductMapper.insert(tbMccOrderProduct);
		}
		//System.out.println("Impid:"+tbMccOrder.getImpid());
	
	}

}
