package utils;

import model.Order;

import java.util.Comparator;

public class SortOderById implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getIdOrder() - o2.getIdOrder();
    }
}
