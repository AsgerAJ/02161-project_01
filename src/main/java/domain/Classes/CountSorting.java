package domain.Classes;

import domain.Interfaces.Count;

import java.util.Comparator;
/*
* Used to allow sorting of objects
* Single Responsibity, as only purpose is to give the java library Collections, knowledge on how to sort items implementing count interface
* */
public class CountSorting implements Comparator<Count> {
    @Override
    public int compare(Count o1, Count o2) {
        return o1.getCount()-o2.getCount();
    }
}
