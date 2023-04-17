package dataInfo;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodInfo {
    private final String URL_NAME = "https://phuclong.com.vn/category/";
    private final String NAME_DRINK = "thuc-uong";
    private final String NAME_BAKERY = "bakery";
    private final String ID_REGEX = "<button class=\"btn btn-default add-to-cart\" data-id=\"(.*?)\"";
    private final String NAME_REGEX = "data-name=\"(.*?)\"";
    private final String PRICE_RGEX = "data-price=\"(.*?)\">Đặt hàng";
    private final String FILE_PATH_FOOD = "./src/main/data/food.csv";
    private final String FILE_PATH_FOOD_UPDATE = "./src/main/data/foodupdate.csv";
    public  void foodInfo() throws IOException {
        List<Integer> listIdDrink = new ArrayList<>();
        List<String> listNameDrink = new ArrayList<>();
        List<Double> listPriceDrink = new ArrayList<>();
        List<Integer> listIdBakery = new ArrayList<>();
        List<String> listNameBakery = new ArrayList<>();
        List<Double> listPriceBakery = new ArrayList<>();

        readInfoToWeb(URL_NAME + NAME_DRINK, ID_REGEX, listIdDrink);
        readInfoToWeb(URL_NAME + NAME_DRINK, NAME_REGEX, listNameDrink);
        readInfoToWeb(URL_NAME + NAME_DRINK, PRICE_RGEX, listPriceDrink);
        readInfoToWeb(URL_NAME + NAME_BAKERY, ID_REGEX, listIdBakery);
        readInfoToWeb(URL_NAME + NAME_BAKERY, NAME_REGEX, listNameBakery);
        readInfoToWeb(URL_NAME + NAME_BAKERY, PRICE_RGEX, listPriceBakery);
        List<String> foods = new ArrayList<>();
        for (int i = 0; i < listIdDrink.size(); i++) {
            int quantity = (int) Math.floor(Math.random()*1000);
            foods.add(listIdDrink.get(i) + "," + listNameDrink.get(i) + "," + quantity + "," +listPriceDrink.get(i) + ",drink");
        }
        for (int i = 0; i < listIdBakery.size(); i++) {
            int quantity = (int) Math.floor(Math.random()*20);
            foods.add(listIdBakery.get(i) + "," + listNameBakery.get(i) + "," + quantity + "," + listPriceBakery.get(i) + ",bakery");
        }
        readToFile(FILE_PATH_FOOD_UPDATE,foods);
        writeToFile(FILE_PATH_FOOD, foods);

    }

    public <E> void readInfoToWeb(String urlName, String regex, List<E> list) {
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
                list.add((E) matcher.group(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readToFile(String filePath, List<String> list) throws IOException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                list.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileReader.close();
            bufferedReader.close();
        }
    }
    public void writeToFile(String path, List<String> list) throws IOException {
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

}
