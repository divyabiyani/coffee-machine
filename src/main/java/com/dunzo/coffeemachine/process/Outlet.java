package com.dunzo.coffeemachine.process;

import com.dunzo.coffeemachine.material.*;
import lombok.Data;

import java.util.concurrent.Callable;

@Data
public class Outlet implements Callable<String> {

    /**
     * This is a class handling all the function of a single outlet in a coffee machine.
     *
     * This class checks the inventory, pick items from inventory while maintaining consistency and also prepares the beverage.
     */

    private String beverageName;

    private Inventory inventory;

    private BeverageList beverageList;

    private final String SUCCESSFUL_BEVERAGE_MESSAGE = " is prepared.";

    public Outlet(String beverageName) {
        this.beverageName = beverageName;
        this.inventory = Inventory.getInstance();
        this.beverageList = BeverageList.getInstance();
    }

    /**
     * checks if all the ingredients necessary for the request beverage is available or not.
     * If the ingredient is not available or the required quantity of the ingredient is not available, it will throw an error.
     *
     * @param Beverage beverage
     */

    private void checkAllIngredientsAvailable(Beverage beverage) throws Exception {
        for(Composition composition: beverage.getCompositionList()) {
            /**
             * checks if the ingredient is present in the inventory or not.
             */
            if(!this.inventory.containsKey(composition.getIngredient())) {
                throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                        " is not available.");
            }

            /**
             * checks if the required quantity necessary to make the beverage is present in the inventory or not.
             */

            if(this.inventory.getComposition(composition.getIngredient()).getQuantity() < composition.getQuantity()) {
                throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                        " is not sufficient.");
            }
        }

        return;
    }

    /**
     * processes the order of the beverage.
     * It acquires a lock on the hashmap to check if the ingredient is available
     * and after the check, it deducts the quantity from the inventory to make the beverage.
     *
     * If the ingredient is not available or the required quantity of the ingredient is not available, it will throw an error.
     *
     * @param Beverage beverage
     */

    public void processOrder(Beverage beverage) throws Exception {
        synchronized (this.inventory.getInventory()) {
            for(Composition composition: beverage.getCompositionList()) {
                Ingredient ingredient = composition.getIngredient();
                if(!this.inventory.containsKey(composition.getIngredient())) {
                    throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                            " is not available.");
                }

                if(this.inventory.getComposition(composition.getIngredient()).getQuantity() < composition.getQuantity()) {
                    throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                            " is not sufficient.");
                }

            }
            for(Composition composition: beverage.getCompositionList()) {
                Ingredient ingredient = composition.getIngredient();
                this.inventory.getInventory().put(ingredient, this.inventory.getComposition(ingredient).consumeQuantity(composition.getQuantity()));
            }
        }
    }

    /**
     * the main function to start the order for the outlet. This function oes multiple things.
     *
     *      1.) It checks if the beverage name given by customer is present in the beverage list or not.
     *          If not, it will throw an error.
     *
     *      2.) It calls the checkAllIngredientsAvailable function.
     *
     *      3.) It calls the processOrder function
     *
     * If the processing is done successfully, it returns a string.
     */

    @Override
    public String call() throws Exception {
        if(!this.beverageList.containsKey(beverageName)) {
            throw new Exception("We do not provide the following " + beverageName);
        }
        Beverage beverage = this.beverageList.getBeverage(beverageName);

        try {
            checkAllIngredientsAvailable(beverage);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            processOrder(beverage);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return beverage.getName() +  SUCCESSFUL_BEVERAGE_MESSAGE;
    }
}
