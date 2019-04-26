package com.zjezyy.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.service.OrderService;
import com.zjezyy.service.PayService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AliPayServiceImplTest {

	@Resource(name = "aliPayServiceImpl")
	PayService aliPayServiceImpl;
	
	@Autowired
	OrderService orderServiceImpl;
	
	@Test
	public void testPayResultQuery() {
		
		//orderServiceImpl.payResultQuery(aliPayServiceImpl, 152);
		
		//aliPayServiceImpl.payResultQuery(152);
	}

}
