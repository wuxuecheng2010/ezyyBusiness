package com.zjezyy.mapper.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.TbReceivables_Eo;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbReceivablesMapperTest {
	@Autowired
	TbReceivablesMapper tbReceivablesMapper;
	@Test
	public void testGetEarliest() {
	float f=	tbReceivablesMapper.getEarliest(4443);
	System.out.println(f);
	}

	@Test
	public void testGetSummer() {
		TbReceivables_Eo f=	tbReceivablesMapper.getSummer(4443);
	System.out.println(f.getNumcreditmoney());
	}
	
	
}
