import dataInfo.FoodInfo;
import view.Menu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.foodInfo();
        Menu menu = new Menu();
        menu.login();
    }
}
