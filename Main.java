import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    static Scanner myObj = new Scanner(System.in) ;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection =  MyJDBC.connectToDataBase() ;
        display(connection);
        MyJDBC.closeConnection(connection);
    }


    public static void display(Connection connection) throws SQLException {
        boolean keep ;
        do {
            menu();
            int userChoice = getChoice() ;
            keep = handleUserChoice(userChoice,connection);
        } while (keep) ;
    }

    public static void menu() {
        System.out.println("----------------------------MENU-------------------------------");
        System.out.println("1- Add new user and card");
        System.out.println("2- Search for a user");
        System.out.println("3- Delete a user");
        System.out.println("4- Display all users");
        System.out.println("5- Exit");
        return;
    }
    public static int getChoice () {
        System.out.println("\n\n");
        int i ;
        while (true) {
            System.out.print("Enter your choice (Please enter a number between 1 and 5): ");
            i = myObj.nextInt() ;
            myObj.nextLine();
            if (i<=5 && i>=1) {
                break;
            }
            System.out.print("\n");

        }
        return i ;
    }

    public static boolean handleUserChoice (int choice , Connection connection) throws SQLException {
        switch (choice) {
            case 1 :
                handleAddUser(connection);
                break ;
            case 2 :
                handleSearchUser(connection);
                break ;
            case 3 :
                handleDeleteUser(connection) ;
                break ;
            case 4 :
                handleDisplayUsers(connection);
                break;
            case 5 :
                handleExit(connection) ;
                break;
        }
        return true ;
    }

    public static void handleAddUser (Connection connection) throws SQLException {

        Random random = new Random();
        long min = 0L; // 10^16
        long max = 999999999999999999L; // 10^18 - 1
        long userCardNumber = Math.abs(min + ((long) (random.nextDouble() * (max - min + 1))));
        while (MyJDBC.searchClient(connection,userCardNumber)) {
            userCardNumber = Math.abs(min + ((long) (random.nextDouble() * (max - min + 1))));
        }

        System.out.println("Card Number was generated successfully...");
        System.out.println("Card number is: " + userCardNumber);
        System.out.println("Please provide the informations we are asking below");
        System.out.print("Enter the user first name: ");
        String userFirstName = myObj.nextLine() ;
        System.out.print("\n");

        System.out.print("Enter the user last name: ");
        String userLastName = myObj.nextLine() ;
        System.out.print("\n");

        System.out.print("Enter the user address: ");
        String useraddress = myObj.nextLine() ;
        System.out.print("\n");

        Client client = new Client(userCardNumber,userFirstName , userLastName , useraddress) ;
        client.setExpiringDate();

        MyJDBC.addClient(connection,client);

        System.out.println("User was added successfully");

    }

    public static void handleSearchUser (Connection connection) throws SQLException {
        Long cardNumber = getCardNumber() ;
        System.out.print("\n");

        if (MyJDBC.searchClient(connection,cardNumber)) {
            System.out.println("User exists and has a card");
            // display user data
        }
        else {
            System.out.println("Number doesn't represent any card");
        }

    }

    public static void handleDeleteUser(Connection connection) throws SQLException {

        Long cardNumber = getCardNumber() ;
        System.out.print("\n");
        SearchResults resultSet = MyJDBC.getClientData(connection,cardNumber);
        if (resultSet.isFound()) {
            System.out.println("User exists and has a card");
            // display user printUserData(resultSet.getResultSet());
            System.out.print("Are you sure you want to delete user (y/n): ");
            String decision = myObj.next();
            if (decision.equals("y")) {
                MyJDBC.deleteClient(connection, cardNumber);
                System.out.println("Client deleted succesfully");
            } else {
                System.out.println("Delete Cancelled...");
            }
        }
        else {
            System.out.println("Number doesn't represent any card");
        }

    }


    public static void handleDisplayUsers(Connection connection) {

    }

    /* public static void printUserData(ResultSet resultSet) throws SQLException {
        long cardNumber = 0  ;
        Date expiringDate = null ;
        String userFirstName = null ;
        String userlastName = null ;
        cardNumber = resultSet.getLong("card_number");
        userFirstName = resultSet.getString("first_name");
        userlastName = resultSet.getString("last_name");



        System.out.println("--------------------------------------------------------------------");

            // Concatenate user data in one line
        String userData = String.format("%-18d| %-25s|%-25s%n", cardNumber, userFirstName, userlastName);
        System.out.println(userData);
        System.out.println("--------------------------------------------------------------------");
    } */

    public static void handleExit(Connection connection) {

        System.out.print("Are you sure you want to exit program (y/n): ");
        String decision = myObj.next();
        if (decision.equals("y")) {
            MyJDBC.closeConnection(connection);
            System.out.println("EXITING PROGRAM...");

            System.exit(0);
        }
    }

    public static long getCardNumber() {
        System.out.print("Enter the card Number: ");
        return Long.valueOf(myObj.nextLong());

    }
}
