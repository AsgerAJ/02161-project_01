package domain.exceptions;

public class UserIdAlreadyInUseExeption extends Exception {

    private static final long serialVersionUID = 1L;

    public UserIdAlreadyInUseExeption(String string){
        super(string);
    }
}