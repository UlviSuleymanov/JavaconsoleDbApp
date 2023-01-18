package dao.inter;

import entity.EmployementHistory;

import java.util.List;

public interface EmploymentHistoryDaoInter {
    public List<EmployementHistory> getAllEmployementHistoryByUserId(int userId);
}
