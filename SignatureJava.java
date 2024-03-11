
import java.security.*;
import java.util.Base64;
import java.security.Signature;

public class SignatureJava {

	public byte[] signData (byte[] data,PrivateKey privateKey)throws NoSuchAlgorithmException{
		byte[] digitalSignature = null;
		try{
		// Cr�ation d'un objet Signature avec l'algorithme SHA256withRSA
	    Signature signature = Signature.getInstance("SHA256withRSA");
	    
	    // Initialisation de l'objet Signature avec la cl� priv�e
	    signature.initSign(privateKey);
	    
	    // Ajout des donn�es � signer
	    signature.update(data);
	    
	    // Signature des donn�es
	     digitalSignature = signature.sign();
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return digitalSignature;
	}
	
	public void main(String[] args) {

        try {
            // Cr�ation d'une instance de cl� priv�e et publique
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Donn�es � signer
            byte[] data = "Exemple de donn�es � signer".getBytes("UTF-8");

            byte[] signdata = signData(data,privateKey);
            
            // V�rification de la signature en utilisant la cl� publique
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(data);
            boolean isVerified = verifier.verify(signdata);

            System.out.println("La signature est v�rifi�e: " + isVerified);

            // Affichage de la signature num�rique
            System.out.println("Signature num�rique: " + Base64.getEncoder().encodeToString(signdata));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
