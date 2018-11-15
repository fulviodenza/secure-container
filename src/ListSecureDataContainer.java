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


    /*Overview: Tipo contenitore del generico con aggiunge due informazioni: Il possessore dell'elemento
                e una lista di utenti autorizzati ad accedere al dato
                IR: owner != null, el != null e allowedUsers != null
                FA: a(el, owner) = {owner, el, allowedUsers} dove owner è la stringa che indica
                                  il proprietario dell'elemento el, e allowUser indica gli utenti autorizzati
                                  ad accedere al dato
    */
    private class Element {
      private String owner = null;
      private E el = null;
      private List<String> allowedUsers;


      /*
      REQUIRES: el, owner != null, altrimenti lancia NullPointerException (unchecked)
      EFFECTS: inizializza this.el, this.owner a (el, owner) e allowedUsers a una lista vuota
      */
      public Element(E el, String owner) {
        if(el == null || owner == null) throw new NullPointerException();
        this.el = el;
        this.owner = owner;
        allowedUsers = new ArrayList<String>();
      }

      /*EFFECTS: Restituisce l'elemento contenuto in this*/
      public E getEl() {
        return el;
      }

      /*EFFECTS: Restituisce true se who è il proprietario di this
        REQUIRES: who != null, altrimenti lancia NullPointerException (unchecked) */
      public boolean ownedBy(String who) {
        return who.equals(owner);
      }

      /*EFFECTS: Restituisce true se il possessore di other e this sono uguali e se
                 l'elemento in other è lo stesso di this (secondo la concezione di uguaglianza
                 di E)
        REQUIRES: other deve essere una istanza di Element, altrimenti lancia IllegalArgumentException (checked)
                  other non dev'essere null, altrimenti lancia NullPointerException (unchecked)
      */
      @Override
      public boolean equals(Object other) {
        if(! (other instanceof ListSecureDataContainer.Element)) throw new IllegalArgumentException();
        Element o = (Element) other;
        return (o.el.equals( this.el ) && o.owner.equals( this.owner ));
      }

      /*EFFECTS: Permette all'utente other di accedere ad El
        REQUIRES: other != null, altrimenti lancia NullPointerException (unchecked)
                  other non in allowedUsers, altrimenti lancia UserAlreadyAllowedException (checked)
        MODIFIES: this */
      public void allowUser(String other) throws UserAlreadyAllowedException {
        if(other == null) throw new NullPointerException();
        if(allowedUsers.contains(other)) throw new UserAlreadyAllowedException();
        allowedUsers.add(other);
      }

      /*EFFECTS: Blocca l'accesso al dato a other
        REQUIRES: other != null, altrimenti lancia NullPointerException (unchecked)
                  other in allowedUsers, altrimenti lancia UserNotAllowedException (checked)
        MODIFIES: this */
      public void denyUser(String other) throws UserNotAllowedException {
        if(other == null) throw new NullPointerException();
        if(!allowedUsers.contains(other)) throw new UserNotAllowedException();
        allowedUsers.remove(other);
      }

      /*EFFECTS: Crea una copia di this, cioè un nuovo Element c t.c c.owner = this.owner,
                c.el = this.el e per ogni u in c.allowedUsers, esiste o in t.allowedUsers t.c u = o
      */
      public Element copy() {
        Element c = new Element(el, owner);
        c.allowedUsers.addAll(this.allowedUsers);
        return c;
      }

      /*EFFECTS: Restituisce true who può accedere a this
        REQUIRES: who != null, altrimenti lancia (da parte di ownedBy) NullPointerException (unchecked)
      */
      public boolean canBeAccessedBy(String who) {
        return ownedBy(who) || allowedUsers.contains(who);
      }
    }

    private List<User> users;
    private List<Element> elements;

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
        Element toBeAdded = new Element(data, Owner);
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
        //Cerca l'elemento per vedere se copiarlo o no
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
                                                                                ElementAlreadyPresentException,
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
        // unmodifiableList restituisce una lista il cui metodo remove lancia
        // UnsupportedOperationException()
        List<E> unmodifiable = Collections.unmodifiableList(userElements);
        return unmodifiable.iterator();
      }
      //Se raggiungiamo questa parte, vuol dire che il login è fallito
      throw new InvalidCredentialsException();
    }
}
