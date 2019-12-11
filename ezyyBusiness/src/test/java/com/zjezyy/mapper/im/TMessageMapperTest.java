package com.zjezyy.mapper.im;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.im.TMessage;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TMessageMapperTest {
	
	@Autowired
	TMessageMapper tMessageMapper;
	@Test
	public void testGetOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		TMessage tMessage=new TMessage("测试","15990683720","Hello");
		tMessageMapper.insert(tMessage);
	}

}
