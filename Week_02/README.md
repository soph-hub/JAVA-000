JVM 核心技术 -- 调优分析 && 面试经验
1. GC 日志解读与分析 *
2. JVM 线程堆栈数据分析
3. 内存分析与相关工具 *
4. JVM 问题分析调优经验
5. GC 疑难情况问题分析
6. JVM 常见面试题汇总 *

NIO 模型 && Netty 入门
1. Java Socket 编程：如何基于 Socket 实现 Server *
2. 深入讨论 IO：Server 处理时到底发生了什么 *
3. IO 模型与相关概念：怎么理解 NIO *
4. Netty 框架简介：什么是 Netty
5. Netty 使用实例：如何使用 Netty 实现 NIO *

##macOS 使用JDK15
####问题：
    1. [warning][gc] -XX:+PrintGC is deprecated. Will use -Xlog:gc instead.
    2. [warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
    3. -XX:+PrintGCDateStamps 报错：Unrecognized VM option 'PrintGCDateStamps'
    4. -XX:+PrintGCTimeStamps 报错：Unrecognized VM option 'PrintGCTimeStamps' 
    5. -XX:+UseConcMarkSweepGC 报错 Unrecognized VM option 'UseConcMarkSweepGC'

####JVM调优分析总结
    1. 增大堆内存大小可以减少GC的次数，减小OOM发生的概率。单位时间内可以创建更多的对象。
    2. 不配置 Xms的话第一次GC时间会提前。如果知道使用多少内存，Xms和Xmx尽量设成一样的。
    3. java程序一般比较耗费内存,选择java应用服务器的时候尽量选择内存4g以上的会好些。
    4. 内存如果过小，各类GC都表现不佳。
    5. 年轻代to区放不下会放到老年代,年轻代经历多代之后依然存活就会进入老年代。
    6. 串行GC尽量不要用,高吞吐考虑并行GC,低延迟考虑CMS GC,如果内存较大考虑G1 GC。

