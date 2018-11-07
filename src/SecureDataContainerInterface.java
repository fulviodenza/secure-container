import java.util.Iterator;

public interface SecureDataContainerInterface<E> {

    // Crea l’identità un nuovo utente della collezione

    public void createUser(String Id, String passw);

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/

    public int getSize(String Owner, String passw);

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public boolean put(String Owner, String passw, E data);

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public E get(String Owner, String passw, E data);

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/

    public E remove(String Owner, String passw, E data);

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public void copy(String Owner, String passw, E data);

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/

    public void share(String Owner, String passw, String Other, E data);

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/

    public Iterator<E> getIterator(String Owner, String passw);
}
