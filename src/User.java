import java.util.*;

public class User {
    private String idUser;
    private String passUser;

    public User(String idUser, String passUser) {
        if(idUser == null || passUser == null) throw new NullPointerException();
        if(idUser.isEmpty() || passUser.isEmpty()) throw new IllegalArgumentException();
        this.idUser = idUser;
        this.passUser = passUser;
    }

    public String getIdUser(){return  idUser;}
    public String getPassUser() {
        return passUser;
    }

    //metodo che restituisce true se gli id degli utenti sono uguali, false altrimenti
    public boolean sameUser(User snd){
        return Objects.equals(idUser,snd.idUser);
    }
    //metodo che restituisce true se gli id degli utenti sono uguali, false altrimenti (ricevendo solo la stringa del nome)
    public boolean isHere(String name){
        return Objects.equals(idUser,name);
    }

    //sovrascrivo i metodi equals e hashCode della classe Object per permettere il confronto tra gli utenti
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
