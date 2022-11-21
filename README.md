### 技术框架
1. 基于Jdk1.8
- [jdk8内存模型](http://www.cnblogs.com/paddix/p/5309550.html)
- [jdk8十大特性](http://www.jb51.net/article/48304.htm)
- [java bio nio aio](http://blog.csdn.net/wanglei_storage/article/details/50225779)

2. Web控制器Jersey2.25.1
- [资源常用注解](https://jersey.github.io/documentation/latest/jaxrs-resources.html)
- [启动方式](http://blog.csdn.net/u013628152/article/details/42126521)

3. Spring4.3.11管理javaBean，持久层mybatis3.4.2
- [spring原理剖析](http://bradyzhu.iteye.com/blog/2270692)
- [Spring 教程](https://www.w3cschool.cn/wkspring/)
- [mybatis中 #和$的区别](http://note.youdao.com/noteshare?id=4a97cad3801c22ef12adba9b18d13028&sub=AEDB30BC3CDE4F2F9E57E57772FAC7F9)

4. MySQL5.7.19、Elasticsearch5.5.3、MongoDB3.4.6、Redis3.2.10
- [Mysql存储引擎](http://blog.csdn.net/xifeijian/article/details/20316775)
- [Elasticsearch基本概念](https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html)
- [MongoDB教程](https://www.w3cschool.cn/mongodb/)
- [Redis教程](https://www.w3cschool.cn/redis/)

```bash
mvn clean deploy scm:checkin -Dmessage="build(tag): release tag v${revision}" scm:tag -Dtag=v${revision}
```
