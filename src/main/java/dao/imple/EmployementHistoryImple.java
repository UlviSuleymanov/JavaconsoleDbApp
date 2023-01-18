package dao.imple;

import dao.inter.AbstractDao;
import dao.inter.EmploymentHistoryDaoInter;
import entity.EmployementHistory;
import entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployementHistoryImple extends AbstractDao implements EmploymentHistoryDaoInter {
    private EmployementHistory getEmploymentHistory(ResultSet rs) throws Exception {
        String header = rs.getString("header");
        String jobDescription = rs.getString("job_description");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        int userId = rs.getInt("user_id");
        EmployementHistory emp = new EmployementHistory(null, header, beginDate, endDate, jobDescription, new User(userId));
        return emp;
    }

    @Override
    public List<EmployementHistory> getAllEmployementHistoryByUserId(int userId) {
        List<EmployementHistory> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement statement = c.prepareStatement("select * from employment_history where user_id=?");
            statement.setInt(1, userId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                EmployementHistory employementHistory = getEmploymentHistory(rs);
                result.add(employementHistory);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
