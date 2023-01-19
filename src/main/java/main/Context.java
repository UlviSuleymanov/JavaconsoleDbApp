package main;

import dao.imple.*;
import dao.inter.*;

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

    public static CountryDaoInter instanceCountryDao() {
        return new CountryDaoImple();
    }

    public static SkillDaoInter instanceSkillDao() {
        return new SkillDaoImple();
    }

}
