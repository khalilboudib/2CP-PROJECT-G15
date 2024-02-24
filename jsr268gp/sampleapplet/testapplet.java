package jsr268gp.sampleapplet;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;

public class testapplet extends Applet {

	private testapplet() {
	}

	public static void install(byte bArray[], short bOffset, byte bLength)
			throws ISOException {
		new testapplet().register();
	}

	public void process(APDU apdu) throws ISOException {
		// TODO Auto-generated method stub

	}

}
