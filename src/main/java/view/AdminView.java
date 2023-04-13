package view;

import service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class AdminView {
    private static final String FILE_ODER = "./src/main/data/oder.csv";
    private FoodView foodView;
    private OderView oderView;
    private FileService fileService;
    private Scanner scanner;
    public AdminView() {
        foodView = new FoodView();
        oderView = new OderView();
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
                    break;
                case 2:
                    launcherOder();
                    break;
                case 3:
                    loginView.showInfoCustomer();
                    break;
                case 4:
                    launcherRevenue();
                    break;
                case 5:
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (!checkAction);
    }
    public  void menuOderManager(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                              Giao diện quản lý đơn hàng                           ║");
        System.out.println("                               ║                       [1] Xem danh sách đơn hàng                                  ║");
        System.out.println("                               ║                       [2] Tìm kiếm đơn hàng theo ID                               ║");
        System.out.println("                               ║                       [3] Thêm đơn hàng vào danh sách đơn hàng                    ║");
        System.out.println("                               ║                       [4] Xóa đơn hàng theo ID                                    ║");
        System.out.println("                               ║                       [5] Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherOder() throws IOException {
        OderView oderView = new OderView();
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
                    oderView.showOderAll();
                    break;
                case 2:
                    oderView.findOderById();
                    break;
                case 3:
                    oderView.addOderByAdmin();
                    break;
                case 4:
                    oderView.deleteOderById();
                    break;
                case 5:
                    launcher();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (!checkAction);
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
        OderView oderView = new OderView();
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
                    oderView.showRevenueByDay();
                    break;
                case 2:
                    oderView.showRevenueByMonth();
                    break;
                case 3:
                    oderView.showTotalRevenue();
                    break;
                case 4:
                    launcher();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    break;
            }
            checkAction = checkActionContinue();
        }while (!checkAction);
    }
    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Continue? Y/N");
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

//    public static void main(String[] args) throws IOException {
//        AdminView adminView = new AdminView();
//        adminView.launcher();
//    }
}
