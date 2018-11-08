import java.util.Iterator;
import java.util.HashMap;
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
public class HashMapSecureDataContainer<E> implements SecureDataContainer<E> {


    private class Element<E> {
      private User owner = null;
      private List<User> allowedUsers;

    }
    private List<User> users;

    private boolean userPresent(String user ) {
      for(User u : storedElts.keySet()) {
        if(u.getUserName().equals(user)) {
          return true;
        }
      }

      return false;
    }

    public HashMapSecureDataContainer() {
      storedElts = new HashMap<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {
      if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
      if(userPresent(Id)) throw new UserAlreadyPresentException(Id);

      User u = new User(Id, passw);
      storedElts.put(u, new ArrayList<E>());
    }

    @Override
    public int getSize(String Owner, String passw) throws InvalidCredentialsException {
        User query = new User(Owner, passw);
        if(!storedElts.containsKey(query)) throw new InvalidCredentialsException();

        ArrayList<E> list = (ArrayList<E>)storedElts.get(query);
        return list.size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) {
      User query = new User(Owner, passw);
      if( !storedElts.containsKey(query) )
        return false;

      ArrayList<E> list = (ArrayList<E>)storedElts.get(query);
      list.add(data);
      return true;

    }

    @Override
    public E get(String Owner, String passw, Object data) {
      User u = new User(Owner, passw);
      if(storedElts.containsKey(u)) {
        List<E> userElts = storedElts.get(u);
        return userElts.get(userElts.indexOf(data));
      }
      return null;
    }

    @Override
    public E remove(String Owner, String passw, Object data) {
      User u = new User(Owner, passw);
      if(storedElts.containsKey(u)) {
        List<E> userElts = storedElts.get(u);
        E backup = userElts.get(userElts.indexOf(data));
        storedElts.get(u).remove(data);
        return backup;
      }
      return null;
    }

    @Override
    public void copy(String Owner, String passw, Object data) throws InvalidCredentialsException, ElementAlreadyPresentException {
      User u = new User(Owner, passw);
      if(data == null) throw new NullPointerException();
      if(storedElts.containsKey(u)) {
        List<E> userList = storedElts.get(u);
        if(userList.contains(data)) throw new ElementAlreadyPresentException();

        userList.add((E)data);
      } else {
        throw new InvalidCredentialsException();
      }
    }

    @Override
    public void share(String Owner, String passw, String Other, Object data) throws InvalidCredentialsException,
                                                                                UserNotPresentException,
                                                                                ElementAlreadyPresentException {
      User u = new User(Owner, passw);
      if( !storedElts.containsKey(u) ) throw new InvalidCredentialsException();
      if( !userPresent(Other) ) throw new UserNotPresentException(Other);
      if( !storedElts.get(u).contains(data) ) throw new IllegalArgumentException();

      for( User o : storedElts.keySet() ) {
        if( o.getUserName().equals(Other) ) {
          List<E> otherList = storedElts.get(o);
          if( otherList.contains(data) ) throw new ElementAlreadyPresentException();
          otherList.add((E)data);
          return;
        }
      }

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidCredentialsException {
        User u = new User(Owner, passw);
        if(storedElts.containsKey(u)) {
          List<E> immutableElts = Collections.unmodifiableList(storedElts.get(u));
          return immutableElts.iterator();
        } else {
          throw new InvalidCredentialsException();
        }
    }
}
