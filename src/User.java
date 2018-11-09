public class User implements Comparable<User> {
  private String userName;
  private String userPass;

  public User(String name, String pass) {
    if(name.isEmpty() || pass.isEmpty())
      throw new IllegalArgumentException();

    this.userName = name;
    this.userPass = pass;
  }

  public String getUserName() { return userName; }
  public String getUserPass() { return userPass; }

  public void setUserPass( String newPass) {
    if( newPass.isEmpty() )
      throw new IllegalArgumentException();

    this.userPass = newPass;
  }

  @Override
  public boolean equals(Object o) {
    User other = (User)o;
    return( other.getUserName().equals( this.getUserName() ) &&
            other.getUserPass().equals( this.getUserPass() ) );
  }

  @Override
  public int compareTo(User other) {
     int nameC = this.getUserName().compareTo(other.getUserName());
     if(nameC == 0) {
       return this.getUserPass().compareTo(other.getUserPass());
     }
     return nameC;
  }
}
