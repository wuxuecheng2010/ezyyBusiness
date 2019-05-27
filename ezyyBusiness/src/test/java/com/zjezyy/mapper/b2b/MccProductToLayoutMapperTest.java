package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProductToLayout;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MccProductToLayoutMapperTest {
	@Autowired
	MccProductToLayoutMapper mccProductToLayoutMapper;
	@Test
	public void testInsert() {
		//delete from mcc_product_to_layout where layout_id=99
		MccProductToLayout mccProductToLayout=new MccProductToLayout(99,99,99);
		mccProductToLayoutMapper.insert(mccProductToLayout);
	}

}
