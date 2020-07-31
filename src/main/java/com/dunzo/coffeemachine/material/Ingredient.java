package com.dunzo.coffeemachine.material;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ingredient {

    /**
     * This is a class containing object name which requires
     *      1.) Name of the ingredient
     *
     * This will be used to store the name of the ingredient.
     * As of now Ingredient class, it only consists of name,
     * but in future it can consist of state of the ingredient(liquid/solid) and unit supported for the ingredient.
     */

    private String name;
}
