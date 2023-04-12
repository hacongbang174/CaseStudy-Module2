package service;

import model.Oder;
import repository.ISearch;
import repository.OderAllRepository;
import repository.OderRepository;

import java.io.IOException;
import java.util.List;

public class OderService {
    private OderRepository oderRepository;
    private OderAllRepository oderAllRepository;
    public OderService() {
        oderRepository = new OderRepository();
        oderAllRepository = new OderAllRepository();
    }
    public List<Oder> getAllOder() throws IOException {
        return oderRepository.getAll();
    }
    public Oder findFoodById(int id) throws IOException {
        return oderRepository.findById(id);
    }
    public int checkIdOderAll(int id) throws IOException {
        return oderAllRepository.checkID(id);
    }
    public int checkIdOder(int id) throws IOException {
        return oderRepository.checkID(id);
    }
    public int checkNameFood(String name) throws IOException {
        return oderRepository.checkName(name);
    }
    public void deleteFoodOutOderAllById(int id) throws IOException {
        oderAllRepository.deleteById(id);
    }
    public void deleteFoodOutOderByName(String name) throws IOException {
        oderRepository.deleteByName(name);
    }
    public void deleteFoodOutOderAllByName(String name) throws IOException {
        oderAllRepository.deleteByName(name);
    }
    public void addOder(Oder oder) throws IOException {
        oderRepository.add(oder);
    }
    public void updateFoodById(int id, Oder oder) throws IOException {
        oderRepository.updateById(id,oder);
    }
    public List<Oder> searchOderByName(String name, ISearch<Oder> iSearch) throws IOException {
        return oderRepository.searchByName(name,iSearch);
    }
    public void addOderList(List<Oder> list) throws IOException {
        oderRepository.addList(list);
    }

    public List<Oder> getAllOderAll() throws IOException {
        return oderAllRepository.getAll();
    }

    public int checkNameFoodInOder(String nameFood) throws IOException {
        return oderRepository.checkName(nameFood);
    }
}
