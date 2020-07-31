package com.dunzo.coffeemachine.material;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Beverage {

    /*
        This is a class containing object Beverage which requires
            1.) Name of the beverage
            2.) Ingredient-Quantity combination(Composition List)

        This will be used to store the static information about the beverage recipe

    */

    private String name;
    private Set<Composition> compositionList;
}
