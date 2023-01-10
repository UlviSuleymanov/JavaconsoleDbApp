package org.example.main;

import org.example.bean.User;
import org.example.dao.imple.UserDaoImple;
import org.example.dao.inter.UserDaoInter;


public class Main {
    public static void main(String[] args) throws Exception {
        UserDaoInter userDao = new UserDaoImple();
        User u = userDao.getById(1);
        u.setName("Efqan");
        u.setName("Ramizov");
        userDao.updateUser(u);
    }
}