package clientPack;

import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.math.BigInteger;


public class test {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// init the generator on RSA
		RSAOps kayPair = new RSAOps(1024);
		
		BigInteger p = new BigInteger(1, kayPair.getPrivateKeyP());
		
		BigInteger q = new BigInteger(1, kayPair.getPrivateKeyQ());
		
		BigInteger N = new BigInteger(1, kayPair.getPublicKeyMod());
		
		
		
		System.out.println("p: " + p);
		System.out.println("q: " + q);
		System.out.println("N: " + N);
		System.out.println("p * q: " + p.multiply(q));
		
	}

}
