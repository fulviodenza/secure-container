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

    public E get(String Owner, String passw, E data)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;

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
    public void empowerUser(String owner,String passw,String nome)throws NoUserException,AlreadyPoweredException;
    /*banna l'utente dall'accesso ai dati del proprietario*/
    public void depowerUser(String owner,String passw,String nome)throws NoUserException,AlreadyWeakException;

    public E get(String name, String passw, E data, String owner)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;

}