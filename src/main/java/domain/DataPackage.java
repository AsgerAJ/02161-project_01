package domain;

public class DataPackage implements SuccessAmount {
    private boolean truthValue =false;
    private int amount = 0;

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
}
