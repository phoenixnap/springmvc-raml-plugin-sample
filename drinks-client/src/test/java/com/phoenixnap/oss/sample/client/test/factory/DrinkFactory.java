package com.phoenixnap.oss.sample.client.test.factory;

import java.util.UUID;

import com.phoenixnap.oss.sample.client.model.CreateDrinkRequest;

/**
 * Simple factory class to generate CreateDrinkRequests on the fly 
 * @author kristiang
 *
 */
public class DrinkFactory {

    public static CreateDrinkRequest getDrink(){
        CreateDrinkRequest createDrinkRequest = new CreateDrinkRequest();
        
        createDrinkRequest.setName(UUID.randomUUID().toString());
        createDrinkRequest.setType("SOFT_DRINK");
        
        return createDrinkRequest;
    }
    
}
