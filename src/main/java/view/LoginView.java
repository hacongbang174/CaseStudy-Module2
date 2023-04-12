package view;

import model.EGender;
import model.ERole;
import model.User;
import repository.FileContext;
import service.FileService;
import service.UserService;
import utils.DateFormat;
import utils.SortUserById;
import utils.ValidateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LoginView {
    private final String FILE_PATH_USER = "./src/main/data/user.csv";
    private final String FILE_PATH_USERUSE = "./src/main/data/userUse.csv";
    private static User currentUser = null;
    private UserService userService;
    private FileService fileService;
    private Scanner scanner;

    public LoginView() {
        userService = new UserService();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void menuLoginAdmin() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                          WELCOME TO COFFE MANAGEMENT SYSTEM                       ║");
        System.out.println("                               ║-----------------------------------ADMIN LOGIN PANEL-------------------------------║");
        System.out.println("                               ║                                 Login now to admin!!!                             ║");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void menuLoginCustomer() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                              WELCOME TO PHUC LONG COFFEE!                         ║");
        System.out.println("                               ║------------------------------- --CUSTOMER LOGIN PANEL--------- -------------------║");
        System.out.println("                               ║                                Login now to customer!!!                           ║");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void loginAdmin() throws IOException {
        menuLoginAdmin();
        boolean checkInfoLogin;
        do {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            checkInfoLogin = userService.checkLoginAdmin(username, password);
            if (!checkInfoLogin) {
                System.out.println("Incorrect username or incorrect password. Please re-enter");
            } else {
                AdminView adminView = new AdminView();
                adminView.launcher();
            }
        } while (checkInfoLogin);
    }

    public void loginCustomer() throws IOException {
        menuLoginCustomer();
        boolean checkInfoLogin;
        do {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            checkInfoLogin = userService.checkLoginCustomer(username, password);
            if (!checkInfoLogin) {
                System.out.println("Incorrect username or incorrect password. Please re-enter");
            } else {
                List<User> userList = new ArrayList<>();
                User user = userService.loginCustomer(username, password);
                userList.add(user);
                fileService.writeData(FILE_PATH_USERUSE, userList);
                CustomerView customerView = new CustomerView();
                customerView.launcher();
            }
        } while (checkInfoLogin);
    }

    public static boolean checkLogin() {
        if (currentUser == null) {
            System.out.println("Vui lòng đăng nhập trước khi sử dụng chức năng này!");
            return false;
        }
        return true;
    }

    public void signUp() throws IOException {
        List<User> userList = userService.getAllUser();
        userList.sort(new SortUserById());
        User user = new User();
//        int id, String username, String password, String fullName, String phoneNumber, EGender gender,
//        long cccd, Date birthDay, String email, String address, ERole eRole
        System.out.println("Nhập các thông tin của bạn!");
        inputUserName(user);
        System.out.println("Nhập password của bạn:");
        String password = scanner.nextLine();
        System.out.println("Nhập họ và tên của bạn:");
        String fullName = scanner.nextLine();
        inputPhoneNumber(user);
        System.out.println("Nhập giới tính của bạn: male/female/other");
        String gender = scanner.nextLine();
        switch (gender) {
            case "male":
                break;
            case "female":
                break;
            case "other":
                break;
            default:
                System.out.println("Nhập sai, xin mời nhập lại (male/female/other):");
                gender = scanner.nextLine();
                break;
        }
        inputCCCD(user);
        System.out.println("Nhập ngày tháng năm sinh: dd/MM/yyyy");
        String date = scanner.nextLine();
        inputEmail(user);
        System.out.println("Nhập địa chỉ của bạn:");
        String address = scanner.nextLine();
        user.setId(userList.get(userList.size() - 1).getId() + 1);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setGender(EGender.getEGenderByName(gender));
        user.setBirthDay(DateFormat.parseDate(date));
        user.setAddress(address);
        user.seteRole(ERole.customer);
        userList.add(user);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã tạo tài khoản thành công ✔\n");
    }


    private void inputEmail(User user) throws IOException {
        String email;
        boolean checkValid = false;
        boolean checkEmail = true;
        do {
            do {
                System.out.println("Nhập Email của bạn:");
                email = scanner.nextLine();
                checkValid = ValidateUtils.isEmail(email);
                if (checkValid == false) {
                    System.out.println("Email không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkEmail = userService.checkEmail(email);
            if (checkEmail) {
                System.out.println("Email đã tồn tại vui lòng nhập lại!");
            }
        } while (checkEmail);
        user.setEmail(email);
    }

    private void inputCCCD(User user) throws IOException {
        String cccd;
        boolean checkValid = false;
        boolean checkCCCD = true;
        do {
            do {
                System.out.println("Nhập số CCCD của bạn:");
                cccd = scanner.nextLine();
                checkValid = ValidateUtils.isCCCD(cccd);
                if (checkValid == false) {
                    System.out.println("Số CCCD không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkCCCD = userService.checkCCCD(cccd);
            if (checkCCCD) {
                System.out.println("Số CCCD đã tồn tại vui lòng nhập lại!");
            }
        } while (checkCCCD);
        user.setCccd(cccd);
    }

    private void inputPhoneNumber(User user) throws IOException {
        String phoneNumber = null;
        boolean checkValid = false;
        boolean checkPhoneNumber = true;
        do {
            do {
                System.out.println("Nhập số điện thoại của bạn:");
                phoneNumber = scanner.nextLine();
                checkValid = ValidateUtils.isPhoneNumber(phoneNumber);
                if (checkValid == false) {
                    System.out.println("Số điện thoại không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkPhoneNumber = userService.checkPhoneNumber(phoneNumber);
            if (checkPhoneNumber) {
                System.out.println("Số điện thoại đã tồn tại vui lòng nhập lại!");
            }
        } while (checkPhoneNumber);
        user.setPhoneNumber(phoneNumber);
    }

    public void inputUserName(User user) throws IOException {
        String username = null;
        boolean checkValid = false;
        boolean checkUserName = true;
        do {
            do {
                System.out.println("Nhập username của bạn:");
                username = scanner.nextLine();
                checkValid = ValidateUtils.isUserName(username);
                if (checkValid == false) {
                    System.out.println("UserName không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkUserName = userService.checkUserName(username);
            if (checkUserName) {
                System.out.println("UserName đã tồn tại vui lòng nhập lại!");
            }
        } while (checkUserName);
        user.setUsername(username);
    }

    public void editPassWord() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User>  userList = userService.getAllUser();

        String password = null;
        String passwordNew = null;
        String passwordNew1 = null;
        boolean checkPassword = true;
        do {
            System.out.println("Nhập password hiện tại của bạn:");
            password = scanner.nextLine();
            checkPassword = userService.checkPassword(password);
            if (checkPassword == false) {
                System.out.println("Password không đúng, vui lòng nhập lại!");
            } else if (checkPassword == true) {
                System.out.println("Nhập password mới của bạn:");
                passwordNew = scanner.nextLine();
                System.out.println("Nhập lại password mới của bạn:");
                do {
                    passwordNew1 = scanner.nextLine();
                    if (!passwordNew.equals(passwordNew1)) {
                        System.out.println("Password vừa nhập khác ở trên! Nhập lại password mới của bạn:");
                    }
                } while (!passwordNew.equals(passwordNew1));
            }
        } while (!checkPassword);
        users.get(0).setPassword(passwordNew);
        for (int i = 0; i < userList.size(); i++){
            if(userList.get(i).getUsername().equals(users.get(0).getUsername())){
                userList.get(i).setPassword(passwordNew);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi password thành công ✔\n");
    }

    public void editPhoneNumber() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User>  userList = userService.getAllUser();
        String phoneNumber = null;
        boolean checkValid = false;
        boolean checkPhoneNumber = true;
        do {
            do {
                System.out.println("Nhập số điện thoại mới của bạn:");
                phoneNumber = scanner.nextLine();
                checkValid = ValidateUtils.isPhoneNumber(phoneNumber);
                if (checkValid == false) {
                    System.out.println("Số điện thoại không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkPhoneNumber = userService.checkPhoneNumber(phoneNumber);
            if (checkPhoneNumber) {
                System.out.println("Số điện thoại đã tồn tại vui lòng nhập lại!");
            }
        } while (checkPhoneNumber);
        users.get(0).setPhoneNumber(phoneNumber);
        for (int i = 0; i < userList.size(); i++){
            if(userList.get(i).getUsername().equals(users.get(0).getUsername())){
                userList.get(i).setPhoneNumber(phoneNumber);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi số điện thoại thành công ✔\n");
    }

    public void editEmail() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User>  userList = userService.getAllUser();
        String email;
        boolean checkValid = false;
        boolean checkEmail = true;
        do {
            do {
                System.out.println("Nhập Email của bạn:");
                email = scanner.nextLine();
                checkValid = ValidateUtils.isEmail(email);
                if (checkValid == false) {
                    System.out.println("Email không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkEmail = userService.checkEmail(email);
            if (checkEmail) {
                System.out.println("Email đã tồn tại vui lòng nhập lại!");
            }
        } while (checkEmail);
        users.get(0).setEmail(email);
        for (int i = 0; i < userList.size(); i++){
            if(userList.get(i).getUsername().equals(users.get(0).getUsername())){
                userList.get(i).setEmail(email);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi email thành công ✔\n");
    }

    public void editAddress() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User>  userList = userService.getAllUser();
        System.out.println("Nhập Email của bạn:");
        String address = scanner.nextLine();
        users.get(0).setAddress(address);
        for (int i = 0; i < userList.size(); i++){
            if(userList.get(i).getUsername().equals(users.get(0).getUsername())){
                userList.get(i).setEmail(address);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi địa chỉ thành công ✔\n");
    }

    public void showInfoAccount() throws IOException {
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦═══════════════╦═════════════════════╦════════════════╦════════════════╦═══════════════╦════════════════╦═════════════════════════════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-15s║ %-20s║ %-15s║ %-15s║%-15s║ %-15s║ %-36s║ %-30s║", "ID", "USERNAME", "FULLNAME", "PHONE NUMBER", "GENDER", "CCCD", "BIRTHDAY", "EMAIL", "ADDRESS").println();
        System.out.println("            ╠═══════╬═══════════════╬═════════════════════╬════════════════╬════════════════╬═══════════════╬════════════════╬═════════════════════════════════════╬═══════════════════════════════╣");
        System.out.printf(users.get(0).userView()).println();
        System.out.println("            ╚═══════╩═══════════════╩═════════════════════╩════════════════╩════════════════╩═══════════════╩════════════════╩═════════════════════════════════════╩═══════════════════════════════╝");
    }
    public void showInfoCustomer() throws IOException {
        List<User> users = userService.getCustomerList();
        System.out.println("            ╔═══════╦═══════════════╦═════════════════════╦════════════════╦════════════════╦═══════════════╦════════════════╦═════════════════════════════════════╦═══════════════════════════════╗");
        System.out.printf("            ║%7s║%-15s║ %-20s║ %-15s║ %-15s║%-15s║ %-15s║ %-36s║ %-30s║", "ID", "USERNAME", "FULLNAME", "PHONE NUMBER", "GENDER", "CCCD", "BIRTHDAY", "EMAIL", "ADDRESS").println();
        System.out.println("            ╠═══════╬═══════════════╬═════════════════════╬════════════════╬════════════════╬═══════════════╬════════════════╬═════════════════════════════════════╬═══════════════════════════════╣");
        System.out.printf(users.get(0).userView()).println();
        System.out.println("            ╚═══════╩═══════════════╩═════════════════════╩════════════════╩════════════════╩═══════════════╩════════════════╩═════════════════════════════════════╩═══════════════════════════════╝");
    }

//    public static void main(String[] args) throws IOException {
//        LoginView loginView = new LoginView();
//        loginView.editPhoneNumber();
//    }
}
