package domain;

public class DataPackage implements SuccessAmount, UserCount {
    private boolean truthValue =false;
    private int amount = 0;
    private User user;
    private int count;
    public DataPackage() {}


    @Override
    public boolean isTrue() {
        return this.truthValue;
    }

    @Override
    public int amount() {
        return this.amount;
    }

    @Override
    public void increaseAmount(int i ) {
        this.amount+=i;
    }

    @Override
    public void decreaseAmount(int i) {
        this.amount-=i;
    }
    @Override
    public void setAmount(int i ) {
        this.amount=i;
    }

    @Override
    public void setTruthValue(boolean v) {
        this.truthValue=v;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void setUser(User u) {
        this.user=u;
    }

    @Override
    public void setCount(int count) {
        this.count=count;
    }

}
