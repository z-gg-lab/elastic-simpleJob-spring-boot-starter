# ![elastic-simpleJob-spring-boot-starter](https://socialify.git.ci/z-gg-lab/elastic-simpleJob-spring-boot-starter/image?font=Inter&language=1&owner=1&stargazers=1&theme=Dark)

#### 提供的组件

| name                     | 用法                                                         | 作用                             |
| ------------------------ | ------------------------------------------------------------ | -------------------------------- |
| @ElasticJobConfig        | @ElasticJobConfig(cron = "0/5 * * * * ?")<br>@ElasticJobConfig(cron = "${cron}") | 配置定时任务cron表达式，支持${}. |
| JobSettingsAPI           | @Resource<br> private JobSettingsAPI jobSettingsApi;         | 任务操作API.                     |
| JobOperateAPI            | @Resource<br/> private JobOperateAPI jobOperateApi;          | 任务操作API.                     |
| JobStatisticsAPI         | @Resource<br/> private JobStatisticsAPI jobStatisticsApi;    | 任务状态展示API.                 |
| CommonElasticJobListener |                                                              | 默认任务监听器.                  |

#### 使用starter

* ```xml
      <dependency>
          <groupId>com.zhutail</groupId>
          <artifactId>elastic-simpleJob-springboot-starter</artifactId>
          <version>0.0.1-SNAPSHOT</version>
      </dependency>
  ```

* 配置注册中心和任务命名空间

  * ```properties
    spring.elastic.job.zookeeper=localhost:2181
    spring.elastic.job.namespace=myJob
    ```

  * ```java
    @Slf4j
    @ElasticJobConfig(cron = "0/5 * * * * ?")
    public class MySimpleJob implements SimpleJob {
    
        @Override
        public void execute(ShardingContext shardingContext) {
            log.info("======执行任务======");
        }
    }
    ```
