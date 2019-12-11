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

import com.alibaba.druid.pool.xa.DruidXADataSource;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.zjezyy.mapper.erp2", sqlSessionTemplateRef  = "erp2SqlSessionTemplate")
public class DataSourceERP2Config {
	
	 // 配置数据源
    @Bean(name = "erp2DataSource")
    public DataSource testDataSource(DBConfigERP2 testConfig) throws SQLException {
        //Atomikos统一管理分布式事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        
        //用druidXADataSource方式或者上面的Properties方式都可以
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(testConfig.getUrl());
        druidXADataSource.setUsername(testConfig.getUsername());
        druidXADataSource.setPassword(testConfig.getPassword());
        
        xaDataSource.setUniqueResourceName("oracle-erp2");
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

    @Bean(name = "erp2SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("erp2DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //以xml文件方式 需要指定路径
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/erp2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "erp2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("erp2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


  

}
