package com.zjezyy.service;

import java.util.Map;

public interface AuthorityService {
	// 创建token
	String genToken(String username,String password);

	// 验证token
	void checkToken(String token);
	
	//创建http 请求header
	void credateHeader(Map<String,String > map_head);
}
