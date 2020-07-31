package com.dunzo.coffeemachine.material;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class BeverageList {
    private static volatile BeverageList instance;

    public Map<String, Beverage> beverages;

    public Beverage getBeverage(String beverageName) {
        return this.beverages.get(beverageName);
    }

    public boolean containsKey(String beverageName) {
        return this.beverages.containsKey(beverageName);
    }

    public static BeverageList getInstance() {
        if (instance == null) {
            synchronized (BeverageList .class) {
                if (instance == null) {
                    instance = new BeverageList();
                }
            }
        }
        return instance;
    }
}
