package com.zjezyy.mapper.b2b;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProductDescription;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MccProductDescriptionMapperTest {
	@Autowired
	MccProductDescriptionMapper mccProductDescriptionMapper;
	@Test
	public void testInsert() {
		MccProductDescription mccProductDescription =new MccProductDescription();
		mccProductDescription.setProduct_id(99999);
		mccProductDescription.setName("Test 000999");
		mccProductDescription.setLanguage_id(1);
		mccProductDescription.setMeta_title("Meta_title");
		mccProductDescription.setTag("Tag");
		mccProductDescription.setDescription("decscs");
		mccProductDescription.setMeta_keyword("Hello");
		mccProductDescription.setMeta_description("xxxx");
		mccProductDescriptionMapper.insert(mccProductDescription);
		




//delete  from mcc_product_description where product_id=99999
	}

}
