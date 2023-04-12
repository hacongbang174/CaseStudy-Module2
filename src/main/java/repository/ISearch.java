package repository;

public interface ISearch<T> {
    boolean searchByName(T t, String name);
}
