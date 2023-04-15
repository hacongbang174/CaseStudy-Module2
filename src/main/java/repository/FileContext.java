package repository;

import service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContext<T> {
    protected  Class<T> tClass;
    protected String filePath;
    private FileService fileService;
    public FileContext(){
        fileService = new FileService();
    }
    public List<T> getAll() throws IOException {
        return fileService.readData(filePath,tClass);
    }
    public int checkID(int id) throws IOException {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> imodel = (IModel<T>) list.get(i);
            if (imodel.getId() == id) {
                return 1;
            }
        }
        return -1;
    }
    public int checkName(String name) throws IOException {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> imodel = (IModel<T>) list.get(i);
            if (imodel.getName().equals(name)) {
                return 1;
            }
        }
        return -1;
    }
    public void deleteById(int id) throws IOException {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> imodel = (IModel<T>) list.get(i);
            if (imodel.getId() == id) {
                list.remove(i);
                break;
            }
        }
        fileService.writeData(filePath,list);
    }
    public void add(T newObj) throws IOException {
        List<T> list = getAll();
        list.add(newObj);
        fileService.writeData(filePath,list);
    }
}
