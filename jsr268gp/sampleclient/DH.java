package jsr268gp.sampleclient;

import javax.crypto.interfaces.DHPublicKey;

import java.security.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;

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
	        // Get the long value from the ByteBuffer
	        return buffer.getLong();
	    } else {
	        // If the byte array already has 8 or more bytes, proceed as before
	        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
	        return buffer.getLong();
	    }
	}
	
	// RSA sign using private key
	public static byte[] signData (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
		byte[] digitalSignature = null;
		try{
		// Création d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signature = Signature.getInstance("SHA256withRSA");
	    
	    // Initialisation de l'objet Signature avec la clé privée
	    signature.initSign(privateKey);
	    
	    // Ajout des données à signer
	    signature.update(data);
	    
	    // Signature des données
	     digitalSignature = signature.sign();
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return digitalSignature;
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
}
