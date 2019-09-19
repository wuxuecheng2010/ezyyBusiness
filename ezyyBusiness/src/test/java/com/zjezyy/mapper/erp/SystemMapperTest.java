package com.zjezyy.mapper.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.erp.SysPriorPrice;
import com.zjezyy.entity.erp.TbUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemMapperTest {

	@Autowired
	SystemMapper systemMapper;

	@Test
	public void testGetERPBillcode() {
		Map<String, String> map = new HashMap<>();
		map.put("v_prefix", "XSDD");
		map.put("v_billcode", "");
		systemMapper.getERPBillcode(map);
		System.out.println(map.get("v_billcode"));
	}

	@Test
	public void testusp_CustomerProduct_Cansell() {
		Map<String, Object> map = new HashMap<>();
		map.put("CustomerID_in", 403);
		map.put("ProductID_in", "5338");
		map.put("SalesNoticeDetailID_in", 1806647);
		map.put("ErrMsg_Out", "");
		map.put("cur_Result", null);
		// systemMapper.usp_CustomerProduct_Cansell(map);
		System.out.println(map.get("ErrMsg_Out"));
	}

	@Test
	public void testusp_CusProduct_WMSCansell() {
		Map<String, String> map = new HashMap<>();
		map.put("v_prefix", "XSDD");
		map.put("v_billcode", "");
		systemMapper.getERPBillcode(map);
		System.out.println(map.get("v_billcode"));
	}

	@Test
	public void testgetUnitTest_() {
		Map<String, String> map = new HashMap<>();
		map.put("i_iunitid", "1");
		map.put("vcunitname", null);
		systemMapper.getUnitTest_(map);
		System.out.println(map.get("i_iunitid"));
	}

	@Test
	public void testgetUnitTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("i_iunitid", "1");
		map.put("o_cursor", new TbUnit());
		systemMapper.getUnitTest(map);
	}

	@Test
	public void testgetCustomerProductPrice() {
		Map<String, Object> map = new HashMap<>();
		map.put("customerid_in", "409");
		map.put("productid_in", "6961");
		map.put("cur_result", null);
		systemMapper.getCustomerProductPrice(map);
		List<SysPriorPrice> list = (List<SysPriorPrice>) map.get("cur_result");
		if (list != null && list.size() > 0)
			System.out.println(list.get(0).getNumbargainingprice());

	}

}
