package com.zjezyy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjezyy.entity.Result;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.utils.JwtUtil;
import com.zjezyy.utils.ResultUtil;

@Controller
@ResponseBody
@RequestMapping(value="/authorize")
public class AuthorizeController extends BaseController{

	@Autowired
	AuthorityService AuthorityServiceImpl;
	
	//获取token令牌
	@RequestMapping(value="/gettoken")
	public Result getToken(HttpServletRequest request, HttpServletResponse response) {
		//获取客户端提交的 接口业务类型、用户名、密码
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		//生成token并返回
		String token =AuthorityServiceImpl.genToken(username, password);
		return ResultUtil.success(token);
	}
	
	
	
	// 定义过滤器失败的重定向地址，作统一异常处理
	@RequestMapping(value = "/tokenerr")
	public String filterErr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Exception e = (Exception)request.getAttribute("exception");
		if (e != null)
			throw  e;
		else
			throw new BusinessException( "别烦我，你这个人类",0000);
	}
	
	/*
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
	*/
	
	
	
}
