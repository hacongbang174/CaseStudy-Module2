package service;

import model.Order;
import repository.ISearch;
import repository.OrderAllRepository;
import repository.OrderRepository;

import java.io.IOException;
import java.util.List;

public class OderService {
    private OrderRepository orderRepository;
    private OrderAllRepository orderAllRepository;
    public OderService() {
        orderRepository = new OrderRepository();
        orderAllRepository = new OrderAllRepository();
    }
    public List<Order> getAllOder() throws IOException {
        return orderRepository.getAll();
    }
    public int checkIdOderAll(int id) throws IOException {
        return orderAllRepository.checkID(id);
    }
    public void deleteFoodOutOderAllById(int id) throws IOException {
        orderAllRepository.deleteById(id);
    }
    public void deleteFoodOutOderById(int id) throws IOException {
        orderRepository.deleteById(id);
    }
    public List<Order> getAllOderAll() throws IOException {
        return orderAllRepository.getAll();
    }
}
