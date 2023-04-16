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
        System.out.println("                               ║                   [10] Xem lịch sử mua hàng                                       ║");
        System.out.println("                               ║                   [11] Thanh toán                                                 ║");
        System.out.println("                               ║                   [12] Quản lý tài khoản                                          ║");
        System.out.println("                               ║                   [13] Đăng xuất                                                  ║");
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
                    foodView.showFoodListStepFood();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    foodView.showFoodListByType();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    foodView.sortByPriceIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    foodView.sortByPriceDecrease();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    foodView.searchFoodByKeyword();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    orderView.addFoodInOderByIdCustomer();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    orderView.editQuantityFoodInOderByIdOder();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    orderView.deleteFoodOutOderByIdOder();
                    checkAction = checkActionContinue();
                    break;
                case 9:
                    orderView.showHistoryOder();
                    checkAction = checkActionContinue();
                    break;
                case 10:
                    orderView.showHistoryOderPaid();
                    checkAction = checkActionContinue();
                    break;
                case 11:
                    orderView.payOder();
                    checkAction = checkActionContinue();
                    break;
                case 12:
                    launcherAccount();
                    checkAction = checkActionContinue();
                    break;
                case 13:
                    fileService.clearData(FILE_PATH_USERUSE);
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            launcher();
        }
    }

    private void menuAccountManager() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Giao diện quản lý tài khoản                         ║");
        System.out.println("                               ║                         [1] Hiển thị thông tin tài khoản                          ║");
        System.out.println("                               ║                         [2] Thay đổi họ và tên của bạn                            ║");
        System.out.println("                               ║                         [3] Thay đổi mật khẩu đăng nhập                           ║");
        System.out.println("                               ║                         [4] Thay đổi số điện thoại                                ║");
        System.out.println("                               ║                         [5] Thay đổi email                                        ║");
        System.out.println("                               ║                         [6] Thay đổi địa chỉ                                      ║");
        System.out.println("                               ║                         [7] Quay lại                                              ║");
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
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    loginView.editFullName();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    loginView.editPassWord();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    loginView.editPhoneNumber();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    loginView.editEmail();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    loginView.editAddress();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    customerView.launcher();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
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
                    return false;
                case "N":
                    return true;
                default:
                    checkActionContinue = false;
            }
        } while (!checkActionContinue);
        return true;
    }
}
