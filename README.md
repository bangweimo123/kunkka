#Redissonx - A plugin for redisson api with manage system

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Redissonx 是一款基于redisson api做二次开发的项目,在redisson api基础上增加了category[用于对key的分组]概念，并且添加可视化后台[https://gitlab.lifesense.com/arch/redissonx-front]

#Features

#Usages
可以参考项目中[redissonx-demo]依赖客户端使用

因为依赖了阿波罗,需要在依赖项中添加[lx-arch-redissonx]
主要记录了zk地址

添加maven依赖
```
 <dependency>
    <groupId>com.leshiguang.arch</groupId>
    <artifactId>redissonx-client</artifactId>
    <version>${需要依赖的版本}</version>
 </dependency>
```

添加spring bean
```
 <bean id="redissonxClient" class="com.leshiguang.arch.redissonx.spring.RedissonxBeanFactory">
    <property name="clusterName" value="{{redissonx后台的连接名}}"></property>
 </bean>
```

添加apollo依赖
```
    <apollo:config/>
    <apollo:config namespaces="lx-arch.redissonx" order="1"/>
```

后台管理平台使用:
//待补充
