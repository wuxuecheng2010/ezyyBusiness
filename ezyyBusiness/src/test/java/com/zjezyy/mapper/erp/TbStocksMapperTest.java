package com.zjezyy.mapper.erp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbStocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TbStocksMapperTest {

	@Autowired
	TbStocksMapper tbStocksMapper;

	@Test
	public void testGetListByIproductid() {
		List<TbStocks> list = tbStocksMapper.getListByIproductid(48);
		for (TbStocks s : list)
			System.out.println(s.getVcbatchnumber());
		
		
		
	}

}
