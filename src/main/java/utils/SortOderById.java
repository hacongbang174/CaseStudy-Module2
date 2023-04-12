package utils;

import model.Oder;

import java.util.Comparator;

public class SortOderById implements Comparator<Oder> {
    @Override
    public int compare(Oder o1, Oder o2) {
        return o1.getIdOder() - o2.getIdOder();
    }
}
