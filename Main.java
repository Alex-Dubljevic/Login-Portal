import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    /*
     * Write a program that mimics a login prompt in which the user
     * will have to enter a valid user name and password.
     * The program will have text files with valid user names and passwords.
     * The program should read in the data from the text files into arrays
     * and then search the arrays to determine whether the user’s input is valid or
     * not.
     */
    // Step 0: Initialize Files and arrays etc.
    File file1 = new File("passwords.txt");
    Scanner inputFile1 = new Scanner(file1);

    File file2 = new File("usernames.txt");
    Scanner inputFile2 = new Scanner(file2);

    File file3 = new File("balances.txt");
    Scanner inputFile3 = new Scanner(file3);

    // Loop that goes through text file and counts how many lines there are
    int numUsers = 0;
    while (inputFile1.hasNextLine()) {
      inputFile1.nextLine();
      numUsers++;
    }
    inputFile1.close();
    // Reset scanner to top
    inputFile1 = new Scanner(file1);

    // Step 0.5: Initialize and Populate arrays with data from text files
    String[] passArray = new String[numUsers];
    String[] userArray = new String[numUsers];
    String[] balArray = new String[numUsers];

    for (int i = 0; i < numUsers; i++) {
      passArray[i] = inputFile1.nextLine();
      userArray[i] = inputFile2.nextLine();
      balArray[i] = inputFile3.nextLine();
    }
    // close files
    inputFile1.close();
    inputFile2.close();
    inputFile3.close();

    // Step 1: Create Keyboard scanner and other variables for storage
    boolean auth = true;
    boolean newAcc = false;
    boolean startup = true;
    String newUser = "";
    int attempts = 0;
    int userNum = 0;
    Scanner keyboard = new Scanner(System.in);

    // Step 1.1: Allow new users to be added, make sure to check for duplicate
    // usernames aswell as updating the old arrays with the new info
    System.out.println("Welcome to Secure Bank Login:\n");
    TimeUnit.SECONDS.sleep(1);
    // While loop to see if user wants to signup or login
    while (startup == true) {
      System.out.println("Login or Signup");
      String customerCheck = keyboard.nextLine();

      if (customerCheck.equalsIgnoreCase("signup")) {
        System.out.println("Signup processing starting...");
        newAcc = true;
        startup = false;
      } 
      if (customerCheck.equalsIgnoreCase("login")) {
        System.out.println("Login processing starting...");
        startup = false;
      } 
    }
      
    if (newAcc == true) {
      boolean dupeCheck = true;
      //check for duplicate username
      while (dupeCheck == true){
        boolean duped = false;
        System.out.println("Please enter your new username:");
        newUser = keyboard.nextLine();
      
        for (int i = 0; i < numUsers; i++){
          if(newUser.equals(userArray[i])){
            System.out.println("This username is already in use please choose a different one:");
            duped = true;
          }
        }
        //breaks if valid username
        if (duped == false){
          System.out.println("Valid Username \n");
          break;
        }
      }//end of while
      //add new password and balance
      boolean strengthCheck = false;
      String newPass = "";
      while (strengthCheck == false){
        boolean strong = false;
        System.out.println("Please enter your new password, it must be at least 8 characters long, and have at least 1 number and 1 capital letter");
        newPass = keyboard.nextLine();

        char[] charPass = newPass.toCharArray();

        if(newPass.length() >= 8){
          if (checkString(newPass) == true){
            strong = true;
          }  
        }

        if (strong == true){
          System.out.println("Valid Password \n");
          break;
        }
        System.out.println("Invalid Password please try again");
      }//end of while
 
      System.out.println("Please enter your deposit amount:");
      String newBal = keyboard.nextLine();

      //add new data to text files and add a new user

      FileWriter fw1 = new FileWriter("passwords.txt", true);
      PrintWriter outputFile1 = new PrintWriter(fw1);
      outputFile1.println(newPass);
      
      FileWriter fw2 = new FileWriter("usernames.txt", true);
      PrintWriter outputFile2 = new PrintWriter(fw2);
      outputFile2.println(newUser);
      
      FileWriter fw3 = new FileWriter("balances.txt", true);
      PrintWriter outputFile3 = new PrintWriter(fw3);
      outputFile3.println(newBal);

      outputFile1.close();
      outputFile2.close();
      outputFile3.close();

      System.out.println("Restarting...");
      System.exit(0);
    }//end of if
    
    // login prompt which asks the user to enter a username and password with while
    // loop that repeats if there is invalid input
    while (auth == true) {
      System.out.println("Please enter your username:");
      String userUsername = keyboard.nextLine();

      System.out.println("\nPlease enter your password:");
      String userPassword = keyboard.nextLine();

      System.out.println("Processing ...");
      TimeUnit.SECONDS.sleep(1);
      System.out.println("...");
      TimeUnit.SECONDS.sleep(1);
      System.out.println("...");
      TimeUnit.SECONDS.sleep(1);
      System.out.println("...");
      TimeUnit.SECONDS.sleep(1);

      // Step 3: Compare the user input to the stored info and either reject or accept
      // the info aswell as store the user number
      for (int i = 0; i < numUsers; i++) {
        if (userUsername.equals(userArray[i])) {
          if (userPassword.equals(passArray[i])) {
            userNum = i;
            auth = false;
          }
        }
      }
      // Breaks while loop here on successful login
      if (auth == false) {
        break;
      }

      // Step 4: If the system detects too many bad inputs it shuts down
      System.out.println("Incorrect Username or Password, Please try again. \n");
      attempts++;
      if (attempts >= 5) {
        System.out.println("The system has detected too many incorrect logins ... shutting down!");
        System.exit(0);
      }
    } // end of while loop

    // Step 5: now that the authentication was successful, display the user's
    // balance
    System.out.println("Sucessful Login!");
    TimeUnit.SECONDS.sleep(1);
    System.out.println("Your balance is: \n" + balArray[userNum]);

  } // end method
  private static boolean checkString(String str) {
    char ch;
    boolean capitalFlag = false;
    boolean numberFlag = false;
    for(int i=0;i < str.length();i++) {
        ch = str.charAt(i);
        if( Character.isDigit(ch)) {
            numberFlag = true;
        }
        else if (Character.isUpperCase(ch)) {
            capitalFlag = true;
        } 
        if(numberFlag && capitalFlag)
            return true;
    }
    return false;
}
}// end class