package com.phoenixnap.oss.sample.client.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.phoenixnap.oss.sample.client.HealthCheckClient;
import com.phoenixnap.oss.sample.client.model.GetHealthCheckResponse;
import com.phoenixnap.oss.sample.main.ClientLauncher;
import com.phoenixnap.oss.sample.server.ServerLauncher;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServerLauncher.class, ClientLauncher.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class TestHealthCheckClient {
    
    @Autowired
    private HealthCheckClient healthCheckClient;
    
    @Test
    public void getHealthCheckIntegrationTest() throws Exception{
        ResponseEntity<GetHealthCheckResponse> healthCheckResponse = healthCheckClient.getHealthCheck();
        
        Assert.assertEquals(HttpStatus.OK, healthCheckResponse.getStatusCode());
        Assert.assertNotNull(healthCheckResponse.getBody());
    }
}
