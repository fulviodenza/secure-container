import java.util.*;

import exceptions.*;

public class SecureDataContainerHashMap<E> implements SecureDataContainerInterface<E>{
    /*
    IR: data != null && per ogni <u,l> in data si ha che u != null e l != null
        per ogni u = <user, elts> in this non esiste o = <user, elts> t.c
        u.getIdUser() == o.getIdUser() &&
        Per ogni u = <user, elts> in this, per ogni e, u in u.elts, con e != u, si ha che u != e.
    FA: f(data) = {<user1, elts1>...<usern, eltsn>} dove usern è un User e eltsn è
        una lista di elementi E
    */

    private HashMap<KeyCouple,Vector<E>> DBUsers;

    SecureDataContainerHashMap(){
        DBUsers = new HashMap<>();

    }

    //DONE
    private boolean existsUser(String user){

        for(KeyCouple u: DBUsers.keySet()){
            if(u.getIdUser().equals(user)){
                return true;
            }
        }
        return false;
    }

    //DONE
    public void createUser(String Id, String passw) throws UserAlreadyPresentException {

        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        if(existsUser(Id)) throw new UserAlreadyPresentException(Id);

        KeyCouple u = new KeyCouple(Id, passw);
        DBUsers.put(u, new Vector<>());
    }

    //DONE
    public void removeUser(String Id, String passw) throws NoUserException {

        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple u = new KeyCouple(Id, passw);
        if(DBUsers.containsKey(u)){
            DBUsers.remove(u);
        }else{
            throw new NoUserException("No user");
        }

    }

    //DONE
    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException, NoUserException{

        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)) {
            return DBUsers.get(user).size();
        } else {
            throw new NoUserException();
        }

    }

    //DONE
    @Override
    public boolean put(String Owner, String passw, E data) throws NoUserException {

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


    //DONE
    @Override
    public E get(String Owner, String passw, E data) throws NoUserException, NoDataException {

        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user;
        user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)) {
            if(DBUsers.get(user).contains(data)) {
                return DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
            }else{
                throw new NoDataException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    //DONE
    @Override
    public E remove(String Owner, String passw, E data) throws NoUserException, NoDataException{

        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        E removedElement = null;
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)) {
                removedElement = DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
                DBUsers.get(user).remove(data);
            }else {
                throw new NoDataException("No data");
            }
        } else {
            throw new NoUserException("No user");
        }
        return removedElement;
    }

    //DONE
    @Override
    public void copy(String Owner, String passw, Object data) throws NoUserException, DataAlreadyPresentException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                throw new DataAlreadyPresentException("Il dato inserito esiste già");
            }else{
                Object copied;
                copied = data;
                DBUsers.get(user).add((E)copied);
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    //DONE
    @Override
    public void share(String Owner, String passw, String Other, E data) throws NoUserException,NoDataException, DataAlreadyPresentException {
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                for(KeyCouple o: DBUsers.keySet()){
                    if(o.getIdUser().equals(Other)){
                        if(DBUsers.get(o).contains(data)){
                            throw new DataAlreadyPresentException();
                        }else {
                            DBUsers.get(o).add(data);
                        }
                    }
                }
            }else{
                throw new NoDataException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    //DONE
    @Override
    public Iterator getIterator(String Owner, String passw) throws NoUserException {

        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            List<E> unmodifiable = DBUsers.get(user);
            return unmodifiable.iterator();
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    public void printUsers(){
        for(KeyCouple K: DBUsers.keySet()){
            System.out.println(K.getIdUser());
        }
    }
}
