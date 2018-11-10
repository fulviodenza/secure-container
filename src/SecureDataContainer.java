import java.util.Iterator;

public interface SecureDataContainer<E> {

    // Crea l’identità un nuovo utente della collezione

    public void createUser(String Id, String passw)throws DoubleUserException;

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/

    public int getSize(String Owner, String passw)throws NoUserException;

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public boolean put(String Owner, String passw, E data) throws NoUserException;

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public E get(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/

    public E remove(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public void copy(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/

    public void share(String Owner, String passw, String Other, E data)throws NoUserException;

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/

    public Iterator<E> getIterator(String Owner, String passw)throws NoUserException;

    /*autorizza l'utente ad accedere ai dati del proprietario*/
    public void empowerUser(String nome)throws NoUserException,AlreadyPoweredException;
    /*banna l'utente dall'accesso ai dati del proprietario*/
    public void depowerUser(String nome)throws NoUserException,AlreadyWeakException;

    /*condivide il dato nella collezione di un utente se autorizzato con un altro utente*/
    public void share(String user)throws NoUserException,NotAuthorizedUserException;
    /*Ottiene una copia del valore del dato nella collezione
    se autorizzato*/
    public E get(String user, E data)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;
    /*Rimuove il dato nella collezione
    se autorizzato*/
    public E remove(String user, E data)throws NoUserException,DataNotFoundException;

}