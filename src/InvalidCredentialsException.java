public class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException(String user, password) {
    super("PORCODIO " + user + " " + password + " non è una combinazione valida di utente/password, scemo!");
  }

  public InvalidCredentialsException() {
    super("Mi scoccio di scrivere un messaggio decente per l'eccezione, poi vedo di renderlo normale");
  }
}
