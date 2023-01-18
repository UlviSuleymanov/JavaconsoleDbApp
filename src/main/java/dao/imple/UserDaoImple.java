package dao.imple;

import bean.Country;
import bean.Skill;
import bean.User;
import bean.UserSkill;
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
        Country country = new Country(nationalityId, null, nationalityStr);
        Country birthPlace = new Country(birthPlaceId, birthPlaceStr, null);
        return new User(id, name, surname, email, phone, birthDate, country, birthPlace);
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


    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        int userId = rs.getInt("user_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");
        return new UserSkill(id, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement statement = c.prepareStatement("select " +
                    "u.*," +
                    "us.skill_id," +
                    "s.name as skill_name," +
                    "us power from" +
                    "user_skill us" +
                    "left join user u on us.user_id=u.id" +
                    "left join skill s on us.skill_id=s.id" +
                    "where" +
                    "us.user_id=?");
            statement.setInt(1, userId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
