package repository;

import model.Food;

public class FoodRepository extends FileContext<Food> {
    public FoodRepository() {
        filePath = "./src/main/data/food.csv";
        tClass = Food.class;
    }
}
