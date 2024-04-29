package domain;

import java.util.Comparator;

public class CountSorting implements Comparator<Count> {



    @Override
    public int compare(Count o1, Count o2) {
        return o1.getCount()-o2.getCount();
    }
}
