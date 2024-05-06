package domain.exceptions;
/*
 @Author: Asger Allin Jensen
 */
public class UserIdAlreadyInUseExeption extends Exception {

    private static final long serialVersionUID = 1L;

    public UserIdAlreadyInUseExeption(String string){
        super(string);
    } // author: Asger Allin Jensen
}
