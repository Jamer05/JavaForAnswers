class User{
  
  private String username;
  private String password;

  //This is a constructor method.
  public User(String username, String password){
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
  
  //This part consists all the values inside of this class, and when you print it, you will see the values in this format.
  @Override
  public String toString(){
    return "Username: " +username+ "\nPassword: " +password+ "\n\n";
  }
}