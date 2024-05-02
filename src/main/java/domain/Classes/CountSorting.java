package domain.Classes;

import domain.Interfaces.Count;

import java.util.Comparator;
/*
* Used to allow sorting of objects
* */
public class CountSorting implements Comparator<Count> {
    @Override
    public int compare(Count o1, Count o2) {
        return o1.getCount()-o2.getCount();
    }
}
