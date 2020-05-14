package cx.catapult.animals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.Cat;

@Repository

public class CatsServiceDao implements Service<Cat> {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cat> all() {
        return jdbcTemplate.query(
                "SELECT * FROM cat",
                (rs, rowNum) ->
                        new Cat(
                                rs.getString("ID"),
                                rs.getString("catName"),
                                rs.getString("catDesc")
                        )
        );
    }

    @Override
    public Cat create(Cat cat) {
	    jdbcTemplate.update(
                "INSERT INTO cats (ID, catName, catDesc) values(?,?,?)",
                cat.getId(), cat.getName(), cat.getDescription());
        return cat;
    }

    @Override
    public Cat get(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM cats where ID = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new Cat(
                                rs.getString("ID"),
                                rs.getString("catName"),
                                rs.getString("catDesc")
                        )
        );

    }

    @Override
    public Cat update(Cat cat) {
		jdbcTemplate.update(
            "UPDATE cats SET catName = ?, catDesc = ? WHERE id = ?",
            cat.getName(), cat.getDescription(), cat.getId());
            return cat;


    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update(
            "DELETE cats WHERE id = ?",
            id);
 }
}
