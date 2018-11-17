import java.util.*;

public class User<E> {
    private String idUser;
    private String passUser;
    //private Pair<E> power;

    public User(String idUser, String passUser) {
        if(idUser == null || passUser == null) throw new NullPointerException();
        if(idUser.isEmpty() || passUser.isEmpty()) throw new IllegalArgumentException();
        this.idUser = idUser;
        this.passUser = passUser;
        //power = new Pair<>();
    }


    public String getIdUser() {
        return idUser;
    }


    public String getPassUser() {
        return passUser;
    }


    /*public void setPower(String p,E d){
        power.setLeftPower(p);
        power.setRightPower(d);
    }

    public Pair<E> getPower(){
        return this.power;
    }*/

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
