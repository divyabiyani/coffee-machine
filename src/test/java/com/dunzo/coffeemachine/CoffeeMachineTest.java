package com.dunzo.coffeemachine;

import com.dunzo.coffeemachine.material.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoffeeMachineTest {
    /**
     * This is a test class to test the Coffee Machine Simulation.
     *
     * CoffeeMachine is used to initialize the inventories, beverageList and also, to prepare and process order.
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
     * This test is checking whether a single beverage which should be prepared
     * based on the initialized inventory and beverage list we have given is giving back the beverage of not.
     */

    @Test
    public void placeAndGetOrderTestSuccessful() {
        Future<String> responseFuture = this.coffeeMachine.placeAndGetOrder("hot_tea");
        try {
            assertEquals("hot_tea is prepared.", responseFuture.get());
        } catch (Exception e) {
        }
    }

    /**
     * This test is checking whether a single beverage which does not appear in our beverage list is throwing an error or not
     */

    @Test
    void contextLoads() {
    }

    @Test
    public void placeAndGetOrderIncorrectBeverageName() {
        Future<String> response = this.coffeeMachine.placeAndGetOrder("hot_teaj");
        try {
            response.get();
        } catch (Exception e) {
            assertEquals("We do not provide the following hot_teaj", e.getMessage().split(": ")[1]);
        }
    }

    /**
     * This test is checking whether a single beverage
     * which contains an ingredient that is not present in the inventory is throwing an error ot not
     */

    @Test
    public void placeAndGetOrderMissingIngredient() {
        Future<String> response = this.coffeeMachine.placeAndGetOrder("cafe_latte");
        try {
            response.get();
        } catch (Exception e) {
            assertEquals("cafe_latte cannot be prepared because cream is not available.", e.getMessage().split(": ")[1]);
        }
    }


    /**
     * This test is checking the response of a list of beverages.
     * Based on the input provided it should give us some successful responses, and some errors.
     */

    @Test
    public void placeAndGetOrderListTest() {
        List<String> beverageList = new ArrayList<>();
        beverageList.add("hot_tea");
        beverageList.add("hot_coffee");
        beverageList.add("black_tea");
        beverageList.add("green_tea");
        List<Future<String>> responseFuture = this.coffeeMachine.placeAndGetOrder(beverageList);
        assertEquals(4, responseFuture.size());

        try {
            assertEquals("hot_tea is prepared.", responseFuture.get(0).get());
        } catch (Exception e) { }

        try {
            assertEquals("hot_coffee is prepared.", responseFuture.get(1).get());
        } catch (Exception e) { }

        try {
            responseFuture.get(2).get();
        } catch (Exception e) {
            assertEquals("black_tea cannot be prepared because hot_water is not sufficient.", e.getMessage().split(": ")[1]);
        }

        try {
            responseFuture.get(3).get();
        } catch (Exception e) {
            assertEquals("green_tea cannot be prepared because sugar_syrup is not sufficient.", e.getMessage().split(": ")[1]);
        }
    }
}
