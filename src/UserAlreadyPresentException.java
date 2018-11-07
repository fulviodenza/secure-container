public class UserAlreadyPresentException extends Exception {
  public UserAlreadyPresentException(String user) {
    super(user + " è un utente già presente nella base.");
  }

  public UserAlreadyPresentException() {
    super("Tentativo di creazione di un utente già presente nella base.");
  }
}
