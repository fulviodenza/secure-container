package Exceptions;
public class NoUserException extends Exception{
    public NoUserException(){
        super();
    }
    public NoUserException(String s){
        super(s);
    }
}