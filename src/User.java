/*
  Overview: Tipo di dato mutabile che rappresenta un utente della SecureDataContainer
  IR: userName, userPass != {null, ""}
  FA: a(userName, userPass) = (username, userPass) dove userName e userPass sono stringhe non vuote
*/

public class User implements Comparable<User> {
  private String userName;
  private String userPass;

  public User(String name, String pass) {
    if(name.isEmpty() || pass.isEmpty())
      throw new IllegalArgumentException();

    this.userName = name;
    this.userPass = pass;
  }

  /*
    EFFECTS: Restituisce il nome utente dell'utente corrente
  */
  public String getUserName() { return userName; }

  /*
    EFFECTS: Restituisce la password dell'utente corrente
  */
  public String getUserPass() { return userPass; }

  /*
    EFFECTS: Cambia la password dell'utente
    REQUIRES: newPass != null, altrimenti lancia NullPointerException (unchecked, a parte di isEmpty())
              newPass != "", altrimenti lancia IllegalArgumentException, checked
  */
  public void setUserPass( String newPass) {
    if( newPass.isEmpty() )
      throw new IllegalArgumentException();

    this.userPass = newPass;
  }


  /*
    EFFECTS: Restituisce true se l'utente other e this sono lo stesso utente
    REQUIRES: o != null, altrimenti lancia NullPointerException (unchecked)
              o istanza di User, altrimenti lancia CastClassException (lanciata quando
              il casting di o a User fallisce, unchecked)
  */
  @Override
  public boolean equals(Object o) {
    if(o == null ) throw new NullPointerException();
    User other = (User)o;
    return( other.getUserName().equals( this.getUserName() ) &&
            other.getUserPass().equals( this.getUserPass() ) );
  }

  /*
    EFFECTS: Restituisce se l'utente va messo in una collezione prima, dopo o vicino a other basandosi
              sulla nozione di precedenza di String (nello specifico di userName).
    REQUIRES: other != null, altrimenti lancia NullPointerException */
  @Override
  public int compareTo(User other) {
     return this.getUserName().compareTo(other.getUserName());
  }
}
