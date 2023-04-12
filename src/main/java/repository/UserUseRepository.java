package repository;

import model.User;

public class UserUseRepository extends FileContext<User>{
    public UserUseRepository(){
        filePath = "./src/main/data/userUse.csv";
        tClass = User.class;
    }
}
