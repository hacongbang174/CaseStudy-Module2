package utils;

import model.Food;

import java.util.Comparator;

public class SortFoodByIdDecrease implements Comparator<Food> {

    @Override
    public int compare(Food o1, Food o2) {
        return o2.getIdFood()- o1.getIdFood();
    }
}
