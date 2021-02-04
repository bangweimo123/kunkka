# Kunkka-一站式缓存中间件管理平台

# 写在前面:
经过测试,针对超大redis数据库,发现scan命令实在太慢,导致最终category的数据列表无法展现的问题
还有,ucloud的分布式数据库不支持scan操作,client操作等
针对这样的情况,做了指定优化
需要在集群详情->实例管理中把连接参数的支持scan和支持client逻辑关掉
这样在详情中会展现不支持scan,就会禁止scan逻辑,只允许输入特定的参数做完全匹配
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611821128175-26f36a75-6f10-45e0-9e86-d270d8c3ed3b.png#align=left&display=inline&height=594&margin=%5Bobject%20Object%5D&name=image.png&originHeight=594&originWidth=1315&size=113084&status=done&style=none&width=1315)
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611821184003-5c1925d6-efdc-40ad-b99f-c1f46b54334e.png#align=left&display=inline&height=716&margin=%5Bobject%20Object%5D&name=image.png&originHeight=716&originWidth=1324&size=119222&status=done&style=none&width=1324)
# 一.项目介绍
Kunkka是基于Spring-redis做的Redis缓存管理平台
API基于spring-redis的标准api构建,使用方便
管理平台通过划分连接,集群,目录(categroy)几个维度,管控redis


# 二.项目地址
beta:[https://beta-kunkka.leshiguang.net/](https://beta-kunkka.leshiguang.net/)
prod:[https://kunkka.leshiguang.net/](https://kunkka.leshiguang.net/)


# 三.后台操作指南
## 3.1 实例管理
### 3.1.1 实例列表
通过区域/地址等维护删选,可以查看操作日志和监控信息
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611718980375-1e081ea5-954b-45a2-b680-892f454786c3.png#align=left&display=inline&height=722&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1444&originWidth=2834&size=529646&status=done&style=none&width=1417)
### 3.1.2 新增实例
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719043764-68d886ad-0d2f-48e2-8bbb-5b44373bab34.png#align=left&display=inline&height=325&margin=%5Bobject%20Object%5D&name=image.png&originHeight=650&originWidth=1428&size=112790&status=done&style=none&width=714)
## 3.2 集群管理
### 3.2.1 集群列表
集群相当于一个ref,管理了不同区域的不同连接,可以通过绑定租户/应用来做权限控制
一个集群下有多个category
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719083402-7b3e0215-55e5-452d-9815-d09f95404ec1.png#align=left&display=inline&height=665&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1330&originWidth=2824&size=612358&status=done&style=none&width=1412)
### 3.2.2 新增集群
集群名:为唯一标识,代码中依赖需要填写
集群模式: 单点模式/集群模式/哨兵模式/副本模式/主从模式
集群引擎: jedis/lettuce/redisson
区域: 选择一个集群能在哪些区域使用
owner/member:定义所属
### 3.2.3 集群详情
状态和操作: 已发布状态=》下线,已就绪状态=》发布
已发布状态的集群不能做任何修改,只能对category做管理
如果要下线集群,请先保证category已所有下线
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719282957-8395215e-1b94-43fb-9a82-ddefea3b4434.png#align=left&display=inline&height=689&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1378&originWidth=2818&size=505564&status=done&style=none&width=1409)


#### 3.2.3.1 集群详情/实例管理
一个集群可以对应不同的区域,每个区域都需要配置对应的连接
连接与具体的区域会有绑定关系
不同的集群模式,需要选择的参数会不同
单点模式: 只需要输入主节点
集群模式/哨兵/副本/主从: 还需要绑定子节点
只有当所有配置的区域都配置好对应的实例,集群才能正常发布
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719429323-48815b62-17b4-493d-9c36-406cdbe4c0ae.png#align=left&display=inline&height=716&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1432&originWidth=2810&size=403851&status=done&style=none&width=1405)


#### 3.2.3.2 集群详情/权限管理
集群可以绑定具体的权限关系,比如哪个租户可以使用,比如哪个应用可以调用等等
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719645438-e47d0dcb-9e29-4c65-ad48-96ac712effb6.png#align=left&display=inline&height=365&margin=%5Bobject%20Object%5D&name=image.png&originHeight=730&originWidth=2440&size=235292&status=done&style=none&width=1220)
#### 3.2.3.3 集群详情/category
这里展现了一个集群下的category列表
category概念: 一组功能相同的业务关联的key group
一个category使用同样的key模版,数据类型,是否支持多租户,是否支持热key等等
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719721169-3a02ac76-14b2-4fb1-9c2d-53375b70dd03.png#align=left&display=inline&height=706&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1412&originWidth=2840&size=508633&status=done&style=none&width=1420)
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719820623-c79c212d-e408-40ac-ad0a-14cad9d75711.png#align=left&display=inline&height=320&margin=%5Bobject%20Object%5D&name=image.png&originHeight=640&originWidth=1746&size=156298&status=done&style=none&width=873)


## 3.3 目录管理
只有已发布的category才会显示在目录列表里
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719939710-4892c2e7-d6fe-40a8-94b0-59e22e02d9eb.png#align=left&display=inline&height=661&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1322&originWidth=2828&size=944469&status=done&style=none&width=1414)
### 3.3.1 category详情
#### 3.3.1.1 无租户相关category
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611719971285-205ffa1a-d271-41b3-8881-d5a7a7b0134b.png#align=left&display=inline&height=719&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1438&originWidth=2846&size=430683&status=done&style=none&width=1423)
可以新增具体的值
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611720019767-3a5492ca-4873-488d-af8a-6743dbcd49c4.png#align=left&display=inline&height=347&margin=%5Bobject%20Object%5D&name=image.png&originHeight=694&originWidth=1144&size=85196&status=done&style=none&width=572)
#### 3.3.2.2 有租户相关信息
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611720061511-dee9873c-fd86-4c2d-8cf3-920bcb798fb5.png#align=left&display=inline&height=720&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1440&originWidth=2834&size=395591&status=done&style=none&width=1417)
# 四.如何接入
## 4.1 依赖相关
### 4.1.1 依赖maven
kunkka.version=0.0.2-SNAPSHOT
```xml
<dependency>
  <groupId>com.leshiguang.arch.kunkka</groupId>
  <artifactId>kunkka-client</artifactId>
  <version>${kunkka.version}</version>
</dependency>
```
### 4.1.2 spring注入
因为使用apollo作为zk配置项的依赖,所以需要依赖apollo
```xml
<apollo:config/>
<apollo:config namespaces="lx-arch.redissonx" order="1"/>    
<bean id="chaosKunkkaClient" class="com.leshiguang.arch.kunkka.client.spring.KunkkaClientFactory">
  <constructor-arg name="clusterName" value="chaos"/>
</bean>
<bean id="deviceKunkkaClient" class="com.leshiguang.arch.kunkka.client.spring.KunkkaClientFactory">
  <constructor-arg name="clusterName" value="device"/>
</bean>
```
### 4.1.3 springboot注入
```java
@Bean
public KunkkaClientFactory chaosKunkkaClient() {
    return new KunkkaClientFactory("chaos");
}
@Bean
public KunkkaClientFactory deviceKunkkaClient() {
    return new KunkkaClientFactory("device");
}
```
## 4.2 API使用方式
### 4.2.1 String类型(基础类型)


```java
public class KunkkaStringExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testSet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.set("2323");
    }

    @Test
    public void testGet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.get();
    }

    @Test
    public void testSetIfAbsent() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.setIfAbsent("2323");
    }

    @Test
    public void testSetIfPresent() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.setIfPresent("32");
    }

    @Test
    public void testGetAndSet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        String old = ops.getAndSet("34");
    }
}
```
### 4.2.2 Hash类型
```java
public class KunkkaHashExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testPut() {
        StoreKey storeKey = new StoreKey("testHash", "32", 44);
        BoundHashOperations<StoreKey, String, Long> ops = chaosKunkkaClient.boundHashOps(storeKey);
        ops.put("a", 432l);
        ops.put("b", 4324l);
    }

    @Test
    public void testMultiGet() {
        StoreKey storeKey = new StoreKey("testHash", "32", 44);
        BoundHashOperations<StoreKey, String, Long> ops = chaosKunkkaClient.boundHashOps(storeKey);
        List<Long> result = ops.multiGet(Arrays.asList("a", "b"));
        System.out.println(result);
    }
}
```
### 4.2.3 ZSet类型
```java
public class KunkkaZSetExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testZSet", "32");
        BoundZSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundZSetOps(storeKey);
        ops.add("a", 0.1);
        ops.add("b", 0.2);
    }

    @Test
    public void testRangeByScore() {
        StoreKey storeKey = new StoreKey("testZSet", "32");
        BoundZSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundZSetOps(storeKey);
        Set<String> values = ops.rangeByScore(0, 1);
        System.out.println(values);
    }
}

```
### 4.2.4 Set类型
```java
public class KunkkaSetExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        ops.add("a");
        ops.add("b");
    }

    @Test
    public void testRandomMembers() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        List<String> value = ops.randomMembers(100l);
        System.out.println(JSON.toJSON(value));
    }

    @Test
    public void testSize() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        Long size = ops.size();
        System.out.println(size);
    }
}
```
### 4.2.5 List类型
```java
public class KunkkaListExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testPush() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        //左侧添加一个 "a" 输出["a"]
        ops.leftPush("a");
        //左侧添加一个 "b" 输出["b","a"]
        ops.leftPush("b");
        //右侧添加一个"c" 输出 ["b","a","c"]
        ops.rightPush("c");
        //右侧插入一个"d" 输出["b","a","c","d"]
        ops.rightPush("d");
        //以c为中间点,在C的右边插入记录s 输出 ["b","a","c","s","d"]
        ops.rightPush("c", "s");
        //以c为中间点,在C的左边插入记录g 输出 ["b","a","g","c","s","d"]
        ops.leftPush("c", "g");
    }

    @Test
    public void testPop() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        for (int i = 0; i < ops.size() - 1; i++) {
            String result = ops.leftPop();
            System.out.println(result);
        }
        for (int i = 0; i < ops.size() - 1; i++) {
            String result = ops.rightPop();
            System.out.println(result);
        }
    }

    @Test
    public void testRange() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        List<String> result = ops.range(0, 100);
        System.out.println(result);
    }
}
```
### 4.2.6 Geo类型
```java
public class KunkkaGeoExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testZSet", "43");
        BoundGeoOperations<StoreKey, String> ops = chaosKunkkaClient.boundGeoOps(storeKey);
        RedisGeoCommands.GeoLocation aLocation = new RedisGeoCommands.GeoLocation("a", new Point(32.32, 23.23));
        ops.add(aLocation);
        RedisGeoCommands.GeoLocation bLocation = new RedisGeoCommands.GeoLocation("b", new Point(32.33, 23.23));
        ops.add(bLocation);
    }

    @Test
    public void testDistance() {
        StoreKey storeKey = new StoreKey("testZSet", "43");
        BoundGeoOperations<StoreKey, String> ops = chaosKunkkaClient.boundGeoOps(storeKey);
        Distance distance = ops.distance("a", "b");
        //输出 1022.0166 METERS
        System.out.println(distance);
    }
}

```
# 五.cat打点说明
任何操作都会在cat中添加打点:
规则如下:
Kunkka.${type}  里面的值 ${category}:options
比如:
string下,category=testString options=set
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611821426141-c5058d3d-1b05-4c7c-9e65-2b75857a7585.png#align=left&display=inline&height=36&margin=%5Bobject%20Object%5D&name=image.png&originHeight=36&originWidth=542&size=12972&status=done&style=none&width=542)
![image.png](https://cdn.nlark.com/yuque/0/2021/png/265873/1611821469225-edccc06b-2b8f-49a8-8760-efeebb347d76.png#align=left&display=inline&height=45&margin=%5Bobject%20Object%5D&name=image.png&originHeight=45&originWidth=537&size=7503&status=done&style=none&width=537)


### 
