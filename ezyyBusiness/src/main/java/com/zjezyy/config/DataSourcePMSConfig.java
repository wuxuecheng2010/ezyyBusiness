package com.zjezyy.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

/**
 * Created by summer on 2016/11/25.
 */
//@Configuration
@MapperScan(basePackages = "com.zjezyy.mapper.pms", sqlSessionTemplateRef  = "pmsSqlSessionTemplate")
public class DataSourcePMSConfig {


	 // 配置数据源
 /**
* @Title: testDataSource
* @Description: TODO(这里用一句话描述这个方法的作用)
* @param @param testConfig
* @param @return
* @param @throws SQLException    参数
* @author wuxuecheng
* @return DataSource    返回类型
* @throws
*/
@Bean(name = "pmsDataSource")
 public DataSource testDataSource(DBConfigPMS testConfig) throws SQLException {
	 
     //Atomikos统一管理分布式事务
     AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

     //用druidXADataSource方式或者上面的Properties方式都可以
     //DruidXADataSource druidXADataSource = new DruidXADataSource();
     //druidXADataSource.setUrl(testConfig.getUrl());
     //druidXADataSource.setUsername(testConfig.getUsername());
     //druidXADataSource.setPassword(testConfig.getPassword());
     
     SQLServerXADataSource druidXADataSource =new SQLServerXADataSource();
     druidXADataSource.setURL(testConfig.getUrl());
     druidXADataSource.setUser(testConfig.getUsername());
     druidXADataSource.setPassword(testConfig.getPassword());
     
     xaDataSource.setUniqueResourceName("sqlserver-pms");
     xaDataSource.setXaDataSource(druidXADataSource);
     xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
     //xaDataSource.setXaDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
     xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
     xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
     xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
     xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
     xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
     xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
     xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
     xaDataSource.setTestQuery(testConfig.getTestQuery());
     
     //LOG.info("分布式事物dataSource1实例化成功");
     return xaDataSource;
 }

 @Bean(name = "pmsSqlSessionFactory")
 public SqlSessionFactory testSqlSessionFactory(@Qualifier("pmsDataSource") DataSource dataSource)
         throws Exception {
     SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
     bean.setDataSource(dataSource);
     bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/pms/*.xml"));
     return bean.getObject();
 }

 @Bean(name = "pmsSqlSessionTemplate")
 public SqlSessionTemplate testSqlSessionTemplate(
         @Qualifier("pmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactory);
 }
 
 
	/*
    @Bean(name = "pmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pms")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pmsSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("pmsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/pms/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "pmsTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("pmsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "pmsSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("pmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

}
