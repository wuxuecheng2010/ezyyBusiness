package com.zjezyy.mapper.b2b;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccOrderProduct;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccOrderProductMapperTest {

	
	@Autowired
	MccOrderProductMapper mccOrderProductMapper;
	
	@Test
	public void testGetOne() {
		MccOrderProduct mccOrderProduct=mccOrderProductMapper.getOne(5);
		log.info(mccOrderProduct.getName());
	}

	@Test
	public void testGetListByOrderId() {
		List<MccOrderProduct> list=mccOrderProductMapper.getListByOrderId(3);
		for(MccOrderProduct mccOrderProduct:list)
				log.info(mccOrderProduct.getName());
	}

}
