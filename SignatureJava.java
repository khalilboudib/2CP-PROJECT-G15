
import java.security.*;
import java.util.Base64;
import java.security.Signature;

public class SignatureJava {

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
	
	public void main(String[] args) {

        try {
            // Création d'une instance de clé privée et publique
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Données à signer
            byte[] data = "Exemple de données à signer".getBytes("UTF-8");

            byte[] signdata = signData(data,privateKey);
            
            // Vérification de la signature en utilisant la clé publique
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(data);
            boolean isVerified = verifier.verify(signdata);

            System.out.println("La signature est vérifiée: " + isVerified);

            // Affichage de la signature numérique
            System.out.println("Signature numérique: " + Base64.getEncoder().encodeToString(signdata));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
