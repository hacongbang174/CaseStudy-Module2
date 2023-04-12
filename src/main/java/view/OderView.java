package view;

import model.EStatus;
import model.Food;
import model.Oder;
import model.User;
import service.FileService;
import service.FoodService;
import service.OderService;
import service.UserService;
import utils.SortOderById;
import utils.ValidateUtils;

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
        if (oderAll.isEmpty()) {
            oder.setIdOder(1);
        } else {
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
                    foods.get(j).setQuantity(foods.get(j).getQuantity() + oders.get(i).getQuantityFood() - quantity);
                }
            }
        }
        for (int i = 0; i < oders.size(); i++) {
            if (oders.get(i).getNameFood().equals(nameFood)) {
                oders.get(i).setQuantityFood(quantity);
            }
        }
        for (int i = 0; i < oderAll.size(); i++) {
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
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < oders.size(); i++) {
            System.out.printf(oders.get(i).oderView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");

    }

    public void showHistoryOder() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            if (oderAll.get(i).getNameCustomer().equals(users.get(0).getFullName())) {
                System.out.printf(oderAll.get(i).oderView()).println();
            }
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
    }

    public void payOder() throws IOException {
        List<Oder> oders = oderService.getAllOder();
        double totalMoney = 0;
        for (int i = 0; i < oders.size(); i++) {
            totalMoney += oders.get(i).getTotalMoney();
        }
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < oders.size(); i++) {
            System.out.printf(oders.get(i).oderView()).println();
        }
        System.out.println("            ╠═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        System.out.printf("            ║                                         TỔNG TIỀN CẦN THANH TOÁN                                       ║ %-13s ║                                                ║", totalMoney).println();
        System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
    }

    public void showTotalRevenue() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        double totalRevenue = 0;
        for (int i = 0; i < oderAll.size(); i++) {
            totalRevenue += oderAll.get(i).getTotalMoney();
        }
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            System.out.printf(oderAll.get(i).oderView()).println();
        }
        System.out.println("            ╠═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        System.out.printf("            ║                                                   TỔNG DOANH THU                                       ║ %-13s ║                                                ║", totalRevenue).println();
        System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
    }

    public void showOderAll() throws IOException {
        List<Oder> oderAll = oderService.getAllOderAll();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            System.out.printf(oderAll.get(i).oderView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
    }

    public void findOderById() throws IOException {
        AdminView adminView = new AdminView();
        List<Oder> oderAll = oderService.getAllOderAll();
        noChange();
        int id = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                checkID = true;
                adminView.launcherOder();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                id = 0;
                continue;
            }
        } while (!checkID);

        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║", "ID ODER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╣");
        for (int i = 0; i < oderAll.size(); i++) {
            if (oderAll.get(i).getIdOder() == id) {
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
            if (oderAll.get(i).getIdOder() == idOder) {
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

    public void deleteOderById() throws IOException {
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
        AdminView adminView = new AdminView();
        foodView.showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUser();
        List<Oder> oderAll = oderService.getAllOderAll();
        oderAll.sort(new SortOderById());
        Oder oder = new Oder();
        noChange();
        String nameCustomer = null;
        boolean checkNameCustomer = false;
        do {
            System.out.println("Nhập tên khách hàng mới:");
            nameCustomer = scanner.nextLine();
            if (nameCustomer.equals("exit")) {
                checkNameCustomer = true;
                adminView.launcherOder();
            }
            int checkCustomer = userService.checkNameCustomer(nameCustomer);
            switch (checkCustomer) {
                case 1:
                    int idFood = 0;
                    boolean checkNameFood = false;
                    do {
                        System.out.println("Nhập ID món bạn muốn thêm:");
                        String inputID = scanner.nextLine();
                        if (inputID.equals("exit")) {
                            checkNameFood = true;
                            adminView.launcherOder();
                        }
                        try {
                            idFood = Integer.parseInt(inputID);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("ID không hợp lệ vui lòng nhập lại!");
                            idFood = 0;
                            continue;
                        }
                        String nameFood = null;
                        for (int n = 0; n < foods.size(); n++) {
                            if (foods.get(n).getIdFood() == idFood) {
                                nameFood = foods.get(n).getNameFood();
                            }
                        }
                        int checkFoodInFoods = foodService.checkIdFood(idFood);
                        switch (checkFoodInFoods) {
                            case 1:
                                if (!oderAll.isEmpty()) {
                                    for (int i = 0; i < oderAll.size(); i++) {
                                        if (oderAll.get(i).getNameFood().equals(nameFood) && oderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                                            noChange();
                                            System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                                            int quantity = 0;
                                            boolean checkValid = false;
                                            boolean checkQuantity = false;
                                            do {
                                                System.out.println("Nhập số lượng:");
                                                String inputQuantity = scanner.nextLine();
                                                if (inputQuantity.equals("exit")) {
                                                    checkNameCustomer = true;
                                                    adminView.launcherOder();
                                                }
                                                try {
                                                    quantity = Integer.parseInt(inputQuantity);
                                                } catch (NumberFormatException numberFormatException) {
                                                    System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                                    quantity = 0;
                                                    continue;
                                                }
                                                checkQuantity = ValidateUtils.isQuantity(quantity);
                                                if (!checkQuantity) {
                                                    System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                                }
                                                for (int j = 0; j < foods.size(); j++) {
                                                    if (foods.get(j).getNameFood().equals(nameFood)) {
                                                        if (quantity + oderAll.get(i).getQuantityFood() <= foods.get(j).getQuantity()) {
                                                            checkValid = true;
                                                        } else {
                                                            checkValid = false;
                                                        }
                                                    }
                                                }
                                                if (!checkValid) {
                                                    System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
                                                    checkQuantity = false;
                                                }
                                            } while (!checkQuantity);
                                            int quantityNew = oderAll.get(i).getQuantityFood() + quantity; //thiếu validate <= 1000
                                            oder.setIdOder(oderAll.get(i).getIdOder());
                                            oder.setNameFood(oderAll.get(i).getNameFood());
                                            oder.setQuantityFood(quantityNew);
                                            oder.setPriceFood(oderAll.get(i).getPriceFood());
                                            double total = oderAll.get(i).getPriceFood() * quantityNew;
                                            oder.setTotalMoney(total);
                                            oder.setCreateDateOder(new Date());
                                            oder.setStatus(oderAll.get(i).getStatus());
                                            oderAll.set(i, oder);
                                            for (int k = 0; k < foods.size(); k++) {
                                                if (foods.get(k).getNameFood().equals(nameFood)) {
                                                    foods.get(k).setQuantity(foods.get(k).getQuantity() - quantity);
                                                }
                                            }
                                        }
                                        else if (oderAll.get(i).getNameFood().equals(nameFood) && oderAll.get(i).getStatus().equals(EStatus.PAID)) {
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
                                                checkQuantity = ValidateUtils.isQuantity(quantity);
                                                if (!checkQuantity) {
                                                    System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                                }
                                                for (int j = 0; j < foods.size(); j++) {
                                                    if (foods.get(j).getNameFood().equals(nameFood)) {
                                                        if (quantity <= foods.get(j).getQuantity()) {
                                                            checkValid = true;
                                                            break;
                                                        } else {
                                                            checkValid = false;
                                                        }
                                                    }
                                                }
                                                if (!checkValid) {
                                                    System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
                                                    checkQuantity = false;
                                                }
                                            } while (!checkQuantity);
                                            double price = 0;
                                            for (int j = 0; j < foods.size(); j++) {
                                                if (foods.get(j).getNameFood().equals(nameFood)) {
                                                    price = foods.get(j).getPriceFood();
                                                    foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                                }
                                            }
                                            double totalMoney = quantity * price;
                                            oder.setIdOder(oderAll.get(oderAll.size() - 1).getIdOder() + 1);
                                            oder.setNameCustomer(nameCustomer);
                                            oder.setNameFood(nameFood);
                                            oder.setQuantityFood(quantity);
                                            oder.setPriceFood(price);
                                            oder.setTotalMoney(totalMoney);
                                            oder.setCreateDateOder(new Date());
                                            oder.setStatus(EStatus.UNPAID);
                                            oderAll.add(oder);
                                        }
                                        else if (!oderAll.get(i).getNameFood().equals(nameFood)) {
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
                                                checkQuantity = ValidateUtils.isQuantity(quantity);
                                                if (!checkQuantity) {
                                                    System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                                }
                                                for (int j = 0; j < foods.size(); j++) {
                                                    if (foods.get(j).getNameFood().equals(nameFood)) {
                                                        if (quantity <= foods.get(j).getQuantity()) {
                                                            checkValid = true;
                                                            break;
                                                        } else {
                                                            checkValid = false;
                                                        }
                                                    }
                                                }
                                                if (!checkValid) {
                                                    System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
                                                    checkQuantity = false;
                                                }
                                            } while (!checkQuantity);
                                            double price = 0;
                                            for (int j = 0; j < foods.size(); j++) {
                                                if (foods.get(j).getNameFood().equals(nameFood)) {
                                                    price = foods.get(j).getPriceFood();
                                                    foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                                }
                                            }
                                            double totalMoney = quantity * price;
                                            oder.setIdOder(oderAll.get(oderAll.size() - 1).getIdOder() + 1);
                                            oder.setNameCustomer(nameCustomer);
                                            oder.setNameFood(nameFood);
                                            oder.setQuantityFood(quantity);
                                            oder.setPriceFood(price);
                                            oder.setTotalMoney(totalMoney);
                                            oder.setCreateDateOder(new Date());
                                            oder.setStatus(EStatus.UNPAID);
                                            oderAll.add(oder);
                                        }
                                    }
                                } else if (oderAll.isEmpty()) {
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
                                        checkQuantity = ValidateUtils.isQuantity(quantity);
                                        if (!checkQuantity) {
                                            System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                        }
                                        for (int j = 0; j < foods.size(); j++) {
                                            if (foods.get(j).getNameFood().equals(nameFood)) {
                                                if (quantity <= foods.get(j).getQuantity()) {
                                                    checkValid = true;
                                                    break;
                                                } else {
                                                    checkValid = false;
                                                }
                                            }
                                        }
                                        if (!checkValid) {
                                            System.out.println("Số lượng vượt quá số lượng hiện tại đang có, vui lòng nhập lại!");
                                            checkQuantity = false;
                                        }
                                    } while (!checkQuantity);
                                    double price = 0;
                                    for (int j = 0; j < foods.size(); j++) {
                                        if (foods.get(j).getNameFood().equals(nameFood)) {
                                            price = foods.get(j).getPriceFood();
                                            foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                        }
                                    }
                                    double totalMoney = quantity * price;
                                    oder.setIdOder(1);
                                    oder.setNameCustomer(nameCustomer);
                                    oder.setNameFood(nameFood);
                                    oder.setQuantityFood(quantity);
                                    oder.setPriceFood(price);
                                    oder.setTotalMoney(totalMoney);
                                    oder.setCreateDateOder(new Date());
                                    oder.setStatus(EStatus.UNPAID);
                                    oderAll.add(oder);
                                }
                                checkNameFood = true;
                                break;
                            case -1:
                                System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                                checkNameFood = false;
                                break;
                        }
                    } while (!checkNameFood);
                    checkNameCustomer = true;
                    break;
                case -1:
                    System.out.println("Khách hàng chưa đăng ký, mời bạn nhập lại");
                    checkNameCustomer = false;
                    break;
            }
        } while (!checkNameCustomer);

        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");

        showOderAll();

    }

    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: exit ⦿ ");
    }

    public static void main(String[] args) throws IOException {
        OderView oderView = new OderView();
        oderView.addOder();
    }
}
