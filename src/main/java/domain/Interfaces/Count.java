package domain.Interfaces;

import java.util.Comparator;

/* @author : Niklas*/
public interface Count extends Comparator <Count>{
    public int getCount();
    public void increaseCount(int i);

    public void setCount(int count);


    public default int compare(Count c1, Count c2) {
        return c1.getCount()-c2.getCount();

    }
    public void decreaseCount(int i);

}
