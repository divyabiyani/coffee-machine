package com.dunzo.coffeemachine.process;

import com.dunzo.coffeemachine.material.*;
import lombok.Data;

import java.util.concurrent.Callable;

@Data
public class Outlet implements Callable<Beverage> {

    private String beverageName;

    private Inventory inventory;

    private BeverageList beverageList;

    public Outlet(String beverageName) {
        this.beverageName = beverageName;
        this.inventory = Inventory.getInstance();
        this.beverageList = BeverageList.getInstance();
    }

    private void checkAllIngredientsAvailable(Beverage beverage) throws Exception {
        for(Composition composition: beverage.getCompositionList()) {
            if(!this.inventory.containsKey(composition.getIngredient())) {
                throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                        " is not available.");
            }

            if(this.inventory.getComposition(composition.getIngredient()).getQuantity() < composition.getQuantity()) {
                throw new Exception(beverage.getName() + " cannot be prepared because " + composition.getIngredient().getName() +
                        " is not sufficient.");
            }
        }

        return;
    }

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

    @Override
    public Beverage call() throws Exception {
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

        return beverage;
    }
}
