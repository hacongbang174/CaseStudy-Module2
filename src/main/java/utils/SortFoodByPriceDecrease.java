package utils;

import model.Food;

import java.util.Comparator;

public class SortFoodByPriceDecrease implements Comparator<Food> {

    @Override
    public int compare(Food o1, Food o2) {
        if(o2.getPriceFood() -  o1.getPriceFood() > 0)
        {
            return 1;
        }else if (o2.getPriceFood() -  o1.getPriceFood() == 0) {
            return 0;
        }else {
            return -1;
        }
    }
}
