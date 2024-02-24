package jsr268gp.sampleclient;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;



import jsr268gp.util.Address;
import jsr268gp.util.Util;

public class SampleClient {

	
	public SampleClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			//Sélectionner votre lecteur de carte
			//Le nom du lecteur se trouve dans la base de registres:
			//Hkey local machine/software / Microsoft/cryptography/calais/readers
			TerminalFactory tf = TerminalFactory.getDefault();
			
			
		// si (objet lecteur=null) alors erreur	
		if (false) //remplacer false par la condition
		{
			System.out.println("debug1");
		}
		try {
			/*
			 *  Remarque: pour le transtypage de tableau byte vers une variable string en hexa 
			 *  utiliser la méthode byteArrayToHexString de la classe Util
			 */

			//Etablir la connexion avec la carte à puce 
			
			//Afficher l'ATR et sa taille (reset the card)
			
			//Trouver la commande APDU qui lit 2 otects de l'adresse de la zone de lecture
			
			//Afficher la commande APDU
			
			//Envoyer la commande APDU
				//CardChannel cc = <objet card>.getBasicChannel();
			
			//Afficher la réponse APDU

			// si (status succes) alors 
			 if (true)	//  remplacer true par la condition
			{
			System.out.println("Succes ");
			System.out.println("\n");

			// Afficher la zone de lecture
			
					// Decompresser les données de retour 
					// à l'aide de la méthode decompresser de la classe Address
					byte [] adresse = {
							/*premier octet de data field */, /* deuxième octet de data field*/
							};
			
					Address ads = new Address(adresse);
		
					
					// Calculer l'adresse de la zone de lecture à l'aide  
					// de méthode add de la classe Address
					byte [] add = {
							(byte) 0x00, (byte) 0x68
							};
		
					
					// Lire 60 octet de la zone de lecture
					
					}
					
			// Déconnexion
			   // <objet carte>.disconnect(false);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
