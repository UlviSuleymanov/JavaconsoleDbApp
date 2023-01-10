package org.example.dao.inter;

import org.example.bean.User;

import java.util.List;

public interface UserDaoInter {
    public List<User> getAll();
    public User getById(int id);
    public boolean updateUser(User u);

    public boolean removeUser(int id);

}
