package jsr268gp.sampleapplet;

import javacard.framework.*;

import java.sql.*;

public class SampleTestApplet extends Applet {

	public static final byte CLA = (byte) 0x80;
	public static final byte INS_ECHO = (byte) 0x99;
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
	
	public static final byte DH_KEY_LENGTH = (byte)0x80;
	
	
	private byte[] cardPublicKeyMod = new byte[256];
	private byte[] cardPublicKeyExp = new byte[256];
	private byte[] serverPublicKeyMod = new byte[256];
	private byte[] serverPublicKeyExp = new byte[256];
	private byte[] cardPrivateKeyP = new byte[256];
	private byte[] cardPrivateKeyQ = new byte[256];
	private byte[] cardPrivateKeyExp = new byte[256];
	private byte[] cardUID = new byte[256];
	
	public boolean select () {
		return (true);
	}
	
	public void deselect () {
	}
	
	public SampleTestApplet () {
		super();
	}
	
	public static void install (byte [] bArray, short bOffset, byte bLength) throws ISOException {
		SampleTestApplet s = new SampleTestApplet ();
		s.register();
	}
	
	public void process(APDU apdu) throws ISOException{
		byte[] buffer = apdu.getBuffer();
		if (selectingApplet()) return;
		
		if (buffer[ISO7816.OFFSET_CLA] != CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		switch (buffer[ISO7816.OFFSET_INS]) {
		case INS_CS_RSA_CARD_PUBLIC_MOD:
			cardPublicKeyMod = receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_CARD_PUBLIC_EXP:
			cardPublicKeyExp = receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_SERVER_PUBLIC_MOD:
			serverPublicKeyMod = receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_SERVER_PUBLIC_EXP:
			serverPublicKeyExp = receiveFromClient(apdu);
			break;
	
		case INS_CS_RSA_CARD_PRIVATE_P:
			cardPrivateKeyP = receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_CARD_PRIVATE_Q:
			cardPrivateKeyQ = receiveFromClient(apdu);
			break;
			
		
		case INS_CS_RSA_CARD_PRIVATE_EXP:
			cardPrivateKeyExp = receiveFromClient(apdu);
			break;
		
		case INS_SC_RSA_CARD_PUBLIC_MOD:
			sendToClient(apdu, cardPublicKeyMod);
			break;
		
		case INS_SC_UID:
			cardUID = receiveFromClient(apdu);
			break;
		
		case INS_CS_DH_PUBLIC_KEY:
			// getting DH public key
			byte[] P = JCSystem.makeTransientByteArray(DH_KEY_LENGTH, JCSystem.CLEAR_ON_RESET);
			P = receiveFromClient(apdu);
			
			// calculate A
			// --------------------
			// calculate modular exponential
			break;
			
		case INS_CS_DH_B:
			// getting B from the server
			byte[] B = JCSystem.makeTransientByteArray(DH_KEY_LENGTH, JCSystem.CLEAR_ON_RESET);
			B = receiveFromClient(apdu);
			// sending card number
			sendToClient(apdu, cardUID);
			
			break;
		case(INS_ECHO):
			// fait echo des donnees du champs C_DATA (
//			short bytesRead = apdu.setIncomingAndReceive(); //Retourne taille des données
//			apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, bytesRead);
			
			//Echo du P1
//			apdu.setOutgoing();
//			apdu.setOutgoingLength((short) 1);
//			apdu.sendBytes((short)2,(short) 1);

			//Echo du P1 (version simplifiée)
			apdu.setOutgoingAndSend((short) 2,(short) 1);

			break;
		default :
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	static private void sendToClient(APDU apdu, byte[] data){
		
		// sending data to the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = (short)data.length;
		Util.arrayCopyNonAtomic(data, (short)0, buffer, (short)0, dataLength);
		apdu.setOutgoingAndSend((short) 0, dataLength);
		
	}
	
	static private byte[] receiveFromClient(APDU apdu){
		
		// receiving data from the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = apdu.setIncomingAndReceive();
		byte[] data = new byte[dataLength];
		Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, data, (short)0, dataLength);
		
		if(data.length < dataLength){
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}
		
		return data;
		
	}
	
	
}
