package serverSecurityTestPack;

import javacard.security.Signature;

import javacard.framework.*;
import javacard.security.*;
import javacardx.crypto.*;

public class serverSecurityTest extends Applet {

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
	public static final byte INS_GET_UID = (byte)0x0C;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_TEST = (byte)0x92;
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
	
	public static final byte DH_KEY_LENGTH = (byte)0x80;
	public static final byte[] SUCCESS = {(byte)0x60};
	public static final byte[] FAIL = {(byte)0x61};
	
	
	private byte[] cardPublicKeyMod = new byte[256];
	private byte[] cardPublicKeyExp = new byte[256];
	private byte[] serverPublicKeyMod = new byte[256];
	private byte[] serverPublicKeyExp = new byte[256];
	private byte[] cardPrivateKeyP = new byte[256];
	private byte[] cardPrivateKeyQ = new byte[256];
	private byte[] cardPrivateKeyExp = new byte[256];
	private byte[] cardUID = new byte[256];
	private static byte cpt ;
	
	private byte[] G = new byte[64];
	private byte[] P;
	private byte[] n;
	private byte[] A;
	private byte[] K;
	private byte[] AB;
	private byte[] B;
	private byte[] signEnc;
	private boolean isVerified;
	private byte[] signDec;
	
	private RandomData randomData;
	//public static Cipher cipher;
	
	
	byte[] rsaPublic = new byte[64];  
    byte[] rsaPrivate = new byte[64];  
    byte[] rsaPublicMod = new byte[64];    
    RSAPublicKey pub; 
    KeyPair pair;
    AES AESIns;
    
	
	Signature signatureIns;
	
	public boolean select () {
		 
		
		pair = new KeyPair(KeyPair.ALG_RSA, (short) 512);  
        pair.genKeyPair();  
        pub = (RSAPublicKey) pair.getPublic();
	    
		//pub = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, false);
	    cpt = 0;
	    isVerified = false;
	    return (true);
	}
	
	public void deselect () {
	}
	
	protected serverSecurityTest () {
		randomData = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);
		
		signatureIns = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);

	}
	
	public static void install (byte [] bArray, short bOffset, byte bLength) throws ISOException {
		serverSecurityTest s = new serverSecurityTest ();
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
			P = receiveFromClient(apdu);
			// calculate A
			// generating random private n
			n = generateRandomNumber();
			//sendToClient(apdu, n);
			// RSA, send A
			sendToClient(apdu, n);

			break;

			
		case INS_CS_DH_B:
			// getting B from the server
			B = receiveFromClient(apdu);


			break;
		case(INS_CS_DH_SIGN):

			signEnc = receiveFromClient(apdu);
			signDec = new byte[64];
			// decrypting signature from AES
			AESIns = new AES();
			AESIns.keySetUp(K);
			AESIns.AesDecrypt(signEnc, signDec, (short)signEnc.length);
			// concatenate A and B
			AB = concat(A, B);
			//verifying signature
			RSAPublicKey publicKey = (RSAPublicKey) createPublicKey(serverPublicKeyMod, serverPublicKeyExp);
			isVerified = verifySignature(AB, signDec, publicKey);
			
			break;
		case(INS_SC_DH_SIGN):
			
			RSAPrivateKey privateKey = (RSAPrivateKey) createPrivateKey(cardPublicKeyMod, cardPrivateKeyExp);
			signDec = signData(AB, privateKey);
			AESIns = new AES();
			AESIns.keySetUp(K);
			AESIns.AesEncryption(signEnc, signDec, (short)signDec.length);
			sendToClient(apdu, signEnc);
		break;
		
		case(INS_SC_SIGN_STATUS):

			if (isVerified == true){
				sendToClient(apdu, SUCCESS);
			}else{
				sendToClient(apdu, FAIL);
			}
			
			break;
		case(INS_ECHO):
			byte[] out = {cpt};
			sendToClient(apdu, out);

			break;
			
		case(INS_GET_UID):
			sendToClient(apdu, cardUID);
			break;
		case(INS_SC_N):
			sendToClient(apdu, n);
			break;
		case(INS_CS_A):
			A = receiveFromClient(apdu);
			break;
		case(INS_CS_DH_K):
			K = receiveFromClient(apdu);
			break;
		case(INS_SC_DH_K):
			sendToClient(apdu, K);
			break;
		case(INS_GET_SERVER_PUB_MOD):
			sendToClient(apdu, serverPublicKeyMod);
		break;
		case(INS_GET_SERVER_PUB_EXP):
			sendToClient(apdu, serverPublicKeyExp);
		break;
		case(INS_GET_CARD_PUB_MOD):
			sendToClient(apdu, cardPublicKeyMod);
		break;
		case(INS_GET_CARD_PUB_EXP):
			sendToClient(apdu, cardPublicKeyExp);
		break;
		case(INS_CS_ENC_AES):
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
			byte[] out2 = new byte[16];
			cpt = (byte)(cpt + 1);
			AES instance = new AES();
			cpt = (byte)(cpt + 1);
			instance.keySetUp(KeyValue);
			cpt = (byte)(cpt + 1);
			instance.AesEncryption(out2, data, (short)data.length);
			cpt = (byte)(cpt + 1);
			sendToClient(apdu, out2);
		break;
		case(INS_CS_DEC_AES):
			byte[] data2 = {
		        (byte) 0xd5, (byte) 0x67, (byte) 0x71, (byte) 0xfa,
		        (byte) 0x86, (byte) 0x10, (byte) 0xda, (byte) 0x44,
		        (byte) 0xc7, (byte) 0xfa, (byte) 0x0f, (byte) 0xbe,
		        (byte) 0x76, (byte) 0x8a, (byte) 0xaa, (byte) 0xae
		    };
		byte[] KeyValue2 = {
				(byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16,
			    (byte) 0x28, (byte) 0xAE, (byte) 0xD2, (byte) 0xA6,
			    (byte) 0xAB, (byte) 0xF7, (byte) 0x97, (byte) 0x67,
			    (byte) 0x76, (byte) 0x5D, (byte) 0x2E, (byte) 0x47
		};
			byte[] out3 = new byte[16];
			cpt = (byte)(cpt + 1);
			AES instance2 = new AES();
			cpt = (byte)(cpt + 1);
			instance2.keySetUp(KeyValue2);
			cpt = (byte)(cpt + 1);
			instance2.AesDecrypt(data2, out3, (short)data2.length);
			cpt = (byte)(cpt + 1);
			sendToClient(apdu, out3);
		break;
		case(INS_TEST_K):
			sendToClient(apdu, K);
		break;
		case(INS_TEST_SIGN):
			sendToClient(apdu, signDec);
		break;
		
		default :
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	static private byte[] testRSA(byte[] msg, byte[] rsaPublicKeyExponent, byte[] rsaPrivateKeyModulus){
		//cpt = (byte)(cpt  + 1);
		Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
		//cpt = (byte)(cpt  + 1);
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, false);
		publicKey.setExponent(rsaPublicKeyExponent, (short) 0, (short) rsaPublicKeyExponent.length);
        publicKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
        cipher.init(publicKey, Cipher.MODE_ENCRYPT);
        byte[] encryptedMsg = new byte[64];
        cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
        
        return encryptedMsg;
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
	
	// verify signed data
	public boolean verifySignature(byte[] data, byte[] signature, RSAPublicKey publicKey) {
	    try {
	        // Initialize Signature object for SHA-256 with RSA
	        //Signature signatureIns = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
	        
	        // Initialize Signature object with public key
	        signatureIns.init(publicKey, Signature.MODE_VERIFY);
	        
	        // Verify the signature
	        return signatureIns.verify(data, (short) 0, (short) data.length, signature, (short) 0, (short) signature.length);
	        
	    } catch (CryptoException e) {
	        // Handle CryptoException
	        return false;
	    }
	}
	
	// verify signed data
		private byte[] signData(byte[] data, RSAPrivateKey privateKey) {
	        
			byte[] signature = new byte[64];
	        signatureIns.init(privateKey, Signature.MODE_SIGN);
	        signatureIns.sign(data, (short)0, (short)data.length, signature, (short)0);
	        return signature;    
	   }
	
	// create public key from byte arrays
	public static RSAPublicKey createPublicKey(byte[] modulus, byte[] exponent) {
		
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, false);
		publicKey.setModulus(modulus, (short)0, (short)64);
		publicKey.setExponent(exponent, (short)0, (short)exponent.length);

		return (RSAPublicKey) publicKey;
	}
	// create private key from byte arrays
		public static RSAPrivateKey createPrivateKey(byte[] modulus, byte[] exponent) {
			
			RSAPrivateKey privateKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_512, false);
			privateKey.setModulus(modulus, (short)0, (short)64);
			privateKey.setExponent(exponent, (short)0, (short)exponent.length);

			return (RSAPrivateKey) privateKey;
		}
	
  
	
	// generate random number
	byte[] generateRandomNumber() {

		// Generate random bytes for the private exponent
        byte[] privateExponent = new byte[64];
        try {
                        
            randomData.generateData(privateExponent, (short) 0, (short) 64);

        } catch (CryptoException e) {
            short reason = e.getReason();
            ISOException.throwIt(reason);
        }
        return privateExponent;
        
    }
	
	// encryption using rsa
	private byte[] modPow(byte[] generator,byte[] exponent, byte[] modulus) {
        
		byte[] output = new byte[64];
		
		pub.setModulus(modulus, (short) 0, (short)64);
        pub.setExponent(exponent, (short) 0, (short)exponent.length);
        cpt = (byte)(cpt  + 1);
        short size = 0;  
        try{  
        cpt = (byte)(cpt  + 1);
        Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);  
        cpt = (byte)(cpt  + 1);
        cipher.init(pub, Cipher.MODE_ENCRYPT);  
        size =  cipher.doFinal(generator, (short) 0, (short) generator.length, output,  (short) 0);  
        }  
        catch(CryptoException e)  
        {  
        switch(e.getReason())  
        {  
        case CryptoException.ILLEGAL_USE:  
        ISOException.throwIt(ISO7816.SW_DATA_INVALID);  
        break;  
        case CryptoException.ILLEGAL_VALUE:  
        ISOException.throwIt(ISO7816.SW_FILE_INVALID);  
        break;  
        case CryptoException.INVALID_INIT:  
        ISOException.throwIt(ISO7816.SW_FILE_NOT_FOUND);  
        break;  
        case CryptoException.NO_SUCH_ALGORITHM:  
        ISOException.throwIt(ISO7816.SW_FILE_INVALID);  
        break;  
        case CryptoException.UNINITIALIZED_KEY:  
        ISOException.throwIt(ISO7816.SW_FILE_FULL);  
        break;  
        default:  
        ISOException.throwIt(ISO7816.SW_FUNC_NOT_SUPPORTED);  
        break;  
        }  
        }  
        
        return output;
      }   
	
	// concatenate two byte arrays
	private byte[] concat(byte[] A, byte[] B){
		
		byte[] AB = new byte[A.length + B.length];
		Util.arrayCopyNonAtomic(A, (short)0, AB, (short)0, (short)A.length);
		Util.arrayCopyNonAtomic(B, (short)0, AB, (short)A.length, (short)B.length);
		return AB;
		
	}
        
        
  }

//
//
//byte[] exp = {(byte)0xac, (byte)0xb3, (byte)0x2d, (byte)0x53, (byte)0x6d, (byte)0xea, (byte)0x37, (byte)0x61, (byte)0x60, (byte)0xb0, (byte)0xb6, (byte)0xca, (byte)0xae, (byte)0xb5, (byte)0x49, (byte)0x26, (byte)0xe2, (byte)0x27, (byte)0xf8, (byte)0x51, (byte)0x95, (byte)0x21, (byte)0xcb, (byte)0x5d, (byte)0x54, (byte)0xbd, (byte)0x9a, (byte)0x86, (byte)0x06, (byte)0xe2, (byte)0x74, (byte)0x1a, (byte)0xf7, (byte)0xf5, (byte)0x43, (byte)0x87, (byte)0xa8, (byte)0xda, (byte)0x56, (byte)0x1a, (byte)0x7e, (byte)0x65, (byte)0x3b, (byte)0xb1, (byte)0x97, (byte)0xe6, (byte)0x85, (byte)0x1a, (byte)0x71, (byte)0xe8, (byte)0xd5, (byte)0x98, (byte)0x19, (byte)0x49, (byte)0xa6, (byte)0x79, (byte)0x7d, (byte)0x72, (byte)0xe5, (byte)0xcd, (byte)0xe1, (byte)0x83, (byte)0x56, (byte)0x39};
//byte[] enc = new byte[64];
////byte[] exp = {
////byte[] mod = {(byte)0xc3, (byte)0x44, (byte)0x1d, (byte)0xe5, (byte)0x40, (byte)0x06, (byte)0xae, (byte)0xe6, (byte)0x8f, (byte)0xad, (byte)0xed, (byte)0x54, (byte)0xa3, (byte)0x9b, (byte)0x98, (byte)0x38, (byte)0x88, (byte)0x55, (byte)0xa3, (byte)0x0b, (byte)0x0a, (byte)0x61, (byte)0x98, (byte)0xa8, (byte)0x40, (byte)0x21, (byte)0x0b, (byte)0x59, (byte)0x6e, (byte)0x7b, (byte)0xde, (byte)0xea, (byte)0x06, (byte)0xef, (byte)0xe0, (byte)0xdc, (byte)0x83, (byte)0x2a, (byte)0x24, (byte)0x29, (byte)0x88, (byte)0x3d, (byte)0xa6, (byte)0xd5, (byte)0x13, (byte)0x9e, (byte)0xd6, (byte)0xf5, (byte)0x4d, (byte)0x97, (byte)0x98, (byte)0x18, (byte)0xc8, (byte)0xc0, (byte)0xa0, (byte)0x62, (byte)0x93, (byte)0x97, (byte)0x9b, (byte)0x0a, (byte)0x71, (byte)0x2c, (byte)0x9a, (byte)0xf5};
////byte[] exp = {
////		(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
////        (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x80
////};
//byte[] mod = {
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x80
//        };
////byte[] generator = {(byte)0xc3, (byte)0x44, (byte)0x1d, (byte)0xe5, (byte)0x40, (byte)0x06, (byte)0xae, (byte)0xe6, (byte)0x8f, (byte)0xad, (byte)0xed, (byte)0x54, (byte)0xa3, (byte)0x9b, (byte)0x98, (byte)0x38, (byte)0x88, (byte)0x55, (byte)0xa3, (byte)0x0b, (byte)0x0a, (byte)0x61, (byte)0x98, (byte)0xa8, (byte)0x40, (byte)0x21, (byte)0x0b, (byte)0x59, (byte)0x6e, (byte)0x7b, (byte)0xde, (byte)0xea, (byte)0x06, (byte)0xef, (byte)0xe0, (byte)0xdc, (byte)0x83, (byte)0x2a, (byte)0x24, (byte)0x29, (byte)0x88, (byte)0x3d, (byte)0xa6, (byte)0xd5, (byte)0x13, (byte)0x9e, (byte)0xd6, (byte)0xf5, (byte)0x4d, (byte)0x97, (byte)0x98, (byte)0x18, (byte)0xc8, (byte)0xc0, (byte)0xa0, (byte)0x62, (byte)0x93, (byte)0x97, (byte)0x9b, (byte)0x0a, (byte)0x71, (byte)0x2c, (byte)0x9a, (byte)0xf5};
//byte[] gen = {
//		(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//        (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x80
//};
//G[(short) (64 - 1)] = (byte) 0x02;
//byte[] G2 = {(byte)0x02};
//
////final byte[] rsaPrivateKeyModulus = { (byte) 0xbe, (byte) 0xdf, (byte) 0xd3, (byte) 0x7a,
////    (byte) 0x08, (byte) 0xe2, (byte) 0x9a, (byte) 0x58, (byte) 0x27, (byte) 0x54, (byte) 0x2a, (byte) 0x49,
////    (byte) 0x18, (byte) 0xce, (byte) 0xe4, (byte) 0x1a, (byte) 0x60, (byte) 0xdc, (byte) 0x62, (byte) 0x75,
////    (byte) 0xbd, (byte) 0xb0, (byte) 0x8d, (byte) 0x15, (byte) 0xa3, (byte) 0x65, (byte) 0xe6, (byte) 0x7b,
////    (byte) 0xa9, (byte) 0xdc, (byte) 0x09, (byte) 0x11, (byte) 0x5f, (byte) 0x9f, (byte) 0xbf, (byte) 0x29,
////    (byte) 0xe6, (byte) 0xc2, (byte) 0x82, (byte) 0xc8, (byte) 0x35, (byte) 0x6b, (byte) 0x0f, (byte) 0x10,
////    (byte) 0x9b, (byte) 0x19, (byte) 0x62, (byte) 0xfd, (byte) 0xbd, (byte) 0x96, (byte) 0x49, (byte) 0x21,
////    (byte) 0xe4, (byte) 0x22, (byte) 0x08, (byte) 0x08, (byte) 0x80, (byte) 0x6c, (byte) 0xd1, (byte) 0xde,
////    (byte) 0xa6, (byte) 0xd3, (byte) 0xc3, (byte) 0x8f };
////
////final byte[] rsaPublicKeyExponent = { (byte) 0x01, (byte) 0x00, (byte) 0x01 };
////
//////byte[] msg = new byte[63];
////
//////RandomData rnd = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);
//////rnd.generateData(msg, (short) 0, (short) msg.length);
////final byte[] msg = { (byte) 0xbe, (byte) 0xdf, (byte) 0xd3, (byte) 0x7a,
////        (byte) 0x08, (byte) 0xe2, (byte) 0x9a, (byte) 0x58, (byte) 0x27, (byte) 0x54, (byte) 0x2a, (byte) 0x49,
////        (byte) 0x18, (byte) 0xce, (byte) 0xe4, (byte) 0x1a, (byte) 0x60, (byte) 0xdc, (byte) 0x62, (byte) 0x75,
////        (byte) 0xbd, (byte) 0xb0, (byte) 0x8d, (byte) 0x15, (byte) 0xa3, (byte) 0x65, (byte) 0xe6, (byte) 0x7b,
////        (byte) 0xa9, (byte) 0xdc, (byte) 0x09, (byte) 0x11, (byte) 0x5f, (byte) 0x9f, (byte) 0xbf, (byte) 0x29,
////        (byte) 0xe6, (byte) 0xc2, (byte) 0x82, (byte) 0xc8, (byte) 0x35, (byte) 0x6b, (byte) 0x0f, (byte) 0x10,
////        (byte) 0x9b, (byte) 0x19, (byte) 0x62, (byte) 0xfd, (byte) 0xbd, (byte) 0x96, (byte) 0x49, (byte) 0x21,
////        (byte) 0xe4, (byte) 0x22, (byte) 0x08, (byte) 0x08, (byte) 0x80, (byte) 0x6c, (byte) 0xd1, (byte) 0xde,
////        (byte) 0xa6, (byte) 0xd3, (byte) 0xc3, (byte) 0x8f };
//
////byte[] exp = {(byte)0x03};
//enc = modPow(G2, exp, mod);
//
//
//sendToClient(apdu, enc);
