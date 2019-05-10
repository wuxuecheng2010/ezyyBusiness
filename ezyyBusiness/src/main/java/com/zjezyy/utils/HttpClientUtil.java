package com.zjezyy.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.zjezyy.entity.Result;

public class HttpClientUtil {

	/**
	 * get请求，参数拼接在地址上
	 * 
	 * @param url
	 *            请求地址加参数
	 * @return 响应
	 */
	public static Result get(String url, Map<String, String> head_map) {
		Result ress=null;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		// 增加header输入
		addHeaders(get, head_map);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
				ress=ResultUtil.success(result);
			}else if(response != null && response.getStatusLine().getStatusCode() != 200){
				ress=ResultUtil.error(response.getStatusLine().getStatusCode(), "statusCode:"+response.getStatusLine().getStatusCode());
			}

		} catch (IOException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ress;
	}

	/*
	 * public static String get_h(String url, Map<String, String> map_h) { String
	 * result = null; CloseableHttpClient httpClient = HttpClients.createDefault();
	 * HttpGet get = new HttpGet(url); addHeaders(get,map_h); CloseableHttpResponse
	 * response = null; try { response = httpClient.execute(get); if (response !=
	 * null && response.getStatusLine().getStatusCode() == 200) { HttpEntity entity
	 * = response.getEntity(); result = entityToString(entity); } return result; }
	 * catch (IOException e) { e.printStackTrace(); } finally { try {
	 * httpClient.close(); if (response != null) { response.close(); } } catch
	 * (IOException e) { e.printStackTrace(); } } return null; }
	 */

	/**
	 * get请求，参数放在map里
	 * 
	 * @param url
	 *            请求地址
	 * @param map
	 *            参数map
	 * @return 响应
	 */
	public static Result getMap(String url, Map<String, String> map, Map<String, String> head_map) {
		Result ress=null;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			builder.setParameters(pairs);
			HttpGet get = new HttpGet(builder.build());
			// 增加header输入
			addHeaders(get, head_map);

			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
				ress=ResultUtil.success(result);
			}else if(response != null && response.getStatusLine().getStatusCode() != 200){
				ress=ResultUtil.error(response.getStatusLine().getStatusCode(), "statusCode:"+response.getStatusLine().getStatusCode());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ress;
	}

	/*
	 * public static String getMap_h(String url, Map<String, String> map,Map<String,
	 * String> map_h) { String result = null; CloseableHttpClient httpClient =
	 * HttpClients.createDefault(); List<NameValuePair> pairs = new
	 * ArrayList<NameValuePair>(); for (Map.Entry<String, String> entry :
	 * map.entrySet()) { pairs.add(new BasicNameValuePair(entry.getKey(),
	 * entry.getValue())); } CloseableHttpResponse response = null; try { URIBuilder
	 * builder = new URIBuilder(url); builder.setParameters(pairs); HttpGet get =
	 * new HttpGet(builder.build()); addHeaders(get, map_h); response =
	 * httpClient.execute(get); if (response != null &&
	 * response.getStatusLine().getStatusCode() == 200) { HttpEntity entity =
	 * response.getEntity(); result = entityToString(entity); } return result; }
	 * catch (URISyntaxException e) { e.printStackTrace(); } catch
	 * (ClientProtocolException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); } finally { try { httpClient.close(); if (response !=
	 * null) { response.close(); } } catch (IOException e) { e.printStackTrace(); }
	 * }
	 * 
	 * return null; }
	 */

	/**
	 * 发送post请求，参数用map接收
	 * 
	 * @param url
	 *            地址
	 * @param map
	 *            参数
	 * @return 返回值
	 */
	public static Result postMap(String url, Map<String, String> map, Map<String, String> head_map) {
		
		Result ress=null;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		addHeaders(post, head_map);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			response = httpClient.execute(post);
			// System.out.println("****请求接口服务
			// 状态码response.getStatusLine().getStatusCode():"+response.getStatusLine().getStatusCode());
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
				ress=ResultUtil.success(result);
			}else if(response != null && response.getStatusLine().getStatusCode() != 200){
				ress=ResultUtil.error(response.getStatusLine().getStatusCode(), "statusCode:"+response.getStatusLine().getStatusCode());
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return ress;
	}

	/**
	 * post请求，参数为json字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonString
	 *            json字符串
	 * @return 响应
	 */
	public static Result postJson(String url, String jsonString, Map<String, String> head_map) {
		Result ress=null;
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		addHeaders(post, head_map);
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
				ress=ResultUtil.success(result);
			}else if(response != null && response.getStatusLine().getStatusCode() != 200){
				ress=ResultUtil.error(response.getStatusLine().getStatusCode(), "statusCode:"+response.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ress= ResultUtil.error(-9999, e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ress;
	}

	private static String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if (entity != null) {
			long lenth = entity.getContentLength();
			if (lenth != -1 && lenth < 2048) {
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while ((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		return result;
	}

	private static void addHeaders(HttpRequestBase obj, Map<String, String> map) {

		if (map != null)
			for (Entry<String, String> e : map.entrySet()) {
				obj.addHeader(e.getKey(), e.getValue());
			}

	}





}
