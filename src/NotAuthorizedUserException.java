public class NotAuthorizedUserException extends Exception {
    public NotAuthorizedUserException(){
        super();
    }
    public NotAuthorizedUserException(String s){
        super(s);
    }
}
