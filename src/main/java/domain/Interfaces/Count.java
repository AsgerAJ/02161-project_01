package domain.Interfaces;

import java.util.Comparator;

/* @author : Niklas*/
public interface Count extends Comparator <Count>{
    int getCount();
    void increaseCount(int i);

    void setCount(int count);


    default int compare(Count c1, Count c2) {
        return c1.getCount()-c2.getCount();

    }

}
