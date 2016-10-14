package com.phoenixnap.oss.sample.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author kristiang
 * @author Aleksandar Stojsavljevic (aleksandars@ccbill.com)
 */
@SpringBootApplication(scanBasePackages = "com.phoenixnap.oss.sample.server")
public class ServerLauncher {

    public static void main(String args[]){
        SpringApplication.run(ServerLauncher.class, args);        
    }    

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
