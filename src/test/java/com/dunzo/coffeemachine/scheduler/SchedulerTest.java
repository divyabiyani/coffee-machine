package com.dunzo.coffeemachine.scheduler;

import com.dunzo.coffeemachine.CoffeeMachine;
import com.dunzo.coffeemachine.material.Beverage;
import com.dunzo.coffeemachine.material.Composition;
import com.dunzo.coffeemachine.material.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulerTest {

    /**
     * This is a test class to test the InventoryStockCheck.
     *
     * InventoryStockCheck is used to periodically refill the inventory.
     */

    private CoffeeMachine coffeeMachine;

    /**
     * This block contains a dummy dats to test the changes using the Coffee Machine Simulator.
     * It contains -
     *      1.) int noOfOutlets
     *      2.) A set of Composition
     *      3.) A set of Beverages
     */
    @BeforeEach
    public void instantiateMachine() {
        int noOfOutlets = 3;

        Set<Composition> initialInventory= new HashSet<>();
        initialInventory.add(new Composition(new Ingredient("hot_water"), 500));
        initialInventory.add(new Composition(new Ingredient("hot_milk"), 500));
        initialInventory.add(new Composition(new Ingredient("ginger_syrup"), 100));
        initialInventory.add(new Composition(new Ingredient("sugar_syrup"), 100));
        initialInventory.add(new Composition(new Ingredient("tea_leaves_syrup"), 100));

        Set<Beverage> beverageList = new HashSet<>();

        Set<Composition> hot_tea= new HashSet<>();
        hot_tea.add(new Composition(new Ingredient("hot_water"), 200));
        hot_tea.add(new Composition(new Ingredient("hot_milk"), 100));
        hot_tea.add(new Composition(new Ingredient("ginger_syrup"), 10));
        hot_tea.add(new Composition(new Ingredient("sugar_syrup"), 10));
        hot_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("hot_tea", hot_tea));

        Set<Composition> hot_coffee= new HashSet<>();
        hot_coffee.add(new Composition(new Ingredient("hot_water"), 100));
        hot_coffee.add(new Composition(new Ingredient("hot_milk"), 400));
        hot_coffee.add(new Composition(new Ingredient("ginger_syrup"), 30));
        hot_coffee.add(new Composition(new Ingredient("sugar_syrup"), 50));
        hot_coffee.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("hot_coffee", hot_coffee));

        Set<Composition> black_tea= new HashSet<>();
        black_tea.add(new Composition(new Ingredient("hot_water"), 300));
        black_tea.add(new Composition(new Ingredient("ginger_syrup"), 30));
        black_tea.add(new Composition(new Ingredient("sugar_syrup"), 50));
        black_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("black_tea", black_tea));

        Set<Composition> green_tea= new HashSet<>();
        green_tea.add(new Composition(new Ingredient("hot_water"), 100));
        green_tea.add(new Composition(new Ingredient("ginger_syrup"), 30));
        green_tea.add(new Composition(new Ingredient("sugar_syrup"), 50));
        green_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("green_tea", green_tea));

        Set<Composition> cafe_latte= new HashSet<>();
        cafe_latte.add(new Composition(new Ingredient("hot_water"), 200));
        cafe_latte.add(new Composition(new Ingredient("hot_milk"), 100));
        cafe_latte.add(new Composition(new Ingredient("ginger_syrup"), 10));
        cafe_latte.add(new Composition(new Ingredient("sugar_syrup"), 10));
        cafe_latte.add(new Composition(new Ingredient("cream"), 30));
        beverageList.add(new Beverage("cafe_latte", cafe_latte));

        this.coffeeMachine = new CoffeeMachine(noOfOutlets, initialInventory, beverageList);
    }

    /**
     * This test is checking whether refilling is happening or not.
     * It is placing an order of a hot_tea at every 10 seconds for 3 times.
     * Without the refilling scheduler working, based on our initial inventory it can only be served atmost 2 times.
     */

    @Test
    public void testInventoryStockScheduler() {
        List<Future<String>> responseFutureList = new ArrayList<>();
        Future<String> initialResponseFuture = this.coffeeMachine.placeAndGetOrder("hot_tea");
        responseFutureList.add(initialResponseFuture);
        IntStream.range(1, 2).forEach(i -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            try {
                Future<String> responseFuture = this.coffeeMachine.placeAndGetOrder("hot_tea");
                responseFutureList.add(responseFuture);
            } catch (Exception e) {
            }
        });

        for (Future<String> responseFuture : responseFutureList) {
            try {
                assertEquals("hot_tea is prepared.", responseFuture.get());
            } catch (Exception e) {
            }
        }
    }
}
