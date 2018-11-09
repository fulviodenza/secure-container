import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*

  IR: data != null && per ogni <u,l> in data si ha che u != null e l != null
      per ogni u = <user, elts> in this non esiste o = <user, elts> t.c
      u.getUserName() == o.getUserName() &&
      Per ogni u = <user, elts> in this, per ogni e, u in u.elts, con e != u,
      si ha che u != e.
  FA: f(data) = {<user1, elts1>...<usern, eltsn>} dove usern è un User e eltsn è
              una lista di elementi E

*/
public class ListSecureDataContainer<E> implements SecureDataContainer<E> {

    private class Element {
      private String owner = null;
      private E el = null;
      private List<String> allowedUsers;
    }

    private List<User> users;
    private List<Element> elements;

    public ListSecureDataContainer() {
      users = new ArrayList<>();
      elements = new ArrayList<>();
    }

    // Crea l’identità un nuovo utente della collezione
    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.equals("") || passw.equals("") ) throw new IllegalArgumentException();

        User u = new User(Id, passw);
        if(users.contains(u)) throw new UserAlreadyPresentException();

        users.add(u);

    }

    /* Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    @Override
    public int getSize(String Owner, String passw) throws InvalidCredentialsException {
      if(Owner == null || passw == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(!users.contains(u)) throw new InvalidCredentialsException();

      int counter = 0;
      for(Element e : elements) {
        if(e.owner.equals(Owner) || e.allowedUsers.contains(Owner)) {
          count ++;
        }
      }

      return counter;
    }

    /*Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public boolean put(String Owner, String passw, E data) {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();
    }

    /* Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public E get(String Owner, String passw, E data) {}

    /* Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public E remove(String Owner, String passw, E data) {}

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementAlreadyPresentException {}

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    @Override
    public void share(String Owner, String passw, String Other, E data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                ElementAlreadyPresentException {}

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
      @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException,
                                                                      ElementAlreadyPresentException,
                                                                      UserNotPresentException {}
}
