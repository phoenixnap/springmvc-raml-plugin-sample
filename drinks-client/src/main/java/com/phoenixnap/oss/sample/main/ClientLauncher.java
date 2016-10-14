package com.phoenixnap.oss.sample.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * @author kristiang
 * @author Aleksandar Stojsavljevic (aleksandars@ccbill.com)
 */
@EnableFeignClients(basePackages = "com.phoenixnap.oss.sample.client")
@PropertySource("classpath:client.properties")
@SpringBootApplication(scanBasePackages = "com.phoenixnap.oss.sample")
public class ClientLauncher {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(ClientLauncher.class).web(false).run(args).close();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
