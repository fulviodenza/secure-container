import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import exceptions.*;
/*
  IR: users, elements != null per ogni e in elements si ha che esiste u in users
      t.c e.ownedBy(u) == true;
      per ogni e in elements, per ogni a in e.allowedUsers, si ha che esiste u
      in users t.c u.getUserName() == e
      Infine, in users non ci possono essere utenti con lo stesso username.
  FA: f(users, elements) = { <(nome1, pass1), ..., (nomen, passn)>,
      <(owner1, el1, allowedUsers1), ..., (ownern, eln, allowedUsersn)>} dove
      nomei, passi ed owneri sono stringhe non vuote,
      allowedUsersi sono liste di stringhe non vuote
*/
public class ListSecureDataContainer<E> implements SecureDataContainer<E> {

    private List<User> users;
    private List<Element<E>> elements;

    //Controlla se l'utente us è presente tra gli utenti di this
    private boolean userAlreadyPresent(String us) {
      for(User u : users) {
        if(u.getUserName().equals(us)) return true;
      }
      return false;
    }

    /*
    Restituisce il contenitore di el se è presente un utente che lo possiede,
    altrimenti restituisce null
    */
    private Element getElt(E el, String owner) {
      for(Element e : elements) {
        if(e.ownedBy(owner) && e.getEl().equals(el))
          return e;
      }

      return null;
    }

    public ListSecureDataContainer() {
      users = new ArrayList<>();
      elements = new ArrayList<>();
    }

    // Crea l’identità un nuovo utente della collezione
    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.equals("") || passw.equals("") ) throw new IllegalArgumentException();

        //Per evitare che si registrino due utenti con lo stesso nome
        if(userAlreadyPresent(Id)) throw new UserAlreadyPresentException();

        User u = new User(Id, passw);
        users.add(u);

    }

    // Crea l’identità un nuovo utente della collezione
    @Override
    public void removeUser(String Id, String passw) throws InvalidCredentialsException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.equals("") || passw.equals("") ) throw new IllegalArgumentException();

        User u = new User(Id, passw);
        //Per evitare che si registrino due utenti con lo stesso nome
        if(!users.contains(u)) throw new InvalidCredentialsException();

        users.remove(u);

        ArrayList<Element> toBeRemoved = new ArrayList<Element>();

        for(Element el : elements ) {
            if(el.ownedBy(Id)) {
                toBeRemoved.add( el );
            }
        }

        elements.removeAll(toBeRemoved);

    }

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione, contanto anche gli elementi che l'utente è autorizzato a vedere,
    e non solo quelli di cui è il proprietario.*/
    @Override
    public int getSize(String Owner, String passw) throws InvalidCredentialsException {
      if(Owner == null || passw == null) throw new NullPointerException();
      User u = new User(Owner, passw);

      // Meccanismo di login nella SecureDataContainer usando User.equals()
      if(!users.contains(u)) throw new InvalidCredentialsException();

      int counter = 0;
      for(Element e : elements) {
        if(e.canBeAccessedBy(Owner)) {
          counter ++;
        }
      }

      return counter;
    }

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public boolean put(String Owner, String passw, E data) {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();

      User u = new User(Owner, passw);
      if(users.contains(u)) {
        Element toBeAdded = new Element<E>(data, Owner);
        //Per evitare di reinserire più copie dello stesso elemento
        if(!elements.contains(toBeAdded)) {
          elements.add(toBeAdded);
          return true;
        }
      }

      return false;
    }

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public E get(String Owner, String passw, E data) {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(users.contains(u)) {
        for(Element e : elements) {
            if(e.getEl().equals(data) && e.canBeAccessedBy(Owner))
                return (E) e.getEl();
        }
      }
      return null;
    }

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public E remove(String Owner, String passw, E data) {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      E el = null;
      if(users.contains(u)) {
        Element elementToRemove = getElt(data, Owner);
        if(elementToRemove != null)
          el = (E) elementToRemove.getEl();
          elements.remove(elementToRemove);
        }
      return el;
    }

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementNotPresentException
    {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(users.contains(u)) {
        //Cerca l'elemento per vedere se copiarlo o no
        Element toBeCopied = getElt(data, Owner);
        if(toBeCopied != null) {
          elements.add( new Element<>(data, Owner) );
        } else throw new ElementNotPresentException();

      } else throw new InvalidCredentialsException();
    }

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    @Override
    public void share(String Owner, String passw, String Other, E data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                UserAlreadyAllowedException
    {
      if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
      if(!userAlreadyPresent(Other)) throw new UserNotPresentException();

      User u = new User(Owner, passw);
      if(users.contains(u)) {
        Element e = getElt(data, Owner);
        // Per com'è impostato getElt, se data non c'è o Owner non possiede data,
        // restituisce null
        if(e == null) throw new UserNotAllowedException();
        e.allowUser(Other);
        return;
      }

      // Se raggiungiamo questo punto, il login è fallito
        throw new InvalidCredentialsException();
    }

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
      if(Owner == null || passw == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(users.contains(u)) {
        List<E> userElements = new ArrayList<>();

        for(Element e : elements) {
          if(e.canBeAccessedBy(Owner))
            userElements.add( (E) e.getEl());
        }
        // unmodifiableList restituisce una lista il cui metodo remove lancia
        // UnsupportedOperationException()
        List<E> unmodifiable = Collections.unmodifiableList(userElements);
        return unmodifiable.iterator();
      }
      //Se raggiungiamo questa parte, vuol dire che il login è fallito
      throw new InvalidCredentialsException();
    }
}
