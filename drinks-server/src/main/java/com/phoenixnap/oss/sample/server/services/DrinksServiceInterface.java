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

import java.util.List;

import com.phoenixnap.oss.sample.server.exceptions.DrinkNotFoundException;
import com.phoenixnap.oss.sample.server.models.AbstractDrink;

/**
 * 
 * @author kristiang
 *
 */
public interface DrinksServiceInterface {
    
    /**
     * 
     * Adds a drink to the list 
     * 
     * @param drink describes the drink's details 
     * @throws DrinkNotFoundException
     */
    void addDrink(AbstractDrink drink) throws DrinkNotFoundException;
    
    /**
     * 
     * Retrieves the full list of available drinks 
     * 
     * @return arraylist of available drinks 
     * 
     */
    List<AbstractDrink> getDrinks();
    
   
    /**
     * 
     * Retrieves a specific drink 
     * 
     * @param name the name of the drink
     * @return the details about the drink 
     * @throws DrinkNotFoundException
     */
    AbstractDrink getDrink(String name) throws DrinkNotFoundException;
    
    /**
     *
     * Modifies the name of a drink 
     * 
     * @param oldName the old name of the drink 
     * @param newName the new name of the drink 
     * @throws DrinkNotFoundException 
     */
    void modifyDrink(String oldName, String newName) throws DrinkNotFoundException;
    
    /**
     * 
     * @param name
     * @throws DrinkNotFoundException
     */
    void deleteDrink(String name) throws DrinkNotFoundException;
}
