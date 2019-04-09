package com.zjezyy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.xa.DruidXADataSource;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Created by wxc on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.zjezyy.mapper.wms", sqlSessionTemplateRef  = "wmsSqlSessionTemplate")
public class DataSourceWMSConfig {


	 // 配置数据源
   @Bean(name = "wmsDataSource")
   @Primary
   public DataSource testDataSource(DBConfigWMS testConfig) throws SQLException {
       //Atomikos统一管理分布式事务
       AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
       //用druidXADataSource方式或者上面的Properties方式都可以
       DruidXADataSource druidXADataSource = new DruidXADataSource();
       druidXADataSource.setUrl(testConfig.getUrl());
       druidXADataSource.setUsername(testConfig.getUsername());
       druidXADataSource.setPassword(testConfig.getPassword());
       
       xaDataSource.setUniqueResourceName("oracle-wms");
       xaDataSource.setXaDataSource(druidXADataSource);
       xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
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

   @Bean(name = "wmsSqlSessionFactory")
   @Primary
   public SqlSessionFactory testSqlSessionFactory(@Qualifier("wmsDataSource") DataSource dataSource)
           throws Exception {
       SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
       bean.setDataSource(dataSource);
     //以xml文件方式 需要指定路径
      // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/wms/*.xml"));
       return bean.getObject();
   }

   @Bean(name = "wmsSqlSessionTemplate")
   @Primary
   public SqlSessionTemplate testSqlSessionTemplate(
           @Qualifier("wmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
       return new SqlSessionTemplate(sqlSessionFactory);
   }
	
	
    /*@Bean(name = "wmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wms")
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "wmsSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("wmsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/wms/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "wmsTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("wmsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "wmsSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("wmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

}
