package flashcards;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@SpringBootApplication
public class Application implements CommandLineRunner{
	//implements CommandLineRunner

	private final DataSource dataSource;

	public Application(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ResourceDatabasePopulator populatorSchema = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
		ResourceDatabasePopulator populatorData = new ResourceDatabasePopulator(new ClassPathResource("data.sql"));
		populatorSchema.execute(dataSource);
		populatorData.execute(dataSource);
	}

}

