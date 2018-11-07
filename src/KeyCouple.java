public class KeyCouple {
    private String idUser;
    private String passUser;


    public KeyCouple(String idUser, String passUser) {
        this.idUser = idUser;
        this.passUser = passUser;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }
}
