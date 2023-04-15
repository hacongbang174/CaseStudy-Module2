package view;

import model.ETypeOfFood;
import model.Food;
import service.FileService;
import service.FoodService;
import utils.SortFoodByIDIncrease;
import utils.SortFoodByPriceDecrease;
import utils.SortFoodByPriceIncrease;
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
        System.out.println("                               ║                        3. Hiển thị đồ uống, thức ăn theo giá tăng dần             ║");
        System.out.println("                               ║                        4. Hiển thị đồ uống, thức ăn theo giá giảm dần             ║");
        System.out.println("                               ║                        5. Thêm đồ uống, thức ăn                                   ║");
        System.out.println("                               ║                        6. Chỉnh sửa đồ uống, thức ăn theo id                      ║");
        System.out.println("                               ║                        7. Tìm kiếm đồ uống, thức ăn theo id                       ║");
        System.out.println("                               ║                        8. Xóa đồ uống, thức ăn theo id                            ║");
        System.out.println("                               ║                        9. Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void launcher() throws IOException {
        boolean checkAction = false;
        int select = 0;
        do {
            menuFoodAdminView();
            System.out.println("Chọn chức năng:");
            try {
                select = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nhập lỗi, vui lòng nhập lại!");
                select = 0;
                continue;
            }
            switch (select) {
                case 1:
                    showFoodList();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    showFoodListByType();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    sortByPriceIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    sortByPriceDecrease();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    addFood();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    editFoodById();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    findFoodById();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    deleteFoodById();
                    checkAction = checkActionContinue();
                    break;

                case 9:
                    AdminView adminView = new AdminView();
                    adminView.launcher();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, mời nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
        if(checkAction) {
            AdminView adminView = new AdminView();
            adminView.launcher();
        }
    }
    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để quay về giao diện trước đó, nhập \"N\" để quay về giao diện Admin!");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return false;
                case "N":
                    return true;
                default:
                    checkActionContinue = false;
            }
        } while (!checkActionContinue);
        return true;
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

    public void sortByPriceDecrease() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByPriceDecrease());
        fileService.writeData(FILE_PATH_FOOD, foods);
        showFoodList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void sortByPriceIncrease() throws IOException {
        List<Food> foods = foodService.getAllFood();
        foods.sort(new SortFoodByPriceIncrease());
        fileService.writeData(FILE_PATH_FOOD, foods);
        showFoodList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void findFoodById() throws IOException {
        List<Food> foods = foodService.getAllFood();
        boolean checkAction = false;
        noChange();
        int id = 0;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            String input = scanner.nextLine();
            if(input.equals("exit")) {
                checkAction = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                id = 0;
                continue;
            }
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

    public void deleteFoodById() throws IOException {
        showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<Food> foodsUpdate = foodService.getAllFoodUpdate();
        noChange();
        int id = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn xóa:");
            String input = scanner.nextLine();
            if(input.equals("exit")) {
                checkID = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                id = 0;
                continue;
            }
            int check = foodService.checkIdFood(id);
            switch (check) {
                case 1:
                    foodService.deleteFoodById(id);
                    foodService.ddeleteFoodUpdateById(id);
                    checkID = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, mời bạn nhập lại");
                    checkID = false;
                    break;
            }
        } while (!checkID);
//
//        fileService.writeData(FILE_PATH_FOOD, foods);
//        fileService.writeData(FILE_PATH_FOOD_UPDATE,foodsUpdate);
        showFoodList();
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }

    public void editFoodById() throws IOException {
        showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<Food> foodsUpdate = foodService.getAllFoodUpdate();
        FoodView foodView = new FoodView();
        noChange();
        int id = 0;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn sửa");
            String input = scanner.nextLine();
            if(input.equals("exit")) {
                checkId = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                id = 0;
                continue;
            }
            int check = foodService.checkIdFood(id);
            switch (check) {
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == id) {
                            do {
                                menuEditFoodView();
                                System.out.println("Chọn mục bạn muốn sửa:");
                                int select = 0;
                                try {
                                    select = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException numberFormatException) {
                                    System.out.println("Nhập lỗi, vui lòng nhập lại!");
                                    select = 0;
                                    continue;
                                }
                                switch (select) {
                                    case 1:
                                        noChange();
                                        String nameFood = null;
                                        boolean checkValidFullName = false;
                                        boolean checkFullName = false;
                                        do {
                                            System.out.println("Nhập tên đồ uống, thức ăn:");
                                            nameFood = scanner.nextLine();
                                            if (nameFood.equals("exit")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            checkValidFullName = ValidateUtils.isNameFood(nameFood);
                                            if (!checkValidFullName) {
                                                System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidFullName);
                                        foods.get(i).setNameFood(nameFood);
                                        for (int j = 0; j < foodsUpdate.size(); j++) {
                                            if(foodsUpdate.get(j).getIdFood() == id) {
                                                foodsUpdate.get(j).setNameFood(nameFood);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 2:
                                        noChange();
                                        int quantity = 0;
                                        boolean checkValidQuantity = false;
                                        do {
                                            System.out.println("Nhập số lượng bạn muốn sửa:");
                                            String input1 = scanner.nextLine();
                                            if(input1.equals("exit")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            try {
                                                quantity = Integer.parseInt(input1);
                                            } catch (NumberFormatException numberFormatException) {
                                                System.out.println("Số không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                                quantity = 0;
                                                continue;
                                            }
                                            checkValidQuantity = ValidateUtils.isQuantity(quantity);
                                            if (checkValidQuantity == false) {
                                                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                            }
                                        } while (checkValidQuantity == false);
                                        foods.get(i).setQuantity(quantity);
                                        for (int j = 0; j < foodsUpdate.size(); j++) {
                                            if(foodsUpdate.get(j).getIdFood() == id) {
                                                foodsUpdate.get(j).setQuantity(quantity);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 3:
                                        noChange();
                                        double price = 0;
                                        boolean checkValidPrice = false;
                                        do {
                                            System.out.println("Nhập giá bạn muốn sửa");
                                            String input1 = scanner.nextLine();
                                            if(input1.equals("exit")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            try {
                                                price = Double.parseDouble(input1);
                                            } catch (NumberFormatException numberFormatException) {
                                                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
                                                price = 0;
                                                continue;
                                            }
                                            checkValidPrice = ValidateUtils.isValidPrice(price);
                                            if (checkValidPrice == false) {
                                                System.out.println("Giá không hợp lệ vui lòng nhập lại! Giá từ 0-200.000đ");
                                            }
                                        } while (!checkValidPrice);
                                        foods.get(i).setPriceFood(price);
                                        for (int j = 0; j < foodsUpdate.size(); j++) {
                                            if(foodsUpdate.get(j).getIdFood() == id) {
                                                foodsUpdate.get(j).setPriceFood(price);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 4:
                                        noChange();
                                        System.out.println("Nhập loại bạn muốn sửa:");
                                        String type = scanner.nextLine();
                                        if(type.equals("exit")) {
                                            checkId = true;
                                            launcher();
                                        }
                                        if (foods.get(i).geteTypeOfFood().equals(ETypeOfFood.DRINK)) {
                                            switch (type) {
                                                case "bakery":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"drink\"! Mời bạn nhập \"bakery\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            foods.get(i).seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(type));
                                            for (int j = 0; j < foodsUpdate.size(); j++) {
                                                if(foodsUpdate.get(j).getIdFood() == id) {
                                                    foodsUpdate.get(j).seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(type));
                                                }
                                            }
                                        }else if(foods.get(i).geteTypeOfFood().equals(ETypeOfFood.BAKERY)){
                                            switch (type) {
                                                case "drink":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"bakery\"! Mời bạn nhập \"drink\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            foods.get(i).seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(type));
                                            for (int j = 0; j < foodsUpdate.size(); j++) {
                                                if(foodsUpdate.get(j).getIdFood() == id) {
                                                    foodsUpdate.get(j).seteTypeOfFood(ETypeOfFood.getTypeOfFoodByName(type));
                                                }
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 5:
                                        foodView.editFoodById();
                                        checkAction = true;
                                        break;
                                    default:
                                        System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                                        checkAction = false;
                                        break;
                                }
                            } while (!checkAction);
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
        fileService.writeData(FILE_PATH_FOOD_UPDATE,foodsUpdate);
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
                    System.out.println("Nhập sai, xin mời nhập lại! \"drink\" or \"bakery\":");
                    break;
            }
        }while (!checkType);
        boolean checkNameFood = false;
        do {
            String nameFood = null;
            boolean checkValidNameFood = false;
                do {
                    System.out.println("Nhập tên đồ uống, thức ăn (tối đa 40 kí tự):");
                    nameFood = scanner.nextLine();
                    if (nameFood.equals("exit")) {
                        checkNameFood = true;
                        launcher();
                    }
                    checkValidNameFood = ValidateUtils.isNameFood(nameFood);
                    if (!checkValidNameFood) {
                        System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                    }
                } while (!checkValidNameFood);
            int checkName = foodService.checkNameFood(nameFood);
            switch (checkName) {
                case 1:
                    System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                    int quantity = 0;
                    boolean checkValidQuantity = false;
                    do {
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        try {
                            quantity = Integer.parseInt(input);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Số không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                            quantity = 0;
                            continue;
                        }
                        checkValidQuantity = ValidateUtils.isQuantity(quantity);
                        if (checkValidQuantity == false) {
                            System.out.println("Số không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                        }
                    } while (checkValidQuantity == false);
                    int quantityNew = 0;
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getNameFood().equals(nameFood)) {
                            quantityNew = foods.get(i).getQuantity() + quantity; //thiếu validate <= 1000
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
                    boolean checkValidQuantity2 = false;
                    do {
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        try {
                            quantity1 = Integer.parseInt(input);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Nhập lỗi, vui lòng nhập lại! Số lượng từ 0-1000");
                            quantity1 = 0;
                            continue;
                        }

                        checkValidQuantity2 = ValidateUtils.isQuantity(quantity1);
                        if (checkValidQuantity2 == false) {
                            System.out.println("Số lượng không đúng, vui lòng nhập lại! Số lượng từ 0-1000");
                        }
                    } while (!checkValidQuantity2);

                    double price = 0;
                    boolean checkValidPrice = false;
                    do {
                        System.out.println("Nhập giá");
                        String input = scanner.nextLine();
                        if(input.equals("exit")) {
                            checkNameFood = true;
                            launcher();
                        }
                        try {
                            price = Double.parseDouble(input);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Nhập lỗi, vui lòng nhập lại! Giá từ 0-200.000đ");
                            price = 0;
                            continue;
                        }
                        checkValidPrice = ValidateUtils.isValidPrice(price);     // false
                        if (checkValidPrice == false) {
                            System.out.println("Giá không đúng vui lòng nhập lại! Giá từ 0-200.000đ");
                        }
                    } while (!checkValidPrice);

                    foods.sort(new SortFoodByIDIncrease());
                    int id = foods.get(foods.size() - 1).getIdFood() + 1;
                    food.setIdFood(id);
                    food.setNameFood(nameFood);
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
//        foods.sort(new SortFoodByIDIncrease());
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (Food food : foods) {
            System.out.printf(food.foodView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }
    public void showFoodListStepTenFood() throws IOException {
        List<Food> foods = foodService.getAllFood();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Food's name", "Quantity", "Price", "Type Of Food").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (int i = 0; i < 10; i++) {
            System.out.printf(foods.get(i).foodView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
        boolean checkShow = false;

        int size = foods.size()-10;
        int index = 10;
        while (size >= 10) {
            do {
                System.out.println("Nhấn Enter để xem tiếp!");
                String s = scanner.nextLine();
                if (s == "") {
                    System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                    for (int i = index; i < index + 10; i++) {
                        if(size>=5){
                            System.out.printf(foods.get(i).foodView()).println();
                        }
                    }
                    System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
                    index += 10;
                    size -= 10;
                    checkShow = true;
                }else {
                    checkShow = false;
                }
            }while (!checkShow);
        }
        boolean checkShow2 = false;
        do {
            System.out.println("Nhấn Enter để xem tiếp!");
            String s = scanner.nextLine();
            if (s == "") {
                System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                for (int i = size; i > 1; i--) {
                    System.out.printf(foods.get(foods.size() - size).foodView()).println();
                }
                System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
                checkShow2 = true;
            }else {
                checkShow2 = false;
            }
        }while (!checkShow2);

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

    public void showFoodListByType() throws IOException {
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
                    System.out.println("Nhập sai, xin mời nhập lại! \"drink\" or \"bakery\":");
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

    public void searchFoodByKeyword() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Food> results = new ArrayList<>();
        List<Food> foods = foodService.getAllFood();
        noChange();
        boolean checkKW = false;
        do {
            System.out.println("Nhập tên bạn muốn tìm kiếm: ");
            String kw = scanner.nextLine();
            if(kw.equals("exit")) {
                checkKW = true;
                customerView.launcher();
            }
            boolean checkOut = false;
            for (int i = 0; i < foods.size(); i++) {
                if (foods.get(i).getNameFood().toUpperCase().contains(kw.toUpperCase())) {
                    results.add(foods.get(i));
                    checkOut = true;
                }
            }
            if(!checkOut) {
                System.out.println("Không tìm thấy món, vui lòng nhập lại!");
                checkKW = false;
            }else {
                checkKW = true;
            }
        }while (!checkKW);
        showFoodList(results);
    }

    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"exit\" ⦿ ");
    }

}
