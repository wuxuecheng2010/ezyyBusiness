package com.zjezyy.service.impl;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccSetting;
import com.zjezyy.service.SettingService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)

@Slf4j
public class SettingServiceImplTest {

	@Autowired
	SettingService settingServiceImpl;
	@Test
	public void testGetMccSetting() {
		MccSetting setting=settingServiceImpl.getMccSetting("total", "total_status");
		log.info("Setting:"+setting.getValue());
	}

	
	@Test
	public void testGetAllMccSettingByCode() {
		Map<String ,String > map=settingServiceImpl.getEzyySettings();
		if(map!=null)
				System.out.println(map.get("order_place_url"));
	}
	
	
}
