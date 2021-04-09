import java.util.*;
import java.io.*;

class Main {

  //This method is called to create the username of an admin account.
  public static String createUsername(ArrayList<Admin> admins) throws IOException{

    String username = "";

    Scanner input = new Scanner(System.in);

    System.out.println("Please create a unique username and password.");

    //This is the part where you ask for the username, and this will search the file if the username you entered is already there, so you can enter a new username, and this also a part where the unique username is being written into the file.
    while (true){

      System.out.print("Create username: ");
      username = input.nextLine();

      if (new Scanner(new File("admins.txt")).useDelimiter("\\Z").next().contains(username)) {
        System.out.println("Sorry, someone already have that as their username/password. Please enter a unique one.");
      } else {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("admins.txt", true))) {
          bufferedWriter.write(username+" ");
        } catch (IOException e) {
          // Exception handling
        }
        createPassword(admins, username);
        break;
      }
    }

    return username;
  }
  
  //This method is called after you successfully create a unique username for your admin account.
  public static void createPassword(ArrayList<Admin> admins, String username) throws IOException{

    String password = "";

    Scanner input = new Scanner(System.in);

    //This part ask for a unique password for the admin account.
    while (true){

      System.out.print("Create password: ");
      password = input.nextLine();

      if (new Scanner(new File("admins.txt")).useDelimiter("\\Z").next().contains(password)) {
        System.out.println("Sorry, someone already have that as their username/password. Please enter a unique one.");
      } else {
        break;
      }
    }

    //This part is where the unique password is being written into the file.
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("admins.txt", true))) {
      bufferedWriter.write(password+"\n");
    } catch (IOException e) {
      // Exception handling
    }

    //This part is where all the username and password that you create is being added to a list from the Admin class.
    Admin admin2 = new Admin(username, password);
    admins.add(admin2);
  }
  
  //This method is called when you want to login into an existing admin account.
  public static boolean adminAuthenticateUsername(ArrayList<Admin> admins) {

    boolean programStatus = false;
    int counter = 0;
    int attempts = 5;

    Scanner input = new Scanner(System.in);

    //This part ask for an existing username of the admin account, and you have several attempts to enter an existing username and if you do not enter an existing username after that, the system will end that part.
    UsernameLogin:
    while (true){
      
      counter++;
      System.out.print("Username: ");
      String username = input.nextLine();

      for (int i = 0; i <admins.size(); i++) {
        if (admins.get(i).getUsername().equals(username)) {
          programStatus = adminAuthenticatePassword(admins, i);
          return programStatus;
        }
      }

      if (programStatus==false){
        System.out.println("You've entered an invalid username. Please try again. You have "+attempts+" more attempts left.");
        attempts--;
      }
      
      if (counter==6){
        System.out.println("You have no more attempt. Please try again later.\nSystem is locking...");
        break UsernameLogin;
      }
    }
    return programStatus;
  }
  
  //This method is called when you successfully enter an existing username for the admin account.
  public static boolean adminAuthenticatePassword(ArrayList<Admin> admins, int userLocation) {

    boolean programStatus = false;
    int counter = 0;
    int attempts = 5;

    Scanner input = new Scanner(System.in);

    //This part ask for the corresponding password you have for the username you entered, and if you don not enter the right password after several attempts, the system will end.
    PasswordLogin:
    while (true) {
      
      counter++;
      System.out.print("Password: ");
      String password = input.nextLine();

      if (admins.get(userLocation).getPassword().equals(password)) {
        System.out.println("Login success!");
        programStatus = true;
        return programStatus;
      }else if (programStatus==false){
        System.out.println("Your password is wrong. Please try again. You have "+attempts+" more attempt.");
        attempts--;
      }
      
      if (counter==6){
        System.out.println("You have no more attempt. Please try again later.\nSystem is locking...");
        break PasswordLogin;
      }
    }
    return programStatus;
  }
  
  //This method is called when you want to login into an existing user account. This is called to asks for your username.
  public static String userAuthenticateUsername(ArrayList<User> users, ArrayList<Book> books, ArrayList<String> bookNames, ArrayList<Rating> ratings) {

    boolean programStatus = false;
    int counter = 0;
    int attempts = 5;
    String username = "";

    Scanner input = new Scanner(System.in);

    //This part is where you are ask to enter an existing username of the user account. You only have several attempts until the system locks.
    UsernameLogin:
    while (true){
      
      counter++;
      System.out.print("Username: ");
      username = input.nextLine();

      for (int i = 0; i <users.size(); i++) {
        if (users.get(i).getUsername().equals(username)) {
          programStatus = true;
          userAuthenticatePassword(users, i, books, bookNames, username, ratings);
          return username;
        }
      }

      if (programStatus==false){
        System.out.println("You've entered an invalid username. Please try again. You have "+attempts+" more attempts left.");
        attempts--;
      }
      
      if (counter==6){
        System.out.println("You have no more attempt. Please try again later.\nSystem is locking...");
        break UsernameLogin;
      }
    }

    return username;
  }

  //This method is called when you successfully enter an existing usernmame for the user account.
  public static boolean userAuthenticatePassword(ArrayList<User> users, int userLocation, ArrayList<Book> books, ArrayList<String> bookNames, String username, ArrayList<Rating> ratings) {

    boolean programStatus = false;
    int counter = 0;
    int attempts = 5;

    Scanner input = new Scanner(System.in);

    //This part ask for the corresponding password of the username you entered. You have several attempts to get your password right, until the system locks.
    PasswordLogin:
    while (true) {
      
      counter++;
      System.out.print("Password: ");
      String password = input.nextLine();

      if (users.get(userLocation).getPassword().equals(password)) {
        System.out.println("Login success!");
        programStatus = true;
        UserInterface.userInterface(username, books, bookNames, ratings);
        break PasswordLogin;
      }else if (programStatus==false){
        System.out.println("Your password is wrong. Please try again. You have "+attempts+" more attempt.");
        attempts--;
      }
      
      if (counter==6){
        System.out.println("You have no more attempt. Please try again later.\nSystem is locking...");
        break PasswordLogin;
      }
    }
    return programStatus;
  }

  //This is the main method where the static methods is being called from.
  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    ArrayList<User> users = new ArrayList<User>();

    //This is an action where the program reads a file and get the values, which is being entered inside a list.
    try(BufferedReader bufReader = new BufferedReader(new FileReader("users.txt"))){  
      String line = bufReader.readLine();
      while (line != null) {
        String [] usersUsernamePassword = line.split(" ");
        String username = usersUsernamePassword[0];
        String password = usersUsernamePassword[1];
        
        User u1 = new User(username, password);
        users.add(u1);
        line = bufReader.readLine();
      }

      bufReader.close();

    } catch (FileNotFoundException e) {
        // Exception handling
    } catch (IOException e) {
        // Exception handling
    }

    ArrayList<Admin> admins = new ArrayList<Admin>();
    
    //This is an action where the program reads a file and get the values, which is being entered inside a list.
    try(BufferedReader bufReader = new BufferedReader(new FileReader("admins.txt"))){  
      String line = bufReader.readLine();
      while (line != null) {
        String [] adminsUsernamePassword = line.split(" ");
        String username = adminsUsernamePassword[0];
        String password = adminsUsernamePassword[1];
        
        Admin admin1 = new Admin(username, password);
        admins.add(admin1);
        line = bufReader.readLine();
      }

      bufReader.close();

    } catch (FileNotFoundException e) {
        // Exception handling
    } catch (IOException e) {
        // Exception handling
    }

    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<String> bookNames = new ArrayList<String>();

    //This is an action where the program reads a file and get the values, which is being entered inside a list.
    try(BufferedReader bufReader = new BufferedReader(new FileReader("books.txt"))){  
      String line = bufReader.readLine();
      while (line != null) {
        String [] bookDetails = line.split(", ");
        String book = bookDetails[0];
        String author = bookDetails[1];
        double averageRating = Double.parseDouble(bookDetails[2]);
        
        Book book1 = new Book(book, author, averageRating);
        books.add(book1);
        bookNames.add(book);
        line = bufReader.readLine();
      }

      bufReader.close();

    } catch (FileNotFoundException e) {
        // Exception handling
    } catch (IOException e) {
        // Exception handling
    }

    ArrayList<Rating> ratings = new ArrayList<Rating>();
    
    //This is an action where the program reads a file and get the values, which is being entered inside a list.
    try(BufferedReader bufReader = new BufferedReader(new FileReader("listOfReviews.txt"))){  
      String line = bufReader.readLine();
      while (line != null) {
        String [] listOfRatings = line.split(", ");
        String user = listOfRatings[0];
        String book = listOfRatings[1];
        String rating = listOfRatings[2];

        double newRate = Double.valueOf(rating);
        
        Rating rating1 = new Rating(user, book, newRate);
        ratings.add(rating1);
        line = bufReader.readLine();
      }

      bufReader.close();

    } catch (FileNotFoundException e) {
        // Exception handling
    } catch (IOException e) {
        // Exception handling
    }
    
    //This part is where the main method starts. This part ask for several things, you can either create a new admin account, login into an admin account, login into an existing user account, or exit the program. This is the part where the main method called for the methods that needs to be performed.
    Opening:
    while (true){
      try {
        System.out.println("Welcome!\nPlease pick one of the following options....\n1. Create a new admin account\n2. Login into an existing admin account\n3. Login into an existing user account\n4. Exit program\nEnter your choice [1,2,3,4]:");
        int userDecision = Integer.parseInt(input.nextLine());

        switch (userDecision){
          case 1:
            createUsername(admins);
            break;
          case 2:
            if (adminAuthenticateUsername(admins)){
              AdminInterface.adminInterface(users, books, bookNames);
            }
            break;
          case 3:
            userAuthenticateUsername(users, books, bookNames, ratings);
            break;
          case 4:
            break Opening;
          default:
            System.out.println("Invalid Entry...");
            break;
        }

    } catch (Exception e) {
        System.out.println("Something went wrong...");
      }
    }
  }
}