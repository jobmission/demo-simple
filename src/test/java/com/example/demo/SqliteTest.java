package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class SqliteTest {

    @Disabled
    @Test
    public void test() {
        try {
            // 连接SQLite的JDBC
            Class.forName("org.sqlite.JDBC");
            // 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/data/zieckey.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate(
                "create table IF NOT EXISTS tbl1(ID INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(20) unique, salary int);");// 创建一个表，两列

            stat.executeUpdate("insert into tbl1 values(NULL,'" + RandomPersonUtil.getChineseName() + "',"
                + RandomPersonUtil.getNum(10000, 50000) + ");"); // 插入数据
            stat.executeUpdate("insert into tbl1 values(NULL,'" + RandomPersonUtil.getChineseName() + "',"
                + RandomPersonUtil.getNum(10000, 50000) + ");"); // 插入数据
            stat.executeUpdate("insert into tbl1 values(NULL,'" + RandomPersonUtil.getChineseName() + "',"
                + RandomPersonUtil.getNum(10000, 50000) + ");"); // 插入数据
            stat.executeUpdate("insert into tbl1 values(NULL,'" + RandomPersonUtil.getChineseName() + "',"
                + RandomPersonUtil.getNum(10000, 50000) + ");"); // 插入数据
            ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("id = " + rs.getString("id") + ", "); // 列属性一
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性
                System.out.println("salary = " + rs.getString("salary")); // 列属性
            }
            rs.close();
            conn.close(); // 结束数据库的连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
