开发一个spring-boot starter项目
-------
## spring-boot 配置 
spring-boot 的一大好处就是基于注解的配置和自动配置

基于注解配置举例：
spring-boot 支持yaml配置文件：[打开配置文件]  
这里我们说有一个id生成器需要再启动的时候

自动配置举例： 
[打开pom]  

我们本节课程来讲解如何开发一个starter项目
目标是产出一个jar，可以被其他项目直接引用

## demo 介绍
以基于雪花算法的id生成器为例：  
SnowFlake 算法，是 Twitter 开源的分布式 id 生成算法。
其核心思想就是：使用一个 64 bit 的 long 型的数字作为全局唯一 id。
在分布式系统中的应用十分广泛，且ID 引入了时间戳，基本上保持自增的。  

```
0 | 50 bits for counter | 4 bits for data center | 9 bits for type
```

使用场景：  
通用评论系统
一个互联网应用，有朋友圈，有文章，有视频，这些都可以让用户评论  
在这样一个场景下，我们希望使用一个评论系统，里边用一张表记录对这几类对象的评论信息  
评论表(comment)结构简要如下：  

字段         |     说明   
------------|--------------  
id          | 评论id，整型  
obj_id      | 被对象id，整型  
user_id     | 用户id， 整型  
create_t    | 创建时间，日期
content     | 评论内容，文本  

要求，仅通过id就能获取该对象的相关评论，不通过额外的字段来标识对象类别.  
类似sql:   
```sql
select id, user_id, create_t, content from comment where obj_id = 123 order by create_t desc limit 10
```
此时就要求obj_id 全局唯一，解决办法：使用雪花算法作为全局统用的id生成器.


## demo项目结构：
使用spring-boot 在线创建工具

```
idgen-demo       # 父级项目
  idgen          # 算法实现  
  idgen-examole  # 依赖idgen项目的一个样例web项目
  idgen-starter             # starter项目
  idgen-starter- example    # 依赖starter项目的一个样例web项目
```
演示创建好的项目

## spring-boot 自动配置说明：

详细文档：[developing-auto-configuration](https://docs.spring.io/spring-boot/docs/2.1.14.RELEASE/reference/html/boot-features-developing-auto-configuration.html)

自动配置(auto-configuration)底层也是使用的 @Configuration classes.   
加上一些 @Conditional 注解 annotations 作为前置条件来自动决定要配置的bean.   
通常用到的有 @ConditionalOnClass， @ConditionalOnMissingBean 等注解.   
这些注解确保只有在相关的类被发现或某些bean缺失的时候才会触发运行你自定义的自动配置.

手写的配置和自动配置本质上是没哟区别的，只是一个需要手写配置类或者bean的构造，  
后者是通过如下机制来实现spring-boot容器启动时自动运行你自定义的配置类：  
Spring Boot 在你提供的jar包里检查是否存在一个META-INF/spring.factories 文件.   
这个文件需要列举出你的配置类，示例文件内容如下:  
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.mycorp.libx.autoconfigure.LibXAutoConfiguration,\
com.mycorp.libx.autoconfigure.LibXWebAutoConfiguration
```


## 主要代码演示

各 pom 依赖
idgen-starter 空项目

## 运行演示


## homework
问题：如果一个web项目里有多个对象，如何使用idgenerator?  
实现基于redis的CacheCounter
