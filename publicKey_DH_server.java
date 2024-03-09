import javax.crypto.interfaces.DHPublicKey;
import java.security.*;
import java.math.BigInteger;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;

public class deffie_hellman {
    public static void main(String[] args) {

            // Generate random prime modulus (p)
            BigInteger primeModulus = generateRandomPrime(1024); // 1024 bits for example

            // Generate random generator (g)
            SecureRandom secureRandom = new SecureRandom();
           // BigInteger generator = BigInteger.probablePrime(primeModulus.bitLength() - 1, secureRandom);

            // Generate Diffie-Hellman key pair
           // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
           // DHParameterSpec dhParameterSpec = new DHParameterSpec(primeModulus, generator);
           // keyPairGenerator.initialize(dhParameterSpec, secureRandom);
           // KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Extract the public key
            //PublicKey publicKey = keyPair.getPublic();

            // Print the prime modulus and generator
            System.out.println("Prime Modulus (p): " + primeModulus.toString(16));
           // System.out.println("Generator (g): " + generator.toString(16));

            // Print the public key
            //System.out.println("Public Key: " + publicKey);

    }
    private static BigInteger generateRandomPrime(int bitLength) {
        SecureRandom secureRandom = new SecureRandom();
        return BigInteger.probablePrime(bitLength, secureRandom);
    }
}
