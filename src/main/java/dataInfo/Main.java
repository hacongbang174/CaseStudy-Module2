package dataInfo;

import model.Food;
import model.Oder;
import model.User;
import service.FoodService;
import service.OderService;
import service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FoodService foodService = new FoodService();
        UserService userService = new UserService();
        OderService oderService = new OderService();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUser();
        List<Oder> oderAll = oderService.getAllOderAll();
        String nameFood = "Trà Ô long dừa Caramel";
        int quantity = 0;
        boolean checkQuantity = false;
        boolean checkValid = false;
        do {
            System.out.println("Nhập số lượng:");
            String input = scanner.nextLine();
            try {
                quantity = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                quantity = 0;
                continue;
            }
            for (int j = 0; j < foods.size(); j++) {
                for (int k = 0; k < oderAll.size(); k++) {
                    if (foods.get(j).getNameFood().equals(nameFood) && oderAll.get(k).getNameFood().equals(nameFood)) {
                        if (0 < quantity + oderAll.get(k).getQuantityFood() && quantity + oderAll.get(k).getQuantityFood() <= foods.get(j).getQuantity()) {
                            checkValid = true;
                            checkQuantity = true;
                            break;
                        }
                    }
                    if (foods.get(j).getNameFood().equals(nameFood) && !oderAll.get(k).getNameFood().equals(nameFood)) {
                        if (0 < quantity  && quantity <= foods.get(j).getQuantity()) {
                            checkValid = true;
                            checkQuantity = true;
                            break;
                        }
                    }
                }
            }
            if (!checkQuantity) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
            }
            if (!checkValid) {
                System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
            }
        } while (!checkQuantity);
    }
}
