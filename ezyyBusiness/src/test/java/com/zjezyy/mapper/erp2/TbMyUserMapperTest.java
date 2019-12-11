package com.zjezyy.mapper.erp2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp2.TbMyUser;
import com.zjezyy.mapper.erp2.TbMyUserMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbMyUserMapperTest {

	@Autowired
	TbMyUserMapper tbMyUserMapper;
	
	
	@Test
	public void testGetOne() {
		TbMyUser tbMyUser=tbMyUserMapper.getOne("2011");
		String tel=tbMyUser.getVctel();
		System.out.println(tel==null);
		System.out.println(tbMyUser.getVcusername());
	}

}
