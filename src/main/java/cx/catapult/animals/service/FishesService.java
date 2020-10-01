package cx.catapult.animals.service;

import cx.catapult.animals.domain.Fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class FishesService extends BaseService<Fish> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Fish> query(String sql) {
        String type = "fish";
        Object[] params = new Object[]{type};
        return jdbcTemplate.query(sql, params, new RowMapper<Fish>() {
            @Override
            public Fish mapRow(ResultSet rs, int rowNum) throws SQLException {
                Fish fish = new Fish();
                fish.setId(rs.getString("id"));
                fish.setName(rs.getString("name"));
                fish.setDescription(rs.getString("description"));
                return fish;
            }
        });
    }

    @PostConstruct
    public void initialize() {
        this.create(new Fish("Nemo", "Friend of Dory"));
        this.create(new Fish("Dory", "I dont remember"));
        this.create(new Fish("Marlin", "Father of Nemo"));
        this.create(new Fish("Gill", "A nice fish afterall"));
        this.create(new Fish("Bloat", "Puff fish"));
        this.create(new Fish("Crush", "His a turtle"));
        this.create(new Fish("Bruce", "Evil shark"));
    }

}
