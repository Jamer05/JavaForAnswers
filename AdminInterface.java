import java.util.*;
import java.io.*;

class AdminInterface{

  //This method is the start of this class.
  public static void adminInterface(ArrayList<User> users, ArrayList<Book> books, ArrayList<String> bookNames){

    Scanner input = new Scanner(System.in);

    //This part asks you to choose from several options and the method will call that method to perform an action.
    AdminInterface:
    while (true){
      try {
        System.out.println("Welcome! Please pick one of the following options...\n1. Create new user account\n2. Add new product\n3. System statistics\n4. Exit admin account\nEnter your choice [1,2,3,4]:");
        int userDecision = Integer.parseInt(input.nextLine());

        switch (userDecision){
          case 1:
            createUsername(users);
            break;
          case 2:
            addProduct(books, bookNames);
            break;
          case 3:
            System.out.println("Number of users: "+users.size()+"\n"+users);
            System.out.println("Number of available products: "+books.size()+"\n"+books);
            break;
          case 4:
            break AdminInterface;
          default:
            System.out.print("Invalid Entry...");
            break;
        }

    } catch (Exception e) {
        System.out.println("Something went wrong.");
      }
    }
  }
  
  //This method is a method where you create a new user account, and this method is where you ask for the username.
  public static String createUsername(ArrayList<User> users) throws IOException{

    String username = "";

    Scanner input = new Scanner(System.in);

    System.out.println("Please create a unique username and password.");

    //This part ask for the username you want, and this will search if the username you entered is already taken and if it is, you can enter a new one until you have a unique username. This part also write your unique username inside a file.
    while (true){

      System.out.print("Create username: ");
      username = input.nextLine();

      if (new Scanner(new File("users.txt")).useDelimiter("\\Z").next().contains(username)) {
        System.out.println("Sorry, someone already have that as their username/password. Please enter a unique one.");
      } else {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.txt", true))) {
          bufferedWriter.write(username+" ");
        } catch (IOException e) {
          // Exception handling
        }
        createPassword(users, username);
        break;
      }
    }

    return username;
  }
  
  //This method is called when you successfully create a unique usernmame.
  public static void createPassword(ArrayList<User> users, String username) throws IOException{

    String password = "";

    Scanner input = new Scanner(System.in);

    //This part ask you to enter a unique password.
    while (true){

      System.out.print("Create password: ");
      password = input.nextLine();

      if (new Scanner(new File("users.txt")).useDelimiter("\\Z").next().contains(password)) {
        System.out.println("Sorry, someone already have that as their username/password. Please enter a unique one.");
      } else {
        break;
      }
    }

    //This part write your unique password inside of a file.
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.txt", true))) {
      bufferedWriter.write(password+"\n");
    } catch (IOException e) {
      // Exception handling
    }

    //This part add the username and password you create inside a list from the User class.
    User u2 = new User(username, password);
    users.add(u2);
  }
  
  //This part is where an admin can add a new product.
  public static void addProduct(ArrayList<Book> books, ArrayList<String> bookNames) {
    
    String book = "";
    String author = "";

    Scanner input = new Scanner(System.in);
    
    //This part ask for the name/title of the book.
    System.out.print("Book: ");
    book = input.nextLine();

    //This part ask for the name of the author.
    System.out.print("Author: ");
    author = input.nextLine();
    
    //This part writes the title of the book, the author, and the starting rate inside a file.
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("books.txt", true))) {
      bufferedWriter.write(book+", "+author+", "+"0.0\n");
    } catch (IOException e) {
      // Exception handling
    }

    //This part add the new product inside the list.
    Book book2 = new Book(book, author, 0.0);
    books.add(book2);
    bookNames.add(book);
  }
}