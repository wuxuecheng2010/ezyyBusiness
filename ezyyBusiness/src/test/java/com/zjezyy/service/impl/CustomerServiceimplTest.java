package com.zjezyy.service.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CustomerServiceimplTest {
@Autowired
	CustomerServiceimpl customerServiceimpl;
	
	@Test
	public void testMakeTbCustomerKindToMccTbCustomerKindByICustomerid() {
		customerServiceimpl.makeTbCustomerKindToMccTbCustomerKindByICustomerid(450);
	}

}
