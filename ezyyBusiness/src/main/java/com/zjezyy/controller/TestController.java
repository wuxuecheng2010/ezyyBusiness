package com.zjezyy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.github.pagehelper.PageHelper;
import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;

@Controller
@RequestMapping(value="/test")
public class TestController {
	@Autowired
	MccProductMapper mccProductMapper;
	@Autowired
	TbProductinfoMapper TbProductinfoMapper;
	 @Autowired
	 private ThymeleafViewResolver thymeleafViewResolver;
	
	@RequestMapping(value="/text")
	public ModelAndView t1() {
		ModelMap mp=new ModelMap();
		mp.put("name", "wuxuecheng");
		return new ModelAndView("test/text",mp);
	}
	
	//通过ModelAndView 来返回模板页面代码
	@RequestMapping(value="/table")
	public ModelAndView t2() {
		ModelMap mp=new ModelMap();
		List<String> list=new ArrayList<>();
		list.add("shanghai");
		list.add("hangzhou");
		mp.put("addresslist", list);
		mp.put("name", "wuxuecheng");
		return new ModelAndView("test/table",mp);
	}
	
	
	    //通过ThymeleafViewResolver解析器 来返回模板页面代码
		@RequestMapping(value="/tablex")
		@ResponseBody
		public String t3(HttpServletRequest request, HttpServletResponse response) {
			ModelMap mp=new ModelMap();
			List<String> list=new ArrayList<>();
			list.add("shanghai");
			list.add("hangzhou");
			mp.put("addresslist", list);
			mp.put("name", "wuxuecheng");
			
			WebContext webContext = new WebContext(request,
	                response,
	                request.getServletContext(),
	                request.getLocale(),
	                mp);
			
			String html = thymeleafViewResolver.getTemplateEngine().process("test/table", webContext);
			
			return html;
		}
	
	//打开页面main  通过里面的ajax代码访问上面的控制器方法
	@RequestMapping(value="/main")
	public ModelAndView main() {
		ModelMap mp=new ModelMap();
		
		return new ModelAndView("test/main",mp);
	}
	
	
	/*https://blog.csdn.net/qq_28988969/article/details/78082116
		1.添加pom文件依赖
		<!-- springboot整合mybatis -->
		<dependency>
		    <groupId>org.mybatis.spring.boot</groupId>
		    <artifactId>mybatis-spring-boot-starter</artifactId>
		    <version>1.3.1</version>
		</dependency>

		<!-- springboot分页插件 -->
		<dependency>
		    <groupId>com.github.pagehelper</groupId>
		    <artifactId>pagehelper-spring-boot-starter</artifactId>
		    <!-- 特别注意版本问题, 看到评论以后得以纠正 -->
		    <version>1.2.3</version>
		</dependency>
		
		2、直接使用
		//使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        List<GoodsType> typeList = typeDao.getList();
		*/
	
	//mybaties 分页插件测试   mysql
	@RequestMapping(value="/page")
	@ResponseBody
	public List<MccProduct> t4(HttpServletRequest request, HttpServletResponse response) {
		PageHelper.startPage(1, 2);
		List<MccProduct> list=mccProductMapper.getAll();
		return list;
	}
	
	
	//mybaties 分页插件测试   oracle
		@RequestMapping(value="/page2")
		@ResponseBody
		public List<TbProductinfo> t5(HttpServletRequest request, HttpServletResponse response) {
			PageHelper.startPage(1, 2);
			List<TbProductinfo> list=TbProductinfoMapper.getProductListForB2B(201);
			return list;
		}
		
		//分页代码展示测试 http://10.0.2.11:8099/bi/test/pageful?pageno=24&pagesize=150
		@RequestMapping(value="/pageful")
		public ModelAndView t6(HttpServletRequest request, HttpServletResponse response) {
			//获取总行数
			int count=TbProductinfoMapper.getProductListCount();
			
			//获取页码及页size
			int pageno=Integer.valueOf(request.getParameter("pageno"));
			int pagesize=Integer.valueOf(request.getParameter("pagesize"));
			PageHelper.startPage(pageno,pagesize );
			List<TbProductinfo> list=TbProductinfoMapper.getProductListAll();
			
			ModelMap map=new ModelMap();
			
			map.put("count", count);
			map.put("data", list);
			map.put("pageno", pageno);
			map.put("pagesize", pagesize);
			map.put("pagecount",Math.ceil( count/pagesize));
			return new ModelAndView("test/pageful",map);
		}
	

}
