package repository;

import model.Order;

public class OrderAllRepository extends FileContext<Order> {
    public OrderAllRepository() {
        filePath = "./src/main/data/orderAll.csv";
        tClass = Order.class;
    }
}
