public class KeyCouple {

    private String idUser;
    private String passUser;

    public KeyCouple(String id,String passw){
        idUser = id;
        passUser = passw;
    }

    public String get(){
        return idUser;
    }
}
