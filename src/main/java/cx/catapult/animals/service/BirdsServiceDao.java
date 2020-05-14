package cx.catapult.animals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.Bird;

@Repository
public class BirdsServiceDao implements Service<Bird> {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Bird> all() {
        return jdbcTemplate.query(
                "SELECT * FROM bird",
                (rs, rowNum) ->
                        new Bird(
                                rs.getString("ID"),
                                rs.getString("birdName"),
                                rs.getString("birdDesc")
                        )
        );
    }

    @Override
    public Bird create(Bird bird) {
	    jdbcTemplate.update(
                "INSERT INTO birds (ID, birdName, birdDesc) values(?,?,?)",
                bird.getId(), bird.getName(), bird.getDescription());
        return bird;
    }

    @Override
    public Bird get(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM birds where ID = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new Bird(
                                rs.getString("ID"),
                                rs.getString("birdName"),
                                rs.getString("birdDesc")
                        )
        );

    }

    @Override
    public Bird update(Bird bird) {
		jdbcTemplate.update(
            "UPDATE birds SET birdName = ?, birdDesc = ? WHERE id = ?",
            bird.getName(), bird.getDescription(), bird.getId());
            return bird;


    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update(
            "DELETE birds WHERE id = ?",
            id);
 }
}
