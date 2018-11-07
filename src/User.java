public class User {
  private String userName;
  private String userPass;

  public String getUserName() { return userName;}
  public String getUserPass() { return userPass;}

  public String setUserPass( String newPass) {
    if(newPass.isEmpty()) throw new InvalidArgumentException();
    this.userPass = newPass;
  }

  @override
  public boolean equals(Object o) {
    User other = (User)o;
    return( other.getUserName().equals(this.getUserName() ) &&
            other.getUserPass().equals(this.getUserPass)  );
  }
}
