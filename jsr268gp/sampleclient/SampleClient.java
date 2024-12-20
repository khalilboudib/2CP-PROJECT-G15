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
import java.util.Arrays;
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
	public static final int MODULUS_SIZE = 128;
	public static final int HASH_SIZE = 32;
	public static final int AES_KEY_SIZE = 16;
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
	public static final byte INS_CS_DH_PUBLIC_KEY = (byte)0x12;
	public static final byte INS_CS_DH_B = (byte)0x0B;
	public static final byte INS_CS_MODPOW=(byte) 0x10;
	public static final byte INS_CS_UID = (byte)0x11;
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
	public static final byte INS_CS_HASH=(byte)0x99;
	public static final byte INS_TEST = (byte)0x68;
	public static final byte INS_TEST2 = (byte)0x67;
	public static final byte INS_TEST3 = (byte)0x66;
	
	static Scanner myObj = new Scanner(System.in) ;
	static byte[] G = {
		(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02
		    };
	static byte[] n = {
		(byte) 0x80, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) 0x02, (byte) 0x03,
	    (byte) 0x04, (byte) 0x05, (byte) 0x01, (byte) 0x08, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x01,
	    (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x04, (byte) 0x06, (byte) 0x08,
	    (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02,
	    (byte) 0x02, (byte) 0x04, (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00,
	    (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05, (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04,
	    (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05, (byte) 0x01, (byte) 0x00,
	    (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05  };
	
	static byte[] P={
	    (byte) 0x8B, (byte) 0x61, (byte) 0xE2, (byte) 0x87,
	    (byte) 0xA4, (byte) 0xA1, (byte) 0x1C, (byte) 0xF8,
	    (byte) 0x5D, (byte) 0xD3, (byte) 0xD4, (byte) 0xC6,
	    (byte) 0xFA, (byte) 0xBD, (byte) 0x8B, (byte) 0x57,
	    (byte) 0xE2, (byte) 0x1D, (byte) 0xB0, (byte) 0x60,
	    (byte) 0xD1, (byte) 0xC3, (byte) 0xF9, (byte) 0xB3,
	    (byte) 0x1B, (byte) 0x16, (byte) 0xD8, (byte) 0x2F,
	    (byte) 0xB4, (byte) 0xBC, (byte) 0x71, (byte) 0xF3,
	    (byte) 0xE0, (byte) 0x2E, (byte) 0xC5, (byte) 0x78,
	    (byte) 0x79, (byte) 0x17, (byte) 0x49, (byte) 0xF8,
	    (byte) 0x3F, (byte) 0xFF, (byte) 0x21, (byte) 0xC4,
	    (byte) 0x7D, (byte) 0x41, (byte) 0xD9, (byte) 0x41,
	    (byte) 0xA5, (byte) 0xD9, (byte) 0x27, (byte) 0x6F,
	    (byte) 0xBB, (byte) 0xFF, (byte) 0xEF, (byte) 0xE4,
	    (byte) 0x35, (byte) 0x80, (byte) 0xF3, (byte) 0xF7,
	    (byte) 0x3B, (byte) 0xFF, (byte) 0xE3, (byte) 0xBF
	};
	static CardTerminal cad;
	static Pair <CardChannel, Card> cadPair;
	static CardChannel canal;
	static Card c;
	static ResponseAPDU respApdu;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, CardException {
		
			//Slectionner votre lecteur de carte
			//Le nom du lecteur se trouve dans la base de registres:
			//Hkey local machine/software / Microsoft/cryptography/calais/readers
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals list = tf.terminals();
		cad = list.getTerminal("ACS ACR1281 1S Dual Reader PICC 0");
		
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
		// Dconnexion
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
        System.out.println("5- Auth");
        System.out.println("6- lsjdf");
        System.out.println("7- Exit");
        System.out.println("8- cpt");
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
                respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_TEST, (byte)0x00, (byte)0x00, canal);
            	DH.printByteArray(respApdu.getData());
            	
            	break;
            
            // tests
            case 5:
            	/*respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_MODPOW, (byte)0x00, (byte)0x00, canal);
            	byte[] AA = DH.modularExponentiation(G, n, P);
            	DH.printByteArray(respApdu.getData());
            	DH.printByteArray(AA);*/
            	byte[] PP = DH.generateRandomPrime(1024).toByteArray();
            	PP = DH.adjustArray(PP, MODULUS_SIZE);
//            	 byte[] PPP = new byte[PP.length - 1];
//            	    
//            	    // Copy elements from PP starting from index 1 to modifiedArray
//            	    System.arraycopy(PP, 1, PPP, 0, PPP.length);
//            	    System.out.println("<< P >> generated -------------------: ");
//                	DH.printByteArray(PPP);
            	    
            	    
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_PUBLIC_KEY, (byte)0x00, (byte)0x00, PP, canal);
            
            	//System.out.println("here is your apdu:      " + respApdu);
            	// retreiving A = g^^n mod P
            	byte[] AA = respApdu.getData();
            	System.out.println("<< A >> received from card --------------");
            	DH.printByteArray(AA);
            	
// ******************************************* for server ( step 1 ) ***************************************

            	byte[] mm = DH.generateRandom(1024).toByteArray();
            	mm = DH.adjustArray(mm, MODULUS_SIZE);
//            	byte[] mmm = new byte[mm.length - 1];
//            	if(mm.length!=64){ 
//        	    // Copy elements from PP starting from index 1 to modifiedArray
//        	    System.arraycopy(mm, 1, mmm, 0, mmm.length);
//            	mm=mmm;
//            	}
            	System.out.println("<< m >> generated -------------------: ");
            	DH.printByteArray(mm);
            	
            	
            	byte[] BB = DH.modularExponentiation(G, mm, PP);
            	System.out.println("<< B >> generated -------------------: ");
            	DH.printByteArray(BB);
            	byte[] KK = DH.masqueFunction(DH.modularExponentiation(AA, mm, PP));
            	
            	System.out.println("<< K (server) >> generated -------------------: ");
            	DH.printByteArray(KK);
// ****************************************************** End for server ( step 1 ) *****************************
            	
            	
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_B, (byte)0x00, (byte)0x00, BB, canal);
            	System.out.println("<< B >> sent to card --------------");
            	byte[] KK2=respApdu.getData();
            	System.out.println("<< K (card) >> generated -------------------: ");
            	DH.printByteArray(KK2);

            	//KK = DH.masqueFunction(KK);
            	byte[] KAes = new byte[AES_KEY_SIZE];
            	System.arraycopy(KK, 0, KAes, 0, AES_KEY_SIZE);
            	System.out.println("<< K (server) >> masque function applied -------------------: ");
            	DH.printByteArray(KAes);
            	respApdu =APDUOps.sendApduToCard(CLA_APPLET, INS_CS_HASH, (byte)0x00, (byte)0x00, canal);
            	KK2=respApdu.getData();
            	System.out.println("<< K (card) >> masque function applied -------------------: ");
            	DH.printByteArray(KK2);
            	respApdu =APDUOps.sendApduToCard(CLA_APPLET, INS_CS_UID, (byte)0x00, (byte)0x00, canal);
            	byte[] UIDD = respApdu.getData();
            	System.out.println("<< UID >> received from card --------------");
            	DH.printByteArray(UIDD);
            	long cardNumber = DH.byteArrayToLong(UIDD);
            	
            	
// ******************************************************* for server ( step 1 - continue ) ****************************
            	// signature
            	byte[] privateKey = MyJDBC.getClientData(connection, cardNumber).getServerPrivateKey();
            	byte[][] concatenatedPrivateKey= RSAOps.separateNAndD(privateKey, MODULUS_SIZE);

            	// concatenate A and B to be signed
            	byte[] AB = DH.concat(AA, BB);
            	System.out.println("<< A and B >> concatenated -------------------: ");
            	DH.printByteArray(AB);
            	System.out.println("\n\n");
            	
            	//byte[] sign = DH.signData(BB, RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
            	byte[] toBeSigned = DH.hash(AB);
            	System.out.println("<< AB hash to be signed  >> server -------------------: ");
            	DH.printByteArray(toBeSigned);
            	byte[] sign = DH.signDataRSA(toBeSigned, RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
            	
            	
            	//byte[] sign = DH.signDataRSA(toBeSigned, RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
            	
            	System.out.println("<< Signature >> generated -------------------: ");
            	DH.printByteArray(sign);
			try {
				sign = AesCBC.encrypt_CBC(sign, KAes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	System.out.println("<< Signature >> encrypted in AES -------------------: ");
            	DH.printByteArray(sign);
// ****************************************************** END for server ( step 1 - continue ) **********************
            	
            	
            	
            	// signature encryption using AES 
            	
            	// sending the signature to the card
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_SIGN, (byte)0x00, (byte)0x00, sign, canal);
            	System.out.println("<< Signature >> sent to card --------------");
            	
            	System.out.println("<< Signature AES Decrypted >> in the card-------------->>>  ");
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_TEST3, (byte)0x00, (byte)0x00, canal);
            	DH.printByteArray(respApdu.getData());

            	
//            	respApdu =APDUOps.sendApduToCard(CLA_APPLET, INS_TEST2, (byte)0x00, (byte)0x00, canal);
//            	System.out.println("<< G hash from card after decryption  >> card -------------------: ");
//            	DH.printByteArray(respApdu.getData());
//            	
//            	
//            	System.out.println("<< G hash in the server  >> -------------------: ");
//            	DH.printByteArray(DH.hash(P));
            	
            	

            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_SIGN_STATUS, (byte)0x00, (byte)0x00, canal);
            	//boolean isVerified1 = (respApdu.getData()[0] == (byte)0x60);
            	System.out.println("<< Signature Verification >> in the card -------------->>>  ");
            	System.out.println(respApdu.getData()[0] == (byte)0x90);
            	System.out.println("\n\n");
            	
            	
            	// verifying signature in the server
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_DH_SIGN, (byte)0x00, (byte)0x00, canal);
            	System.out.println("<< Signature >> received from the card --------------");
            	byte[] tmp = respApdu.getData();
            	DH.printByteArray(tmp);
            	System.out.println("<< Signature decrypted Aes>> received from the card --------------");
    			
            	
            	
  // **************************************************************** for server ( step - 2 ) **********************************
            	try {
    				tmp = AesCBC.decrypt_CBC(tmp, KAes);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			DH.printByteArray(tmp);
            	System.out.println("<< hash to be compared ( after decryption ) >> in the server -------------->>>  ");
            	byte[] PublicKeyy = MyJDBC.getClientData(connection, cardNumber).getPublicKey();
            	byte[][] concatenatedPublicKeyy= RSAOps.separateNAndD(PublicKeyy, MODULUS_SIZE);
            	byte[] tmpp=new byte[HASH_SIZE];
            	
			byte[] signn;
			try {
				signn = DH.decrypt(tmp,RSAOps.createPublicKey(concatenatedPublicKeyy[0], concatenatedPublicKeyy[1]));
				System.arraycopy(signn, (MODULUS_SIZE - HASH_SIZE), tmpp, 0, tmpp.length);
				DH.printByteArray(tmpp);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("<< Signature Verification >> in the server -------------->>>  ");	
			System.out.println(Arrays.equals(tmpp, toBeSigned));
// ************************************************ END for server ( step - 2 ) *************************************
			
			

			System.out.println("\n\n");

          	break;
            case 6:
            	// auth with http 
            	
            	
            	break;
            case 7 :
                handleExit(connection) ;
                break;
            

            case 8 :
            	respApdu = APDUOps.sendApduToCard(CLA_APPLET, (byte)0x20, (byte)0x00, (byte)0x00, canal);
            	DH.printByteArray(respApdu.getData());
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
		System.out.println("server pub from server");
		DH.printByteArray(serverPublicMod);
        //sending keys to the card
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_EXP, (byte)0x00, (byte)0x00, cardPublicExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, cardPublicMod, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_EXP, (byte)0x00, (byte)0x00, cardPrivateExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_P, (byte)0x00, (byte)0x00, cardPrivateP, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_Q, (byte)0x00, (byte)0x00, cardPrivateQ, canal);
		
		// server public keys
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_EXP, (byte)0x00, (byte)0x00, serverPublicExp, canal);
		
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_MOD, (byte)0x00, (byte)0x00, serverPublicMod, canal);
		
		// sending the UID of the card
		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_UID, (byte)0x00, (byte)0x00, DH.longToBytes(userCardNumber), canal);
		//System.out.println("Here when generating user card number, remember?");
		//DH.printByteArray(DH.longToBytes(userCardNumber));
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
	
	// padding with zeros
	public static byte[] zeroPadding(byte[] data){
		
		byte[] newData = new byte[64];
		System.arraycopy(data, 0, newData, (64 - data.length), data.length);
		
		return newData;
	}
	
    public static byte[] reverseEndian(byte[] original) {
        byte[] reversed = new byte[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        return reversed;
    }

}