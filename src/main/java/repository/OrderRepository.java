package repository;

import model.Order;

public class OrderRepository extends FileContext<Order> {
    public OrderRepository() {
        filePath = "./src/main/data/order.csv";
        tClass = Order.class;
    }
}
