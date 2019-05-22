package com.zjezyy.mapper.b2b;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccTbCustomerKindPrice;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccTbCustomerKindPriceMapperTest {
	@Autowired
	MccTbCustomerKindPriceMapper mccTbCustomerKindPriceMapper;

	@Test
	public void testGetOne() {
		MccTbCustomerKindPrice	mccTbCustomerKindPrice =mccTbCustomerKindPriceMapper.getOne(123);
	System.out.println(mccTbCustomerKindPrice==null);
	}

	@Test
	public void testUpdateByTbCustomerKindPrice() {
		//fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		//fail("Not yet implemented");
	}

}
