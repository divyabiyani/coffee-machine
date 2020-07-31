package com.dunzo.coffeemachine;

import com.dunzo.coffeemachine.material.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoffeeMachineTest {
    private CoffeeMachine coffeeMachine;

    @Before
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

    @Test
    public void placeAndGetOrderTestSuccessful() {
        String response = this.coffeeMachine.placeAndGetOrder("hot_tea");
        assertEquals("hot_tea is prepared.", response);
    }

    @Test
    public void placeAndGetOrderTestMultiple() {
        String response = this.coffeeMachine.placeAndGetOrder("hot_tea");
        assertEquals("hot_tea is prepared.", response);

        response = this.coffeeMachine.placeAndGetOrder("black_tea");
        assertEquals("black_tea is prepared.", response);

        response = this.coffeeMachine.placeAndGetOrder("hot_coffee");
        assertEquals("hot_coffee cannot be prepared because sugar_syrup is not sufficient.", response);
    }

    @Test
    public void placeAndGetOrderIncorrectBeverageName() {
        String response = this.coffeeMachine.placeAndGetOrder("hot_teaj");
        assertEquals("We do not provide the following hot_teaj", response);
    }

    @Test
    public void placeAndGetOrderMissingIngredient() {
        String response = this.coffeeMachine.placeAndGetOrder("cafe_latte");
        assertEquals("cafe_latte cannot be prepared because cream is not available.", response);
    }

    @Test
    public void placeAndGetOrderListTestSuccessful() {
        List<String> beverageList = new ArrayList<>();
        beverageList.add("hot_tea");
        beverageList.add("hot_coffee");
        beverageList.add("black_tea");
        beverageList.add("green_tea");
        List<String> response = this.coffeeMachine.placeAndGetOrder(beverageList);
        System.out.println(response.toString());
        assertEquals(4, response.size());
        assertEquals("hot_tea is prepared.", response.get(0));
        assertEquals("hot_coffee is prepared.", response.get(1));
        assertEquals("black_tea cannot be prepared because hot_water is not sufficient.", response.get(2));
        assertEquals("green_tea cannot be prepared because sugar_syrup is not sufficient.", response.get(3));
    }
}
