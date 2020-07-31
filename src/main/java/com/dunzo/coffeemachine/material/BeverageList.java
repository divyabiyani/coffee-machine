package com.dunzo.coffeemachine.material;

import lombok.Data;

import java.util.Map;

@Data
public class BeverageList {
    private static volatile BeverageList instance;

     /**
      * This is a class containing a map of Beverage name and Beverage.
      *
      * This will be used to store all the beverages in a map for easy access through its name.
    */

    public Map<String, Beverage> beverages;

    /**
     * returns the beverage object based on beverage name
     *
     * @param String beverageName
     * @return Beverage beverage
     */
    public Beverage getBeverage(String beverageName) {
        return this.beverages.get(beverageName);
    }

    /**
     * checks whether the beverage name is present or not
     *
     * @param String beverageName
     * @return Beverage beverage
     */
    public boolean containsKey(String beverageName) {
        return this.beverages.containsKey(beverageName);
    }

    /**
     * creates a singleton instance of the Beverage List using double-checked Locking.
     *
     * @return BeverageList beverageList
     */
    public static BeverageList getInstance() {
        if (instance == null) {
            synchronized (BeverageList.class) {
                if (instance == null) {
                    instance = new BeverageList();
                }
            }
        }
        return instance;
    }
}
