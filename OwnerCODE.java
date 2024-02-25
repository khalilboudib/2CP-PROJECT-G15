package codes_carte;

import javacard.framework.Util;

public class OwnerCODE {
	
	 // Instance variables
    private byte[] cod; // Array to store CODE

    // Constructor
    public OwnerCODE(byte codeSize) {
        cod = new byte[codeSize];
    }

    // Method to initialize or update the CODE
    public void update(byte[] newCODE, short offset, byte length) {
        // Copy new CODE data into the code array
        Util.arrayCopyNonAtomic(newCODE, offset,cod, (short) 0, length);
    }
    
    public void getcode(byte[] buffer) {
    	Util.arrayCopyNonAtomic(cod, (short) 0,buffer, (short) 0, (short)3);
    }
}
