import java.util.*;

import exceptions.*;

public class TreeMapSecureDataContainer<E> implements SecureDataContainer<E> {


    private TreeMap< User, List<Element<E> > > db;

    //Assumendo che l'utente si sia autenticato
    private Element<E> findElt(User u, E data) {
        for(Element e : db.get(u)) {
            if(e.getEl().equals(data))
                return e;
        }

        return null;
    }

    private boolean userAlreadyPresent(String who) {
        for(User u : db.keySet()) {
            if(u.getUserName().equals(who))
                return true;
        }

        return false;
    }

    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {
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

        return db.get(u).size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> e = new Element<E>(data, Owner);
            List<Element<E>> userData = db.get(u);
            if(!userData.contains(e)) {
                userData.add(e);
                return true;
            }
        }

        return false;
    }

    @Override
    public E get(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> el = new Element<E>(data, Owner);
            List<Element<E>> userData = db.get(u);
            if(userData.contains(el)) {
                return userData.get( userData.indexOf(el) ).getEl();
            }

        }
        return null;
    }

    @Override
    public E remove(String Owner, String passw, E data) {
        if(Owner == null || passw == null || data == null ) throw new NullPointerException();

        User u = new User(Owner, passw);
        if(db.containsKey(u)) {
            Element<E> el = new Element<E>(data, Owner);
            List<Element<E>> userData = db.get(u);
            if(userData.contains(el)) {
                E backup = userData.get( userData.indexOf(el) ).getEl();
                userData.remove(el);
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

        Element<E> el = new Element<E>(data, Owner);
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

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
        if (Owner == null || passw == null ) throw new NullPointerException();
        User u = new User(Owner, passw);
        if (!db.containsKey(u)) throw new InvalidCredentialsException();

        List<E> initialList = new ArrayList<>(db.get(u).size());
        for(Element e : db.get(u)) {
            initialList.add( (E) e.getEl() );
        }
        return Collections.unmodifiableList(initialList).iterator();
    }
}
