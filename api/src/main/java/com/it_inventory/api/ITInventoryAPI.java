package com.it_inventory.api;

import com.it_inventory.api.Slack.SlackConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(SlackConfig.class)
public class ITInventoryAPI {

	public static void main(String[] args) {
		SpringApplication.run(ITInventoryAPI.class, args);
	}

}
