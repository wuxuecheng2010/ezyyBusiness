package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MccPayResultMapperTest {
	@Autowired
	MccPayResultMapper mccPayResultMapper;

	@Test
	public void testCount() {
		int x=mccPayResultMapper.countMccPayResultByOrderID(2345);
		System.out.println(x);
	}

}
