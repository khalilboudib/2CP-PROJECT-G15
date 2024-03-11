public class RSA extends Applet{  
        byte[] rsaPublic = new byte[4];  
        byte[] rsaPrivate = new byte[4];  
        byte[] rsaPublicMod = new byte[4];  
        RSAPrivateKey pri ;  
        RSAPublicKey pub ;  
        private final static byte INS_ENCRYPT = (byte) 0x20;  
        private final static byte INS_DECRYPT = (byte) 0x30;  
        KeyPair pair ;  
        
        private RSA() {  
          super();  
          register();  
        }  
          
          
        public static void install(byte bArray[], short bOffset, byte bLength)  
          throws ISOException {  
          new RSA();  
        }  
          
          
        public boolean select() {  
          pair = new KeyPair(KeyPair.ALG_RSA, (short) 1024);  
          pair.genKeyPair();  
        pri = (RSAPrivateKey) pair.getPrivate();  
        pub = (RSAPublicKey) pair.getPublic();  
          
          return super.select();  
        }  
          
          
        public void deselect() {  
          super.deselect();  
        }  
          
          
           public void process(APDU apdu) throws ISOException {  
            byte[] buffer = apdu.getBuffer();
            apdu.setIncomingAndReceive();
            byte[] modulus=new byte[64];
            byte[] exponent=new byte[64];
            Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_CDATA, modulus, (short)0, (short) 64);
            switch(buffer[ISO7816.OFFSET_INS])  
            {  
            case INS_ENCRYPT:  
            encrypt(apdu,modulus,exponent);  
            break;  
            case INS_DECRYPT:  
            
            break;  
            }  
           }  
          
          
       
        private void encrypt(APDU apdu,byte[] modulus, byte[] exponent) {
          byte[] buffer=apdu.getBuffer();
          byte[] generator={(byte) 0x02};
          pub.setModulus(modulus, (short) 0, (short)64);
          pub.setExponent(exponent, (short) 0, (short)64);
          short size = 0;  
          try{  
          Cipher cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);  
          cipher.init(pub, Cipher.MODE_ENCRYPT);  
          size =  cipher.doFinal(generator, (short) 0, (short) 1, buffer,  (short) 0);  
          }  
          catch(CryptoException e)  
          {  
          switch(e.getReason())  
          {  
          case CryptoException.ILLEGAL_USE:  
          ISOException.throwIt(ISO7816.SW_DATA_INVALID);  
          break;  
          case CryptoException.ILLEGAL_VALUE:  
          ISOException.throwIt(ISO7816.SW_FILE_INVALID);  
          break;  
          case CryptoException.INVALID_INIT:  
          ISOException.throwIt(ISO7816.SW_FILE_NOT_FOUND);  
          break;  
          case CryptoException.NO_SUCH_ALGORITHM:  
          ISOException.throwIt(ISO7816.SW_FILE_INVALID);  
          break;  
          case CryptoException.UNINITIALIZED_KEY:  
          ISOException.throwIt(ISO7816.SW_FILE_FULL);  
          break;  
          default:  
          ISOException.throwIt(ISO7816.SW_FUNC_NOT_SUPPORTED);  
          break;  
          }  
          }  
          //pub.getExponent(buffer, (short) 0);
          apdu.setOutgoing();  
          apdu.setOutgoingLength(size);  
          apdu.sendBytes((short)0, size);  
        }    
          
    } 
