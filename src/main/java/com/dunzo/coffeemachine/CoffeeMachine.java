package com.dunzo.coffeemachine;

import com.dunzo.coffeemachine.material.*;
import com.dunzo.coffeemachine.process.Outlet;
import lombok.Data;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public final class CoffeeMachine {

    /**
     * This is a class that depicts the simulation of a coffee machine.
     * This will store the no of outlets in the coffee machine, will initialize the inventory as well as prepare the beverage list.
     */

    private final int noOfOutlets;

    private Inventory inventory = Inventory.getInstance();

    private BeverageList beverageList = BeverageList.getInstance();

    /**
     * OutletPools will make sure at a time, maximum N(i.e. noOfOutlets) beverages be served.
     */
    ExecutorService outletPools;

    public CoffeeMachine(int noOfOutlets, Set<Composition> initialInventory, Set<Beverage> beverageList) {
        this.noOfOutlets = noOfOutlets;
        this.inventory.setInventory(initialInventory.stream().collect(Collectors.toMap(Composition::getIngredient, Function.identity())));
        this.beverageList.setBeverages(beverageList.stream().collect(Collectors.toMap(Beverage::getName, Function.identity())));
        this.outletPools = Executors.newFixedThreadPool(this.noOfOutlets);
    }

    /**
     * returns a list of Future containing the result of the processing of the order containing the list of beverage names.
     *
     * @param List<Future<String>> beverages
     * @return List<Future> responseFuture
     */
    public List<Future<String>> placeAndGetOrder(List<String> beverages) {
        List<Future<String>> beveragesProcessResponse= new ArrayList<>();

        for(String beverage: beverages) {
            Future<String> beverageProcessResponse = placeAndGetOrder(beverage);
            beveragesProcessResponse.add(beverageProcessResponse);
        }

        return beveragesProcessResponse;
    }

    /**
     * returns the Future containing the result of the processing of the order conataining a beverage name.
     *
     * @param Future<String> beverages
     * @return Future responseFuture
     */

    public Future<String> placeAndGetOrder(String beverage) {
        Callable callable = new Outlet(beverage);
        Future<String>  successfulBeverageFuture = outletPools.submit(callable);

        return successfulBeverageFuture;

    }
}

