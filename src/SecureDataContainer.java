import exceptions.*;

import java.util.*;

public class SecureDataContainer<E> implements SecureDataContainerInterface<E>{
    /*
    IR: data != null && per ogni <u,l> in data si ha che u != null e l != null
        per ogni u = <user, elts> in this non esiste o = <user, elts> t.c
        u.getUserName() == o.getUserName() &&
        Per ogni u = <user, elts> in this, per ogni e, u in u.elts, con e != u, si ha che u != e.
    FA: f(data) = {<user1, elts1>...<usern, eltsn>} dove usern è un User e eltsn è
        una lista di elementi E
    */

    private Map<KeyCouple,Vector<E>> DBUsers;

    public SecureDataContainer(){
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
    @Override
    /*public void createUser(String Id, String passw) throws UserAlreadyPresent {

        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw  new IllegalArgumentException();

        KeyCouple IdPassw = new KeyCouple(Id, passw);
        if(DBUsers.containsKey(IdPassw)){
            if(!existsUser(IdPassw)){
                throw new UserAlreadyPresent("User already present");
            }
        }else{
            DBUsers.put(IdPassw, new Vector<E>());
        }
    }*/

    public void createUser(String Id, String passw) throws UserAlreadyPresent {
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        if(existsUser(Id)) throw new UserAlreadyPresent(Id);

        KeyCouple u = new KeyCouple(Id, passw);
        DBUsers.put(u, new Vector<>());
    }

    //DONE
    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        return DBUsers.size();

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
    public E remove(String Owner, String passw, E data){
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)) {
                E removedElement = DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
                DBUsers.get(user).remove(data);
                return removedElement;
            }
        }
        return null;
    }

    //DONE
    @Override
    public void copy(String Owner, String passw, Object data) throws NoUserException, DataAlreadyPresent {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                throw new DataAlreadyPresent("Il dato inserito esiste già");
            }else{
                DBUsers.get(user).add((E) data);
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    //DONE
    @Override
    public void share(String Owner, String passw, String Other, E data) throws NoUserException,NoDataException,DataAlreadyPresent {
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                for(KeyCouple o: DBUsers.keySet()){
                    if(o.getIdUser().equals(Other)){
                        if(DBUsers.get(o).contains(data)){
                            throw new DataAlreadyPresent();
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

    @Override
    public Iterator getIterator(String Owner, String passw) throws NoUserException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner,passw);
        if(DBUsers.containsKey(user)){
            Iterator<Map.Entry<KeyCouple, Vector<E>>> it;
            it = DBUsers.entrySet().iterator();
            return it;
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
}
