package com.phoenixnap.oss.sample.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * @author kristiang
 *
 */
@SpringBootApplication
@ImportResource("classpath:beans.server.launcher.xml")
public class ServerLauncher {

    public static void main(String args[]){
        SpringApplication.run(ServerLauncher.class, args);        
    }    
}
