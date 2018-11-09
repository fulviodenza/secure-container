package exceptions;
public class UserNotAllowedException extends Exception {
  public UserNotAllowedException() {
    super("L'utente non dispone dei privilegi di accesso al dato");
  }
}
