package domain.exceptions;

/*
 @Author: Niklas Emil Lysdal
 */
public class AUserIsAlreadyLoggedInException extends Exception {

    private static final long serialVersionUID = 1L;

    public AUserIsAlreadyLoggedInException(String string) { // author: Niklas Emil Lysdal
        super(string);
    }
}
