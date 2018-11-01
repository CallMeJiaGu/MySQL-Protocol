基于MySQL网络协议实现自己的数据库驱动。主要是利用Wireshark解析MySQL的packet，使用Socket实现通信。具体实现思路可以点击[个人博客](https://callmejiagu.github.io/)查看

实现MySQL的查询功能可以看下面列子

```java
package com.jiagu.mysql.CURD;

/**
 * Created by jintx on 2018/11/1.
 */
public class QueryTest {

    public static void main(String args[]) throws Exception{
        Query query = new Query();
        String host = "192.168.43.97";//数据库ip地址
        int port = 3306;//端口号
        String user = "root";//用户名
        String password = "tdlab401";//密码
        String dataBase = "data";//操作的数据库名
        String sqlStr = "SELECT name,author FROM `paper` limit 0,2;";//查询语句
        query.query(host,port,user,password,dataBase,sqlStr);//输出结果
    }

}
```

结果：
> 一种基于混沌的敏感数据加密算法,司德成,
> 水下无线电能传输和信号接口系统设计和分析,周世鹏,

