package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        foo();
    }

    public static void foo() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306";
        String username = "root";
        String password = "12345";
        Connection c = DriverManager.getConnection(url, username, password);
        Statement statement = c.createStatement();
        statement.execute("select * from user");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String phone = resultSet.getNString("phone");

        }
    }

}