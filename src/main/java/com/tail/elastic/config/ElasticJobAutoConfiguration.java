package com.tail.elastic.config;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobSettingsAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobStatisticsAPI;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Optional;
import com.tail.elastic.listener.CommonElasticJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elastic job自动配置类
 *
 * @author 003364
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.elastic.job",name = {"namespace","zookeeper"})
@ConditionalOnClass(ElasticJobAutoConfiguration.class)
public class ElasticJobAutoConfiguration {


    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public ZookeeperRegistryCenter regCenter(@Value("${spring.elastic.job.zookeeper}") final String zookeeper,
                                             @Value("${spring.elastic.job.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(zookeeper, namespace));
    }


    @Value("${spring.elastic.job.zookeeper}")
    private String zookeeper;


    @Value("${spring.elastic.job.namespace}")
    private String nameSpace;

    @Bean()
    public JobSettingsAPI jobSettingsApi(){
        return JobAPIFactory.createJobSettingsAPI(zookeeper, nameSpace, Optional.fromNullable(null));
    }

    @Bean()
    public JobOperateAPI jobOperateApi(){
        return JobAPIFactory.createJobOperateAPI(zookeeper, nameSpace, Optional.fromNullable(null));
    }

    @Bean()
    public JobStatisticsAPI jobStatisticsApi(){
        return JobAPIFactory.createJobStatisticsAPI(zookeeper, nameSpace, Optional.fromNullable(null));
    }

    @Bean
    public ElasticSchedulerInit elasticSchedulerInit(){
        return new ElasticSchedulerInit();
    }
    @Bean
    public CommonElasticJobListener commonElasticJobListener(){
        return new CommonElasticJobListener();
    }
}
