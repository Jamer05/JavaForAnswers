import java.util.*;
import java.io.*;

class UserInterface{
  
  //This method is the start of this class.
  public static void userInterface(String username, ArrayList<Book> books, ArrayList<String> bookNames, ArrayList<Rating> ratings){

    Scanner input = new Scanner(System.in);

    int userDecision;

    //This part ask you to rate a book or see information of the book.
    BookSection:
    while (true){
      try {
        System.out.println("Welcome!\nPlease choose from the following options...\n1. Rate books\n2. Books information\n3. Exit\nEnter your choice [1,2,3]:");
        userDecision = Integer.parseInt(input.nextLine());

        switch (userDecision){
          case 1:
            searchBook(username, books, bookNames, ratings);
            break;
          case 2:
            try(BufferedReader bufReader = new BufferedReader(new FileReader("books.txt"))){  
              String line1 = bufReader.readLine();
              while (line1 != null) {
                String [] bookDetails = line1.split(", ");
                String newBook = bookDetails[0];
                String author = bookDetails[1];
                double averageRating = Double.parseDouble(bookDetails[2]);
                
                line1 = bufReader.readLine();
              }

              bufReader.close();

            } catch (FileNotFoundException e) {
              // Exception handling
            } catch (IOException e) {
              // Exception handling
            }
            System.out.print(books);
            break;
          case 3:
            break BookSection;
          default:
            System.out.print("Invalid Entry...");
            break;
        }

    } catch (Exception e) {
        System.out.println("Something went wrong.");
      }
    }
  }

  public static String searchBook(String username, ArrayList<Book> books, ArrayList<String> bookNames, ArrayList<Rating> ratings) throws IOException{

    Scanner input = new Scanner(System.in);

    boolean bookAvailable = false;
    String book = "";

    UsernameLogin:
    while (true){
      
      System.out.println("Welcome! Please choose from the following books...");

      System.out.print(bookNames+"\n");

      System.out.print("Book: ");
      book = input.nextLine();
    
      for (int i = 0; i <books.size(); i++) {
        if (books.get(i).getBook().equals(book)) {

          File originalFile = new File("listOfReviews.txt");
          BufferedReader br = new BufferedReader(new FileReader(originalFile));

          String line = null;

          while ((line = br.readLine()) != null) {
            if (line.contains(username+", "+book)) {
              bookAvailable = true;
              editRateBooks(username, book, ratings);
              suggestionBox();
              break UsernameLogin;
            }else{
              bookAvailable = true;
              rateBooks(username, book, ratings);
              break UsernameLogin;
            }
          }

          br.close();
        }
      }

      if (bookAvailable==false){
        System.out.println("The book you entered is not available.");
      }
    }

    return book;
  }

  public static double rateBooks(String username, String book, ArrayList<Rating> ratings) throws IOException{

    Scanner input = new Scanner(System.in);

    double count = 0; 

    RatingBooks:
    while (true){
      try {
        System.out.print("Please enter the review you want to give: ");
        double rating = Double.parseDouble(input.nextLine());   

        File f1 = new File("listOfReviews.txt");
        FileReader fr1 = new FileReader(f1);
        BufferedReader br1 = new BufferedReader(fr1);
        String[] words = null;
        String s;
        String input1 = book;

        while((s = br1.readLine()) != null){
          words = s.split(", ");
          for (String word : words){
            if (word.equals(input1)){
              count++;
            }
          }
        }  
          
        while((s = br1.readLine()) != null){
          if (s.contains(book)) {
            String strCurrentBalance1 = s.substring(s.lastIndexOf(" "), s.length());
      
            if (strCurrentBalance1 != null || !strCurrentBalance1.trim().isEmpty()) {
              double accountBalance1 = (Double.parseDouble(strCurrentBalance1.trim()));
              changingRating(count, accountBalance1, book, rating);
            }
          }
        }

        Rating rating2 = new Rating(username, book, rating);
        ratings.add(rating2);
    
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("listOfReviews.txt", true))) {
          bufferedWriter.write(username+", "+book+", "+rating+"\n");
          break RatingBooks;
        } catch (IOException e) {
          // Exception handling
        }

      } catch (Exception e) {
        System.out.println("Something went wrong.");
      }
    }

    return count;
  }

  public static void editRateBooks(String username, String book, ArrayList<Rating> ratings) throws IOException{

    Scanner input = new Scanner(System.in);

    RatingBooks:
    while (true){
      try {

        System.out.print("Please enter the new review: ");
        double rating = Double.parseDouble(input.nextLine());

        File originalFile = new File("books.txt");
        BufferedReader br = new BufferedReader(new FileReader(originalFile));

        File tempFile = new File("tempfile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

        String line = null;

        while ((line = br.readLine()) != null) {
          if (line.contains(book)) {
          String strCurrentBalance = line.substring(line.lastIndexOf(" "), line.length());
      
          if (strCurrentBalance != null || !strCurrentBalance.trim().isEmpty()) {
            double accountBalance = (Double.parseDouble(strCurrentBalance.trim()));
            double newBalance = ((accountBalance*2)-rating)/2;
            System.out.println("Average Rating: "+newBalance);
            line = line.substring(0,line.lastIndexOf(" "))+" " + newBalance;
          }
        }
        pw.println(line);
        pw.flush();
      }
      pw.close();
      br.close();

      //Delete the original file
      if (!originalFile.delete()) {
        System.out.println("Could not delete file");
      }

      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(originalFile)){
        System.out.println("Could not rename file");
      }

      try {
        File newFile = new File("listOfReviews.txt");
        BufferedReader nf = new BufferedReader(new FileReader(newFile));

        File file = new File("tempfile.txt");
        PrintWriter hl = new PrintWriter(new FileWriter(file));

        String newLine = null;

        while ((newLine = nf.readLine()) != null) {
          if (newLine.contains(username+", "+book)) {
          String strCurrentBalance = newLine.substring(newLine.lastIndexOf(" "), newLine.length());
      
          if (strCurrentBalance != null || !strCurrentBalance.trim().isEmpty()) {
            double accountBalance = (Double.parseDouble(strCurrentBalance.trim()));
            double newBalance = accountBalance+rating;
            newLine = newLine.substring(0,newLine.lastIndexOf(" "))+" " +(newBalance-accountBalance) ;
          }
        }
        hl.println(newLine);
        hl.flush();
      }
      hl.close();
      nf.close();

      //Delete the original file
      if (!newFile.delete()) {
        System.out.println("Could not delete file");
      }

      //Rename the new file to the filename the original file had.
      if (!file.renameTo(newFile)){
        System.out.println("Could not rename file");
      }

    } catch (Exception f) {
        System.out.println("Something went wrong.");
      }

    } catch (Exception e) {
        System.out.println("Something went wrong.");
      }
    }
  }

  public static void changingRating(double count, double accountBalance1, String book, double rating) throws IOException{
    
    Rating:
    while (true){
      try {

        File originalFile = new File("books.txt");
        BufferedReader br = new BufferedReader(new FileReader(originalFile));

        File tempFile = new File("tempfile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

        String line = null;

        while ((line = br.readLine()) != null) {
          if (line.contains(book)) {
          String strCurrentBalance = line.substring(line.lastIndexOf(" "), line.length());
      
          if (strCurrentBalance != null || !strCurrentBalance.trim().isEmpty()) {
            double accountBalance = (Double.parseDouble(strCurrentBalance.trim()) +rating);
            double newBalance = (accountBalance*count);
            double newBalance1 = (newBalance-accountBalance1+rating)/count;
            System.out.println("Average Rating: "+newBalance1);
            line = line.substring(0,line.lastIndexOf(" "))+" " + newBalance1;
            suggestionBox();
            break Rating;
          }
        }
        pw.println(line);
        pw.flush();
      }
      pw.close();
      br.close();

      //Delete the original file
      if (!originalFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }

      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(originalFile)){
        System.out.println("Could not rename file");
      }

    } catch (Exception e) {
        System.out.println("Something went wrong.");
      }
    }
  }

  public static void suggestionBox(){

    ArrayList<String> bookSuggestionList = new ArrayList<String>();

    Scanner input = new Scanner(System.in);

    System.out.println("Thank you for rating! Please enter a book title you want to see in our store:)");

    System.out.print("Book: ");
    String bookName = input.nextLine();

    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("bookSuggestion.txt", true))) {
      bufferedWriter.write(bookName+"\n");
    } catch (IOException e) {
      // Exception handling
    }

    bookSuggestionList.add(bookName);
  }
}