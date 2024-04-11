private void modPow(APDU apdu,byte[] modulus, byte[] exponent) {
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
