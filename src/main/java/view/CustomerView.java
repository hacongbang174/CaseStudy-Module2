package view;

import service.FileService;
import service.OderService;
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
        System.out.println("                               ║                   [2] Tìm kiếm đồ uống, thức ăn theo chuỗi tìm kiếm               ║");
        System.out.println("                               ║                   [3] Thêm món vào oder theo id đồ uống, thức ăn                  ║");
        System.out.println("                               ║                   [4] Chỉnh sửa số lượng món đã oder theo id đồ uống, thức ăn     ║");
        System.out.println("                               ║                   [5] Xóa món khỏi oder theo id đồ uống, thức ăn                  ║");
        System.out.println("                               ║                   [6] Xem lịch sử oder món                                        ║");
        System.out.println("                               ║                   [7] Thanh toán                                                  ║");
        System.out.println("                               ║                   [8] Quản lý tài khoản                                           ║");
        System.out.println("                               ║                   [9] Đăng xuất                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcher() throws IOException {
        boolean checkAction = false;
        do {
            menuCustomerView();
            System.out.println("Chọn chức năng:");
            int select = Integer.parseInt(scanner.nextLine());
            switch (select) {
                case 1:
                    foodView.showFoodList();
                    break;
                case 2:
                    foodView.searchFoodByString();
                    break;
                case 3:
                    oderView.addFoodInOder();
                    break;
                case 4:
                    oderView.editQuantityFoodInOder();
                    break;
                case 5:
                    oderView.deleteFoodOutOder();
                    break;
                case 6:
                    oderView.showHistoryOder();
                    break;
                case 7:
                    oderView.payOder();
                    break;
                case 8:
                    launcherAccount();
                    break;
                case 9:
                    fileService.clearData(FILE_PATH_USERUSE);
                    fileService.clearData(FILE_PATH_ODER);
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    CustomerView customerView = new CustomerView();
                    customerView.launcher();
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
}
