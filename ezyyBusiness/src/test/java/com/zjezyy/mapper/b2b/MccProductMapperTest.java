package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProduct_Eo;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccProductMapperTest {

	@Autowired
	MccProductMapper mccProductMapper;
	@Test
	public void testGetOne() {
		MccProduct_Eo mccProduct_Ex=	mccProductMapper.getOne(1, 1);
		System.out.println(mccProduct_Ex.getModel());
	}
	
	@Test
	public void testInsert() {
		////delete from mcc_product where erpiproductid=99999
		MccProduct mccproduct=new MccProduct( "model", "sku", "location", 99999);
		mccProductMapper.insert(mccproduct);
		System.out.println(mccproduct.getProduct_id());
	}
	
	

}
