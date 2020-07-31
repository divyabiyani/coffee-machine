package com.dunzo.coffeemachine.material;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Composition {
    private Ingredient ingredient;
    private int quantity;

    public Composition consumeQuantity(int consumableQuantity) {
        this.quantity = this.quantity - consumableQuantity;
        return this;
    }

    public Composition addQuantity(int refillQuantity) {
        this.quantity = this.quantity + refillQuantity;
        return this;
    }
}
