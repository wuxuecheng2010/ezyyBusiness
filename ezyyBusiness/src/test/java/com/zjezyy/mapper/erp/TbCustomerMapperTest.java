package com.zjezyy.mapper.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbCustomer;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbCustomerMapperTest {

	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	@Test
	public void testGetOne() {
		TbCustomer tbCustomer	=tbCustomerMapper.getOne(76);
		System.out.println(tbCustomer.getVccustomername());
	}

}
