import java.util.Iterator;
import java.util.List;

import Exceptions.*;
import java.util.*;
/*
 * Seconda implementazione
 *
 * IR: DBUsers != null && forall(k).(k appartenga a DBUsers) ==> k != null &&
 * forall(x,y).(x,y appartengano a DBUsers && x != y) ==> x.getUserName != y.getUserName &&
 * forall(z).(z appartenga a DBElems) ==> z != null
 *
 * FA: f(DB) = <DBUsers, DBElems>, ossia <{User0,...,Usern},{El0,...,Elk}> ossia
 * <{(Nome0,passw0),...,(Nomen,passwn)},{(Owner0,el0,{AuthUsers0}),...,(Ownerk,elk,AuthUsersk)}> ossia
 * <{(Nome0,passw0),...,(Nomen,passwn)},{(Owner0,el0,{authorized0,...authorizedt}0),...,(Ownerk,elk,{authorized0,...,authorizedt}k)}>
 * con n == DBUsers.size()-1 && k == DBElems.size()-1 && t == AuthUsers.size()-1
 *
 * */
public class SecureDataContainerVectors<E> implements SecureDataContainer<E> {

    private List<User> DBUsers;
    private List<Dato<E>> DBElems;

    //restituisce il dato d se è posseduto da Owner o se Owner è un utente autorizzato ad accedere a quel dato
    private Dato<E> getElem(String Owner, E el) {
        for(Dato<E> d : DBElems) {
            if(((d.getOwner().equals(Owner)) || d.auth(Owner)) && d.getEl().equals(el))
                return d;
        }
        return null;
    }
    //Metodo che restituisce true se l'utente di nome 'name' è già presente nella collezione
    private boolean alreadyIn(String name){
        for(User t : DBUsers){
            if(t.getIdUser().equals(name)) return true;
        }
        return false;
    }
    //Metodo che restituisce l'utente di nome 'nome'
    private User getUs(String nome){
        for(User t : DBUsers){
            if(t.getIdUser().equals(nome)) return t;
        }
        return null;
    }
    public SecureDataContainerVectors(){
        DBUsers = new Vector<>();
        DBElems = new Vector<>();
    }
    /*OVERVIEW: Crea l’identità un nuovo utente della collezione*/
    public void createUser(String Id, String passw) throws DoubleUserException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Id,passw);
        if(!alreadyIn(Id)){
            DBUsers.add(u);
        }else throw new DoubleUserException("Utente già esistente");
    }
    /*OVERVIEW:Rimuove l'utente dalla collezione*/
    public void RemoveUser(String Id, String passw) throws NoUserException {
        if(Id == null || passw == null) throw new NullPointerException();
        if(Id.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Id,passw);
        if(DBUsers.contains(u)){
            DBUsers.remove(u);
            for(Dato<E> e : DBElems){
                if(e.getOwner().equals(Id)) e.clearAuth();
                e.getAuth().remove(Id);
            }
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: Restituisce il numero degli elementi di un utente presenti nella
    collezione*/
    public int getSize(String Owner, String passw) throws NoUserException {
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        int c = 0;
        if(DBUsers.contains(u)){
           for(Dato t : DBElems){
               if(t.getOwner().equals(Owner) || t.getAuth().contains(Owner)){
                   c++;
               }
           }
        }else throw new NoUserException("Utente non esistente o credenziali errate");
        return c;
    }
    /*OVERVIEW: Inserisce il valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public boolean put(String Owner, String passw, E data) throws NoUserException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u)){
            Dato<E> d = new Dato<>(Owner,data);
            return DBElems.add(d);
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: Ottiene una copia del valore del dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E get(String Owner, String passw, E data) throws NoUserException, DataNotFoundException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u)){
            for(Dato<E> t : DBElems){
                if(t.getEl().equals(data) && t.auth(Owner)){
                    return t.getEl();
                }
            }
            throw new DataNotFoundException("Il dato non esiste");
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: Rimuove il dato nella collezione
    se vengono rispettati i controlli di identità*/
    public E remove(String Owner, String passw, E data) throws NoUserException, DataNotFoundException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u)){
            Dato<E> d = getElem(Owner,data);
            E aux;
            if(d != null){
                aux = d.getEl();
                DBElems.remove(d);
            }else throw new DataNotFoundException("Il dato non esiste");
            return aux;
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: Crea una copia del dato nella collezione
    se vengono rispettati i controlli di identità*/

    public void copy(String Owner, String passw, E data) throws NoUserException, DataNotFoundException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u)){
            Dato<E> aux = getElem(Owner,data);
            if(aux != null){
                Dato<E> e = new Dato<>(Owner,data);
                DBElems.add(e);
            }else throw new DataNotFoundException("Il dato non esiste");
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: Condivide il dato nella collezione con un altro utente
    se vengono rispettati i controlli di identità*/
    public void share(String Owner, String passw, String Other, E data) throws NoUserException, DataNotFoundException, AlreadyAuthorizedException {
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("") || Other.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u) || (getUs(Other) != null && DBUsers.contains(getUs(Other)))){
            Dato aux = getElem(Owner,data);
            if(aux != null){
                if(!aux.auth(Other)){
                    aux.setAuth(Other);
                }else throw new AlreadyAuthorizedException("Utente già autorizzato");
            }else throw new DataNotFoundException("Il dato non esiste");
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
    /*OVERVIEW: restituisce un iteratore (senza remove) che genera tutti i dati
    dell’utente in ordine arbitrario
    se vengono rispettati i controlli di identità*/
    public Iterator<E> getIterator(String Owner, String passw) throws NoUserException {
        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.equals("") || passw.equals("")) throw new IllegalArgumentException();
        User u = new User(Owner,passw);
        if(DBUsers.contains(u)){
            List<E> utenti = new Vector<>();
            for(Dato<E> t : DBElems) {
                if(t.auth(Owner)) {
                    utenti.add(t.getEl());
                }
            }
            List<E> aux = Collections.unmodifiableList(utenti);
            return aux.iterator();
        }else throw new NoUserException("Utente non esistente o credenziali errate");
    }
}
