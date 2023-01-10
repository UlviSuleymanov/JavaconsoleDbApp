package org.example.dao.imple;

import org.example.bean.User;
import org.example.dao.inter.UserDaoInter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImple implements UserDaoInter {
    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connect().createStatement();
            statement.execute("select * from user");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getNString("phone");
                String email = resultSet.getNString("email");
                result.add(new User(id, name, surname, email, phone));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {
        try {
            Statement statement = connect().createStatement();
            return statement.execute("update user set name= 'Ulvi' where id = 1");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean removeUser(int id) {
        try {
            Statement statement = connect().createStatement();
            return statement.execute("delete from user where id = 1");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "12345";
        Connection c = DriverManager.getConnection(url, username, password);
        return c;
    }

}
