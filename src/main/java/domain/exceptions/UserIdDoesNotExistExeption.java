package domain.exceptions;

public class UserIdDoesNotExistExeption extends Exception {

    private static final long serialVersionUID = 1L;

    public UserIdDoesNotExistExeption(String string){
        super(string);
    }
}
