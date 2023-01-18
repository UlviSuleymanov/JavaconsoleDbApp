package dao.imple;

import dao.inter.AbstractDao;
import dao.inter.UserSkillDaoInter;
import entity.Skill;
import entity.User;
import entity.UserSkill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDaoImple extends AbstractDao implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");
        return new UserSkill(null, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement statement = c.prepareStatement("select " +
                    "u.*," +
                    "us.skill_id," +
                    "s.name as skill_name, " +
                    "us.power " +
                    "FROM " +
                    " user_skill us " +
                    " LEFT join user u on us.user_id=u.id " +
                    " LEFT join skill s on us.skill_id=s.id " +
                    "WHERE " +
                    " us.user_id=?");
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
