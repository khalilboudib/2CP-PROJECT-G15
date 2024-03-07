package Sign;

import javacard.framework.*;
import javacard.security.*;

public class SignatureApplet extends Applet {

    // CLA byte for command
    final static byte CLA_DEMOAPPLET = (byte) 0xB0;

    // INS bytes for commands
    final static byte INS_SIGN = (byte) 0x01;

    // Object for signature
    Signature signature;

    protected SignatureApplet() {
        signature = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
    }

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new SignatureApplet().register();
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }

        byte[] buffer = apdu.getBuffer();
        switch (buffer[ISO7816.OFFSET_INS]) {
            case INS_SIGN:
            	KeyPair keyPair = new KeyPair(KeyPair.ALG_RSA, KeyBuilder.LENGTH_RSA_2048);
                keyPair.genKeyPair();
                PrivateKey privateKey = (PrivateKey) keyPair.getPrivate();
                signData(apdu,privateKey);
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }

    private void signData(APDU apdu,PrivateKey privateKey) {
        byte[] buffer = apdu.getBuffer();

        // Retrieve data to be signed
        short dataLength = apdu.setIncomingAndReceive();
        byte[] data = new byte[dataLength];
        Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_CDATA, data, (short) 0, dataLength);

        // Initialize signature object with private key
        try {
            signature.init(privateKey, Signature.MODE_SIGN);

            // Sign data
            short signatureLength = signature.sign(data, (short) 0, dataLength, buffer, (short) 0);
            
            // Send signature
            apdu.setOutgoing();
            apdu.setOutgoingLength(signatureLength);
            apdu.sendBytesLong(buffer, (short) 0, signatureLength);
        } catch (CryptoException e) {
            ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
        }
    }
}
