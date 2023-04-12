package service;

import model.ERole;
import model.User;
import repository.UserRepository;
import repository.UserUseRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private UserUseRepository userUseRepository;
    public UserService() {
        userRepository = new UserRepository();
        userUseRepository = new UserUseRepository();
    }
    public List<User> getAllUser() throws IOException {
        return userRepository.getAll();
    }
//    public User loginCustomer(String username, String password) throws IOException {
//        List<User> allUsers = getAllUser();
//        for (int i = 0; i < allUsers.size(); i++) {
//            if(allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(password) && allUsers.get(i).geteRole() == ERole.customer) {
//                return allUsers.get(i);
//            }
//        }
//        return null;
//    }
    public boolean checkLoginAdmin(String username, String pass) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(pass) && allUsers.get(i).geteRole().equals(ERole.admin)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkLoginCustomer(String username, String pass) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(pass) && allUsers.get(i).geteRole().equals(ERole.customer)) {
                return true;
            }
        }
        return false;
    }
    public List<User> getCustomerList() throws IOException {
        List<User> allUsers = userRepository.getAll();
        List<User> customerList = new ArrayList<>();
        for(User user: allUsers){
            if(user.geteRole() == ERole.customer){
                customerList.add(user);
            }
        }
        return customerList;
    }
    public void deleteById(int id) throws IOException {
        userRepository.deleteById(id);
    }

    public User loginAdmin(String username, String password) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(password) && allUsers.get(i).geteRole().equals(ERole.admin)) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public User loginCustomer(String username, String password) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(password) && allUsers.get(i).geteRole().equals(ERole.customer)) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public boolean checkUserName(String username) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public int checkNameCustomer(String nameCustomer) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getFullName().equals(nameCustomer) && allUsers.get(i).geteRole().equals(ERole.customer)) {
                return 1;
            }
        }
        return -1;
    }
    public boolean checkPhoneNumber(String phoneNumber) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCCCD(String cccd) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getCccd().equals(cccd)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkPassword(String password) throws IOException {
        List<User> allUsers = getAllUserUse();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUserUse() throws IOException {
        return userUseRepository.getAll();
    }
}
