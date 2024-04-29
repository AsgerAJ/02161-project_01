package domain;

public class InvalidDateException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidDateException(String string) {
        super(string);
    }
}
