package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Collection<BaseAnimal> search(String query) {
        String sql = "select distinct a.id, a.name, a.description, t.name as type"
                + " from animal a left join animaltype t on a.type = t.id where a.name like '%" + query
                + "%' or a.id like '%" + query + "%'";

        return jdbcTemplate.query(sql, new RowMapper<BaseAnimal>() {
            @Override
            public BaseAnimal mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new BaseAnimal(rs.getString("id"), rs.getString("name"), rs.getString("description"), null);
            }
        });
    }
}
