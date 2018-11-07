public class User {
  private String userName;
  private String userPass;

  public User(String name, String pass) {
    if(name.isEmpty() || pass.isEmpty())
      throw new InvalidArgumentException();

    this.userName = name;
    this.userPass = pass;
  }

  public String getUserName() { return userName; }
  public String getUserPass() { return userPass; }

  public String setUserPass( String newPass) {
    if( newPass.isEmpty() )
      throw new InvalidArgumentException();

    this.userPass = newPass;
  }

  @Override
  public boolean equals(Object o) {
    User other = (User)o;
    return( other.getUserName().equals( this.getUserName() ) &&
            other.getUserPass().equals( this.getUserPass() ) );
  }
}
