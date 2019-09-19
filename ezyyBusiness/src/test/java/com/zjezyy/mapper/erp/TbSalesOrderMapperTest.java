package com.zjezyy.mapper.erp;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbSalesOrder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TbSalesOrderMapperTest {

	@Autowired
	TbSalesOrderMapper tbSalesOrderMapper;

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByImpidAndTypeID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByTypeIDAndIFBAndDays() {
		List<TbSalesOrder> list = tbSalesOrderMapper.getPerformListByTypeIDAndIFBAndDays(3, 1, 100);
		System.out.println(list.size());
	}

}
