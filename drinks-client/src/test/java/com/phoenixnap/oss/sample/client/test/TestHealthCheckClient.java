package com.phoenixnap.oss.sample.client.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.phoenixnap.oss.sample.client.HealthCheckClient;
import com.phoenixnap.oss.sample.server.ServerLauncher;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerLauncher.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class TestHealthCheckClient {
    
    @Autowired
    private HealthCheckClient healthCheckClient;
    
    private MockMvc mockMvc;
   
    @Test
    public void getHealthCheckIntegrationTest() throws Exception{
        ResponseEntity healthCheckResponse = healthCheckClient.getHealthCheck();
        
        Assert.assertTrue(healthCheckClient.getHealthCheck().getStatusCode().equals(HttpStatus.OK));
        Assert.assertTrue(healthCheckClient.getHealthCheck().getBody()!=null);
    }
    
    

}
