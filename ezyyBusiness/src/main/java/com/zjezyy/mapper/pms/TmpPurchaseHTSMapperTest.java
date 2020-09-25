package com.zjezyy.mapper.pms;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.pms.TmpPurchaseHTS;
import com.zjezyy.job.Task_GeneralTest;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TmpPurchaseHTSMapperTest {

	@Autowired
	TmpPurchaseHTSMapper tmpPurchaseHTSMapper;
	@Test
	public void testGetListVcbillno() {
		List<TmpPurchaseHTS> s=tmpPurchaseHTSMapper.getListVcbillno("CGHT2009250001");
		
	}

}
