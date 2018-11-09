package exceptions;
public class UserAlreadyAllowedException extends Exception {
  public UserAlreadyAllowedException() {
    super("L'utente dispone già dei privilegi di accesso al dato");
  }
}
