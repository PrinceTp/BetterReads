package princetp.betterreads.betterreadsdataloader;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import jakarta.annotation.PostConstruct;
import princetp.betterreads.betterreadsdataloader.author.Author;
import princetp.betterreads.betterreadsdataloader.author.AuthorRepository;
import princetp.betterreads.betterreadsdataloader.connection.DataStaxAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterreadsDataLoaderApplication {

	@Autowired
	AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}

	@PostConstruct
	public void start() {
		Author author = new Author();
		author.setId("id");
		author.setName("Name");
		author.setPersonalName("Personal Name");
		authorRepository.save(author);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessiopBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}
}
