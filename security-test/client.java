package clientPack;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javacard.framework.Util;

import com.sun.javacard.apduio.*;

//import com.sun.javacard.apduio.*;

public class client {

public static void main(String[] args) throws IOException, CadTransportException {
	
	final byte CLA_APPLET = (byte)0xA0;
	final byte INS_CARD_PC = (byte)0x00;
	final byte INS_PC_CARD = (byte)0x01;
	
	// TODO Auto-generated method stub
	CadT1Client cad;
	Socket sckClient;
	Scanner scanner = new Scanner(System.in);

	// openning socket
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
		return;
	}
	// powring the card
	try{
		cad.powerUp();
	}
	catch(Exception e){
		System.out.println("Error! Can not powerup the card");
		return;
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
		return;
	}else{
		System.out.println("Selection successful");
	}
	
	boolean stop = false;
	int choice;
	while(!stop){
		System.out.println("1- get the secret");
		System.out.println("-----------------");
		System.out.println("2- send the secret");
		System.out.println("-----------------");
		System.out.print("Your choice is: ");
		choice = scanner.nextInt();
		switch(choice){
		case 1:
			// getting the secret from the server
			apdu = new Apdu();
			apdu.command[Apdu.CLA] = CLA_APPLET;
			apdu.command[Apdu.P1] = 0x00;
			apdu.command[Apdu.P2] = 0x00;
			apdu.command[Apdu.INS] = INS_CARD_PC;
			apdu.setLe(0x7F);
			cad.exchangeApdu(apdu);
			// getting the response
			if(apdu.getStatus() != 0x9000){
				System.out.println("Error occured, can not fetch the secret, error code: "+ String.format("%02X", apdu.getStatus()));
			}
			// getting the data of the response
			byte[] secret = apdu.dataOut;
			String myName = new String(secret, StandardCharsets.UTF_8);
			System.out.println("my name is: " + myName);
			break;
		case 2:
			//String hisName = "chakib";
			//byte[] buffer = hisName.getBytes(StandardCharsets.UTF_8);
			byte[] buffer = {0x61, 0x62, 0x63};
			apdu = new Apdu();
			apdu.command[Apdu.CLA] = CLA_APPLET;
			apdu.command[Apdu.P1] = 0x00;
			apdu.command[Apdu.P2] = 0x00;
			apdu.command[Apdu.INS] = INS_PC_CARD;
			apdu.setLe(0x7F);
			apdu.setDataIn(buffer);
			
			cad.exchangeApdu(apdu);
			// getting the response
			if(apdu.getStatus() != 0x9000){
				System.out.println("Error occured, can not fetch the secret, error code: "+ String.format("%02X", apdu.getStatus()));
			}
			break;
		default:
			System.out.println("Invalid choice");
			stop = true;

		}
	}
	cad.powerDown();
	
	
	
	
}


}