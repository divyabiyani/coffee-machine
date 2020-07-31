package com.dunzo.coffeemachine;

import com.dunzo.coffeemachine.material.*;
import com.dunzo.coffeemachine.process.Outlet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@ComponentScan
public final class CoffeeMachine {

    private final int noOfOutlets;

    //private Inventory inventory;

    //private BeverageList beverageList;

    ExecutorService outletPools;

    private final String SUCCESSFUL_BEVERAGE_MESSAGE = " is prepared";


    public CoffeeMachine(int noOfOutlets, Set<Composition> initialInventory, Set<Beverage> beverageList) {
        this.noOfOutlets = noOfOutlets;
        Inventory.getInstance().setInventory(initialInventory.stream().collect(Collectors.toMap(Composition::getIngredient, Function.identity())));
        BeverageList.getInstance().setBeverages(beverageList.stream().collect(Collectors.toMap(Beverage::getName, Function.identity())));
        this.outletPools = Executors.newFixedThreadPool(this.noOfOutlets);
    }

    public List<String> placeAndGetOrder(List<String> beverages) {
        List<String> beveragesProcessResponse= new ArrayList<>();

        for(String beverage: beverages) {
            String beverageProcessResponse = placeAndGetOrder(beverage);
            beveragesProcessResponse.add(beverageProcessResponse);
        }

        return beveragesProcessResponse;
    }

    public String placeAndGetOrder(String beverage) {
        Beverage successfulBeverage = null;
        try {
            Callable callable = new Outlet(beverage);
            Future<Beverage>  successfulBeverageFuture = outletPools.submit(callable);
            successfulBeverage = successfulBeverageFuture.get();
        } catch(Exception e) {
            //return error statement
        }

        return successfulBeverage.getName() + SUCCESSFUL_BEVERAGE_MESSAGE;
    }
}

