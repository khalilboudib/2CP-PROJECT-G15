package jsr268gp.sampleapplet;

import javacard.framework.*;
import javacard.security.*;
import javacardx.crypto.*;
import javacard.security.Signature;

public class SampleTestApplet extends Applet {

	public static final short MODULUS_SIZE = (short)128;
	public static final short HASH_SIZE = (short)32;
	public static final short AES_KEY_SIZE = (short)16;
	public static final byte CLA = (byte) 0x80;
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
	public static final byte INS_CS_MODPOW = (byte) 0x10;
	public static final byte INS_CS_UID =(byte) 0x11;
	public static final byte DH_KEY_LENGTH = (byte)0x80;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_TEST = (byte)0x68;
	public static final byte INS_TEST2 = (byte)0x67;
	public static final byte INS_TEST3 = (byte)0x66;
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
	public static final byte INS_SC_K = (byte)0x86;
	public static final byte INS_CS_HASH=(byte)0x99;
	private  byte[] P=new byte[MODULUS_SIZE];
	private  byte[] n=new byte[MODULUS_SIZE];
	private byte[] K =new byte[MODULUS_SIZE];
	private byte[] KAes = new byte[16];
	private byte[] A =new byte[MODULUS_SIZE];
	private byte[] B =new byte[MODULUS_SIZE];
	private byte[] AB;
	private byte[] cardPublicKeyMod = new byte[MODULUS_SIZE];
	private byte[] cardPublicKeyExp = new byte[MODULUS_SIZE];
	private byte[] serverPublicKeyMod = new byte[MODULUS_SIZE];
	private byte[] serverPublicKeyExp = new byte[MODULUS_SIZE];
	private byte[] cardPrivateKeyP = new byte[MODULUS_SIZE];
	private byte[] cardPrivateKeyQ = new byte[MODULUS_SIZE];
	private byte[] cardPrivateKeyExp = new byte[MODULUS_SIZE];
	private byte[] cardUID = new byte[64];
	private byte[] supposedABHash = new byte[HASH_SIZE];
	public static byte[] cpt={(byte) 0x00};
	public static final byte[] SUCCESS = {(byte)0x90};
	public static final byte[] FAIL = {(byte)0x67};

	
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
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02
		    };
	private byte[] signEnc=new byte[MODULUS_SIZE];
	private boolean isVerified;
	private byte[] signDec;
	AES AESIns;
	
	private RandomData randomData;
	
    

	Signature verifsign;
	Signature signatureIns;
	
	public boolean select () {
		
		return (true);
	}
	
	public void deselect () {
	}
	
	public SampleTestApplet () {
		randomData = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);
		

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
			 P = receiveFromClient(apdu);
			
			// calculate A
			// generating random private n
			 n = generateRandomNumber();
			//sendToClient(apdu, n);
			// RSA 
			
			A = testRSA(G, n, P);
			sendToClient(apdu, A);
			// --------------------
			// calculate modular exponential
			//byte[] A = modPow(G, P, n);
			//sendToClient(apdu, n);
			break;
			
		case INS_CS_DH_B:
			// getting B from the server
			B = receiveFromClient(apdu);
			 K=masqueFunction(testRSA(B,n,P));
			// sending card number
			//sendToClient(apdu, K);

			break;
		case INS_CS_MODPOW :
            byte[] modulus={
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
		byte[] exponent = {
        	    (byte) 0x80, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) 0x02, (byte) 0x03,
        	    (byte) 0x04, (byte) 0x05, (byte) 0x01, (byte) 0x08, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x01,
        	    (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x04, (byte) 0x06, (byte) 0x08,
        	    (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02,
        	    (byte) 0x02, (byte) 0x04, (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00,
        	    (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05, (byte) 0x01, (byte) 0x00, (byte) 0x07, (byte) 0x04,
        	    (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05, (byte) 0x01, (byte) 0x00,
        	    (byte) 0x07, (byte) 0x04, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x05};
		byte[] generator={
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
      		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02};
		byte[] encryptedddd = testRSA(generator,exponent,modulus);
		//cpt[0] = (byte)(cpt[0] + 1);
		sendToClient(apdu, encryptedddd);
			break;
		case INS_CS_UID :
			sendToClient(apdu,cardUID);
			break;
		case(0x20):
			sendToClient(apdu, cpt);
		    break;
		case(INS_CS_DH_SIGN):

			signEnc = receiveFromClient(apdu);
			signDec = new byte[MODULUS_SIZE];
			// decrypting signature from AES
			AESIns = new AES();

			
			AESIns.keySetUp(K);
			AESIns.AesDecrypt(signEnc, signDec, (short)signEnc.length);
			// concatenate A and B
			AB = concat(A, B);
			//verifying signature
			isVerified = false;
			//cpt[0] = (byte)(cpt[0] + 1);
			//signatureIns = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
			//cpt[0] = (byte)(cpt[0] + 1);
			byte[] tmp = DecryptRSA(signDec, serverPublicKeyExp, serverPublicKeyMod);
			Util.arrayCopyNonAtomic(tmp, (short)96, supposedABHash, (short)0, (short)supposedABHash.length);
			isVerified = areEqual(hash(AB), supposedABHash);
			//isVerified = verifySignature(B, signEnc, publicKey);
			
			break;
		case(INS_TEST):

			break;
		case(INS_TEST2):
			byte[] P={
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
//		byte[] AES_KEY = new byte[16];
//		AESIns = new AES();
//		Util.arrayCopyNonAtomic(K, (short)0, AES_KEY , (short)0, (short)AES_KEY .length);
//		AESIns.keySetUp(AES_KEY);
//		AESIns.AesEncryption(signEnc, P, (short)P.length);
//		Cipher cipher ; 
//		AESKey aesKey;
//		byte [] IV = new byte[16];
//		aesKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_128, false);
//    	aesKey.setKey(AES_KEY, (short) 0);	
//        cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
//        cipher.init(aesKey, Cipher.MODE_ENCRYPT, IV, (short) 0 ,(short) 16 );
//        cipher.doFinal(P, (short) 0 , (short) 16,signEnc , (short) 0 );	
		sendToClient(apdu, signEnc);
			break;
		case(INS_TEST3):
			sendToClient(apdu, signDec);
			break;
        case(INS_SC_DH_SIGN):
			
			RSAPrivateKey privateKey = (RSAPrivateKey) createPrivateKey(cardPublicKeyMod, cardPrivateKeyExp);
			byte[] tmpp=new byte[MODULUS_SIZE];
			byte[] hashb=hash(AB);
			Util.arrayCopyNonAtomic(hashb, (short)0, tmpp, (short)96, (short)hashb.length);
			signDec = EncryptRSA(tmpp, cardPrivateKeyExp, cardPublicKeyMod);
    		
    		AESIns = new AES();
    		AESIns.keySetUp(K);
    		byte[] out = new byte[MODULUS_SIZE];
    		
//    		Cipher cipher ; 
//    		AESKey aesKey;
//    		byte [] IV = new byte[16];
//    		aesKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_128, false);
//        	aesKey.setKey(AES_KEY2, (short) 0);	
//            cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
//            cipher.init(aesKey, Cipher.MODE_ENCRYPT, IV, (short) 0 ,(short) 16 );
//            cipher.doFinal(signDec, (short) 0 , (short) 64,out , (short) 0 );	
    		
    		AESIns.AesEncryption(out, signDec, (short)signDec.length);

			sendToClient(apdu, out);
		break;
        case(INS_SC_SIGN_STATUS):

			if (isVerified == true){
				sendToClient(apdu, SUCCESS);
			}else{
				sendToClient(apdu, FAIL);
			}
			
			break;
        case(INS_CS_HASH):
        	//K=masqueFunction(K);
        	
        	Util.arrayCopyNonAtomic(K, (short)0, KAes, (short)0, (short)16);
        	sendToClient(apdu, KAes);
        	break;
        case(INS_SC_K):
        	sendToClient(apdu, K);
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
public static RSAPublicKey createPublicKey(byte[] modulus, byte[] exponent) {
		
		RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
		publicKey.setModulus(modulus, (short)0, MODULUS_SIZE);
		publicKey.setExponent(exponent, (short)0, (short)exponent.length);

		return (RSAPublicKey) publicKey;
	}
	// create private key from byte arrays
		public static RSAPrivateKey createPrivateKey(byte[] modulus, byte[] exponent) {
			
			RSAPrivateKey privateKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_1024, false);
			privateKey.setModulus(modulus, (short)0, MODULUS_SIZE);
			privateKey.setExponent(exponent, (short)0, (short)exponent.length);

			return (RSAPrivateKey) privateKey;
		}
	
	// verify signed data
	private boolean verifySignatureWithPublicKey(byte[] data, byte[] signature,PublicKey publicKey) {
        
        verifsign.init(publicKey, Signature.MODE_VERIFY);
        boolean isSignatureValid = verifsign.verify(signature, (short)0,(short)signature.length, data,(short)0,(short)data.length);
        return isSignatureValid;    
   }
	
	// encryption rsa - pow modular
	byte[] modPow(byte[] base,byte[] rsaPublicKeyModulus, byte[] rsaPublicKeyExponent) {
		Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
        RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, false);
        publicKey.setExponent(rsaPublicKeyExponent, (short) 0,(short) rsaPublicKeyExponent.length);
        publicKey.setModulus(rsaPublicKeyModulus, (short) 0,(short) rsaPublicKeyModulus.length);
        cipher.init(publicKey, Cipher.MODE_ENCRYPT);
        byte[] encryptedMsg = new byte[64];
        cipher.doFinal(base, (short) 0, (short) base.length, encryptedMsg, (short) 0);
        
        return encryptedMsg;
      }     
	
	// generate random number
	byte[] generateRandomNumber() {

		// Generate random bytes for the private exponent
        byte[] privateExponent = new byte[MODULUS_SIZE];
        try {
                        
            randomData.generateData(privateExponent, (short) 0, (short) MODULUS_SIZE);

        } catch (CryptoException e) {
            short reason = e.getReason();
            ISOException.throwIt(reason);
        }
        return privateExponent;
        
    }
	static private byte[] testRSA(byte[] msg, byte[] rsaPublicKeyExponent, byte[] rsaPrivateKeyModulus){
        //cpt[0] = (byte)(cpt[0] + 1);
        Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
        //cpt[0] = (byte)(cpt[0] + 1);
        RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
        //cpt[0] = (byte)(cpt[0] + 1);
        publicKey.setExponent(rsaPublicKeyExponent, (short) 0, (short) rsaPublicKeyExponent.length);
        //cpt[0] = (byte)(cpt[0] + 1);
        publicKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
        //cpt[0] = (byte)(cpt[0] + 1);
        cipher.init(publicKey, Cipher.MODE_ENCRYPT);
        //cpt[0] = (byte)(cpt[0] + 1);
        byte[] encryptedMsg = new byte[MODULUS_SIZE];
        //cpt[0] = (byte)(cpt[0] + 1);
        cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
        //cpt[0] = (byte)(cpt[0] + 1);
        
        return encryptedMsg;
    }
private byte[] concat(byte[] A, byte[] B){
		
		byte[] AB = new byte[(short) (A.length + B.length)];
		Util.arrayCopyNonAtomic(A, (short)0, AB, (short)0, (short)A.length);
		Util.arrayCopyNonAtomic(B, (short)0, AB, (short)A.length, (short)B.length);
		return AB;
		
	}
private byte[] signData(byte[] data, RSAPrivateKey privateKey) {
    
	byte[] output = new byte[20];
	//cpt[0] = (byte)(cpt[0] + 1);
	//signature =Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
	//cpt[0] = (byte)(cpt[0] + 1);
    signatureIns.init(privateKey, Signature.MODE_SIGN);
    //cpt[0] = (byte)(cpt[0] + 1);
    short sigLength = signatureIns.sign(data, (short) 0,(short) data.length, output, (short) 0);
    //cpt[0] = (byte)(cpt[0] + 1);
    //signatureIns.init(privateKey, Signature.MODE_SIGN);
    //signatureIns.sign(data, (short)0, (short)data.length, signature, (short)0);
    return output;    
}
public boolean verifySignature(byte[] data, byte[] signature, RSAPublicKey publicKey) {
    try {
        // Initialize Signature object for SHA-256 with RSA
    	
    	//cpt[0] = (byte)(cpt[0] + 1);
    	Signature signatureIns = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
        
        // Initialize Signature object with public key
    	//cpt[0] = (byte)(cpt[0] + 1);
        signatureIns.init(publicKey, Signature.MODE_VERIFY);
        //cpt[0] = (byte)(cpt[0] + 1);
        boolean truth =  signatureIns.verify(data, (short) 0, (short) data.length, signature, (short) 0, (short) signature.length);
        //cpt[0] = (byte)(cpt[0] + 1);
        // Verify the signature
        return truth;
        
    } catch (CryptoException e) {
        
        return false;
    }
}
static public byte[] masqueFunction(byte[] data){
	
	byte[] out = new byte[AES_KEY_SIZE];
	Util.arrayCopyNonAtomic(hash(data), (short)0, out, (short)0	, (short)out.length);
	return out;
}


static public byte[] hash(byte[] secret) {
    try {
        // Compute the SHA-256 hash of the shared secret
    	//cpt[0] = (byte)(cpt[0] + 1);
    	MessageDigest sha256 = MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
    	//cpt[0] = (byte)(cpt[0] + 1);
        sha256.reset();
        //sha256.update(secret, (short) 0, (short) secret.length);
        //cpt[0] = (byte)(cpt[0] + 1);
        //byte[] sha256Hash = new byte[16];
        //cpt[0] = (byte)(cpt[0] + 1);
        byte[] aesKeyBytes = new byte[32];
        //cpt[0] = (byte)(cpt[0] + 1);
        sha256.doFinal(secret, (short) 0,(short)secret.length,aesKeyBytes,(short) 0);
        //cpt[0] = (byte)(cpt[0] + 1);

        // Truncate the hash to 128 bits (16 bytes)
       // Util.arrayCopyNonAtomic(sha256Hash, (short) 0, aesKeyBytes, (short) 0, (short) aesKeyBytes.length);
        return aesKeyBytes;

    } catch (CryptoException e) {
        return null;
    }
}
static private byte[] DecryptRSA(byte[] msg, byte[] rsaPublicKeyExponent, byte[] rsaPrivateKeyModulus){
	//cpt[0] = (byte)(cpt[0] + 1);
	Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
	//cpt[0] = (byte)(cpt[0] + 1);
	RSAPublicKey publicKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_1024, false);
	//cpt[0] = (byte)(cpt[0] + 1);
	publicKey.setExponent(rsaPublicKeyExponent, (short) 0, (short) rsaPublicKeyExponent.length);
	//cpt[0] = (byte)(cpt[0] + 1);
	publicKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
	//cpt[0] = (byte)(cpt[0] + 1);
	cipher.init(publicKey, Cipher.MODE_DECRYPT);
	//cpt[0] = (byte)(cpt[0] + 1);
	byte[] encryptedMsg = new byte[MODULUS_SIZE];
	//cpt[0] = (byte)(cpt[0] + 1);
	cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
	//cpt[0] = (byte)(cpt[0] + 1);
    return encryptedMsg;
}

static private byte[] EncryptRSA(byte[] msg, byte[] rsaPrivateKeyExponent, byte[] rsaPrivateKeyModulus){
	cpt[0] = (byte)(cpt[0] + 1);
	Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
	cpt[0] = (byte)(cpt[0] + 1);
	RSAPrivateKey privateKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_1024, false);
    cpt[0] = (byte)(cpt[0] + 1);
	privateKey.setExponent(rsaPrivateKeyExponent, (short) 0, (short) rsaPrivateKeyExponent.length);
	cpt[0] = (byte)(cpt[0] + 1);
	privateKey.setModulus(rsaPrivateKeyModulus, (short) 0, (short) rsaPrivateKeyModulus.length);
	cpt[0] = (byte)(cpt[0] + 1);
	cipher.init(privateKey, Cipher.MODE_ENCRYPT);
	cpt[0] = (byte)(cpt[0] + 1);
	byte[] encryptedMsg = new byte[MODULUS_SIZE];
	cpt[0] = (byte)(cpt[0] + 1);
	cipher.doFinal(msg, (short) 0, (short) msg.length, encryptedMsg, (short) 0);
	cpt[0] = (byte)(cpt[0] + 1);
    return encryptedMsg;
}
public static boolean areEqual(byte[] array1, byte[] array2) {
    // Check if arrays are null or have different lengths
    if (array1 == null || array2 == null || array1.length != array2.length) {
        return false;
    }

    // Compare arrays using Util.arrayCompare method
    return (Util.arrayCompare(array1, (short) 0,array2, (short) 0, (short) array2.length)==0);
}
        
  }