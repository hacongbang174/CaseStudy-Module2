package model;

import repository.IModel;
import utils.DateFormat;

import java.util.Date;

public class User implements IModel<User> {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private EGender gender;
    private String cccd;
    private Date birthDay;
    private String email;
    private String address;
    private ERole eRole;

    public User(int id, String username, String password, String fullName, String phoneNumber, EGender gender, String cccd, Date birthDay, String email, String address, ERole eRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.cccd = cccd;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
        this.eRole = eRole;
    }

    //    public User(String raw) {
//        String [] strings = raw.split(",");
//        this.id = Integer.parseInt(strings[0]);
//        this.username = strings[1];
//        this.password = strings[2];
//        this.fullName = strings[3];
//        this.phoneNumber = strings[4];
//        this.gender = EGender.getEGenderByName(strings[5]);
//        this.cccd = Integer.parseInt(strings[6]);
//        this.birthDay = DateFormat.parseDate(strings[7]);
//        this.email = strings[8];
//        this.address = strings[9];
//        this.eRole = ERole.valueOf(strings[10]);
//    }
    public User() {

    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return fullName;
    }

    @Override
    public void update(User obj) {
        this.id = obj.id;
        this.username = obj.username;
        this.password = obj.password;
        this.fullName = obj.fullName;
        this.phoneNumber = obj.phoneNumber;
        this.gender = obj.gender;
        this.cccd = obj.cccd;
        this.birthDay = obj.birthDay;
        this.email = obj.email;
        this.address = obj.address;
        this.eRole = obj.eRole;
    }

    @Override
    public User parseData(String line) {
        User user = new User();
        String[] strings = line.split(",");
        int id = Integer.parseInt(strings[0]);
        String username = strings[1];
        String password = strings[2];
        String fullName = strings[3];
        String phoneNumber = strings[4];
        EGender gender = EGender.getEGenderByName(strings[5]);
        String cccd = strings[6];
        Date birthDay = DateFormat.parseDate(strings[7]);
        String email = strings[8];
        String address = strings[9];
        ERole eRole = ERole.getRoleByName(strings[10]);

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        ;
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.setCccd(cccd);
        user.setBirthDay(birthDay);
        user.setEmail(email);
        user.setAddress(address);
        user.seteRole(eRole);

        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ERole geteRole() {
        return eRole;
    }

    public void seteRole(ERole eRole) {
        this.eRole = eRole;
    }

    public String userView() {
        return String.format("            ║%7s║%-15s║ %-20s║ %-15s║ %-15s║%-15s║ %-15s║ %-36s║ %-30s║", this.id, this.username, this.fullName, this.phoneNumber, this.gender.getName(), this.cccd, DateFormat.convertDateToString(this.birthDay), this.email, this.address);
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + fullName + "," + phoneNumber + "," + gender.getName() + "," + cccd + "," + DateFormat.convertDateToString(birthDay) + "," + email + "," + address + "," + eRole.getName();
    }
}
