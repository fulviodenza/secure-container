import java.util.Iterator;
import exceptions.*;

/* Typical element: {<user1, elts1>...<usern, eltsn>}*/
public interface SecureDataContainer<E> {

    // Crea l’identità un nuovo utente della collezione
    /* @effects: Aggiunge un nuovo utente a this
     * @modifies: this
     * @requires: Id, passw != null, altrimenti lancia NullPointerException
     *            Id, passw != "" (non vuote), altrimenti lancia una InvalidArgumentException
     *            Id non in this, altrimenti lancia una UserAlreadyPresentException
     */
    void createUser(String Id, String passw) throws UserAlreadyPresentException, IllegalArgumentException;

    // Rimuove l’identità dalla collezione, assieme a tutti gli elementi che possiede
    /* @effects: Rimuove (Id, passw) da this, elts = elts / { v : v.owner = Id }
     * @modifies: this
     * @requires: Id, passw != null, altrimenti lancia NullPointerException
     *            Id, passw != "", altrimenti lancia una InvalidArgumentException
     */
    void removeUser(String Id, String passw) throws InvalidCredentialsException;

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    /* @effects: Restituisce il numero di elementi contenuti da un utente (compresi quelli condivisi)
     * @requires: Owner, passw != null, altrimenti lancia NullPointerException
     * 		      Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
     * 		       lancia InvalidCredentialsException */
    int getSize(String Owner, String passw) throws InvalidCredentialsException;

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: Restituisce true se l'utente è presente in this (Di conseguenza l'inserimento è riuscito),
     * 		 Altrimenti restituisce false
     * @requires: Owner, passw, data != null, altrimenti lancia NullPointerException
     * @modifies: this(owner, passw) = this(owner,pass) + data */
    boolean put(String Owner, String passw, E data);

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità (Cercando anche tra gli elementi condivisi)*/
    /* @effects: Restituisce una copia di data se il login ha esito positivo, altrimenti restituisce null
     * @requires: Owner, passw, data != null, altrimenti lancia NullPointerException
     */
    E get(String Owner, String passw, E data);

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: Rimuove data da this(owner, passw) e restituisce l'elemento rimosso
    *  @requires: Owner, passw, data != null, altrimenti lancia una NullPointerException
    *  @modifies: this
    */
    E remove(String Owner, String passw, E data);

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: copia data in this(owner, passw) se è presente tra gli elementi di this(owner, passw)
    *  @requires: Owner, passw, data != null, altrimenti ti sputa in faccia una NullPointerException
    *               data deve appartenere a this(OwOner, passw), altrimenti lancia ElementNotPresent
    * 		      Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		        lancia InvalidCredentialsException.
    * @modifies: this
    */
    void copy(String Owner, String passw, E data) throws InvalidCredentialsException, ElementNotPresentException;

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    /* @effects: copia data di this(owner,passw) con this(Other) se è presente tra gli elementi di this(owner, passw)
    *  @requires: Owner, passw, Other, data != null, altrimenti lancia  NullPointerException (unchecked)
    *               data deve appartenere a this(Owner, passw) (considerando solo gli elementi direttamente posseduti),
    *               altrimenti lancia UserNotAllowedException (checked)
    * 		      Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		        lancia InvalidCredentialsException. (checked)
    *             Se Other non è un utente in this, lancia una UserNotPresentException (checked)
    *             Se this(Other) è già autorizzato ad accedere a data, lancia una UserAlreadyAllowedException (checked)
    * @modifies: this(Other)
    */
    void share(String Owner, String passw, String Other, E data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                UserAlreadyAllowedException ;

    /* Restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario (compresi quelli condivisi)
    se vengono rispettati i controlli di identità*/
    /*@effects: Restituisce un iteratore degli elementi di this(Owner, passw)
     * 		      Owner, passw devono essere le credenziali corrette di un utente, altrimenti
     * 		      lancia InvalidCredentialsException (checked)
     */
    Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException;

    /* @Effects:  Restituisce un iteratore (senza remove) che genera tutti i dati
     *              dell’utente in ordine arbitrario (esclusi quelli condivisi)
     *              se vengono rispettati i controlli di identità
     * @Requires: Owner, passw devono essere le credenziali corrette di un utente, altrimenti
     * 		        lancia InvalidCredentialsException (checked)
     */
    Iterator<E> getOwnedIterator(String Owner, String passw) throws InvalidCredentialsException;
}
