package com.zjezyy.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorePositionChangeServiceImplTest {
	
	@Autowired
	StorePositionChangeServiceImpl storePositionChangeServiceImpl;

	@Test
	public void testNotifyTimeOutOrder() {
		try {
			storePositionChangeServiceImpl.notifyTimeOutOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNotifyItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCreaterTel() {
		fail("Not yet implemented");
	}

}
