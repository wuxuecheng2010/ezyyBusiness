package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProductToStore;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MccProductToStoreMapperTest {
	@Autowired
	MccProductToStoreMapper mccProductToStoreMapper;

	@Test
	public void testInsert() {
		//delete from mcc_product_to_store  where store_id=5
		MccProductToStore mccProductToStore=new MccProductToStore(9999,5);
		mccProductToStoreMapper.insert(mccProductToStore);
	}

}
