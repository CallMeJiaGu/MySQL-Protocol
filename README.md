基于MySQL网络协议实现自己的数据库驱动。主要是利用`Wireshark`解析MySQL的packet，使用`Socket`实现通信。具体实现思路可以点击[个人博客](https://callmejiagu.github.io/2018/10/26/%E5%9F%BA%E4%BA%8EMySQL%E7%BD%91%E7%BB%9C%E5%8D%8F%E8%AE%AE-%E7%BC%96%E5%86%99%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8/)查看。

在实现功能，借鉴了 [sea-boat](https://github.com/sea-boat/mysql-protocol)的packet实现，在其基础上进行功能块的编写，如查询功能可以看下面列子

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

