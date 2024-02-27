package clientpackage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import clientpackage.Main;

import com.sun.javacard.apduio.Apdu;
import com.sun.javacard.apduio.CadT1Client;
import com.sun.javacard.apduio.CadTransportException;

import java.util.Scanner;
public class Main{
    private static final byte CLA_MONAPPLET = (byte) 0xB0;
    public static final byte INS_SET_PIN= 0x01;
    public static final byte INS_VERIFY_PIN= 0x02;
    public static final byte INS_SET_CODE= 0x03;
    public static final byte INS_GET_CODE= 0x04;    
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, CadTransportException {

    	Scanner sc = new Scanner(System.in);
    /********** Connexion à la carte *************/
    
        CadT1Client cad;
        Socket sckClient;
        
        try{
            
            sckClient=new Socket("localhost",9025);
            sckClient.setTcpNoDelay(true);
            BufferedInputStream input= new 
                BufferedInputStream(sckClient.getInputStream());
            BufferedOutputStream output=new 
                BufferedOutputStream(sckClient.getOutputStream());
            cad= new CadT1Client(input,output);
        }
        catch(Exception e){
            System.out.println("Impossible de se connecter à la carte");
            return;
        }
        
        
        /**********Mise sous tension de la carte ******************/
        
        try{
            cad.powerUp();
        }
        catch(Exception e){
            System.out.println("Erreur lors de l'envoi de la commande powerup à la carte");
            return;
        }
        
 /******* Sélection de l'applet : Commande APDU de type SELECT************/
        
        Apdu apdu = new Apdu();
        apdu.command[Apdu.CLA] = 0x00;
        apdu.command[Apdu.INS] = (byte) 0xA4;
        apdu.command[Apdu.P1] = 0x04;
        apdu.command[Apdu.P2] = 0x00;
        byte[] appletAID = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,0x07, 0x08,
                             0x09, 0x00, 0x00 };           
        apdu.setDataIn(appletAID);
        cad.exchangeApdu(apdu);
        
        if (apdu.getStatus() != 0x9000 ) {
           System.out.println("Erreur lors de la sélection de l'applet");
           System.exit(1);
        }
        
                        
/************************ Menu principal *********************/
        
        boolean fin=false;
        int triesCount = 3;
        while(!fin && triesCount != 0){
            
            System.out.println();
            System.out.println("Application client JavaCard");
            System.out.println("---------------------------");
            System.out.println();
            System.out.println("1- Set the PIN");
            System.out.println("2- Verify the PIN");
            System.out.println("3- Set code ID");
            System.out.println("4- Get code ID");
            System.out.println("5- EXIT");
            System.out.print("Your choice :");
            
            int choix = sc.nextInt();
            while (!(choix >= 1 && choix <= 5)) {
               choix = sc.nextInt();
            }
            
            apdu=new Apdu();
            apdu.command[Apdu.CLA]=Main.CLA_MONAPPLET;
            apdu.command[Apdu.P1]=0x00;
            apdu.command[Apdu.P2]=0x00;
            apdu.setLe(0x7F);
            byte pinBytes[] = new byte[]{0x00,0x00,0x00,0x00};
                        
            switch (choix) {
            case 1:
            	apdu.command[Apdu.INS] = Main.INS_SET_PIN;
                System.out.println("PIN code (d4 d3 d2 d1): ");
                for(int i = 0;i<4;i++){
                	System.out.print("d"+(i+1)+": ");
                    pinBytes[i] = sc.nextByte();	
                }
                
                apdu.setDataIn(pinBytes);
                
                
                cad.exchangeApdu(apdu);
                if (apdu.getStatus() != 0x9000)
                   System.out.println("Erreur : status word different de 0x9000");
                else 
                	System.out.println("CODE PIN is set");
                break;
            
            case 2 :
                System.out.println("PIN code (d4 d3 d2 d1): ");
                for(int i = 0;i<4;i++){
                	System.out.print("d"+(i+1)+": ");
                    pinBytes[i] = sc.nextByte();	
                }
                
                apdu.setDataIn(pinBytes);

                apdu.command[Apdu.INS] =Main.INS_VERIFY_PIN;
                cad.exchangeApdu(apdu);
                byte[] responce = apdu.getResponseApduBytes();
                //triesCount = responce[1];
                if(responce[0] == (byte)0x01){
                	System.out.println("Allowed");
                }else{
                	triesCount  --;
                	System.out.print("Not Allowed, attempts remaining : "+triesCount);
                }
       
                break;
                
            /*case 3:
                System.out.print("Enter ID code (4 digits): ");
                System.out.print("Enter ID code (d4 d3 d2 d1): ");
                pinString = sc.nextLine();
                // Convert the PIN code string to a byte array
                pinBytes = readDigits(pinString) ;
                
                apdu.setDataIn(pinBytes);
                
                apdu.command[Apdu.INS] = Main.INS_SET_CODE;
                cad.exchangeApdu(apdu);
                if (apdu.getStatus() != 0x9000) 
                    System.out.println("Erreur : status word different de 0x9000");
                else 
                    System.out.println("OK");
                break;
                
            case 4:
                apdu.command[Apdu.INS] = Main.INS_GET_CODE;
                byte[] donnees = new byte[1];
                donnees[0] =5;                
                apdu.setDataIn(donnees);
                cad.exchangeApdu(apdu);
                byte[] responce = apdu.getDataOut(); 
                
                for(int i = 0;i<responce.length;i++){
                	//TODO
                }
                if (apdu.getStatus() != 0x9000) 
                    System.out.println("Erreur : status word different de 0x9000");
                else {
                    System.out.println("Valeur d'initialisation est "+ 
                                        donnees[0]);
                    System.out.println("OK");
                }
                break; */
                
            case 5: 
            	System.out.println("Exiting ...");
                fin=true;
                break;
            }                
        }
        
/**************** Mise hors tension de la carte *****************/
        
        try {
            cad.powerDown();
        } 
        catch (Exception e){
            System.out.println("Erreur lors de l'envoi de la commande Powerdown à la carte");
            return;
        } } 
    public static byte[] readDigits(String input) { 
    	String[] tokens = input.split(" ");
    	if (tokens.length != 4) {
    		return null;
    		} 
    	byte[] digits = new byte[4];
    	try {
    		for (int i = 0; i < 4; i++) {
    			digits[i] = Byte.parseByte(tokens[i]);
    			if (digits[i] < 0 || digits[i] > 9) {
    				return null; 
    			}
    		}
    		}catch (NumberFormatException e)
    	{             return null;        
    	}                  return digits;    
    	} }
