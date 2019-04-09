package com.zjezyy.utils.wx;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.URL;  
import java.net.URLConnection;  


import org.apache.log4j.Logger;  


/** 
 * http请求工具类 
 * @author chenp 
 * 
 */  
public class HttpUtil {  

    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds  连接超时的时间  
    private final static String DEFAULT_ENCODING = "UTF-8";  //字符串编码  
    private static Logger lg=Logger.getLogger(HttpUtil.class);  

    public static String postData(String urlStr, String data){    
        return postData(urlStr, data, null);    
    }    
    /** 
     * post数据请求 
     * @param urlStr 
     * @param data 
     * @param contentType 
     * @return 
     */  
    public static String postData(String urlStr, String data, String contentType){    
        BufferedReader reader = null;    
        try {    
            URL url = new URL(urlStr);    
            URLConnection conn = url.openConnection();    
            conn.setDoOutput(true);    
            conn.setConnectTimeout(CONNECT_TIMEOUT);    
            conn.setReadTimeout(CONNECT_TIMEOUT);    
            if(contentType != null)    
                conn.setRequestProperty("content-type", contentType);    
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);    
            if(data == null)    
                data = "";    
            writer.write(data);     
            writer.flush();    
            writer.close();      

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));    
            StringBuilder sb = new StringBuilder();    
            String line = null;    
            while ((line = reader.readLine()) != null) {    
                sb.append(line);    
                sb.append("\r\n");    
            }    
            return sb.toString();    
        } catch (IOException e) {    
        lg.info("Error connecting to " + urlStr + ": " + e.getMessage());    
        } finally {    
            try {    
                if (reader != null)    
                    reader.close();    
            } catch (IOException e) {    
            }    
        }    
        return null;    
    }    
}   