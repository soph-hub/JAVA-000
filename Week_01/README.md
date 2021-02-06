### JVM 核心技术 - 基础知识
> #### 1. JVM 概念
> #### 2. Java 字节码技术
> #### 3. JVM 类加载器 *
> #### 4. JVM 内存模型 *
> #### 5. JVM 启动参数

* 课后作业：
-[x] 1.（选做）自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码。
-[x] 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
-[x] 3.（必做）画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
-[ ] 4.（选做）检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。
---

### JVM 核心技术 - 工具与GC策略
> #### 1. JDK 内置命令行工具 *
> #### 2. JDK 内置图形化工具 *
> #### 3. GC 的背景与一般原理
> #### 4. Serial GC/Parallel GC *
> #### 5. CMS GC/G1 GC *
> #### 6. ZGC/Shenandoah GC

* 课后作业：
-[ ] 1.（选做）本机使用 G1 GC 启动一个程序，仿照课上案例分析一下 JVM 情况。