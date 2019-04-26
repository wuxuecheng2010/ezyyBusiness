package com.zjezyy.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

//一般的数据转换工具类
public class DataUtil {
	/**
     * <获取参数map>
     * @return 参数map
     * @throws Exception 
     */
    public static  Map<String, String> getParameterMap(HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String[]> tempMap = request.getParameterMap();
        Set<String> keys = tempMap.keySet();
        for (String key : keys) {
            byte source [] = request.getParameter(key).getBytes("iso8859-1");
            String modelname = new String (source,"UTF-8");
            resultMap.put(key,modelname);
        }
        return resultMap;
    }
	

}
