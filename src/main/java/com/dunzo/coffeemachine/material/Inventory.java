package com.dunzo.coffeemachine.material;

import lombok.Data;

import java.util.Map;

@Data
public class Inventory {
    private static volatile Inventory instance;

     /**
     * This is a class containing a map of Ingredient and Composition.
     *
     * This will be used to store all the composition in a map available for easy access through the ingredient object.
     */

    public Map<Ingredient, Composition> inventory;

    /**
     * returns the composition object based on ingredient object
     *
     * @param Ingredient ingredient
     * @return Composition composition
     */
    public Composition getComposition(Ingredient ingredient) {
        return this.inventory.get(ingredient);
    }


    /**
     * checks whether the Ingredient is present or not
     *
     * @param Ingredient ingredient
     * @return boolean
     */
    public boolean containsKey(Ingredient ingredient) {
        return this.inventory.containsKey(ingredient);
    }


    /**
     * creates a singleton instance of the Inventory using double-checked Locking.
     *
     * @return Inventory inventory
     */
    public static Inventory getInstance() {
        if (instance == null) {
            synchronized (Inventory .class) {
                if (instance == null) {
                    instance = new Inventory();
                }
            }
        }
        return instance;
    }
}
