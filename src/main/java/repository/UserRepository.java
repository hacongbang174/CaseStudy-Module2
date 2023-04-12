package repository;

import model.User;

public class UserRepository extends FileContext<User> {
    public UserRepository(){
        filePath = "./src/main/data/user.csv";
        tClass = User.class;
    }
}
