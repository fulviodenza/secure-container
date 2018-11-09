public class ElementAlreadyPresentException extends Exception {
  public ElementAlreadyPresentException() {
    super("Tentativo di aggiungere un elemento già presente nella lista dell'utente.");
  }
}
