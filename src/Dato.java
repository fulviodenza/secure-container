import java.util.*;

public class Dato<E> {
    private String owner;
    private String name;
    private E data;
    private List<String> poweredUsers;

    public Dato(String owner, String name, E data){
        if(owner == null || data == null || name == null) throw new NullPointerException();
        this.owner = owner;
        this.name = name;
        this.data = data;
        poweredUsers = new Vector<>();
    }
    public Dato(E data){
        if(data == null) throw new NullPointerException();
        this.data = data;
        poweredUsers = new Vector<>();
    }
    public void empower(String name)throws AlreadyPoweredException{
        if(poweredUsers.contains(name)){
            throw new AlreadyPoweredException("Questo utente può già accedere ai dati");
        }else{
            poweredUsers.add(name);
        }
    }
    public void banUser(String name)throws AlreadyWeakException{
        if(!poweredUsers.contains(name)){
            throw new AlreadyWeakException("Questo utente è già stato bannato dai dati");
        }else{
            poweredUsers.remove(name);
        }
    }
    public boolean isOwner(String name){
        return owner == name;
    }
    public boolean whoIsPowered(String name){
        if(name == owner || isOwner(name)){
            return true;
        }else{
            return false;
        }
    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<String> getPoweredUsers() {
        return poweredUsers;
    }

    public void setAllowedUsers(List<String> poweredUsers) {
        this.poweredUsers = poweredUsers;
    }
}
