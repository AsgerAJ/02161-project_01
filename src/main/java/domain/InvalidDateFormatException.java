package domain;

public class InvalidDateFormatException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidDateFormatException(String string) {
        super(string);
    }
}
