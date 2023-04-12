package view;

import model.ETypeOfFood;
import model.Food;
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
    private final String FILE_PATH_FOOD = "./src/main/data/food.csv";
    private final String FILE_PATH_FOOD_UPDATE = "./src/main/data/foodupdate.csv";
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
        System.out.println("                               ║                        2. Hiển thị danh sách đồ uống, thức ăn theo danh mục       ║");
        System.out.println("                               ║                        3. Thêm đồ uống, thức ăn                                   ║");
        System.out.println("                               ║                        4. Chỉnh sửa đồ uống, thức ăn theo id                      ║");
        System.out.println("                               ║                        5. Tìm kiếm đồ uống, thức ăn theo id                       ║");
        System.out.println("                               ║                        6. Xóa đồ uống, thức ăn theo id                            ║");
        System.out.println("                               ║                        7. Sắp xếp đồ uống, thức ăn theo id tăng dần               ║");
        System.out.println("                               ║                        8. Sắp xếp đồ uống, thức ăn theo id giảm dần               ║");
        System.out.println("                               ║                        9. Quay lại                                                ║");
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
                    showFoodListByType();
                    break;
                case 3:
                    addFood();
                    break;
                case 4:
                    editFoodById();
                    break;
                case 5:
                    findFoodById();
                    break;
                case 6:
                    deleteFoodById();
                    break;
                case 7:
                    sortByIdIncrease();
                    break;
                case 8:
                    sortByIdDecrease();
                    break;
                case 9:
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
        fileService.writeData(FILE_PATH_FOOD, foods);
        showFoodList();
        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    private void sortByIdIncrease() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByIDIncrease());
        fileService.writeData(FILE_PATH_FOOD, foods);
        showFoodList();
        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    private void findFoodById() throws IOException {
        List<Food> foods = foodService.getAllFood();
        boolean checkAction = false;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            int id = Integer.parseInt(scanner.nextLine());
            switch (foodService.checkIdFood(id)) {
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
        } while (!checkAction);
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
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }

    public void editFoodById() throws IOException {

        List<Food> foods = foodService.getAllFood();
        FoodView foodView = new FoodView();
        noChange();
        int id;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn sửa");
            String input = scanner.nextLine();
            if(input.equals("exit")) {
                checkId = true;
                launcher();
            }
            id = Integer.parseInt(input);
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
                                        noChange();
                                        System.out.println("Nhập tên bạn muốn sửa:");
                                        String name = scanner.nextLine();
                                        if(name.equals("exit")) {
                                            checkId = true;
                                            launcher();
                                        }
                                        foods.get(i).setNameFood(name);
                                        break;
                                    case 2:
                                        noChange();
                                        System.out.println("Nhập số lượng bạn muốn sửa:");
                                        int quantity = 0;
                                        boolean checkValid = false;
                                        do {
                                            System.out.println("Nhập số lượng");
                                            String input1 = scanner.nextLine();
                                            if(input.equals("exit")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            quantity = Integer.parseInt(input1);
                                            checkValid = ValidateUtils.isQuantity(quantity);
                                            if (checkValid == false) {
                                                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                            }
                                        } while (checkValid == false);
                                        foods.get(i).setQuantity(quantity);
                                        break;
                                    case 3:
                                        noChange();
                                        double price = 0;
                                        boolean checkValid1 = false;
                                        do {
                                            System.out.println("Nhập giá");
                                            String input1 = scanner.nextLine();
                                            if(input.equals("exit")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            price = Double.parseDouble(input1);
                                            checkValid = ValidateUtils.isValidPrice(price);
                                            if (checkValid1 == false) {
                                                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
                                            }
                                        } while (!checkValid1);
                                        foods.get(i).setPriceFood(price);
                                        break;
                                    case 4:
                                        noChange();
                                        System.out.println("Nhập loại bạn muốn sửa:");
                                        String type = scanner.nextLine();
                                        if(type.equals("exit")) {
                                            checkId = true;
                                            launcher();
                                        }
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
        } while (!checkId);
        fileService.writeData(FILE_PATH_FOOD, foods);
        showFoodList();
        System.out.println("✔ Bạn đã cập nhật sản phẩm thành công ✔\n");
    }

    public void addFood() throws IOException {
        List<Food> foods = foodService.getAllFood();
        List<Food> foodsUpdate = foodService.getAllFoodUpdate();
        Food food = new Food();
        noChange();
        String typeOfFood;
        boolean checkType = false;
        do {
            System.out.println("Nhập danh mục cần thêm! \"drink\" or \"bakery\":");
            typeOfFood = scanner.nextLine();
            if(typeOfFood.equals("exit")){
                checkType = true;
                launcher();
            }
            switch (typeOfFood) {
                case "drink":
                    checkType = true;
                    break;
                case "bakery":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại (drink/bakery):");
                    break;
            }
        }while (!checkType);
        boolean checkNameFood = false;
        do {
            System.out.println("Nhập tên đồ uống, thức ăn:");
            String name = scanner.nextLine();
            if(name.equals("exit")) {
                checkNameFood = true;
                launcher();
            }
            int checkName = foodService.checkNameFood(name);
            switch (checkName) {
                case 1:
                    System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                    int quantity = 0;
                    boolean checkValid = false;
                    do {
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        quantity = Integer.parseInt(input);
                        checkValid = ValidateUtils.isQuantity(quantity);
                        if (checkValid == false) {
                            System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                        }
                    } while (checkValid == false);
                    int quantityNew = 0;
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getNameFood().equals(name)) {
                            quantityNew = foods.get(i).getQuantity() + quantity;
                            food.setIdFood(foods.get(i).getIdFood());
                            food.setNameFood(foods.get(i).getNameFood());
                            food.setQuantity(quantityNew);
                            food.setPriceFood(foods.get(i).getPriceFood());
                            food.seteTypeOfFood(foods.get(i).geteTypeOfFood());
                            foods.set(i, food);
                        }
                    }
                    checkNameFood = true;
                    break;
                case -1:
                    int quantity1 = 0;
                    boolean checkValid1 = false;
                    do {
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        quantity1 = Integer.parseInt(input);
                        checkValid1 = ValidateUtils.isQuantity(quantity1);
                        if (checkValid1 == false) {
                            System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                        }
                    } while (!checkValid1);

                    double price = 0;
                    boolean checkValid2 = false;
                    do {
                        System.out.println("Nhập giá");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        price = Double.parseDouble(input);
                        checkValid2 = ValidateUtils.isValidPrice(price);     // false
                        if (checkValid2 == false) {
                            System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
                        }
                    } while (!checkValid2);

                    foods.sort(new SortFoodByIDIncrease());
                    int id = foods.get(foods.size() - 1).getIdFood() + 1;
                    food.setIdFood(id);
                    food.setNameFood(name);
                    food.setQuantity(quantity1);
                    food.setPriceFood(price);
                    food.seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(typeOfFood));
                    foods.add(food);
                    foodsUpdate.add(food);
                    checkNameFood = true;
                    break;
            }
        }while (!checkNameFood);

        fileService.writeData(FILE_PATH_FOOD_UPDATE, foodsUpdate);
        fileService.writeData(FILE_PATH_FOOD, foods);
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

    private void showFoodListByType() throws IOException {
        List<Food> foods = foodService.getAllFood();
        noChange();
        String typeOfFood;
        boolean checkType = false;
        do {
            System.out.println("Nhập danh mục cần thêm! \"drink\" or \"bakery\":");
            typeOfFood = scanner.nextLine();
            if(typeOfFood.equals("exit")){
                checkType = true;
                launcher();
            }
            switch (typeOfFood) {
                case "drink":
                    checkType = true;
                    break;
                case "bakery":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại (drink/bakery):");
                    break;
            }
        }while (!checkType);
        foods.sort(new SortFoodByIDIncrease());
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (int i = 0; i < foods.size(); i++) {
            if(foods.get(i).geteTypeOfFood().getName().equals(typeOfFood)) {
                System.out.printf(foods.get(i).foodView()).println();
            }

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
            if (checkValid == false) {
                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
            }
        } while (checkValid == false);

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
            if (checkValid == false) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
            }
        } while (checkValid == false);
        food.setQuantity(quantity);
    }

    public void searchFoodByString() throws IOException {
        System.out.println("Nhập tên bạn muốn tìm kiếm: ");
        String kw = scanner.nextLine();
        List<Food> results = new ArrayList<>();

        List<Food> foods = foodService.getAllFood();
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getNameFood().contains(kw)) {
                results.add(foods.get(i));
            }
        }
        showFoodList(results);
    }

    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: exit ⦿ ");
    }

    public static void main(String[] args) throws IOException {
        FoodView foodView = new FoodView();
//        foodView.showFoodListByType();
        foodView.addFood();
    }

}
