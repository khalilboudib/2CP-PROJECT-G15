package jsr268gp.sampleclient;

import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.ResponseAPDU;

abstract class Session {
	
	// constants
	public static final int MODULUS_SIZE = 128;
	public static final int HASH_SIZE = 32;
	public static final int AES_KEY_SIZE = 16;
	public static final byte CLA_APPLET = (byte)0x80;
	public static final byte INS_CS_DH_PUBLIC_KEY = (byte)0x12;
	public static final byte INS_CS_UID = (byte)0x11;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_CS_DH_B = (byte)0x0B;
	public static final byte INS_SC_K = (byte)0x86;


	// vars
	protected String url;
	protected CardChannel canal;
	protected ResponseAPDU respApdu;
	
	protected byte[] K;
	protected byte[] UID;
	
	public Session(CardChannel canal, String url){
		this.url = url;
		this.canal = canal;
		this.K = null;
	}
	
	// authenticate to the remote
	public boolean auth() throws Exception, CardAuthFailed, ServerError{
		
		if(this.canal == null){
			throw new CardNotFound();
		}
		// generate P
		byte[] P = DH.generateRandomPrime(1024).toByteArray();
    	P = DH.adjustArray(P, MODULUS_SIZE);
    	
    	// get A from card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_PUBLIC_KEY, (byte)0x00, (byte)0x00, P, canal);
    	byte[] A = respApdu.getData();
    	
    	// getting card uid
    	respApdu =APDUOps.sendApduToCard(CLA_APPLET, INS_CS_UID, (byte)0x00, (byte)0x00, canal);
    	this.UID = respApdu.getData();
    	
    	// sending auth first phase to the server
    	Map<String, String> mp = new HashMap<String, String>();
    	mp.put("step", "1");
    	mp.put("uid", Base64.getEncoder().encodeToString(UID));
    	mp.put("p", Base64.getEncoder().encodeToString(P));
    	mp.put("a", Base64.getEncoder().encodeToString(A));
    	String data = UtilRequest.mapToJsonString(mp);
    	String result = new String();
    	int requestStatus = UtilRequest.sendRequest("POST", data, url+"/auth", result);
    	if(requestStatus != HttpURLConnection.HTTP_OK){
    		throw new ServerError(requestStatus);
    	}
    	
    	// getting other protocol parameters
    	mp.clear();
    	mp = UtilRequest.jsonStringToMap(result);
    	byte[] B = Base64.getDecoder().decode(mp.get("b"));
    	byte[] sign = Base64.getDecoder().decode(mp.get("sign"));
    	
    	// sending B to the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_B, (byte)0x00, (byte)0x00, B, canal);
    	// sending encrypted signature to the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_SIGN, (byte)0x00, (byte)0x00, sign, canal);

    	// checking the signature verification status
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_SIGN_STATUS, (byte)0x00, (byte)0x00, canal);
    	if(respApdu.getData()[0] != (byte)0x90){
    		throw new CardAuthFailed();
    	}
    	
    	// getting the signature from the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_DH_SIGN, (byte)0x00, (byte)0x00, canal);

    	// sending the signature to the server
    	sign = respApdu.getData();
    	mp.clear();
    	mp.put("step", "2");
    	mp.put("uid", Base64.getEncoder().encodeToString(UID));
    	mp.put("sign", Base64.getEncoder().encodeToString(sign));
    	data = UtilRequest.mapToJsonString(mp);
    	requestStatus = UtilRequest.sendRequest("POST", data, url+"/auth", result);
    	// check server authentication status
    	if(requestStatus != HttpURLConnection.HTTP_OK){
    		throw new ServerError(requestStatus);
    	}
    	
    	// getting K from the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_K, (byte)0x00, (byte)0x00, canal);
    	this.K = respApdu.getData();
		return true; // auth successful
		
	}
	
	public void endSession(){
		this.K = null;
	}
	
	public byte[] getUID(){
		return this.UID;
	}
	
	// this methode must be reimplemented by all users
	abstract public void getPersonalInfo() throws NotAuthenticatedError;

}
// admin session
class SessionAdmin extends Session{
	
	public SessionAdmin(CardChannel canal, String url){
		super(canal, url);
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void getAllDoctors() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void getAllPatients() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	
	public void addNewUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void modifyUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void deleteUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
}

// doctor session
class SessionDoctor extends Session{
	
	public SessionDoctor(CardChannel canal, String url){
		super(canal, url);
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// modify medical record
	public void modifyPatientMedicalRecord() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// list all patients
	public void getAllPatients() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
		
}

// special session
class SessionSpecialPatient extends Session{
	public SessionSpecialPatient(CardChannel canal, String url){
		super(canal, url);
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
}

class CardNotFound extends Exception{
	
}

class CardAuthFailed extends Exception{
	
}

class ServerError extends Exception{
	private int requestStatus;
	
	ServerError(int requestStatus){
		this.requestStatus = requestStatus;
	}
	
	public void printError(){
		System.out.println("Error happened in the server with status code: " + this.requestStatus);
	}
}

class NotAuthenticatedError extends Exception{
	
}
