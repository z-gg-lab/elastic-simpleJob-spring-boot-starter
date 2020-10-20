package com.tail.elastic.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.tail.elastic.annotation.ElasticJobConfig;
import com.tail.elastic.dto.CustomJobDTO;
import com.tail.elastic.listener.CommonElasticJobListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author 003364
 */
public class ElasticSchedulerInit implements ApplicationContextAware, InitializingBean {

    private static final String PREFIX = "$";

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private CommonElasticJobListener commonElasticJobListener;

    @Resource
    private Environment environment;

    @Override
    public void afterPropertiesSet() {
        registerJob(applicationContext);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void registerJob(ApplicationContext applicationContext) {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(ElasticJobConfig.class);
        for (String beanName : beanNamesForAnnotation) {
            Class<?> handlerType = applicationContext.getType(beanName);
            Object bean = applicationContext.getBean(beanName);
            ElasticJobConfig annotation = AnnotationUtils.findAnnotation(handlerType, ElasticJobConfig.class);
            addJobToContext(annotation, bean);
        }
    }

    /**
     * 将任务添加到容器中
     *
     * @param elasticJobConfig
     * @param bean
     */
    private void addJobToContext(ElasticJobConfig elasticJobConfig, Object bean) {
        CustomJobDTO customJob = new CustomJobDTO();
        customJob.setCron(elasticJobConfig.cron());
        customJob.setName(StringUtils.isNotBlank(elasticJobConfig.jobName()) ? elasticJobConfig.jobName() : bean.getClass().getName());
        customJob.setShardingItemParameters(elasticJobConfig.shardingItemParameters());
        customJob.setShardingCount(elasticJobConfig.shardingTotalCount());
        customJob.setBeanName(bean.getClass().getSimpleName());
        customJob.setJobClass((SimpleJob) bean);
        customJob.setDescription(elasticJobConfig.description());
        customJob.setEnabled(elasticJobConfig.disabled());
        this.parseCorn(customJob);
        LiteJobConfiguration jobConfig = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(customJob.getName(), customJob.getCron(), customJob.getShardingCount())
                        .shardingItemParameters(customJob.getShardingItemParameters())
                        .jobParameter(customJob.getJobParameter())
                        .description(customJob.getDescription())
                        .build()
                , customJob.getJobClass().getClass().getName())).overwrite(true)
                .build();
        new SpringJobScheduler(customJob.getJobClass(), regCenter, jobConfig, commonElasticJobListener).init();
    }


    /**
     * 处理corn表达式
     *
     * @param customJob
     */
    private void parseCorn(CustomJobDTO customJob) {
        if (customJob.getCron().startsWith(PREFIX)) {
            String corns = customJob.getCron().substring(customJob.getCron().indexOf("${") + 2, customJob.getCron().lastIndexOf("}"));
            customJob.setCron(environment.getProperty(corns));
        }
    }


}
