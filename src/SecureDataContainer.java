import java.util.Iterator;

/*
* Typical element: {<u1,l1>,...<un,ln>}
* */
public interface SecureDataContainer<E> {

    // Crea l’identità un nuovo utente della collezione
    /*OVERVIEW: Crea un nuovo utente nella collezione*/
    /*
    * REQUIRES:Id != null && passw != null && Id != "" && passw != "" && forall(x) (appartenenti a this) ==> x != Id
    * MODIFIES: this
    * EFFECTS: Aggiunge un utente a this
    * THROWS: NullPointerException se (Id || passw) == null (unchecked), IllegalArgumentException se (Id || passw) == "" (unchecked), DoubleUserExceptions se esiste già un user con quell'Id (checked)
    * */
    public void createUser(String Id, String passw)throws DoubleUserException;

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    /*OVERVIEW: Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    /*
     * REQUIRES:Owner != null && passw != null && Owner != "" && passw != "" && Esiste(x) (appartenente a this) ==> x == Owner && Owner e passw devono essere corretti
     * EFFECTS: restituisce il numero di elementi di un utente se le credenziali erano corrette
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserExceptions se le credenziali sono errate o non esiste l'utente (checked)
     * */
    public int getSize(String Owner, String passw)throws NoUserException;

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*OVERVIEW: Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && Id != "" && passw != "" && esiste(x) (appartenente a this) ==> x == Owner
     * MODIFIES: this
     * EFFECTS: Aggiunge un elemento all'utente Owner se le sue credenziali sono corrette e ritorna true, false altrimenti
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked)
     * */
    public boolean put(String Owner, String passw, E data);

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*OVERVIEW: Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x == Owner && this.get(Owner).contains(data) || l'utente deve essere autorizzato
     * EFFECTS: Restituisce una copia del riferimento dell'elemento data se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste, NotAuthorizedException (checked) se non è autorizzato
     * */
    public E get(String Owner, String passw, E data)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*OVERVIEW: Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x == Owner && this.get(Owner).contains(data) || l'utente deve essere autorizzato
     * MODIFIES: this
     * EFFECTS: Elimina data da this e restituisce una sua copia, se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste, NotAuthorizedException (checked) se non è autorizzato
     * */
    public E remove(String Owner, String passw, E data)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*OVERVIEW: Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && data != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x == Owner && this.get(Owner).contains(data) || l'utente deve essere autorizzato
     * MODIFIES: this
     * EFFECTS: Copia in this l'elemento data, se esiste
     * THROWS: NullPointerException se (Owner || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user, DataNotFoundException (checked) se il dato non esiste, NotAuthorizedException (checked) se non è autorizzato
     * */
    public void copy(String Owner, String passw, E data)throws NoUserException,DataNotFoundException,NotAuthorizedUserException;

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    /*OVERVIEW: Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw && Other != null != null && data != null && Owner != "" && passw != "" && Other != "" && esiste(x) (appartenente a this) ==> x == Owner && this.get(Owner).contains(data) && Other deve essere un utente in this
     * MODIFIES: this
     * EFFECTS: Copia l'elemento data,se esiste, dalla collezione nella collezione di un altro utente, se esiste
     * THROWS: NullPointerException se (Owner|| Other || passw || data) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user
     * */
    public void share(String Owner, String passw, String Other, E data)throws NoUserException;

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
     /*OVERVIEW: restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    /*
     * REQUIRES:Owner != null && passw != null && Owner != "" && passw != "" && esiste(x) (appartenente a this) ==> x == Owner
     * MODIFIES: this
     * EFFECTS: Restituisce un iteratore che genera i dati in ordine di inserimento dell'utente, se esiste. Restituisce null se non ha elementi.
     * THROWS: NullPointerException se (Owner || passw) == null (unchecked), IllegalArgumentException se (Owner || passw) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user
     * */
    public Iterator<E> getIterator(String Owner, String passw)throws NoUserException;

    /*OVERVIEW: autorizza l'utente ad accedere ai dati del proprietario*/
    /*
     * REQUIRES:owner != null && passw != null && nome != null && owner != "" && passw != "" && nome != null&& esiste(x) (appartenente a this) ==> x == Owner  && nome deve essere un utente in this
     * MODIFIES: this
     * EFFECTS: aggiunge nome nella lista di utenti autorizzati di owner, se esiste
     * THROWS: NullPointerException se (owner || passw || nome) == null (unchecked), IllegalArgumentException se (owner || passw || nome) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user(owner) oppure se non esiste lo user (nome),AlreadyPoweredException (checked) se è già autorizzato
     * */
    public void empowerUser(String owner,String passw,String nome)throws NoUserException,AlreadyPoweredException;
    /*OVERVIEW: banna l'utente dall'accesso ai dati del proprietario*/
    /*
     * REQUIRES:owner != null && passw != null && nome != null && owner != "" && passw != "" && nome != null&& esiste(x) (appartenente a this) ==> x == Owner  && nome deve essere un utente in this
     * MODIFIES: this
     * EFFECTS: rimuove nome dalla lista di utenti autorizzati di owner, se esiste
     * THROWS: NullPointerException se (owner || passw || nome) == null (unchecked), IllegalArgumentException se (owner || passw || nome) == "" (unchecked), NoUserException (checked) se le credenziali sono errate o se non esiste l'user(owner) oppure se non esiste lo user (nome),AlreadyWeakException (checked) se è già autorizzato
     * */
    public void depowerUser(String owner,String passw,String nome)throws NoUserException,AlreadyWeakException;



}