import java.util.*;

class Book{

  private String book, author;
  private double averageRating = 0;
  
  //This is a constructor method. 
  public Book(String book, String author, double averageRating){
    this.book = book;
    this.author = author;
    this.averageRating = averageRating;
  }

  //getter
  public String getBook(){
    return book;
  }
  
  //getter
  public String getAuthor(){
    return author;
  }

  //getter
  public double getAverageRating(){
    return averageRating;
  }

  //This part consists all the values inside of this class, and when you print it, you will see the values in this format.
  @Override
  public String toString(){
    return "Book: " +book+ "\nAuthor: " +author+ "\nRating: " +averageRating+ "\n\n";
  }
}