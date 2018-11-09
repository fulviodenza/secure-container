import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import exceptions.*;
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

      public Element(E el, String owner) {
        if(el == null || owner == null) throw new NullPointerException();
        this.el = el;
        this.owner = owner;
        allowedUsers = new ArrayList<String>();
      }

      public E getEl() {
        return el;
      }

      public boolean ownedBy(String who) {
        return owner.equals(who);
      }

      @Override
      public boolean equals(Object other) {
        if(! (other instanceof ListSecureDataContainer.Element)) throw new IllegalArgumentException();
        Element o = (Element)other;
        return (o.el.equals( this.el ) && o.owner.equals( this.owner ));
      }

      public void allowUser(String other) throws UserAlreadyAllowedException {
        if(allowedUsers.contains(other)) throw new UserAlreadyAllowedException();
        allowedUsers.add(other);
      }

      public void denyUser(String other) throws UserNotAllowedException {
        if(!allowedUsers.contains(other)) throw new UserNotAllowedException();
        allowedUsers.remove(other);
      }

      public Element copy() {
        Element c = new Element(el, owner);
        c.allowedUsers.addAll(this.allowedUsers);
        return c;
      }

      public boolean canBeAccessedBy(String who) {
        return ownedBy(who) || allowedUsers.contains(who);
      }
    }

    private List<User> users;
    private List<Element> elements;

    private boolean userAlreadyPresent(String us) {
      for(User u : users) {
        if(u.getUserName().equals(us)) return true;
      }
      return false;
    }

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

        if(userAlreadyPresent(Id)) throw new UserAlreadyPresentException();

        User u = new User(Id, passw);
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
        Element toBeAdded = new Element(data, Owner);
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
      E el = null;
      if(users.contains(u)) {
        Element elementToFind = new Element(data, Owner);
        int ind = elements.indexOf(elementToFind);
        if(ind >= 0) el = elements.get(ind).el;
      }
      return el;
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
          el = elementToRemove.getEl();
          elements.remove(elementToRemove);
        }
      return el;
    }

    /* Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementAlreadyPresentException
    {
      if(Owner == null || passw == null || data == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(users.contains(u)) {
        Element toBeCopied = getElt(data, Owner);
        if(toBeCopied != null) {
          elements.add(toBeCopied.copy());
        }
      } else throw new InvalidCredentialsException();
    }

    /* Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    @Override
    public void share(String Owner, String passw, String Other, E data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                ElementAlreadyPresentException
    {
      if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
      if(!userAlreadyPresent(Other)) throw new UserNotPresentException();

      User u = new User(Owner, passw);
      if(users.contains(u)) {
        Element e = getElt(data, Owner);
        if(e == null) throw new UserNotAllowedException();
        try {
          e.allowUser(Other);
        } catch (UserAlreadyAllowedException ex ) {

        }
      }
    }

    /* restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
      @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
      if(Owner == null || passw == null) throw new NullPointerException();
      User u = new User(Owner, passw);
      if(users.contains(u)) {
        List<E> userElements = new ArrayList<E>();
        for(Element e : elements) {
          if(e.canBeAccessedBy(Owner))
            userElements.add(e.getEl());
        }

        List<E> unmodifiable = Collections.unmodifiableList(userElements);
        return unmodifiable.iterator();
      }
      throw new InvalidCredentialsException();
    }
}
