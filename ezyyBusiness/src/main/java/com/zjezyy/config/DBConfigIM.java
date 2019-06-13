package com.zjezyy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data

@Component
/**
 * 将application.properties配置文件中配置自动封装到实体类字段中
 * @author Administrator
 */
@ConfigurationProperties(prefix = "spring.datasource.im") // 注意这个前缀要和application.properties文件的前缀一样
public class DBConfigIM {
	private String url;
	// 比如这个url在properties中是这样子的mysql.datasource.test1.username = root
	private String username;
	private String password;
	private int minPoolSize;
	private int maxPoolSize;
	private int maxLifetime;
	private int borrowConnectionTimeout;
	private int loginTimeout;
	private int maintenanceInterval;
	private int maxIdleTime;
	private String testQuery;
	
	private String validationQuery;
	private Boolean testWhileIdle;
	private Boolean testOnBorrow;
	private Boolean testOnReturn;
	private int timeBetweenEvictionRunsMillis;
}
