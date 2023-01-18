package main;

import dao.imple.EmployementHistoryImple;
import dao.imple.UserDaoImple;
import dao.imple.UserSkillDaoImple;
import dao.inter.EmploymentHistoryDaoInter;
import dao.inter.UserDaoInter;
import dao.inter.UserSkillDaoInter;

public class Context {
    public static UserDaoInter instanceUserDao() {
        return new UserDaoImple();
    }

    public static UserSkillDaoInter instanceUserSkillDao() {
        return new UserSkillDaoImple();
    }

    public static EmploymentHistoryDaoInter instanceEmploymentHistoryDao() {
        return new EmployementHistoryImple();
    }

}
