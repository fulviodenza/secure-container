public class UserNotPresentException extends Exception {
  public UserNotPresentException(String user) {
    super("PORCODIO " + user + " non è un utente valido, scemo!");
  }

  public UserNotPresentException() {
    super("Mi scoccio di scrivere un messaggio decente per l'eccezione, poi vedo di renderlo normale");
  }
}
