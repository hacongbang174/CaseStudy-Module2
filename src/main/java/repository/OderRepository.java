package repository;

import model.Oder;

public class OderRepository extends FileContext<Oder> {
    public OderRepository() {
        filePath = "./src/main/data/oder.csv";
        tClass = Oder.class;
    }
}
