package repository;

public interface IModel<T> {
    int getId();
    String getName();
    void update (T obj);
    T parseData(String line);
}
