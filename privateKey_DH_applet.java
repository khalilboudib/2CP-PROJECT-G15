package habacha;
import javacard.framework.*;
import javacard.security.*;
import javacardx.crypto.Cipher;

public class keys_GeneratorApplet extends Applet {
    private static final byte INS_GENERATE_KEY = (byte) 0x01;
    private Cipher cipher;
    private RSAPrivateKey privKey;
    private byte[] modulus;
    private RandomData randomData;

    public static void install(byte bArray[], short bOffset, byte bLength) {
        new keys_GeneratorApplet().register();
    }

    public keys_GeneratorApplet() {
        randomData = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);
        // Initialize the private key and modulus
        }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }

        byte[] buffer = apdu.getBuffer();
        switch (buffer[ISO7816.OFFSET_INS]) {
            case INS_GENERATE_KEY:
                generateRandomNumber(apdu);
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }

    void generateRandomNumber(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
       // byte[] generator = new byte[128];
       // generator[127] = (byte) 0x02;

        try {
            // Generate random bytes for the private exponent
            byte[] privateExponent = new byte[128];
           // modulus = new byte[128]; // Assuming 1024-bit modulus (128 bytes)
            //privKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_1024, false);
            
            randomData.generateData(privateExponent, (short) 0, (short) 128);
            // Generate random bytes for the modulus
           // randomData.generateData(modulus, (short) 0, (short) 128);
            // Set the private exponent and modulus
           // privKey.setExponent(privateExponent, (short) 0, (short) privateExponent.length);
         //   privKey.setModulus(modulus, (short) 0, (short) modulus.length);

           // cipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);
           // cipher.init(privKey, Cipher.MODE_DECRYPT);

            // Perform RSA decryption (modular exponentiation)
           // short cipherLength = cipher.doFinal(generator, (short) 0, (short) generator.length, buffer, (short) 0);
            // Send the result
            Util.arrayCopyNonAtomic(privateExponent, (short) 0,buffer,  (short) 0,(short) privateExponent.length);
            apdu.setOutgoingAndSend((short) 0, (short)128);
        } catch (CryptoException e) {
            short reason = e.getReason();
            ISOException.throwIt(reason);
        }
    }
}
