import java.util.*;

public class SecureDataContainerHashMap<E> implements SecureDataContainer<E> {

    private Map<User,Vector<E>> DBUsers;

    public SecureDataContainerHashMap(){
        DBUsers = new HashMap<>();
    }
    private boolean doubleUser(User u){
        for(User t : DBUsers.keySet()){
            if(u.sameUser(t)) return true;
        }
        return false;
    }
    private boolean checkUser(String name){
        for(User t : DBUsers.keySet()){
            if(t.isHere(name)) return true;
        }
        return false;
    }
    @Override
    /*OVERVIEW: Crea un nuovo utente nella collezione*/
    public void createUser(String Id, String passw) throws NullPointerException,IllegalArgumentException,DoubleUserException{
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Id,passw);
        if(doubleUser(user)){
            throw new DoubleUserException("Utente già esistente");
        }else{
            DBUsers.put(user,new Vector<E>());
        }
    }

    @Override
    /*OVERVIEW: Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException,NoUserException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(!DBUsers.containsKey(user)) throw new NoUserException("Non esiste l'utente richiesto");
        Vector<E> aux = DBUsers.get(user);
        return aux.size();
    }

    @Override
    /*OVERVIEW: Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public boolean put(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            return DBUsers.get(user).add(data);
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }


    @Override
    /*OVERVIEW: Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E get(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                return aux.get(aux.indexOf(data));
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    @Override
    /*OVERVIEW: Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E remove(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                E cpy = DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
                DBUsers.get(user).remove(data);
                return cpy;
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
    @Override
    /*OVERVIEW: Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public void copy(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                E cpy = aux.get(aux.indexOf(data));
                aux.add(cpy);
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }


    @Override
    /*OVERVIEW: Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException,IllegalArgumentException,NoUserException{
        if(Owner == null || passw == null || data == null ||Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(!(checkUser(Other))){
            throw new NoUserException("L'utente con cui condividere il dato non esiste");
        }
        else{
            for(User t : DBUsers.keySet()){
                if(t.isHere(Other)){
                    List<E> aux = DBUsers.get(t);
                    aux.add(data);
                    break;
                }
            }
        }
    }

    @Override
    /*OVERVIEW: restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    public Iterator getIterator(String Owner, String passw) throws NullPointerException,IllegalArgumentException,NoUserException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            return DBUsers.get(user).iterator();
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
}