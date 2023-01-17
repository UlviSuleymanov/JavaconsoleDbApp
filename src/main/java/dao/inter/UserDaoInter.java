package dao.inter;

import bean.User;
import bean.UserSkill;

import java.util.List;

public interface UserDaoInter {
    public List<User> getAll();

    public User getById(int id);

    public boolean updateUser(User u);

    public boolean addUser(User u);

    public boolean removeUser(int id);

    public List<UserSkill> getAllSkillByUserId(int userId);

}
