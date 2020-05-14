package cx.catapult.animals;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.BirdsServiceDao;
import cx.catapult.animals.service.CatsServiceDao;

@SpringBootApplication
public class AnimalsApiApplication implements CommandLineRunner {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("birdsServiceDao")
	private BirdsServiceDao birdsServiceDao;

	@Autowired
	@Qualifier("catsServiceDao")
	private CatsServiceDao catsServiceDao;

	public static void main(String[] args) {
		SpringApplication.run(AnimalsApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
		};
	}

	@Override
    public void run(String... args) {
        System.out.println("StartApplication...");
        runJDBC();
    }

	void runJDBC() {

        System.out.println("Creating tables for testing...");

        jdbcTemplate.execute("DROP TABLE IF EXISTS birds;");
        jdbcTemplate.execute("CREATE TABLE birds (" +
			"ID VARCHAR(36) NOT NULL PRIMARY KEY," +
			"birdName VARCHAR(40) NOT NULL," +
			"birdDesc VARCHAR(250) NOT NULL" +
			");");

		  jdbcTemplate.execute("DROP TABLE IF EXISTS cats;");
		  jdbcTemplate.execute("CREATE TABLE cats (" +
			"ID VARCHAR(36) NOT NULL PRIMARY KEY," +
			"catName VARCHAR(40) NOT NULL," +
			"catDesc VARCHAR(250) NOT NULL" +
		  	");");

			  
        List<Cat> cats = Arrays.asList(
			new Cat(UUID.randomUUID().toString(), "Tigger", "not a scary cat"),
			new Cat(UUID.randomUUID().toString(), "Garfield", "Lazy cat")
			);
		
		List<Bird> birds = Arrays.asList(
			new Bird(UUID.randomUUID().toString(), "Tweety", "Friend of Donald"),
			new Bird(UUID.randomUUID().toString(), "Donald", "Not really a bird")
			);

    	System.out.println("[SAVE]");
        cats.forEach(cat -> {
            System.out.println("Saving..." +cat.getName());
            catsServiceDao.create(cat);
		});

		System.out.println("[SAVE]");
        birds.forEach(bird -> {
            System.out.println("Saving..." +bird.getName());
            birdsServiceDao.create(bird);
		});

    }
}
