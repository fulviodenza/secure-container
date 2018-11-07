public class UserAlreadyPresentException extends Exception {
  public UserAlreadyPresentException(String user) {
    super("PORCODIO " + user + " è gia presente, scemo!");
  }

  public UserAlreadyPresentException() {
    super("Mi scoccio di scrivere un messaggio decente per l'eccezione, poi vedo di renderlo normale");
  }
}
