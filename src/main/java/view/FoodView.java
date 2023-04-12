package view;

import model.ETypeOfFood;
import model.Food;
import model.Oder;
import service.FileService;
import service.FoodService;
import utils.SortFoodByIDIncrease;
import utils.SortFoodByIdDecrease;
import utils.ValidateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodView {
    private final String FILE_PATH = "./src/main/data/food.csv";
    private FileService fileService;
    private FoodService foodService;
    private Scanner scanner;

    public FoodView() {
        fileService = new FileService();
        foodService = new FoodService();
        scanner = new Scanner(System.in);
    }

    public void menuFoodAdminView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Giao diện đồ uống, thức ăn                          ║");
        System.out.println("                               ║                        1. Hiển thị danh sách đồ uống, thức ăn                     ║");
        System.out.println("                               ║                        2. Thêm đồ uống, thức ăn                                   ║");
        System.out.println("                               ║                        3. Chỉnh sửa đồ uống, thức ăn theo id                      ║");
        System.out.println("                               ║                        4. Tìm kiếm đồ uống, thức ăn theo id                       ║");
        System.out.println("                               ║                        5. Xóa đồ uống, thức ăn theo id                            ║");
        System.out.println("                               ║                        6. Sắp xếp đồ uống, thức ăn theo id tăng dần               ║");
        System.out.println("                               ║                        7. Sắp xếp đồ uống, thức ăn theo id giảm dần               ║");
        System.out.println("                               ║                        8. Thoát chức năng                                         ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void launcher() throws IOException {
        boolean checkAction = false;
        do {
            menuFoodAdminView();
            System.out.println("Chọn chức năng:");
            int select = Integer.parseInt(scanner.nextLine());
            switch (select) {
                case 1:
                    showFoodList();
                    break;
                case 2:
                    addFood();
                    break;
                case 3:
                    editFoodById();
                    break;
                case 4:
                    findFoodById();
                    break;
                case 5:
                    deleteFoodById();
                    break;
                case 6:
                    sortByIdIncrease();
                    break;
                case 7:
                    sortByIdDecrease();
                    break;
                case 8:
                    AdminView adminView = new AdminView();
                    adminView.launcher();
                    break;
                default:
                    FoodView foodView = new FoodView();
                    foodView.launcher();
                    break;
            }
            checkAction = checkActionContinue();
        } while (checkAction);
    }

    private void sortByIdDecrease() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByIdDecrease());
        fileService.writeData(FILE_PATH, foods);
        showFoodList();
        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    private void sortByIdIncrease() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByIDIncrease());
        fileService.writeData(FILE_PATH, foods);
        showFoodList();
        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    private void findFoodById() throws IOException {
        List<Food> foods = foodService.getAllFood();
        boolean checkAction = false;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            int id = Integer.parseInt(scanner.nextLine());
            switch (foodService.checkIdFood(id)){
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == id) {
                            System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                            System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
                            System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
                            System.out.printf(foods.get(i).foodView()).println();
                            System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
                        }
                    }
                    checkAction = true;
                    break;
                case -1:
                    System.out.println("Không tìm thấy ID, mời bạn nhập lại:");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
    }

    public void menuEditFoodView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Chọn mục bạn muốn sửa                               ║");
        System.out.println("                               ║                        1. Chỉnh sửa tên đồ uống, thức ăn                          ║");
        System.out.println("                               ║                        2. Chỉnh sửa số lượng đồ uống, thức ăn                     ║");
        System.out.println("                               ║                        3. Chỉnh sửa giá đồ uống, thức ăn                          ║");
        System.out.println("                               ║                        4. Chỉnh sửa loại uống, thức ăn                            ║");
        System.out.println("                               ║                        5. Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void deleteFoodById() throws IOException {
        List<Food> foods = foodService.getAllFood();
        int id = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn xóa:");
            id = Integer.parseInt(scanner.nextLine());
            int check = foodService.checkIdFood(id);
            switch (check) {
                case 1:
                    checkID = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, mời bạn nhập lại");
                    checkID = false;
                    break;
            }
        } while (!checkID);
        foodService.deleteFoodById(id);
        fileService.writeData(FILE_PATH, foods);
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }
    public void editFoodById() throws IOException {

        List<Food> foods = foodService.getAllFood();
        FoodView foodView = new FoodView();
        int id;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn sửa");
            id = Integer.parseInt(scanner.nextLine());
            int check = foodService.checkIdFood(id);
            switch (check) {
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == id) {
                            do {
                                menuEditFoodView();
                                System.out.println("Chọn mục bạn muốn sửa:");
                                int select = Integer.parseInt(scanner.nextLine());
                                switch (select) {
                                    case 1:
                                        System.out.println("Nhập tên bạn muốn sửa:");
                                        String name = scanner.nextLine();
                                        foods.get(i).setNameFood(name);
                                        break;
                                    case 2:
                                        System.out.println("Nhập số lượng bạn muốn sửa:");
                                        inputFoodQuantity(foods.get(i));
                                        break;
                                    case 3:
                                        System.out.println("Nhập giá bạn muốn sửa:");
                                        inputFoodPrice(foods.get(i));
                                        break;
                                    case 4:
                                        System.out.println("Nhập loại bạn muốn sửa:");
                                        String type = scanner.nextLine();
                                        switch (type) {
                                            case "drink":
                                                break;
                                            case "bakery":
                                                break;
                                            default:
                                                System.out.println("Nhập sai, xin mời nhập lại (drink/bakery):");
                                                type = scanner.nextLine();
                                                break;
                                        }
                                        foods.get(i).seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(type));
                                        break;
                                    case 5:
                                        foodView.editFoodById();
                                        break;
                                    default:
                                        foodView.launcher();
                                        break;
                                }
                            } while (checkAction);
                            foods.set(i, foods.get(i));
                        }
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        }while (!checkId);
        fileService.writeData(FILE_PATH, foods);
        showFoodList();
        System.out.println("✔ Bạn đã cập nhật sản phẩm thành công ✔\n");
    }

    public void addFood() throws IOException {
        List<Food> foods = foodService.getAllFood();
        Food food = new Food();
        System.out.println("Nhập tên đồ uống, thức ăn:");
        String name = scanner.nextLine();

        int checkName = foodService.checkNameFood(name);
        switch (checkName) {
            case 1:
                System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                int quantity = 0;
                boolean checkValid = false;
                do {
                    System.out.println("Nhập số lượng");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                        quantity = 0;
                        continue;
                    }
                    checkValid = ValidateUtils.isQuantity(quantity);
                    if (checkValid==false) {
                        System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                    }
                } while (checkValid==false);
                int quantityNew = 0;
                for (int i = 0; i < foods.size(); i++) {
                    if (foods.get(i).getNameFood().equals(name)) {
                        quantityNew = foods.get(i).getQuantity() + quantity;
                        food.setIdFood(foods.get(i).getIdFood());
                        food.setNameFood(foods.get(i).getNameFood());
                        food.setQuantity(quantityNew);
                        food.setPriceFood(foods.get(i).getPriceFood());
                        food.seteTypeOfFood(foods.get(i).geteTypeOfFood());
                        foods.set(i,food);
                    }
                }
                break;
            case -1:
                inputFoodQuantity(food);
                inputFoodPrice(food);
                System.out.println("Nhập loại (drink/bakery):");
                String typeOfFood = scanner.nextLine();
                switch (typeOfFood) {
                    case "drink":
                        break;
                    case "bakery":
                        break;
                    default:
                        System.out.println("Nhập sai, xin mời nhập lại (drink/bakery):");
                        typeOfFood = scanner.nextLine();
                        break;
                }
                foods.sort(new SortFoodByIDIncrease());
                int id = foods.get(foods.size() - 1).getIdFood() + 1;
                food.setIdFood(id);
                food.setNameFood(name);
//                food.setQuantity(quantity);
//                food.setPriceFood(price);
                food.seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(typeOfFood));
                foods.add(food);
                break;
        }
        fileService.writeData(FILE_PATH, foods);
        showFoodList();
        System.out.println("✔ Bạn đã thêm sản phẩm thành công ✔\n");
    }

    public void showFoodList() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByIDIncrease());
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (Food food : foods) {
            System.out.printf(food.foodView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }
    public void showFoodList(List<Food> foods) throws IOException {
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (Food food : foods) {
            System.out.printf(food.foodView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }

    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Continue? Y/N");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    checkActionContinue = true;
            }
        } while (checkActionContinue);
        return false;
    }
    public void inputFoodPrice(Food food) {
        double price = 0;
        boolean checkValid = false;
        do {
            System.out.println("Nhập giá");
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
                price = 0;
                continue;
            }
            checkValid = ValidateUtils.isValidPrice(price);     // false
            if (checkValid==false) {
                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
            }
        } while (checkValid==false);

        food.setPriceFood(price);
    }
    public void inputFoodQuantity(Food food) {
        int quantity = 0;
        boolean checkValid = false;
        do {
            System.out.println("Nhập số lượng");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                quantity = 0;
                continue;
            }
            checkValid = ValidateUtils.isQuantity(quantity);
            if (checkValid==false) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
            }
        } while (checkValid==false);
        food.setQuantity(quantity);
    }

    public void searchFoodByString() throws IOException {
        System.out.println("Nhập tên bạn muốn tìm kiếm: ");
        String kw = scanner.nextLine();
        List<Food> results = new ArrayList<>();

        List<Food> foods = foodService.getAllFood();
        for (int i = 0; i < foods.size(); i++) {
            if(foods.get(i).getNameFood().contains(kw)){
                results.add(foods.get(i));
            }
        }
        showFoodList(results);
    }
}
