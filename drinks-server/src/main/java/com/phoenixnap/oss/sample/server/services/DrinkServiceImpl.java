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

package com.phoenixnap.oss.sample.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phoenixnap.oss.sample.server.exceptions.DrinkNotFoundException;
import com.phoenixnap.oss.sample.server.models.AbstractDrink;
import com.phoenixnap.oss.sample.server.models.AlcoholicDrink;
import com.phoenixnap.oss.sample.server.models.SoftDrink;

/**
 * Simple service layer performing CRUD tasks on an in-memory map of POJOs. 
 * 
 * @author kristiang
 *
 */
@Component
public class DrinkServiceImpl implements DrinksServiceInterface{

    private Map<String,AbstractDrink> drinks;
    
    private final static Logger LOG = LoggerFactory.getLogger(DrinkServiceImpl.class);
    
    public DrinkServiceImpl(){
        LOG.debug("Initializing drinks dispenser... ");
        
        //initialize drinks array 
        drinks = new HashMap<String,AbstractDrink>();
        
        drinks.put("fanta",new SoftDrink("Fanta"));
        drinks.put("cocacola",new SoftDrink("CocaCola"));
        drinks.put("martini", new AlcoholicDrink("Martini"));
        drinks.put("vodka", new AlcoholicDrink("Vodka"));
        
        LOG.debug("Initialized drinks dispenser with {} items", drinks.size());
    }
    
    @Override
    public void modifyDrink(String oldName, String newName) throws DrinkNotFoundException{
        LOG.info("Modifying drink with name [{}] to [{}] ...", oldName, newName);
        if (drinks.containsKey(oldName.toLowerCase())){
            LOG.debug("Successfully found drink with name [{}]. Changing drink name to [{}]..." , oldName, newName);
            AbstractDrink drink = drinks.get(oldName.toLowerCase());
            drink.setName(newName);
            drinks.put(newName.toLowerCase(), drink);
            drinks.remove(oldName.toLowerCase());
            LOG.debug("Successfully completed name change for drink with name [{}]!", newName);
        }else{
            LOG.error("Failed to find drink with name: [{}]");
            throw new DrinkNotFoundException("Could not find drink with name " + oldName);
        }
    }
    
    @Override
    public void addDrink(AbstractDrink drink) throws DrinkNotFoundException{
        LOG.info("Adding drink with name: [{}] and type: [{}]", drink.getName(), drink.getDrinkTypeEnum().name());
        if (!drinks.containsKey(drink.getName().toLowerCase())){            
            drinks.put(drink.getName().toLowerCase(), drink);
            LOG.debug("Drink [{}] has been successfuly created!", drink.getName());
        }else{
            LOG.warn("Drink name already exists!");
            throw new DrinkNotFoundException("Drink already exists");
        }
        
    }
    
    @Override
    public void deleteDrink(String name) throws DrinkNotFoundException{
        LOG.info("Deleting drink with name: [{}]", name);
        if (drinks.containsKey(name.toLowerCase())){            
            drinks.remove(name.toLowerCase());
            LOG.debug("Successfully deleted [{}]",name);
        }else{
            LOG.error("[{}] could not be found!", name);
            throw new DrinkNotFoundException("Drink not found!");
        }
    }
    
    @Override
    public List<AbstractDrink> getDrinks() {
        LOG.info("Retrieving list of available drinks ... ");
        return new ArrayList<AbstractDrink>(this.drinks.values());
    }

    @Override
    public AbstractDrink getDrink(String name) throws DrinkNotFoundException {
        LOG.info("Retrieving details for [{}] ...", name);
        if (drinks.containsKey(name.toLowerCase())){
            LOG.debug("Found drink with name: [{}]. Returning details ... ", name);
            return this.drinks.get(name.toLowerCase());
        }else{
            LOG.error("Could not find drink with name: [{}]",name);
            throw new DrinkNotFoundException("Failed to find drink with name : " + name);
        }        
    }
    
}
