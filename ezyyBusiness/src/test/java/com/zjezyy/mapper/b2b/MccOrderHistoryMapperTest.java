package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccOrderHistory;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccOrderHistoryMapperTest {

	@Autowired
	MccOrderHistoryMapper mccOrderHistoryMapper;
	@Test
	public void testInsert() {
		MccOrderHistory mccOrderHistory=new MccOrderHistory();
		mccOrderHistory.setComment("Test");
		mccOrderHistory.setNotify(0);
		mccOrderHistory.setOrder_id(99);
		mccOrderHistory.setOrder_status_id(100);
		mccOrderHistoryMapper.insert(mccOrderHistory);
		System.out.println(mccOrderHistory.getOrder_history_id());
	}

}
