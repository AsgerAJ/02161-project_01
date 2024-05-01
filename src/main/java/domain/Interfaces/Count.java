package domain.Interfaces;

import java.util.Comparator;

/* @author : Niklas*/
public interface Count extends Comparator <Count>{
    public int getCount();

    public default int compare(Count c1, Count c2) {
        return c1.getCount()-c2.getCount();

    }
    public void setCount(int count);
}
