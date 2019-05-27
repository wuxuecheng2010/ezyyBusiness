package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProductToCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MccProductToCategoryMapperTest {
	@Autowired
	MccProductToCategoryMapper mccProductToCategoryMapper;
	@Test
	public void testInsert() {
		//delete  from mcc_product_to_category x where x.category_id=5 and product_id=999
		MccProductToCategory mccProductToCategory=new MccProductToCategory(999,5);
		mccProductToCategoryMapper.insert(mccProductToCategory);
	}

}
