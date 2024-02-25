package codes_carte;

import javacard.framework.*;

public class CodId_CodPin extends Applet{

	// Constants
    final static byte PIN_TRY_LIMIT = 3; // Maximum number of PIN tries
    final static byte PIN_SIZE = 4; // Length of PIN
    final static byte[] DEFAULT_PIN = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04}; // Default PIN
    final static byte CODE_SIZE = (short)3; // Length of CODE
    
    // Instance variables
    OwnerPIN pin;
    OwnerCODE codid;
    
    // Constructor
    protected CodId_CodPin() {
        pin = new OwnerPIN(PIN_TRY_LIMIT, PIN_SIZE);
        pin.update(DEFAULT_PIN, (short) 0, PIN_SIZE);
        codid = new OwnerCODE(CODE_SIZE);
        register();
    }

    // Applet installation method
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new CodId_CodPin();
    }

    // Main processing method
    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }

        byte[] buffer = apdu.getBuffer();

        switch (buffer[ISO7816.OFFSET_INS]) {
            case (byte) 0x01: // Set PIN
                setPIN(apdu);
                break;
            case (byte) 0x02: // verification PIN
                verify(apdu);
                break;
            case (byte) 0x03: // verification PIN
            	setcode(apdu);
                break;
            case (byte) 0x04: // verification PIN
            	getcode(apdu);
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }

    // Method to set a new PIN
    private void setPIN(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        byte byteRead = (byte) (apdu.setIncomingAndReceive());

        if (byteRead != PIN_SIZE) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }
        
        pin.update(buffer, ISO7816.OFFSET_CDATA, byteRead);
    }
    
    // Method to verify the PIN
    private void verify(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        byte byteRead = (byte) (apdu.setIncomingAndReceive());

        // Check if PIN verification is required
        if (pin.getTriesRemaining() == 0) {
            ISOException.throwIt(ISO7816.SW_WRONG_DATA);
        }

        // Check PIN
        if (pin.check(buffer, ISO7816.OFFSET_CDATA, byteRead) == false) {
        	 ISOException.throwIt((short) (ISO7816.SW_DATA_INVALID + pin.getTriesRemaining()));
        }
    }
    
 // Method to set a new code
    private void setcode(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        byte byteRead = (byte) (apdu.setIncomingAndReceive());

        if (byteRead != CODE_SIZE) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }
        
        codid.update(buffer, ISO7816.OFFSET_CDATA, byteRead);
    }
    
  //Method to get the code
    private void getcode(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
    	codid.getcode(buffer);
    	apdu.setOutgoingAndSend((short)0, CODE_SIZE);
    }
    
}


