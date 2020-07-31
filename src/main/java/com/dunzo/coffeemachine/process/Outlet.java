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

    private void checkAllIngredientsAvailable(Beverage beverage) {
        for(Composition composition: beverage.getCompositionList()) {
            if(!this.inventory.containsKey(composition.getIngredient())) {
                //throw exception
            }

            if(this.inventory.getComposition(composition.getIngredient()).getQuantity() < composition.getQuantity()) {
                //throw exceptiom
            }
        }

        return;
    }

    public void processOrder(Beverage beverage) {
        synchronized (this.inventory.getInventory()) {
            for(Composition composition: beverage.getCompositionList()) {
                Ingredient ingredient = composition.getIngredient();
                if(!this.inventory.containsKey(ingredient)) {
                    //throw exception
                }

                if(this.inventory.getComposition(ingredient).getQuantity() < composition.getQuantity()) {
                    //throw exceptiom
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
            //throw exception
        }
        Beverage beverage = this.beverageList.getBeverage(beverageName);

        try {
            checkAllIngredientsAvailable(beverage);
        } catch (Exception e) {
            //throw exception
        }

        try {
            processOrder(beverage);
        } catch (Exception e) {
            //throw exception
        }

        return beverage;
    }
}
