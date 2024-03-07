package Hach;

import javacard.security.MessageDigest;
import javacard.framework.*;

public class HachApplet extends Applet {

    // Define instruction codes
    final static byte INS_HASH = (byte) 0x01;

    // Instance variables
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new HachApplet().register();
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }
        
        byte[] buffer = apdu.getBuffer();
        switch (buffer[ISO7816.OFFSET_INS]) {
            case INS_HASH:
                hashByteArray(apdu);
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }

    private void hashByteArray(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        short data = apdu.setIncomingAndReceive();
        MessageDigest md;
        md = MessageDigest.getInstance(MessageDigest.ALG_SHA_256,false);
        md.reset();
        md.update(buffer, ISO7816.OFFSET_CDATA, data);
        short Digest = md.doFinal(buffer, (short) 0, (short) 0, buffer, (short) 0);
        apdu.setOutgoingAndSend((short) 0,Digest );
    }
}
