package com.zjezyy.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.service.PayService;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class WxPayServiceImplTest {

	@Resource(name="wxPayServiceImpl")
	PayService wxPayServiceImpl;
	@Test
	public void testPayResultQuery() {
		try {
			wxPayServiceImpl.payResultQuery(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
