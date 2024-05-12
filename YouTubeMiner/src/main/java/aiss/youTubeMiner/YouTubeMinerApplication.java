package aiss.youTubeMiner;

import aiss.youTubeMiner.oauth2.Authenticator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YouTubeMinerApplication {
	public static void main(String[] args) {
		SpringApplication.run(YouTubeMinerApplication.class, args);
	}

	@Bean("restTemplate")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean("authenticator")
	@DependsOn("restTemplate")
	public Authenticator authenticator() {
		return new Authenticator();
	}
}
