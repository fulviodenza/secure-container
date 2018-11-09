import java.util.Objects;

public class KeyCouple {

    private String idUser;
    private String passUser;

    public KeyCouple(String id,String passw){
        idUser = id;
        passUser = passw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyCouple keyCouple = (KeyCouple) o;
        return Objects.equals(idUser, keyCouple.idUser) &&
                Objects.equals(passUser, keyCouple.passUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, passUser);
    }

    public String getIdUser(){
        return idUser;
    }

}
