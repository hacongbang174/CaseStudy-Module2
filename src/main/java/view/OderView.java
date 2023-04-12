package view;

import model.Food;
import model.Oder;
import model.User;
import service.FileService;
import service.FoodService;
import service.OderService;
import service.UserService;
import utils.SortOderById;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OderView {
    private static final String FILE_PATH_FOOD = "./src/main/data/food.csv";
    private final String FILE_PATH = "./src/main/data/oder.csv";
    private final String FILE_PATH_ODERALL = "./src/main/data/oderAll.csv";
    private FoodService foodService;
    private UserService userService;
    private OderService oderService;
    private FileService fileService;
    private Scanner scanner;

    public OderView() {
        foodService = new FoodService();
        userService = new UserService();
        oderService = new OderService();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void addFoodInOder() throws IOException {
//        int idOder, String nameCustomer, String nameFood, int quantityFood, double priceFood, double totalMoney, Date createDateOder
        FoodView foodView = new FoodView();
        foodView.showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUserUse();
        List<Oder> oderAll = oderService.getAllOderAll();
        List<Oder> oders = oderService.getAllOder();
        oderAll.sort(new SortOderById());
        Oder oder = new Oder();
        int id;
        String nameFood = null;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn oder:");
            id = Integer.parseInt(scanner.nextLine());
            int check = foodService.checkIdFood(id);
            switch (check) {
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == id) {
                            nameFood = foods.get(i).getNameFood();
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
        int quantity = 0;
        boolean checkQuantity = false;
        boolean checkValid = false;
        do {
            System.out.println("Nhập số lượng:");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                quantity = 0;
                continue;
            }
            for (int i = 0; i < foods.size(); i++) {
                if (foods.get(i).getIdFood() == id) {
                    if (quantity <= foods.get(i).getQuantity()) {
                        checkValid = true;
                        checkQuantity = true;
                    } else {
                        checkValid = false;
                        checkQuantity = false;
                    }
                }
            }
            if (!checkValid) {
                System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
            }
        } while (!checkQuantity);
        double price = 0;
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getIdFood() == id) {
                price = foods.get(i).getPriceFood();
                foods.get(i).setQuantity(foods.get(i).getQuantity() - quantity);
            }
        }
        double totalMoney = quantity * price;
        if(oderAll.isEmpty()) {
            oder.setIdOder(1);
        }else {
            oder.setIdOder(oderAll.get(oderAll.size() - 1).getIdOder() + 1);
        }
        oder.setNameCustomer(users.get(0).getFullName());
        oder.setNameFood(nameFood);
        oder.setQuantityFood(quantity);
        oder.setPriceFood(price);
        oder.setTotalMoney(totalMoney);
        oder.setCreateDateOder(new Date());
        oderAll.add(oder);
        oders.add(oder);
        fileService.writeData(FILE_PATH, oders);
        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");
    }

    public void editQuantityFoodInOder() throws IOException {
        showOder();
        List<Food> foods = foodService.getAllFood();
        List<Oder> oderAll = oderService.getAllOderAll();
        List<Oder> oders = oderService.getAllOder();
        String nameFood = null;
        boolean checkName = false;
        int quantity = 0;
        do {
            boolean checkAction = false;
            System.out.println("Nhập tên đồ uống, thức ăn bạn muốn sửa số lượng:");
            nameFood = scanner.nextLine();
            int check = oderService.checkNameFoodInOder(nameFood);
            switch (check) {
                case 1:
                    boolean checkQuantity = false;
                    boolean checkValid = false;
                    do {
                        System.out.println("Nhập số lượng bạn muốn sửa:");
                        try {
                            quantity = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                            quantity = 0;
                            continue;
                        }
                        for (int i = 0; i < oders.size(); i++) {
                            for (int j = 0; j < foods.size(); j++) {
                                if (oders.get(i).getNameFood().equals(nameFood) && foods.get(j).getNameFood().equals(nameFood)) {
                                    if (quantity <= foods.get(j).getQuantity() + oders.get(i).getQuantityFood()) {
                                        checkValid = true;
                                    } else {
                                        checkValid = false;
                                    }
                                }
                            }
                        }
                        if (!checkValid) {
                            System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
                        }
                    } while (!checkQuantity);
                    checkName = true;
                    break;
                case -1:
                    System.out.println("Tên không tìm thấy, mời bạn nhập lại");
                    checkName = false;
                    break;
            }
        } while (!checkName);
        for (int j = 0; j < foods.size(); j++) {
            for (int i = 0; i < oders.size(); i++) {
                if (foods.get(j).getNameFood().equals(nameFood) && oders.get(i).getNameFood().equals(nameFood)) {
                    foods.get(j).setQuantity(foods.get(j).getQuantity()+oders.get(i).getQuantityFood() - quantity);
                }
            }
        }
        for(int i = 0; i<oders.size();i++) {
            if (oders.get(i).getNameFood().equals(nameFood)) {
                oders.get(i).setQuantityFood(quantity);
            }
        }
        for(int i = 0; i<oderAll.size();i++) {
            if (oderAll.get(i).getNameFood().equals(nameFood)) {
                oderAll.get(i).setQuantityFood(quantity);
            }
        }
        fileService.writeData(FILE_PATH, oders);
        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã cập nhật số lượng thành công ✔\n");
    }

    public void deleteFoodOutOder() throws IOException {
        showOder();
        List<Food> foods = foodService.getAllFood();
        List<Oder> oderAll = oderService.getAllOderAll();
        List<Oder> oders = oderService.getAllOder();
        String nameFood = null;
        boolean checkName = false;
        do {
            System.out.println("Nhập tên đồ uống, thức ăn bạn muốn sửa số lượng:");
            nameFood = scanner.nextLine();
            int check = oderService.checkNameFoodInOder(nameFood);
            switch (check) {
                case 1:
                    checkName = true;
                    break;
                case -1:
                    System.out.println("Tên không tìm thấy, mời bạn nhập lại");
                    checkName = false;
                    break;
            }
        } while (!checkName);
        oderService.deleteFoodOutOderByName(nameFood);
        oderService.deleteFoodOutOderAllByName(nameFood);

//        fileService.writeData(FILE_PATH, oders);
//        fileService.writeData(FILE_PATH_ODERALL, oderAll);
//        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }
    public void showOder() throws IOException {
        List<Oder> oders = oderService.getAllOder();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oders.size(); i++) {
            System.out.printf(oders.get(i).oderView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╝");

    }
    public void showHistoryOder() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            if(oderAll.get(i).getNameCustomer().equals(users.get(0).getFullName())) {
                System.out.printf(oderAll.get(i).oderView()).println();
            }
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╝");
    }

    public void payOder() throws IOException {
        List<Oder> oders = oderService.getAllOder();
        double totalMoney = 0;
        for (int i = 0; i < oders.size(); i++) {
            totalMoney += oders.get(i).getTotalMoney();
        }
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oders.size(); i++) {
            System.out.printf(oders.get(i).oderView()).println();
        }
        System.out.println("            ╠═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╣");
        System.out.printf("            ║                                         TỔNG TIỀN CẦN THANH TOÁN                                       ║ %-13s ║                               ║", totalMoney).println();
        System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩═══════════════════════════════╝");
    }

    public void showTotalRevenue() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        double totalRevenue = 0;
        for (int i = 0; i < oderAll.size(); i++) {
            totalRevenue += oderAll.get(i).getTotalMoney();
        }
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            System.out.printf(oderAll.get(i).oderView()).println();
        }
        System.out.println("            ╠═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╣");
        System.out.printf("            ║                                                   TỔNG DOANH THU                                       ║ %-13s ║                               ║", totalRevenue).println();
        System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩═══════════════════════════════╝");
    }

    public void showOderAll() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            System.out.printf(oderAll.get(i).oderView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╝");
    }

    public void findOderByNameCustomer() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        System.out.println("Nhập tên khách hàng:");
        String nameCustomer = scanner.nextLine();

        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER" , "NAME CUSTOMER" , "NAME FOOD" , "QUANTITY" , "PRICE" , "TOTAL MONEY" , "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            if(oderAll.get(i).getNameCustomer().equals(nameCustomer)) {
                System.out.printf(oderAll.get(i).oderView()).println();
            }
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╝");
    }

    public void editOder() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        List<Food> foods = foodService.getAllFood();
        int idOder = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID oder muốn sửa số lượng:");
            idOder = Integer.parseInt(scanner.nextLine());
            int check = oderService.checkIdOderAll(idOder);
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
        String nameCustomer = null;
        boolean checkNameCustomer = false;
        do {
            System.out.println("Nhập tên khách hàng mới:");
            nameCustomer = scanner.nextLine();
            int check = userService.checkNameCustomer(nameCustomer);
            switch (check) {
                case 1:
                    checkNameCustomer = true;
                    break;
                case -1:
                    System.out.println("Khách hàng chưa đăng ký, mời bạn nhập lại");
                    checkNameCustomer = false;
                    break;
            }
        } while (!checkNameCustomer);
        String nameFood = null;
        boolean checkNameFood = false;
        do {
            System.out.println("Nhập tên món mới:");
            nameFood = scanner.nextLine();
            int check = foodService.checkNameFood(nameFood);
            switch (check) {
                case 1:
                    checkNameFood = true;
                    break;
                case -1:
                    System.out.println("Tên món không có trong menu, mời bạn nhập lại");
                    checkNameFood = false;
                    break;
            }
        } while (!checkNameFood);
        int quantity = 0;
        boolean checkQuantity = false;
        boolean checkValid = false;
        do {
            System.out.println("Nhập số lượng:");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                quantity = 0;
                continue;
            }
            for (int i = 0; i < foods.size(); i++) {
                if (foods.get(i).getNameFood().equals(nameFood)) {
                    if (quantity <= foods.get(i).getQuantity()) {
                        checkValid = true;
                        checkQuantity = true;
                    } else {
                        checkValid = false;
                        checkQuantity = false;
                    }
                }
            }
            if (!checkValid) {
                System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
            }
        } while (!checkQuantity);
        double price = 0;
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getNameFood().equals(nameFood)) {
                price = foods.get(i).getPriceFood();
                foods.get(i).setQuantity(foods.get(i).getQuantity() - quantity);
            }
        }
        double totalMoney = quantity * price;

        for (int i = 0; i < oderAll.size(); i++) {
            if(oderAll.get(i).getIdOder() == idOder) {
                oderAll.get(i).setNameCustomer(nameCustomer);
                oderAll.get(i).setNameFood(nameFood);
                oderAll.get(i).setQuantityFood(quantity);
                oderAll.get(i).setPriceFood(price);
                oderAll.get(i).setTotalMoney(totalMoney);
                oderAll.get(i).setCreateDateOder(new Date());
            }
        }
        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã sửa đơn hàng thành công ✔\n");
    }

    public void deleteOder() throws IOException {
        showOderAll();
        int idOder = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID oder muốn sửa số lượng:");
            idOder = Integer.parseInt(scanner.nextLine());
            int check = oderService.checkIdOderAll(idOder);
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
        oderService.deleteFoodOutOderAllById(idOder);
//        List<Oder> oderAll = oderService.getAllOderAll();
//        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        System.out.println("✔ Bạn đã xóa oder thành công ✔\n");
    }

    public void addOder() throws IOException {
        FoodView foodView = new FoodView();
        foodView.showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUser();
        List<Oder> oderAll = oderService.getAllOderAll();
        oderAll.sort(new SortOderById());
        Oder oder = new Oder();
        String nameCustomer = null;
        boolean checkNameCustomer = false;
        do {
            System.out.println("Nhập tên khách hàng mới:");
            nameCustomer = scanner.nextLine();
            int check = userService.checkNameCustomer(nameCustomer);
            switch (check) {
                case 1:
                    checkNameCustomer = true;
                    break;
                case -1:
                    System.out.println("Khách hàng chưa đăng ký, mời bạn nhập lại");
                    checkNameCustomer = false;
                    break;
            }
        } while (!checkNameCustomer);
        String nameFood = null;
        boolean checkNameFood = false;
        do {
            System.out.println("Nhập tên món mới:");
            nameFood = scanner.nextLine();
            int check = foodService.checkNameFood(nameFood);
            switch (check) {
                case 1:
                    checkNameFood = true;
                    break;
                case -1:
                    System.out.println("Tên món không có trong menu, mời bạn nhập lại");
                    checkNameFood = false;
                    break;
            }
        } while (!checkNameFood);
        int quantity = 0;
        boolean checkQuantity = false;
        boolean checkValid = false;
        do {
            System.out.println("Nhập số lượng:");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                quantity = 0;
                continue;
            }
            for (int i = 0; i < foods.size(); i++) {
                if (foods.get(i).getNameFood().equals(nameFood)) {
                    if (quantity <= foods.get(i).getQuantity()) {
                        checkValid = true;
                        checkQuantity = true;
                    } else {
                        checkValid = false;
                        checkQuantity = false;
                    }
                }
            }
            if (!checkValid) {
                System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
            }
        } while (!checkQuantity);
        double price = 0;
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getNameFood().equals(nameFood)) {
                price = foods.get(i).getPriceFood();
                foods.get(i).setQuantity(foods.get(i).getQuantity() - quantity);
            }
        }
        double totalMoney = quantity * price;

        if(oderAll.isEmpty()) {
            oder.setIdOder(1);
        }else {
            oder.setIdOder(oderAll.get(oderAll.size() - 1).getIdOder() + 1);
        }
        oder.setNameCustomer(nameCustomer);
        oder.setNameFood(nameFood);
        oder.setQuantityFood(quantity);
        oder.setPriceFood(price);
        oder.setTotalMoney(totalMoney);
        oder.setCreateDateOder(new Date());
        oderAll.add(oder);
        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");
    }

//    public static void main(String[] args) throws IOException {
//        OderView oderView = new OderView();
//        oderView.showTotalRevenue();
//    }
}
