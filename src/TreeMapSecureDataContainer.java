import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.*;

public class TreeMapSecureDataContainer<E> implements SecureDataContainer<E> {


    private TreeMap< User, List<Element<E> > > db;

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
        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws InvalidCredentialsException,
                                                                ElementAlreadyPresentException {

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws  InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                UserNotAllowedException,
                                                                                ElementAlreadyPresentException,
                                                                                UserAlreadyAllowedException {

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
        return null;
    }
}
