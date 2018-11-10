/*
* IR: DBUsers != null && forall <u,list(el)> ==> (u != null && list(el)) != null && (forall el ==> el != null) && forall(x,y).(x,y appartengano a DBUsers && x != y) ==> x.getUserName != y.getUserName
* FA: DBUsers = {<u1,{el0,...,eln}k>,....<uk,{el0,...,eln}k>} dove u1 è un User e {el0,...eln} una lista di elementi dove n == list.size()-1 e k == DBUsers.size()-1
* */

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
    private User chiave(String name){
        for(User t : DBUsers.keySet()){
            if(t.isHere(name)) return t;
        }
        return null;
    }
    private List<String> cercaOwners(E dato){
        List<String> chiavi = new Vector<>();
        for(User t : DBUsers.keySet()){
            if(DBUsers.get(t).contains(dato)) chiavi.add(t.getIdUser());
        }
        return chiavi;
    }
    public void stampaAuth(String name, String passw){
        User u = chiave(name);
        System.out.println("Gli utenti autorizzati sono: "+u.getPower().size());
        for(int i=0;i<u.getPower().size();i++){
            System.out.println(u.getPower().get(i));
        }
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
    public E get(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                return aux.get(aux.indexOf(data));
            }else{
                if(cercaOwners(data) != null){
                    for(String a : cercaOwners(data)){
                        if(chiave(a).getPower().contains(Owner)){
                            return get(Owner,passw,data,a);
                        }
                    }
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati");
                }else{
                    throw new DataNotFoundException("Non esiste il dato richiesto");
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    @Override
    /*OVERVIEW: Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E remove(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,passw);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                E cpy = DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
                DBUsers.get(user).remove(data);
                return cpy;
            }else{
                if(cercaOwners(data) != null){
                    for(String a : cercaOwners(data)){
                        if(chiave(a).getPower().contains(Owner)){
                            return remove(Owner,passw,data,a);
                        }
                    }
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati");
                }else{
                    throw new DataNotFoundException("Non esiste il dato richiesto");
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
    @Override
    /*OVERVIEW: Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public void copy(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = chiave(Owner);
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                E cpy = aux.get(aux.indexOf(data));
                aux.add(cpy);
            }else{
                if(cercaOwners(data) != null){
                    for(String a : cercaOwners(data)){
                        if(chiave(a).getPower().contains(Owner)){
                            copy(Owner,passw,data,a);
                            return;
                        }
                    }
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati");
                }else{
                    throw new DataNotFoundException("Non esiste il dato richiesto");
                }
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
        if(!DBUsers.containsKey(user)){
            throw new NoUserException("Non esiste l'utente richiesto");
        }else{
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

    @Override
    public void empowerUser(String owner, String passw,String nome) throws NoUserException,AlreadyPoweredException{
        if(owner==null || passw == null || nome == null)throw new NullPointerException();
        if(owner.isEmpty() || passw.isEmpty() || nome.isEmpty())throw new IllegalArgumentException();
        User u = chiave(owner);
        if(DBUsers.containsKey(u)){
            if(!(checkUser(nome))){
                throw new NoUserException("L'utente a cui autorizzare l'accesso non esiste");
            }else{
                if (u.getPower().contains(nome)) {
                    throw new AlreadyPoweredException("L'utente scelto ha già i diritti di accedere ai tuoi dati");
                } else {
                    Vector<E> aux = DBUsers.get(u);
                    DBUsers.remove(u);
                    u.increasePower(nome);
                    DBUsers.put(u,aux);
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    @Override
    public void depowerUser(String owner, String passw, String nome) throws NoUserException,AlreadyWeakException{
        if(owner==null || passw == null || nome == null)throw new NullPointerException();
        if(owner.isEmpty() || passw.isEmpty() || nome.isEmpty())throw new IllegalArgumentException();
        User u = chiave(owner);
        if(DBUsers.containsKey(u)){
            if(!(checkUser(nome))){
                throw new NoUserException("L'utente a cui togliere l'autorizzazione non esiste");
            }else{
                if (u.getPower().contains(nome)) {
                    Vector<E> aux = DBUsers.get(u);
                    DBUsers.remove(u);
                    u.decreasePower(nome);
                    DBUsers.put(u,aux);
                } else {
                    throw new AlreadyWeakException("L'utente scelto è già stato bannato dai tuoi dati");
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }

    public E get(String name, String passw, E data, String owner)throws NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(owner==null || passw == null || name == null || data == null)throw new NullPointerException();
        if(owner.isEmpty() || passw.isEmpty() || name.isEmpty())throw new IllegalArgumentException();
        User u = new User(name,passw);
        if(DBUsers.containsKey(u)){
            if(!(checkUser(owner))){
                throw new NoUserException("Il proprietario del dato non esiste");
            }else{
                if(chiave(owner).getPower().contains(name)){
                        if(DBUsers.get(chiave(owner)).contains(data)){
                            List<E> aux = DBUsers.get(chiave(owner));
                            return aux.get(aux.indexOf(data));
                        }else{
                            throw new DataNotFoundException("Non esiste il dato richiesto");
                        }
                }else{
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati di "+owner);
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
    public E remove(String name, String passw, E data, String owner)throws NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(owner==null || passw == null || name == null || data == null)throw new NullPointerException();
        if(owner.isEmpty() || passw.isEmpty() || name.isEmpty())throw new IllegalArgumentException();
        User u = new User(name,passw);
        if(DBUsers.containsKey(u)){
            if(!(checkUser(owner))){
                throw new NoUserException("Il proprietario del dato non esiste");
            }else{
                if(chiave(owner).getPower().contains(name)){
                    if(DBUsers.get(chiave(owner)).contains(data)){
                        E cpy = DBUsers.get(chiave(owner)).get(DBUsers.get(chiave(owner)).indexOf(data));
                        DBUsers.get(chiave(owner)).remove(data);
                        return cpy;
                    }else{
                        throw new DataNotFoundException("Non esiste il dato richiesto");
                    }
                }else{
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati di "+owner);
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
    public void copy(String name, String passw, E data, String owner)throws NoUserException,DataNotFoundException,NotAuthorizedUserException{
        if(owner==null || passw == null || name == null || data == null)throw new NullPointerException();
        if(owner.isEmpty() || passw.isEmpty() || name.isEmpty())throw new IllegalArgumentException();
        User u = new User(name,passw);
        if(DBUsers.containsKey(u)){
            if(!(checkUser(owner))){
                throw new NoUserException("Il proprietario del dato non esiste");
            }else{
                if(chiave(owner).getPower().contains(name)){
                    if(DBUsers.get(chiave(owner)).contains(data)){
                        List<E> aux = DBUsers.get(chiave(owner));
                        E cpy = aux.get(aux.indexOf(data));
                        aux.add(cpy);
                    }else{
                        throw new DataNotFoundException("Non esiste il dato richiesto");
                    }
                }else{
                    throw new NotAuthorizedUserException("Non sei autorizzato ad accedere ai dati di "+owner);
                }
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }


}