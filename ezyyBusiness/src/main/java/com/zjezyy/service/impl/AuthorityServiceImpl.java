package com.zjezyy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.im.TUser;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.im.TUserMapper;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.service.SettingService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Autowired
	TUserMapper tUserMapper;
	
	@Autowired
	SettingService settingServiceImpl;

	@Value("${im.secret}")
	private String SECRET;
	
	@Value("${im.token.timetout}")
	private Integer expiretime;
	
	@Override
	public String genToken(String username,String password)throws RuntimeException {
		
		TUser tUser=tUserMapper.getOne(username);
		if(tUser==null) 
			throw new BusinessException(ExceptionEnum.USER_ERR);
		if(!tUser.getPassword().equals(password))
			throw new BusinessException(ExceptionEnum.USER_PSW_ERR);
		
		HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("username", username);
        map.put("password",password);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expiretime))// 3600_000_000L 1000 hour
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return "Bearer "+jwt; //jwt前面一般都会加Bearer
		
		
	}

	@Override
	public void checkToken(String token) {

		 try {
	            // parse the token.
	            Map<String, Object> body = Jwts.parser()
	                    .setSigningKey(SECRET)
	                    .parseClaimsJws(token.replace("Bearer ",""))
	                    .getBody();
	            //System.out.println("xxxx");
	        }catch (Exception e){
	            throw new IllegalStateException("Invalid Token. "+e.getMessage());
	        }
		
	}

	
	@Override
	public void credateHeader(Map<String, String> map_head) {
		String erp_api_header_name=settingServiceImpl.getEzyySettingValue(EzyySettingKey.API_HEADER_NAME);
		String api_user_name=settingServiceImpl.getEzyySettingValue(EzyySettingKey.API_USER_NAME);
		String api_user_password=settingServiceImpl.getEzyySettingValue(EzyySettingKey.API_USER_PASSWORD);
		String token =genToken(api_user_name, api_user_password);
		map_head.put(erp_api_header_name, token);
	}

}
