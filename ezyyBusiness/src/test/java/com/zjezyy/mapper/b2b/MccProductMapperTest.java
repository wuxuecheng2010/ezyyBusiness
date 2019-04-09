package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProduct_Eo;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccProductMapperTest {

	@Autowired
	MccProductMapper mccProductMapper;
	@Test
	public void testGetOne() {
		MccProduct_Eo mccProduct_Ex=	mccProductMapper.getOne(1, 1);
		System.out.println(mccProduct_Ex.getModel());
	}

}
