import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class SecureDataContainer<E> implements SecureDataContainerInterface {

    private Map<KeyCouple,Vector<E>> DBUsers;

    public SecureDataContainer(){
        this.DBUsers = new HashMap<>();

    }

    @Override
    public void createUser(String Id, String passw) throws NullPointerException, IllegalArgumentException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw  new IllegalArgumentException();
        KeyCouple IdPassw = new KeyCouple(Id,passw);
        DBUsers.put(IdPassw,null);

    }

    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        return DBUsers.size();

    }

    @Override
    public boolean put(String Owner, String passw, Object data) {
        return false;
    }

    @Override
    public Object get(String Owner, String passw, Object data) {
        return null;
    }

    @Override
    public Object remove(String Owner, String passw, Object data) {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, Object data) {}

    @Override
    public void share(String Owner, String passw, String Other, Object data) {}

    @Override
    public Iterator getIterator(String Owner, String passw) {
        return null;
    }
}
