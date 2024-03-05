package serverSecurityTestPack;

import javacard.framework.*;
import javacard.security.*;
import javacardx.crypto.*;
import javacardx.apdu.ExtendedLength;

public class serverSecurityTest extends Applet {

	public static final byte CLA_APPLET = (byte)0x80;
	public static final byte INS_CS_RSA_CARD_PUBLIC_MOD= (byte)0x01;
	public static final byte INS_CS_RSA_CARD_PUBLIC_EXP= (byte)0x02;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_MOD= (byte)0x03;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_EXP= (byte)0x04;
	public static final byte INS_CS_RSA_CARD_PRIVATE_P= (byte)0x05;
	public static final byte INS_CS_RSA_CARD_PRIVATE_Q= (byte)0x06;
	public static final byte INS_CS_RSA_CARD_PRIVATE_EXP= (byte)0x07;
	public static final byte INS_SC_RSA_CARD_PUBLIC_MOD= (byte)0x08;
	public static final byte INS_SC_TEST = (byte)0x0B;
	public static final byte INS_CS_TEST = (byte)0x0C;
	//public static final byte INS_CARD_PC = (byte)0x08;
	public static final byte INS_PC_CARD = (byte)0x09;
	public static final byte INS_RSA_ENCRYPT = (byte)0x0A;
	
	private byte[] cardPublicKeyMod = new byte[256];
	private byte[] cardPublicKeyExp = new byte[256];
	private byte[] serverPublicKeyMod = new byte[256];
	private byte[] serverPublicKeyExp = new byte[256];
	private byte[] cardPrivateKeyP = new byte[256];
	private byte[] cardPrivateKeyQ = new byte[256];
	private byte[] cardPrivateKeyExp = new byte[256];
	
	
	
	//private byte[] secret;
	
	
	private serverSecurityTest() {
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException {
		new serverSecurityTest().register();
	}
	

	public void sendToClient(APDU apdu, byte[] data){
		
		// sending data to the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = (short)data.length;
		Util.arrayCopyNonAtomic(data, (short)0, buffer, (short)0, dataLength);
		apdu.setOutgoingAndSend((short) 0, dataLength);
		
	}
	
	public byte[] receiveFromClient(APDU apdu){
		
		// receiving data from the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = apdu.getIncomingLength();
		apdu.setIncomingAndReceive();
		byte[] data = new byte[dataLength];
		Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, data, (short)0, dataLength);
		
		if(data.length < dataLength){
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}
		
		return data;
		
	}
	
	public void process(APDU apdu) throws ISOException {
		
		// avoid processing selection requests in this methode
		if(this.selectingApplet()) return;
		// getting apdu first bytes
		byte[] buffer = apdu.getBuffer();
		short dataLength;
		short offsetCData;
		
		// handling CLA
		if(buffer[ISO7816.OFFSET_CLA] != CLA_APPLET) {
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		}
		
		// findout the operation we are going to process
		switch (buffer[ISO7816.OFFSET_INS]){

			// receive public keys
		
		
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
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				
				
			
				
			/*case INS_RSA_ENCRYPT:
				
				// getting received data ( Extended APDU )
				buffer = apdu.getBuffer();
				dataLength = apdu.setIncomingAndReceive();
				byte[] pubKey = new byte[dataLength];
				offsetCData = apdu.getOffsetCdata();
				Util.arrayCopyNonAtomic(buffer, offsetCData, pubKey, (short)0, dataLength);
				// send data
				Util.arrayCopyNonAtomic(pubKey, (short)0, buffer, (short)0, dataLength);
				apdu.setOutgoingLength(dataLength);
				apdu.sendBytesLong(buffer, (short)0, dataLength);
				
				dataLength = apdu.getIncomingLength();
				
				short readLen = apdu.setIncomingAndReceive();
				short offset = 0;
				Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, pubKey, offset, readLen);
				short i = readLen;
				while(i < dataLength){
					
					offset += readLen;
					try{
						readLen = (short)apdu.receiveBytes(ISO7816.OFFSET_CDATA);
					}catch(Exception e){
						ISOException.throwIt(ISO7816.SW_UNKNOWN);
						return;
					}
					Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, pubKey, offset, readLen);
					i += readLen;
					
				}
				
				// sending the data to the client
				short toSend = dataLength;
				short sentLen;
				offset = 0;
				apdu.setOutgoingLength(toSend);
				short maxDataApdu = (short)(buffer.length - ISO7816.OFFSET_CDATA - 1);
				
				while(toSend > 0){
					
					sentLen = (short)Math.min(toSend, maxDataApdu);
					Util.arrayCopyNonAtomic(pubKey, (short)0, buffer, (short)0, sentLen);
					try{
						apdu.sendBytes(offset, sentLen);
					}catch(Exception e){
						ISOException.throwIt(ISO7816.SW_UNKNOWN);
						return;
					}
						
					toSend -= sentLen;
					
				}
				
				
				
				
				Util.arrayCopyNonAtomic(buffer, (short)apdu.getOffsetCdata(), pubKey, (short)0, dataLength);
				apdu.setOutgoing();
				try{
					apdu.setOutgoingLength(dataLength);
				}catch(Exception e){
					ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
				}
				apdu.sendBytesLong(pubKey, (short)0, dataLength);*/
				//Util.arrayCopyNonAtomic(pubKey, (short)0, buffer, (short)0, dataLength);
				
				//apdu.setOutgoingAndSend((short)0, dataLength);
				/*dataLength = buffer[ISO7816.OFFSET_LC];
				byte[] pubKey = new byte[dataLength];
				Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, pubKey, (short)0, dataLength);
				
				
				// init the key
				RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
				publicKey.setModulus(pubKey, (short)0, (short)128);
				publicKey.setExponent(pubKey, (short)128, (short)34);
				// init the cipher
				final Cipher rsaCipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
				rsaCipher.init(publicKey, Cipher.MODE_ENCRYPT);
				try{
					short decryptedDataLen = rsaCipher.doFinal(secret, (short)0, (short)secret.length, buffer, (short)0);
					apdu.setOutgoingAndSend((short)0, decryptedDataLen);
				}catch(Exception e){
					ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
				}
				break;
				
			default:
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	//public void encrypt(APDU apdu){
		
		// getting apdu data
		byte[] buffer = apdu.getBuffer();
		apdu.setIncomingAndReceive();
		
		short dataLength = buffer[ISO7816.OFFSET_LC];
		byte[] pubKey = new byte[dataLength];
		Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, pubKey, (short)0, dataLength);
		// init the key
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
		publicKey.setModulus(pubKey, (short)0, (short)128);
		publicKey.setExponent(pubKey, (short)128, (short)34);
		// init the cipher
		final Cipher rsaCipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
		rsaCipher.init(publicKey, Cipher.MODE_ENCRYPT);
		try{
			//short decryptedDataLen = rsaCipher.doFinal(secret, (short)0, (short)secret.length, buffer, (short)0);
			buffer[0] = (byte)0x61;
			buffer[1] = (byte)0x62;
			apdu.setOutgoingAndSend((short)0, (short)2);
		}catch(Exception e){
			ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
		}
		
		
	//}*/
		}
	}
}
