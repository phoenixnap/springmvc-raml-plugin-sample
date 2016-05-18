/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.phoenixnap.oss.sample.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.phoenixnap.oss.sample.client.DrinkClient;
import com.phoenixnap.oss.sample.client.DrinkClientImpl;
import com.phoenixnap.oss.sample.client.HealthCheckClient;
import com.phoenixnap.oss.sample.client.HealthCheckClientImpl;
import com.phoenixnap.oss.sample.client.model.CreateDrinkRequest;
import com.phoenixnap.oss.sample.client.model.GetDrinkByIdResponse;
import com.phoenixnap.oss.sample.client.model.GetDrinksResponse;
import com.phoenixnap.oss.sample.client.model.GetHealthCheckResponse;
import com.phoenixnap.oss.sample.client.model.UpdateDrinkByIdRequest;

/**
 *
 * Simple launcher to run through the CRUD methods offered by the server 
 * using the generated client
 * 
 * @author krisga
 *
 */
public class ClientLauncher {

    private static final Logger LOG = LoggerFactory.getLogger(ClientLauncher.class);
    

    public static void main (String args[]){
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("classpath:beans.client.launcher.xml");
        
        //checks health of endpoint
        HealthCheckClient hcClient = ctx.getBean(HealthCheckClientImpl.class);
        GetHealthCheckResponse healthCheck = hcClient.getHealthCheck().getBody();
        assert(healthCheck.getStatus().equals(HttpStatus.OK)) : "Health check failed! Server is not healthy"; 
        LOG.info("Health Check status : {}" , healthCheck.getStatus());
        
        //get list of drinks (GET list)
        DrinkClient drinkClient  =  ctx.getBean(DrinkClientImpl.class);
        ResponseEntity<List<GetDrinksResponse>> getDrinksResponse = drinkClient.getDrinks(); 
        assert getDrinksResponse.getStatusCode().equals(HttpStatus.OK) : "Failed to perform GET request.";
        getDrinksResponse.getBody().forEach(d -> LOG.info(d.getName() + ","));        
        
        //get specific drink (GET)
        ResponseEntity<GetDrinkByIdResponse> drink = drinkClient.getDrinkById("Martini");
        assert drink.getStatusCode().equals(HttpStatus.OK) : "Failed to perform GET request.";
        
        //output all details returned
        LOG.info("Drink name: [{}]", drink.getBody().getName());
        LOG.info("Drink type: [{}]", drink.getBody().getType());
        
        //create drink (POST)
        CreateDrinkRequest createDrinkRequest = new CreateDrinkRequest();
        createDrinkRequest.setName("Wine");
        createDrinkRequest.setType("ALCOHOL");
        ResponseEntity createDrinkResponse = drinkClient.createDrink(createDrinkRequest);
        assert createDrinkResponse.getStatusCode().equals(HttpStatus.ACCEPTED) : "Failed to perform POST request.";
        LOG.info("Successfully created drink with name [{}]", createDrinkRequest.getName());
        
        //update drink (PUT)
        UpdateDrinkByIdRequest updateDrinkByIdRequest = new UpdateDrinkByIdRequest();
        updateDrinkByIdRequest.setName("Beer");
        ResponseEntity updateResponse = drinkClient.updateDrinkById("wine", updateDrinkByIdRequest);
        assert updateResponse.getStatusCode().equals(HttpStatus.OK) :"Failed to perform PUT request.";
        LOG.info("Successfully updated drink. Verifying ...");
        
        //Verify update
        ResponseEntity<GetDrinkByIdResponse> updatedDrink = drinkClient.getDrinkById(updateDrinkByIdRequest.getName());
        assert updatedDrink.getStatusCode().equals(HttpStatus.OK) && updatedDrink.getBody()!=null : "Failed to verify PUT request. Failed to update drink";        
        LOG.info("Successfully verified drink update!");
        
        //delete drink (DELETE)
        ResponseEntity deleteResponse = drinkClient.deleteDrinkById(updateDrinkByIdRequest.getName());
        assert deleteResponse.getStatusCode().equals(HttpStatus.ACCEPTED) : "Failed perform DELETE request.";
        LOG.info("Successfully deleted drink with name: [{}]", updateDrinkByIdRequest.getName());
    }
     
    
}
