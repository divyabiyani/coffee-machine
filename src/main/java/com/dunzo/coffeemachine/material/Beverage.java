package com.dunzo.coffeemachine.material;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Beverage {
    private String name;
    private List<Composition> compositionList;
}
