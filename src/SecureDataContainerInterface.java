import exceptions.*;

import java.util.Iterator;

public interface SecureDataContainerInterface<E>{

    //Typical Element: {<User_0, [e_0,...,e_r-1]_0>,<User_2, [e_0,...,e_s-1]>,...,<User_n-1, [e_0,...,e_u-1]>}

    // Crea l’identità un nuovo utente della collezione

    /*
    REQUIRES:Id != null && passw != null, altrimenti throws NullPointerException
              Id != "" && passw != "", altrimenti throws InvalidArgumentException
              Id in this ==> UserAlreadyPresent
    EFFECTS :Aggiunge un nuovo utente a this
    MODIFY  :this
     */

    public void createUser(String Id, String passw) throws UserAlreadyPresent; //IMPLEMENTATA

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/

    /*
    REQUIRES:Owner != null && passw != null, altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
    EFFECTS :Restituisce il numero di elementi a cui l'utente Owner ha accesso, se il login
             fallisce restituisce 0
     */
    public void removeUser(String Id, String passw) throws NoUserException;

    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException; //IMPLEMENTATA

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null, data != null altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
    EFFECTS :Restituisce True se Owner appartiene a this, False altrimenti
    MODIFY  :this(Owner,passw) = this(Owner,passw) U (data)
     */

    public boolean put(String Owner, String passw, E data) throws NoUserException; //IMPLEMENTATA

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null && data != null, altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
             La coppia (Owner,passw) appartiene all' insieme delle chiavi di this, altrimenti lancia NoUserException
             Exists (this.get(user).(this.get(user) == (data)))
    EFFECTS :Restituisce una copia del valore del dato nella collezione
    */

    public E get(String Owner, String passw, E data) throws NoUserException, NoDataException; //IMPLMEMENTATA

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null && data != null, altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
             La coppia (Owner,passw) appartiene all' insieme delle chiavi di this, altrimenti lancia NoUserException
             Exists (this.get(user).(this.get(user) == (data)))
    EFFECTS :this.get(user) = this.get(user) \ (data)
    MODIFY  :this
    */

    public E remove(String Owner, String passw, E data) throws NoUserException; //IMPLEMENTATA

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null && data != null, altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
             La coppia (Owner,passw) appartiene all' insieme delle chiavi di this, altrimenti lancia NoUserException
             Forall (this.get(user).(this.get(user) != (data))), altrimenti lancia DataAlreadyPresentException
    EFFECTS :Crea una copia del dato nella collezione
    */

    public void copy(String Owner, String passw, Object data) throws NoUserException, DataAlreadyPresentException; //IMPLEMENTATA

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null && data != null && Other != null, altrimenti throws NullPointerException
             Owner != "" && passw != "" && Other != "",  altrimenti throws InvalidArgumentException
             La coppia (Owner,passw) appartiene all' insieme delle chiavi di this, altrimenti lancia NoUserException
             Other appartiene all'insieme degli utenti.getId() di this, altrimenti lancia NoUserException
             Exists (this.get(Other).(this.get(Other) == (data))) ==> DataAlreadyPresentException
    EFFECTS :Se data esiste in this(Owner,passw), copia data di this(Owner,passw) in this(Other)
    MODIFY  :this(Other)
    */

    public void share(String Owner, String passw, String Other, E data) throws NoUserException,NoDataException, DataAlreadyPresentException; //IMPLEMENTATA

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/

    /*
    REQUIRES:Owner != null && passw != null, altrimenti throws NullPointerException
             Owner != "" && passw != "", altrimenti throws InvalidArgumentException
             La coppia (Owner,passw) appartiene all' insieme delle chiavi di this, altrimenti lancia NoUserException
    EFFECTS :Restituisce un Iteratore di tipo generico che genera tutti i dati di this(Owner,passw)
    MODIFY  :Iterator
    */

    public Iterator<E> getIterator(String Owner, String passw) throws NoUserException;
}
