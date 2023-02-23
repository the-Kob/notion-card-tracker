package dev.kob.notioncardtracker;

import dev.kob.notioncardtracker.notion.config.NotionConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NotionConfigProperties.class)
public class NotionCardTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotionCardTrackerApplication.class, args);
	}

}
