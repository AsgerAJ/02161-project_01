package domain;




public interface UserCount extends Count {

    public User getUser();

    public void setUser(User u);


    public default String UserCountToString() {
        return getUser().getUserId() + ":" + getCount();
    }




}

