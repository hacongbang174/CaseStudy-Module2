package view;

import model.User;
import service.UserService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void menuLogin() {
        System.out.println("                               ╔══════════════════════════════════════--Giao diện--════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                              PHÚC LONG COFFEE XIN KÍNH CHÀO!                      ║");
        System.out.println("                               ║                               [1] Đăng nhập bằng Admin                            ║");
        System.out.println("                               ║                               [2] Đăng nhập bằng khách hàng                       ║");
        System.out.println("                               ║                               [3] Đăng ký tài khoản                               ║");
        System.out.println("                               ║                               [4] Thoát                                           ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void login() throws IOException {
        int select;
        boolean checkAction = false;
        do {
            menuLogin();
            System.out.println("\nChọn chức năng ");
            System.out.print("\t➺ ");
            try {
                select = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nhập lỗi, vui lòng nhập lại!");
                select = 0;
                continue;
            }
            switch (select) {
                case 1:
                    admin();
                    break;
                case 2:
                    customer();
                    break;
                case 3:
                    signUp();
                    break;
                case 4:
                    exit();
                    break;
                default:
                    System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
    }

    public void signUp() throws IOException {
        LoginView loginView = new LoginView();
        loginView.signUp();
    }

    public void admin() throws IOException {
        LoginView loginView = new LoginView();
        loginView.loginAdmin();

    }

    public void customer() throws IOException {
        LoginView loginView = new LoginView();
        loginView.loginCustomer();
    }

    public void exit() {
        System.out.println("\t\t\t\t\t\tCám ơn quý khách");
        System.out.println("\t\t\t\t\t\t ✌ Hẹn gặp lại ✌");
        System.exit(0);
    }

}
