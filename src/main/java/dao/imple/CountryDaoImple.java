package dao.imple;

import dao.inter.AbstractDao;
import dao.inter.CountryDaoInter;
import entity.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImple extends AbstractDao implements CountryDaoInter {
    private Country getCountry(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String nationality = resultSet.getString("nationality");
        Country country = new Country(id, name, nationality);
        return country;

    }

    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList<>();
        try (Connection c = connect()) {
            Statement statement = c.createStatement();
            statement.execute("select * from country");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Country country = getCountry(resultSet);
                result.add(country);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
