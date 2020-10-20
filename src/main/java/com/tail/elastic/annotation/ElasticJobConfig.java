package com.tail.elastic.annotation;


import java.lang.annotation.*;

/**
 * @author 003364
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ElasticJobConfig {
    /**
     * 任务名称
     * @return
     */
    String jobName() default "";

    /**
     * cron表达式，用于控制作业触发时间
     * @return
     */
    String cron();

    /**
     * 分片参数
     * @return
     */
    String shardingItemParameters() default "";

    /**
     * 总分片数
     * @return
     */
    int shardingTotalCount() default 1;

    /**
     * 任务描述
     * @return
     */
    String description() default "";

    /**
     * 是否自动失效转移
     * @return
     */
    boolean misfire() default false;

    /**
     * 错过是否重执行
     * @return
     */
    boolean failover() default false;

    /**
     * 作业是否启动时禁止
     * @return
     */
    boolean disabled() default false;

    /**
     * 任务全路径
     * @return
     */
    String jobClassFullPath() default "";
}
