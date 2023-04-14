package view;

import model.EStatus;
import model.Food;
import model.Order;
import model.User;
import service.FileService;
import service.FoodService;
import service.OderService;
import service.UserService;
import utils.DateFormat;
import utils.SortOderById;
import utils.ValidateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private static final String FILE_PATH_FOOD = "./src/main/data/food.csv";
    private final String FILE_PATH_ORDER = "./src/main/data/order.csv";
    private final String FILE_PATH_ODERALL = "./src/main/data/orderAll.csv";
    private FoodService foodService;
    private UserService userService;
    private OderService oderService;
    private FileService fileService;
    private Scanner scanner;

    public OrderView() {
        foodService = new FoodService();
        userService = new UserService();
        oderService = new OderService();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void addFoodInOderByIdCustomer() throws IOException {
//        int idOder, String nameCustomer, String nameFood, int quantityFood, double priceFood, double totalMoney, Date createDateOder
        CustomerView customerView = new CustomerView();
        FoodView foodView = new FoodView();
        foodView.showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUserUse();
        List<Order> orderAll = oderService.getAllOderAll();
        List<Order> orders = oderService.getAllOder();
        orderAll.sort(new SortOderById());
        Order order = new Order();
        Order orderNew = new Order();
        noChange();
        int idFood = 0;
        String nameFood = null;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn oder:");
            String inputID = scanner.nextLine();
            if (inputID.equals("exit")) {
                checkId = true;
                customerView.launcher();
            }
            try {
                idFood = Integer.parseInt(inputID);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                idFood = 0;
                continue;
            }
            int checkIdFood = foodService.checkIdFood(idFood);
            switch (checkIdFood) {
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == idFood) {
                            nameFood = foods.get(i).getNameFood();
                        }
                    }
                    if (!orders.isEmpty()) {
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getNameFood().equals(nameFood) && users.get(0).getId() == orders.get(i).getIdCustomer()) {
                                noChange();
                                System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                                int quantity = 0;
                                boolean checkValid = false;
                                boolean checkQuantity = false;
                                do {
                                    System.out.println("Nhập số lượng:");
                                    String inputQuantity = scanner.nextLine();
                                    if (inputQuantity.equals("exit")) {
                                        checkId = true;
                                        customerView.launcher();
                                    }
                                    try {
                                        quantity = Integer.parseInt(inputQuantity);
                                    } catch (NumberFormatException numberFormatException) {
                                        System.out.println("Nhập lỗi, vui lòng nhập lại! Số lượng từ 0-1000");
                                        quantity = 0;
                                        continue;
                                    }
                                    checkValid = ValidateUtils.isQuantity(quantity);
                                    if (!checkValid) {
                                        System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                        checkQuantity = false;
                                    } else {
                                        for (int j = 0; j < foods.size(); j++) {
                                            if (foods.get(j).getNameFood().equals(nameFood)) {
                                                if (quantity <= foods.get(j).getQuantity()) {
                                                    checkQuantity = true;
                                                } else {
                                                    System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                                    checkQuantity = false;
                                                }
                                            }
                                        }
                                    }
                                } while (!checkQuantity);
                                int quantityNew = orders.get(i).getQuantityFood() + quantity;
                                double total = orders.get(i).getPriceFood() * quantityNew;
                                order.setIdOder(orders.get(i).getIdOder());
                                order.setIdCustomer(orders.get(i).getIdCustomer());
                                order.setNameCustomer(orders.get(i).getNameCustomer());
                                order.setNameFood(nameFood);
                                order.setQuantityFood(quantityNew);
                                order.setPriceFood(orders.get(i).getPriceFood());
                                order.setTotalMoney(total);
                                order.setCreateDateOder(new Date());
                                order.setStatus(orders.get(i).getStatus());
                                orders.set(i, order);
                                for (int j = 0; j < foods.size(); j++) {
                                    if (foods.get(j).getNameFood().equals(nameFood)) {
                                        foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                    }
                                }
                                fileService.writeData(FILE_PATH_ORDER, orders);
                                fileService.writeData(FILE_PATH_FOOD, foods);
                                for (int j = 0; j < orderAll.size(); j++) {
                                    if (orderAll.get(i).getIdCustomer() == users.get(0).getId() && orderAll.get(i).getNameFood().equals(nameFood) && orderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                                        orderAll.get(i).setQuantityFood(quantityNew);
                                        fileService.writeData(FILE_PATH_ODERALL, orderAll);
                                    }
                                }
                                break;
                            } else if (orders.get(i).getNameFood().equals(nameFood) && users.get(0).getId() != orders.get(i).getIdCustomer()) {
                                noChange();
                                int quantity = 0;
                                boolean checkValid = false;
                                boolean checkQuantity = false;
                                do {
                                    System.out.println("Nhập số lượng:");
                                    String inputQuantity = scanner.nextLine();
                                    if (inputQuantity.equals("exit")) {
                                        checkId = true;
                                        customerView.launcher();
                                    }
                                    try {
                                        quantity = Integer.parseInt(inputQuantity);
                                    } catch (NumberFormatException numberFormatException) {
                                        System.out.println("Nhập lỗi, vui lòng nhập lại! Số lượng từ 0-1000");
                                        quantity = 0;
                                        continue;
                                    }
                                    checkValid = ValidateUtils.isQuantity(quantity);
                                    if (!checkValid) {
                                        System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                        checkQuantity = false;
                                    } else {
                                        for (int j = 0; j < foods.size(); j++) {
                                            if (foods.get(j).getIdFood() == idFood) {
                                                if (quantity <= foods.get(j).getQuantity()) {
                                                    checkQuantity = true;
                                                } else {
                                                    System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                                    checkQuantity = false;
                                                }
                                            }
                                        }
                                    }
                                } while (!checkQuantity);
                                double price = 0;
                                for (int j = 0; j < foods.size(); j++) {
                                    if (foods.get(j).getIdFood() == idFood) {
                                        price = foods.get(j).getPriceFood();
                                        foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                    }
                                }
                                double totalMoney = quantity * price;
                                order.setIdOder(orders.get(orders.size() - 1).getIdOder() + 1);
                                order.setIdCustomer(users.get(0).getId());
                                order.setNameCustomer(users.get(0).getFullName());
                                order.setNameFood(nameFood);
                                order.setQuantityFood(quantity);
                                order.setPriceFood(price);
                                order.setTotalMoney(totalMoney);
                                order.setCreateDateOder(new Date());
                                order.setStatus(EStatus.UNPAID);
                                orders.add(order);
                                fileService.writeData(FILE_PATH_ORDER, orders);
                                fileService.writeData(FILE_PATH_FOOD, foods);
                                break;
                            }
                        }
                    } else if (orders.isEmpty()) {
                        noChange();
                        int quantity = 0;
                        boolean checkValid = false;
                        boolean checkQuantity = false;
                        do {
                            System.out.println("Nhập số lượng:");
                            String inputQuantity = scanner.nextLine();
                            if (inputQuantity.equals("exit")) {
                                checkId = true;
                                customerView.launcher();
                            }
                            try {
                                quantity = Integer.parseInt(inputQuantity);
                            } catch (NumberFormatException numberFormatException) {
                                System.out.println("Nhập lỗi, vui lòng nhập lại! Số lượng từ 0-1000");
                                quantity = 0;
                                continue;
                            }
                            checkValid = ValidateUtils.isQuantity(quantity);
                            if (!checkValid) {
                                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                                checkQuantity = false;
                            } else {
                                for (int j = 0; j < foods.size(); j++) {
                                    if (foods.get(j).getIdFood() == idFood) {
                                        if (quantity <= foods.get(j).getQuantity()) {
                                            checkQuantity = true;
                                        } else {
                                            System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                            checkQuantity = false;
                                        }
                                    }
                                }
                            }
                        } while (!checkQuantity);
                        double price = 0;
                        for (int j = 0; j < foods.size(); j++) {
                            if (foods.get(j).getIdFood() == idFood) {
                                price = foods.get(j).getPriceFood();
                                foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                            }
                        }
                        double totalMoney = quantity * price;
                        order.setIdOder(1);
                        order.setIdCustomer(users.get(0).getId());
                        order.setNameCustomer(users.get(0).getFullName());
                        order.setNameFood(nameFood);
                        order.setQuantityFood(quantity);
                        order.setPriceFood(price);
                        order.setTotalMoney(totalMoney);
                        order.setCreateDateOder(new Date());
                        order.setStatus(EStatus.UNPAID);
                        orders.add(order);
                        fileService.writeData(FILE_PATH_ORDER, orders);
                        fileService.writeData(FILE_PATH_FOOD, foods);
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);
        orderNew.setIdOder(orderAll.get(orderAll.size() - 1).getIdOder() + 1);
        orderNew.setIdCustomer(order.getIdCustomer());
        orderNew.setNameCustomer(order.getNameCustomer());
        orderNew.setNameFood(order.getNameFood());
        orderNew.setQuantityFood(order.getQuantityFood());
        orderNew.setPriceFood(order.getPriceFood());
        orderNew.setTotalMoney(order.getTotalMoney());
        orderNew.setCreateDateOder(order.getCreateDateOder());
        orderNew.setStatus(order.getStatus());
        orderAll.add(orderNew);
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        showOderNow();
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");
    }

    public void editQuantityFoodInOderByIdOder() throws IOException {
        showOderNow();
        CustomerView customerView = new CustomerView();
        List<Food> foods = foodService.getAllFood();
        List<Order> orderAll = oderService.getAllOderAll();
        List<Order> orders = oderService.getAllOder();
        List<User> users = userService.getAllUserUse();
        int idOder = 0;
        String nameFood = null;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID oder bạn muốn chỉnh sửa:");
            String inputID = scanner.nextLine();
            if (inputID.equals("exit")) {
                checkId = true;
                customerView.launcher();
            }
            try {
                idOder = Integer.parseInt(inputID);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                idOder = 0;
                continue;
            }
            int checkIdOder = foodService.checkIdFood(idOder);
            switch (checkIdOder) {
                case 1:
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getIdOder() == idOder) {
                            nameFood = orders.get(i).getNameFood();
                        }
                    }
                    noChange();
                    int quantity = 0;
                    boolean checkValid = false;
                    boolean checkQuantity = false;
                    do {
                        System.out.println("Nhập số lượng bạn muốn sửa:");
                        String inputQuantity = scanner.nextLine();
                        if (inputQuantity.equals("exit")) {
                            checkId = true;
                            customerView.launcher();
                        }
                        try {
                            quantity = Integer.parseInt(inputQuantity);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Nhập lỗi, vui lòng nhập lại! Số lượng từ 0-1000");
                            quantity = 0;
                            continue;
                        }
                        checkValid = ValidateUtils.isQuantity(quantity);
                        if (!checkValid) {
                            System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                            checkQuantity = false;
                        } else {
                            for (int i = 0; i < orders.size(); i++) {
                                for (int j = 0; j < foods.size(); j++) {
                                    if (foods.get(j).getNameFood().equals(nameFood) && orders.get(i).getIdOder() == idOder) {
                                        if (orders.get(i).getQuantityFood() <= quantity && quantity <= foods.get(j).getQuantity()) {
                                            orders.get(i).setQuantityFood(quantity);
                                            foods.get(i).setQuantity(foods.get(i).getQuantity() + orders.get(i).getQuantityFood() - quantity);
                                            checkQuantity = true;
                                        } else if (orders.get(i).getQuantityFood() > quantity) {
                                            orders.get(i).setQuantityFood(quantity);
                                            foods.get(i).setQuantity(foods.get(i).getQuantity() + orders.get(i).getQuantityFood() - quantity);
                                            checkQuantity = true;
                                        } else if (quantity > foods.get(j).getQuantity() + orders.get(i).getQuantityFood()) {
                                            System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                            checkQuantity = false;
                                        }
                                    }
                                }
                            }
                        }
                    } while (!checkQuantity);

                    for (int i = 0; i < orderAll.size(); i++) {
                        if (orderAll.get(i).getNameFood().equals(nameFood) && users.get(0).getId() == orderAll.get(i).getIdCustomer()) {
                            orderAll.get(i).setQuantityFood(quantity);
                        }
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);

        fileService.writeData(FILE_PATH_ORDER, orders);
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        showOderNow();
        System.out.println("✔ Bạn đã cập nhật số lượng thành công ✔\n");
    }

    public void deleteFoodOutOderByIdOder() throws IOException {
        showOderNow();
        CustomerView customerView = new CustomerView();
        List<Food> foods = foodService.getAllFood();
        List<Order> orderAll = oderService.getAllOderAll();
        List<Order> orders = oderService.getAllOder();
        List<User> users = userService.getAllUserUse();
        int idOder = 0;
        int idOderAll = 0;
        String nameFood = null;
        boolean checkId = false;
        do {
            boolean checkAction = false;
            System.out.println("Nhập ID oder bạn muốn xóa:");
            String inputID = scanner.nextLine();
            if (inputID.equals("exit")) {
                checkId = true;
                customerView.launcher();
            }
            try {
                idOder = Integer.parseInt(inputID);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                idOder = 0;
                continue;
            }
            int checkIdOder = foodService.checkIdFood(idOder);
            switch (checkIdOder) {
                case 1:
                    oderService.deleteFoodOutOderById(idOder);
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getIdOder() == idOder) {
                            nameFood = orders.get(i).getNameFood();
                        }
                    }
                    for (int i = 0; i < orderAll.size(); i++) {
                        if (orderAll.get(i).getNameFood().equals(nameFood) && orderAll.get(i).getIdCustomer() == users.get(0).getId()) {
                            idOderAll = orderAll.get(i).getIdOder();
                        }
                    }
                    oderService.deleteFoodOutOderAllById(idOderAll);
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);
        showOderNow();
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }

    public void showOderNow() throws IOException {
        List<Order> orders = oderService.getAllOder();
        System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < orders.size(); i++) {
            System.out.printf(orders.get(i).oderView()).println();
        }
        System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");

    }

    public void showHistoryOder() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getIdCustomer() == users.get(0).getId()) {
                System.out.printf(orderAll.get(i).oderView()).println();
            }
        }
        System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
    }

    public void payOder() throws IOException {
        FileService fileService = new FileService();
        List<Order> orders = oderService.getAllOder();
        List<Order> orderAll = oderService.getAllOderAll();
        List<User> users = userService.getAllUserUse();
        double totalMoney = 0;
//        for (int i = 0; i < orders.size(); i++) {
//            if(orders.get(i).getIdCustomer() == users.get(i).getId()) {
//                totalMoney += orders.get(i).getTotalMoney();
//            }
//        }
        System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(i).getId()) {
                totalMoney += orders.get(i).getTotalMoney();
                orders.get(i).setStatus(EStatus.PAID);
                System.out.printf(orders.get(i).oderView()).println();
            }
        }
        System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
        System.out.printf("            ║                                             TỔNG TIỀN CẦN THANH TOÁN                                           ║ %-13s ║                                                ║", totalMoney).println();
        System.out.println("            ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getIdCustomer() == users.get(i).getId()) {
                orderAll.get(i).setStatus(EStatus.PAID);
            }
        }
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(i).getId()) {
                orders.remove(i);
            }
        }
        fileService.writeData(FILE_PATH_ORDER,orders);
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        System.out.println("✔ Bạn đã thanh toán thành công ✔\n");
    }

    public void showRevenueByDay() throws IOException {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> orderAll = oderService.getAllOderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Không có đơn hàng, không có doanh thu!");
        } else {
            String date = null;
            boolean checkDate = false;
            do {
                System.out.println("Nhập ngày tháng năm bạn muốn xem doanh thu: dd-MM-yyyy");
                date = scanner.nextLine();
                if (date.equals("exit")) {
                    checkDate = true;
                    adminView.launcherRevenue();
                }
                checkDate = ValidateUtils.isDay(date);
                if (!checkDate) {
                    System.out.println("Ngày tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: dd-MM-yyyy");
                }
            } while (!checkDate);
            double totalRevenueByDay = 0;
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(date) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    totalRevenueByDay += orderAll.get(i).getTotalMoney();
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
            System.out.printf("            ║                                                  TỔNG DOANH THU THEO NGÀY                                              ║ %-13s ║                                                ║", totalRevenueByDay).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
        }
    }

    public void showRevenueByMonth() throws IOException {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> orderAll = oderService.getAllOderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Không có đơn hàng, không có doanh thu!");
        } else {
            String month = null;
            boolean checkMonth = false;
            do {
                System.out.println("Nhập tháng năm bạn muốn xem doanh thu: MM-yyyy");
                month = scanner.nextLine();
                if (month.equals("exit")) {
                    checkMonth = true;
                    adminView.launcherRevenue();
                }
                checkMonth = ValidateUtils.isMonth(month);
                if (!checkMonth) {
                    System.out.println("Tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: MM-yyyy");
                }
            } while (!checkMonth);
            double totalRevenueByMonth = 0;
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(month) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    totalRevenueByMonth += orderAll.get(i).getTotalMoney();
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
            System.out.printf("            ║                                                  TỔNG DOANH THU THEO NGÀY                                              ║ %-13s ║                                                ║", totalRevenueByMonth).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
        }
    }

    public void showTotalRevenue() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
        double totalRevenue = 0;
        for (int i = 0; i < orderAll.size(); i++) {
            totalRevenue += orderAll.get(i).getTotalMoney();
        }
        System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                System.out.printf(orderAll.get(i).oderView()).println();
            }
        }
        System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
        System.out.printf("            ║                                                            TỔNG DOANH THU                                              ║ %-13s ║                                                ║", totalRevenue).println();
        System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
    }

    public void showOderAll() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                System.out.printf(orderAll.get(i).oderView()).println();
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void showOderUnPaid() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void showOderPaid() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void findOderById() throws IOException {
        AdminView adminView = new AdminView();
        List<Order> orderAll = oderService.getAllOderAll();
        noChange();
        int idOrder = 0;
        boolean checkIdOrder = false;
        do {
            System.out.println("Nhập ID order, thức ăn bạn muốn tìm");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                checkIdOrder = true;
                adminView.launcherOder();
            }
            try {
                idOrder = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                idOrder = 0;
                continue;
            }
            int select = oderService.checkIdOderAll(idOrder);
            switch (select) {
                case 1:
                    System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
                    System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
                    System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
                    for (int i = 0; i < orderAll.size(); i++) {
                        if (orderAll.get(i).getIdOder() == idOrder) {
                            System.out.printf(orderAll.get(i).oderView()).println();
                        }
                    }
                    System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
                    checkIdOrder = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, vui lòng nhập lại!");
                    checkIdOrder = false;
                    break;
            }
        } while (!checkIdOrder);

    }

    public void editOder() throws IOException {
        List<Order> orderAll = oderService.getAllOderAll();
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

        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getIdOder() == idOder) {
                orderAll.get(i).setNameCustomer(nameCustomer);
                orderAll.get(i).setNameFood(nameFood);
                orderAll.get(i).setQuantityFood(quantity);
                orderAll.get(i).setPriceFood(price);
                orderAll.get(i).setTotalMoney(totalMoney);
                orderAll.get(i).setCreateDateOder(new Date());
            }
        }
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã sửa đơn hàng thành công ✔\n");
    }

    public void deleteOderById() throws IOException {
        AdminView adminView = new AdminView();
        showOderAll();
        noChange();
        List<Order> orderAll = oderService.getAllOderAll();
        int idOder = 0;
        boolean checkID = false;
        do {
            System.out.println("Nhập ID oder muốn xóa:");
            String inputID = scanner.nextLine();
            if (inputID.equals("exit")) {
                checkID = true;
                adminView.launcherOder();
            }
            try {
                idOder = Integer.parseInt(inputID);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                idOder = 0;
                continue;
            }
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
        int index = 0;
        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getIdOder() == idOder) {
                index = i;
            }
        }
        if (orderAll.get(index).getStatus().equals(EStatus.UNPAID)) {
            oderService.deleteFoodOutOderAllById(idOder);
            showOderAll();
        } else if (orderAll.get(index).getStatus().equals(EStatus.PAID)) {
            System.out.println("Đơn hàng đã thanh toán, không thể xóa! Quay lại menu.");
            adminView.launcherOder();
        }

//        List<Oder> oderAll = oderService.getAllOderAll();
//        fileService.writeData(FILE_PATH_ODERALL, oderAll);
        System.out.println("✔ Bạn đã xóa oder thành công ✔\n");
    }

    public void addOderByAdmin() throws IOException {
        FoodView foodView = new FoodView();
        AdminView adminView = new AdminView();
        foodView.showFoodList();
        List<Food> foods = foodService.getAllFood();
        List<User> users = userService.getAllUser();
        List<Order> orderAll = oderService.getAllOderAll();
        orderAll.sort(new SortOderById());
        Order order = new Order();
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
                                if (!orderAll.isEmpty()) {
                                    for (int i = 0; i < orderAll.size(); i++) {
                                        if (orderAll.get(i).getNameFood().equals(nameFood) && orderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
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
                                                        if (quantity + orderAll.get(i).getQuantityFood() <= foods.get(j).getQuantity()) {
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
                                            int quantityNew = orderAll.get(i).getQuantityFood() + quantity; //thiếu validate <= 1000
                                            order.setIdOder(orderAll.get(i).getIdOder());
                                            order.setNameFood(orderAll.get(i).getNameFood());
                                            order.setQuantityFood(quantityNew);
                                            order.setPriceFood(orderAll.get(i).getPriceFood());
                                            double total = orderAll.get(i).getPriceFood() * quantityNew;
                                            order.setTotalMoney(total);
                                            order.setCreateDateOder(new Date());
                                            order.setStatus(orderAll.get(i).getStatus());
                                            orderAll.set(i, order);
                                            for (int k = 0; k < foods.size(); k++) {
                                                if (foods.get(k).getNameFood().equals(nameFood)) {
                                                    foods.get(k).setQuantity(foods.get(k).getQuantity() - quantity);
                                                }
                                            }
                                            break;
                                        } else if (orderAll.get(i).getNameFood().equals(nameFood) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                                            noChange();
                                            int quantity = 0;
                                            boolean checkQuantity = false;
                                            boolean checkValid = false;
                                            do {
                                                System.out.println("Nhập số lượng:");
                                                String input = scanner.nextLine();
                                                if (input.equals("exit")) {
                                                    checkNameCustomer = true;
                                                    adminView.launcherOder();
                                                }
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
                                            order.setIdOder(orderAll.get(orderAll.size() - 1).getIdOder() + 1);
                                            order.setNameCustomer(nameCustomer);
                                            order.setNameFood(nameFood);
                                            order.setQuantityFood(quantity);
                                            order.setPriceFood(price);
                                            order.setTotalMoney(totalMoney);
                                            order.setCreateDateOder(new Date());
                                            order.setStatus(EStatus.UNPAID);
                                            orderAll.add(order);
                                            break;
                                        } else if (!orderAll.get(i).getNameFood().equals(nameFood)) {
                                            noChange();
                                            int quantity = 0;
                                            boolean checkQuantity = false;
                                            boolean checkValid = false;
                                            do {
                                                System.out.println("Nhập số lượng:");
                                                String input = scanner.nextLine();
                                                if (input.equals("exit")) {
                                                    checkNameCustomer = true;
                                                    adminView.launcherOder();
                                                }
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
                                            order.setIdOder(orderAll.get(orderAll.size() - 1).getIdOder() + 1);
                                            order.setNameCustomer(nameCustomer);
                                            order.setNameFood(nameFood);
                                            order.setQuantityFood(quantity);
                                            order.setPriceFood(price);
                                            order.setTotalMoney(totalMoney);
                                            order.setCreateDateOder(new Date());
                                            order.setStatus(EStatus.UNPAID);
                                            orderAll.add(order);
                                            break;
                                        }
                                    }
                                } else if (orderAll.isEmpty()) {
                                    noChange();
                                    int quantity = 0;
                                    boolean checkQuantity = false;
                                    boolean checkValid = false;
                                    do {
                                        System.out.println("Nhập số lượng:");
                                        String input = scanner.nextLine();
                                        if (input.equals("exit")) {
                                            checkNameCustomer = true;
                                            adminView.launcherOder();
                                        }
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
                                    order.setIdOder(1);
                                    order.setNameCustomer(nameCustomer);
                                    order.setNameFood(nameFood);
                                    order.setQuantityFood(quantity);
                                    order.setPriceFood(price);
                                    order.setTotalMoney(totalMoney);
                                    order.setCreateDateOder(new Date());
                                    order.setStatus(EStatus.UNPAID);
                                    orderAll.add(order);
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

        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        fileService.writeData(FILE_PATH_FOOD, foods);
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");

        showOderAll();

    }

    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: exit ⦿ ");
    }

    public static void main(String[] args) throws IOException {
        OrderView orderView = new OrderView();
        orderView.findOderById();
    }


}
