package serverSecurityTestPack;

import javacard.framework.*;

public class serverSecurityTest extends Applet {

	public static final byte CLA_APPLET = (byte)0xA0;
	public static final byte INS_CARD_PC = (byte)0x00;
	public static final byte INS_PC_CARD = (byte)0x01;
	
	
	private byte secret =(byte) 0x12;
	
	
	private serverSecurityTest() {
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException {
		new serverSecurityTest().register();
	}
	


	
	public void process(APDU apdu) throws ISOException {
		
		// avoid processing selection requests in this methode
		if(this.selectingApplet()) return;
		// getting apdu first bytes
		byte[] buffer = apdu.getBuffer();
		// handling CLA
		if(buffer[ISO7816.OFFSET_CLA] != CLA_APPLET) {
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		}
		
		// findout the operation we are going to process
		switch (buffer[ISO7816.OFFSET_INS]){
			case INS_CARD_PC:
			// sending the result in the first byte of the same apdu buffer
				buffer[0] = secret;
				apdu.setOutgoingAndSend((short) 0, (short) 1);
			
			case INS_PC_CARD:
				// addition of two numbers and storing the result
				apdu.setIncomingAndReceive();
				// update the result
				secret = (byte)buffer[ISO7816.OFFSET_CDATA];
				//secret = (short)(buffer[ISO7816.OFFSET_CDATA] + buffer[ISO7816.OFFSET_CDATA + 1]);
				break;

			default:
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
}
