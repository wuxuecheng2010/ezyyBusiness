package com.zjezyy.job;

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
public class Task_GeneralTest {
	@Autowired
	Task_General task_General;
	
	@Autowired
	Task_Fast task_Fast;

	@Test
	public void testB2bProductPacksize() {
		try {
			task_General.b2bProductPacksize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testproductforb2b() {
		try {
			task_General.productforb2b();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testlowStorage() {

		try {
			//task_General.lowStorage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
	
	
	
	@Test
	public void testunpayOrderStatusQuery() {
		try {
			task_Fast.unpayOrderStatusQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//b2bProductPacksize
	
	@Test
	public void testb2bProductPacksize() {
		try {
			task_General.b2bProductPacksize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testb2bPrice() {
		
		try {
		//	task_General.b2bPrice();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
