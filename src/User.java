public class User {
    private String idUser;
    private String passUser;

    public User(String idUser, String passUser) {
        if(idUser == null || passUser == null) throw new NullPointerException();
        if(idUser.equals("") || passUser.equals("")) throw new IllegalArgumentException();
        this.idUser = idUser;
        this.passUser = passUser;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        if(idUser == null) throw new NullPointerException();
        if(idUser.equals("")) throw new IllegalArgumentException();
        this.idUser = idUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        if(passUser == null) throw new NullPointerException();
        if(passUser.equals("")) throw new IllegalArgumentException();
        this.passUser = passUser;
    }

    public boolean sameUser(User snd){
        return this.getIdUser().equals(snd.getIdUser());
    }
}
