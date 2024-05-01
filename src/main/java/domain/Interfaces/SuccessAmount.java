package domain.Interfaces;

public interface SuccessAmount {
    public boolean isTrue();
    public int amount();

    public void increaseAmount(int i);
    public void decreaseAmount(int i);

    public void setAmount(int i);

    public void setTruthValue(boolean v);
}
