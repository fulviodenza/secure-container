public class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException(String user, String password) {
    super(user + " " + password + " non è una combinazione valida di utente/password.");
  }

  public InvalidCredentialsException() {
    super("Login fallito");
  }
}
