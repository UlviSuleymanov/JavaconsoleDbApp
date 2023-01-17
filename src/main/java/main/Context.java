package main;

import dao.imple.UserDaoImple;
import dao.inter.UserDaoInter;

public class Context {
    public static UserDaoInter instanceUserDao(){
        return new UserDaoImple();
    }
}
