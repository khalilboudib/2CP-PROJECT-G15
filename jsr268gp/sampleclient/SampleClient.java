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
		
			//S�lectionner votre lecteur de carte
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
			 *  utiliser la m�thode byteArrayToHexString de la classe Util
			 */

			//Etablir la connexion avec la carte � puce 
			
			//Afficher l'ATR et sa taille (reset the card)
			
			//Trouver la commande APDU qui lit 2 otects de l'adresse de la zone de lecture
			
			//Afficher la commande APDU
			
			//Envoyer la commande APDU
				//CardChannel cc = <objet card>.getBasicChannel();
			
			//Afficher la r�ponse APDU

			// si (status succes) alors 
			 if (true)	//  remplacer true par la condition
			{
			System.out.println("Succes ");
			System.out.println("\n");

			// Afficher la zone de lecture
			
					// Decompresser les donn�es de retour 
					// � l'aide de la m�thode decompresser de la classe Address
					byte [] adresse = {
							/*premier octet de data field */, /* deuxi�me octet de data field*/
							};
			
					Address ads = new Address(adresse);
		
					
					// Calculer l'adresse de la zone de lecture � l'aide  
					// de m�thode add de la classe Address
					byte [] add = {
							(byte) 0x00, (byte) 0x68
							};
		
					
					// Lire 60 octet de la zone de lecture
					
					}
					
			// D�connexion
			   // <objet carte>.disconnect(false);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
