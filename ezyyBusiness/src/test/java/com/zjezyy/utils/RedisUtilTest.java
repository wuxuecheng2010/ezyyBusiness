package com.zjezyy.utils;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.mapper.erp.TbCustomerMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisUtilTest {

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	TbCustomerMapper tbCustomerMapper;
	
	private static final String  key="redisUtil";
	@Test
	public void testExpire() {
		//String key="redisUtil";
		String value="Hello redis";
		redisUtil.set(key, value);
		//redisUtil.expire(key, 10);
		
	}

	@Test
	public void testGetExpire() {
		//String key="redisUtil";
		long l=redisUtil.getExpire(key);
		System.out.println(l);
	}

	@Test
	public void testHasKey() {
	boolean f=	redisUtil.hasKey(key);
	System.out.println(f);
	}

	@Test
	public void testDel() {
		redisUtil.del(key,"x");
		System.out.println(redisUtil.hasKey(key));
	}

	@Test
	public void testGet() {
		TbCustomer tbCustomer=new TbCustomer();
		tbCustomer=tbCustomerMapper.getOne(764) ;
		String k="tbCustomer";
		redisUtil.set(k, tbCustomer);
		TbCustomer  t=(TbCustomer) redisUtil.get(k);
	}

	@Test
	public void testSetStringT() {
		
	}

	@Test
	public void testSetStringTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncr() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecr() {
		fail("Not yet implemented");
	}

	@Test
	public void testHget() {
		fail("Not yet implemented");
	}

	@Test
	public void testHmget() {
		fail("Not yet implemented");
	}

	@Test
	public void testHmsetStringMapOfStringT() {
		fail("Not yet implemented");
	}

	@Test
	public void testHmsetStringMapOfStringTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testHsetStringStringT() {
		fail("Not yet implemented");
	}

	@Test
	public void testHsetStringStringTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testHdel() {
		fail("Not yet implemented");
	}

	@Test
	public void testHHasKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testHincr() {
		fail("Not yet implemented");
	}

	@Test
	public void testHdecr() {
		fail("Not yet implemented");
	}

	@Test
	public void testSGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSHasKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testSSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSSetAndTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSGetSetSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testLGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testLGetListSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testLGetIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testLSetStringT() {
		fail("Not yet implemented");
	}

	@Test
	public void testLSetStringTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testLSetStringListOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testLSetStringListOfTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testLUpdateIndex() {
		fail("Not yet implemented");
	}

	@Test
	public void testLRemove() {
		fail("Not yet implemented");
	}

}
