package com.zjezyy.mapper.b2b;

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
public class MccOrderTbSalesNoticeRelateMapperTest {
	
	@Autowired
	MccOrderTbSalesNoticeRelateMapper mccOrderTbSalesNoticeRelateMapper;

	@Test
	public void testUpdate() {
		mccOrderTbSalesNoticeRelateMapper.update(820180);
	}

}
