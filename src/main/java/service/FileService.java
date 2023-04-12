package service;

import model.Food;
import model.User;
import repository.IModel;
import repository.UserRepository;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public  <T> List<T> readData(String filePath, Class<T> cls) throws IOException {
        List<T> list = new ArrayList<>();
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try{
            reader = new FileReader(filePath);
            bufferedReader = new BufferedReader(reader);
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                IModel<T> iModel = (IModel<T>) cls.getDeclaredConstructor().newInstance();
                T temp = iModel.parseData(line);
                list.add(temp);
            }
            bufferedReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            reader.close();
            bufferedReader.close();
        }
        return list;
    }
    public  <T> void writeData(String filePath, List<T> list) throws IOException {
        FileWriter writer = null;
        PrintWriter printWriter = null;
        try{
            writer = new FileWriter(filePath);
            printWriter = new PrintWriter(writer);
            for(int i = 0; i < list.size(); i++){
                printWriter.write(list.get(i).toString());
                if(i != (list.size()-1)){
                    printWriter.write("\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            writer.close();
            printWriter.close();
        }
    }
    public <T> void clearData(String filePath) throws IOException {
        FileWriter writer = null;
        PrintWriter printWriter = null;
        try{
            writer = new FileWriter(filePath);
            printWriter = new PrintWriter(writer);
            printWriter.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            writer.close();
            printWriter.close();
        }
    }

//    public static void main(String[] args) throws IOException {
//        List<Food> list = readData("./src/main/data/food.csv", Food.class);
//        System.out.println(list.get(0));
//    }
}
