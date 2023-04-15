package view;

import service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class AdminView {
    private static final String FILE_ODER = "./src/main/data/order.csv";
    private FoodView foodView;
    private OrderView orderView;
    private FileService fileService;
    private Scanner scanner;
    public AdminView() {
        foodView = new FoodView();
        orderView = new OrderView();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }
    public  void menuAdminView(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Giao diện Admin                                ║");
        System.out.println("                               ║                                 [1] Quản lý đồ uống, thức ăn                      ║");
        System.out.println("                               ║                                 [2] Quản lý đơn hàng                              ║");
        System.out.println("                               ║                                 [3] Xem danh sách khách hàng                      ║");
        System.out.println("                               ║                                 [4] Xem doanh thu                                 ║");
        System.out.println("                               ║                                 [5] Đăng xuất                                     ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcher() throws IOException {
        LoginView loginView = new LoginView();
        int select = 0;
        boolean checkAction = false;
        do {
            menuAdminView();
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
                    foodView.launcher();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    launcherOder();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    loginView.showInfoCustomer();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    launcherRevenue();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    Menu menu = new Menu();
                    menu.login();
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
    public  void menuOderManager(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                              Giao diện quản lý đơn hàng                           ║");
        System.out.println("                               ║                       [1] Xem danh sách tất cả đơn hàng                           ║");
        System.out.println("                               ║                       [2] Xem danh sách đơn hàng chưa thanh toán                  ║");
        System.out.println("                               ║                       [3] Xem danh sách đơn hàng đã thanh toán                    ║");
        System.out.println("                               ║                       [4] Tìm kiếm đơn hàng theo ID                               ║");
        System.out.println("                               ║                       [5] Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherOder() throws IOException {
        OrderView orderView = new OrderView();
        int select = 0;
        boolean checkAction = false;
        do {
            menuOderManager();
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
                    orderView.showOderAll();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    orderView.showOderUnPaid();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    orderView.showOderPaid();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    orderView.findOderById();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    launcher();
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
    public  void menuRevenue(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                              Doanh thu Phúc Long coffe                            ║");
        System.out.println("                               ║                       [1] Xem doanh thu theo ngày                                 ║");
        System.out.println("                               ║                       [2] Xem doanh thu theo tháng                                ║");
        System.out.println("                               ║                       [3] Xem tổng doanh thu toàn bộ                              ║");
        System.out.println("                               ║                       [4] Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherRevenue() throws IOException {
        OrderView orderView = new OrderView();
        int select = 0;
        boolean checkAction = false;
        do {
            menuRevenue();
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
                    orderView.showRevenueByDay();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    orderView.showRevenueByMonth();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    orderView.showTotalRevenue();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    launcher();
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
}
