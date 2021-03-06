package com.zjezyy.mapper.b2b;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccDistrict;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccDistrictMapperTest {
	
	@Autowired
	MccDistrictMapper mccDistrictMapper;

	@Test
	public void testGetOnebyName() {
		MccDistrict mccDistrict=mccDistrictMapper.getOnebyName("天台县");
		System.out.println(mccDistrict.getCity_id());
	}

	@Test
	public void testGetOnebyShortName() {
		fail("Not yet implemented");
	}

}
