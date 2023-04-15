package model;

import repository.IModel;
import utils.CurrencyFormat;
import utils.DateFormat;

import java.util.Date;

public class Order implements IModel<Order> {
    private int idOder;
    private int idCustomer;
    private String nameCustomer;
    private String nameFood;
    private int quantityFood;
    private double priceFood;
    private double totalMoney;
    private Date createDateOder;
    private EStatus status;

    public Order() {
    }

    public Order(int idOder, int idCustomer, String nameCustomer, String nameFood, int quantityFood, double priceFood, double totalMoney, Date createDateOder, EStatus status) {
        this.idOder = idOder;
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.nameFood = nameFood;
        this.quantityFood = quantityFood;
        this.priceFood = priceFood;
        this.totalMoney = totalMoney;
        this.createDateOder = createDateOder;
        this.status = status;
    }

    public int getIdOrder() {
        return idOder;
    }

    public void setIdOder(int idOder) {
        this.idOder = idOder;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getQuantityFood() {
        return quantityFood;
    }

    public void setQuantityFood(int quantityFood) {
        this.quantityFood = quantityFood;
    }

    public double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(double priceFood) {
        this.priceFood = priceFood;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreateDateOder() {
        return this.createDateOder;
    }

    public void setCreateDateOder(Date createDateOder) {
        this.createDateOder = createDateOder;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    @Override
    public int getId() {
        return idOder;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void update(Order obj) {
        this.idOder = obj.idOder;
        this.idCustomer = obj.idCustomer;
        this.nameCustomer = obj.nameCustomer;
        this.nameFood = obj.nameFood;
        this.quantityFood = obj.quantityFood;
        this.priceFood = obj.priceFood;
        this.totalMoney = obj.totalMoney;
        this.createDateOder = obj.createDateOder;
        this.status = obj.status;
    }

    @Override
    public Order parseData(String line) {
        Order order = new Order();
        String[] strings = line.split(",");
        int idOder = Integer.parseInt(strings[0]);
        int idCustomer = Integer.parseInt(strings[1]);
        String nameCustomer = strings[2];
        String nameFood = strings[3];
        int quantityFood = Integer.parseInt(strings[4]);
        double priceFood = Double.parseDouble(strings[5]);
        double totalMoney = Double.parseDouble(strings[6]);
        Date createDateOder = DateFormat.parseDate2(strings[7]);
        EStatus eStatus = EStatus.getStatusByName(strings[8]);
        order.setIdOder(idOder);
        order.setIdCustomer(idCustomer);
        order.setNameCustomer(nameCustomer);
        order.setNameFood(nameFood);
        order.setQuantityFood(quantityFood);
        order.setPriceFood(priceFood);
        order.setTotalMoney(totalMoney);
        order.setCreateDateOder(createDateOder);
        order.setStatus(eStatus);
        return order;
    }
    public String oderView() {
        return String.format("            ║ %-6s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", this.idOder, this.idCustomer, this.nameCustomer, this.nameFood, this.quantityFood, CurrencyFormat.covertPriceToString(this.priceFood), CurrencyFormat.covertPriceToString(this.totalMoney), DateFormat.convertDateToString2(this.createDateOder), this.status.getName());
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", this.idOder, this.idCustomer, this.nameCustomer, this.nameFood, this.quantityFood, CurrencyFormat.parseInteger(this.priceFood), CurrencyFormat.parseInteger(this.totalMoney), DateFormat.convertDateToString2(this.createDateOder), this.status.getName());
    }
}
