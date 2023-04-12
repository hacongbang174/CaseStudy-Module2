package repository;

import model.Food;

public class FoodUpdateRepository extends FileContext<Food>{
    public FoodUpdateRepository() {
        filePath = "./src/main/data/foodupdate.csv";
        tClass = Food.class;
    }
}
