package exceptions;

public class UserAlreadyPresentException extends Exception{

    public UserAlreadyPresentException(){
        super();

    }

    public UserAlreadyPresentException(String s){
        super(s);

    }
}
