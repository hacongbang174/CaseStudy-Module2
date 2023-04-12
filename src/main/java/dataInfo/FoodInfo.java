package dataInfo;

import model.ETypeOfFood;
import model.Food;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodInfo {
    public  void foodInfo() throws IOException {
        List<Integer> listIdDrink = new ArrayList<>();
        List<String> listNameDrink = new ArrayList<>();
        List<Double> listPriceDrink = new ArrayList<>();
        List<Integer> listIdBakery = new ArrayList<>();
        List<String> listNameBakery = new ArrayList<>();
        List<Double> listPriceBakery = new ArrayList<>();

        readInfoToWeb("https://phuclong.com.vn/category/thuc-uong", "<button class=\"btn btn-default add-to-cart\" data-id=\"(.*?)\"", listIdDrink);
        readInfoToWeb("https://phuclong.com.vn/category/thuc-uong", "data-name=\"(.*?)\"", listNameDrink);
        readInfoToWeb("https://phuclong.com.vn/category/thuc-uong", "data-price=\"(.*?)\">Đặt hàng", listPriceDrink);
        readInfoToWeb("https://phuclong.com.vn/category/bakery", "<button class=\"btn btn-default add-to-cart\" data-id=\"(.*?)\"", listIdBakery);
        readInfoToWeb("https://phuclong.com.vn/category/bakery", "data-name=\"(.*?)\"", listNameBakery);
        readInfoToWeb("https://phuclong.com.vn/category/bakery", "data-price=\"(.*?)\">Đặt hàng", listPriceBakery);
        List<String> foods = new ArrayList<>();
        for (int i = 0; i < listIdDrink.size(); i++) {
            int quantity = (int) Math.floor(Math.random()*1000);
            foods.add(listIdDrink.get(i) + "," + listNameDrink.get(i) + "," + quantity + "," +listPriceDrink.get(i) + ",drink");
        }
        for (int i = 0; i < listIdBakery.size(); i++) {
            int quantity = (int) Math.floor(Math.random()*20);
            foods.add(listIdBakery.get(i) + "," + listNameBakery.get(i) + "," + quantity + "," + listPriceBakery.get(i) + ",bakery");
        }
        writeToFile("./src/main/data/food.csv", foods);
    }

    private  <E> void readInfoToWeb(String urlName, String regex, List<E> list) {
        try {
            URL url = new URL(urlName);
            Scanner scanner = new Scanner(new InputStreamReader(url.openStream(), "UTF-8"));
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            content.replaceAll("\\n+", "").replaceAll("\\r+", "");
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
//                System.out.println(matcher.group(1));
                list.add((E) matcher.group(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeToFile(String path, List<String> list) throws IOException {
        try (
                Writer fos = new OutputStreamWriter(
                        new FileOutputStream(path), "UTF-8");
                BufferedWriter oos = new BufferedWriter(fos)
        ) {
            for (String s : list) {
                oos.write(s);
                oos.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void inputDrinkInfo(String url, String key) {
//
//    }
}
