package com.zjezyy.mapper.sys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.im.TUser;
import com.zjezyy.mapper.im.TUserMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TUserMapperTest {
	
	@Autowired
	TUserMapper tUserMapper;

	@Test
	public void testGetOne() {
		
		TUser tUser=tUserMapper.getOne("1025220");
		System.out.println(tUser.getPassword());
	}

}
