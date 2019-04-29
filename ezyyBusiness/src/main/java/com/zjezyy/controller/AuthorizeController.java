package com.zjezyy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjezyy.entity.Result;
import com.zjezyy.utils.JwtUtil;
import com.zjezyy.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping(value="/Authorize")
public class AuthorizeController extends BaseController{

	//获取token令牌
	@RequestMapping(value="/getToken")
	//@Prevent
	public Result getToken(HttpServletRequest request, HttpServletResponse response) {
		//获取客户端提交的 接口业务类型、用户名、密码
		//验证用户名秘密的合法性  
		//生成token并返回
		result=ResultUtil.success("Hello");
		return this.result;
	}
	
	@RequestMapping(value="/create")
	public String cretoken() {
		return JwtUtil.generateToken("wuxuecheng");
	}

	@RequestMapping(value="/check")
	public String checktoken(String token ) {
		JwtUtil.validateToken(token);
		return "true";
	}
	
	@RequestMapping(value="/api/read")
	public String read() {
		return "wuxuecheng";
	}
	
	
	
	
}
