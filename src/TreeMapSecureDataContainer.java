import java.util.*;
import exceptions.*;

/*
  IR: db != null e per ogni (u, l) in db si ha che u != null, l != null e
      per ogni (el) in l, el != null, el.ownedBy(u.getName()) == true
      Infine, per ogni (u, l) in db, non esiste (o, e) in db t.c u.getName() == o.getName()
  FA: f(users, elements) = { <user_1, elts_1>...<user_n, elts_n>},
      user_i è una istanza della classe User, mentre elts_i è una lista di Element<E>
*/
public class TreeMapSecureDataContainer<E> implements SecureDataContainer<E> {


    private TreeMap< User, List<Element<E> > > db;

    @SuppressWarnings("unchecked")
    // Assumendo che l'utente si sia autenticato
    private Element<E> findElt(User u, E data) {
        for(Element e : db.get(u)) {
            if(e.getEl().equals(data))
                return (Element<E>) e;
        }

        return null;
    }

    // Controlla se esiste un utente di nome who
    private boolean userAlreadyPresent(String who) {
        for(User u : db.keySet()) {
            if(u.getUserName().equals(who))
                return true;
        }

        return false;
    }

    public TreeMapSecureDataContainer() {
        db = new TreeMap<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException, IllegalArgumentException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        if(userAlreadyPresent(Id)) throw new UserAlreadyPresentException(Id);

        User u = new User(Id, passw);
        db.put(u, new ArrayList<>());
    }

    @Override
    public void removeUser(String Id, String passw) throws InvalidCredentialsException {
        if(Id == null || passw == null) throw new NullPointerException();

        User u = new User(Id, passw);
        if(!db.containsKey(u)) throw new InvalidCredentialsException();

        db.remove(u);
    }

    @Override
    public int getSize(String Owner, String passw) throws InvalidCredentialsException {
        if(Owner == null || passw == null) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(!db.containsKey(u)) throw new InvalidCredentialsException();

        int count = db.get(u).size();
        for( User other : db.keySet() ) {
            if(!other.equals(u)) {
                List<Element<E>> userList = db.get(other);
                for(Element e : userList ) {
                    if(e.canBeAccessedBy(Owner)) {
                        count ++;
                    }
                }
            }
        }

        return count;
    }

    @Override
    public int getOwnedSize(String Owner, String passw) throws InvalidCredentialsException {
        if(Owner == null || passw == null) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(!db.containsKey(u)) throw new InvalidCredentialsException();

        return db.get(u).size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> e = new Element<>(data, Owner);
            List<Element<E>> userData = db.get(u);
            if(!userData.contains(e)) {
                userData.add(e);
                return true;
            }
        }

        return false;
    }

    // C'è un modo per renderlo più carino?
    @SuppressWarnings("unchecked")
    @Override
    public E get(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            // Bisogna controllare ogni elemento, e non solo quelli posseduti direttamente dall'utente
            for( User other : db.keySet() ) {
                List<Element<E>> userList = db.get(other);
                for(Element e : userList ) {
                    if(e.canBeAccessedBy(Owner)) return (E) e.getEl();
                }
            }

        }
        return null;
    }

    @Override
    public E getInOwned(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> elt = findElt(u, data);
            if(elt != null)
                return elt.getEl();
        }
        return null;
    }

    @Override
    public E remove(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> el = new Element<>(data, Owner);
            List<Element<E>> userData = db.get(u);
            int index = userData.indexOf(el);
            if(index != -1) {
                E backup = userData.get( index ).getEl();
                userData.remove(index);
                return backup;
            }
        }

        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementNotPresentException {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(!db.containsKey(u)) throw new InvalidCredentialsException();

        Element<E> el = new Element<>(data, Owner);
        List<Element<E>> userData = db.get(u);
        if (!userData.contains(el)) throw new ElementNotPresentException();

        userData.add(el);

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws  InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                UserAlreadyAllowedException {
        if (Owner == null || passw == null || Other == null || data == null ) throw new NullPointerException();
        if (!userAlreadyPresent(Other)) throw new UserNotPresentException();

        User u = new User(Owner, passw);
        if (!db.containsKey(u)) throw new InvalidCredentialsException();

        Element e = findElt(u, data);
        if(e == null) throw new UserNotAllowedException();

        e.allowUser(Other);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
        if (Owner == null || passw == null ) throw new NullPointerException();
        User u = new User(Owner, passw);
        if (!db.containsKey(u)) throw new InvalidCredentialsException();

        // Bisogna iterare in ogni elemento, non solo in quelli associati direttamente all'utente
        List<E> initialList = new ArrayList<>(db.get(u).size());
        for( User other : db.keySet() ) {
            List<Element<E>> userList = db.get(other);
            for(Element e : userList ) {
                if(e.canBeAccessedBy(Owner))  initialList.add((E) e.getEl());
            }
        }
        return Collections.unmodifiableList(initialList).iterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> getOwnedIterator(String Owner, String passw) throws InvalidCredentialsException {
        if (Owner == null || passw == null ) throw new NullPointerException();
        User u = new User(Owner, passw);
        if (!db.containsKey(u)) throw new InvalidCredentialsException();

        // Bisogna iterare in ogni elemento, non solo in quelli associati direttamente all'utente
        List<E> initialList = new ArrayList<>(db.get(u).size());
        for( Element e : db.get(u)) {
            initialList.add( (E) e.getEl() );
        }
        return Collections.unmodifiableList(initialList).iterator();
    }
}
