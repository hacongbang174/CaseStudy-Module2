package repository;

import model.Order;

public class OrderRepository extends FileContext<Order> {
    public OrderRepository() {
        filePath = "./src/main/data/oder.csv";
        tClass = Order.class;
    }
}
