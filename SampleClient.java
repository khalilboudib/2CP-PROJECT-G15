package jsr268gp.sampleclient;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;


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
		CardTerminals list = tf.terminals();
		//CardTerminal cad = list.getTerminal("USB CCID Smart Card Reader 0");
		//CardTerminal cad = list.getTerminal("ACS ACR1281U 0");	
		CardTerminal cad = list.getTerminal("ACS ACR1281 1S Dual Reader ICC 0");
			
			
		try {
			/*
			 *  Remarque: pour le transtypage de tableau byte vers une variable string en hexa 
			 *  utiliser la méthode byteArrayToHexString de la classe Util
			 */
			
			//Etablir la connexion avec la carte à puce 
			Card c = cad.connect("T=0");
			System.out.println("Card: "+c);
			
			//Afficher l'ATR et sa taille (reset the card)
			ATR atr = c.getATR();
			System.out.println("ATR: "+Util.byteArrayToHexString(atr.getBytes(), " ")+"\n");
			
			//Ouverture d'un canal de communication
			CardChannel canal = c.getBasicChannel();

			//Select de l'applet
		                                                                                        	                             
		
			CommandAPDU commande = new CommandAPDU(new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04,(byte) 0x00,(byte) 0x07,(byte) 0xA0,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x04,(byte) 0x00,(byte) 0x02, (byte) 0x7F});
		
			                                                                                                                      
			ResponseAPDU reponse = canal.transmit(commande);
			System.out.println("Reponse SELECT1 : "+Util.byteArrayToHexString(reponse.getBytes(), " ")+"\n");
			
			
			commande = new CommandAPDU(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x03,(byte) 0x20,(byte) 0x20}); // ca marche get challenge
			 

				System.out.println("Commande2 : "+Util.byteArrayToHexString(commande.getBytes(), " "));
			
				//Envoyer la commande APDU
				reponse = canal.transmit(commande);
				
				//Afficher la réponse APDU
				System.out.println("Reponse : "+Util.byteArrayToHexString(reponse.getBytes(), " "));
						

					
			// Déconnexion
			c.disconnect(false);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}