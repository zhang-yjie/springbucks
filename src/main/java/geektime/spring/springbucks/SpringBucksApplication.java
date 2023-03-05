package geektime.spring.springbucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching(proxyTargetClass = true)
@SpringBootApplication
public class SpringBucksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

}

