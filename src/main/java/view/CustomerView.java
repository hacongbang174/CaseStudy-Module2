package view;

import service.FileService;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class CustomerView {
    private static final String FILE_PATH_ODER = "./src/main/data/oder.csv";
    private final String FILE_PATH_USERUSE = "./src/main/data/userUse.csv";
    private UserService userService;
    private OderView oderView;
    private FoodView foodView;
    private Scanner scanner;
    private FileService fileService;
    private LoginView loginView;

    public CustomerView() {
        oderView = new OderView();
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
        System.out.println("                               ║                   [3] Tìm kiếm đồ uống, thức ăn theo keyword                      ║");
        System.out.println("                               ║                   [4] Thêm món vào oder theo id đồ uống, thức ăn                  ║");
        System.out.println("                               ║                   [5] Chỉnh sửa số lượng món đã oder theo id oder                 ║");
        System.out.println("                               ║                   [6] Xóa món khỏi oder theo id oder                              ║");
        System.out.println("                               ║                   [7] Xem lịch sử oder món                                        ║");
        System.out.println("                               ║                   [8] Thanh toán                                                  ║");
        System.out.println("                               ║                   [9] Quản lý tài khoản                                           ║");
        System.out.println("                               ║                   [10] Đăng xuất                                                  ║");
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
                    foodView.searchFoodByKeyword();
                    break;
                case 4:
                    oderView.addFoodInOderByIdCustomer();
                    break;
                case 5:
                    oderView.editQuantityFoodInOderByIdOder();
                    break;
                case 6:
                    oderView.deleteFoodOutOderByIdOder();
                    break;
                case 7:
                    oderView.showOder();
                    break;
                case 8:
                    oderView.payOder();
                    break;
                case 9:
                    launcherAccount();
                    break;
                case 10:
                    fileService.clearData(FILE_PATH_USERUSE);
                    fileService.clearData(FILE_PATH_ODER);
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (checkAction);
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
        do {
            menuAccountManager();
            System.out.println("Chọn chức năng:");
            int select = Integer.parseInt(scanner.nextLine());
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
                    customerView.launcherAccount();
                    break;
            }
            checkAction = checkActionContinue();
        }while (checkAction);
    }
    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Continue? Y/N! Y - menu hiện tại, N - menu chính!");
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
