package clientPack;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;










import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.sun.javacard.apduio.Apdu;
import com.sun.javacard.apduio.CadT1Client;
import com.sun.javacard.apduio.CadTransportException;


public class clientMain {

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
	public static final byte INS_GET_UID = (byte)0x0C;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_ECHO = (byte)0x99;
	public static final byte INS_SC_N = (byte)0x98;
	public static final byte INS_CS_A = (byte)0x97;
	public static final byte INS_CS_DH_K = (byte)0x96;
	public static final byte INS_SC_DH_K = (byte)0x95;
	public static final byte INS_GET_SERVER_PUB_MOD = (byte)0x94;
	public static final byte INS_GET_SERVER_PUB_EXP = (byte)0x93;
	public static final byte INS_GET_CARD_PUB_MOD = (byte)0x92;
	public static final byte INS_GET_CARD_PUB_EXP = (byte)0x91;
	public static final byte INS_CS_ENC_AES = (byte)0x90;
	public static final byte INS_CS_DEC_AES = (byte)0x89;
	public static final byte INS_TEST_K = (byte)0x88;
	public static final byte INS_TEST_SIGN = (byte)0x87;
	public static final byte INS_TEST = (byte)0x92;
	
	public static final int MODULUS_SIZE = 64;
	
	static Scanner myObj = new Scanner(System.in) ;
	static Apdu respApdu;
	static Apdu sendApdu;
	static CadT1Client cad;
	static Socket sckClient;
	static byte[] G = {(byte)0x02};
	
	public static void main(String[] args) throws Exception {

		Connection connection =  MyJDBC.connectToDataBase() ;
		cad = connectAndSelect();
        display(connection);
        MyJDBC.closeConnection(connection);
		
	}
	
	public static void display(Connection connection) throws Exception {
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
        System.out.println("5- test");
        System.out.println("6- Exit");
        System.out.println("7- Auth");
        return;
    }
    public static int getChoice () {
        System.out.println("\n\n");
        int i ;
        while (true) {
            System.out.print("Enter your choice (Please enter a number between 1 and 5): ");
            i = myObj.nextInt() ;
            myObj.nextLine();
            if (i<=10 && i>=1) {
                break;
            }
            System.out.print("\n");

        }
        return i ;
    }

    public static boolean handleUserChoice (int choice , Connection connection) throws Exception {
        switch (choice) {
            case 1 :
				handleAddUser(connection);
                break ;
            case 2 :
                handleSearchUser(connection);
                break ;
            case 3 :
                //handleDeleteUser(connection) ;
            	//cad = connectAndSelect();
                respApdu = sendApduToCard(CLA_APPLET, INS_ECHO, (byte)0x00, (byte)0x00, cad);
                System.out.println(respApdu);
            	break ;
            case 4 :
            	
            	// ********************************************
                //cad = connectAndSelect();
            	// ********************************************
            	
        		//handleDisplayUsers(connection);
            	//cad = connectAndSelect();
//                respApdu = sendApduToCard(CLA_APPLET, INS_SC_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, cad);
//            	System.out.println(respApdu);
            	byte[] data = {
        				(byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16,
        			    (byte) 0x28, (byte) 0xAE, (byte) 0xD2, (byte) 0xA6,
        			    (byte) 0xAB, (byte) 0xF7, (byte) 0x97, (byte) 0x67,
        			    (byte) 0x76, (byte) 0x5D, (byte) 0x2E, (byte) 0x47
        			};
            	byte[] KeyValue = {
    					(byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16,
    				    (byte) 0x28, (byte) 0xAE, (byte) 0xD2, (byte) 0xA6,
    				    (byte) 0xAB, (byte) 0xF7, (byte) 0x97, (byte) 0x67,
    				    (byte) 0x76, (byte) 0x5D, (byte) 0x2E, (byte) 0x47
    			};
            	System.out.println("Cipher:");
            	DH.printByteArray(AesCBC.encrypt_CBC(data, KeyValue));
            	System.out.println("Plain:");
            	DH.printByteArray(AesCBC.decrypt_CBC(AesCBC.encrypt_CBC(data, KeyValue), KeyValue));
            	//cad.powerDown();
                
            	break;
            	
            // tests
            case 5:
            	
                respApdu = sendApduToCard(CLA_APPLET, INS_CS_ENC_AES, (byte)0x00, (byte)0x00, cad);
                System.out.println(respApdu);
                respApdu = sendApduToCard(CLA_APPLET, INS_CS_DEC_AES, (byte)0x00, (byte)0x00, cad);
                System.out.println(respApdu);
            	
    			
            	break;
            	
            case 6 :
                handleExit(connection) ;
                break;

            
            case 7: 
            	// authentication
            	// generation of DH public key
            	byte[] P = DH.generateRandomPrime(512).toByteArray();
            	System.out.println("<< P >> generated -------------------: ");
            	DH.printByteArray(P);
            	
            	// **************************** CLIENT terminal *************************************
            	
            	// send P to the card and get the A
            	
            	respApdu = sendApduToCard(CLA_APPLET, INS_CS_DH_PUBLIC_KEY, (byte)0x00, (byte)0x00, P, cad);
            	System.out.println("<< P >> sent to card --------------");
            	System.out.println("\n\n");
            	// retreiving A = g^^n mod P
            	//byte[] A = respApdu.dataOut;*
            	byte[] n = respApdu.dataOut;
            	System.out.println("<< n >> received from card --------------");
            	DH.printByteArray(n);
            	System.out.println("\n\n");
            	byte[] A = getA(P, n);
            	System.out.println("<< A >> received from card ( not really ) --------------");
            	DH.printByteArray(A);
            	System.out.println("\n\n");
            	// getting card number
            	respApdu = sendApduToCard(CLA_APPLET, INS_GET_UID, (byte)0x00, (byte)0x00, cad);
            	long cardNumber = DH.byteArrayToLong(respApdu.dataOut);
            	System.out.println("<< card UID >> received from card --------------");
            	System.out.println(cardNumber);
            	System.out.println("\n\n");

            	            	
            	// **************************** SERVER ***********************************
            	
            	// generate random private m
            	byte[] m = DH.generateRandom(512).toByteArray();
            	System.out.println("<< m >> generated -------------------: ");
            	DH.printByteArray(m);
            	System.out.println("\n\n");
            	
            	byte[] B = DH.modularExponentiation(G, m, P);
            	System.out.println("<< B >> generated -------------------: ");
            	DH.printByteArray(B);
            	System.out.println("\n\n");
            	
            	// find AES key
            	byte[] K = DH.modularExponentiation(A, m, P);
            	System.out.println("<< K (server) >> generated -------------------: ");
            	DH.printByteArray(K);
            	K = DH.masqueFunction(K);
            	System.out.println("<< K (server) >> masque function applied -------------------: ");
            	DH.printByteArray(K);
            	System.out.println("\n\n");
            	
            	// signature
            	byte[] privateKey = MyJDBC.getClientData(connection, cardNumber).getServerPrivateKey();
            	byte[][] concatenatedPrivateKey= RSAOps.separateNAndD(privateKey, MODULUS_SIZE);

            	// concatenate A and B to be signed
            	byte[] AB = DH.concat(A, B);
            	System.out.println("<< A and B >> concatenated -------------------: ");
            	DH.printByteArray(AB);
            	System.out.println("\n\n");
            	
            	
            	byte[] sign = DH.signData(AB, RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
            	//byte[] sign = DH.signData(data, RSAOps.createPrivateKey(serverPrivMod, serverPrivExp));
            	System.out.println("<< Signature >> generated -------------------: ");
            	DH.printByteArray(sign);
            	sign = AesCBC.encrypt_CBC(sign, K);
            	System.out.println("<< Signature >> encrypted in AES -------------------: ");
            	DH.printByteArray(sign);
            	System.out.println("\n\n");
            	// signature encryption using AES 

            	
            	// sending B to the card
            	respApdu = sendApduToCard(CLA_APPLET, INS_CS_DH_B, (byte)0x00, (byte)0x00, B, cad);
            	System.out.println("<< B >> sent to card --------------");
            	System.out.println("\n\n");
            	
            	// this is supposed to be calculated within the card
            	byte[] K2 = DH.modularExponentiation(B, n, P);
            	System.out.println("<< K (card) >> generated -------------------: ");
            	K2 = DH.masqueFunction(K2);
            	System.out.println("<< K (card) >> masque function applied -------------------: ");
            	DH.printByteArray(K2);
            	respApdu = sendApduToCard(CLA_APPLET, INS_CS_DH_K, (byte)0x00, (byte)0x00, K2, cad);
            	System.out.println("<< K >> sent -------------------: ");
            	System.out.println("\n\n");
            	
            	// sending the signature to the card
            	respApdu = sendApduToCard(CLA_APPLET, INS_CS_DH_SIGN, (byte)0x00, (byte)0x00, sign, cad);
            	System.out.println("<< Signature >> sent to card --------------");
            	System.out.println("\n\n");
            	

            	respApdu = sendApduToCard(CLA_APPLET, INS_SC_SIGN_STATUS, (byte)0x00, (byte)0x00, cad);
            	boolean isVerified1 = (respApdu.dataOut[0] == (byte)0x60);
            	System.out.println("<< Signature Verification >> in the card -------------->>>  " + isVerified1);
            	System.out.println("\n\n");


            	
            	// verifying the signature of the card
            	respApdu = sendApduToCard(CLA_APPLET, INS_SC_DH_SIGN, (byte)0x00, (byte)0x00, cad);
            	sign = respApdu.dataOut;
            	System.out.println("<< Encrypted Signature >> received from card --------------");
            	DH.printByteArray(sign);
            	
            	// decrypting the signature from AES 128
            	sign = AesCBC.decrypt_CBC(sign, K);
            	System.out.println("<< Decrypted Signature >> --------------");
            	DH.printByteArray(sign);
            	System.out.println("\n\n");
            	
            	// verification
            	byte[] publicKey1 = MyJDBC.getClientData(connection, cardNumber).getPublicKey();
            	byte[][] concatenatedPublicKey= RSAOps.separateNAndD(publicKey1, MODULUS_SIZE);
            	boolean isVerified2 = DH.verifySignature(AB, sign, RSAOps.createPublicKey(concatenatedPublicKey[0], concatenatedPublicKey[1]));
            	System.out.println("<< Signature Verification >> in the server --------------: " + isVerified2);
            	System.out.println("\n\n");
//            	System.out.println("<< K >> received -------------------: ");
//            	respApdu = sendApduToCard(CLA_APPLET, INS_SC_DH_K, (byte)0x00, (byte)0x00, cad);
//            	K2 = respApdu.dataOut; 
//            	DH.printByteArray(K2);
            	// getting the card number from the card


            	
            	
            	
            	// create digital signature for A and B
            	
            	// getting private key modulus and exponent from the data base
            	// error to fix heeeeeeeeeeeeeeeeeeeeere
            	//Client client2 = MyJDBC.getClientData(connection, cardNumber);
            	//System.out.println("heere our client: " + client2);
            	//String valueAsString = "6208778557861222465";
                //long longValue = Long.parseLong(valueAsString);


            	// here you should get the server public key of the server from the card
//            	byte[] modulusBytes = sendApduToCard(CLA_APPLET, INS_GET_SERVER_PUB_MOD, (byte)0x00, (byte)0x00, cad).dataOut;
//            	byte[] publicExponentBytes = sendApduToCard(CLA_APPLET, INS_GET_SERVER_PUB_EXP, (byte)0x00, (byte)0x00, cad).dataOut;
//            	boolean isVerified = DH.verifySignature(A, sign, RSAOps.createPublicKey(modulusBytes, publicExponentBytes));
//            	System.out.println("<< Signature is verified >>-------------------: " + isVerified);
            	
  	
            	break;
            
        }
        return true ;
    }

    public static void handleAddUser (Connection connection) throws SQLException, IOException, CadTransportException, NoSuchAlgorithmException, InvalidKeySpecException {

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
        
        

        // generating rsa keys ----------------------------------------------------
		// card keys
		RSAOps cardRSA = new RSAOps(512);
		// private
		byte[] cardPrivateExp = cardRSA.getPrivateKeyExponent();
		byte[] cardPrivateP = cardRSA.getPrivateKeyP();
		byte[] cardPrivateQ = cardRSA.getPrivateKeyQ();
		// public
		byte[] cardPublicExp = cardRSA.getPublicKeyExponent();
		byte[] cardPublicMod = cardRSA.getPublicKeyMod();
		
		
		
		
		
		// server keys
		RSAOps serverRSA = new RSAOps(512);
		// private
		byte[] serverPrivateExp = serverRSA.getPrivateKeyExponent();
		byte[] serverPrivateMod = serverRSA.getPrivateKeyMod();

		byte[] serverPrivateP = serverRSA.getPrivateKeyP();
		byte[] serverPrivateQ = serverRSA.getPrivateKeyQ();
		// public
		byte[] serverPublicExp = serverRSA.getPublicKeyExponent();
		byte[] serverPublicMod = serverRSA.getPublicKeyMod();

		
        //sending keys to the card
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_EXP, (byte)0x00, (byte)0x00, cardPrivateExp, cad);
		
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_P, (byte)0x00, (byte)0x00, cardPrivateP, cad);
		
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_Q, (byte)0x00, (byte)0x00, cardPrivateQ, cad);
		
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_EXP, (byte)0x00, (byte)0x00, cardPublicExp, cad);
		
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, cardPublicMod, cad);
		
		// server public keys
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_EXP, (byte)0x00, (byte)0x00, serverPublicExp, cad);
		
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_MOD, (byte)0x00, (byte)0x00, serverPublicMod, cad);
		
		// sending the UID of the card
		respApdu = sendApduToCard(CLA_APPLET, INS_SC_UID, (byte)0x00, (byte)0x00, DH.longToBytes(userCardNumber), cad);
		
		
        // saving all information to the database
		System.out.println("Keys sent to card successfully!!");
		
		
        // saving all information to the database
        Client client = new Client(userCardNumber,userFirstName , userLastName , useraddress) ;
        // setting the keys
        client.setExpiringDate();
        client.setPublicKey(concatenateByteArrays(cardPublicMod, cardPublicExp));
        client.setServerPrivateKey(concatenateByteArrays(serverPrivateMod, serverPrivateExp));
        MyJDBC.addClient(connection,client);
        
        // saving information to the smart card-------------------------------------------------------------
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
    
    public static Apdu sendApduToCard (byte cla , byte ins , byte p1 , byte p2 , byte[] data, CadT1Client cad) throws IOException, CadTransportException {
	    Apdu apdu = new Apdu() ;
	    apdu.command[Apdu.CLA] = cla;
	    apdu.command[Apdu.INS] = ins;
	    apdu.command[Apdu.P1] = p1;
	    apdu.command[Apdu.P2] = p2;
	    //apdu.setLc(data.length);
	    apdu.setDataIn(data);
	    //apdu.setLe(0x7F);
	    cad.exchangeApdu(apdu);
	    return apdu ;
	}

	public static Apdu sendApduToCard (byte cla , byte ins , byte p1 , byte p2 , CadT1Client cad) throws IOException, CadTransportException {
	    Apdu apdu = new Apdu() ;
	    apdu.command[Apdu.CLA] = cla;
	    apdu.command[Apdu.INS] = ins;
	    apdu.command[Apdu.P1] = p1;
	    apdu.command[Apdu.P2] = p2;
	    apdu.setLc(0x00);
	    //apdu.setLe(0x7F);
	    cad.exchangeApdu(apdu);
	    return apdu ;
	}
	
	// concatenate byte arrays
	public static byte[] concatenateByteArrays(byte[] a, byte[] b) throws IOException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    outputStream.write(a);
	    outputStream.write(b);
	    return outputStream.toByteArray();
	}
	
	
	// connect to simulator and select applet
	public static CadT1Client connectAndSelect() throws IOException, CadTransportException{
	
		
		
		try{
			sckClient = new Socket("localhost", 9025);
			sckClient.setTcpNoDelay(true);
			BufferedInputStream input = new BufferedInputStream(sckClient.getInputStream());
			BufferedOutputStream output = new BufferedOutputStream(sckClient.getOutputStream());
			// creation of the reader with the previous input and output streams
			cad = new CadT1Client(input, output);
		}
		catch(Exception e){
			System.out.println("Error! Can not open socket or create the reader");
		
		}
		// powring the card
		try{
			cad.powerUp();
		}
		catch(Exception e){
			System.out.println("Error! Can not powerup the card");
			
		}
	
		// Beginning of conversations with the card
	
		// Select the applet
		Apdu apdu = new Apdu();
		apdu.command[Apdu.CLA] = (byte)0x00;
		apdu.command[Apdu.INS] = (byte)0xA4;
		apdu.command[Apdu.P1] = 0x04;
		apdu.command[Apdu.P2] = 0x00;
		byte[] appletAID = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,0x07, 0x08, 0x09, 0x00, 0x00 };
		apdu.setDataIn(appletAID);
		cad.exchangeApdu(apdu);
		// check the response
		if(apdu.getStatus() != 0x9000){
			System.out.println("Error occured. Selection is not successful");
			
		}else{
			System.out.println("Selection successful");
		}
		
		return cad;
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
	
	public static byte[] getA(byte[] P, byte[]n) throws IOException, CadTransportException{
		
			
		byte[] A = DH.modularExponentiation(G, n, P);
		respApdu = sendApduToCard(CLA_APPLET, INS_CS_A, (byte)0x00, (byte)0x00, A, cad);
		if(respApdu.getStatus() != 0x9000){
			System.out.println("Couldn't send the A to the card");
		}
		return A;
	}
	
	
	

}
