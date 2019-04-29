package com.zjezyy.service.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.service.AuthorityService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AuthorityServiceImplTest {

	@Autowired
	AuthorityService authorityServiceImpl;
	
	@Test
	public void testGenToken() {
		String token=authorityServiceImpl.genToken("1025220", "dhkahkjhjakhkhk");
		System.out.println(token);
	}

	@Test
	public void testCheckToken() {
		String token=authorityServiceImpl.genToken("1025220", "dhkahkjhjakhkhk");
		authorityServiceImpl.checkToken(token);
	}

}
