import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

public class SecureDataContainer<E> implements SecureDataContainerInterface<E> {

    private Map<KeyCouple,Vector<E>> DBUsers;

    public SecureDataContainer(){
        DBUsers = new HashMap<>();
    }

    @Override
    public void createUser(String Id, String passw) {}

    @Override
    public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        return DBUsers.size();
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            Vector<E> aux = DBUsers.get(user);
            return aux.add(data);
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }


    @Override
    public E get(String Owner, String passw, Object data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                return DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste il dato richiesto");
        }
    }

    @Override
    public E remove(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                DBUsers.get(user).remove(DBUsers.get(user).indexOf(data));
                return data;
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste il dato richiesto");
        }
    }
    @Override
    public void copy(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                DBUsers.get(user).add(data);
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste il dato richiesto");
        }
    }


    @Override
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{}

    @Override
    public Iterator getIterator(String Owner, String passw) {
        return null;
    }
}