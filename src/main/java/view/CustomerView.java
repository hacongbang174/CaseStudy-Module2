package view;

import service.FileService;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class CustomerView {
    private static final String FILE_PATH_ODER = "./src/main/data/order.csv";
    private final String FILE_PATH_USERUSE = "./src/main/data/userUse.csv";
    private UserService userService;
    private OrderView orderView;
    private FoodView foodView;
    private Scanner scanner;
    private FileService fileService;
    private LoginView loginView;

    public CustomerView() {
        orderView = new OrderView();
        foodView = new FoodView();
        fileService = new FileService();
        scanner = new Scanner(System.in);
        loginView = new LoginView();
    }

    public void menuCustomerView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Giao diện Customer                             ║");
        System.out.println("                               ║                   [1] Xem danh sách đồ uống, thức ăn                              ║");
        System.out.println("                               ║                   [2] Xem danh sách đồ uống, thức ăn theo danh mục                ║");
        System.out.println("                               ║                   [3] Xem danh sách đồ uống, thức ăn theo giá tăng dần            ║");
        System.out.println("                               ║                   [4] Xem danh sách đồ uống, thức ăn theo giá giảm dần            ║");
        System.out.println("                               ║                   [5] Tìm kiếm đồ uống, thức ăn theo keyword                      ║");
        System.out.println("                               ║                   [6] Thêm món vào order theo id đồ uống, thức ăn                 ║");
        System.out.println("                               ║                   [7] Chỉnh sửa số lượng món đã order theo id order               ║");
        System.out.println("                               ║                   [8] Xóa món khỏi order theo id order                            ║");
        System.out.println("                               ║                   [9] Xem lịch sử order món                                       ║");
        System.out.println("                               ║                   [10] Thanh toán                                                 ║");
        System.out.println("                               ║                   [11] Quản lý tài khoản                                          ║");
        System.out.println("                               ║                   [12] Đăng xuất                                                  ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcher() throws IOException {
        int select = 0;
        boolean checkAction = false;
        do {
            menuCustomerView();
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
                    foodView.showFoodList();
                    break;
                case 2:
                    foodView.showFoodListByType();
                    break;
                case 3:
                    foodView.sortByPriceIncrease();
                    break;
                case 4:
                    foodView.sortByPriceDecrease();
                    break;
                case 5:
                    foodView.searchFoodByKeyword();
                    break;
                case 6:
                    orderView.addFoodInOderByIdCustomer();
                    break;
                case 7:
                    orderView.editQuantityFoodInOderByIdOder();
                    break;
                case 8:
                    orderView.deleteFoodOutOderByIdOder();
                    break;
                case 9:
                    orderView.showHistoryOder();
                    break;
                case 10:
                    orderView.payOder();
                    break;
                case 11:
                    launcherAccount();
                    break;
                case 12:
                    fileService.clearData(FILE_PATH_USERUSE);
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (checkAction);
        if(checkAction) {
            launcher();
        }else {
            launcher();
        }
    }

    private void menuAccountManager() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Giao diện quản lý tài khoản                         ║");
        System.out.println("                               ║                         [1] Hiển thị thông tin tài khoản                          ║");
        System.out.println("                               ║                         [2] Thay đổi mật khẩu đăng nhập                           ║");
        System.out.println("                               ║                         [3] Thay đổi số điện thoại                                ║");
        System.out.println("                               ║                         [4] Thay đổi email                                        ║");
        System.out.println("                               ║                         [5] Thay đổi địa chỉ                                      ║");
        System.out.println("                               ║                         [6] Quay lại                                              ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherAccount() throws IOException {
        CustomerView customerView = new CustomerView();
        boolean checkAction = false;
        int select;
        do {
            menuAccountManager();
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
                    loginView.showInfoAccount();
                    break;
                case 2:
                    loginView.editPassWord();
                    break;
                case 3:
                    loginView.editPhoneNumber();
                    break;
                case 4:
                    loginView.editEmail();
                    break;
                case 5:
                    loginView.editAddress();
                    break;
                case 6:
                    customerView.launcher();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (checkAction);
        if(checkAction) {
            launcherAccount();
        }else {
            launcher();
        }
    }
    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để quay về giao diện trước đó, nhập \"N\" để quay về giao diện Customer!");
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
}
