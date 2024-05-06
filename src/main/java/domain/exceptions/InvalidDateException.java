package domain.exceptions;
/*
 @Author: Niklas Emil Lysdal
 */
public class InvalidDateException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidDateException(String string) {
        super(string);
    } // author: Niklas Emil Lysdal
}
