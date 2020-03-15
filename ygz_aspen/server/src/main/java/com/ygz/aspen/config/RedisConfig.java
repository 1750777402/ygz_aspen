//package com.ygz.aspen.config;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPoolConfig;
//
//
//@Configuration
//public class RedisConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//
//
//    @Value("${spring.oldredis.host}")
//    private String hostSpare;
//
//    @Value("${spring.oldredis.port}")
//    private String portSpare;
//
//    @Value("${spring.oldredis.password}")
//    private String passwordSpare;
//
//    @Value("${spring.biredis.host}")
//    private String hostBI;
//
//    @Value("${spring.biredis.port}")
//    private String portBI;
//
//    @Value("${spring.biredis.password}")
//    private String passwordBI;
//
//
//
//    //最大空闲连接数
//    private static final int MAX_IDLE = 50;
//    //最大连接数
//    private static final int MAX_TOTAL = 100;
//    //建立连接最长等待时间
//    private static final long MAX_WAIT_MILLIS = 5000;
//
//
//    //配置工厂
//    private RedisConnectionFactory connectionFactory(String host, int port, String password) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPort(port);
//
//        if (!StringUtils.isEmpty(password)) {
//            jedisConnectionFactory.setPassword(password);
//        }
//        jedisConnectionFactory.setPoolConfig(poolConfig());
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }
//
//    //连接池配置
//    private JedisPoolConfig poolConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(MAX_IDLE);
//        poolConfig.setMaxTotal(MAX_TOTAL);
//        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
//        poolConfig.setTestOnBorrow(false);
//        return poolConfig;
//    }
//
//    @Primary
//    @Bean(name = "redisTemplateMaster")
//    public StringRedisTemplate redisTemplateMaster() {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(
//                connectionFactory(host, Integer.parseInt(port), password));
//
//        return template;
//    }
//
////    @Bean(name = "redisTemplateOld")
////    public StringRedisTemplate redisTemplateSpare() {
////        StringRedisTemplate template = new StringRedisTemplate();
////        template.setConnectionFactory(
////                connectionFactory(hostSpare, Integer.parseInt(portSpare), passwordSpare));
////        return template;
////    }
//
//}
//
