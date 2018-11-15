import java.util.Iterator;
import exceptions.*;

/* Typical element: {<user1, elts1>...<usern, eltsn>}*/
public interface SecureDataContainer<E> {

    // Crea l’identità un nuovo utente della collezione
    /* @effects: Aggiunge un nuovo utente a this
     * @modifies: this
     * @requires: Id, passw != null, altrimenti lancia NullPointerException
     *            Id, passw != "", altrimenti lancia una InvalidArgumentException
  *               Id non in this, altrimenti lancia una UserAlreadyPresentException
     */
    public void createUser(String Id, String passw) throws UserAlreadyPresentException;

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    /* @effects: Restituisce il numero di elementi contenuti da un utente, 0 se il login fallisce
     * @requires: Owner, passw != null, altrimenti spara una NullPointerException
     * 		       Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
     * 		       ti odia a vita e ti da come motivazione InvalidCredentialsException */
    public int getSize(String Owner, String passw) throws InvalidCredentialsException;

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: Restituisce true se l'utente è presente in this (Di conseguenza l'inserimento è riuscito),
     * 		 Altrimenti restituisce false
     * @requires: Owner, passw, data != null, altrimenti ti porge una NullPointerException
     * @modifies: this(owner, passw) = this(owner,pass) + data */
    public boolean put(String Owner, String passw, E data);

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: Restituisce una copia di data se il login ha esito positivo, altrimenti ti chiava null
     * @requires: Owner, passw, data != null, altrimenti lancia NullPointerException
     */
    public E get(String Owner, String passw, E data);

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: Rimuove data da this(owner, passw) e restituisce l'elemento rimosso
    *  @requires: Owner, passw, data != null, altrimenti ti colpisce mortalmente con una NullPointerException
    *             data deve appartenere a this(owner, passw), altrimenti owo he throws a sexy ElementNotInListException
    * 		        Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		        ti insulta con una InvalidCredentialsException
    * @modifies: this
    */
    public E remove(String Owner, String passw, E data);

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /* @effects: copia data in this(owner, passw) se è presente tra gli elementi di this(owner, passw)
    *  @requires: Owner, passw, data != null, altrimenti ti sputa in faccia una NullPointerException
    *             data deve appartenere a this(OwOner, passw), altrimenti owo he throws a sexy ElementNotInListException
    * 		        Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		        ti regala una InvalidCredentialsException. Che gentile <3
    *             Se l'utente possiede già data, ti prende a schiaffi e ti restituisce ElementAlreadyPresentException
    * @modifies: this
    */
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException, ElementAlreadyPresentException;

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    /* @effects: copia data di this(owner,passw) con this(Other) se è presente tra gli elementi di this(owner, passw)
    *  @requires: Owner, passw, Other, data != null, altrimenti ti sputa in faccia una NullPointerException
    *             data deve appartenere a this(OwOner, passw), altrimenti owo he throws a sexy ElementNotInListException
    * 		        Inoltre Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		        ti regala con una InvalidCredentialsException. Che gentile <3
    *             Se Other non è un utente in this, lancia una UserNotPresentException. Commento troppo normale, eh?
    *             Se this(Other) contiene già data, lancia una ElementAlreadyPresentException. Anche questo, neh?
    *             Se this(Other) è già autorizzato ad acceder a data, lancia una UserAlreadyAllowedException. Anche questo, neh?
    * @modifies: this(Other)
    */
    public void share(String Owner, String passw, String Other, E data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                ElementAlreadyPresentException.
                                                                                UserAlreadyAllowedException ;

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    /*@effects: Restituisce un iteratore degli elementi di this(Owner, passw)
    * 		      Owner, passw devono essere le credenziali corrette di un utente, altrimenti
    * 		      ti urla contro "InvalidCredentialsException".
    */
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException;
}
