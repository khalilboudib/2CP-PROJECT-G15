package clientPack;

import java.security.*;
import java.math.BigInteger;
import java.security.interfaces.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAOps {

	// the pair of keys
	private KeyPair keyPair;
	private BigInteger bigInteger;
	private RSAPrivateCrtKey privateKey;
	private RSAPublicKey publicKey;
	private KeyFactory keyFactoryIns;
	
	final int defaultRsaSize = 1024;
	
	
	
	public RSAOps(int rsaSize) throws NoSuchAlgorithmException, InvalidKeySpecException{
		// cipher generator
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		// keypair
		if(rsaSize != 512 && rsaSize != 1024){
			generator.initialize(defaultRsaSize);
			
		}else{
			generator.initialize(rsaSize);
		}
		
		this.keyPair = generator.genKeyPair();
		this.keyFactoryIns = KeyFactory.getInstance("RSA");
		// getting the private key
		privateKey = (RSAPrivateCrtKey) this.keyFactoryIns.generatePrivate(new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded()));	
		// getting the public key
		publicKey = (RSAPublicKey) this.keyFactoryIns.generatePublic(new X509EncodedKeySpec(keyPair.getPublic().getEncoded()));
	}
	
	public byte[] getPrivateKeyMod (){
		// converting private key modulus to byte array
		byte[] modBytes = this.privateKey.getModulus().toByteArray();
		// removing the sign byte which is zero
	    if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
	        byte[] trimmedBytes = new byte[modBytes.length - 1];
	        System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
	        return trimmedBytes;
	    } else {
	        return modBytes;
	    }
		
	}
	
	
	public byte[] getPrivateKeyP(){
		// converting private key modulus to byte array
		byte[] modBytes = this.privateKey.getPrimeP().toByteArray();
		// removing the sign byte which is zero
		if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
		    byte[] trimmedBytes = new byte[modBytes.length - 1];
		    System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
		    return trimmedBytes;
		} else {
		    return modBytes;
		}
	}
	
	public byte[] getPrivateKeyQ(){
		// converting private key modulus to byte array
		byte[] modBytes = this.privateKey.getPrimeQ().toByteArray();
		// removing the sign byte which is zero
		if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
		    byte[] trimmedBytes = new byte[modBytes.length - 1];
		    System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
		    return trimmedBytes;
		} else {
		    return modBytes;
		}
	}
	
	public byte[] getPrivateKeyExponent(){
		// converting private key modulus to byte array
		byte[] modBytes = this.privateKey.getPrivateExponent().toByteArray();
		// removing the sign byte which is zero
		if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
		    byte[] trimmedBytes = new byte[modBytes.length - 1];
		    System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
		    return trimmedBytes;
		} else {
		    return modBytes;
		}
	}
	
	public byte[] getPublicKeyMod (){
		// converting public key modulus to byte array
		byte[] modBytes = this.publicKey.getModulus().toByteArray();
		// removing the sign byte which is 0
	    if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
	        byte[] trimmedBytes = new byte[modBytes.length - 1];
	        System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
	        return trimmedBytes;
	    } else {
	        return modBytes;
	    }
				
	}
	
	public byte[] getPublicKeyExponent(){
		// converting private key modulus to byte array
		byte[] modBytes = this.publicKey.getPublicExponent().toByteArray();
		// removing the sign byte which is zero
		if (modBytes[0] == 0) { // If the most significant byte is 0, strip it off
		    byte[] trimmedBytes = new byte[modBytes.length - 1];
		    System.arraycopy(modBytes, 1, trimmedBytes, 0, trimmedBytes.length);
		    return trimmedBytes;
		} else {
		    return modBytes;
		}
	}
	


}
