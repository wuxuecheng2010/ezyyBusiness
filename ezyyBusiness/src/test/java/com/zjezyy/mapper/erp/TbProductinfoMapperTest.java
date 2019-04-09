package com.zjezyy.mapper.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbProductinfoMapperTest {
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	@Test
	public void testGetOne() {
		TbProductinfo tbProductinfo	=tbProductinfoMapper.getOne(118);
		System.out.println(tbProductinfo.getVcuniversalname());
	}
	
	@Test
	public void testGetOne2() {
		TbProductinfo_Eo tbProductinfo	=tbProductinfoMapper.getOneEo(12112,201);
		System.out.println(tbProductinfo.getVcuniversalname());
	}


}
