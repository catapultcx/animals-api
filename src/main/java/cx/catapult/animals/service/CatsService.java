package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class CatsService extends BaseService<Cat> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Cat> query(String sql) {
        String type = "cat";
        Object[] params = new Object[]{type};
        return jdbcTemplate.query(sql, params, new RowMapper<Cat>() {
            @Override
            public Cat mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cat cat = new Cat();
                cat.setId(rs.getString("id"));
                cat.setName(rs.getString("name"));
                cat.setDescription(rs.getString("description"));
                return cat;
            }
        });
    }

    @PostConstruct
    public void initialize() {
        this.create(new Cat("Tom", "Friend of Jerry"));
        this.create(new Cat("Jerry", "Not really a cat"));
        this.create(new Cat("Bili", "Furry cat"));
        this.create(new Cat("Smelly", "Cat with friends"));
        this.create(new Cat("Tiger", "Large cat"));
        this.create(new Cat("Tigger", "Not a scary cat"));
        this.create(new Cat("Garfield", "Lazy cat"));
    }

}
