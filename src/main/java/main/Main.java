package main;

import bean.User;
import dao.inter.UserDaoInter;


public class Main {
    public static void main(String[] args) {
        UserDaoInter userDao = Context.instanceUserDao();
        User user = new User(0, "Ulvi", "Suleymanov", "suleymanov.ulvi@gmail.com", "9945555714");
    }
}