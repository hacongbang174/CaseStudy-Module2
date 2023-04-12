package utils;

import model.Food;

import java.util.Comparator;

public class SortFoodByIDIncrease implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        return o1.getIdFood() - o2.getIdFood();
    }
}
