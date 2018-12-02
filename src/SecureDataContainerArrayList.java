import exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class SecureDataContainerArrayList<E> implements SecureDataContainerInterface<E> {

    /*
    IR: arrayListElement != null && per ogni <el,us[]> in arrayListElement si ha che el != null e us[] != null
        && per ogni elemento di us[] si ha che non esiste u = <Id,passw> && o = <Id,passw> t.c. u.getIdUser() == o.getIdUser()
        && per ogni el appartenente ad arrayListElement vale che us[0] (il primo utente che ha visibilità sull'elemento el) == arrayListElement.returnOwner()
    FA: f(data) = {<el0,us0>,<el1,us1>,...,<el(n-1),us(n-1)>}
        dove el0,...,el(n-1) sono gli elementi generici dell'utente e us0,...,us(n-1) sono liste di utenti che hanno
        visibilità sull'elemento el0,...,el(n-1)
     */

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

    public int getSize(String Owner, String passw) throws NullPointerException, IllegalArgumentException, NoUserException {
        if(Owner == null && passw == null){throw new NullPointerException();}
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        KeyCouple u = new KeyCouple(Owner,passw);
        int count = 0;
        if(Users.contains(u)){
            for(int i = 0; i < DataBase.size(); i++){
                if(DataBase.get(i).returnOwner().equals(Owner) && DataBase.get(i).isOfOwner(Owner)){
                    count++;
                }
            }
            return count;
        }else {
            throw new NoUserException();
        }
    }

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

    public E remove(String Owner, String passw, E data) throws NoUserException, NoDataException {
        if(Owner == null || passw == null || data == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();

        E removedEl = null;
        KeyCouple user = new KeyCouple(Owner,passw);
        ArrayListElement el = new ArrayListElement(data,user);

        if(Users.contains(user)){
            Iterator<ArrayListElement> iter = DataBase.iterator();
            while(iter.hasNext()){
                ArrayListElement mandrillo = iter.next();
                if(mandrillo.getData().equals(data) && mandrillo.returnOwner().equals(Owner)){
                    removedEl = (E)mandrillo.getData();
                    iter.remove();
                }
            }
        }else {
            throw new NoUserException();
        }
        if(removedEl == null){
            throw new NoDataException();
        }else{
            return removedEl;
        }
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

    public void share(String Owner, String passw, String Other, E data) throws NoUserException {
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner, passw);
        String passOther = ""; //segnaposto momentaneo
        for (KeyCouple u: Users) {
            if(u.getIdUser().equals(Other)) {
                passOther = u.getPassUser();
                break;
            }
        }

        KeyCouple userShare = new KeyCouple(Other,passOther);
        if(Users.contains(userShare)) {
            ArrayListElement el = new ArrayListElement(data, user);
            int iter = 0;
            if (Users.contains(user)) {
                while (!DataBase.get(DataBase.indexOf(el)).equals(el) && iter < DataBase.size()) {
                    iter++;
                }

                for (ArrayListElement e : DataBase) {
                    if (e.equals(el)) {
                        e.addUsers(userShare);
                    }
                }
                DataBase.get(iter).addUsers(userShare);
            } else {
                throw new NoUserException();
            }
        }else{
            throw new NoUserException();
        }
    }

    public Iterator<E> getIterator(String Owner, String passw) throws NoUserException {

        if(Owner == null || passw == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty()) throw new IllegalArgumentException();
        KeyCouple user = new KeyCouple(Owner,passw);
        List<E> unmodifiable = new ArrayList<>();
        if(Users.contains(user)){
            for (ArrayListElement e: DataBase) {
                if(e.returnOwner().equals(Owner) || e.isOfOwner(Owner)){
                    unmodifiable.add((E)e.getData());
                }
            }
            return unmodifiable.iterator();
        }else{
            throw new NoUserException("Non esiste l'utente richiesto");
        }
    }
    
    public void unshare(String Owner, String passw, String Other, E data) throws NoUserException{
        if(Owner == null || passw == null || data == null || Other == null) throw new NullPointerException();
        if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) throw new IllegalArgumentException();

        KeyCouple user = new KeyCouple(Owner, passw);
        String passOther = ""; //segnaposto momentaneo
        for (KeyCouple u: Users) {
            if(u.getIdUser().equals(Other)) {
                passOther = u.getPassUser();
                break;
            }
        }

        KeyCouple userShare = new KeyCouple(Other,passOther);
        if(Users.contains(userShare)) {
            ArrayListElement el = new ArrayListElement(data, user);
            int iter = 0;
            if (Users.contains(user)) {
                while (!DataBase.get(DataBase.indexOf(el)).equals(el) && iter < DataBase.size()) {
                    iter++;
                }

                for (ArrayListElement e : DataBase) {
                    if (e.equals(el)) {
                        e.remove(userShare);
                    }
                }
            } else {
                throw new NoUserException("no user");
            }
        } else {
            throw new NoUserException("no user");
        }
    }
}
