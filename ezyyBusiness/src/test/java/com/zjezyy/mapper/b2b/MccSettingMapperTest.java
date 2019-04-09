package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccSetting;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccSettingMapperTest {

	@Autowired
	MccSettingMapper mccSettingMapper;

	@Test
	public void testGetSettingByKey() {
		MccSetting set = mccSettingMapper.getSettingByKey("cms_blog", "cms_blog_description");
		System.out.println(set.getValue());
	}

}
