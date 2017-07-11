

# web 模块 #
```
machine learning 模块
可视化后台

```
web-log-monitor-web
   - web-log-monitor-biz
   - web-log-monitor-dao
   - web-log-monitor-common
   - web-log-monitor-service-api
   - web-log-montiro-service-impl

# olep 模块 #
```
往queue里面投递日志

```

# storm模块 #
```
流式计算模块
```
web-log-monitor-storm
 - web-log-monitor-dao
 - web-log-monitor-common
 - web-log-monitor-integration
 
在 linux 下面 如何 执行 storm topology 的 jar
命令格式：storm jar 【jar路径】 【拓扑包名.拓扑类名】 【拓扑名称】
样例：storm jar /storm-starter.jar test.WordCountTopology wordcountTop

storm kill topologyName

