### Java 相关框架 - Spring
> #### 1. Spring 技术发展
> #### 2. Spring 框架设计 *
> #### 3. Spring AOP 详解 *
> #### 4. Spring Bean 核心原理 *
> #### 5. Spring XML 配置原理 *
> #### 6. Spring Messaging 等技术
* 课后作业：
-[ ] 1.（选做）使 Java 里的动态代理，实现一个简单的 AOP。
-[x] 2.（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）。
-[ ] 3.（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如：Student/Klass/School。
-[ ] 4.（选做）改进
>-[ ] i.（挑战）将网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式。
>-[ ] ii.（挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现。
>-[ ] iii.（中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息。
>-[ ] iv.（中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP。
>-[ ] v.（超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP。
---
### Java 相关框架 - Spring Boot
> #### 1. 从 Spring 到 Spring Boot
> #### 2. Spring Boot 核心原理 *
> #### 3. Spring Boot Starter 详解 *
> #### 4. JDBC 与数据库连接池 *
> #### 5. ORM框架：Hibernate/MyBatis *
> #### 6. Spring 集成 ORM/JPA *
> #### 7. Spring Boot 集成 ORM/JPA
* 课后作业：
-[ ] 1.（选做）总结一下，单例的各种写法，比较它们的优劣。
-[ ] 2.（选做）maven/spring 的 profile 机制，都有什么用法？
-[ ] 3.（选做）总结 Hibernate 与 MyBatis 的各方面异同点。
-[ ] 4.（必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
-[ ] 5.（选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。
-[x] 6.（必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法
>-[x] i. 使用 JDBC 原生接口，实现数据库的增删改查操作。
>-[x] ii. 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。 
>-[x] iii. 配置 Hikari 连接池，改进上述操作。

* 附加题： 
-[ ] 1.（挑战）基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存 60 秒。
-[ ] 2.（挑战）自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。
-[ ] 3.（挑战）基于 MyBatis 实现一个简单的分库分表 + 读写分离 + 分布式 ID 生成方案。