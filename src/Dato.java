import java.util.*;
public class Dato<E> {
    private String Owner;
    private E el;
    private List<String> AuthUsers;

    public Dato(String ow, E el){
        Owner = ow;
        this.el = el;
        AuthUsers = new Vector<>();
    }

    public List<String> getAuth(){
        return AuthUsers;
    }
    public void setAuth(String name){
        AuthUsers.add(name);
    }
    public void clearAuth(){
       AuthUsers.clear();
    }
    public String getOwner(){
        return Owner;
    }
    public boolean auth(String ow){
        return (AuthUsers.contains(ow) || ow.equals(Owner));
    }
    public E getEl(){
        return el;
    }
}
