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

package com.phoenixnap.oss.sample.server.models;

import com.phoenixnap.oss.sample.server.models.enums.DrinkTypeEnum;


/**
 * Representation of a drink type
 * 
 * @author kristiang
 *
 */
public class AlcoholicDrink extends AbstractDrink{

    private static final DrinkTypeEnum DRINK_TYPE = DrinkTypeEnum.ALCOHOL;
  
    public AlcoholicDrink(String name) {
        super(name);
    }

  
    @Override
    public DrinkTypeEnum getDrinkTypeEnum() {
       return DRINK_TYPE;
    }
    
}
