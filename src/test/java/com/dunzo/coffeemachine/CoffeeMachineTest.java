package com.dunzo.coffeemachine;

import com.dunzo.coffeemachine.material.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Composition> hot_tea= new ArrayList<>();
        hot_tea.add(new Composition(new Ingredient("hot_water"), 200));
        hot_tea.add(new Composition(new Ingredient("hot_milk"), 100));
        hot_tea.add(new Composition(new Ingredient("ginger_syrup"), 10));
        hot_tea.add(new Composition(new Ingredient("sugar_syrup"), 10));
        hot_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("hot_tea", hot_tea));

        List<Composition> hot_coffee= new ArrayList<>();
        hot_tea.add(new Composition(new Ingredient("hot_water"), 100));
        hot_tea.add(new Composition(new Ingredient("hot_milk"), 400));
        hot_tea.add(new Composition(new Ingredient("ginger_syrup"), 30));
        hot_tea.add(new Composition(new Ingredient("sugar_syrup"), 50));
        hot_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("hot_coffee", hot_coffee));

        List<Composition> black_tea= new ArrayList<>();
        hot_tea.add(new Composition(new Ingredient("hot_water"), 300));
        hot_tea.add(new Composition(new Ingredient("ginger_syrup"), 30));
        hot_tea.add(new Composition(new Ingredient("sugar_syrup"), 50));
        hot_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("black_tea", black_tea));

        List<Composition> green_tea= new ArrayList<>();
        hot_tea.add(new Composition(new Ingredient("hot_water"), 100));
        hot_tea.add(new Composition(new Ingredient("ginger_syrup"), 30));
        hot_tea.add(new Composition(new Ingredient("sugar_syrup"), 50));
        hot_tea.add(new Composition(new Ingredient("tea_leaves_syrup"), 30));
        beverageList.add(new Beverage("green_tea", green_tea));

        this.coffeeMachine = new CoffeeMachine(noOfOutlets, initialInventory, beverageList);
    }

    @Test
    public void placeAndGetOrderTest() {
        this.coffeeMachine.placeAndGetOrder("hot_tea");
    }
}
