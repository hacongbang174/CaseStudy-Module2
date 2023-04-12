package view;

import model.Food;
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
        System.out.println("                               ║                                 [4] Tổng doanh thu                                ║");
        System.out.println("                               ║                                 [5] Đăng xuất                                     ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcher() throws IOException {
        LoginView loginView = new LoginView();
        boolean checkAction = false;
        do {
            menuAdminView();
            System.out.println("Chọn chức năng:");
            int select = Integer.parseInt(scanner.nextLine());
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
                    oderView.showTotalRevenue();
                    break;
                default:
                    FoodView foodView = new FoodView();
                    foodView.launcher();
                    break;
            }
            checkAction = checkActionContinue();
        }while (checkAction);
    }
    public  void menuOderManager(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                              Giao diện quản lý đơn hàng                           ║");
        System.out.println("                               ║                   [1] Xem danh sách đơn hàng                                      ║");
        System.out.println("                               ║                   [2] Tìm kiếm đơn hàng theo tên khách hàng                       ║");
        System.out.println("                               ║                   [3] Thêm đơn hàng vào danh sách đơn hàng                        ║");
        System.out.println("                               ║                   [4] Chỉnh sửa đơn hàng theo id                                  ║");
        System.out.println("                               ║                   [5] Xóa đơn hàng theo id                                        ║");
        System.out.println("                               ║                   [6] Quay lại                                                    ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherOder() throws IOException {
        OderView oderView = new OderView();
        boolean checkAction = false;
        do {
            menuOderManager();
            System.out.println("Chọn chức năng:");
            int select = Integer.parseInt(scanner.nextLine());
            switch (select) {
                case 1:
                    oderView.showOderAll();
                    break;
                case 2:
                    oderView.findOderByNameCustomer();
                    break;
                case 3:
                    oderView.addOder();
                    break;
                case 4:
                    oderView.editOder();
                    break;
                case 5:
                    oderView.deleteOder();
                    break;
                case 6:
                    launcher();
                    break;
                default:
                    FoodView foodView = new FoodView();
                    foodView.launcher();
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
