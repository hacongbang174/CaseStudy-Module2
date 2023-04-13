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
    public Order findFoodById(int id) throws IOException {
        return orderRepository.findById(id);
    }
    public int checkIdOderAll(int id) throws IOException {
        return orderAllRepository.checkID(id);
    }
    public int checkIdOder(int id) throws IOException {
        return orderRepository.checkID(id);
    }
    public int checkNameFood(String name) throws IOException {
        return orderRepository.checkName(name);
    }
    public void deleteFoodOutOderAllById(int id) throws IOException {
        orderAllRepository.deleteById(id);
    }
    public void deleteFoodOutOderByName(String name) throws IOException {
        orderRepository.deleteByName(name);
    }
    public void deleteFoodOutOderAllByName(String name) throws IOException {
        orderAllRepository.deleteByName(name);
    }
    public void addOder(Order order) throws IOException {
        orderRepository.add(order);
    }
    public void updateFoodById(int id, Order order) throws IOException {
        orderRepository.updateById(id, order);
    }
    public List<Order> searchOderByName(String name, ISearch<Order> iSearch) throws IOException {
        return orderRepository.searchByName(name,iSearch);
    }
    public void addOderList(List<Order> list) throws IOException {
        orderRepository.addList(list);
    }

    public List<Order> getAllOderAll() throws IOException {
        return orderAllRepository.getAll();
    }

    public int checkNameFoodInOder(String nameFood) throws IOException {
        return orderRepository.checkName(nameFood);
    }
    public int checkNameFoodInOderAll(String nameFood) throws IOException {
        return orderAllRepository.checkName(nameFood);
    }
}
