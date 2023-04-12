package repository;

import model.Oder;

public class OderAllRepository extends FileContext<Oder> {
    public OderAllRepository() {
        filePath = "./src/main/data/oderAll.csv";
        tClass = Oder.class;
    }
}
