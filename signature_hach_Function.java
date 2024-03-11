//fonction de signature java
public byte[] signData (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
    byte[] digitalSignature = null;
    try{
    // Création d'un objet Signature avec l'algorithme SHA256withRSA
    Signature signature = Signature.getInstance("SHA256withRSA");
    
    // Initialisation de l'objet Signature avec la clé privée
    signature.initSign(privateKey);
    
    // Ajout des données à signer
    signature.update(data);
    
    // Signature des données
     digitalSignature = signature.sign();
    
    }catch(Exception e){
        e.printStackTrace();
    }
    return digitalSignature;
}


//fonction de signature java card
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


// la fonction de hachage java
public static byte[] hashByteArray(byte[] input) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashedBytes = digest.digest(input);
    return hashedBytes;
}


//la foction de hachage java card
private void hashByteArray(APDU apdu) {
    byte[] buffer = apdu.getBuffer();
    short dataLength = apdu.setIncomingAndReceive();
    MessageDigest md;
    md = MessageDigest.getInstance(MessageDigest.ALG_SHA_256,false);
    md.reset();
    md.update(buffer, ISO7816.OFFSET_CDATA, data);
    short DigestLength = md.doFinal(buffer, (short) 0, (short) 0, buffer, (short) 0);
    apdu.setOutgoingAndSend((short) 0,Digest );
}

