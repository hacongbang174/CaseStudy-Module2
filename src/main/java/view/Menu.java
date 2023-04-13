package view;

import model.User;
import service.UserService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private LoginView loginView;
    private Scanner scanner;

    public Menu() {
        loginView = new LoginView();
        scanner = new Scanner(System.in);
    }

    public void menuLogin() {
        System.out.println("                               ╔════════════════════════════════════--Giao diện--══════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                              PHÚC LONG COFFEE XIN KÍNH CHÀO!                      ║");
        System.out.println("                               ║                                   1. Đăng nhập bằng Admin                         ║");
        System.out.println("                               ║                                   2. Đặng nhập bằng khách hàng                    ║");
        System.out.println("                               ║                                   3. Đăng ký tài khoản                            ║");
        System.out.println("                               ║                                   4. Thoát                                        ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void login() {
        do {
            menuLogin();
            try {
                int select;
                Scanner scanner = new Scanner(System.in);
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
                        login();
                }

            } catch (InputMismatchException io) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void signUp() throws IOException {
        loginView.signUp();
    }

    public void admin() throws IOException {
        loginView.loginAdmin();

    }
    public void customer() throws IOException {
        loginView.loginCustomer();
    }

    public void exit() {
        System.out.println("\t\t\t\t\t\tCám ơn quý khách");
        System.out.println("\t\t\t\t\t\t ✌ Hẹn gặp lại ✌");

        System.exit(0);
    }

}
