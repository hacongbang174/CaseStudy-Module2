package service;

import model.ERole;
import model.Food;
import model.User;
import repository.FoodRepository;
import repository.IModel;
import repository.ISearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodService {
    private static FoodRepository foodRepository;
    public FoodService() {
        foodRepository = new FoodRepository();
    }
    public List<Food> getAllFood() throws IOException {
        return foodRepository.getAll();
    }
    public Food findFoodById(int id) throws IOException {
        return foodRepository.findById(id);
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
    public void addFood(Food food) throws IOException {
        foodRepository.add(food);
    }
    public void updateFoodById(int id, Food food) throws IOException {
        foodRepository.updateById(id,food);
    }
    public List<Food> searchFoodByName(String name, ISearch<Food> iSearch) throws IOException {
        return foodRepository.searchByName(name,iSearch);
    }
    public void addFoodList(List<Food> list) throws IOException {
        foodRepository.addList(list);
    }

    public static void main(String[] args) throws IOException {
        FoodService foodService = new FoodService();
        foodService.getAllFood();
        for(Food food : foodService.getAllFood()) {
            System.out.println(food);
        }
    }
}
