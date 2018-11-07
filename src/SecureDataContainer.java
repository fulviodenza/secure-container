import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

public class SecureDataContainer<E> implements SecureDataContainerInterface {

    private HashMap<User, ArrayList<E>> data;

    public SecureDataContainer() {
      data = new HashMap<>();
    }

    @Override
    public void createUser(String Id, String passw) {}

    @Override
    public int getSize(String Owner, String passw) {
        return 0;
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
