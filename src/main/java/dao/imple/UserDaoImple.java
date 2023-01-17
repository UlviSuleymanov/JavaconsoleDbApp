package dao.imple;

import bean.Nationality;
import bean.User;
import dao.inter.AbstractDao;
import dao.inter.UserDaoInter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImple extends AbstractDao implements UserDaoInter {
    private User getuser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String phone = rs.getNString("phone");
        String email = rs.getNString("email");
        int nationalityId = rs.getInt("nationality_id");
        int birthPlaceId = rs.getInt("birthplace_id");
        String nationalityStr = rs.getString("nationality");
        String birthPlaceStr = rs.getString("birthplace");
        Date birthDate = rs.getDate("birthdate");
        Nationality nationality = new Nationality(nationalityId, nationalityStr, null);
        Nationality birthPlace = new Nationality(birthPlaceId, null, birthPlaceStr);
        return new User(id, name, surname, email, phone, birthDate, nationality, birthPlace);
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute("select u.*, " +
                    "n.name as nationality," +
                    " c.country_name as birthplace from user u " +
                    " left join nationality n  on u.nationality_id = n.id " +
                    "left join nationality c on u.birthplace_id = c.id");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                User u = getuser(resultSet);
                result.add(u);
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
                    "         left join resume.nationality c on u.birthplace_id = c.id where u.id=" + userId);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getNString("phone");
                String email = resultSet.getNString("email");
                result = getuser(resultSet);
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
