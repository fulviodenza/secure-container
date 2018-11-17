import java.util.Iterator;
import Exceptions.*;
/*
* Typical element: {<u1,l1>,...<un,ln>}
* */
public interface SecureDataContainer<E> {

    // Crea l’identità un nuovo utente della collezione
    /*
    * REQUIRES:Id != null && passw != null && Id != "" && passw != "" && forall(x) (appartenenti a this) ==> x.getName != Id
    * MODIFIES: this
    * EFFECTS: Aggiunge un utente a this
    * THROWS: NullPointerException se (Id || passw) == null (unchecked), IllegalArgumentException se (Id || passw) == "" (unchecked), DoubleUserExceptions se esiste già un user con quell'Id (checked)
    * */
    public void createUser(String Id, String passw)throws DoubleUserException;
    //Rimuove l'utente dalla collezione
    /*
     * REQUIRES:Id != null && passw != null && Id != "" && passw != "" && Esiste(x) (appartenenti a this) ==> x.getName == Id
     * MODIFIES: this
     * EFFECTS: Rimuove un utente a this
     * THROWS: NullPointerException se (Id || passw) == null (unchecked), IllegalArgumentException se (Id || passw) == "" (unchecked), NoUserException se non esiste un user con quell'Id (checked)
     * */
    public void RemoveUser(String Id, String passw)throws NoUserException;
    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    /*
     * REQUIRES:Owner != null && passw != null && Owner != "" && passw != "" && Esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw
     * EFFECTS: restituisce il numero di elementi di un utente se le credenziali erano corrette
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserExceptions se le credenziali sono errate o non esiste l'utente (checked)
     * */
    public int getSize(String Owner, String passw)throws NoUserException;

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && Id != "" && passw != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw
     * MODIFIES: this
     * EFFECTS: Aggiunge un elemento all'utente Owner se le sue credenziali sono corrette e ritorna true, false altrimenti
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserExceptions se le credenziali sono errate o non esiste l'utente (checked)
     * */
    public boolean put(String Owner, String passw, E data)throws NoUserException;

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw && this.get(Owner).contains(data)
     * EFFECTS: Restituisce una copia del riferimento dell'elemento data se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste
     * */
    public E get(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw && this.get(Owner).contains(data)
     * MODIFIES: this
     * EFFECTS: Elimina data da this e restituisce una sua copia, se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste
     * */
    public E remove(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw && this.get(Owner).contains(data)
     * MODIFIES: this
     * EFFECTS: Copia in this l'elemento data, se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste
     * */
    public void copy(String Owner, String passw, E data)throws NoUserException,DataNotFoundException;

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && Other != null && data != null && Owner != "" && passw != "" && Other != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw && this.get(Owner).contains(data) && Other deve essere un utente in this != Owner && this.get(Owner).contains(data)
     * MODIFIES: this
     * EFFECTS: Copia l'elemento data,se esiste, dalla collezione nella collezione di un altro utente, se esiste
     * THROWS: NullPointerException se (Owner|| Other || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" || Owner == Other (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste
     * */
    public void share(String Owner, String passw, String Other, E data) throws NoUserException,DataNotFoundException;

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/

    /*
     * REQUIRES:Owner != null && passw != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x.getName == Owner && x.getPass == passw
     * MODIFIES: this
     * EFFECTS: Restituisce un iteratore che genera i dati in ordine di inserimento dell'utente, se esiste. Restituisce null se non ha elementi.
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user
     * */
    public Iterator<E> getIterator(String Owner, String passw)throws NoUserException;

}