package jsr268gp.sampleapplet;

import javacard.framework.*;

public class SampleTestApplet extends Applet {

	public static final byte CLA = (byte) 0x80;
	public boolean select () {
		return (true);
	}
	
	public void deselect () {
	}
	
	public SampleTestApplet () {
		super();
	}
	
	public static void install (byte [] bArray, short bOffset, byte bLength) throws ISOException {
		SampleTestApplet s = new SampleTestApplet ();
		s.register();
	}
	
	public void process(APDU apdu) throws ISOException{
		byte[] buffer = apdu.getBuffer();
		if (selectingApplet()) return;
		
		if (buffer[ISO7816.OFFSET_CLA] != CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		switch (buffer[ISO7816.OFFSET_INS]) {
		default :
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	
}
