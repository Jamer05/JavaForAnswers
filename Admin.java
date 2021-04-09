class Admin{

  private String username;
  private String password;

  //This is a constructor method.
  public Admin(String username, String password){
    this.username = username;
    this.password = password;
  }

  //getter
  public String getUsername(){
    return username;
  }
  
  //getter
  public String getPassword(){
    return password;
  }
}