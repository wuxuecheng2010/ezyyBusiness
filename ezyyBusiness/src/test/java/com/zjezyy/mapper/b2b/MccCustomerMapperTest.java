package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccCustomer;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccCustomerMapperTest {
	@Autowired
	MccCustomerMapper mccCustomerMapper;

	@Test
	public void testGetOne() {
		MccCustomer mccCustomer=	mccCustomerMapper.getOne(17);
		log.info(mccCustomer.getFullname());
	}

}
