package jsr268gp.sampleclient;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

import javafx.util.Pair;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import jsr268gp.util.Util;

public class SampleClient {

	
	public SampleClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static final byte CLA_APPLET = (byte)0x80;
	public static final byte INS_CS_RSA_CARD_PUBLIC_MOD= (byte)0x01;
	public static final byte INS_CS_RSA_CARD_PUBLIC_EXP= (byte)0x02;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_MOD= (byte)0x03;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_EXP= (byte)0x04;
	public static final byte INS_CS_RSA_CARD_PRIVATE_P= (byte)0x05;
	public static final byte INS_CS_RSA_CARD_PRIVATE_Q= (byte)0x06;
	public static final byte INS_CS_RSA_CARD_PRIVATE_EXP= (byte)0x07;
	public static final byte INS_SC_RSA_CARD_PUBLIC_MOD= (byte)0x08;
	public static final byte INS_SC_UID = (byte)0x09;
	public static final byte INS_CS_DH_PUBLIC_KEY = (byte)0x0A;
	public static final byte INS_CS_DH_B = (byte)0x0B;
	
	public static final int MODULUS_SIZE = 128;
	
	static Scanner myObj = new Scanner(System.in) ;
	static byte[] G = {(byte)0x02};
	static CardTerminal cad;
	static Pair <CardChannel, Card> cadPair;
	static CardChannel canal;
	static Card c;
	static ResponseAPDU respApdu;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, CardException {
		
			//Sélectionner votre lecteur de carte
			//Le nom du lecteur se trouve dans la base de registres:
			//Hkey local machine/software / Microsoft/cryptography/calais/readers
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals list = tf.terminals();
		cad = list.getTerminal("Gemplus USB SmartCard Reader 0");
		
		// connect to the database
		Connection connection =  MyJDBC.connectToDataBase() ;
        display(connection);
        MyJDBC.closeConnection(connection);
			
			
		
		// conncet, create channel and select the applet
		/*Pair <CardChannel, Card> p = APDUOps.connectAndSelect(cad);
		CardChannel canal = p.getKey();
		Card c = p.getValue();
		
		byte[] arr = {(byte)0x77};
		ResponseAPDU response = APDUOps.sendApduToCard((byte)0x80, (byte)0x99, (byte)0x01, (byte)0x02, arr, canal);
		
		System.out.println("Reponse : "+Util.byteArrayToHexString(response.getBytes(), " "));*/
		// Déconnexion
		//c.disconnect(false);
			
		
	}
	
	public static void display(Connection connection) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, CardException{
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
        System.out.println("4- Get the card public key");
        System.out.println("5- Display all users");
        System.out.println("6- Auth");
        System.out.println("7- Exit");
        return;
    }
    public static int getChoice () {
        System.out.println("\n\n");
        int i ;
        while (true) {
            System.out.print("Enter your choice (Please enter a number between 1 and 7): ");
            i = myObj.nextInt() ;
            myObj.nextLine();
            if (i<=7 && i>=1) {
                break;
            }
            System.out.print("\n");

        }
        return i ;
    }

    public static boolean handleUserChoice (int choice , Connection connection) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, CardException{
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
            	
            	// ********************************************
                //cad = connectAndSelect();
            	// ********************************************
            	
        		//handleDisplayUsers(connection);
            	cadPair = APDUOps.connectAndSelect(cad);
            	canal = cadPair.getKey();
            	c = cadPair.getValue();
                respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, canal);
            	DH.printByteArray(respApdu.getData());
            	
            	break;
            
            // tests
            case 5:
            	handleDisplayUsers(connection) ;
            	break ;
            case 6 :
                handleExit(connection) ;
                break;
            
            case 7: 
            	// authentication
            	// generation of DH public key
            	byte[] P = DH.generateRandomPrime(1024).toByteArray();
            	
            	// **************************** CLIENT terminal *************************************
            	
            	// send P to the card and get the A
            	cadPair = APDUOps.connectAndSelect(cad);
            	canal = cadPair.getKey();
            	c = cadPair.getValue();	
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_PUBLIC_KEY, (byte)0x00, (byte)0x00, P, canal);
            	// retreiving A = g^^n mod P
            	            	
            	// **************************** SERVER ***********************************
            	
            	// generate random private m
            	byte[] m = DH.generateRandom(1024).toByteArray();
            	byte[] B = DH.modularExponentiation(G, m, P);
            	// getting the A from the card
            	//byte[] A = respApdu.dataOut;
            	
            	// simulating the A
            	byte[] n = DH.generateRandom(1024).toByteArray();
            	byte[] A = DH.modularExponentiation(G, n, P);
            	
            	
            	// sending B to the card
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_B, (byte)0x00, (byte)0x00, B, canal);
            	// getting the card number from the card
            	long cardNumber;
            	System.out.println("card heeeere: apdu: ");
            	System.out.println(respApdu);
            	cardNumber = DH.byteArrayToLong(respApdu.getData());
            	
            	System.out.println("card UID: " + cardNumber);
            	System.out.println("the m: ");
            	DH.printByteArray(m);
            	System.out.println("the B: ");
            	DH.printByteArray(B);
            	System.out.println("the A: ");
            	DH.printByteArray(A);
            	System.out.println("the P: ");
            	DH.printByteArray(P);
            	
            	// create digital signature for A and B
            	
            	// getting private key modulus and exponent from the data base
            	byte[] privateKey = MyJDBC.getClientData(connection, cardNumber).getServerPrivateKey();
            	byte[][] concatenatedPrivateKey= RSAOps.separateNAndD(privateKey, MODULUS_SIZE); 
            
            	// start signing
            	byte[] sign = DH.signData(addArrays(A, B), RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
            	
            	// find AES key
            	byte[] K = DH.modularExponentiation(A, m, P);
            	
            	
            	
            	
            	
            	
            	break;
            
        }
        return true ;
    }

    public static void handleAddUser (Connection connection) throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, CardException {

        Random random = new Random();
        long userCardNumber = Math.abs(random.nextLong());
        while (MyJDBC.searchClient(connection,userCardNumber)) {
            userCardNumber = Math.abs(random.nextLong());
        }

        System.out.println("Card Number was generated successfully...");
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

        // saving information to the database ------------------------------------------------------------
        
        cadPair = APDUOps.connectAndSelect(cad);
    	canal = cadPair.getKey();
    	c = cadPair.getValue();

        // generating rsa keys ----------------------------------------------------
		// card keys
		RSAOps cardRSA = new RSAOps(1024);
		// private
		byte[] cardPrivateExp = cardRSA.getPrivateKeyExponent();
		byte[] cardPrivateP = cardRSA.getPrivateKeyP();
		byte[] cardPrivateQ = cardRSA.getPrivateKeyQ();
		// public
		byte[] cardPublicExp = cardRSA.getPublicKeyExponent();
		byte[] cardPublicMod = cardRSA.getPublicKeyMod();
		
		
		
		
		
		// server keys
		RSAOps serverRSA = new RSAOps(1024);
		// private
		byte[] serverPrivateExp = serverRSA.getPrivateKeyExponent();
		byte[] serverPrivateMod = serverRSA.getPrivateKeyMod();
		byte[] serverPrivateP = serverRSA.getPrivateKeyP();
		byte[] serverPrivateQ = serverRSA.getPrivateKeyQ();
		// public
		byte[] serverPublicExp = serverRSA.getPublicKeyExponent();
		byte[] serverPublicMod = serverRSA.getPublicKeyMod();
		
        //sending keys to the card
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_EXP, (byte)0x00, (byte)0x00, cardPrivateExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_P, (byte)0x00, (byte)0x00, cardPrivateP, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_Q, (byte)0x00, (byte)0x00, cardPrivateQ, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_EXP, (byte)0x00, (byte)0x00, cardPublicExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, cardPublicMod, canal);
		
		// server public keys
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_EXP, (byte)0x00, (byte)0x00, serverPublicExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_MOD, (byte)0x00, (byte)0x00, serverPublicMod, canal);
		
		// sending the UID of the card
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_UID, (byte)0x00, (byte)0x00, DH.longToBytes(userCardNumber), canal);
		
		
        // saving all information to the database
        Client client = new Client(userCardNumber,userFirstName , userLastName , useraddress) ;
        // setting the keys
        client.setExpiringDate();
        client.setPublicKey(concatenateByteArrays(cardPublicMod, cardPublicExp));
        client.setServerPrivateKey(concatenateByteArrays(serverPrivateMod, serverPrivateExp));
        MyJDBC.addClient(connection,client);
        
        // saving information to the smart card-------------------------------------------------------------
        
        System.out.println("User was added successfully");
        
        // shutdown the card
        

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
        Client client = MyJDBC.getClientData(connection,cardNumber);
        if (client != null) {
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


    public static void handleDisplayUsers(Connection connection) throws SQLException {

        Client[] clients = MyJDBC.getClients(connection , 1000000 , 1) ;
        System.out.println(" Card Number      | First Name              | last Name               ");
        System.out.println("----------------------------------------------------------------------");
        for (Client client : clients) {
        	if (client != null) {
        		String userData = String.format("%-18d| %-25s|%-25s", client.getCardNumber(), client.getFirstName(), client.getLastName());
        		System.out.println(userData);
        		System.out.println("----------------------------------------------------------------------");
        	}

        }

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
    
    
	
	// concatenate byte arrays
	public static byte[] concatenateByteArrays(byte[] a, byte[] b) throws IOException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    outputStream.write(a);
	    outputStream.write(b);
	    return outputStream.toByteArray();
	}
	
	
	// addition of two byte arrays
	public static byte[] addArrays(byte[] array1, byte[] array2) {
        // Check if both arrays have the same length
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        // Create a new array to store the result
        byte[] result = new byte[array1.length];

        // Perform addition of corresponding elements
        for (int i = 0; i < array1.length; i++) {
            // Add the elements of the two arrays at index i
            result[i] = (byte) (array1[i] + array2[i]);
        }

        return result;
    }

}
