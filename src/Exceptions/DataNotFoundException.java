package Exceptions;
public class DataNotFoundException extends Exception{
    public DataNotFoundException(){
        super();
    }
    public DataNotFoundException(String s){
        super(s);
    }
}