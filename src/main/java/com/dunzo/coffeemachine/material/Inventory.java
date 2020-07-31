package com.dunzo.coffeemachine.material;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class Inventory {
    private static volatile Inventory instance;

    public Map<Ingredient, Composition> inventory;

    public Composition getComposition(Ingredient ingredient) {
        return this.inventory.get(ingredient);
    }

    public boolean containsKey(Ingredient ingredient) {
        return this.inventory.containsKey(ingredient);
    }

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
