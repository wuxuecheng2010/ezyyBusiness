package com.zjezyy.mapper.erp;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemMapperTest {

	@Autowired
	SystemMapper systemMapper;
	@Test
	public void testGetERPBillcode() {
		Map<String,String> map=new HashMap<>();
		map.put("v_prefix", "XSDD");
		map.put("v_billcode", "");
		systemMapper.getERPBillcode(map);
		System.out.println(map.get("v_billcode"));
	}

}
