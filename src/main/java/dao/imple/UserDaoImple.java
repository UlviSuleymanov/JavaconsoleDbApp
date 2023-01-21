package dao.imple;

import dao.inter.AbstractDao;
import dao.inter.UserDaoInter;
import entity.Country;
import entity.User;

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
        String adress = rs.getNString("adress");
        String profileDescription = rs.getNString("profile_description");
        int nationalityId = rs.getInt("nationality_id");
        int birthPlaceId = rs.getInt("birthplace_id");
        String nationalityStr = rs.getString("nationality");
        String birthPlaceStr = rs.getString("birthplace");
        Date birthDate = rs.getDate("birthdate");
        Country country = new Country(nationalityId, null, nationalityStr);
        Country birthPlace = new Country(birthPlaceId, birthPlaceStr, null);
        return new User(id, name, profileDescription, adress, surname, email, phone, birthDate, country, birthPlace);
    }


    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute("select u.*, " +
                    "n.nationality as nationality," +
                    " c.name as birthplace from user u " +
                    " left join country n  on u.nationality_id = n.id " +
                    "left join country c on u.birthplace_id = c.id");
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
            statement.execute("select u.*, " +
                    "n.nationality as nationality," +
                    " c.name as birthplace from user u " +
                    " left join country n  on u.nationality_id = n.id " +
                    "left join country c on u.birthplace_id = c.id where u.id=" + userId);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
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
            PreparedStatement statement = connection.prepareStatement("update user set name=?, surname=?, email=?,profile_description=?, adress=? ,phone =? where id=?");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPhone());
            statement.setString(5, u.getAdress());
            statement.setString(6, u.getProfileDesc());
            statement.setInt(7, u.getId());
            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addUser(User u) {
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement("insert into user(name,surname,email,phone,profile_description,adress) values(?,?,?,?,?,?)");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPhone());
            statement.setString(5, u.getProfileDesc());
            statement.setString(6, u.getAdress());

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
