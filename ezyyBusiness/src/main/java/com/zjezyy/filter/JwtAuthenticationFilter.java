package com.zjezyy.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.service.AuthorityService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="authfilter",urlPatterns="/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    
    @Autowired
	AuthorityService AuthorityServiceImpl;
    
    @Autowired
    SettingService settingServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IllegalStateException,ServletException, IOException {
        try {
            if(isProtectedUrl(request)) {
            	
            	String headerName=settingServiceImpl.getEzyySettingValue(EzyySettingKey.API_HEADER_NAME);
                String token = request.getHeader(headerName);
                //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
                //JwtUtil.validateToken(token);
                AuthorityServiceImpl.checkToken(token);
            }
        } 
        catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        	request.setAttribute("exception", e);
        	request.getRequestDispatcher("/authorize/tokenerr").forward(request, response);
            return;
        }
        //如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api 
        filterChain.doFilter(request, response);
    }


    //我们只对地址 /api 开头的api检查jwt. 不然的话登录/login也需要jwt
    private boolean isProtectedUrl(HttpServletRequest request) {
    	String path=request.getServletPath();
    	boolean flag= pathMatcher.match("/**/api/**", request.getServletPath());
        return flag;
    }

}