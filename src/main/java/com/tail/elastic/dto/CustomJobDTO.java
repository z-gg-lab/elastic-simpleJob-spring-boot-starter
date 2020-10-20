package com.tail.elastic.dto;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *
 * @author 003364
 * @since 2020-10-20
 */
@Data
@Accessors(chain = true)
public class CustomJobDTO {

    /**
     * 任务组
     */
    private String groupName;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述信息
     */
    private String description;

    /**
     * corn执行表达式
     */
    private String cron;

    /**
     * bean实例名称
     */
    private String beanName;

    /**
     * 作业参数
     */
    private String jobParameter;
    /**
     * 分片数
     */
    private Integer shardingCount;

    /**
     * 分片参数
     */
    private String shardingItemParameters;

    /**
     * 数据
     */
    private String payload;

    /**
     * 该任务剩余执行次数
     */
    private Integer leftExecutionTimes;

    /**
     * 任务是否删除
     */
    private Boolean deleted;

    /**
     * 任务是否可用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;

    /**
     * 任务最近一次更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;

    /**
     * 任务失效时间，默认永不失效
     */
    private LocalDateTime endTime;

    private SimpleJob jobClass;

}
