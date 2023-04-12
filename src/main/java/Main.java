import dataInfo.FoodInfo;
import feature.PrintColor;
import view.LoginView;
import view.Menu;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.foodInfo();
        Menu menu = new Menu();
        menu.login();
    }
}
