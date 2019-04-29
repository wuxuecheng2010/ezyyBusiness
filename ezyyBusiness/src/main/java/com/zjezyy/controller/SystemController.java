package com.zjezyy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping(value = "/system")
public class SystemController {
	// 定义过滤器失败的重定向地址，作统一异常处理
	@RequestMapping(value = "/filtererr")
	public void filterErr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Exception e = (Exception)request.getAttribute("exception");
		if (e != null)
			throw  e;

	}
}
