package main;

import dao.inter.EmploymentHistoryDaoInter;


public class Main {
    public static void main(String[] args) {
        EmploymentHistoryDaoInter employmentHistoryDaoInter = Context.instanceEmploymentHistoryDao();
        System.out.println(employmentHistoryDaoInter.getAllEmployementHistoryByUserId(1));
    }
}