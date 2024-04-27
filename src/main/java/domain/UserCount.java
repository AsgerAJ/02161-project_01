package domain;

import java.util.ArrayList;

public interface UserCount {

    public User getUser();
    public int getCount();
    public void setUser(User u);
    public void setCount(int count);

    public default String UserCountToString() {
        return getUser().getUserId() + ":" + getCount();
    }




}

