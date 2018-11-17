package exceptions;

public class ElementNotPresentException extends Exception {
    public ElementNotPresentException() {
        super("L'elemento richiesto non fa parte della collezione dell'utente.");
    }
}
