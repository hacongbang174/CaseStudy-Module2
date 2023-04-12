package model;

import repository.IModel;

import java.util.Currency;

public class Food implements IModel<Food> {
    private int idFood;
    private String nameFood;
    private int quantity;
    private Double priceFood;
    private ETypeOfFood eTypeOfFood;

    public Food() {
    }

    public Food(int idFood, String nameFood, int quantity, Double priceFood, ETypeOfFood eTypeOfFood) {
        this.idFood = idFood;
        this.nameFood = nameFood;
        this.quantity = quantity;
        this.priceFood = priceFood;
        this.eTypeOfFood = eTypeOfFood;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(Double priceFood) {
        this.priceFood = priceFood;
    }

    public ETypeOfFood geteTypeOfFood() {
        return eTypeOfFood;
    }

    public void seteTypeOfFood(ETypeOfFood eTypeOfFood) {
        this.eTypeOfFood = eTypeOfFood;
    }

    @Override
    public int getId() {
        return idFood;
    }

    @Override
    public String getName() {
        return nameFood;
    }

    @Override
    public void update(Food obj) {
        this.idFood = obj.idFood;
        this.nameFood = obj.nameFood;
        this.quantity = obj.quantity;
        this.priceFood = obj.priceFood;
        this.eTypeOfFood = obj.eTypeOfFood;
    }

    @Override
    public Food parseData(String line) {
       Food food = new Food();
       String[] strings = line.split(",");
       int id = Integer.parseInt(strings[0]);
       String name = strings[1];
       int quantity = Integer.parseInt(strings[2]);
       Double price = Double.parseDouble(strings[3]);
       ETypeOfFood typeOfFood = ETypeOfFood.getTypeOfFoodByName(strings[4]);
       food.setIdFood(id);
       food.setNameFood(name);
       food.setQuantity(quantity);
       food.setPriceFood(price);
       food.seteTypeOfFood(typeOfFood);
       return food;
    }
    public String foodView() {
        return String.format("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", this.idFood, this.nameFood, this.quantity, this.priceFood, this.eTypeOfFood.getName());
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", this.idFood, this.nameFood, this.quantity, this.priceFood, this.eTypeOfFood.getName());
    }
}
