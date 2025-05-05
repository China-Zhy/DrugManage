> 基于 SpringBoot + MyBatis + MySQL + Layui + Thymeleaf 的药品管理系统。
- 作者：张宏业
- 博客：https://blog.csdn.net/SuperCodeZhy
- 邮箱：3030695949@qq.com

  ![我爱黑花](https://www.helloimg.com/i/2025/01/07/677cd94cc2737.jpg)

***

> 说明：此项目是课程任务，因此以训练为主，下面记录一下本项目中我学到的一些知识
1. 对于CPU密集型任务，比如循环，可以使用并行流 parallelStream() 来利用多核CPU，提高处理速度。
2. 优化数据库查询：减少数据库查询的次数，使用缓存（如Redis）来存储频繁查询的数据，减少不必要的I/O操作。
3. 内存管理优化：通过使用对象池（如 Apache Commons Pool）来管理资源，减少频繁的对象创建和销毁，提高内存使用效率。