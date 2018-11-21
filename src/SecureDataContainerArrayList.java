import exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class SecureDataContainerArrayList<E> implements SecureDataContainerInterface<E> {

    private ArrayList<ArrayListElement> DataBase = new ArrayList<>();
    private Vector<KeyCouple> Users = new Vector<>();

    private boolean existsUser(String user){
        for(KeyCouple u: Users){
            if(u.getIdUser().equals(user)){
                return true;
            }
        }
        return false;
    }

    private boolean existsData(E data, String Owner, String passw){
        KeyCouple user = new KeyCouple(Owner,passw);
        ArrayListElement el = new ArrayListElement(data, user);

        for(int i = 0; i < DataBase.size(); i++){
            if(DataBase.get(DataBase.indexOf(el)).getData().equals(data)){
                return true;
            }
        }
        return false;
    }

    private void removeAllElements(String user){
        for (int i = 0; i < DataBase.size(); i++) {
            if(DataBase.get(i).returnOwner().equals(user)){
                DataBase.remove(i);
            }
        }
    }

    @Override
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        if(existsUser(Id)) throw new UserAlreadyPresentException(Id);

        if(!existsUser(Id)) {
            KeyCouple u = new KeyCouple(Id, passw);
            Users.add(u);
        }else{
            throw new UserAlreadyPresentException();
        }
    }

    public void removeUser(String Id, String passw) throws NoUserException {

        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple u = new KeyCouple(Id, passw);
        if(Users.contains(u)){
            Users.remove(u);
            removeAllElements(Id);
        }else{
            throw new NoUserException("No user");
        }
    }
    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException, NoUserException {
        if(Owner == null && passw == null){throw new NullPointerException();}
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple u = new KeyCouple(Owner,passw);
        int count = 0;
        if(Users.contains(u)){
            for(int i = 0; i < DataBase.size(); i++){
                if(DataBase.get(i).returnOwner().equals(Owner)){
                    count++;
                }
            }
            return count;
        }else {
            throw new NoUserException();
        }
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws NoUserException {
        if(Owner == null && passw == null){throw new NullPointerException();}
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        ArrayListElement newEl = new ArrayListElement(data,user);
        if(Users.contains(user)){
            return DataBase.add(newEl);
        }else {
            throw new NoUserException();
        }
    }

    @Override
    public E get(String Owner, String passw, E data) throws NoUserException, NoDataException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        E coniglio = null;
        KeyCouple user = new KeyCouple(Owner,passw);
        if(Users.contains(user)){
            for (ArrayListElement a: DataBase) {
                if (a.returnOwner().equals(Owner) && data.equals(a.getData())) {
                    coniglio = (E) a.getData();
                }
            }
        }else{
            throw new NoUserException();
        }
        if(coniglio == null){
            throw new NoDataException();
        }else{
            return coniglio;
        }
    }

    @Override
    public E remove(String Owner, String passw, E data) throws NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        E removedEl = null;
        KeyCouple user = new KeyCouple(Owner,passw);
        ArrayListElement el = new ArrayListElement(data,user);

        if(Users.contains(user)){
            Iterator<ArrayListElement> iter = DataBase.iterator();
            while(iter.hasNext()){
                if(iter.next().getData().equals(data) && iter.next().returnOwner().equals(Owner)){
                    //removedEl = iter.next().getData();
                    //***************ERRORE*******************
                }
            }
        }else {
            throw new NoUserException();
        }
        return removedEl;
    }

    public void copy(String Owner, String passw, E data) throws NoUserException, DataAlreadyPresentException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        E dromedario;
        KeyCouple user = new KeyCouple(Owner,passw);
        if(Users.contains(user)){
            dromedario = data;
            for(ArrayListElement e: DataBase){
                if(e.getData().equals(data)){
                    throw new DataAlreadyPresentException();
                }
            }
            ArrayListElement copyEl = new ArrayListElement(dromedario,user);
            DataBase.add(copyEl);
        }else{
            throw new NoUserException();
        }
    }

    public void share(String Owner, String passw, String Other, E data) throws NoUserException, NoDataException {
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner, passw);
        KeyCouple userShare = new KeyCouple(Other,null);
        ArrayListElement el = new ArrayListElement(data,user);
        int iter = 0;
        if(Users.contains(user)){
            while(!DataBase.get(DataBase.indexOf(el)).getData().equals(data) && !DataBase.get(DataBase.indexOf(el)).returnOwner().equals(Owner) && iter < DataBase.size()){
                iter++;
            }
            DataBase.get(iter).addUsers(userShare);
        }else{
            throw new NoUserException();
        }
    }

    public Iterator<E> getIterator(String Owner, String passw) throws NoUserException {
        return null;
    }
}
