package com.zjezyy.mapper.erp;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbProductinfo_Eo1;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.service.SettingService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbProductinfoMapperTest {
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	@Autowired
	MccProductMapper mccProductMapper;
	
	@Autowired
	SettingService SettingServiceImpl;
	@Test
	public void testGetOne() {
		TbProductinfo tbProductinfo	=tbProductinfoMapper.getOne(118);
		System.out.println(tbProductinfo.getVcuniversalname());
	}
	
	@Test
	public void testGetOne2() {
		TbProductinfo_Eo tbProductinfo	=tbProductinfoMapper.getOneEo_(12112,201);
		System.out.println(tbProductinfo.getVcuniversalname());
	}
	
	@Test
	public void testgetOneEoDirty() {
		
		MccProduct mccProduct=mccProductMapper.getOne(13692, 0);
		TbProductinfo_Eo tbProductinfo	=tbProductinfoMapper.getOneEoDirty(mccProduct.getErpiproductid());
		
		//,SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD)
		System.out.println(SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD));
		System.out.println(tbProductinfo.getVcuniversalname());
		 Field[] fields = tbProductinfo.getClass().getDeclaredFields();
		 /*for(Field field:fields) {
			 field.setAccessible(true);
			 String fieldName=field.getName();
			 if(fieldName.equals(SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD))) {
				 field.get(tbProductinfo);
				 break ;
			 }
		 }*/
		 reflect(tbProductinfo);

		 
		 
	}
	
	
	 public static void reflect(Object obj) {
	        if (obj == null) {
	            return;
	        }
	        Field[] fields = obj.getClass().getDeclaredFields();
	        String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
	        String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character",
	                "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
	        for (int j = 0; j < fields.length; j++) {
	            fields[j].setAccessible(true);
	            // 字段名
	            System.out.print(fields[j].getName() + ":");
	            // 字段值
	            for (int i = 0; i < types1.length; i++) {
	                if (fields[j].getType().getName()
	                        .equalsIgnoreCase(types1[i]) || fields[j].getType().getName().equalsIgnoreCase(types2[i])) {
	                    try {
	                        System.out.print(fields[j].get(obj) + "     ");
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }

	
	
	@Test
	public void testgetOneEo1() {
		
		TbProductinfo_Eo1 tbProductinfo1	=tbProductinfoMapper.getOneEo1(12112);
		System.out.println(tbProductinfo1.getVcuniversalname());
		
	}
	
	@Test
	public void testgetOneEo1ByCustomerSet() {
		TbProductinfo_Eo1 tbProductinfo1	=tbProductinfoMapper.getOneEo1ByCustomerSet(2638,102);
		System.out.println(tbProductinfo1.getVcuniversalname());
	}
	
	
	@Test
	public void testgetProductListForB2B() {
		List<TbProductinfo> list	=tbProductinfoMapper.getProductListForB2B(102);
		System.out.println(list.size());
	}
	
	@Test
	public void testCountProduct() {
		int n	=tbProductinfoMapper.getProductListCount();
		System.out.println(n);
	}


}
