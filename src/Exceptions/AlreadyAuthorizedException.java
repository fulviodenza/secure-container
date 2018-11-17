package Exceptions;

public class AlreadyAuthorizedException extends Exception{
    public AlreadyAuthorizedException(){
        super();
    }
    public AlreadyAuthorizedException(String s){
        super(s);
    }
}
