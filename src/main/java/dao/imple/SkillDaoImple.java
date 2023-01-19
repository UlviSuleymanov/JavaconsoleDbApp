package dao.imple;

import dao.inter.AbstractDao;
import dao.inter.SkillDaoInter;
import entity.Skill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImple extends AbstractDao implements SkillDaoInter {
    private Skill getSkill(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Skill skill = new Skill(id, name);
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<>();
        try (Connection c = connect()) {
            Statement statement = c.createStatement();
            statement.execute("select * from skill");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Skill skill = getSkill(resultSet);
                result.add(skill);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
