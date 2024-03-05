/*
 * @file  HashSample.java
 * @version v1.0
 * Package AID: 4A617661436172644F53 
 * Applet AID:  4A617661436172644F5305
 * @brief The ALgorithm of Hash Sample Code in JavaCard API Specification
 * @comment The purpose of this example is only used to show the usage of API functions and there is no practical significance.
 * @copyright Copyright(C) 2016 JavaCardOS Technologies Co., Ltd. All rights reserved.
 */

package jsr268gp.sampleapplet;

import javacard.framework.*;
import javacard.security.MessageDigest;
import javacard.security.RandomData;
import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.Util;

public class SampleTestApplet extends Applet {

	// constants declaration // 
    // code of CLA byte in the command APDU header
    final static byte RANDOM_CLA = (byte) 0x00;
 
    // codes of INS byte in the command APDU header
   // final static byte  INS = (byte) 0x00;
   
    
   // final static byte P1 = (byte) 0x01;
    
    
   // final static byte P2 = (byte) 0x20;// un nombre de taille 256 bits
  
    byte [] C1 = {0x61,0x62,0x63};
    
   
   
    byte[] generatedArray;
 
    byte[] DataOut;
    
    RandomData randomDataSecure = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
    RandomData randomDataPseudo = RandomData.getInstance(RandomData.ALG_PSEUDO_RANDOM);
    MessageDigest  Data_Secure = MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
   
    
    private SampleTestApplet() {
    }

    public static void install(byte bArray[], short bOffset, byte bLength)
            throws ISOException {
        new SampleTestApplet().register();
    }
    
    
    public void process(APDU apdu) throws ISOException {

        if (selectingApplet()) {
            return;
        }

        short offset = 0;
        short Len_DataIn = 0x03;
        short Len_DataOut = 0x20;
        
        byte[] buffer = apdu.getBuffer();

        generatedArray = JCSystem.makeTransientByteArray(
                (short) buffer[ISO7816.OFFSET_P2], JCSystem.CLEAR_ON_DESELECT);

        switch (buffer[ISO7816.OFFSET_P1]) {
        case (0x01):
           // generatedRandom = secureRandomGenerator(apdu);
            DataOut = secureRandomGenerator(apdu);
            break;

        case (0x02):
          //  generatedRandom = pseudoRandomGenerator(apdu);
            DataOut = pseudoRandomGenerator(apdu);
            break;

        case (0x03):
            //  DataOut = hach256(apdu,C1, offset, Len_DataIn);
              DataOut = hach256(C1, offset, Len_DataIn);
              break;    
            
        default:
            return;
        }

  
  
        Util.arrayCopyNonAtomic(generatedArray, offset, buffer, offset,
                (short) Len_DataOut);
     
       apdu.setOutgoingAndSend(offset, (short) Len_DataOut);

  
       
    }

    public byte[] secureRandomGenerator(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        randomDataSecure.generateData(generatedArray, (short) 0,
                (short) buffer[ISO7816.OFFSET_P2]);
        return generatedArray;
    }

    public byte[] pseudoRandomGenerator(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        randomDataPseudo.generateData(generatedArray, (short) 0,
                (short) buffer[ISO7816.OFFSET_P2]);
        return generatedArray;
    }  

  public byte[] hach256 (byte [] C1 , short offset, short Len_DataIn ) {  
	
	 Data_Secure.reset();
	 Data_Secure.doFinal(C1, offset,(short) Len_DataIn, generatedArray, offset);
	 
   	 return generatedArray;
   }
    


} 	
	
