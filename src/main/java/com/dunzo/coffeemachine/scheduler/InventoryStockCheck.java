package com.dunzo.coffeemachine.scheduler;

import com.dunzo.coffeemachine.material.Composition;
import com.dunzo.coffeemachine.material.Ingredient;
import com.dunzo.coffeemachine.material.Inventory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class InventoryStockCheck {

    private final int LOW_STOCK_CHECK_NUMBER = 100;

    private final int REFILL_STOCK_QUANTITY = 100;

    Inventory inventory = Inventory.getInstance();

    @Scheduled(fixedRate = 10000)
    public void checkInventoryStock() {
        System.out.println("Inventory Check has started.");
        Map<Ingredient, Composition> inventoryMap =  this.inventory.getInventory();
        if(inventoryMap==null) {
            System.out.println("Inventory Check has finished.");
            return;
        }
        for(Ingredient ingredient: inventoryMap.keySet()) {
            if(inventoryMap.get(ingredient).getQuantity() < LOW_STOCK_CHECK_NUMBER ) {
                System.out.println(ingredient.getName() + " stock is running low.");
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
