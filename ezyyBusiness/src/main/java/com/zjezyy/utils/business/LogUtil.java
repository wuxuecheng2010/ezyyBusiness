package com.zjezyy.utils.business;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {
    public static void logForB2BProduct(String clazz,String method,Exception e,MccProduct mccProduct) {
    	String into="";
    	if(e instanceof BusinessException) {
			String message=((BusinessException)e) .getMessage();
			Integer code=((BusinessException)e).getCode();
			into=String.format("业务 %s.%s： B2B商品ID：%s,出错Code：%d,错误信息：%s", clazz,method,mccProduct.getProduct_id(),code,message);
		    log.warn(into);
    	}else {
    		into=String.format("业务 %s.$s：B2B商品ID：%s,错误信息%s", clazz,method,mccProduct.getProduct_id(),e.getMessage());
			log.warn(into);
		}
    }
}
