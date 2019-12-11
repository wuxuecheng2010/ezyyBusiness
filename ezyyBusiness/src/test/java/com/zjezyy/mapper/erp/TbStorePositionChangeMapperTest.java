package com.zjezyy.mapper.erp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbStorePositionChange;
import com.zjezyy.entity.erp.TbStorePositionChangeS;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TbStorePositionChangeMapperTest {
	
	@Autowired
	TbStorePositionChangeMapper tbStorePositionChangeMapper;
	@Autowired
	TbStorePositionChangeSMapper tbStorePositionChangeSMapper;
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	@Test
	public void testGetListTimerout() {
	List<TbStorePositionChange>	list=tbStorePositionChangeMapper.getListTimerout();
	log.info("长度{}",list.size());
	
	 for(TbStorePositionChange p:list) {
		 int ibillid=p.getIbillid();
		List<TbStorePositionChangeS> listS= tbStorePositionChangeSMapper.getListByIbillid(ibillid);
		for(TbStorePositionChangeS ps:listS) {
			int iproductid=ps.getIproductid();
			TbProductinfo tbproductinfo =tbProductinfoMapper.getOne(iproductid);
			System.out.println(tbproductinfo.getVcuniversalname());
		}
		
	 }
	}

}
