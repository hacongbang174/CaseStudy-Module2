package service;

import model.ERole;
import model.Food;
import model.User;
import repository.FoodRepository;
import repository.FoodUpdateRepository;
import repository.IModel;
import repository.ISearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodService {
    private  FoodRepository foodRepository;
    private FoodUpdateRepository foodUpdateRepository;
    public FoodService() {
        foodRepository = new FoodRepository();
        foodUpdateRepository = new FoodUpdateRepository();
    }
    public List<Food> getAllFood() throws IOException {
        return foodRepository.getAll();
    }
    public List<Food> getAllFoodUpdate() throws IOException {
        return foodUpdateRepository.getAll();
    }
    public int checkIdFood(int id) throws IOException {
        return foodRepository.checkID(id);
    }
    public int checkNameFood(String name) throws IOException {
        return foodRepository.checkName(name);
    }
    public void deleteFoodById(int id) throws IOException {
        foodRepository.deleteById(id);
    }
    public void ddeleteFoodUpdateById(int id) throws IOException {
        foodUpdateRepository.deleteById(id);
    }
}
