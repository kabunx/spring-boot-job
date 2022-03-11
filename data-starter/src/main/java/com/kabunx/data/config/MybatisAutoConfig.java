package com.kabunx.data.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.kabunx.data.mybatis.injector.PlusSqlInjector;
import com.kabunx.data.property.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisAutoConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        return new MybatisPlusInterceptor();
    }

    @Bean
    public PlusSqlInjector plusSqlInjector() {
        return new PlusSqlInjector();
    }
}
