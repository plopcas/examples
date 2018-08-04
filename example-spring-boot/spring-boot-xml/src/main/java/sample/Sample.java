package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:beans.xml")
public class Sample implements CommandLineRunner {

	@Value("${sample}")
	private String sample;

	@Autowired
	SampleService service;

	public static void main(String[] args) {
		SpringApplication.run(Sample.class, args);
	}

	public void run(String... args) {
		System.out.println(sample);
		System.out.println(service.greetings());
	}
}
