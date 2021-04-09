class Rating{

  private String book;
  private double rating;
  private String user;

  //This is a constructor method.
  public Rating(String user, String book, double rating){
    this.book = book;
    this.rating = rating;
    this.user = user;
  }

  //getter
  public String getUser(){
    return user;
  }
  
  //getter
  public String getBook(){
    return book;
  }
  
  //getter
  public double getRating(){
    return rating;
  }

  //This part consists all the values inside of this class, and when you print it, you will see the values in this format.
  @Override
  public String toString(){
    return "User: " +user+ "\nBook: " +book+ "\nRating: " +rating+ "\n\n";
  }
}