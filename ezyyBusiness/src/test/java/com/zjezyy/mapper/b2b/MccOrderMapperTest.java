package com.zjezyy.mapper.b2b;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccOrder;
import com.zjezyy.enums.Payment;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MccOrderMapperTest {
	@Autowired
	MccOrderMapper mccOrderMapper;
	@Test
	public void testGetOne() {
		MccOrder mccOrder= mccOrderMapper.getOne(57);
		System.out.println(mccOrder.getFullname());
	}
	
	
	@Test
	public void testGetUnPayMccOrderList() {
		List<MccOrder> list= mccOrderMapper.getUnPayMccOrderList(1000);
		System.out.println(list.size());
	}
	
	
	@Test
	public void testUpdateMccOrderStatus() {
		MccOrder mccOrder= mccOrderMapper.getOne(99);
		System.out.println(mccOrder.getOrder_status_id());
		mccOrderMapper.updateMccOrderStatus(99, "18");
	}
	
	
	
	

}



