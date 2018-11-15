package exceptions;

public class UserNotPresentException extends Exception {
  public UserNotPresentException(String user) {
    super(user + " non è un utente valido");
  }

  public UserNotPresentException() {
    super("Tentativo di accesso ad un utente non presente nella base");
  }
}
