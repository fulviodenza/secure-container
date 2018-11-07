import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
public class SecureDataContainer<E> implements SecureDataContainerInterface {

    private Map<KeyCouple,Vector> DBUsers;

    public SecureDataContainer(){
        this.DBUsers = new HashMap<>();
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
    public boolean put(String Owner, String passw, Object data) throws NullPointerException,IllegalArgumentException,NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            return DBUsers.get(user).add(data);
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    @Override
    public Object get(String Owner, String passw, Object data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                Object aux = data;
                return aux;
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    @Override
    public Object remove(String Owner, String passw, Object data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        return null;
    }

    @Override
    public void copy(String Owner, String passw, Object data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{}

    @Override
    public void share(String Owner, String passw, String Other, Object data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{}

    @Override
    public Iterator getIterator(String Owner, String passw) {
        return null;
    }
}