基于MySQL网络协议实现自己的数据库驱动。主要是利用Wireshark解析MySQL的packet，使用Socket实现通信。具体实现思路可以点击下方的[个人博客](https://callmejiagu.github.io/)查看 。

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
> 
> 水下无线电能传输和信号接口系统设计和分析,周世鹏,



[实现自己的数据库驱动——MySQL网络协议（一）](https://callmejiagu.github.io/2018/10/26/%E5%9F%BA%E4%BA%8EMySQL%E7%BD%91%E7%BB%9C%E5%8D%8F%E8%AE%AE-%E7%BC%96%E5%86%99%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8/)

[实现自己的数据库驱动——WireShark分析MySQL网络协议中的数据包（二）](https://callmejiagu.github.io/2018/10/26/WireShark-%E5%88%86%E6%9E%90MySQL%E7%BD%91%E7%BB%9C%E5%8D%8F%E8%AE%AE%E4%B8%AD%E7%9A%84%E6%95%B0%E6%8D%AE%E5%8C%85%EF%BC%88%E4%BA%8C%EF%BC%89/)

[实现自己的数据库驱动——MySQL协议Handshake解析（三）](https://callmejiagu.github.io/2018/10/26/MySQL%E5%8D%8F%E8%AE%AEHandshake%E8%A7%A3%E6%9E%90%EF%BC%88%E4%B8%89%EF%BC%89/)

[实现自己的数据库驱动——MySQL协议Auth Packet解析（四）](https://callmejiagu.github.io/2018/10/27/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94MySQL%E5%8D%8F%E8%AE%AEAuth-Packet%E8%A7%A3%E6%9E%90%EF%BC%88%E5%9B%9B%EF%BC%89/)

[实现自己的数据库驱动——MySQL协议OK/Error包解析（五）](https://callmejiagu.github.io/2018/10/27/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94MySQL%E5%8D%8F%E8%AE%AEOK-Error-Packet%E8%A7%A3%E6%9E%90%EF%BC%88%E4%BA%94%EF%BC%89/)

[实现自己的数据库驱动——MySQL协议Result Set包解析（六）](https://callmejiagu.github.io/2018/10/29/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94MySQL%E5%8D%8F%E8%AE%AEResult%E5%8C%85%E8%A7%A3%E6%9E%90%EF%BC%88%E5%85%AD%EF%BC%89/)

[实现自己的数据库驱动——MySQL协议Quit包解析（七）](https://callmejiagu.github.io/2018/10/29/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94MySQL%E5%8D%8F%E8%AE%AEQuit%E5%8C%85%E8%A7%A3%E6%9E%90%EF%BC%88%E4%B8%83%EF%BC%89/)

[实现自己的数据库驱动——字节位运算细节（八）](https://callmejiagu.github.io/2018/10/30/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94%E5%AD%97%E8%8A%82%E4%BD%8D%E8%BF%90%E7%AE%97%E7%BB%86%E8%8A%82%EF%BC%88%E5%85%AB%EF%BC%89/)

[实现自己的数据库驱动——Packet的read（九）](https://callmejiagu.github.io/2018/10/31/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94Packet%E7%9A%84read%EF%BC%88%E4%B9%9D%EF%BC%89/)

[实现自己的数据库驱动——Packet的write（十）](https://callmejiagu.github.io/2018/10/31/%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%B7%B1%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%A9%B1%E5%8A%A8%E2%80%94%E2%80%94Packet%E7%9A%84write%EF%BC%88%E5%8D%81%EF%BC%89/)

