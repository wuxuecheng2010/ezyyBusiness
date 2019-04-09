package com.zjezyy.service.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.service.SystemService;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SystemServiceImplTest {
	@Autowired
	SystemService systemServiceImpl;
	@Test
	public void testGenToken() {
	}

	@Test
	public void testCheckToken() {
	}



	@Test
	public void testGenBillCodeByHttp() {
		String x=systemServiceImpl.genBillCode("XXDD");
		System.out.println(x);
	}
	
	@Test
	public void testGenBillCode() {
		String x=systemServiceImpl.genBillCode("XXDD");
		System.out.println(x);
	}
	
	@Test
	public void testgenBillCodeForTransactional() {
		String x=systemServiceImpl.genBillCodeForTransactional("FFDD");
		System.out.println(x);
	}
	
	
	
	

	
}
