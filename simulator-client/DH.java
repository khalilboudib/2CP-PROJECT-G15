package clientPack;

import javax.crypto.interfaces.DHPublicKey;

import java.security.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import java.security.*;

public class DH {

	public static BigInteger generateRandomPrime(int bitLength) {
        SecureRandom secureRandom = new SecureRandom();
        return BigInteger.probablePrime(bitLength, secureRandom);
    }
	
	public static BigInteger generateRandom(int bitLength) {
		SecureRandom secureRandom = new SecureRandom();
        BigInteger randomNum = new BigInteger(bitLength, secureRandom);
        return randomNum;
	}
	
	// find modular exponentiation
	public static byte[] modularExponentiation(byte[] base, byte[] exponent, byte[] modulus) {
        // Convert byte arrays to BigIntegers
        BigInteger baseInt = new BigInteger(1, base);
        BigInteger exponentInt = new BigInteger(1, exponent);
        BigInteger modulusInt = new BigInteger(1, modulus);
        
        // Perform modular exponentiation
        BigInteger resultInt = baseInt.modPow(exponentInt, modulusInt);
        
        // Convert result back to byte array
        byte[] result = resultInt.toByteArray();
        
        return result;
    }
	
	// convert long to byte array
	public static byte[] longToBytes(long numberLong){
		 // Example long number
        
        byte[] byteArray = new byte[8]; // Long is 8 bytes in Java
        
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (numberLong >> (i * 8));
        }
		
        return byteArray;
	}
	
	// convert byte array to long
	public static long byteArrayToLong(byte[] byteArray) {
	    if (byteArray.length < 8) {
	        // Create a new byte array with enough space for padding
	        byte[] paddedArray = new byte[8];
	        // Copy the original data to the padded array starting from index 8 - length of original array
	        System.arraycopy(byteArray, 0, paddedArray, 8 - byteArray.length, byteArray.length);
	        // Create a ByteBuffer and wrap the padded byte array
	        ByteBuffer buffer = ByteBuffer.wrap(paddedArray);
	        buffer.order(ByteOrder.LITTLE_ENDIAN);
	        // Get the long value from the ByteBuffer
	        return buffer.getLong();
	    } else {
	        // If the byte array already has 8 or more bytes, proceed as before
	        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
	        buffer.order(ByteOrder.LITTLE_ENDIAN);
	        return buffer.getLong();
	    }
	}
	
	// RSA sign using private key
	public static byte[] signData (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
		byte[] digitalSignature = null;
		
		try{
		// Création d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signatureIns = Signature.getInstance("SHA1withRSA");
	    
	    // Initialisation de l'objet Signature avec la clé privée
	    signatureIns.initSign(privateKey);
	    
	    // Ajout des données à signer
	    signatureIns.update(data);
	    
	    // Signature des données
	     digitalSignature = signatureIns.sign();
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return digitalSignature;
	}
	
	public static boolean verifySignature(byte[] data, byte[] signature,PublicKey publicKey) {
		boolean isVerified = false;
		try{
		// Création d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signatureIns = Signature.getInstance("SHA1withRSA");
	    
	    // Initialisation de l'objet Signature avec la clé privée
	    signatureIns.initVerify(publicKey);
	    
	    // Ajout des données à signer
	    signatureIns.update(data);
	    
	    // Signature des données
	    isVerified = signatureIns.verify(signature);
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return isVerified;
   }
	
	// masque function
	public static byte[] masqueFunction(byte[] secret){
		try {
	        // Use SHA-256 as the hash function
	        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

	        // Compute the SHA-256 hash of the shared secret
	        byte[] sha256Hash = sha256.digest(secret);

	        // Truncate the hash to 128 bits (16 bytes)
	        byte[] aesKeyBytes = new byte[16];
	        System.arraycopy(sha256Hash, 0, aesKeyBytes, 0, aesKeyBytes.length);
	        return aesKeyBytes;

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	// print byte array
	public static void printByteArray(byte[] byteArray) {
        System.out.print("[");
        for (int i = 0; i < byteArray.length; i++) {
            System.out.print(String.format("%02X", byteArray[i]));
            if (i < byteArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
	
	// concatenate two byte arrays
	public static byte[] concat(byte[] A, byte[] B){
		
		byte[] AB = new byte[A.length + B.length];
		System.arraycopy(A, 0, AB, 0, A.length);
		System.arraycopy(B, 0, AB, A.length, B.length);
		return AB;
		
	}
}