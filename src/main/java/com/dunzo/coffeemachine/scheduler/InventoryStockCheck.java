package com.dunzo.coffeemachine.scheduler;

import com.dunzo.coffeemachine.material.Composition;
import com.dunzo.coffeemachine.material.Ingredient;
import com.dunzo.coffeemachine.material.Inventory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

public class InventoryStockCheck {

    /**
     * This is a class that checks after 10 sec, if the inventory is running low on some ingredient and also, refills them automactically,
     */

    /**
     * This is the variable to define the threshold at which we consider the inventory to be runnning low of a particular ingredient.
     * TODO: Move the constant value from the code to application.properties
     * OR
     * TODO: Make this value different for each ingredient and get it as a param from user
     */
    private final int LOW_STOCK_CHECK_NUMBER = 100;

    /**
     * This is the variable to define the quantity of refilling of the ingredient.
     * TODO: Move the constant value from the code to application.properties
     * OR
     * TODO: Make this value different for each ingredient and get it as a param from user
     *
     */
    private final int REFILL_STOCK_QUANTITY = 200;

    Inventory inventory = Inventory.getInstance();

    /**
     * TODO: Move the fixed time of inventory check from the code to application.properties
     */
    @Scheduled(fixedRate = 10000)
    public void checkInventoryStock() {
        System.out.println("Inventory Check has started.");
        Map<Ingredient, Composition> inventoryMap =  this.inventory.getInventory();
        if(inventoryMap==null) {
            System.out.println("Inventory Check has finished.");
            return;
        }
        for(Ingredient ingredient: inventoryMap.keySet()) {
            if(inventoryMap.get(ingredient).getQuantity() <= LOW_STOCK_CHECK_NUMBER ) {
                /**
                 * If the value is lower than the threshold limit, we acquire a lock and refill the inventory.
                 */
                synchronized (inventory) {
                    System.out.println(ingredient.getName() + " refilling...");
                    inventory.getInventory().put(ingredient, inventoryMap.get(ingredient).addQuantity(REFILL_STOCK_QUANTITY));
                    System.out.println(ingredient.getName() + " refilled...");
                }

            }
        }
        System.out.println("Inventory Check has finished.");
    }


}
