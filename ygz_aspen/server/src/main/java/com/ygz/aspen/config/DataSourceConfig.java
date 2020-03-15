package com.ygz.aspen.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PaginationInterceptor;
import com.ygz.aspen.constant.DataSourceConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
//启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableTransactionManagement
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.ygz.aspen.dao", sqlSessionTemplateRef  = "sqlSessionTemplate")
public class DataSourceConfig {

    @Autowired
    private AbstractEnvironment environment;

    //获取并设置连接池公共属性
    private void setDruidProperty(DruidDataSource dataSource) {
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(8);
        dataSource.setMaxActive(30);
        dataSource.setMaxWait(5000);
        dataSource.setValidationQuery("SELECT 1");
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DruidDataSource masterDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        setDruidProperty(druidDataSource);
        return druidDataSource;
    }

//    @Bean(initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.read")
//    public DruidDataSource readDataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        setDruidProperty(druidDataSource);
//        return druidDataSource;
//    }
//
//
//    @Bean(initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.read2")
//    public DruidDataSource read2DataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        setDruidProperty(druidDataSource);
//        return druidDataSource;
//    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     *
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource/**,
                                        @Qualifier("readDataSource") DataSource readDataSource,
                                        @Qualifier("read2DataSource") DataSource read2DataSource,
                                        @Qualifier("oldMasterDataSource") DruidDataSource oldMasterDataSource,
                                        @Qualifier("biDataSource") DataSource biDataSource,
                                        @Qualifier("blackholeDataSource") DataSource blackholeDataSource */)
    {
        //按照目标数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceConstant.DATA_SOURCE_MASTER, masterDataSource);
//        targetDataSources.put(DataSourceConstant.DATA_SOURCE_READ,readDataSource);
//        targetDataSources.put(DataSourceConstant.DATA_SOURCE_READ_2,read2DataSource);
//        targetDataSources.put(DataSourceConstant.DATA_SOURCE_OLD_MASTER, oldMasterDataSource);

//        DruidDataSource druidDataSource= (DruidDataSource) biDataSource;
//        if(StringUtils.isNotBlank(druidDataSource.getRawJdbcUrl())){
//            targetDataSources.put(DataSourceConstant.DATA_SOURCE_BI,biDataSource);
//        }
//        DruidDataSource druidDataSourceBlackhole= (DruidDataSource) blackholeDataSource;
//        if(StringUtils.isNotBlank(druidDataSourceBlackhole.getRawJdbcUrl())){
//            targetDataSources.put(DataSourceConstant.DATA_SOURCE_BLACKHOLE,blackholeDataSource);
//        }

        //采用是想AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        //设置默认的数据源，当拿不到数据源时，使用此配置

        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {

        String active = environment.getActiveProfiles().length == 0 ? "" : environment.getActiveProfiles()[0];

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);
        factoryBean.setTypeAliasesPackage("com.ygz.aspen.dao.*");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));

//        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//        mybatisConfiguration.setMapUnderscoreToCamelCase(false);
//        if (StringUtils.isBlank(active) && ("dev".equals(active) || "pre".equals(active) || "prep".equals(active))) {
//            mybatisConfiguration.setLogImpl(StdOutImpl.class);
//        }
//        factoryBean.setConfiguration(mybatisConfiguration);
        //分页插件
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        Interceptor[] plugins = {paginationInterceptor};
//        factoryBean.setPlugins(plugins);

        return factoryBean.getObject();
    }


    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
