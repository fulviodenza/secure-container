/*
* Prima implementazione
*
* IR: DBUsers != null && forall <u,list(el)> ==> (u != null) && (forall el ==> el != null)
* && forall(x,y).(x,y appartengano a DBUsers && x != y) ==> x.getUserName != y.getUserName
* FA: f(DBUsers) = {<u0,{el0,...,eln}0>,....<uk,{el0,...,eln}k>} dove u1 è un User e {el0,...eln} una lista di elementi
 * dove n == list.size()-1 e k == DBUsers.size()-1
* */

import Exceptions.*;
import java.util.*;

public class SecureDataContainerHashMap<E> implements SecureDataContainer<E> {

    private Map<User,Vector<E>> DBUsers;

    public SecureDataContainerHashMap(){
        DBUsers = new HashMap<>();
    }
    //questo metodo cerca tra tutti gli utenti se ne esiste già uno uguale a quello passato come parametro e restituisce true in caso positivo, false altrimenti.
    private boolean doubleUser(User u){
        for(User t : DBUsers.keySet()){
            if(u.sameUser(t)) return true;
        }
        return false;
    }
    //questo metodo mi restituisce l'oggetto User relativo all'utente di cui passo il nome, se esiste.
    private User chiave(String name) throws NoUserException{
        for(User t : DBUsers.keySet()){
            if(t.isHere(name)) return t;
        }
        throw new NoUserException("Non esiste quest'utente");
    }
    //questo metodo verifica che non esista un utente con quel nome, ricevendo come input solo la stringa del nome (per verificare che esiste l'utente 'Other' senza sapere le sue credenziali)
    private boolean checkUser(String name) throws NoUserException {
        return doubleUser(chiave(name));
    }
    //questo metodo elimina tutti i riferimenti al dato passato come parametro una volta eseguita la "remove"
    private void swap(E dato){
        for(User t : DBUsers.keySet()){
            DBUsers.get(t).remove(dato);
        }
    }
    //metodo che usa un algoritmo simile al cifrario di Cesare a (7+i) posizioni per cifrare la password
    private String encrypt(String passw){
        char[] lettere = passw.toCharArray();

        StringBuilder passwBuilder = new StringBuilder();
        for(int i = 0; i<lettere.length; i++){
            lettere[i] += 7+i;
            passwBuilder.append(lettere[i]);
        }
        passw = passwBuilder.toString();
        return passw;
    }

    /*OVERVIEW: Crea un nuovo utente nella collezione*/
    public void createUser(String Id, String passw) throws NullPointerException,IllegalArgumentException,DoubleUserException{
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Id,encrypt(passw));
        if(doubleUser(user)){
            throw new DoubleUserException("Utente già esistente");
        }else{
            DBUsers.put(user,new Vector<>());
        }
    }

    /*OVERVIEW:Rimuove l'utente dalla collezione*/
    public void RemoveUser(String Id, String passw) throws NullPointerException,IllegalArgumentException,NoUserException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User u = new User(Id,encrypt(passw));
        if(DBUsers.containsKey(u)){
            DBUsers.remove(u);
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }
    /*OVERVIEW: Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException,NoUserException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(!DBUsers.containsKey(user)) throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        Vector<E> aux = DBUsers.get(user);
        return aux.size();
    }
    /*OVERVIEW: Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public boolean put(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(DBUsers.containsKey(user)){
            return DBUsers.get(user).add(data);
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }
    /*OVERVIEW: Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E get(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                return aux.get(aux.indexOf(data));
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }
    /*OVERVIEW: Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E remove(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(DBUsers.containsKey(user)){
            if(DBUsers.get(user).contains(data)){
                E cpy = DBUsers.get(user).get(DBUsers.get(user).indexOf(data));
                swap(data);
                return cpy;
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }
    /*OVERVIEW: Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public void copy(String Owner, String passw, E data) throws NullPointerException,IllegalArgumentException,NoUserException,DataNotFoundException{
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(DBUsers.containsKey(user) && user.getPassUser().equals(encrypt(passw))){
            if(DBUsers.get(user).contains(data)){
                List<E> aux = DBUsers.get(user);
                E cpy = aux.get(aux.indexOf(data));
                aux.add(cpy);
            }else{
                throw new DataNotFoundException("Non esiste il dato richiesto");
            }
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }
    /*OVERVIEW: Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    public void share(String Owner, String passw, String Other, E data) throws NullPointerException, IllegalArgumentException, NoUserException, DataNotFoundException {
        if(Owner == null || passw == null || data == null ||Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if(!DBUsers.containsKey(user) || !user.getPassUser().equals(encrypt(passw))){
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }else{
            if(!(checkUser(Other))){
                throw new NoUserException("L'utente con cui condividere il dato non esiste");
            }
            else{
                if(DBUsers.get(user).contains(data)) {
                    for (User t : DBUsers.keySet()) {
                        if (t.isHere(Other)) {
                            List<E> aux = DBUsers.get(t);
                            aux.add(data);
                            break;
                        }
                    }
                }else{
                    throw new DataNotFoundException("Non esiste il dato richiesto");
                }
            }
        }
    }
    /*OVERVIEW: restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException,IllegalArgumentException,NoUserException{
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        User user = new User(Owner,encrypt(passw));
        if((DBUsers.containsKey(user) && user.getPassUser().equals(encrypt(passw)))){
            //Rende impossibile l'invocazione del metodo remove sull'iteratore restituito
            return Collections.unmodifiableList(DBUsers.get(user)).iterator();
        }else{
            throw new NoUserException("Non esiste l'utente richiesto o le credenziali sono errate");
        }
    }

}