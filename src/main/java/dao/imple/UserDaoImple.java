package dao.imple;

import bean.User;
import dao.inter.AbstractDao;
import dao.inter.UserDaoInter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImple extends AbstractDao implements UserDaoInter {
    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute("select u.*," +
                    "       n.name         as nationality," +
                    "       c.country_name as birthplace" +
                    "from resume.user u" +
                    "         left join resume.nationality n on u.nationality_id = n.id" +
                    "         left join resume.nationality c on u.birthplace_id = c.id");
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
    public User getById(int userId) {
        User result = null;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute("select u.*," +
                    "       n.name         as nationality," +
                    "       c.country_name as birthplace" +
                    "from resume.user u" +
                    "         left join resume.nationality n on u.nationality_id = n.id" +
                    "         left join resume.nationality c on u.birthplace_id = c.id where u.id=" +userId);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getNString("phone");
                String email = resultSet.getNString("email");
                result = new User(id, name, surname, email, phone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("update user set name=?, surname=?, email=? ,phone =? where id=?");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPhone());
            statement.setInt(5, u.getId());
            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addUser(User u) {
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("insert into user(name,surname,email,phone) values(?,?,?,?)");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPhone());
            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return statement.execute("delete from user where id = 1");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
