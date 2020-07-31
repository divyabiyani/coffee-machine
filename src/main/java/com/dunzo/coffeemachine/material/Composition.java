package com.dunzo.coffeemachine.material;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Composition {

    /**
     * This is a class containing object Composition which requires
     *      1.) Name of the ingredient
     *      2.) Quantity of the ingredient
     *
     * This will be used to store the available inventory items as well as beverage's required item.
     */

    private Ingredient ingredient;
    private int quantity;


    /**
     * returns the composition object after reducing the consumed quantity.
     *
     * @param int consumableQuantity
     * @return Composition composition
     */
    public Composition consumeQuantity(int consumableQuantity) {
        this.quantity = this.quantity - consumableQuantity;
        return this;
    }

    /**
     * returns the composition object after adding the refill quantity
     *
     * @param int refillQuantity
     * @return Composition composition
     */
    public Composition addQuantity(int refillQuantity) {
        this.quantity = this.quantity + refillQuantity;
        return this;
    }
}
