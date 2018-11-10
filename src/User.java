import java.util.*;

public class User {
    private String idUser;
    private String passUser;
    private Vector<String> power;

    public User(String idUser, String passUser) {
        if(idUser == null || passUser == null) throw new NullPointerException();
        if(idUser.isEmpty() || passUser.isEmpty()) throw new IllegalArgumentException();
        this.idUser = idUser;
        this.passUser = passUser;
        power = new Vector<>();
    }

    public void increasePower(String u){
        this.power.add(u);
    }
    public void decreasePower(String u) {
        this.power.remove(u);
    }

    public void setPower(Vector<String> p){
        this.power = p;
    }
    public Vector<String> getPower() {
        return this.power;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        if(idUser == null) throw new NullPointerException();
        if(idUser.isEmpty()) throw new IllegalArgumentException();
        this.idUser = idUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        if(passUser == null) throw new NullPointerException();
        if(passUser.isEmpty()) throw new IllegalArgumentException();
        this.passUser = passUser;
    }

    public boolean sameUser(Object snd){
        User aux = (User)snd;
        return Objects.equals(idUser,aux.idUser);
    }
    public boolean isHere(String name){
        return Objects.equals(idUser,name);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User u = (User) o;
        return Objects.equals(idUser, u.idUser) &&
                Objects.equals(passUser,u.passUser);
    }
    @Override
    public int hashCode(){
        return Objects.hash(idUser,passUser);
    }
}
