package main;

import bean.User;
import dao.inter.UserDaoInter;


public class Main {
    public static void main(String[] args) {
        UserDaoInter userDaoInter = Context.instanceUserDao();
        System.out.println(userDaoInter.getAll());
    }
}