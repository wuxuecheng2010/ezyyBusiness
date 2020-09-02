package com.zjezyy.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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
	
    
    
  //末尾数是数字的删除  循环删除，直到“”
    public static String formatNameSubfix(String vcuniversalname) {


		if(vcuniversalname!=null && vcuniversalname.length()>0)
		{
			String subfix=vcuniversalname.substring(vcuniversalname.length()-1,vcuniversalname.length());
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
		    boolean flag= pattern.matcher(subfix).matches(); 
			if(flag) {
				vcuniversalname=vcuniversalname.substring(0,vcuniversalname.length()-1);
				vcuniversalname=formatNameSubfix(vcuniversalname);
			}else {
				
				return  vcuniversalname;
			}
				
		}
		
		return vcuniversalname;
	} 
    

}
