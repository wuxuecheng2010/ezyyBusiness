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

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.zjezyy.mapper.b2b", sqlSessionTemplateRef  = "b2bSqlSessionTemplate")
public class DataSourceB2BConfig {


	 // 配置数据源
  @Bean(name = "b2bDataSource")
  public DataSource testDataSource(DBConfigB2B testConfig) throws SQLException {
      //Atomikos统一管理分布式事务
      AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

      //用druidXADataSource方式或者上面的Properties方式都可以
      DruidXADataSource druidXADataSource = new DruidXADataSource();
      druidXADataSource.setUrl(testConfig.getUrl());
      druidXADataSource.setUsername(testConfig.getUsername());
      druidXADataSource.setPassword(testConfig.getPassword());
      
      xaDataSource.setUniqueResourceName("mysql-b2b");
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

  @Bean(name = "b2bSqlSessionFactory")
  public SqlSessionFactory testSqlSessionFactory(@Qualifier("b2bDataSource") DataSource dataSource)
          throws Exception {
      SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
      bean.setDataSource(dataSource);
      //以xml文件方式 需要指定路径
      //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/b2b/*.xml"));
      return bean.getObject();
  }

  @Bean(name = "b2bSqlSessionTemplate")
  public SqlSessionTemplate testSqlSessionTemplate(
          @Qualifier("b2bSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(sqlSessionFactory);
  }
	
  
  
	/*
    @Bean(name = "mapserverDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mapserver")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mapserverSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("mapserverDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/mapserver/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "mapserverTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("mapserverDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mapserverSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("mapserverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

}
