package main;

import dao.inter.SkillDaoInter;


public class Main {
    public static void main(String[] args) {
        SkillDaoInter skillDao = Context.instanceSkillDao();
        System.out.println(skillDao.getAll());

    }
}