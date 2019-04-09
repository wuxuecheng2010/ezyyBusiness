package com.zjezyy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zjezyy.config.DBConfigERP;
import com.zjezyy.config.DBConfigB2B;
import com.zjezyy.config.DBConfigWMS;

@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages="com.zjezyy.mapper")
//@EnableScheduling
@EnableConfigurationProperties(value= {DBConfigERP.class,DBConfigWMS.class,DBConfigB2B.class})

public class EzyyBusinessApplication {

	private static ApplicationContext ctx;
	public static void main(String[] args) {
		ctx=SpringApplication.run(EzyyBusinessApplication.class, args);
		 try {
	            String host = InetAddress.getLocalHost().getHostAddress();
	            TomcatServletWebServerFactory tomcatServletWebServerFactory= (TomcatServletWebServerFactory) ctx.getBean("tomcatServletWebServerFactory");
	            int port = tomcatServletWebServerFactory.getPort();
	            String contextPath = tomcatServletWebServerFactory.getContextPath();
	            System.out.println("http://"+host+":"+port+contextPath+"/");
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }

	}

}
