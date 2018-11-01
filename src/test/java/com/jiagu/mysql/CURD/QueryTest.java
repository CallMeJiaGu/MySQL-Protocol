package com.jiagu.mysql.CURD;

/**
 * Created by jintx on 2018/11/1.
 */
public class QueryTest {

    public static void main(String args[]) throws Exception{
        Query query = new Query();
        String host = "192.168.43.97";
        int port = 3306;
        String user = "root";
        String password = "tdlab401";
        String dataBase = "data";
        String sqlStr = "SELECT name,author FROM `paper` limit 0,2;";
        query.query(host,port,user,password,dataBase,sqlStr);
    }

}
