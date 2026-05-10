package in.aj.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventServiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventServiveApplication.class, args);
	}

}
